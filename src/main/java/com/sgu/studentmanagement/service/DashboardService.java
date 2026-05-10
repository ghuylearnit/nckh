package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.entity.User;
import com.sgu.studentmanagement.repository.*;
import com.sgu.studentmanagement.util.GpaCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * DashboardService - Business logic for Dashboard Statistics
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;

    /**
     * Get overall statistics for dashboard
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getOverallStats() {
        log.debug("Getting overall statistics");
        
        // Count students
        long totalStudents = userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .count();
        
        // Count by status
        long activeStudents = userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .filter(user -> user.getStatus() == User.Status.ACTIVE)
                .count();
        
        long suspendedStudents = userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .filter(user -> user.getStatus() == User.Status.SUSPENDED)
                .count();
        
        long graduatedStudents = userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .filter(user -> user.getStatus() == User.Status.GRADUATED)
                .count();
        
        // Count courses and departments
        long totalCourses = courseRepository.count();
        long totalDepartments = departmentRepository.count();
        
        // Count enrollments and grades
        long totalEnrollments = enrollmentRepository.count();
        long totalGrades = gradeRepository.count();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", totalStudents);
        stats.put("activeStudents", activeStudents);
        stats.put("suspendedStudents", suspendedStudents);
        stats.put("graduatedStudents", graduatedStudents);
        stats.put("totalCourses", totalCourses);
        stats.put("totalDepartments", totalDepartments);
        stats.put("totalEnrollments", totalEnrollments);
        stats.put("totalGrades", totalGrades);
        
        return stats;
    }

    /**
     * Get students by status distribution
     */
    @Transactional(readOnly = true)
    public Map<String, Long> getStudentsByStatus() {
        log.debug("Getting students by status");
        
        Map<String, Long> distribution = new HashMap<>();
        
        for (User.Status status : User.Status.values()) {
            long count = userRepository.findAll().stream()
                    .filter(user -> user.getRole() == User.Role.STUDENT)
                    .filter(user -> user.getStatus() == status)
                    .count();
            distribution.put(status.name(), count);
        }
        
        return distribution;
    }

    /**
     * Get students by department
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getStudentsByDepartment() {
        log.debug("Getting students by department");
        
        Map<String, Object> result = new HashMap<>();
        
        departmentRepository.findAll().forEach(dept -> {
            long count = userRepository.findAll().stream()
                    .filter(user -> user.getRole() == User.Role.STUDENT)
                    .filter(user -> user.getDepartment() != null && user.getDepartment().getId().equals(dept.getId()))
                    .count();
            
            Map<String, Object> deptInfo = new HashMap<>();
            deptInfo.put("departmentId", dept.getId());
            deptInfo.put("departmentName", dept.getName());
            deptInfo.put("departmentCode", dept.getCode());
            deptInfo.put("studentCount", count);
            
            result.put(dept.getCode(), deptInfo);
        });
        
        return result;
    }

    /**
     * Get GPA distribution
     */
    @Transactional(readOnly = true)
    public Map<String, Long> getGPADistribution() {
        log.debug("Getting GPA distribution");
        
        Map<String, Long> distribution = new HashMap<>();
        distribution.put("Xuất sắc (3.6-4.0)", 0L);
        distribution.put("Giỏi (3.2-3.6)", 0L);
        distribution.put("Khá (2.5-3.2)", 0L);
        distribution.put("Trung bình (2.0-2.5)", 0L);
        distribution.put("Yếu (<2.0)", 0L);
        
        userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .forEach(student -> {
                    var grades = gradeRepository.findByUserId(student.getId());
                    
                    // Only count students who have grades
                    if (grades != null && !grades.isEmpty()) {
                        double gpa = GpaCalculator.calculateGPA(grades);
                        
                        if (gpa >= 3.6) {
                            distribution.put("Xuất sắc (3.6-4.0)", distribution.get("Xuất sắc (3.6-4.0)") + 1);
                        } else if (gpa >= 3.2) {
                            distribution.put("Giỏi (3.2-3.6)", distribution.get("Giỏi (3.2-3.6)") + 1);
                        } else if (gpa >= 2.5) {
                            distribution.put("Khá (2.5-3.2)", distribution.get("Khá (2.5-3.2)") + 1);
                        } else if (gpa >= 2.0) {
                            distribution.put("Trung bình (2.0-2.5)", distribution.get("Trung bình (2.0-2.5)") + 1);
                        } else {
                            distribution.put("Yếu (<2.0)", distribution.get("Yếu (<2.0)") + 1);
                        }
                    }
                });
        
        return distribution;
    }
}
