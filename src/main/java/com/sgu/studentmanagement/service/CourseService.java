package com.sgu.studentmanagement.service;

import com.sgu.studentmanagement.dto.response.CourseResponse;
import com.sgu.studentmanagement.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CourseService - Business logic for Course Management
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    /**
     * Get all courses
     */
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        log.debug("Getting all courses");
        return courseRepository.findAll()
                .stream()
                .map(CourseResponse::fromEntity)
                .toList();
    }

    /**
     * Get course by ID
     */
    @Transactional(readOnly = true)
    public Optional<CourseResponse> getCourseById(Long id) {
        log.debug("Getting course by id: {}", id);
        return courseRepository.findById(id)
                .map(CourseResponse::fromEntity);
    }

    /**
     * Get course by code
     */
    @Transactional(readOnly = true)
    public Optional<CourseResponse> getCourseByCode(String code) {
        log.debug("Getting course by code: {}", code);
        return courseRepository.findByCode(code)
                .map(CourseResponse::fromEntity);
    }

    /**
     * Count total courses
     */
    @Transactional(readOnly = true)
    public long countCourses() {
        log.debug("Counting courses");
        return courseRepository.count();
    }
}
