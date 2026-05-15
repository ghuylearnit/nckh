package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.response.DepartmentReportResponse;
import com.sgu.studentmanagement.dto.response.StudentReportResponse;
import com.sgu.studentmanagement.entity.*;
import com.sgu.studentmanagement.repository.*;
import com.sgu.studentmanagement.util.GpaCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentCertificateRepository studentCertificateRepository;
    private final AcademicWarningRepository academicWarningRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final StudentProgramRepository studentProgramRepository;

    @Transactional(readOnly = true)
    public StudentReportResponse generateStudentReport(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        if (student.getRole() != User.Role.STUDENT) {
            throw new RuntimeException("User is not a student");
        }

        List<Grade> grades = gradeRepository.findByUserId(studentId);
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(studentId);
        List<StudentCertificate> certificates = studentCertificateRepository.findByUserId(studentId);
        List<AcademicWarning> warnings = academicWarningRepository.findByUserId(studentId);

        StudentReportResponse.AcademicPerformance academicPerformance = buildAcademicPerformance(grades);
        StudentReportResponse.GraduationStatus graduationStatus = buildGraduationStatus(student, grades, enrollments, certificates);
        List<StudentReportResponse.CourseHistory> courseHistory = buildCourseHistory(grades);
        List<StudentReportResponse.CertificateInfo> certificateInfos = buildCertificateInfo(certificates);
        List<String> academicWarningMessages = buildAcademicWarnings(warnings, academicPerformance.getOverallGPA());

        return StudentReportResponse.builder()
                .studentId(student.getId())
                .studentCode(student.getStudentCode())
                .studentName(student.getName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .dateOfBirth(student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : null)
                .gender(null)
                .address(student.getAddress())
                .departmentName(student.getDepartment() != null ? student.getDepartment().getName() : null)
                .departmentCode(student.getDepartment() != null ? student.getDepartment().getCode() : null)
                .className(student.getClassEntity() != null ? student.getClassEntity().getName() : null)
                .programName(getProgramName(student))
                .status(student.getStatus().name())
                .academicPerformance(academicPerformance)
                .graduationStatus(graduationStatus)
                .courseHistory(courseHistory)
                .certificates(certificateInfos)
                .academicWarnings(academicWarningMessages)
                .generatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .build();
    }

    @Transactional(readOnly = true)
    public DepartmentReportResponse generateDepartmentReport(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        List<User> students = userRepository.findByDepartmentId(departmentId);
        List<Course> courses = courseRepository.findAll(); // Get all courses since Course doesn't have department field

        DepartmentReportResponse.StudentStatistics studentStats = buildStudentStatistics(students);
        DepartmentReportResponse.CourseStatistics courseStats = buildCourseStatistics(courses, students);
        DepartmentReportResponse.AcademicPerformanceStats academicStats = buildAcademicPerformanceStats(students);
        DepartmentReportResponse.GraduationStatistics graduationStats = buildGraduationStatistics(students);
        List<DepartmentReportResponse.TopStudent> topStudents = getTopStudents(students, 10);
        List<DepartmentReportResponse.AtRiskStudent> atRiskStudents = getAtRiskStudents(students);

        return DepartmentReportResponse.builder()
                .departmentId(department.getId())
                .departmentCode(department.getCode())
                .departmentName(department.getName())
                .description(department.getDescription())
                .studentStatistics(studentStats)
                .courseStatistics(courseStats)
                .academicPerformance(academicStats)
                .graduationStatistics(graduationStats)
                .topStudents(topStudents)
                .atRiskStudents(atRiskStudents)
                .generatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .build();
    }

    private StudentReportResponse.AcademicPerformance buildAcademicPerformance(List<Grade> grades) {
        if (grades.isEmpty()) {
            return StudentReportResponse.AcademicPerformance.builder()
                    .overallGPA(0.0)
                    .totalCredits(0)
                    .totalCourses(0)
                    .passedCourses(0)
                    .failedCourses(0)
                    .academicStanding("Chưa có điểm")
                    .atRisk(false)
                    .build();
        }

        double gpaValue = GpaCalculator.calculateGPA(grades);
        int totalCredits = grades.stream()
                .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                .sum();
        int passedCourses = (int) grades.stream().filter(Grade::getPassed).count();
        int failedCourses = grades.size() - passedCourses;

        return StudentReportResponse.AcademicPerformance.builder()
                .overallGPA(gpaValue)
                .totalCredits(totalCredits)
                .totalCourses(grades.size())
                .passedCourses(passedCourses)
                .failedCourses(failedCourses)
                .academicStanding(getAcademicStanding(gpaValue))
                .atRisk(gpaValue < 2.0)
                .build();
    }

    private StudentReportResponse.GraduationStatus buildGraduationStatus(User student, List<Grade> grades, 
                                                                          List<Enrollment> enrollments, 
                                                                          List<StudentCertificate> certificates) {
        Optional<StudentProgram> studentProgramOpt = studentProgramRepository.findByUserId(student.getId());
        
        int requiredCredits = 140;
        double requiredGPA = 2.0;
        
        if (studentProgramOpt.isPresent()) {
            StudentProgram sp = studentProgramOpt.get();
            if (sp.getProgram() != null && sp.getProgram().getTotalCreditsRequired() != null) {
                requiredCredits = sp.getProgram().getTotalCreditsRequired();
            }
        }

        int completedCredits = grades.stream()
                .filter(Grade::getPassed)
                .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                .sum();

        int remainingCredits = Math.max(0, requiredCredits - completedCredits);

        double currentGPAValue;
        if (grades.isEmpty()) {
            currentGPAValue = 0.0;
        } else {
            currentGPAValue = GpaCalculator.calculateGPA(grades);
        }

        boolean canGraduate = completedCredits >= requiredCredits && 
                             currentGPAValue >= requiredGPA &&
                             hasCertificates(certificates);

        List<String> missingRequirements = new ArrayList<>();
        if (completedCredits < requiredCredits) {
            missingRequirements.add("Thiếu " + remainingCredits + " tín chỉ");
        }
        if (currentGPAValue < requiredGPA) {
            missingRequirements.add("GPA chưa đạt yêu cầu (hiện tại: " + String.format("%.2f", currentGPAValue) + ", yêu cầu: " + requiredGPA + ")");
        }
        if (!hasCertificates(certificates)) {
            missingRequirements.add("Thiếu chứng chỉ bắt buộc");
        }

        int remainingTerms = remainingCredits > 0 ? (int) Math.ceil(remainingCredits / 15.0) : 0;
        String estimatedTerm = remainingTerms > 0 ? "Còn " + remainingTerms + " học kỳ" : "Đủ điều kiện tốt nghiệp";

        return StudentReportResponse.GraduationStatus.builder()
                .canGraduate(canGraduate)
                .requiredCredits(requiredCredits)
                .completedCredits(completedCredits)
                .remainingCredits(remainingCredits)
                .requiredGPA(requiredGPA)
                .currentGPA(currentGPAValue)
                .estimatedGraduationTerm(estimatedTerm)
                .remainingTerms(remainingTerms)
                .missingRequirements(missingRequirements)
                .build();
    }

    private List<StudentReportResponse.CourseHistory> buildCourseHistory(List<Grade> grades) {
        return grades.stream()
                .map(grade -> StudentReportResponse.CourseHistory.builder()
                        .termName(grade.getEnrollment().getTerm() != null ? grade.getEnrollment().getTerm().getName() : "N/A")
                        .courseCode(grade.getEnrollment().getCourse().getCode())
                        .courseName(grade.getEnrollment().getCourse().getName())
                        .credits(grade.getEnrollment().getCourse().getCredits())
                        .midtermScore(grade.getMidtermScore() != null ? grade.getMidtermScore().doubleValue() : null)
                        .finalScore(grade.getFinalScore() != null ? grade.getFinalScore().doubleValue() : null)
                        .totalScore(grade.getTotalScore() != null ? grade.getTotalScore().doubleValue() : null)
                        .gradeLetter(grade.getGradeLetter())
                        .gpaPoint(grade.getGpaPoint() != null ? grade.getGpaPoint().doubleValue() : null)
                        .passed(grade.getPassed())
                        .build())
                .collect(Collectors.toList());
    }

    private List<StudentReportResponse.CertificateInfo> buildCertificateInfo(List<StudentCertificate> certificates) {
        return certificates.stream()
                .map(cert -> StudentReportResponse.CertificateInfo.builder()
                        .certificateType(cert.getCertificateType() != null ? cert.getCertificateType().name() : null)
                        .certificateName(cert.getCertificateName())
                        .issueDate(cert.getIssueDate() != null ? cert.getIssueDate().toString() : null)
                        .expiryDate(cert.getExpiryDate() != null ? cert.getExpiryDate().toString() : null)
                        .status(cert.getVerified() ? "Verified" : "Not Verified")
                        .build())
                .collect(Collectors.toList());
    }

    private List<String> buildAcademicWarnings(List<AcademicWarning> warnings, Double gpa) {
        List<String> messages = new ArrayList<>();
        
        if (gpa != null && gpa < 2.0) {
            messages.add("Cảnh báo: GPA dưới 2.0");
        }
        
        warnings.forEach(warning -> {
            if (warning.getWarningLevel() != null) {
                messages.add("Cảnh báo mức " + warning.getWarningLevel() + ": " + warning.getReason());
            }
        });
        
        return messages;
    }

    private DepartmentReportResponse.StudentStatistics buildStudentStatistics(List<User> students) {
        List<User> studentUsers = students.stream()
                .filter(u -> u.getRole() == User.Role.STUDENT)
                .collect(Collectors.toList());

        int total = studentUsers.size();
        int active = (int) studentUsers.stream().filter(s -> s.getStatus() == User.Status.ACTIVE).count();
        int suspended = (int) studentUsers.stream().filter(s -> s.getStatus() == User.Status.SUSPENDED).count();
        int graduated = (int) studentUsers.stream().filter(s -> s.getStatus() == User.Status.GRADUATED).count();

        Map<String, Integer> byClass = studentUsers.stream()
                .filter(s -> s.getClassEntity() != null)
                .collect(Collectors.groupingBy(
                        s -> s.getClassEntity().getName(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        Map<String, Integer> byStatus = studentUsers.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStatus().name(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        return DepartmentReportResponse.StudentStatistics.builder()
                .totalStudents(total)
                .activeStudents(active)
                .suspendedStudents(suspended)
                .graduatedStudents(graduated)
                .studentsByClass(byClass)
                .studentsByStatus(byStatus)
                .build();
    }

    private DepartmentReportResponse.CourseStatistics buildCourseStatistics(List<Course> courses, List<User> students) {
        int totalCourses = courses.size();
        
        List<Long> studentIds = students.stream()
                .filter(u -> u.getRole() == User.Role.STUDENT)
                .map(User::getId)
                .collect(Collectors.toList());

        int totalEnrollments = 0;
        if (!studentIds.isEmpty()) {
            totalEnrollments = enrollmentRepository.findAll().stream()
                    .filter(e -> studentIds.contains(e.getUser().getId()))
                    .collect(Collectors.toList())
                    .size();
        }

        double avgEnrollment = totalCourses > 0 ? (double) totalEnrollments / totalCourses : 0.0;

        return DepartmentReportResponse.CourseStatistics.builder()
                .totalCourses(totalCourses)
                .mandatoryCourses(0)
                .electiveCourses(0)
                .totalEnrollments(totalEnrollments)
                .averageEnrollmentPerCourse(avgEnrollment)
                .build();
    }

    private DepartmentReportResponse.AcademicPerformanceStats buildAcademicPerformanceStats(List<User> students) {
        List<Long> studentIds = students.stream()
                .filter(u -> u.getRole() == User.Role.STUDENT)
                .map(User::getId)
                .collect(Collectors.toList());

        if (studentIds.isEmpty()) {
            return DepartmentReportResponse.AcademicPerformanceStats.builder()
                    .averageGPA(0.0)
                    .highestGPA(0.0)
                    .lowestGPA(0.0)
                    .gpaDistribution(new HashMap<>())
                    .totalGrades(0)
                    .passRate(0.0)
                    .failRate(0.0)
                    .build();
        }

        List<Double> gpas = new ArrayList<>();
        int totalGrades = 0;
        int passedGrades = 0;

        for (Long studentId : studentIds) {
            List<Grade> grades = gradeRepository.findByUserId(studentId);
            if (!grades.isEmpty()) {
                double gpaValue = GpaCalculator.calculateGPA(grades);
                gpas.add(gpaValue);
                totalGrades += grades.size();
                passedGrades += (int) grades.stream().filter(Grade::getPassed).count();
            }
        }

        double avgGPA = gpas.isEmpty() ? 0.0 : gpas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double highestGPA = gpas.isEmpty() ? 0.0 : gpas.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        double lowestGPA = gpas.isEmpty() ? 0.0 : gpas.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);

        Map<String, Integer> gpaDistribution = new LinkedHashMap<>();
        gpaDistribution.put("Xuất sắc (3.6-4.0)", (int) gpas.stream().filter(g -> g >= 3.6).count());
        gpaDistribution.put("Giỏi (3.2-3.6)", (int) gpas.stream().filter(g -> g >= 3.2 && g < 3.6).count());
        gpaDistribution.put("Khá (2.5-3.2)", (int) gpas.stream().filter(g -> g >= 2.5 && g < 3.2).count());
        gpaDistribution.put("Trung bình (2.0-2.5)", (int) gpas.stream().filter(g -> g >= 2.0 && g < 2.5).count());
        gpaDistribution.put("Yếu (<2.0)", (int) gpas.stream().filter(g -> g < 2.0).count());

        double passRate = totalGrades > 0 ? (double) passedGrades / totalGrades * 100 : 0.0;
        double failRate = 100.0 - passRate;

        return DepartmentReportResponse.AcademicPerformanceStats.builder()
                .averageGPA(avgGPA)
                .highestGPA(highestGPA)
                .lowestGPA(lowestGPA)
                .gpaDistribution(gpaDistribution)
                .totalGrades(totalGrades)
                .passRate(passRate)
                .failRate(failRate)
                .build();
    }

    private DepartmentReportResponse.GraduationStatistics buildGraduationStatistics(List<User> students) {
        List<Long> studentIds = students.stream()
                .filter(u -> u.getRole() == User.Role.STUDENT)
                .map(User::getId)
                .collect(Collectors.toList());

        int eligible = 0;
        for (Long studentId : studentIds) {
            List<Grade> grades = gradeRepository.findByUserId(studentId);
            if (!grades.isEmpty()) {
                int credits = grades.stream()
                        .filter(Grade::getPassed)
                        .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                        .sum();
                double gpaValue = GpaCalculator.calculateGPA(grades);
                if (credits >= 140 && gpaValue >= 2.0) {
                    eligible++;
                }
            }
        }

        return DepartmentReportResponse.GraduationStatistics.builder()
                .eligibleForGraduation(eligible)
                .expectedGraduatesThisTerm(0)
                .expectedGraduatesNextTerm(0)
                .averageTimeToGraduate(4.0)
                .build();
    }

    private List<DepartmentReportResponse.TopStudent> getTopStudents(List<User> students, int limit) {
        List<DepartmentReportResponse.TopStudent> topStudents = new ArrayList<>();

        for (User student : students) {
            if (student.getRole() != User.Role.STUDENT) continue;

            List<Grade> grades = gradeRepository.findByUserId(student.getId());
            if (!grades.isEmpty()) {
                double gpaValue = GpaCalculator.calculateGPA(grades);
                int credits = grades.stream()
                        .filter(Grade::getPassed)
                        .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                        .sum();

                topStudents.add(DepartmentReportResponse.TopStudent.builder()
                        .studentId(student.getId())
                        .studentCode(student.getStudentCode())
                        .studentName(student.getName())
                        .gpa(gpaValue)
                        .totalCredits(credits)
                        .academicStanding(getAcademicStanding(gpaValue))
                        .build());
            }
        }

        return topStudents.stream()
                .sorted(Comparator.comparing(DepartmentReportResponse.TopStudent::getGpa).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private List<DepartmentReportResponse.AtRiskStudent> getAtRiskStudents(List<User> students) {
        List<DepartmentReportResponse.AtRiskStudent> atRiskStudents = new ArrayList<>();

        for (User student : students) {
            if (student.getRole() != User.Role.STUDENT) continue;

            List<Grade> grades = gradeRepository.findByUserId(student.getId());
            if (!grades.isEmpty()) {
                double gpaValue = GpaCalculator.calculateGPA(grades);
                
                if (gpaValue < 2.0) {
                    List<String> warnings = new ArrayList<>();
                    String riskLevel;
                    
                    if (gpaValue < 1.0) {
                        warnings.add("GPA < 1.0 - Nguy cơ bị đình chỉ");
                        riskLevel = "CRITICAL";
                    } else if (gpaValue < 1.5) {
                        warnings.add("GPA < 1.5 - Cảnh báo học vụ mức cao");
                        riskLevel = "HIGH";
                    } else {
                        warnings.add("GPA < 2.0 - Cảnh báo học vụ");
                        riskLevel = "MEDIUM";
                    }

                    atRiskStudents.add(DepartmentReportResponse.AtRiskStudent.builder()
                            .studentId(student.getId())
                            .studentCode(student.getStudentCode())
                            .studentName(student.getName())
                            .gpa(gpaValue)
                            .riskLevel(riskLevel)
                            .warnings(warnings)
                            .build());
                }
            }
        }

        return atRiskStudents.stream()
                .sorted(Comparator.comparing(DepartmentReportResponse.AtRiskStudent::getGpa))
                .collect(Collectors.toList());
    }

    private String getAcademicStanding(double gpa) {
        if (gpa >= 3.6) return "Xuất sắc";
        if (gpa >= 3.2) return "Giỏi";
        if (gpa >= 2.5) return "Khá";
        if (gpa >= 2.0) return "Trung bình";
        return "Yếu";
    }

    private String getProgramName(User student) {
        Optional<StudentProgram> programOpt = studentProgramRepository.findByUserId(student.getId());
        if (programOpt.isPresent() && programOpt.get().getProgram() != null) {
            return programOpt.get().getProgram().getName();
        }
        return null;
    }

    private boolean hasCertificates(List<StudentCertificate> certificates) {
        return !certificates.isEmpty();
    }
}
