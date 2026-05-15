package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.response.DepartmentResponse;
import com.sgu.studentmanagement.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DepartmentController - REST API for Department Management
 */
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department Management", description = "APIs for managing departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * Get all departments
     */
    @GetMapping
    @Operation(summary = "Get all departments", description = "Retrieve list of all departments")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    /**
     * Get department by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID", description = "Retrieve a department by its ID")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @Parameter(description = "Department ID") @PathVariable Long id) {
        return departmentService.getDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get department by code
     */
    @GetMapping("/code/{code}")
    @Operation(summary = "Get department by code", description = "Retrieve a department by its code")
    public ResponseEntity<DepartmentResponse> getDepartmentByCode(
            @Parameter(description = "Department code (e.g., CNTT)") @PathVariable String code) {
        return departmentService.getDepartmentByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get total number of departments
     */
    @GetMapping("/count")
    @Operation(summary = "Count departments", description = "Get total number of departments")
    public ResponseEntity<Long> countDepartments() {
        return ResponseEntity.ok(departmentService.countDepartments());
    }
}
