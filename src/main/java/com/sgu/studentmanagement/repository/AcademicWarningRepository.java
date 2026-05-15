package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.AcademicWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AcademicWarningRepository - Repository for AcademicWarning entity
 */
@Repository
public interface AcademicWarningRepository extends JpaRepository<AcademicWarning, Long> {
    
    List<AcademicWarning> findByUserId(Long userId);
    
    List<AcademicWarning> findByUserIdAndStatus(Long userId, AcademicWarning.Status status);
    
    List<AcademicWarning> findByStatus(AcademicWarning.Status status);
}
