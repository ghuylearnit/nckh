package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for student transcript (bảng điểm)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptResponse {
    
    // Student info
    private Long studentId;
    private String studentCode;
    private String studentName;
    private String email;
    private String departmentName;
    private String className;
    
    // Overall statistics
    private Integer totalCredits;
    private Integer totalCourses;
    private Double cumulativeGpa;
    private String academicStanding;
    
    // Transcript by semester
    private List<SemesterTranscript> semesters;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SemesterTranscript {
        private Long termId;
        private String termName; // HK1, HK2
        private Integer year;
        private Integer totalCredits;
        private Double semesterGpa;
        private List<CourseGrade> courses;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseGrade {
        private Long courseId;
        private String courseCode;
        private String courseName;
        private Integer credits;
        private BigDecimal midtermScore;
        private BigDecimal finalScore;
        private BigDecimal totalScore;
        private String gradeLetter; // A+, A, B+, B, C+, C, D+, D, F
        private BigDecimal gpaPoint;
        private Boolean passed;
        private Integer attemptNumber;
    }
}
