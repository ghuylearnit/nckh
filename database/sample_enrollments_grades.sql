-- =============================================
-- Sample Enrollments and Grades Data
-- =============================================

USE sgu_student_management;

-- =============================================
-- ENROLLMENTS & GRADES - Sinh viên CNTT
-- =============================================

-- Sinh viên 1: Nguyễn Văn An (3122410001) - Sinh viên xuất sắc
-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(2, (SELECT id FROM course WHERE code='GE101'), 1, 'completed', 1),
(2, (SELECT id FROM course WHERE code='MA101'), 1, 'completed', 1),
(2, (SELECT id FROM course WHERE code='CS101'), 1, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 9.0, 9.5, 9.3, 'A+', 4.0, TRUE),
(LAST_INSERT_ID()-1, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 9.0, 9.0, 9.0, 'A', 3.7, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(2, (SELECT id FROM course WHERE code='GE102'), 2, 'completed', 1),
(2, (SELECT id FROM course WHERE code='MA102'), 2, 'completed', 1),
(2, (SELECT id FROM course WHERE code='CS102'), 2, 'completed', 1),
(2, (SELECT id FROM course WHERE code='MA103'), 2, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID()-2, 9.0, 8.5, 8.7, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- HK1 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(2, (SELECT id FROM course WHERE code='GE103'), 3, 'completed', 1),
(2, (SELECT id FROM course WHERE code='CS103'), 3, 'completed', 1),
(2, (SELECT id FROM course WHERE code='CS104'), 3, 'completed', 1),
(2, (SELECT id FROM course WHERE code='MA104'), 3, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 9.0, 9.0, 9.0, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-2, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 9.0, 9.5, 9.3, 'A+', 4.0, TRUE),
(LAST_INSERT_ID(), 8.0, 8.5, 8.3, 'B+', 3.5, TRUE);

-- HK2 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(2, (SELECT id FROM course WHERE code='GE104'), 4, 'completed', 1),
(2, (SELECT id FROM course WHERE code='CS105'), 4, 'completed', 1),
(2, (SELECT id FROM course WHERE code='CS106'), 4, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID(), 9.0, 9.0, 9.0, 'A', 3.7, TRUE);

-- HK1 2024
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(2, (SELECT id FROM course WHERE code='GE105'), 5, 'completed', 1),
(2, (SELECT id FROM course WHERE code='CS107'), 5, 'completed', 1),
(2, (SELECT id FROM course WHERE code='CS109'), 5, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 9.0, 9.0, 9.0, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 8.0, 8.5, 8.3, 'B+', 3.5, TRUE);

-- HK2 2024 (đang học)
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(2, (SELECT id FROM course WHERE code='CS108'), 6, 'enrolled', 1),
(2, (SELECT id FROM course WHERE code='CS110'), 6, 'enrolled', 1);

-- =============================================
-- Sinh viên 2: Trần Thị Bình (3122410002) - Sinh viên khá
-- =============================================
-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(3, (SELECT id FROM course WHERE code='GE101'), 1, 'completed', 1),
(3, (SELECT id FROM course WHERE code='MA101'), 1, 'completed', 1),
(3, (SELECT id FROM course WHERE code='CS101'), 1, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(3, (SELECT id FROM course WHERE code='GE102'), 2, 'completed', 1),
(3, (SELECT id FROM course WHERE code='MA102'), 2, 'completed', 1),
(3, (SELECT id FROM course WHERE code='CS102'), 2, 'completed', 1),
(3, (SELECT id FROM course WHERE code='MA103'), 2, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-2, 7.5, 7.5, 7.5, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 7.0, 8.0, 7.6, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 7.5, 7.0, 7.2, 'B', 3.0, TRUE);

-- HK1 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(3, (SELECT id FROM course WHERE code='GE103'), 3, 'completed', 1),
(3, (SELECT id FROM course WHERE code='CS103'), 3, 'completed', 1),
(3, (SELECT id FROM course WHERE code='CS104'), 3, 'completed', 1),
(3, (SELECT id FROM course WHERE code='MA104'), 3, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 8.0, 8.0, 8.0, 'B+', 3.5, TRUE),
(LAST_INSERT_ID()-2, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID(), 7.0, 7.5, 7.3, 'B', 3.0, TRUE);

-- HK2 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(3, (SELECT id FROM course WHERE code='GE104'), 4, 'completed', 1),
(3, (SELECT id FROM course WHERE code='CS105'), 4, 'completed', 1),
(3, (SELECT id FROM course WHERE code='CS106'), 4, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 7.5, 7.5, 7.5, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

-- HK1 2024
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(3, (SELECT id FROM course WHERE code='GE105'), 5, 'completed', 1),
(3, (SELECT id FROM course WHERE code='CS107'), 5, 'completed', 1),
(3, (SELECT id FROM course WHERE code='CS109'), 5, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 7.5, 8.0, 7.8, 'B', 3.0, TRUE),
(LAST_INSERT_ID()-1, 7.0, 7.5, 7.3, 'B', 3.0, TRUE),
(LAST_INSERT_ID(), 7.5, 7.5, 7.5, 'B', 3.0, TRUE);

-- HK2 2024 (đang học)
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(3, (SELECT id FROM course WHERE code='CS108'), 6, 'enrolled', 1),
(3, (SELECT id FROM course WHERE code='CS110'), 6, 'enrolled', 1);

-- =============================================
-- Sinh viên 5: Hoàng Văn Em (3122410005) - Sinh viên yếu, bị cảnh báo
-- =============================================
-- HK1 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(6, (SELECT id FROM course WHERE code='GE101'), 1, 'completed', 1),
(6, (SELECT id FROM course WHERE code='MA101'), 1, 'completed', 1),
(6, (SELECT id FROM course WHERE code='CS101'), 1, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 5.0, 5.5, 5.3, 'D', 1.0, TRUE),
(LAST_INSERT_ID()-1, 4.5, 5.0, 4.8, 'D', 1.0, TRUE),
(LAST_INSERT_ID(), 5.5, 6.0, 5.8, 'D+', 1.5, TRUE);

-- HK2 2022
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(6, (SELECT id FROM course WHERE code='GE102'), 2, 'completed', 1),
(6, (SELECT id FROM course WHERE code='MA102'), 2, 'completed', 1),
(6, (SELECT id FROM course WHERE code='CS102'), 2, 'failed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 5.0, 5.5, 5.3, 'D', 1.0, TRUE),
(LAST_INSERT_ID()-1, 4.0, 4.5, 4.3, 'F', 0.0, FALSE),
(LAST_INSERT_ID(), 3.5, 4.0, 3.8, 'F', 0.0, FALSE);

-- HK1 2023 - Học lại CS102
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(6, (SELECT id FROM course WHERE code='CS102'), 3, 'completed', 2),
(6, (SELECT id FROM course WHERE code='MA103'), 3, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-1, 5.5, 6.0, 5.8, 'D+', 1.5, TRUE),
(LAST_INSERT_ID(), 5.0, 5.5, 5.3, 'D', 1.0, TRUE);

-- HK2 2023
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(6, (SELECT id FROM course WHERE code='GE103'), 4, 'completed', 1),
(6, (SELECT id FROM course WHERE code='CS103'), 4, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-1, 5.5, 6.0, 5.8, 'D+', 1.5, TRUE),
(LAST_INSERT_ID(), 5.0, 5.5, 5.3, 'D', 1.0, TRUE);

-- HK1 2024
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(6, (SELECT id FROM course WHERE code='CS104'), 5, 'completed', 1),
(6, (SELECT id FROM course WHERE code='MA104'), 5, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-1, 5.5, 6.0, 5.8, 'D+', 1.5, TRUE),
(LAST_INSERT_ID(), 5.0, 5.0, 5.0, 'D', 1.0, TRUE);

-- HK2 2024 (đang học)
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(6, (SELECT id FROM course WHERE code='GE104'), 6, 'enrolled', 1),
(6, (SELECT id FROM course WHERE code='CS105'), 6, 'enrolled', 1);

-- =============================================
-- STUDENT CERTIFICATES
-- =============================================
-- Sinh viên 1: Đã có đủ chứng chỉ
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, issue_date, verified) VALUES
(2, 'english', 'TOEIC', '650', '2024-03-15', TRUE),
(2, 'it', 'MOS Excel', 'Pass', '2023-12-10', TRUE),
(2, 'physical_education', 'GDTC', 'Pass', '2024-01-20', TRUE),
(2, 'defense_education', 'GDQP', 'Pass', '2023-06-15', TRUE);

-- Sinh viên 2: Thiếu chứng chỉ tiếng Anh
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, issue_date, verified) VALUES
(3, 'it', 'IC3', 'Pass', '2023-11-20', TRUE),
(3, 'physical_education', 'GDTC', 'Pass', '2024-01-20', TRUE),
(3, 'defense_education', 'GDQP', 'Pass', '2023-06-15', TRUE);

-- Sinh viên 5: Chưa có chứng chỉ nào
-- (Không insert gì)

-- =============================================
-- ACADEMIC WARNINGS
-- =============================================
-- Sinh viên 5: Cảnh báo học vụ mức 2
INSERT INTO academic_warning (user_id, term_id, warning_level, reason, status, notes) VALUES
(6, 2, 1, 'GPA học kỳ 2 năm 2022 dưới 1.0 (GPA: 0.67)', 'active', 'Cảnh báo lần 1'),
(6, 3, 2, 'GPA tích lũy dưới 1.5 sau học kỳ 1 năm 2023', 'active', 'Cảnh báo lần 2 - Nguy cơ buộc thôi học');

-- =============================================
-- SAMPLE ENROLLMENTS FOR OTHER STUDENTS (simplified)
-- =============================================

-- Sinh viên QTKD: Bùi Thị Hoa (user_id=9) - Sinh viên tốt
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(9, (SELECT id FROM course WHERE code='GE101'), 1, 'completed', 1),
(9, (SELECT id FROM course WHERE code='MA101'), 1, 'completed', 1),
(9, (SELECT id FROM course WHERE code='BA101'), 1, 'completed', 1),
(9, (SELECT id FROM course WHERE code='BA102'), 1, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-3, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-2, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID()-1, 8.5, 8.5, 8.5, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 9.0, 9.0, 9.0, 'A', 3.7, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(9, (SELECT id FROM course WHERE code='GE102'), 2, 'completed', 1),
(9, (SELECT id FROM course WHERE code='BA103'), 2, 'completed', 1),
(9, (SELECT id FROM course WHERE code='BA104'), 2, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 8.0, 8.5, 8.3, 'B+', 3.5, TRUE),
(LAST_INSERT_ID()-1, 8.5, 9.0, 8.8, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 8.0, 8.0, 8.0, 'B+', 3.5, TRUE);

INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, issue_date, verified) VALUES
(9, 'english', 'TOEIC', '550', '2024-02-10', TRUE),
(9, 'it', 'MOS Word', 'Pass', '2023-10-15', TRUE),
(9, 'physical_education', 'GDTC', 'Pass', '2024-01-20', TRUE),
(9, 'defense_education', 'GDQP', 'Pass', '2023-06-15', TRUE);

-- Sinh viên SPA: Cao Thị Oanh (user_id=15) - Sinh viên giỏi
INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(15, (SELECT id FROM course WHERE code='GE101'), 1, 'completed', 1),
(15, (SELECT id FROM course WHERE code='EN101'), 1, 'completed', 1),
(15, (SELECT id FROM course WHERE code='EN102'), 1, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 9.0, 9.5, 9.3, 'A+', 4.0, TRUE),
(LAST_INSERT_ID()-1, 9.0, 9.0, 9.0, 'A', 3.7, TRUE),
(LAST_INSERT_ID(), 9.5, 9.5, 9.5, 'A+', 4.0, TRUE);

INSERT INTO enrollment (user_id, course_id, term_id, status, attempt_number) VALUES
(15, (SELECT id FROM course WHERE code='GE102'), 2, 'completed', 1),
(15, (SELECT id FROM course WHERE code='EN103'), 2, 'completed', 1),
(15, (SELECT id FROM course WHERE code='EN104'), 2, 'completed', 1);

INSERT INTO grade (enrollment_id, midterm_score, final_score, total_score, grade_letter, gpa_point, passed) VALUES
(LAST_INSERT_ID()-2, 9.0, 9.0, 9.0, 'A', 3.7, TRUE),
(LAST_INSERT_ID()-1, 9.5, 9.5, 9.5, 'A+', 4.0, TRUE),
(LAST_INSERT_ID(), 9.0, 9.5, 9.3, 'A+', 4.0, TRUE);

INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, issue_date, verified) VALUES
(15, 'english', 'IELTS', '7.5', '2024-01-15', TRUE),
(15, 'it', 'IC3', 'Pass', '2023-09-20', TRUE),
(15, 'physical_education', 'GDTC', 'Pass', '2024-01-20', TRUE),
(15, 'defense_education', 'GDQP', 'Pass', '2023-06-15', TRUE);

-- =============================================
-- SAMPLE CHAT HISTORY
-- =============================================
INSERT INTO chat_history (user_id, message, response, created_at) VALUES
(2, 'Tôi cần bao nhiêu tín chỉ nữa để tốt nghiệp?', 'Bạn đã tích lũy được 90 tín chỉ. Bạn cần thêm 30 tín chỉ nữa để đủ điều kiện tốt nghiệp.', '2025-05-01 10:30:00'),
(2, 'Tôi có thể đăng ký môn Học máy không?', 'Có, bạn đã hoàn thành môn tiên quyết Trí tuệ nhân tạo. Bạn có thể đăng ký môn Học máy trong học kỳ tới.', '2025-05-02 14:15:00'),
(3, 'GPA của tôi hiện tại là bao nhiêu?', 'GPA tích lũy của bạn hiện tại là 3.05. Bạn đang ở mức học lực Khá.', '2025-05-03 09:20:00'),
(6, 'Tôi có bị cảnh báo học vụ không?', 'Có, bạn đang có 2 cảnh báo học vụ. GPA tích lũy của bạn là 1.18, thấp hơn mức yêu cầu 2.0. Bạn cần cải thiện kết quả học tập trong học kỳ tới.', '2025-05-04 16:45:00');
