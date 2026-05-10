package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportResponse {
    
    // Basic Information
    private Long studentId;
    private String studentCode;
    private String studentName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String address;
    
    // Academic Information
    private String departmentName;
    private String departmentCode;
    private String className;
    private String programName;
    private String status;
    
    // Academic Performance
    private AcademicPerformance academicPerformance;
    
    // Graduation Status
    private GraduationStatus graduationStatus;
    
    // Course History
    private List<CourseHistory> courseHistory;
    
    // Certificates
    private List<CertificateInfo> certificates;
    
    // Academic Warnings
    private List<String> academicWarnings;
    
    // Generated Date
    private String generatedDate;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcademicPerformance {
        private Double overallGPA;
        private Integer totalCredits;
        private Integer totalCourses;
        private Integer passedCourses;
        private Integer failedCourses;
        private String academicStanding;
        private Boolean atRisk;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GraduationStatus {
        private Boolean canGraduate;
        private Integer requiredCredits;
        private Integer completedCredits;
        private Integer remainingCredits;
        private Double requiredGPA;
        private Double currentGPA;
        private String estimatedGraduationTerm;
        private Integer remainingTerms;
        private List<String> missingRequirements;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseHistory {
        private String termName;
        private String courseCode;
        private String courseName;
        private Integer credits;
        private Double midtermScore;
        private Double finalScore;
        private Double totalScore;
        private String gradeLetter;
        private Double gpaPoint;
        private Boolean passed;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertificateInfo {
        private String certificateType;
        private String certificateName;
        private String issueDate;
        private String expiryDate;
        private String status;
    }
}
