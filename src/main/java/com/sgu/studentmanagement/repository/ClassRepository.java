package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ClassRepository - Repository for Class entity
 */
@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    
    Optional<Class> findByName(String name);
    
    List<Class> findByDepartmentId(Long departmentId);
    
    List<Class> findByYear(String year);
}
