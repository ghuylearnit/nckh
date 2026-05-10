package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * TermRepository - Repository for Term entity
 */
@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
    
    Optional<Term> findByNameAndYear(String name, Integer year);
    
    List<Term> findByYear(Integer year);
}
