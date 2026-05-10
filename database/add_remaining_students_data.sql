-- =============================================
-- Add Data for Remaining Students (5, 7, 8)
-- Skip Student 4 (already has data)
-- =============================================

USE sgu_student_management;

-- =============================================
-- Sinh viên 5: Phạm Thị Dung (3122410004) - user_id = 5
-- Sinh viên khá giỏi
-- =============================================

-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(5, (SELECT id FROM course WHERE code='GE101'), 1, 'completed', 1),
(5, (SELECT id FROM course WHERE code='MA101'), 1, 'completed', 1),
(5, (SELECT id FROM course WHERE code='CS101'), 1, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID()-1, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 8.5, 8.5, 8.5, 'A', 3.7, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(5, (SELECT id FROM course WHERE code='GE102'), 2, 'completed', 1),
(5, (SELECT id FROM course WHERE code='MA102'), 2, 'completed', 1),
(5, (SELECT id FROM course WHERE code='CS102'), 2, 'completed', 1),
(5, (SELECT id FROM course WHERE code='MA103'), 2, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 8.0, 8.0, 8.0, 'B+', 3.5, TRUE),
(LAST_INSERT_ID()-2, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- HK1 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(5, (SELECT id FROM course WHERE code='GE103'), 3, 'completed', 1),
(5, (SELECT id FROM course WHERE code='CS103'), 3, 'completed', 1),
(5, (SELECT id FROM course WHERE code='CS104'), 3, 'completed', 1),
(5, (SELECT id FROM course WHERE code='MA104'), 3, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 8.5, 8.5, 8.5, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-2, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID()-1, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- HK2 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(5, (SELECT id FROM course WHERE code='GE104'), 4, 'completed', 1),
(5, (SELECT id FROM course WHERE code='CS105'), 4, 'completed', 1),
(5, (SELECT id FROM course WHERE code='CS106'), 4, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 8.5, 8.5, 8.5, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID(), 8.5, 9.0, 8.8, 'A', 3.7, TRUE);

-- HK1 2024
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(5, (SELECT id FROM course WHERE code='GE105'), 5, 'completed', 1),
(5, (SELECT id FROM course WHERE code='CS107'), 5, 'completed', 1),
(5, (SELECT id FROM course WHERE code='CS109'), 5, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID(), 8.5, 8.5, 8.5, 'A', 3.7, TRUE);

-- Chứng chỉ
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, issue_date, verified) VALUES
(5, 'english', 'TOEIC', '600', '2024-02-15', TRUE),
(5, 'it', 'IC3', 'Pass', '2023-12-20', TRUE),
(5, 'physical_education', 'GDTC', 'Pass', '2024-01-20', TRUE),
(5, 'defense_education', 'GDQP', 'Pass', '2023-06-15', TRUE);

-- =============================================
-- Sinh viên 7: Võ Thị Phương (3122410006) - user_id = 7
-- Sinh viên khá
-- =============================================

-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(7, (SELECT id FROM course WHERE code='GE101'), 1, 'completed', 1),
(7, (SELECT id FROM course WHERE code='MA101'), 1, 'completed', 1),
(7, (SELECT id FROM course WHERE code='CS101'), 1, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(7, (SELECT id FROM course WHERE code='GE102'), 2, 'completed', 1),
(7, (SELECT id FROM course WHERE code='MA102'), 2, 'completed', 1),
(7, (SELECT id FROM course WHERE code='CS102'), 2, 'completed', 1),
(7, (SELECT id FROM course WHERE code='MA103'), 2, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 7.5, 7.5, 7.5, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-2, 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 7.0, 7.5, 7.3, 'B', 3.0, TRUE);

-- HK1 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(7, (SELECT id FROM course WHERE code='GE103'), 3, 'completed', 1),
(7, (SELECT id FROM course WHERE code='CS103'), 3, 'completed', 1),
(7, (SELECT id FROM course WHERE code='CS104'), 3, 'completed', 1),
(7, (SELECT id FROM course WHERE code='MA104'), 3, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 8.0, 8.0, 8.0, 'B+', 3.5, TRUE),
(LAST_INSERT_ID()-2, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 8.0, 8.0, 8.0, 'B+', 3.5, TRUE),
(LAST_INSERT_ID(), 7.5, 7.5, 7.5, 'B', 3.0, TRUE);

-- HK2 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(7, (SELECT id FROM course WHERE code='GE104'), 4, 'completed', 1),
(7, (SELECT id FROM course WHERE code='CS105'), 4, 'completed', 1),
(7, (SELECT id FROM course WHERE code='CS106'), 4, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 7.5, 7.5, 7.5, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- HK1 2024
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(7, (SELECT id FROM course WHERE code='GE105'), 5, 'completed', 1),
(7, (SELECT id FROM course WHERE code='CS107'), 5, 'completed', 1),
(7, (SELECT id FROM course WHERE code='CS109'), 5, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 7.5, 7.5, 7.5, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- Chứng chỉ
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, issue_date, verified) VALUES
(7, 'english', 'TOEIC', '480', '2024-03-10', TRUE),
(7, 'it', 'MOS', 'Pass', '2023-10-25', TRUE),
(7, 'physical_education', 'GDTC', 'Pass', '2024-01-20', TRUE),
(7, 'defense_education', 'GDQP', 'Pass', '2023-06-15', TRUE);

-- =============================================
-- Sinh viên 8: Đặng Văn Giang (3122410007) - user_id = 8
-- Sinh viên giỏi
-- =============================================

-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(8, (SELECT id FROM course WHERE code='GE101'), 1, 'completed', 1),
(8, (SELECT id FROM course WHERE code='MA101'), 1, 'completed', 1),
(8, (SELECT id FROM course WHERE code='CS101'), 1, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 8.5, 8.5, 8.5, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 9.0, 9.0, 9.0, 'A', 3.7, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(8, (SELECT id FROM course WHERE code='GE102'), 2, 'completed', 1),
(8, (SELECT id FROM course WHERE code='MA102'), 2, 'completed', 1),
(8, (SELECT id FROM course WHERE code='CS102'), 2, 'completed', 1),
(8, (SELECT id FROM course WHERE code='MA103'), 2, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 8.5, 8.5, 8.5, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-2, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 9.0, 9.0, 9.0, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 8.5, 8.5, 8.5, 'A', 3.7, TRUE);

-- HK1 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(8, (SELECT id FROM course WHERE code='GE103'), 3, 'completed', 1),
(8, (SELECT id FROM course WHERE code='CS103'), 3, 'completed', 1),
(8, (SELECT id FROM course WHERE code='CS104'), 3, 'completed', 1),
(8, (SELECT id FROM course WHERE code='MA104'), 3, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 9.0, 9.0, 9.0, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-2, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 9.0, 9.5, 9.3, 'A+', 4.0, TRUE),
(LAST_INSERT_ID(), 8.5, 8.5, 8.5, 'A', 3.7, TRUE);

-- HK2 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(8, (SELECT id FROM course WHERE code='GE104'), 4, 'completed', 1),
(8, (SELECT id FROM course WHERE code='CS105'), 4, 'completed', 1),
(8, (SELECT id FROM course WHERE code='CS106'), 4, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 8.5, 8.5, 8.5, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 9.0, 9.0, 9.0, 'A', 3.7, TRUE);

-- HK1 2024
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(8, (SELECT id FROM course WHERE code='GE105'), 5, 'completed', 1),
(8, (SELECT id FROM course WHERE code='CS107'), 5, 'completed', 1),
(8, (SELECT id FROM course WHERE code='CS109'), 5, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 9.0, 9.0, 9.0, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 9.0, 9.0, 9.0, 'A', 3.7, TRUE);

-- Chứng chỉ
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, issue_date, verified) VALUES
(8, 'english', 'TOEIC', '700', '2024-01-25', TRUE),
(8, 'it', 'IC3', 'Pass', '2023-11-30', TRUE),
(8, 'physical_education', 'GDTC', 'Pass', '2024-01-20', TRUE),
(8, 'defense_education', 'GDQP', 'Pass', '2023-06-15', TRUE);

-- =============================================
-- VERIFICATION
-- =============================================
SELECT 
    u.id,
    u.name,
    COUNT(e.id) as enrollments,
    COUNT(g.id) as grades
FROM user u
LEFT JOIN enrollment e ON u.id = e.user_id
LEFT JOIN grade g ON e.id = g.enrollment_id
WHERE u.id IN (5, 7, 8)
GROUP BY u.id, u.name;
