package com.sgu.studentmanagement.dto.response;

import com.sgu.studentmanagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * StudentResponse DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    
    private Long id;
    private String name;
    private String email;
    private String studentCode;
    private String phone;
    private LocalDate dateOfBirth;
    private String address;
    private String status;
    private LocalDateTime createdAt;
    
    // Department info
    private Long departmentId;
    private String departmentName;
    private String departmentCode;
    
    // Class info
    private Long classId;
    private String className;
    
    /**
     * Convert User entity to StudentResponse DTO
     */
    public static StudentResponse fromEntity(User user) {
        StudentResponseBuilder builder = StudentResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .studentCode(user.getStudentCode())
                .phone(user.getPhone())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .status(user.getStatus() != null ? user.getStatus().name() : null)
                .createdAt(user.getCreatedAt());
        
        // Add department info if exists
        if (user.getDepartment() != null) {
            builder.departmentId(user.getDepartment().getId())
                   .departmentName(user.getDepartment().getName())
                   .departmentCode(user.getDepartment().getCode());
        }
        
        // Add class info if exists
        if (user.getClassEntity() != null) {
            builder.classId(user.getClassEntity().getId())
                   .className(user.getClassEntity().getName());
        }
        
        return builder.build();
    }
}
