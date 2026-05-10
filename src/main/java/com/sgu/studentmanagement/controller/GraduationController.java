package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.response.GraduationCheckResponse;
import com.sgu.studentmanagement.dto.response.GraduationPredictionResponse;
import com.sgu.studentmanagement.dto.response.MissingSubjectResponse;
import com.sgu.studentmanagement.service.GraduationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * GraduationController - REST API for Graduation Management
 */
@RestController
@RequestMapping("/api/graduation")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Graduation", description = "Graduation Management APIs")
public class GraduationController {

    private final GraduationService graduationService;

    /**
     * Check graduation requirements for a student
     * 
     * @param studentId Student ID
     * @return Graduation requirements check result
     */
    @GetMapping("/student/{studentId}/check")
    @Operation(summary = "Check graduation requirements", 
               description = "Check if student meets all graduation requirements")
    public ResponseEntity<GraduationCheckResponse> checkGraduationRequirements(
            @PathVariable Long studentId) {
        log.info("Checking graduation requirements for student ID: {}", studentId);
        
        try {
            GraduationCheckResponse response = graduationService.checkGraduationRequirements(studentId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error checking graduation requirements for student ID: {}", studentId, e);
            throw e;
        }
    }

    /**
     * Get missing subjects for graduation
     * 
     * @param studentId Student ID
     * @return List of missing subjects
     */
    @GetMapping("/student/{studentId}/missing-subjects")
    @Operation(summary = "Get missing subjects", 
               description = "Get list of subjects student needs to complete for graduation")
    public ResponseEntity<MissingSubjectResponse> getMissingSubjects(
            @PathVariable Long studentId) {
        log.info("Getting missing subjects for student ID: {}", studentId);
        
        try {
            MissingSubjectResponse response = graduationService.getMissingSubjects(studentId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error getting missing subjects for student ID: {}", studentId, e);
            throw e;
        }
    }

    /**
     * Predict graduation timeline
     * 
     * @param studentId Student ID
     * @return Graduation prediction with multiple scenarios
     */
    @GetMapping("/student/{studentId}/prediction")
    @Operation(summary = "Predict graduation timeline", 
               description = "Predict when student can graduate based on current progress")
    public ResponseEntity<GraduationPredictionResponse> predictGraduation(
            @PathVariable Long studentId) {
        log.info("Predicting graduation for student ID: {}", studentId);
        
        try {
            GraduationPredictionResponse response = graduationService.predictGraduation(studentId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error predicting graduation for student ID: {}", studentId, e);
            throw e;
        }
    }
}

