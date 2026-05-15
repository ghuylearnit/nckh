package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.StudentCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * StudentCertificateRepository - Repository for StudentCertificate entity
 */
@Repository
public interface StudentCertificateRepository extends JpaRepository<StudentCertificate, Long> {
    
    List<StudentCertificate> findByUserId(Long userId);
    
    List<StudentCertificate> findByUserIdAndCertificateType(Long userId, StudentCertificate.CertificateType certificateType);
}
