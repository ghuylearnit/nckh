package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MissingSubjectResponse - Response DTO for missing subjects
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissingSubjectResponse {
    
    private Long studentId;
    private String studentName;
    private String studentCode;
    private Integer totalMissingSubjects;
    private Integer totalMissingCredits;
    private List<MissingSubject> mandatorySubjects;
    private List<MissingSubject> electiveSubjects;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissingSubject {
        private Long courseId;
        private String courseCode;
        private String courseName;
        private Integer credits;
        private String type; // MANDATORY or ELECTIVE
        private Boolean canRegister;
        private String reason;
        private List<String> prerequisites; // List of prerequisite course codes
    }
}
