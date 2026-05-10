package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.response.GradeResponse;
import com.sgu.studentmanagement.entity.Grade;
import com.sgu.studentmanagement.repository.GradeRepository;
import com.sgu.studentmanagement.util.GpaCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GradeService - Business logic for Grade Management
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GradeService {

    private final GradeRepository gradeRepository;

    /**
     * Get all grades for a student
     */
    @Transactional(readOnly = true)
    public List<GradeResponse> getStudentGrades(Long studentId) {
        log.debug("Getting grades for student: {}", studentId);
        return gradeRepository.findByUserId(studentId)
                .stream()
                .map(GradeResponse::fromEntity)
                .toList();
    }

    /**
     * Get student's GPA
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getStudentGPA(Long studentId) {
        log.debug("Calculating GPA for student: {}", studentId);
        
        List<Grade> grades = gradeRepository.findByUserId(studentId);
        
        // Calculate overall GPA
        double overallGPA = GpaCalculator.calculateGPA(grades);
        
        // Calculate total credits
        int totalCredits = GpaCalculator.calculateTotalCredits(grades);
        
        // Get academic standing
        String academicStanding = GpaCalculator.getAcademicStanding(overallGPA);
        
        // Check if at risk
        boolean atRisk = GpaCalculator.isAtRisk(overallGPA);
        
        Map<String, Object> result = new HashMap<>();
        result.put("studentId", studentId);
        result.put("overallGPA", overallGPA);
        result.put("totalCredits", totalCredits);
        result.put("totalCourses", grades.size());
        result.put("passedCourses", grades.stream().filter(g -> g.getPassed() != null && g.getPassed()).count());
        result.put("failedCourses", grades.stream().filter(g -> g.getPassed() != null && !g.getPassed()).count());
        result.put("academicStanding", academicStanding);
        result.put("atRisk", atRisk);
        
        return result;
    }

    /**
     * Get student's transcript (all grades with GPA)
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getStudentTranscript(Long studentId) {
        log.debug("Getting transcript for student: {}", studentId);
        
        List<GradeResponse> grades = getStudentGrades(studentId);
        Map<String, Object> gpaInfo = getStudentGPA(studentId);
        
        Map<String, Object> transcript = new HashMap<>();
        transcript.put("grades", grades);
        transcript.put("gpaInfo", gpaInfo);
        
        return transcript;
    }

    /**
     * Get grades by term for a student
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getStudentGradesByTerm(Long studentId, Long termId) {
        log.debug("Getting grades for student: {} in term: {}", studentId, termId);
        
        List<Grade> allGrades = gradeRepository.findByUserId(studentId);
        
        // Filter grades by term
        List<Grade> termGrades = allGrades.stream()
                .filter(grade -> grade.getEnrollment() != null 
                        && grade.getEnrollment().getTerm() != null
                        && grade.getEnrollment().getTerm().getId().equals(termId))
                .toList();
        
        // Calculate term GPA
        double termGPA = GpaCalculator.calculateGPA(termGrades);
        int termCredits = GpaCalculator.calculateTotalCredits(termGrades);
        
        List<GradeResponse> gradeResponses = termGrades.stream()
                .map(GradeResponse::fromEntity)
                .toList();
        
        Map<String, Object> result = new HashMap<>();
        result.put("studentId", studentId);
        result.put("termId", termId);
        result.put("termGPA", termGPA);
        result.put("termCredits", termCredits);
        result.put("grades", gradeResponses);
        
        return result;
    }
}
