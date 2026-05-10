package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentReportResponse {
    
    // Department Information
    private Long departmentId;
    private String departmentCode;
    private String departmentName;
    private String description;
    
    // Student Statistics
    private StudentStatistics studentStatistics;
    
    // Course Statistics
    private CourseStatistics courseStatistics;
    
    // Academic Performance
    private AcademicPerformanceStats academicPerformance;
    
    // Graduation Statistics
    private GraduationStatistics graduationStatistics;
    
    // Top Performers
    private List<TopStudent> topStudents;
    
    // At-Risk Students
    private List<AtRiskStudent> atRiskStudents;
    
    // Generated Date
    private String generatedDate;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentStatistics {
        private Integer totalStudents;
        private Integer activeStudents;
        private Integer suspendedStudents;
        private Integer graduatedStudents;
        private Map<String, Integer> studentsByClass;
        private Map<String, Integer> studentsByStatus;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseStatistics {
        private Integer totalCourses;
        private Integer mandatoryCourses;
        private Integer electiveCourses;
        private Integer totalEnrollments;
        private Double averageEnrollmentPerCourse;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcademicPerformanceStats {
        private Double averageGPA;
        private Double highestGPA;
        private Double lowestGPA;
        private Map<String, Integer> gpaDistribution;
        private Integer totalGrades;
        private Double passRate;
        private Double failRate;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GraduationStatistics {
        private Integer eligibleForGraduation;
        private Integer expectedGraduatesThisTerm;
        private Integer expectedGraduatesNextTerm;
        private Double averageTimeToGraduate;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopStudent {
        private Long studentId;
        private String studentCode;
        private String studentName;
        private Double gpa;
        private Integer totalCredits;
        private String academicStanding;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AtRiskStudent {
        private Long studentId;
        private String studentCode;
        private String studentName;
        private Double gpa;
        private String riskLevel;
        private List<String> warnings;
    }
}
