package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.response.AcademicWarningResponse;
import com.sgu.studentmanagement.dto.response.AtRiskStudentResponse;
import com.sgu.studentmanagement.service.AcademicWarningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AcademicWarningController - REST API for Academic Warning System
 */
@RestController
@RequestMapping("/api/academic-warning")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Academic Warning", description = "Academic Warning System APIs")
public class AcademicWarningController {

    private final AcademicWarningService academicWarningService;

    /**
     * Check academic warning status for a student
     * 
     * @param studentId Student ID
     * @return Academic warning status and recommendations
     */
    @GetMapping("/student/{studentId}/check")
    @Operation(summary = "Check academic warning", 
               description = "Check if student has academic warning and get recommendations")
    public ResponseEntity<AcademicWarningResponse> checkAcademicWarning(
            @PathVariable Long studentId) {
        log.info("Checking academic warning for student ID: {}", studentId);
        
        try {
            AcademicWarningResponse response = academicWarningService.checkAcademicWarning(studentId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error checking academic warning for student ID: {}", studentId, e);
            throw e;
        }
    }

    /**
     * Get all at-risk students
     * 
     * @return List of students at risk with their warning levels
     */
    @GetMapping("/at-risk-students")
    @Operation(summary = "Get at-risk students", 
               description = "Get list of all students who are at risk academically")
    public ResponseEntity<AtRiskStudentResponse> getAtRiskStudents() {
        log.info("Getting all at-risk students");
        
        try {
            AtRiskStudentResponse response = academicWarningService.getAtRiskStudents();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error getting at-risk students", e);
            throw e;
        }
    }
}
