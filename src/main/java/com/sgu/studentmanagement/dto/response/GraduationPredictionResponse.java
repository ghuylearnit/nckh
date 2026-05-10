package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * GraduationPredictionResponse - Response DTO for graduation prediction
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraduationPredictionResponse {
    
    private Long studentId;
    private String studentName;
    private String studentCode;
    private String currentTerm;
    private String estimatedGraduationTerm;
    private Integer remainingTerms;
    private Double remainingYears;
    private Integer creditsPerTerm;
    private List<String> assumptions;
    private Map<String, Scenario> scenarios;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Scenario {
        private Integer creditsPerTerm;
        private String graduationTerm;
        private Integer remainingTerms;
        private Double remainingYears;
        private String description;
    }
}
