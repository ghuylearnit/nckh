-- =============================================
-- COMPLETE GRADES DATA FOR ALL STUDENTS
-- Dữ liệu điểm đầy đủ cho 19 sinh viên
-- =============================================

USE sgu_student_management;

-- =============================================
-- ENROLLMENTS & GRADES - STUDENT 2 (Nguyễn Văn An - CNTT)
-- =============================================
-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(2, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(2, (SELECT id FROM course WHERE code='MA101'), 1, 'completed'),
(2, (SELECT id FROM course WHERE code='CS101'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=2 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=2 AND course_id=(SELECT id FROM course WHERE code='MA101') AND term_id=1), 8.0, 8.5, 8.3, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=2 AND course_id=(SELECT id FROM course WHERE code='CS101') AND term_id=1), 9.0, 9.5, 9.3, 'A+', 4.0, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(2, (SELECT id FROM course WHERE code='GE102'), 2, 'completed'),
(2, (SELECT id FROM course WHERE code='MA102'), 2, 'completed'),
(2, (SELECT id FROM course WHERE code='CS102'), 2, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=2 AND course_id=(SELECT id FROM course WHERE code='GE102') AND term_id=2), 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=2 AND course_id=(SELECT id FROM course WHERE code='MA102') AND term_id=2), 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=2 AND course_id=(SELECT id FROM course WHERE code='CS102') AND term_id=2), 8.0, 8.5, 8.3, 'A', 3.7, TRUE);

-- =============================================
-- ENROLLMENTS & GRADES - STUDENT 3 (Trần Thị Bình - CNTT)
-- =============================================
-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(3, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(3, (SELECT id FROM course WHERE code='MA101'), 1, 'completed'),
(3, (SELECT id FROM course WHERE code='CS101'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=3 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 6.5, 7.0, 6.8, 'C+', 2.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=3 AND course_id=(SELECT id FROM course WHERE code='MA101') AND term_id=1), 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=3 AND course_id=(SELECT id FROM course WHERE code='CS101') AND term_id=1), 8.0, 8.5, 8.3, 'A', 3.7, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(3, (SELECT id FROM course WHERE code='GE102'), 2, 'completed'),
(3, (SELECT id FROM course WHERE code='MA102'), 2, 'completed'),
(3, (SELECT id FROM course WHERE code='CS102'), 2, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=3 AND course_id=(SELECT id FROM course WHERE code='GE102') AND term_id=2), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=3 AND course_id=(SELECT id FROM course WHERE code='MA102') AND term_id=2), 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=3 AND course_id=(SELECT id FROM course WHERE code='CS102') AND term_id=2), 8.5, 9.0, 8.8, 'A', 3.7, TRUE);

-- =============================================
-- ENROLLMENTS & GRADES - STUDENT 5 (Phạm Thị Dung - CNTT)
-- =============================================
-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(5, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(5, (SELECT id FROM course WHERE code='MA101'), 1, 'completed'),
(5, (SELECT id FROM course WHERE code='CS101'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=5 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 9.0, 9.5, 9.3, 'A+', 4.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=5 AND course_id=(SELECT id FROM course WHERE code='MA101') AND term_id=1), 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=5 AND course_id=(SELECT id FROM course WHERE code='CS101') AND term_id=1), 9.5, 10.0, 9.8, 'A+', 4.0, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(5, (SELECT id FROM course WHERE code='GE102'), 2, 'completed'),
(5, (SELECT id FROM course WHERE code='MA102'), 2, 'completed'),
(5, (SELECT id FROM course WHERE code='CS102'), 2, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=5 AND course_id=(SELECT id FROM course WHERE code='GE102') AND term_id=2), 9.0, 9.5, 9.3, 'A+', 4.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=5 AND course_id=(SELECT id FROM course WHERE code='MA102') AND term_id=2), 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=5 AND course_id=(SELECT id FROM course WHERE code='CS102') AND term_id=2), 9.0, 9.5, 9.3, 'A+', 4.0, TRUE);

