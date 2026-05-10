package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.response.StudentResponse;
import com.sgu.studentmanagement.entity.User;
import com.sgu.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentController - REST API for Student Management
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing students")
public class StudentController {

    private final StudentService studentService;

    /**
     * Get all students
     */
    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve list of all students")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Get student by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a student by their ID")
    public ResponseEntity<StudentResponse> getStudentById(
            @Parameter(description = "Student ID") @PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get student by student code
     */
    @GetMapping("/code/{studentCode}")
    @Operation(summary = "Get student by code", description = "Retrieve a student by their student code")
    public ResponseEntity<StudentResponse> getStudentByCode(
            @Parameter(description = "Student code (e.g., 3122410001)") @PathVariable String studentCode) {
        return studentService.getStudentByCode(studentCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get total number of students
     */
    @GetMapping("/count")
    @Operation(summary = "Count students", description = "Get total number of students")
    public ResponseEntity<Long> countStudents() {
        return ResponseEntity.ok(studentService.countStudents());
    }

    /**
     * Get students by department
     */
    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get students by department", description = "Retrieve students by department ID")
    public ResponseEntity<List<StudentResponse>> getStudentsByDepartment(
            @Parameter(description = "Department ID") @PathVariable Long departmentId) {
        return ResponseEntity.ok(studentService.getStudentsByDepartment(departmentId));
    }

    /**
     * Get students by class
     */
    @GetMapping("/class/{classId}")
    @Operation(summary = "Get students by class", description = "Retrieve students by class ID")
    public ResponseEntity<List<StudentResponse>> getStudentsByClass(
            @Parameter(description = "Class ID") @PathVariable Long classId) {
        return ResponseEntity.ok(studentService.getStudentsByClass(classId));
    }

    /**
     * Get students by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get students by status", description = "Retrieve students by status (ACTIVE, SUSPENDED, EXPELLED, GRADUATED)")
    public ResponseEntity<List<StudentResponse>> getStudentsByStatus(
            @Parameter(description = "Student status") @PathVariable User.Status status) {
        return ResponseEntity.ok(studentService.getStudentsByStatus(status));
    }
}
