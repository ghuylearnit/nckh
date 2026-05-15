package com.sgu.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * CoursePrerequisite Entity - Môn tiên quyết
 */
@Entity
@Table(name = "course_prerequisite", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"course_id", "prerequisite_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePrerequisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // Môn hiện tại

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prerequisite_id", nullable = false)
    private Course prerequisite; // Môn tiên quyết

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
