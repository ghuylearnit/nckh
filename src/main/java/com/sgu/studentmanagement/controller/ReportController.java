package com.sgu.studentmanagement.controller;

import com.sgu.studentmanagement.dto.response.DepartmentReportResponse;
import com.sgu.studentmanagement.dto.response.StudentReportResponse;
import com.sgu.studentmanagement.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Report Management", description = "APIs for generating student and department reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/student/{studentId}")
    @Operation(
            summary = "Generate Student Report",
            description = "Generate a comprehensive report for a specific student including academic performance, " +
                    "graduation status, course history, certificates, and academic warnings"
    )
    public ResponseEntity<StudentReportResponse> generateStudentReport(
            @PathVariable Long studentId
    ) {
        try {
            StudentReportResponse report = reportService.generateStudentReport(studentId);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            log.error("Error generating student report for studentId: {}", studentId, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/department/{departmentId}")
    @Operation(
            summary = "Generate Department Report",
            description = "Generate a comprehensive report for a specific department including student statistics, " +
                    "course statistics, academic performance, graduation statistics, top students, and at-risk students"
    )
    public ResponseEntity<DepartmentReportResponse> generateDepartmentReport(
            @PathVariable Long departmentId
    ) {
        try {
            DepartmentReportResponse report = reportService.generateDepartmentReport(departmentId);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            log.error("Error generating department report for departmentId: {}", departmentId, e);
            return ResponseEntity.badRequest().build();
        }
    }
}
