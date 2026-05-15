package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EnrollmentRepository - Repository for Enrollment entity
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    List<Enrollment> findByUserId(Long userId);
    
    List<Enrollment> findByCourseId(Long courseId);
    
    List<Enrollment> findByTermId(Long termId);
    
    List<Enrollment> findByUserIdAndTermId(Long userId, Long termId);
}
