package com.sgu.studentmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EnrollmentRequest - Request DTO for course enrollment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {
    
    private Long studentId;
    private Long courseId;
    private Long termId;
}
