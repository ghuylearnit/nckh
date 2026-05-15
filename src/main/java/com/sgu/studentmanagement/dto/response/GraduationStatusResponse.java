package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO for graduation status
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraduationStatusResponse {
    
    private Integer totalStudents;
    private Integer eligibleStudents;
    private Integer notEligibleStudents;
    private Double eligibilityRate; // Percentage
    
    private List<GraduationCandidate> candidates;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GraduationCandidate {
        private Long studentId;
        private String studentCode;
        private String studentName;
        private String email;
        private String departmentName;
        private String className;
        private String programType; // CU_NHAN or KY_SU
        
        // Academic info
        private Integer totalCredits;
        private Integer requiredCredits;
        private Double gpa;
        private String academicStanding;
        
        // Certificate info
        private Integer certificateCount;
        private Boolean hasEnglishCertificate;
        private Boolean hasITCertificate;
        private Boolean hasRequiredCertificates;
        
        // Graduation readiness
        private Boolean isEligible;
        private Double readinessPercentage;
        private List<String> missingRequirements;
        
        // Expected graduation
        private Integer expectedGraduationYear;
        private String expectedGraduationTerm;
    }
}
