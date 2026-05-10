package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.response.StudentResponse;
import com.sgu.studentmanagement.entity.User;
import com.sgu.studentmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * StudentService - Business logic for Student Management
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final UserRepository userRepository;

    /**
     * Get all students
     */
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        log.debug("Getting all students");
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .map(StudentResponse::fromEntity)
                .toList();
    }

    /**
     * Get student by ID
     */
    @Transactional(readOnly = true)
    public Optional<StudentResponse> getStudentById(Long id) {
        log.debug("Getting student by id: {}", id);
        return userRepository.findById(id)
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .map(StudentResponse::fromEntity);
    }

    /**
     * Get student by student code
     */
    @Transactional(readOnly = true)
    public Optional<StudentResponse> getStudentByCode(String studentCode) {
        log.debug("Getting student by code: {}", studentCode);
        return userRepository.findByStudentCode(studentCode)
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .map(StudentResponse::fromEntity);
    }

    /**
     * Count total students
     */
    @Transactional(readOnly = true)
    public long countStudents() {
        log.debug("Counting students");
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .count();
    }

    /**
     * Get students by department
     */
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByDepartment(Long departmentId) {
        log.debug("Getting students by department: {}", departmentId);
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .filter(user -> user.getDepartment() != null && user.getDepartment().getId().equals(departmentId))
                .map(StudentResponse::fromEntity)
                .toList();
    }

    /**
     * Get students by class
     */
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByClass(Long classId) {
        log.debug("Getting students by class: {}", classId);
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .filter(user -> user.getClassEntity() != null && user.getClassEntity().getId().equals(classId))
                .map(StudentResponse::fromEntity)
                .toList();
    }

    /**
     * Get students by status
     */
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByStatus(User.Status status) {
        log.debug("Getting students by status: {}", status);
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == User.Role.STUDENT)
                .filter(user -> user.getStatus() == status)
                .map(StudentResponse::fromEntity)
                .toList();
    }
}
