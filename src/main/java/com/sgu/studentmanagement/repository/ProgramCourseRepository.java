package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.ProgramCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProgramCourseRepository - Repository for ProgramCourse entity
 */
@Repository
public interface ProgramCourseRepository extends JpaRepository<ProgramCourse, Long> {
    
    List<ProgramCourse> findByProgramId(Long programId);
    
    List<ProgramCourse> findByCourseId(Long courseId);
}
