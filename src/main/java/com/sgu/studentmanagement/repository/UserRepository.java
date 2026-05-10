package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository - Repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByStudentCode(String studentCode);
    
    boolean existsByEmail(String email);
    
    boolean existsByStudentCode(String studentCode);
    
    List<User> findByDepartmentId(Long departmentId);
}
