-- =============================================
-- SGU Student Management System - Sample Data
-- =============================================

USE sgu_student_management;

-- =============================================
-- 1. INSERT DEPARTMENTS (3 ngành)
-- =============================================
INSERT INTO department (name, code, description) VALUES
('Công nghệ thông tin', 'CNTT', 'Khoa Công nghệ thông tin'),
('Quản trị kinh doanh', 'QTKD', 'Khoa Quản trị kinh doanh'),
('Sư phạm Anh', 'SPA', 'Khoa Sư phạm Anh');

-- =============================================
-- 2. INSERT CLASSES
-- =============================================
INSERT INTO class (name, department_id, year) VALUES
-- CNTT
('DCT122C1', 1, 'K22'),
('DCT122C2', 1, 'K22'),
-- QTKD
('DBA122C1', 2, 'K22'),
('DBA122C2', 2, 'K22'),
-- SPA
('DEA122C1', 3, 'K22'),
('DEA122C2', 3, 'K22');

-- =============================================
-- 3. INSERT ADMIN USER
-- =============================================
-- Password: admin123 (hashed with BCrypt)
INSERT INTO user (name, email, password, role, status) VALUES
('Admin System', 'admin@sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'admin', 'active');

-- =============================================
-- 4. INSERT STUDENTS - CNTT (7 sinh viên)
-- =============================================
-- Password for all students: student123
INSERT INTO user (name, email, password, role, student_code, department_id, class_id, phone, date_of_birth, status) VALUES
('Nguyễn Văn An', 'an.nv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122410001', 1, 1, '0901234567', '2004-03-15', 'active'),
('Trần Thị Bình', 'binh.tt@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122410002', 1, 1, '0901234568', '2004-05-20', 'active'),
('Lê Hoàng Cường', 'cuong.lh@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122410003', 1, 1, '0901234569', '2004-07-10', 'active'),
('Phạm Thị Dung', 'dung.pt@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122410004', 1, 2, '0901234570', '2004-02-28', 'active'),
('Hoàng Văn Em', 'em.hv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122410005', 1, 2, '0901234571', '2004-11-05', 'suspended'),
('Võ Thị Phương', 'phuong.vt@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122410006', 1, 2, '0901234572', '2004-09-18', 'active'),
('Đặng Văn Giang', 'giang.dv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122410007', 1, 1, '0901234573', '2004-01-22', 'active');

-- =============================================
-- 5. INSERT STUDENTS - QTKD (6 sinh viên)
-- =============================================
INSERT INTO user (name, email, password, role, student_code, department_id, class_id, phone, date_of_birth, status) VALUES
('Bùi Thị Hoa', 'hoa.bt@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122420001', 2, 3, '0902234567', '2004-04-12', 'active'),
('Ngô Văn Inh', 'inh.nv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122420002', 2, 3, '0902234568', '2004-06-25', 'active'),
('Đinh Thị Kim', 'kim.dt@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122420003', 2, 3, '0902234569', '2004-08-30', 'active'),
('Trương Văn Long', 'long.tv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122420004', 2, 4, '0902234570', '2004-10-15', 'active'),
('Lý Thị Mai', 'mai.lt@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122420005', 2, 4, '0902234571', '2004-12-08', 'active'),
('Phan Văn Nam', 'nam.pv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122420006', 2, 4, '0902234572', '2004-03-20', 'active');

-- =============================================
-- 6. INSERT STUDENTS - SPA (6 sinh viên)
-- =============================================
INSERT INTO user (name, email, password, role, student_code, department_id, class_id, phone, date_of_birth, status) VALUES
('Cao Thị Oanh', 'oanh.ct@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122430001', 3, 5, '0903234567', '2004-05-14', 'active'),
('Dương Văn Phúc', 'phuc.dv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122430002', 3, 5, '0903234568', '2004-07-22', 'active'),
('Huỳnh Thị Quỳnh', 'quynh.ht@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122430003', 3, 5, '0903234569', '2004-09-11', 'active'),
('Mai Văn Rồng', 'rong.mv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122430004', 3, 6, '0903234570', '2004-11-28', 'active'),
('Tô Thị Sương', 'suong.tt@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122430005', 3, 6, '0903234571', '2004-01-17', 'active'),
('Vũ Văn Tài', 'tai.vv@student.sgu.edu.vn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', 'student', '3122430006', 3, 6, '0903234572', '2004-04-09', 'active');

-- =============================================
-- 7. INSERT PROGRAMS (Chương trình đào tạo)
-- =============================================
INSERT INTO program (name, department_id, total_credits_required, description) VALUES
('Cử nhân Công nghệ thông tin', 1, 120, 'Chương trình đào tạo Cử nhân CNTT'),
('Cử nhân Quản trị kinh doanh', 2, 120, 'Chương trình đào tạo Cử nhân QTKD'),
('Cử nhân Sư phạm Anh', 3, 120, 'Chương trình đào tạo Cử nhân Sư phạm Anh');

-- =============================================
-- 8. INSERT GRADUATION REQUIREMENTS
-- =============================================
-- CNTT Requirements
INSERT INTO graduation_requirement (program_id, requirement_type, requirement_value, description, is_mandatory) VALUES
(1, 'total_credits', '120', 'Tổng số tín chỉ tích lũy', TRUE),
(1, 'gpa_minimum', '2.0', 'Điểm GPA tối thiểu', TRUE),
(1, 'english_cert', 'TOEIC 450', 'Chứng chỉ tiếng Anh tối thiểu TOEIC 450 hoặc tương đương', TRUE),
(1, 'it_cert', 'MOS hoặc IC3', 'Chứng chỉ tin học cơ bản', TRUE),
(1, 'physical_education', 'Pass', 'Hoàn thành chương trình Giáo dục thể chất', TRUE),
(1, 'defense_education', 'Pass', 'Hoàn thành chương trình Giáo dục Quốc phòng', TRUE);

-- QTKD Requirements
INSERT INTO graduation_requirement (program_id, requirement_type, requirement_value, description, is_mandatory) VALUES
(2, 'total_credits', '120', 'Tổng số tín chỉ tích lũy', TRUE),
(2, 'gpa_minimum', '2.0', 'Điểm GPA tối thiểu', TRUE),
(2, 'english_cert', 'TOEIC 500', 'Chứng chỉ tiếng Anh tối thiểu TOEIC 500 hoặc tương đương', TRUE),
(2, 'it_cert', 'MOS', 'Chứng chỉ tin học cơ bản MOS', TRUE),
(2, 'physical_education', 'Pass', 'Hoàn thành chương trình Giáo dục thể chất', TRUE),
(2, 'defense_education', 'Pass', 'Hoàn thành chương trình Giáo dục Quốc phòng', TRUE);

-- SPA Requirements
INSERT INTO graduation_requirement (program_id, requirement_type, requirement_value, description, is_mandatory) VALUES
(3, 'total_credits', '120', 'Tổng số tín chỉ tích lũy', TRUE),
(3, 'gpa_minimum', '2.5', 'Điểm GPA tối thiểu (cao hơn do là sư phạm)', TRUE),
(3, 'english_cert', 'IELTS 6.5', 'Chứng chỉ tiếng Anh tối thiểu IELTS 6.5 hoặc TOEIC 750', TRUE),
(3, 'it_cert', 'IC3', 'Chứng chỉ tin học cơ bản IC3', TRUE),
(3, 'physical_education', 'Pass', 'Hoàn thành chương trình Giáo dục thể chất', TRUE),
(3, 'defense_education', 'Pass', 'Hoàn thành chương trình Giáo dục Quốc phòng', TRUE);

-- =============================================
-- 9. LINK STUDENTS TO PROGRAMS
-- =============================================
-- CNTT students
INSERT INTO student_program (user_id, program_id, start_year, expected_graduation_year) VALUES
(2, 1, 2022, 2026),
(3, 1, 2022, 2026),
(4, 1, 2022, 2026),
(5, 1, 2022, 2026),
(6, 1, 2022, 2027),
(7, 1, 2022, 2026),
(8, 1, 2022, 2026);

-- QTKD students
INSERT INTO student_program (user_id, program_id, start_year, expected_graduation_year) VALUES
(9, 2, 2022, 2026),
(10, 2, 2022, 2026),
(11, 2, 2022, 2026),
(12, 2, 2022, 2026),
(13, 2, 2022, 2026),
(14, 2, 2022, 2026);

-- SPA students
INSERT INTO student_program (user_id, program_id, start_year, expected_graduation_year) VALUES
(15, 3, 2022, 2026),
(16, 3, 2022, 2026),
(17, 3, 2022, 2026),
(18, 3, 2022, 2026),
(19, 3, 2022, 2026),
(20, 3, 2022, 2026);

-- =============================================
-- 10. INSERT COURSES - CNTT
-- =============================================
INSERT INTO course (code, name, credits, description) VALUES
-- Môn chung
('GE101', 'Triết học Mác - Lênin', 3, 'Môn học đại cương'),
('GE102', 'Kinh tế chính trị Mác - Lênin', 2, 'Môn học đại cương'),
('GE103', 'Chủ nghĩa xã hội khoa học', 2, 'Môn học đại cương'),
('GE104', 'Lịch sử Đảng Cộng sản Việt Nam', 2, 'Môn học đại cương'),
('GE105', 'Tư tưởng Hồ Chí Minh', 2, 'Môn học đại cương'),
('MA101', 'Toán cao cấp A1', 3, 'Toán học cơ bản'),
('MA102', 'Toán cao cấp A2', 3, 'Toán học cơ bản'),
('MA103', 'Toán rời rạc', 3, 'Toán học cho CNTT'),
('MA104', 'Xác suất thống kê', 3, 'Toán học ứng dụng'),
-- Môn chuyên ngành CNTT
('CS101', 'Nhập môn lập trình', 4, 'Lập trình cơ bản với Python'),
('CS102', 'Cấu trúc dữ liệu và giải thuật', 4, 'CTDL & GT cơ bản'),
('CS103', 'Lập trình hướng đối tượng', 4, 'OOP với Java'),
('CS104', 'Cơ sở dữ liệu', 4, 'Database fundamentals'),
('CS105', 'Hệ điều hành', 3, 'Operating Systems'),
('CS106', 'Mạng máy tính', 3, 'Computer Networks'),
('CS107', 'Công nghệ Web', 4, 'Web Development'),
('CS108', 'Phát triển ứng dụng di động', 4, 'Mobile App Development'),
('CS109', 'Trí tuệ nhân tạo', 3, 'Artificial Intelligence'),
('CS110', 'Học máy', 3, 'Machine Learning'),
('CS111', 'Đồ án chuyên ngành', 4, 'Capstone Project');

-- =============================================
-- 11. INSERT COURSES - QTKD
-- =============================================
INSERT INTO course (code, name, credits, description) VALUES
-- Môn chuyên ngành QTKD
('BA101', 'Nguyên lý quản trị', 3, 'Principles of Management'),
('BA102', 'Kinh tế vi mô', 3, 'Microeconomics'),
('BA103', 'Kinh tế vĩ mô', 3, 'Macroeconomics'),
('BA104', 'Quản trị marketing', 4, 'Marketing Management'),
('BA105', 'Quản trị tài chính', 4, 'Financial Management'),
('BA106', 'Quản trị nhân sự', 3, 'Human Resource Management'),
('BA107', 'Quản trị chiến lược', 3, 'Strategic Management'),
('BA108', 'Kế toán quản trị', 3, 'Management Accounting'),
('BA109', 'Hành vi tổ chức', 3, 'Organizational Behavior'),
('BA110', 'Quản trị dự án', 3, 'Project Management'),
('BA111', 'Thương mại điện tử', 3, 'E-Commerce'),
('BA112', 'Đồ án tốt nghiệp', 4, 'Graduation Thesis');

-- =============================================
-- 12. INSERT COURSES - SPA
-- =============================================
INSERT INTO course (code, name, credits, description) VALUES
-- Môn chuyên ngành Sư phạm Anh
('EN101', 'Ngữ âm tiếng Anh', 3, 'English Phonetics'),
('EN102', 'Ngữ pháp tiếng Anh', 3, 'English Grammar'),
('EN103', 'Kỹ năng nghe', 3, 'Listening Skills'),
('EN104', 'Kỹ năng nói', 3, 'Speaking Skills'),
('EN105', 'Kỹ năng đọc', 3, 'Reading Skills'),
('EN106', 'Kỹ năng viết', 3, 'Writing Skills'),
('EN107', 'Văn học Anh - Mỹ', 3, 'English-American Literature'),
('EN108', 'Phương pháp giảng dạy tiếng Anh', 4, 'English Teaching Methodology'),
('EN109', 'Ngôn ngữ học đối chiếu', 3, 'Contrastive Linguistics'),
('EN110', 'Thực hành sư phạm', 4, 'Teaching Practice'),
('EN111', 'Khóa luận tốt nghiệp', 4, 'Graduation Thesis');

-- =============================================
-- 13. INSERT COURSE PREREQUISITES
-- =============================================
-- CNTT Prerequisites
INSERT INTO course_prerequisite (course_id, prerequisite_id, is_mandatory) VALUES
((SELECT id FROM course WHERE code='CS102'), (SELECT id FROM course WHERE code='CS101'), TRUE),
((SELECT id FROM course WHERE code='CS103'), (SELECT id FROM course WHERE code='CS101'), TRUE),
((SELECT id FROM course WHERE code='CS104'), (SELECT id FROM course WHERE code='CS102'), TRUE),
((SELECT id FROM course WHERE code='CS107'), (SELECT id FROM course WHERE code='CS103'), TRUE),
((SELECT id FROM course WHERE code='CS108'), (SELECT id FROM course WHERE code='CS103'), TRUE),
((SELECT id FROM course WHERE code='CS110'), (SELECT id FROM course WHERE code='CS109'), TRUE),
((SELECT id FROM course WHERE code='CS111'), (SELECT id FROM course WHERE code='CS104'), TRUE);

-- =============================================
-- 14. INSERT PROGRAM COURSES - CNTT
-- =============================================
INSERT INTO program_course (program_id, course_id, course_type, semester_recommended) VALUES
-- Học kỳ 1
(1, (SELECT id FROM course WHERE code='GE101'), 'general', 1),
(1, (SELECT id FROM course WHERE code='MA101'), 'general', 1),
(1, (SELECT id FROM course WHERE code='CS101'), 'mandatory', 1),
-- Học kỳ 2
(1, (SELECT id FROM course WHERE code='GE102'), 'general', 2),
(1, (SELECT id FROM course WHERE code='MA102'), 'general', 2),
(1, (SELECT id FROM course WHERE code='CS102'), 'mandatory', 2),
(1, (SELECT id FROM course WHERE code='MA103'), 'mandatory', 2),
-- Học kỳ 3
(1, (SELECT id FROM course WHERE code='GE103'), 'general', 3),
(1, (SELECT id FROM course WHERE code='CS103'), 'mandatory', 3),
(1, (SELECT id FROM course WHERE code='CS104'), 'mandatory', 3),
(1, (SELECT id FROM course WHERE code='MA104'), 'mandatory', 3),
-- Học kỳ 4
(1, (SELECT id FROM course WHERE code='GE104'), 'general', 4),
(1, (SELECT id FROM course WHERE code='CS105'), 'mandatory', 4),
(1, (SELECT id FROM course WHERE code='CS106'), 'mandatory', 4),
-- Học kỳ 5
(1, (SELECT id FROM course WHERE code='GE105'), 'general', 5),
(1, (SELECT id FROM course WHERE code='CS107'), 'mandatory', 5),
(1, (SELECT id FROM course WHERE code='CS109'), 'mandatory', 5),
-- Học kỳ 6
(1, (SELECT id FROM course WHERE code='CS108'), 'elective', 6),
(1, (SELECT id FROM course WHERE code='CS110'), 'elective', 6),
-- Học kỳ 7
(1, (SELECT id FROM course WHERE code='CS111'), 'mandatory', 7);

-- =============================================
-- 15. INSERT PROGRAM COURSES - QTKD
-- =============================================
INSERT INTO program_course (program_id, course_id, course_type, semester_recommended) VALUES
-- Học kỳ 1
(2, (SELECT id FROM course WHERE code='GE101'), 'general', 1),
(2, (SELECT id FROM course WHERE code='MA101'), 'general', 1),
(2, (SELECT id FROM course WHERE code='BA101'), 'mandatory', 1),
(2, (SELECT id FROM course WHERE code='BA102'), 'mandatory', 1),
-- Học kỳ 2
(2, (SELECT id FROM course WHERE code='GE102'), 'general', 2),
(2, (SELECT id FROM course WHERE code='BA103'), 'mandatory', 2),
(2, (SELECT id FROM course WHERE code='BA104'), 'mandatory', 2),
-- Học kỳ 3
(2, (SELECT id FROM course WHERE code='GE103'), 'general', 3),
(2, (SELECT id FROM course WHERE code='BA105'), 'mandatory', 3),
(2, (SELECT id FROM course WHERE code='BA106'), 'mandatory', 3),
-- Học kỳ 4
(2, (SELECT id FROM course WHERE code='GE104'), 'general', 4),
(2, (SELECT id FROM course WHERE code='BA107'), 'mandatory', 4),
(2, (SELECT id FROM course WHERE code='BA108'), 'mandatory', 4),
-- Học kỳ 5
(2, (SELECT id FROM course WHERE code='GE105'), 'general', 5),
(2, (SELECT id FROM course WHERE code='BA109'), 'mandatory', 5),
(2, (SELECT id FROM course WHERE code='BA110'), 'mandatory', 5),
-- Học kỳ 6
(2, (SELECT id FROM course WHERE code='BA111'), 'elective', 6),
-- Học kỳ 7
(2, (SELECT id FROM course WHERE code='BA112'), 'mandatory', 7);

-- =============================================
-- 16. INSERT PROGRAM COURSES - SPA
-- =============================================
INSERT INTO program_course (program_id, course_id, course_type, semester_recommended) VALUES
-- Học kỳ 1
(3, (SELECT id FROM course WHERE code='GE101'), 'general', 1),
(3, (SELECT id FROM course WHERE code='EN101'), 'mandatory', 1),
(3, (SELECT id FROM course WHERE code='EN102'), 'mandatory', 1),
-- Học kỳ 2
(3, (SELECT id FROM course WHERE code='GE102'), 'general', 2),
(3, (SELECT id FROM course WHERE code='EN103'), 'mandatory', 2),
(3, (SELECT id FROM course WHERE code='EN104'), 'mandatory', 2),
-- Học kỳ 3
(3, (SELECT id FROM course WHERE code='GE103'), 'general', 3),
(3, (SELECT id FROM course WHERE code='EN105'), 'mandatory', 3),
(3, (SELECT id FROM course WHERE code='EN106'), 'mandatory', 3),
-- Học kỳ 4
(3, (SELECT id FROM course WHERE code='GE104'), 'general', 4),
(3, (SELECT id FROM course WHERE code='EN107'), 'mandatory', 4),
(3, (SELECT id FROM course WHERE code='EN108'), 'mandatory', 4),
-- Học kỳ 5
(3, (SELECT id FROM course WHERE code='GE105'), 'general', 5),
(3, (SELECT id FROM course WHERE code='EN109'), 'mandatory', 5),
-- Học kỳ 6
(3, (SELECT id FROM course WHERE code='EN110'), 'mandatory', 6),
-- Học kỳ 7
(3, (SELECT id FROM course WHERE code='EN111'), 'mandatory', 7);

-- =============================================
-- 17. INSERT TERMS (Học kỳ từ 2022-2026)
-- =============================================
INSERT INTO term (name, year, start_date, end_date) VALUES
('HK1', 2022, '2022-09-01', '2023-01-15'),
('HK2', 2022, '2023-02-01', '2023-06-30'),
('HK1', 2023, '2023-09-01', '2024-01-15'),
('HK2', 2023, '2024-02-01', '2024-06-30'),
('HK1', 2024, '2024-09-01', '2025-01-15'),
('HK2', 2024, '2025-02-01', '2025-06-30'),
('HK1', 2025, '2025-09-01', '2026-01-15'),
('HK2', 2025, '2026-02-01', '2026-06-30');
