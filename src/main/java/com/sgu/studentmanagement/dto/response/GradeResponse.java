package com.sgu.studentmanagement.dto.response;

import com.sgu.studentmanagement.entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * GradeResponse DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponse {
    
    private Long id;
    private Long enrollmentId;
    private Double midtermScore;
    private Double finalScore;
    private Double totalScore;
    private String gradeLetter;
    private Double gpaPoint;
    private Boolean passed;
    private LocalDateTime createdAt;
    
    // Student info
    private Long studentId;
    private String studentName;
    private String studentCode;
    
    // Course info
    private Long courseId;
    private String courseCode;
    private String courseName;
    private Integer courseCredits;
    
    // Term info
    private Long termId;
    private String termName;
    
    /**
     * Convert Grade entity to GradeResponse DTO
     */
    public static GradeResponse fromEntity(Grade grade) {
        GradeResponseBuilder builder = GradeResponse.builder()
                .id(grade.getId())
                .midtermScore(grade.getMidtermScore() != null ? grade.getMidtermScore().doubleValue() : null)
                .finalScore(grade.getFinalScore() != null ? grade.getFinalScore().doubleValue() : null)
                .totalScore(grade.getTotalScore() != null ? grade.getTotalScore().doubleValue() : null)
                .gradeLetter(grade.getGradeLetter())
                .gpaPoint(grade.getGpaPoint() != null ? grade.getGpaPoint().doubleValue() : null)
                .passed(grade.getPassed())
                .createdAt(grade.getCreatedAt());
        
        // Add enrollment info
        if (grade.getEnrollment() != null) {
            builder.enrollmentId(grade.getEnrollment().getId());
            
            // Add student info
            if (grade.getEnrollment().getUser() != null) {
                builder.studentId(grade.getEnrollment().getUser().getId())
                       .studentName(grade.getEnrollment().getUser().getName())
                       .studentCode(grade.getEnrollment().getUser().getStudentCode());
            }
            
            // Add course info
            if (grade.getEnrollment().getCourse() != null) {
                builder.courseId(grade.getEnrollment().getCourse().getId())
                       .courseCode(grade.getEnrollment().getCourse().getCode())
                       .courseName(grade.getEnrollment().getCourse().getName())
                       .courseCredits(grade.getEnrollment().getCourse().getCredits());
            }
            
            // Add term info
            if (grade.getEnrollment().getTerm() != null) {
                builder.termId(grade.getEnrollment().getTerm().getId())
                       .termName(grade.getEnrollment().getTerm().getName());
            }
        }
        
        return builder.build();
    }
}
