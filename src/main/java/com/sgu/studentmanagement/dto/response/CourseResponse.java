package com.sgu.studentmanagement.dto.response;

import com.sgu.studentmanagement.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CourseResponse DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    
    private Long id;
    private String code;
    private String name;
    private Integer credits;
    private String description;
    
    /**
     * Convert Course entity to CourseResponse DTO
     */
    public static CourseResponse fromEntity(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .code(course.getCode())
                .name(course.getName())
                .credits(course.getCredits())
                .description(course.getDescription())
                .build();
    }
}
