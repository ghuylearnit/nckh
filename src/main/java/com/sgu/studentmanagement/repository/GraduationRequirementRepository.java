package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.GraduationRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GraduationRequirementRepository - Repository for GraduationRequirement entity
 */
@Repository
public interface GraduationRequirementRepository extends JpaRepository<GraduationRequirement, Long> {
    
    List<GraduationRequirement> findByProgramId(Long programId);
    
    List<GraduationRequirement> findByProgramIdAndIsMandatory(Long programId, Boolean isMandatory);
}
