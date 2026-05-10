package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.response.CourseResponse;
import com.sgu.studentmanagement.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CourseController - REST API for Course Management
 */
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Course Management", description = "APIs for managing courses")
public class CourseController {

    private final CourseService courseService;

    /**
     * Get all courses
     */
    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieve list of all courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    /**
     * Get course by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieve a course by its ID")
    public ResponseEntity<CourseResponse> getCourseById(
            @Parameter(description = "Course ID") @PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get course by code
     */
    @GetMapping("/code/{code}")
    @Operation(summary = "Get course by code", description = "Retrieve a course by its code")
    public ResponseEntity<CourseResponse> getCourseByCode(
            @Parameter(description = "Course code (e.g., CS101)") @PathVariable String code) {
        return courseService.getCourseByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get total number of courses
     */
    @GetMapping("/count")
    @Operation(summary = "Count courses", description = "Get total number of courses")
    public ResponseEntity<Long> countCourses() {
        return ResponseEntity.ok(courseService.countCourses());
    }
}
