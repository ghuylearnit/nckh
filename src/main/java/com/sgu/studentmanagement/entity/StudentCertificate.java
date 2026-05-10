package com.sgu.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * StudentCertificate Entity - Chứng chỉ sinh viên
 */
@Entity
@Table(name = "student_certificate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "certificate_type", nullable = false)
    @Convert(converter = CertificateTypeConverter.class)
    private CertificateType certificateType;

    @Column(name = "certificate_name", nullable = false)
    private String certificateName; // TOEIC, IELTS, MOS, IC3, etc.

    @Column(name = "score_or_level", length = 100)
    private String scoreOrLevel; // 450, 5.0, Pass, etc.

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(nullable = false)
    private Boolean verified = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * CertificateType Enum
     */
    public enum CertificateType {
        ENGLISH,
        IT,
        PHYSICAL_EDUCATION,
        DEFENSE_EDUCATION,
        OTHER
    }
    
    /**
     * Converter for CertificateType enum to handle case-insensitive conversion
     */
    @Converter
    public static class CertificateTypeConverter implements AttributeConverter<CertificateType, String> {
        
        @Override
        public String convertToDatabaseColumn(CertificateType attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.name();
        }
        
        @Override
        public CertificateType convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.trim().isEmpty()) {
                return null;
            }
            
            try {
                // Try uppercase first (standard)
                return CertificateType.valueOf(dbData.toUpperCase());
            } catch (IllegalArgumentException e) {
                // If fails, log warning and return null
                System.err.println("Unknown CertificateType value: " + dbData);
                return null;
            }
        }
    }
}
