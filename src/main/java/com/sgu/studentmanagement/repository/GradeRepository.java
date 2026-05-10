package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * GradeRepository - Repository for Grade entity
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    
    Optional<Grade> findByEnrollmentId(Long enrollmentId);
    
    /**
     * Find all grades for a specific user (student)
     * Eager load enrollment, course, and term to avoid lazy loading issues
     */
    @Query("SELECT g FROM Grade g " +
           "JOIN FETCH g.enrollment e " +
           "JOIN FETCH e.course c " +
           "JOIN FETCH e.term t " +
           "WHERE e.user.id = :userId")
    List<Grade> findByUserId(@Param("userId") Long userId);
}
