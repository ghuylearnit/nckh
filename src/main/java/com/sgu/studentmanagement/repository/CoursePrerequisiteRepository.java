package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.CoursePrerequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CoursePrerequisiteRepository - Repository for CoursePrerequisite entity
 */
@Repository
public interface CoursePrerequisiteRepository extends JpaRepository<CoursePrerequisite, Long> {
    
    List<CoursePrerequisite> findByCourseId(Long courseId);
}
