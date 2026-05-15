package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.response.GraduationStatusResponse;
import com.sgu.studentmanagement.dto.response.StudentListResponse;
import com.sgu.studentmanagement.dto.response.TranscriptResponse;
import com.sgu.studentmanagement.entity.Grade;
import com.sgu.studentmanagement.entity.StudentCertificate;
import com.sgu.studentmanagement.entity.User;
import com.sgu.studentmanagement.repository.GradeRepository;
import com.sgu.studentmanagement.repository.StudentCertificateRepository;
import com.sgu.studentmanagement.repository.UserRepository;
import com.sgu.studentmanagement.util.GpaCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final StudentCertificateRepository studentCertificateRepository;

    @Transactional(readOnly = true)
    public StudentListResponse getStudentList(
            Integer page,
            Integer size,
            String search,
            String className,
            Long departmentId,
            Integer course
    ) {
        // Create pageable
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 10,
                Sort.by("studentCode").ascending()
        );

        // Get students with filters
        Page<User> studentPage;
        
        if (search != null && !search.trim().isEmpty()) {
            // Search by student code or name
            studentPage = userRepository.findByRoleAndStudentCodeContainingOrNameContaining(
                    User.Role.STUDENT, search, search, pageable);
        } else if (departmentId != null) {
            // Filter by department
            studentPage = userRepository.findByRoleAndDepartmentId(
                    User.Role.STUDENT, departmentId, pageable);
        } else {
            // Get all students
            studentPage = userRepository.findByRole(User.Role.STUDENT, pageable);
        }

        // Map to DTOs
        List<StudentListResponse.StudentItem> studentItems = studentPage.getContent().stream()
                .map(this::mapToStudentItem)
                .collect(Collectors.toList());

        // Build pagination info
        StudentListResponse.PaginationInfo paginationInfo = StudentListResponse.PaginationInfo.builder()
                .currentPage(studentPage.getNumber())
                .pageSize(studentPage.getSize())
                .totalElements(studentPage.getTotalElements())
                .totalPages(studentPage.getTotalPages())
                .hasNext(studentPage.hasNext())
                .hasPrevious(studentPage.hasPrevious())
                .build();

        return StudentListResponse.builder()
                .students(studentItems)
                .pagination(paginationInfo)
                .build();
    }

    private StudentListResponse.StudentItem mapToStudentItem(User student) {
        // Get grades
        List<Grade> grades = gradeRepository.findByUserId(student.getId());
        
        // Calculate GPA
        double gpa = grades.isEmpty() ? 0.0 : GpaCalculator.calculateGPA(grades);
        
        // Calculate total credits
        int totalCredits = grades.stream()
                .filter(Grade::getPassed)
                .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                .sum();
        
        // Get academic standing
        String academicStanding = getAcademicStanding(gpa);
        
        // Check if at risk
        boolean atRisk = gpa < 2.0;
        
        // Get certificates
        List<StudentCertificate> certificates = studentCertificateRepository.findByUserId(student.getId());
        int certificateCount = certificates.size();
        
        // Check required certificates (ENGLISH + IT)
        boolean hasEnglish = certificates.stream()
                .anyMatch(c -> c.getCertificateType() == StudentCertificate.CertificateType.ENGLISH);
        boolean hasIT = certificates.stream()
                .anyMatch(c -> c.getCertificateType() == StudentCertificate.CertificateType.IT);
        boolean hasRequiredCertificates = hasEnglish && hasIT;
        
        // Extract course (year) from student code
        Integer course = extractCourseFromStudentCode(student.getStudentCode());
        
        return StudentListResponse.StudentItem.builder()
                .id(student.getId())
                .studentCode(student.getStudentCode())
                .name(student.getName())
                .email(student.getEmail())
                .className(student.getClassEntity() != null ? student.getClassEntity().getName() : null)
                .departmentName(student.getDepartment() != null ? student.getDepartment().getName() : null)
                .departmentCode(student.getDepartment() != null ? student.getDepartment().getCode() : null)
                .course(course)
                .totalCredits(totalCredits)
                .gpa(gpa)
                .academicStanding(academicStanding)
                .status(student.getStatus().name())
                .atRisk(atRisk)
                .certificateCount(certificateCount)
                .hasRequiredCertificates(hasRequiredCertificates)
                .build();
    }

    private String getAcademicStanding(double gpa) {
        if (gpa >= 3.6) return "Xuất sắc";
        if (gpa >= 3.2) return "Giỏi";
        if (gpa >= 2.5) return "Khá";
        if (gpa >= 2.0) return "Trung bình";
        if (gpa > 0) return "Yếu";
        return "Chưa có điểm";
    }

    private Integer extractCourseFromStudentCode(String studentCode) {
        if (studentCode == null || studentCode.length() < 6) {
            return null;
        }
        try {
            // Extract year from student code
            // Format: 3122410001
            //         31 22 4 10001
            //         ^^ ^^ ^ ^^^^^
            //         |  |  | student number
            //         |  |  program type (4 = bachelor)
            //         |  year (22 = 2022)
            //         cohort (K31)
            String yearStr = studentCode.substring(2, 4);
            int year = Integer.parseInt(yearStr);
            return 2000 + year; // Convert 22 to 2022
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public TranscriptResponse getTranscript(Long studentId) {
        // Get student
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        // Verify student role
        if (student.getRole() != User.Role.STUDENT) {
            throw new RuntimeException("User is not a student");
        }
        
        // Get all grades
        List<Grade> grades = gradeRepository.findByUserId(studentId);
        
        // Calculate overall statistics
        double cumulativeGpa = grades.isEmpty() ? 0.0 : GpaCalculator.calculateGPA(grades);
        int totalCredits = grades.stream()
                .filter(Grade::getPassed)
                .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                .sum();
        int totalCourses = grades.size();
        String academicStanding = getAcademicStanding(cumulativeGpa);
        
        // Group grades by term (semester)
        Map<Long, List<Grade>> gradesByTerm = grades.stream()
                .collect(Collectors.groupingBy(g -> g.getEnrollment().getTerm().getId()));
        
        // Build semester transcripts
        List<TranscriptResponse.SemesterTranscript> semesters = gradesByTerm.entrySet().stream()
                .map(entry -> {
                    Long termId = entry.getKey();
                    List<Grade> termGrades = entry.getValue();
                    
                    // Get term info from first grade
                    var term = termGrades.get(0).getEnrollment().getTerm();
                    
                    // Calculate semester GPA
                    double semesterGpa = GpaCalculator.calculateGPA(termGrades);
                    
                    // Calculate semester credits
                    int semesterCredits = termGrades.stream()
                            .filter(Grade::getPassed)
                            .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                            .sum();
                    
                    // Map courses
                    List<TranscriptResponse.CourseGrade> courses = termGrades.stream()
                            .map(grade -> {
                                var enrollment = grade.getEnrollment();
                                var course = enrollment.getCourse();
                                
                                return TranscriptResponse.CourseGrade.builder()
                                        .courseId(course.getId())
                                        .courseCode(course.getCode())
                                        .courseName(course.getName())
                                        .credits(course.getCredits())
                                        .midtermScore(grade.getMidtermScore())
                                        .finalScore(grade.getFinalScore())
                                        .totalScore(grade.getTotalScore())
                                        .gradeLetter(grade.getGradeLetter())
                                        .gpaPoint(grade.getGpaPoint())
                                        .passed(grade.getPassed())
                                        .attemptNumber(enrollment.getAttemptNumber())
                                        .build();
                            })
                            .collect(Collectors.toList());
                    
                    return TranscriptResponse.SemesterTranscript.builder()
                            .termId(termId)
                            .termName(term.getName())
                            .year(term.getYear())
                            .totalCredits(semesterCredits)
                            .semesterGpa(semesterGpa)
                            .courses(courses)
                            .build();
                })
                .sorted(Comparator.comparing(TranscriptResponse.SemesterTranscript::getYear)
                        .thenComparing(TranscriptResponse.SemesterTranscript::getTermName))
                .collect(Collectors.toList());
        
        // Build response
        return TranscriptResponse.builder()
                .studentId(student.getId())
                .studentCode(student.getStudentCode())
                .studentName(student.getName())
                .email(student.getEmail())
                .departmentName(student.getDepartment() != null ? student.getDepartment().getName() : null)
                .className(student.getClassEntity() != null ? student.getClassEntity().getName() : null)
                .totalCredits(totalCredits)
                .totalCourses(totalCourses)
                .cumulativeGpa(cumulativeGpa)
                .academicStanding(academicStanding)
                .semesters(semesters)
                .build();
    }

    @Transactional(readOnly = true)
    public GraduationStatusResponse getGraduationStatus(Boolean eligibleOnly) {
        // Get all students
        List<User> allStudents = userRepository.findByRole(User.Role.STUDENT, 
                PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        
        // Map to graduation candidates
        List<GraduationStatusResponse.GraduationCandidate> candidates = allStudents.stream()
                .map(this::mapToGraduationCandidate)
                .collect(Collectors.toList());
        
        // Filter if eligibleOnly
        if (eligibleOnly != null && eligibleOnly) {
            candidates = candidates.stream()
                    .filter(GraduationStatusResponse.GraduationCandidate::getIsEligible)
                    .collect(Collectors.toList());
        }
        
        // Calculate statistics
        int totalStudents = allStudents.size();
        long eligibleCount = candidates.stream()
                .filter(GraduationStatusResponse.GraduationCandidate::getIsEligible)
                .count();
        int eligibleStudents = (int) eligibleCount;
        int notEligibleStudents = totalStudents - eligibleStudents;
        double eligibilityRate = totalStudents > 0 ? (eligibleCount * 100.0 / totalStudents) : 0.0;
        
        // Sort by readiness percentage (descending)
        candidates.sort(Comparator.comparing(
                GraduationStatusResponse.GraduationCandidate::getReadinessPercentage).reversed());
        
        return GraduationStatusResponse.builder()
                .totalStudents(totalStudents)
                .eligibleStudents(eligibleStudents)
                .notEligibleStudents(notEligibleStudents)
                .eligibilityRate(Math.round(eligibilityRate * 100.0) / 100.0)
                .candidates(candidates)
                .build();
    }

    private GraduationStatusResponse.GraduationCandidate mapToGraduationCandidate(User student) {
        // Get grades
        List<Grade> grades = gradeRepository.findByUserId(student.getId());
        
        // Calculate GPA
        double gpa = grades.isEmpty() ? 0.0 : GpaCalculator.calculateGPA(grades);
        
        // Calculate total credits
        int totalCredits = grades.stream()
                .filter(Grade::getPassed)
                .mapToInt(g -> g.getEnrollment().getCourse().getCredits())
                .sum();
        
        // Get academic standing
        String academicStanding = getAcademicStanding(gpa);
        
        // Get certificates
        List<StudentCertificate> certificates = studentCertificateRepository.findByUserId(student.getId());
        int certificateCount = certificates.size();
        
        // Check required certificates
        boolean hasEnglish = certificates.stream()
                .anyMatch(c -> c.getCertificateType() == StudentCertificate.CertificateType.ENGLISH);
        boolean hasIT = certificates.stream()
                .anyMatch(c -> c.getCertificateType() == StudentCertificate.CertificateType.IT);
        boolean hasRequiredCertificates = hasEnglish && hasIT;
        
        // Determine program type and required credits
        // programType not yet implemented in User entity, default to CU_NHAN
        String programType = "CU_NHAN";
        int requiredCredits = 132;
        
        // Check eligibility
        boolean hasEnoughCredits = totalCredits >= requiredCredits;
        boolean hasGoodGpa = gpa >= 2.0;
        boolean isEligible = hasEnoughCredits && hasRequiredCertificates && hasGoodGpa;
        
        // Calculate readiness percentage
        double creditsPercentage = Math.min(100.0, (totalCredits * 100.0 / requiredCredits));
        double gpaPercentage = Math.min(100.0, (gpa * 100.0 / 4.0));
        double certificatesPercentage = hasRequiredCertificates ? 100.0 : (certificateCount * 50.0);
        double readinessPercentage = (creditsPercentage + gpaPercentage + certificatesPercentage) / 3.0;
        
        // Identify missing requirements
        List<String> missingRequirements = new ArrayList<>();
        if (!hasEnoughCredits) {
            int missingCredits = requiredCredits - totalCredits;
            missingRequirements.add("Thiếu " + missingCredits + " tín chỉ");
        }
        if (!hasEnglish) {
            missingRequirements.add("Chưa có chứng chỉ Ngoại ngữ");
        }
        if (!hasIT) {
            missingRequirements.add("Chưa có chứng chỉ Tin học");
        }
        if (!hasGoodGpa) {
            missingRequirements.add("GPA < 2.0 (hiện tại: " + String.format("%.2f", gpa) + ")");
        }
        
        // Calculate expected graduation
        Integer course = extractCourseFromStudentCode(student.getStudentCode());
        Integer expectedGraduationYear = course != null ? course + 4 : null;
        String expectedGraduationTerm = "HK2";
        
        return GraduationStatusResponse.GraduationCandidate.builder()
                .studentId(student.getId())
                .studentCode(student.getStudentCode())
                .studentName(student.getName())
                .email(student.getEmail())
                .departmentName(student.getDepartment() != null ? student.getDepartment().getName() : null)
                .className(student.getClassEntity() != null ? student.getClassEntity().getName() : null)
                .programType(programType)
                .totalCredits(totalCredits)
                .requiredCredits(requiredCredits)
                .gpa(gpa)
                .academicStanding(academicStanding)
                .certificateCount(certificateCount)
                .hasEnglishCertificate(hasEnglish)
                .hasITCertificate(hasIT)
                .hasRequiredCertificates(hasRequiredCertificates)
                .isEligible(isEligible)
                .readinessPercentage(Math.round(readinessPercentage * 100.0) / 100.0)
                .missingRequirements(missingRequirements)
                .expectedGraduationYear(expectedGraduationYear)
                .expectedGraduationTerm(expectedGraduationTerm)
                .build();
    }
}
