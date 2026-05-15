package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.response.GradeResponse;
import com.sgu.studentmanagement.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * GradeController - REST API for Grade Management
 */
@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
@Tag(name = "Grade Management", description = "APIs for managing grades and GPA")
public class GradeController {

    private final GradeService gradeService;

    /**
     * Get all grades for a student
     */
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get student grades", description = "Retrieve all grades for a specific student")
    public ResponseEntity<List<GradeResponse>> getStudentGrades(
            @Parameter(description = "Student ID") @PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getStudentGrades(studentId));
    }

    /**
     * Get student's GPA
     */
    @GetMapping("/student/{studentId}/gpa")
    @Operation(summary = "Get student GPA", description = "Calculate and retrieve student's GPA information")
    public ResponseEntity<Map<String, Object>> getStudentGPA(
            @Parameter(description = "Student ID") @PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getStudentGPA(studentId));
    }

    /**
     * Get student's transcript
     */
    @GetMapping("/student/{studentId}/transcript")
    @Operation(summary = "Get student transcript", description = "Retrieve complete transcript with all grades and GPA")
    public ResponseEntity<Map<String, Object>> getStudentTranscript(
            @Parameter(description = "Student ID") @PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getStudentTranscript(studentId));
    }

    /**
     * Get student's grades by term
     */
    @GetMapping("/student/{studentId}/term/{termId}")
    @Operation(summary = "Get student grades by term", description = "Retrieve grades for a specific student in a specific term")
    public ResponseEntity<Map<String, Object>> getStudentGradesByTerm(
            @Parameter(description = "Student ID") @PathVariable Long studentId,
            @Parameter(description = "Term ID") @PathVariable Long termId) {
        return ResponseEntity.ok(gradeService.getStudentGradesByTerm(studentId, termId));
    }
}
