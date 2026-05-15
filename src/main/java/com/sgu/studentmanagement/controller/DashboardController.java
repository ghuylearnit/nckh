package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * DashboardController - REST API for Dashboard Statistics
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "APIs for dashboard statistics and analytics")
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Get overall statistics
     */
    @GetMapping("/stats")
    @Operation(summary = "Get overall statistics", description = "Retrieve overall system statistics")
    public ResponseEntity<Map<String, Object>> getOverallStats() {
        return ResponseEntity.ok(dashboardService.getOverallStats());
    }

    /**
     * Get students by status distribution
     */
    @GetMapping("/students-by-status")
    @Operation(summary = "Get students by status", description = "Retrieve student distribution by status")
    public ResponseEntity<Map<String, Long>> getStudentsByStatus() {
        return ResponseEntity.ok(dashboardService.getStudentsByStatus());
    }

    /**
     * Get students by department
     */
    @GetMapping("/students-by-department")
    @Operation(summary = "Get students by department", description = "Retrieve student distribution by department")
    public ResponseEntity<Map<String, Object>> getStudentsByDepartment() {
        return ResponseEntity.ok(dashboardService.getStudentsByDepartment());
    }

    /**
     * Get GPA distribution
     */
    @GetMapping("/gpa-distribution")
    @Operation(summary = "Get GPA distribution", description = "Retrieve GPA distribution across all students")
    public ResponseEntity<Map<String, Long>> getGPADistribution() {
        return ResponseEntity.ok(dashboardService.getGPADistribution());
    }
}
