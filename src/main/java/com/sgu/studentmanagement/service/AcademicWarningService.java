package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.response.AcademicWarningResponse;
import com.sgu.studentmanagement.dto.response.AtRiskStudentResponse;
import com.sgu.studentmanagement.entity.Grade;
import com.sgu.studentmanagement.entity.User;
import com.sgu.studentmanagement.repository.GradeRepository;
import com.sgu.studentmanagement.repository.UserRepository;
import com.sgu.studentmanagement.util.GpaCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AcademicWarningService - Business logic for Academic Warning System
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AcademicWarningService {

    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;

    @Transactional(readOnly = true)
    public AcademicWarningResponse checkAcademicWarning(Long studentId) {
        log.debug("Checking academic warning for student ID: {}", studentId);
        try {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
            if (student.getRole() != User.Role.STUDENT) throw new RuntimeException("User is not a student");
            
            List<Grade> grades = gradeRepository.findByUserId(studentId);
            if (grades.isEmpty()) {
                return AcademicWarningResponse.builder().studentId(studentId).studentName(student.getName())
                        .studentCode(student.getStudentCode()).hasWarning(false).currentGPA(0.0).termGPA(0.0)
                        .failedCourses(0).riskLevel("LOW").warnings(new ArrayList<>())
                        .recommendations(List.of("Chưa có dữ liệu điểm")).build();
            }
            
            double currentGPA = GpaCalculator.calculateGPA(grades);
            Long latestTermId = grades.stream().map(grade -> grade.getEnrollment().getTerm().getId())
                    .max(Long::compareTo).orElse(0L);
            List<Grade> termGrades = grades.stream()
                    .filter(grade -> grade.getEnrollment().getTerm().getId().equals(latestTermId))
                    .collect(Collectors.toList());
            double termGPA = termGrades.isEmpty() ? 0.0 : GpaCalculator.calculateGPA(termGrades);
            int failedCourses = (int) grades.stream().filter(grade -> !grade.getPassed()).count();
            int termFailedCourses = (int) termGrades.stream().filter(grade -> !grade.getPassed()).count();
            
            List<String> warnings = new ArrayList<>();
            String riskLevel = "LOW";
            boolean hasWarning = false;
            
            if (currentGPA < 1.0) {
                warnings.add("GPA tổng < 1.0 - Nguy cơ bị đình chỉ học tập");
                riskLevel = "CRITICAL";
                hasWarning = true;
            } else if (currentGPA < 1.5) {
                warnings.add("GPA tổng < 1.5 - Cảnh báo học vụ mức cao");
                riskLevel = "HIGH";
                hasWarning = true;
            } else if (currentGPA < 2.0) {
                warnings.add("GPA tổng < 2.0 - Cảnh báo học vụ");
                riskLevel = "MEDIUM";
                hasWarning = true;
            }
            
            if (termGPA < 1.0) {
                warnings.add("GPA học kỳ < 1.0 - Kết quả học tập kém");
                if (riskLevel.equals("LOW")) riskLevel = "HIGH";
                hasWarning = true;
            } else if (termGPA < 1.5) {
                warnings.add("GPA học kỳ < 1.5 - Cần cải thiện");
                if (riskLevel.equals("LOW")) riskLevel = "MEDIUM";
                hasWarning = true;
            }
            
            if (failedCourses >= 5) {
                warnings.add("Đã rớt " + failedCourses + " môn - Nguy cơ cao");
                if (riskLevel.equals("LOW") || riskLevel.equals("MEDIUM")) riskLevel = "HIGH";
                hasWarning = true;
            } else if (failedCourses >= 3) {
                warnings.add("Đã rớt " + failedCourses + " môn");
                if (riskLevel.equals("LOW")) riskLevel = "MEDIUM";
                hasWarning = true;
            }
            
            if (termFailedCourses >= 3) {
                warnings.add("Rớt " + termFailedCourses + " môn trong học kỳ này");
                if (riskLevel.equals("LOW")) riskLevel = "MEDIUM";
                hasWarning = true;
            }
            
            List<String> recommendations = new ArrayList<>();
            if (hasWarning) {
                if (riskLevel.equals("CRITICAL")) {
                    recommendations.add("Gặp cố vấn học tập NGAY LẬP TỨC");
                    recommendations.add("Xem xét giảm số tín chỉ đăng ký");
                    recommendations.add("Tham gia lớp học phụ đạo");
                    recommendations.add("Cân nhắc nghỉ học tạm thời để điều chỉnh");
                } else if (riskLevel.equals("HIGH")) {
                    recommendations.add("Gặp cố vấn học tập để được tư vấn");
                    recommendations.add("Tập trung vào các môn cơ bản");
                    recommendations.add("Tham gia nhóm học tập");
                    recommendations.add("Giảm hoạt động ngoại khóa");
                } else if (riskLevel.equals("MEDIUM")) {
                    recommendations.add("Cải thiện phương pháp học tập");
                    recommendations.add("Tham gia buổi tư vấn học tập");
                    recommendations.add("Ôn tập thường xuyên");
                    recommendations.add("Tìm kiếm sự hỗ trợ từ giảng viên");
                }
            } else {
                recommendations.add("Duy trì kết quả học tập tốt");
                recommendations.add("Tiếp tục nỗ lực");
                if (currentGPA >= 3.0) recommendations.add("Xem xét tham gia các hoạt động nghiên cứu");
            }
            
            return AcademicWarningResponse.builder().studentId(studentId).studentName(student.getName())
                    .studentCode(student.getStudentCode()).hasWarning(hasWarning).currentGPA(currentGPA)
                    .termGPA(termGPA).failedCourses(failedCourses).riskLevel(riskLevel)
                    .warnings(warnings).recommendations(recommendations).build();
        } catch (Exception e) {
            log.error("Error checking academic warning for student ID: {}", studentId, e);
            throw new RuntimeException("Error checking academic warning: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public AtRiskStudentResponse getAtRiskStudents() {
        log.debug("Getting all at-risk students");
        try {
            List<User> allStudents = userRepository.findAll().stream()
                    .filter(user -> user.getRole() == User.Role.STUDENT).collect(Collectors.toList());
            
            List<AtRiskStudentResponse.AtRiskStudent> atRiskStudents = new ArrayList<>();
            int criticalCount = 0, highCount = 0, mediumCount = 0;
            
            for (User student : allStudents) {
                List<Grade> grades = gradeRepository.findByUserId(student.getId());
                if (grades.isEmpty()) continue;
                
                double currentGPA = GpaCalculator.calculateGPA(grades);
                Long latestTermId = grades.stream().map(grade -> grade.getEnrollment().getTerm().getId())
                        .max(Long::compareTo).orElse(0L);
                List<Grade> termGrades = grades.stream()
                        .filter(grade -> grade.getEnrollment().getTerm().getId().equals(latestTermId))
                        .collect(Collectors.toList());
                double termGPA = termGrades.isEmpty() ? 0.0 : GpaCalculator.calculateGPA(termGrades);
                int failedCourses = (int) grades.stream().filter(grade -> !grade.getPassed()).count();
                
                String riskLevel = "LOW";
                List<String> warnings = new ArrayList<>();
                boolean isAtRisk = false;
                
                if (currentGPA < 1.0) {
                    warnings.add("GPA tổng < 1.0");
                    riskLevel = "CRITICAL";
                    isAtRisk = true;
                    criticalCount++;
                } else if (currentGPA < 1.5 || failedCourses >= 5) {
                    if (currentGPA < 1.5) warnings.add("GPA tổng < 1.5");
                    if (failedCourses >= 5) warnings.add("Rớt " + failedCourses + " môn");
                    riskLevel = "HIGH";
                    isAtRisk = true;
                    highCount++;
                } else if (currentGPA < 2.0 || failedCourses >= 3 || termGPA < 1.5) {
                    if (currentGPA < 2.0) warnings.add("GPA tổng < 2.0");
                    if (failedCourses >= 3) warnings.add("Rớt " + failedCourses + " môn");
                    if (termGPA < 1.5) warnings.add("GPA học kỳ < 1.5");
                    riskLevel = "MEDIUM";
                    isAtRisk = true;
                    mediumCount++;
                }
                
                if (isAtRisk) {
                    String departmentName = student.getDepartment() != null 
                            ? student.getDepartment().getName() : "Chưa xác định";
                    atRiskStudents.add(AtRiskStudentResponse.AtRiskStudent.builder()
                            .studentId(student.getId()).studentName(student.getName())
                            .studentCode(student.getStudentCode()).departmentName(departmentName)
                            .currentGPA(currentGPA).termGPA(termGPA).failedCourses(failedCourses)
                            .riskLevel(riskLevel).warnings(warnings).build());
                }
            }
            
            atRiskStudents.sort((a, b) -> {
                int riskCompare = getRiskLevelPriority(a.getRiskLevel()) - getRiskLevelPriority(b.getRiskLevel());
                if (riskCompare != 0) return riskCompare;
                return Double.compare(a.getCurrentGPA(), b.getCurrentGPA());
            });
            
            return AtRiskStudentResponse.builder().totalAtRisk(atRiskStudents.size())
                    .criticalRisk(criticalCount).highRisk(highCount).mediumRisk(mediumCount)
                    .students(atRiskStudents).build();
        } catch (Exception e) {
            log.error("Error getting at-risk students", e);
            throw new RuntimeException("Error getting at-risk students: " + e.getMessage(), e);
        }
    }
    
    private int getRiskLevelPriority(String riskLevel) {
        switch (riskLevel) {
            case "CRITICAL": return 1;
            case "HIGH": return 2;
            case "MEDIUM": return 3;
            default: return 4;
        }
    }
}
