package com.sgu.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * ProgramCourse Entity - Môn học thuộc chương trình đào tạo
 */
@Entity
@Table(name = "program_course", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"program_id", "course_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type", nullable = false)
    private CourseType courseType = CourseType.MANDATORY;

    @Column(name = "semester_recommended")
    private Integer semesterRecommended;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * CourseType Enum
     */
    public enum CourseType {
        MANDATORY,  // Bắt buộc
        ELECTIVE,   // Tự chọn
        GENERAL     // Đại cương
    }
}
