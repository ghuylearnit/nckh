package com.sgu.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * StudentProgram Entity - Sinh viên thuộc chương trình đào tạo
 */
@Entity
@Table(name = "student_program", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "program_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @Column(name = "expected_graduation_year")
    private Integer expectedGraduationYear;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
