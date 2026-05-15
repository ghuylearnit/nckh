package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.response.GraduationCheckResponse;
import com.sgu.studentmanagement.dto.response.GraduationPredictionResponse;
import com.sgu.studentmanagement.dto.response.MissingSubjectResponse;
import com.sgu.studentmanagement.entity.*;
import com.sgu.studentmanagement.repository.*;
import com.sgu.studentmanagement.util.GpaCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * GraduationService - Business logic for Graduation Management
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GraduationService {

    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentProgramRepository studentProgramRepository;
    private final ProgramCourseRepository programCourseRepository;
    private final StudentCertificateRepository studentCertificateRepository;
    private final GraduationRequirementRepository graduationRequirementRepository;
    private final CoursePrerequisiteRepository coursePrerequisiteRepository;

    /**
     * Check if student meets graduation requirements
     */
    @Transactional(readOnly = true)
    public GraduationCheckResponse checkGraduationRequirements(Long studentId) {
        log.debug("Checking graduation requirements for student ID: {}", studentId);
        
        try {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
            
            if (student.getRole() != User.Role.STUDENT) {
                throw new RuntimeException("User is not a student");
            }
            
            StudentProgram studentProgram = null;
            try {
                studentProgram = studentProgramRepository.findByUserId(studentId).stream().findFirst().orElse(null);
            } catch (Exception e) {
                log.warn("Error fetching student program: {}", e.getMessage());
            }
            
            Long programId = null;
            String programName = "Chưa xác định chương trình đào tạo";
            if (studentProgram != null && studentProgram.getProgram() != null) {
                programId = studentProgram.getProgram().getId();
                programName = studentProgram.getProgram().getName();
            }
            
            List<GraduationRequirement> gradRequirements = new ArrayList<>();
            if (programId != null) {
                try {
                    gradRequirements = graduationRequirementRepository.findByProgramId(programId);
                } catch (Exception e) {
                    log.warn("Error fetching graduation requirements: {}", e.getMessage());
                }
            }
            
            int requiredTotalCredits = 140;
            double requiredGpa = 2.0;
            for (GraduationRequirement req : gradRequirements) {
                try {
                    if (req.getRequirementType() == GraduationRequirement.RequirementType.TOTAL_CREDITS) {
                        requiredTotalCredits = Integer.parseInt(req.getRequirementValue());
                    } else if (req.getRequirementType() == GraduationRequirement.RequirementType.GPA_MINIMUM) {
                        requiredGpa = Double.parseDouble(req.getRequirementValue());
                    }
                } catch (Exception e) {
                    log.warn("Error parsing requirement: {}", e.getMessage());
                }
            }
            
            List<Grade> grades = gradeRepository.findByUserId(studentId);
            int completedCredits = grades.stream().filter(Grade::getPassed)
                    .mapToInt(grade -> grade.getEnrollment().getCourse().getCredits()).sum();
            double currentGpa = GpaCalculator.calculateGPA(grades);
            
            List<Long> completedCourseIds = grades.stream().filter(Grade::getPassed)
                    .map(grade -> grade.getEnrollment().getCourse().getId()).collect(Collectors.toList());
            
            List<ProgramCourse> programCourses = new ArrayList<>();
            if (programId != null) {
                try {
                    programCourses = programCourseRepository.findByProgramId(programId);
                } catch (Exception e) {
                    log.warn("Error fetching program courses: {}", e.getMessage());
                }
            }
            
            long completedMandatoryCourses = programCourses.stream()
                    .filter(pc -> pc.getCourseType() == ProgramCourse.CourseType.MANDATORY)
                    .filter(pc -> completedCourseIds.contains(pc.getCourse().getId())).count();
            long totalMandatoryCourses = programCourses.stream()
                    .filter(pc -> pc.getCourseType() == ProgramCourse.CourseType.MANDATORY).count();
            
            long completedElectiveCourses = programCourses.stream()
                    .filter(pc -> pc.getCourseType() == ProgramCourse.CourseType.ELECTIVE)
                    .filter(pc -> completedCourseIds.contains(pc.getCourse().getId())).count();
            long totalElectiveCourses = programCourses.stream()
                    .filter(pc -> pc.getCourseType() == ProgramCourse.CourseType.ELECTIVE).count();
            
            List<StudentCertificate> certificates = new ArrayList<>();
            try {
                certificates = studentCertificateRepository.findByUserId(studentId);
            } catch (Exception e) {
                log.warn("Error fetching certificates: {}", e.getMessage());
            }
            
            boolean hasEnglishCert = certificates.stream()
                    .anyMatch(cert -> cert.getCertificateType() == StudentCertificate.CertificateType.ENGLISH);
            boolean hasItCert = certificates.stream()
                    .anyMatch(cert -> cert.getCertificateType() == StudentCertificate.CertificateType.IT);
            boolean hasPhysicalEd = certificates.stream()
                    .anyMatch(cert -> cert.getCertificateType() == StudentCertificate.CertificateType.PHYSICAL_EDUCATION);
            boolean hasMilitaryEd = certificates.stream()
                    .anyMatch(cert -> cert.getCertificateType() == StudentCertificate.CertificateType.DEFENSE_EDUCATION);
            boolean allCertsMet = hasEnglishCert && hasItCert && hasPhysicalEd && hasMilitaryEd;
            
            GraduationCheckResponse.CreditRequirement creditReq = GraduationCheckResponse.CreditRequirement.builder()
                    .required(requiredTotalCredits).completed(completedCredits)
                    .remaining(Math.max(0, requiredTotalCredits - completedCredits))
                    .met(completedCredits >= requiredTotalCredits).build();
            
            GraduationCheckResponse.GpaRequirement gpaReq = GraduationCheckResponse.GpaRequirement.builder()
                    .required(requiredGpa).current(currentGpa).met(currentGpa >= requiredGpa).build();
            
            GraduationCheckResponse.CourseRequirement mandatoryReq = GraduationCheckResponse.CourseRequirement.builder()
                    .required((int) totalMandatoryCourses).completed((int) completedMandatoryCourses)
                    .remaining((int) Math.max(0, totalMandatoryCourses - completedMandatoryCourses))
                    .met(totalMandatoryCourses == 0 || completedMandatoryCourses >= totalMandatoryCourses).build();
            
            GraduationCheckResponse.CourseRequirement electiveReq = GraduationCheckResponse.CourseRequirement.builder()
                    .required((int) totalElectiveCourses).completed((int) completedElectiveCourses)
                    .remaining((int) Math.max(0, totalElectiveCourses - completedElectiveCourses))
                    .met(totalElectiveCourses == 0 || completedElectiveCourses >= totalElectiveCourses).build();
            
            GraduationCheckResponse.CertificateRequirement certReq = GraduationCheckResponse.CertificateRequirement.builder()
                    .englishCertificate(hasEnglishCert).itCertificate(hasItCert)
                    .physicalEducation(hasPhysicalEd).militaryEducation(hasMilitaryEd).allMet(allCertsMet).build();
            
            GraduationCheckResponse.RequirementDetails requirements = GraduationCheckResponse.RequirementDetails.builder()
                    .totalCredits(creditReq).gpa(gpaReq).mandatoryCourses(mandatoryReq)
                    .electiveCourses(electiveReq).certificates(certReq).build();
            
            boolean canGraduate = creditReq.getMet() && gpaReq.getMet() && mandatoryReq.getMet() 
                    && electiveReq.getMet() && allCertsMet;
            
            List<String> missingRequirements = new ArrayList<>();
            if (!creditReq.getMet()) missingRequirements.add("Thiếu " + creditReq.getRemaining() + " tín chỉ");
            if (!gpaReq.getMet()) missingRequirements.add(String.format("GPA chưa đạt (hiện tại: %.2f, yêu cầu: %.2f)", currentGpa, requiredGpa));
            if (!mandatoryReq.getMet() && totalMandatoryCourses > 0) missingRequirements.add("Thiếu " + mandatoryReq.getRemaining() + " môn bắt buộc");
            if (!electiveReq.getMet() && totalElectiveCourses > 0) missingRequirements.add("Thiếu " + electiveReq.getRemaining() + " môn tự chọn");
            if (!hasEnglishCert) missingRequirements.add("Thiếu chứng chỉ tiếng Anh");
            if (!hasItCert) missingRequirements.add("Thiếu chứng chỉ tin học");
            if (!hasPhysicalEd) missingRequirements.add("Thiếu chứng chỉ GDTC");
            if (!hasMilitaryEd) missingRequirements.add("Thiếu chứng chỉ GDQP");
            
            return GraduationCheckResponse.builder().studentId(studentId).studentName(student.getName())
                    .studentCode(student.getStudentCode()).programId(programId).programName(programName)
                    .canGraduate(canGraduate).requirements(requirements).missingRequirements(missingRequirements).build();
        } catch (Exception e) {
            log.error("Error checking graduation requirements for student ID: {}", studentId, e);
            throw new RuntimeException("Error checking graduation requirements: " + e.getMessage(), e);
        }
    }

    /**
     * Get missing subjects for graduation
     */
    @Transactional(readOnly = true)
    public MissingSubjectResponse getMissingSubjects(Long studentId) {
        log.debug("Getting missing subjects for student ID: {}", studentId);
        try {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
            if (student.getRole() != User.Role.STUDENT) throw new RuntimeException("User is not a student");
            
            StudentProgram studentProgram = null;
            try {
                studentProgram = studentProgramRepository.findByUserId(studentId).stream().findFirst().orElse(null);
            } catch (Exception e) {
                log.warn("Error fetching student program: {}", e.getMessage());
            }
            
            Long programId = null;
            if (studentProgram != null && studentProgram.getProgram() != null) {
                programId = studentProgram.getProgram().getId();
            }
            
            List<Grade> grades = gradeRepository.findByUserId(studentId);
            Set<Long> completedCourseIds = grades.stream().filter(Grade::getPassed)
                    .map(grade -> grade.getEnrollment().getCourse().getId()).collect(Collectors.toSet());
            
            List<ProgramCourse> programCourses = new ArrayList<>();
            if (programId != null) {
                try {
                    programCourses = programCourseRepository.findByProgramId(programId);
                } catch (Exception e) {
                    log.warn("Error fetching program courses: {}", e.getMessage());
                }
            }
            
            if (programCourses.isEmpty()) {
                return MissingSubjectResponse.builder().studentId(studentId).studentName(student.getName())
                        .studentCode(student.getStudentCode()).totalMissingSubjects(0).totalMissingCredits(0)
                        .mandatorySubjects(new ArrayList<>()).electiveSubjects(new ArrayList<>()).build();
            }
            
            List<MissingSubjectResponse.MissingSubject> mandatorySubjects = new ArrayList<>();
            List<MissingSubjectResponse.MissingSubject> electiveSubjects = new ArrayList<>();
            int totalMissingCredits = 0;
            
            for (ProgramCourse pc : programCourses) {
                Course course = pc.getCourse();
                if (completedCourseIds.contains(course.getId())) continue;
                
                List<CoursePrerequisite> prerequisites = new ArrayList<>();
                try {
                    prerequisites = coursePrerequisiteRepository.findByCourseId(course.getId());
                } catch (Exception e) {
                    log.warn("Error fetching prerequisites for course {}: {}", course.getId(), e.getMessage());
                }
                
                boolean canRegister = true;
                String reason = "Có thể đăng ký";
                List<String> prerequisiteCodes = new ArrayList<>();
                
                for (CoursePrerequisite prereq : prerequisites) {
                    Course prereqCourse = prereq.getPrerequisite();
                    prerequisiteCodes.add(prereqCourse.getCode());
                    if (!completedCourseIds.contains(prereqCourse.getId())) {
                        canRegister = false;
                        reason = "Chưa hoàn thành môn tiên quyết: " + prereqCourse.getCode();
                        break;
                    }
                }
                if (canRegister && !prerequisites.isEmpty()) reason = "Đã hoàn thành môn tiên quyết";
                
                MissingSubjectResponse.MissingSubject missingSubject = MissingSubjectResponse.MissingSubject.builder()
                        .courseId(course.getId()).courseCode(course.getCode()).courseName(course.getName())
                        .credits(course.getCredits()).type(pc.getCourseType().name()).canRegister(canRegister)
                        .reason(reason).prerequisites(prerequisiteCodes).build();
                
                totalMissingCredits += course.getCredits();
                if (pc.getCourseType() == ProgramCourse.CourseType.MANDATORY) {
                    mandatorySubjects.add(missingSubject);
                } else {
                    electiveSubjects.add(missingSubject);
                }
            }
            
            mandatorySubjects.sort((a, b) -> {
                if (a.getCanRegister() != b.getCanRegister()) return a.getCanRegister() ? -1 : 1;
                return a.getCourseCode().compareTo(b.getCourseCode());
            });
            electiveSubjects.sort((a, b) -> {
                if (a.getCanRegister() != b.getCanRegister()) return a.getCanRegister() ? -1 : 1;
                return a.getCourseCode().compareTo(b.getCourseCode());
            });
            
            return MissingSubjectResponse.builder().studentId(studentId).studentName(student.getName())
                    .studentCode(student.getStudentCode())
                    .totalMissingSubjects(mandatorySubjects.size() + electiveSubjects.size())
                    .totalMissingCredits(totalMissingCredits).mandatorySubjects(mandatorySubjects)
                    .electiveSubjects(electiveSubjects).build();
        } catch (Exception e) {
            log.error("Error getting missing subjects for student ID: {}", studentId, e);
            throw new RuntimeException("Error getting missing subjects: " + e.getMessage(), e);
        }
    }

    /**
     * Predict graduation timeline
     */
    @Transactional(readOnly = true)
    public GraduationPredictionResponse predictGraduation(Long studentId) {
        log.debug("Predicting graduation for student ID: {}", studentId);
        try {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
            if (student.getRole() != User.Role.STUDENT) throw new RuntimeException("User is not a student");
            
            List<Grade> grades = gradeRepository.findByUserId(studentId);
            int completedCredits = grades.stream().filter(Grade::getPassed)
                    .mapToInt(grade -> grade.getEnrollment().getCourse().getCredits()).sum();
            
            int requiredTotalCredits = 140;
            StudentProgram studentProgram = null;
            try {
                studentProgram = studentProgramRepository.findByUserId(studentId).stream().findFirst().orElse(null);
            } catch (Exception e) {
                log.warn("Error fetching student program: {}", e.getMessage());
            }
            
            Long programId = null;
            if (studentProgram != null && studentProgram.getProgram() != null) {
                programId = studentProgram.getProgram().getId();
            }
            
            if (programId != null) {
                try {
                    List<GraduationRequirement> gradRequirements = graduationRequirementRepository.findByProgramId(programId);
                    for (GraduationRequirement req : gradRequirements) {
                        if (req.getRequirementType() == GraduationRequirement.RequirementType.TOTAL_CREDITS) {
                            try {
                                requiredTotalCredits = Integer.parseInt(req.getRequirementValue());
                            } catch (NumberFormatException e) {
                                log.warn("Invalid total credits value: {}", req.getRequirementValue());
                            }
                        }
                    }
                } catch (Exception e) {
                    log.warn("Error fetching graduation requirements: {}", e.getMessage());
                }
            }
            
            int remainingCredits = Math.max(0, requiredTotalCredits - completedCredits);
            String currentTerm = "HK2 2024-2025";
            int creditsPerTerm = 15;
            
            Set<Long> uniqueTerms = grades.stream().map(grade -> grade.getEnrollment().getTerm().getId()).collect(Collectors.toSet());
            if (!uniqueTerms.isEmpty() && completedCredits > 0) {
                creditsPerTerm = completedCredits / uniqueTerms.size();
                creditsPerTerm = Math.max(10, Math.min(24, creditsPerTerm));
            }
            
            Map<String, GraduationPredictionResponse.Scenario> scenarios = new HashMap<>();
            
            int fastCreditsPerTerm = 20;
            int fastRemainingTerms = (int) Math.ceil((double) remainingCredits / fastCreditsPerTerm);
            double fastRemainingYears = fastRemainingTerms / 2.0;
            String fastGraduationTerm = calculateGraduationTerm(currentTerm, fastRemainingTerms);
            scenarios.put("fast", GraduationPredictionResponse.Scenario.builder().creditsPerTerm(fastCreditsPerTerm)
                    .graduationTerm(fastGraduationTerm).remainingTerms(fastRemainingTerms).remainingYears(fastRemainingYears)
                    .description("Tốc độ nhanh - Đăng ký nhiều tín chỉ mỗi học kỳ").build());
            
            int normalCreditsPerTerm = 15;
            int normalRemainingTerms = (int) Math.ceil((double) remainingCredits / normalCreditsPerTerm);
            double normalRemainingYears = normalRemainingTerms / 2.0;
            String normalGraduationTerm = calculateGraduationTerm(currentTerm, normalRemainingTerms);
            scenarios.put("normal", GraduationPredictionResponse.Scenario.builder().creditsPerTerm(normalCreditsPerTerm)
                    .graduationTerm(normalGraduationTerm).remainingTerms(normalRemainingTerms).remainingYears(normalRemainingYears)
                    .description("Tốc độ bình thường - Đăng ký số tín chỉ trung bình").build());
            
            int slowCreditsPerTerm = 10;
            int slowRemainingTerms = (int) Math.ceil((double) remainingCredits / slowCreditsPerTerm);
            double slowRemainingYears = slowRemainingTerms / 2.0;
            String slowGraduationTerm = calculateGraduationTerm(currentTerm, slowRemainingTerms);
            scenarios.put("slow", GraduationPredictionResponse.Scenario.builder().creditsPerTerm(slowCreditsPerTerm)
                    .graduationTerm(slowGraduationTerm).remainingTerms(slowRemainingTerms).remainingYears(slowRemainingYears)
                    .description("Tốc độ chậm - Đăng ký ít tín chỉ mỗi học kỳ").build());
            
            String estimatedGraduationTerm = normalGraduationTerm;
            int remainingTerms = normalRemainingTerms;
            double remainingYears = normalRemainingYears;
            
            List<String> assumptions = new ArrayList<>();
            assumptions.add("Đăng ký " + creditsPerTerm + " tín chỉ mỗi học kỳ");
            assumptions.add("Qua tất cả các môn đã đăng ký");
            assumptions.add("Không bị cảnh báo học vụ");
            assumptions.add("Hoàn thành đủ chứng chỉ yêu cầu");
            assumptions.add("Không nghỉ học giữa chừng");
            
            return GraduationPredictionResponse.builder().studentId(studentId).studentName(student.getName())
                    .studentCode(student.getStudentCode()).currentTerm(currentTerm)
                    .estimatedGraduationTerm(estimatedGraduationTerm).remainingTerms(remainingTerms)
                    .remainingYears(remainingYears).creditsPerTerm(creditsPerTerm).assumptions(assumptions)
                    .scenarios(scenarios).build();
        } catch (Exception e) {
            log.error("Error predicting graduation for student ID: {}", studentId, e);
            throw new RuntimeException("Error predicting graduation: " + e.getMessage(), e);
        }
    }
    
    private String calculateGraduationTerm(String currentTerm, int remainingTerms) {
        try {
            String[] parts = currentTerm.split(" ");
            String semester = parts[0];
            String yearRange = parts[1];
            int currentSemester = semester.equals("HK1") ? 1 : 2;
            String[] years = yearRange.split("-");
            int startYear = Integer.parseInt(years[0]);
            int totalSemesters = currentSemester + remainingTerms;
            int yearsToAdd = (totalSemesters - 1) / 2;
            int finalSemester = ((totalSemesters - 1) % 2) + 1;
            int graduationYear = startYear + yearsToAdd;
            String graduationSemester = finalSemester == 1 ? "HK1" : "HK2";
            return graduationSemester + " " + graduationYear + "-" + (graduationYear + 1);
        } catch (Exception e) {
            log.warn("Error calculating graduation term: {}", e.getMessage());
            return "Không xác định";
        }
    }
}
