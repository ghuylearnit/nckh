package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.response.DepartmentResponse;
import com.sgu.studentmanagement.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * DepartmentService - Business logic for Department Management
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    /**
     * Get all departments
     */
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {
        log.debug("Getting all departments");
        return departmentRepository.findAll()
                .stream()
                .map(DepartmentResponse::fromEntity)
                .toList();
    }

    /**
     * Get department by ID
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentResponse> getDepartmentById(Long id) {
        log.debug("Getting department by id: {}", id);
        return departmentRepository.findById(id)
                .map(DepartmentResponse::fromEntity);
    }

    /**
     * Get department by code
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentResponse> getDepartmentByCode(String code) {
        log.debug("Getting department by code: {}", code);
        return departmentRepository.findByCode(code)
                .map(DepartmentResponse::fromEntity);
    }

    /**
     * Count total departments
     */
    @Transactional(readOnly = true)
    public long countDepartments() {
        log.debug("Counting departments");
        return departmentRepository.count();
    }
}
