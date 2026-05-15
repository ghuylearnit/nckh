package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.response.GraduationStatusResponse;
import com.sgu.studentmanagement.dto.response.StudentListResponse;
import com.sgu.studentmanagement.dto.response.TranscriptResponse;
import com.sgu.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Student operations
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    /**
     * Get list of students with pagination, search, and filters
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param search Search by student code or name
     * @param className Filter by class name
     * @param departmentId Filter by department ID
     * @param course Filter by course (year)
     * @return StudentListResponse with pagination info
     */
    @GetMapping
    public ResponseEntity<StudentListResponse> getStudentList(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Integer course
    ) {
        StudentListResponse response = studentService.getStudentList(
                page, size, search, className, departmentId, course
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Get student transcript (bảng điểm)
     * 
     * @param id Student ID
     * @return TranscriptResponse with all grades grouped by semester
     */
    @GetMapping("/{id}/transcript")
    public ResponseEntity<TranscriptResponse> getTranscript(@PathVariable Long id) {
        TranscriptResponse response = studentService.getTranscript(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get graduation status for all students or eligible students only
     * 
     * @param eligibleOnly If true, return only eligible students (default: false)
     * @return GraduationStatusResponse with list of candidates
     */
    @GetMapping("/graduation-status")
    public ResponseEntity<GraduationStatusResponse> getGraduationStatus(
            @RequestParam(required = false, defaultValue = "false") Boolean eligibleOnly
    ) {
        GraduationStatusResponse response = studentService.getGraduationStatus(eligibleOnly);
        return ResponseEntity.ok(response);
    }
}
