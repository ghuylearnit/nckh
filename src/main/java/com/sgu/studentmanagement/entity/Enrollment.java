package com.sgu.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Enrollment Entity - Đăng ký môn học
 */
@Entity
@Table(name = "enrollment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    @Convert(converter = StatusConverter.class)
    @Column(nullable = false)
    private Status status = Status.ENROLLED;

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber = 1;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Status Enum
     */
    public enum Status {
        ENROLLED,
        COMPLETED,
        DROPPED,
        FAILED
    }
    
    /**
     * Converter to handle case-insensitive enum values from database
     */
    @Converter
    public static class StatusConverter implements AttributeConverter<Status, String> {
        
        @Override
        public String convertToDatabaseColumn(Status status) {
            if (status == null) {
                return null;
            }
            return status.name().toLowerCase();
        }
        
        @Override
        public Status convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.isEmpty()) {
                return null;
            }
            try {
                return Status.valueOf(dbData.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown enrollment status: " + dbData, e);
            }
        }
    }
}
