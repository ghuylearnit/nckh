-- =============================================
-- BỔ SUNG GRADES CHO SINH VIÊN THIẾU ĐIỂM (SIMPLIFIED)
-- Sử dụng biến để tránh lỗi subquery
-- =============================================

USE sgu_student_management;

-- =============================================
-- STUDENT ID 6: Võ Thị Phương (GPA ~1.8)
-- =============================================
SET @user_id = 6;

-- HK1
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 1, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 5.0, 5.5, 5.3, 'D', 1.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 2, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 4.5, 5.0, 4.8, 'F', 0.0, FALSE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 3, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.0, 6.5, 6.3, 'C', 2.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 4, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 5.5, 6.0, 5.8, 'D+', 1.5, TRUE);

-- HK2
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 5, 2, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.5, 7.0, 6.8, 'C+', 2.5, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 6, 2, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.0, 6.5, 6.3, 'C', 2.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 7, 2, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 5.5, 6.0, 5.8, 'D+', 1.5, TRUE);

-- =============================================
-- STUDENT ID 8: Bùi Thị Hoa (GPA ~3.5)
-- =============================================
SET @user_id = 8;

-- HK1
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 1, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.5, 9.0, 8.8, 'A', 3.7, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 2, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 3, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 9.0, 9.0, 9.0, 'A', 3.7, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 4, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- HK2
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 5, 2, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.5, 8.5, 8.5, 'A', 3.7, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 6, 2, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 7, 2, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.5, 9.0, 8.8, 'A', 3.7, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 8, 2, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.5, 8.0, 7.8, 'B', 3.0, TRUE);

-- HK3
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 9, 3, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.5, 9.0, 8.8, 'A', 3.7, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 10, 3, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 11, 3, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.5, 8.5, 8.5, 'A', 3.7, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 12, 3, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.5, 8.0, 7.8, 'B', 3.0, TRUE);

-- =============================================
-- STUDENT ID 12, 13, 14: Thêm grades đơn giản
-- =============================================

-- Student 12: GPA ~2.8
SET @user_id = 12;
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 1, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.0, 7.5, 7.3, 'B', 3.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 2, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.5, 7.5, 7.5, 'B', 3.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 3, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.5, 7.0, 6.8, 'C+', 2.5, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 4, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.0, 7.0, 7.0, 'B', 3.0, TRUE);

-- Student 13: GPA ~3.3
SET @user_id = 13;
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 1, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 2, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.5, 8.5, 8.5, 'A', 3.7, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 3, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.5, 8.0, 7.8, 'B', 3.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 4, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- Student 14: GPA ~2.3
SET @user_id = 14;
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 1, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.5, 7.0, 6.8, 'C+', 2.5, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 2, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.0, 6.5, 6.3, 'C', 2.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 3, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.0, 7.0, 7.0, 'B', 3.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 4, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.0, 6.5, 6.3, 'C', 2.0, TRUE);

-- Students 17, 18, 19: GPA ~2.7
SET @user_id = 17;
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 1, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.0, 7.5, 7.3, 'B', 3.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 2, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.5, 7.0, 6.8, 'C+', 2.5, TRUE);

SET @user_id = 18;
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 1, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.5, 7.5, 7.5, 'B', 3.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 2, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.0, 7.5, 7.3, 'B', 3.0, TRUE);

SET @user_id = 19;
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 1, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 6.5, 7.0, 6.8, 'C+', 2.5, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES (@user_id, 2, 1, 'COMPLETED');
SET @enroll_id = LAST_INSERT_ID();
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
VALUES (@enroll_id, 7.0, 7.5, 7.3, 'B', 3.0, TRUE);

COMMIT;

-- Kiểm tra kết quả
SELECT 'Tổng số grades:', COUNT(*) FROM grade;
