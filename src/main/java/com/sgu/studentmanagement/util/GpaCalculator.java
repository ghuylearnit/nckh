package com.sgu.studentmanagement.util;

import com.sgu.studentmanagement.entity.Grade;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * GpaCalculator - Utility class for GPA calculations
 */
@Slf4j
public class GpaCalculator {

    /**
     * Calculate GPA from list of grades
     * Formula: GPA = Sum(GPA Point * Credits) / Sum(Credits)
     */
    public static double calculateGPA(List<Grade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        BigDecimal totalPoints = BigDecimal.ZERO;
        int totalCredits = 0;

        for (Grade grade : grades) {
            if (grade.getGpaPoint() != null && grade.getEnrollment() != null 
                    && grade.getEnrollment().getCourse() != null) {
                int credits = grade.getEnrollment().getCourse().getCredits();
                totalPoints = totalPoints.add(grade.getGpaPoint().multiply(BigDecimal.valueOf(credits)));
                totalCredits += credits;
            }
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        BigDecimal gpa = totalPoints.divide(BigDecimal.valueOf(totalCredits), 2, RoundingMode.HALF_UP);
        return gpa.doubleValue();
    }

    /**
     * Get grade letter from score
     * Based on Vietnamese grading system
     */
    public static String getGradeLetter(double score) {
        if (score >= 9.0) return "A+";
        if (score >= 8.5) return "A";
        if (score >= 8.0) return "B+";
        if (score >= 7.0) return "B";
        if (score >= 6.5) return "C+";
        if (score >= 5.5) return "C";
        if (score >= 5.0) return "D+";
        if (score >= 4.0) return "D";
        return "F";
    }

    /**
     * Get GPA point from score
     * Based on 4.0 scale
     */
    public static double getGpaPoint(double score) {
        if (score >= 9.0) return 4.0;
        if (score >= 8.5) return 3.7;
        if (score >= 8.0) return 3.5;
        if (score >= 7.0) return 3.0;
        if (score >= 6.5) return 2.5;
        if (score >= 5.5) return 2.0;
        if (score >= 5.0) return 1.5;
        if (score >= 4.0) return 1.0;
        return 0.0;
    }

    /**
     * Check if grade is passing
     */
    public static boolean isPassed(double score) {
        return score >= 4.0;
    }

    /**
     * Calculate total credits from grades
     */
    public static int calculateTotalCredits(List<Grade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0;
        }

        return grades.stream()
                .filter(grade -> grade.getPassed() != null && grade.getPassed())
                .filter(grade -> grade.getEnrollment() != null && grade.getEnrollment().getCourse() != null)
                .mapToInt(grade -> grade.getEnrollment().getCourse().getCredits())
                .sum();
    }

    /**
     * Get academic standing based on GPA
     */
    public static String getAcademicStanding(double gpa) {
        if (gpa >= 3.6) return "Xuất sắc";
        if (gpa >= 3.2) return "Giỏi";
        if (gpa >= 2.5) return "Khá";
        if (gpa >= 2.0) return "Trung bình";
        return "Yếu";
    }

    /**
     * Check if student is at risk (GPA < 2.0)
     */
    public static boolean isAtRisk(double gpa) {
        return gpa < 2.0;
    }
}
