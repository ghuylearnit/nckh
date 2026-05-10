package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.request.EnrollmentRequest;
import com.sgu.studentmanagement.dto.response.EnrollmentResponse;
import com.sgu.studentmanagement.entity.*;
import com.sgu.studentmanagement.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * EnrollmentService - Business logic for Enrollment Management
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TermRepository termRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;
    private final CoursePrerequisiteRepository coursePrerequisiteRepository;

    /**
     * Register a student for a course
     */
    @Transactional
    public EnrollmentResponse registerCourse(EnrollmentRequest request) {
        log.debug("Registering course for student ID: {}, course ID: {}", 
                request.getStudentId(), request.getCourseId());
        
        try {
            // Validate student
            User student = userRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + request.getStudentId()));
            
            if (student.getRole() != User.Role.STUDENT) {
                return buildErrorResponse("User is not a student");
            }
            
            // Validate course
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found with ID: " + request.getCourseId()));
            
            // Validate term
            Term term = termRepository.findById(request.getTermId())
                    .orElseThrow(() -> new RuntimeException("Term not found with ID: " + request.getTermId()));
            
            // Check if already enrolled
            List<Enrollment> existingEnrollments = enrollmentRepository.findByUserId(request.getStudentId());
            boolean alreadyEnrolled = existingEnrollments.stream()
                    .anyMatch(e -> e.getCourse().getId().equals(request.getCourseId()) 
                            && e.getTerm().getId().equals(request.getTermId()));
            
            if (alreadyEnrolled) {
                return buildErrorResponse("Sinh viên đã đăng ký môn học này trong học kỳ này");
            }
            
            // Check if already passed this course
            List<Grade> grades = gradeRepository.findByUserId(request.getStudentId());
            boolean alreadyPassed = grades.stream()
                    .anyMatch(g -> g.getEnrollment().getCourse().getId().equals(request.getCourseId()) 
                            && g.getPassed());
            
            if (alreadyPassed) {
                return buildErrorResponse("Sinh viên đã hoàn thành môn học này");
            }
            
            // Check prerequisites
            List<CoursePrerequisite> prerequisites = coursePrerequisiteRepository.findByCourseId(request.getCourseId());
            if (!prerequisites.isEmpty()) {
                Set<Long> completedCourseIds = grades.stream()
                        .filter(Grade::getPassed)
                        .map(grade -> grade.getEnrollment().getCourse().getId())
                        .collect(Collectors.toSet());
                
                for (CoursePrerequisite prereq : prerequisites) {
                    if (prereq.getIsMandatory() && !completedCourseIds.contains(prereq.getPrerequisite().getId())) {
                        return buildErrorResponse("Chưa hoàn thành môn tiên quyết: " + prereq.getPrerequisite().getCode());
                    }
                }
            }
            
            // Check credit limit (max 24 credits per term)
            int currentTermCredits = existingEnrollments.stream()
                    .filter(e -> e.getTerm().getId().equals(request.getTermId()))
                    .mapToInt(e -> e.getCourse().getCredits())
                    .sum();
            
            if (currentTermCredits + course.getCredits() > 24) {
                return buildErrorResponse("Vượt quá số tín chỉ tối đa (24 tín chỉ/học kỳ). Hiện tại: " 
                        + currentTermCredits + ", thêm: " + course.getCredits());
            }
            
            // Create enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setUser(student);
            enrollment.setCourse(course);
            enrollment.setTerm(term);
            enrollment.setStatus(Enrollment.Status.ENROLLED);
            
            Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
            
            return EnrollmentResponse.builder()
                    .success(true)
                    .message("Đăng ký môn học thành công")
                    .enrollmentId(savedEnrollment.getId())
                    .studentId(student.getId())
                    .studentName(student.getName())
                    .courseId(course.getId())
                    .courseCode(course.getCode())
                    .courseName(course.getName())
                    .credits(course.getCredits())
                    .termId(term.getId())
                    .termName(term.getName())
                    .build();
                    
        } catch (RuntimeException e) {
            log.error("Error registering course", e);
            return buildErrorResponse(e.getMessage());
        }
    }
    
    /**
     * Withdraw from a course
     */
    @Transactional
    public EnrollmentResponse withdrawCourse(Long enrollmentId) {
        log.debug("Withdrawing enrollment ID: {}", enrollmentId);
        
        try {
            Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                    .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + enrollmentId));
            
            // Check if already has grade
            Grade grade = gradeRepository.findByEnrollmentId(enrollmentId).orElse(null);
            if (grade != null) {
                return buildErrorResponse("Không thể hủy đăng ký - Môn học đã có điểm");
            }
            
            // Check enrollment status
            if (enrollment.getStatus() == Enrollment.Status.COMPLETED) {
                return buildErrorResponse("Không thể hủy đăng ký - Môn học đã hoàn thành");
            }
            
            if (enrollment.getStatus() == Enrollment.Status.DROPPED) {
                return buildErrorResponse("Môn học đã được hủy đăng ký trước đó");
            }
            
            // Update status to DROPPED
            enrollment.setStatus(Enrollment.Status.DROPPED);
            enrollmentRepository.save(enrollment);
            
            return EnrollmentResponse.builder()
                    .success(true)
                    .message("Hủy đăng ký môn học thành công")
                    .enrollmentId(enrollment.getId())
                    .studentId(enrollment.getUser().getId())
                    .studentName(enrollment.getUser().getName())
                    .courseId(enrollment.getCourse().getId())
                    .courseCode(enrollment.getCourse().getCode())
                    .courseName(enrollment.getCourse().getName())
                    .credits(enrollment.getCourse().getCredits())
                    .termId(enrollment.getTerm().getId())
                    .termName(enrollment.getTerm().getName())
                    .build();
                    
        } catch (RuntimeException e) {
            log.error("Error withdrawing course", e);
            return buildErrorResponse(e.getMessage());
        }
    }
    
    private EnrollmentResponse buildErrorResponse(String message) {
        return EnrollmentResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
