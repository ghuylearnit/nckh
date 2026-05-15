package com.sgu.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * GraduationRequirement Entity - Yêu cầu tốt nghiệp
 */
@Entity
@Table(name = "graduation_requirement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraduationRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Enumerated(EnumType.STRING)
    @Column(name = "requirement_type", nullable = false)
    @Convert(converter = RequirementTypeConverter.class)
    private RequirementType requirementType;

    @Column(name = "requirement_value", nullable = false)
    private String requirementValue;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * RequirementType Enum
     */
    public enum RequirementType {
        TOTAL_CREDITS,
        ENGLISH_CERT,
        IT_CERT,
        PHYSICAL_EDUCATION,
        DEFENSE_EDUCATION,
        GPA_MINIMUM
    }
    
    /**
     * Converter for RequirementType enum to handle case-insensitive conversion
     */
    @Converter
    public static class RequirementTypeConverter implements AttributeConverter<RequirementType, String> {
        
        @Override
        public String convertToDatabaseColumn(RequirementType attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.name();
        }
        
        @Override
        public RequirementType convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.trim().isEmpty()) {
                return null;
            }
            
            try {
                // Try uppercase first (standard)
                return RequirementType.valueOf(dbData.toUpperCase());
            } catch (IllegalArgumentException e) {
                // If fails, log warning and return null
                System.err.println("Unknown RequirementType value: " + dbData);
                return null;
            }
        }
    }
}
