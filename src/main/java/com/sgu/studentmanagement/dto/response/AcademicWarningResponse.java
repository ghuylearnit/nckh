package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AcademicWarningResponse - Response DTO for academic warning check
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicWarningResponse {
    
    private Long studentId;
    private String studentName;
    private String studentCode;
    private Boolean hasWarning;
    private Double currentGPA;
    private Double termGPA;
    private Integer failedCourses;
    private String riskLevel; // LOW, MEDIUM, HIGH, CRITICAL
    private List<String> warnings;
    private List<String> recommendations;
}
