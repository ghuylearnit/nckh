package com.sgu.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO for student list with pagination
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentListResponse {
    
    private List<StudentItem> students;
    private PaginationInfo pagination;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentItem {
        private Long id;
        private String studentCode;
        private String name;
        private String email;
        private String className;
        private String departmentName;
        private String departmentCode;
        private Integer course; // Khóa (năm nhập học)
        private Integer totalCredits;
        private Double gpa;
        private String academicStanding;
        private String status;
        private Boolean atRisk;
        private Integer certificateCount;
        private Boolean hasRequiredCertificates;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaginationInfo {
        private Integer currentPage;
        private Integer pageSize;
        private Long totalElements;
        private Integer totalPages;
        private Boolean hasNext;
        private Boolean hasPrevious;
    }
}
