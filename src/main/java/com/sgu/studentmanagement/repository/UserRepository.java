package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    // Pagination methods for Student List API
    Page<User> findByRole(User.Role role, Pageable pageable);
    
    Page<User> findByRoleAndStudentCodeContainingOrNameContaining(
            User.Role role, String studentCode, String name, Pageable pageable);
    
    Page<User> findByRoleAndDepartmentId(User.Role role, Long departmentId, Pageable pageable);
}
