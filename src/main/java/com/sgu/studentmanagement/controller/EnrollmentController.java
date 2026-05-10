package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.request.EnrollmentRequest;
import com.sgu.studentmanagement.dto.response.EnrollmentResponse;
import com.sgu.studentmanagement.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * EnrollmentController - REST API for Enrollment Management
 */
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Enrollment", description = "Enrollment Management APIs")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    /**
     * Register a student for a course
     * 
     * @param request Enrollment request with studentId, courseId, termId
     * @return Enrollment response with success status and details
     */
    @PostMapping("/register")
    @Operation(summary = "Register for a course", 
               description = "Register a student for a course with validation")
    public ResponseEntity<EnrollmentResponse> registerCourse(
            @RequestBody EnrollmentRequest request) {
        log.info("Registering course - Student: {}, Course: {}, Term: {}", 
                request.getStudentId(), request.getCourseId(), request.getTermId());
        
        try {
            EnrollmentResponse response = enrollmentService.registerCourse(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error registering course", e);
            return ResponseEntity.ok(EnrollmentResponse.builder()
                    .success(false)
                    .message("Lỗi hệ thống: " + e.getMessage())
                    .build());
        }
    }

    /**
     * Withdraw from a course
     * 
     * @param enrollmentId Enrollment ID to withdraw
     * @return Enrollment response with success status
     */
    @DeleteMapping("/{enrollmentId}")
    @Operation(summary = "Withdraw from a course", 
               description = "Withdraw a student from an enrolled course")
    public ResponseEntity<EnrollmentResponse> withdrawCourse(
            @PathVariable Long enrollmentId) {
        log.info("Withdrawing enrollment ID: {}", enrollmentId);
        
        try {
            EnrollmentResponse response = enrollmentService.withdrawCourse(enrollmentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error withdrawing course", e);
            return ResponseEntity.ok(EnrollmentResponse.builder()
                    .success(false)
                    .message("Lỗi hệ thống: " + e.getMessage())
                    .build());
        }
    }
}
