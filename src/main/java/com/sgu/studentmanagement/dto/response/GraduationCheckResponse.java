package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * GraduationCheckResponse - Response DTO for graduation requirements check
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraduationCheckResponse {
    
    private Long studentId;
    private String studentName;
    private String studentCode;
    private Long programId;
    private String programName;
    private Boolean canGraduate;
    private RequirementDetails requirements;
    private List<String> missingRequirements;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequirementDetails {
        private CreditRequirement totalCredits;
        private GpaRequirement gpa;
        private CourseRequirement mandatoryCourses;
        private CourseRequirement electiveCourses;
        private CertificateRequirement certificates;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditRequirement {
        private Integer required;
        private Integer completed;
        private Integer remaining;
        private Boolean met;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GpaRequirement {
        private Double required;
        private Double current;
        private Boolean met;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseRequirement {
        private Integer required;
        private Integer completed;
        private Integer remaining;
        private Boolean met;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertificateRequirement {
        private Boolean englishCertificate;
        private Boolean itCertificate;
        private Boolean physicalEducation;
        private Boolean militaryEducation;
        private Boolean allMet;
    }
}
