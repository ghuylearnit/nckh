-- =============================================
-- ADD GRADES FOR MORE STUDENTS - SIMPLE VERSION
-- Thêm dữ liệu grades cho nhiều sinh viên
-- =============================================

USE sgu_student_management;

-- =============================================
-- STUDENT 2: Nguyễn Văn An (CNTT - GPA tốt)
-- =============================================
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(2, 1, 1, 'completed'),  -- GE101
(2, 6, 1, 'completed'),  -- MA101
(2, 10, 1, 'completed'); -- CS101

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 7.5, 8.0, 7.8, 'B+', 3.5, TRUE FROM enrollment WHERE user_id=2 AND course_id=1 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 8.0, 8.5, 8.3, 'A', 3.7, TRUE FROM enrollment WHERE user_id=2 AND course_id=6 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 9.0, 9.5, 9.3, 'A+', 4.0, TRUE FROM enrollment WHERE user_id=2 AND course_id=10 AND term_id=1;

-- =============================================
-- STUDENT 3: Trần Thị Bình (CNTT - GPA khá)
-- =============================================
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(3, 1, 1, 'completed'),  -- GE101
(3, 6, 1, 'completed'),  -- MA101
(3, 10, 1, 'completed'); -- CS101

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 6.5, 7.0, 6.8, 'C+', 2.5, TRUE FROM enrollment WHERE user_id=3 AND course_id=1 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 7.0, 7.5, 7.3, 'B', 3.0, TRUE FROM enrollment WHERE user_id=3 AND course_id=6 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 8.0, 8.5, 8.3, 'A', 3.7, TRUE FROM enrollment WHERE user_id=3 AND course_id=10 AND term_id=1;

-- =============================================
-- STUDENT 5: Phạm Thị Dung (CNTT - GPA xuất sắc)
-- =============================================
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(5, 1, 1, 'completed'),  -- GE101
(5, 6, 1, 'completed'),  -- MA101
(5, 10, 1, 'completed'); -- CS101

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 9.0, 9.5, 9.3, 'A+', 4.0, TRUE FROM enrollment WHERE user_id=5 AND course_id=1 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 8.5, 9.0, 8.8, 'A', 3.7, TRUE FROM enrollment WHERE user_id=5 AND course_id=6 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 9.5, 10.0, 9.8, 'A+', 4.0, TRUE FROM enrollment WHERE user_id=5 AND course_id=10 AND term_id=1;

-- =============================================
-- STUDENT 6: Hoàng Văn Em (CNTT - AT RISK - GPA thấp)
-- =============================================
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(6, 1, 1, 'completed'),  -- GE101
(6, 6, 1, 'completed'),  -- MA101
(6, 10, 1, 'completed'); -- CS101

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 4.5, 5.0, 4.8, 'D', 1.0, TRUE FROM enrollment WHERE user_id=6 AND course_id=1 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 3.5, 4.0, 3.8, 'F', 0.0, FALSE FROM enrollment WHERE user_id=6 AND course_id=6 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 5.0, 5.5, 5.3, 'C', 2.0, TRUE FROM enrollment WHERE user_id=6 AND course_id=10 AND term_id=1;

-- Academic Warning
INSERT INTO academic_warning (user_id, term_id, warning_level, reason, action_required, issued_date) VALUES
(6, 1, 1, 'GPA dưới 2.0 - Cảnh báo học vụ lần 1', 'Sinh viên cần cải thiện kết quả học tập', '2023-01-20');

-- =============================================
-- STUDENT 9: Bùi Thị Hoa (QTKD - GPA xuất sắc)
-- =============================================
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(9, 1, 1, 'completed'),  -- GE101
(9, 6, 1, 'completed'),  -- MA101
(9, 22, 1, 'completed'), -- BA101
(9, 23, 1, 'completed'); -- BA102

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 8.0, 8.5, 8.3, 'A', 3.7, TRUE FROM enrollment WHERE user_id=9 AND course_id=1 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 7.5, 8.0, 7.8, 'B+', 3.5, TRUE FROM enrollment WHERE user_id=9 AND course_id=6 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 8.5, 9.0, 8.8, 'A', 3.7, TRUE FROM enrollment WHERE user_id=9 AND course_id=22 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 9.0, 9.5, 9.3, 'A+', 4.0, TRUE FROM enrollment WHERE user_id=9 AND course_id=23 AND term_id=1;

-- =============================================
-- STUDENT 15: Cao Thị Oanh (SPA - GPA xuất sắc)
-- =============================================
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(15, 1, 1, 'completed'),  -- GE101
(15, 35, 1, 'completed'), -- EN101
(15, 36, 1, 'completed'); -- EN102

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 8.5, 9.0, 8.8, 'A', 3.7, TRUE FROM enrollment WHERE user_id=15 AND course_id=1 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 9.0, 9.5, 9.3, 'A+', 4.0, TRUE FROM enrollment WHERE user_id=15 AND course_id=35 AND term_id=1;
INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) 
SELECT id, 8.5, 9.0, 8.8, 'A', 3.7, TRUE FROM enrollment WHERE user_id=15 AND course_id=36 AND term_id=1;

-- =============================================
-- CERTIFICATES
-- =============================================
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, issue_date, expiry_date, verified) VALUES
(5, 'english', 'TOEIC 850', '2024-03-15', '2026-03-15', TRUE),
(5, 'it', 'MOS Excel Expert', '2024-05-20', NULL, TRUE),
(9, 'english', 'TOEIC 750', '2024-04-10', '2026-04-10', TRUE),
(9, 'it', 'MOS Word', '2024-06-15', NULL, TRUE),
(15, 'english', 'IELTS 7.0', '2024-02-20', '2026-02-20', TRUE),
(15, 'it', 'IC3 Digital Literacy', '2024-05-10', NULL, TRUE);

-- =============================================
-- SUMMARY
-- =============================================
-- Students with grades: 2, 3, 4, 5, 6, 9, 15 (7 students)
-- At-risk students: 6 (GPA < 2.0)
-- Top students: 4, 5, 9, 15 (GPA >= 3.5)
-- Students with certificates: 4, 5, 9, 15