-- =============================================
-- ENROLLMENTS & GRADES - STUDENT 6 (Hoàng Văn Em - CNTT - AT RISK)
-- =============================================
-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(6, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(6, (SELECT id FROM course WHERE code='MA101'), 1, 'completed'),
(6, (SELECT id FROM course WHERE code='CS101'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=6 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 4.5, 5.0, 4.8, 'D', 1.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=6 AND course_id=(SELECT id FROM course WHERE code='MA101') AND term_id=1), 3.5, 4.0, 3.8, 'F', 0.0, FALSE),
((SELECT id FROM enrollment WHERE user_id=6 AND course_id=(SELECT id FROM course WHERE code='CS101') AND term_id=1), 5.0, 5.5, 5.3, 'C', 2.0, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(6, (SELECT id FROM course WHERE code='GE102'), 2, 'completed'),
(6, (SELECT id FROM course WHERE code='MA102'), 2, 'completed'),
(6, (SELECT id FROM course WHERE code='CS102'), 2, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=6 AND course_id=(SELECT id FROM course WHERE code='GE102') AND term_id=2), 5.5, 6.0, 5.8, 'C', 2.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=6 AND course_id=(SELECT id FROM course WHERE code='MA102') AND term_id=2), 4.0, 4.5, 4.3, 'D', 1.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=6 AND course_id=(SELECT id FROM course WHERE code='CS102') AND term_id=2), 3.0, 3.5, 3.3, 'F', 0.0, FALSE);

-- Academic Warning for Student 6
INSERT INTO academic_warning (user_id, term_id, warning_level, gpa, reason, action_required, issued_date) VALUES
(6, 2, 1, 1.33, 'GPA dưới 2.0 - Cảnh báo học vụ lần 1', 'Sinh viên cần cải thiện kết quả học tập trong học kỳ tiếp theo', '2023-07-01');

-- =============================================
-- ENROLLMENTS & GRADES - STUDENT 7 (Võ Thị Phương - CNTT)
-- =============================================
-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(7, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(7, (SELECT id FROM course WHERE code='MA101'), 1, 'completed'),
(7, (SELECT id FROM course WHERE code='CS101'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=7 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=7 AND course_id=(SELECT id FROM course WHERE code='MA101') AND term_id=1), 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=7 AND course_id=(SELECT id FROM course WHERE code='CS101') AND term_id=1), 8.5, 9.0, 8.8, 'A', 3.7, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(7, (SELECT id FROM course WHERE code='GE102'), 2, 'completed'),
(7, (SELECT id FROM course WHERE code='MA102'), 2, 'completed'),
(7, (SELECT id FROM course WHERE code='CS102'), 2, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=7 AND course_id=(SELECT id FROM course WHERE code='GE102') AND term_id=2), 8.0, 8.5, 8.3, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=7 AND course_id=(SELECT id FROM course WHERE code='MA102') AND term_id=2), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=7 AND course_id=(SELECT id FROM course WHERE code='CS102') AND term_id=2), 8.5, 9.0, 8.8, 'A', 3.7, TRUE);

-- =============================================
-- ENROLLMENTS & GRADES - QTKD STUDENTS (9-14)
-- =============================================
-- Student 9 (Bùi Thị Hoa - QTKD)
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(9, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(9, (SELECT id FROM course WHERE code='MA101'), 1, 'completed'),
(9, (SELECT id FROM course WHERE code='BA101'), 1, 'completed'),
(9, (SELECT id FROM course WHERE code='BA102'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=9 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 8.0, 8.5, 8.3, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=9 AND course_id=(SELECT id FROM course WHERE code='MA101') AND term_id=1), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=9 AND course_id=(SELECT id FROM course WHERE code='BA101') AND term_id=1), 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=9 AND course_id=(SELECT id FROM course WHERE code='BA102') AND term_id=1), 9.0, 9.5, 9.3, 'A+', 4.0, TRUE);

-- Student 10 (Ngô Văn Inh - QTKD)
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(10, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(10, (SELECT id FROM course WHERE code='MA101'), 1, 'completed'),
(10, (SELECT id FROM course WHERE code='BA101'), 1, 'completed'),
(10, (SELECT id FROM course WHERE code='BA102'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=10 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=10 AND course_id=(SELECT id FROM course WHERE code='MA101') AND term_id=1), 6.5, 7.0, 6.8, 'C+', 2.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=10 AND course_id=(SELECT id FROM course WHERE code='BA101') AND term_id=1), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=10 AND course_id=(SELECT id FROM course WHERE code='BA102') AND term_id=1), 8.0, 8.5, 8.3, 'A', 3.7, TRUE);

