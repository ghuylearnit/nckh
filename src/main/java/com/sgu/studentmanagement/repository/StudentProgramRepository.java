package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.StudentProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * StudentProgramRepository - Repository for StudentProgram entity
 */
@Repository
public interface StudentProgramRepository extends JpaRepository<StudentProgram, Long> {
    
    Optional<StudentProgram> findByUserId(Long userId);
    
    List<StudentProgram> findByProgramId(Long programId);
}
