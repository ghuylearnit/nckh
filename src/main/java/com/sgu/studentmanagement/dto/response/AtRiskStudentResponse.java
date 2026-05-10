package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AtRiskStudentResponse - Response DTO for at-risk students
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtRiskStudentResponse {
    
    private Integer totalAtRisk;
    private Integer criticalRisk;
    private Integer highRisk;
    private Integer mediumRisk;
    private List<AtRiskStudent> students;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AtRiskStudent {
        private Long studentId;
        private String studentName;
        private String studentCode;
        private String departmentName;
        private Double currentGPA;
        private Double termGPA;
        private Integer failedCourses;
        private String riskLevel;
        private List<String> warnings;
    }
}
