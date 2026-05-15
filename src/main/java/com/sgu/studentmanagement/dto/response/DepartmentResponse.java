package com.sgu.studentmanagement.dto.response;

import com.sgu.studentmanagement.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DepartmentResponse DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    
    private Long id;
    private String name;
    private String code;
    private String description;
    
    /**
     * Convert Department entity to DepartmentResponse DTO
     */
    public static DepartmentResponse fromEntity(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .description(department.getDescription())
                .build();
    }
}