-- Student 11 (Đinh Thị Kim - QTKD)
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(11, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(11, (SELECT id FROM course WHERE code='MA101'), 1, 'completed'),
(11, (SELECT id FROM course WHERE code='BA101'), 1, 'completed'),
(11, (SELECT id FROM course WHERE code='BA102'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=11 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 6.0, 6.5, 6.3, 'C+', 2.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=11 AND course_id=(SELECT id FROM course WHERE code='MA101') AND term_id=1), 5.5, 6.0, 5.8, 'C', 2.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=11 AND course_id=(SELECT id FROM course WHERE code='BA101') AND term_id=1), 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=11 AND course_id=(SELECT id FROM course WHERE code='BA102') AND term_id=1), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE);

-- =============================================
-- ENROLLMENTS & GRADES - SPA STUDENTS (15-20)
-- =============================================
-- Student 15 (Cao Thị Oanh - SPA)
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(15, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(15, (SELECT id FROM course WHERE code='EN101'), 1, 'completed'),
(15, (SELECT id FROM course WHERE code='EN102'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=15 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=15 AND course_id=(SELECT id FROM course WHERE code='EN101') AND term_id=1), 9.0, 9.5, 9.3, 'A+', 4.0, TRUE),
((SELECT id FROM enrollment WHERE user_id=15 AND course_id=(SELECT id FROM course WHERE code='EN102') AND term_id=1), 8.5, 9.0, 8.8, 'A', 3.7, TRUE);

-- Student 16 (Dương Văn Phúc - SPA)
INSERT INTO enrollment (user_id, course_id, term_id, status) VALUES
(16, (SELECT id FROM course WHERE code='GE101'), 1, 'completed'),
(16, (SELECT id FROM course WHERE code='EN101'), 1, 'completed'),
(16, (SELECT id FROM course WHERE code='EN102'), 1, 'completed');

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
((SELECT id FROM enrollment WHERE user_id=16 AND course_id=(SELECT id FROM course WHERE code='GE101') AND term_id=1), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE),
((SELECT id FROM enrollment WHERE user_id=16 AND course_id=(SELECT id FROM course WHERE code='EN101') AND term_id=1), 8.0, 8.5, 8.3, 'A', 3.7, TRUE),
((SELECT id FROM enrollment WHERE user_id=16 AND course_id=(SELECT id FROM course WHERE code='EN102') AND term_id=1), 7.5, 8.0, 7.8, 'B+', 3.5, TRUE);

-- =============================================
-- CERTIFICATES DATA
-- =============================================
-- Student 4 (already has certificates)
-- Student 5 (Top student)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, issue_date, expiry_date, verified) VALUES
(5, 'english', 'TOEIC 850', '2024-03-15', '2026-03-15', TRUE),
(5, 'it', 'MOS Excel Expert', '2024-05-20', NULL, TRUE);

-- Student 9 (QTKD top student)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, issue_date, expiry_date, verified) VALUES
(9, 'english', 'TOEIC 750', '2024-04-10', '2026-04-10', TRUE),
(9, 'it', 'MOS Word', '2024-06-15', NULL, TRUE);

-- Student 15 (SPA top student)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, issue_date, expiry_date, verified) VALUES
(15, 'english', 'IELTS 7.0', '2024-02-20', '2026-02-20', TRUE),
(15, 'it', 'IC3 Digital Literacy', '2024-05-10', NULL, TRUE);

-- =============================================
-- SUMMARY
-- =============================================
-- Total students with grades: 10 students (2, 3, 4, 5, 6, 7, 9, 10, 11, 15, 16)
-- Students at risk (GPA < 2.0): 1 student (6)
-- Top students (GPA >= 3.5): 5 students (4, 5, 7, 9, 15)
-- Students with certificates: 4 students (4, 5, 9, 15)
