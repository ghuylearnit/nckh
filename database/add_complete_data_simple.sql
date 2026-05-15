-- =============================================
-- SCRIPT ĐƠN GIẢN: BỔ SUNG DỮ LIỆU ĐẦY ĐỦ
-- Đã sửa lỗi: tên bảng, tên cột, cú pháp MySQL
-- =============================================

USE sgu_student_management;

-- BƯỚC 1: Thêm cột program_type vào bảng user (nếu chưa có)
-- Kiểm tra xem cột đã tồn tại chưa bằng cách thử thêm, nếu lỗi thì bỏ qua
SET @sql = 'ALTER TABLE user ADD COLUMN program_type VARCHAR(20) DEFAULT ''CU_NHAN''';
SET @check = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
              WHERE TABLE_SCHEMA = 'sgu_student_management' 
              AND TABLE_NAME = 'user' 
              AND COLUMN_NAME = 'program_type');

-- Chỉ thêm cột nếu chưa tồn tại
SET @sql = IF(@check = 0, @sql, 'SELECT ''Column program_type already exists''');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- BƯỚC 2: Update program_type cho tất cả sinh viên
-- CU_NHAN = 132 tín chỉ, KY_SU = 150 tín chỉ
UPDATE user SET program_type = 'CU_NHAN' WHERE role = 'STUDENT';

-- BƯỚC 3: Update chứng chỉ hiện có với tên cụ thể và điểm số
-- Student ID 2
UPDATE student_certificate 
SET certificate_name = 'TOEIC', score_or_level = '550' 
WHERE user_id = 2 AND certificate_type = 'ENGLISH' 
LIMIT 1;

UPDATE student_certificate 
SET certificate_name = 'IC3', score_or_level = 'Pass' 
WHERE user_id = 2 AND certificate_type = 'IT' 
LIMIT 1;

-- Student ID 3
UPDATE student_certificate 
SET certificate_name = 'TOEIC', score_or_level = '450' 
WHERE user_id = 3 AND certificate_type = 'ENGLISH';

UPDATE student_certificate 
SET certificate_name = 'ICDL', score_or_level = 'Pass' 
WHERE user_id = 3 AND certificate_type = 'IT';

-- Student ID 5
UPDATE student_certificate 
SET certificate_name = 'IELTS', score_or_level = '6.0' 
WHERE user_id = 5 AND certificate_type = 'ENGLISH';

UPDATE student_certificate 
SET certificate_name = 'IC3', score_or_level = 'Pass' 
WHERE user_id = 5 AND certificate_type = 'IT';

-- Student ID 9
UPDATE student_certificate 
SET certificate_name = 'TOEIC', score_or_level = '500' 
WHERE user_id = 9 AND certificate_type = 'ENGLISH';

UPDATE student_certificate 
SET certificate_name = 'IC3', score_or_level = 'Pass' 
WHERE user_id = 9 AND certificate_type = 'IT';

-- Student ID 15
UPDATE student_certificate 
SET certificate_name = 'IELTS', score_or_level = '5.5' 
WHERE user_id = 15 AND certificate_type = 'ENGLISH';

UPDATE student_certificate 
SET certificate_name = 'MOS Excel', score_or_level = 'Pass' 
WHERE user_id = 15 AND certificate_type = 'IT';

-- BƯỚC 4: Thêm chứng chỉ cho sinh viên chưa có (để họ đủ điều kiện tốt nghiệp)
-- Student ID 1 (chưa có chứng chỉ)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(1, 'ENGLISH', 'TOEIC', '600', TRUE, '2024-06-15'),
(1, 'IT', 'IC3', 'Pass', TRUE, '2024-07-20');

-- Student ID 4 (chưa có chứng chỉ)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(4, 'ENGLISH', 'IELTS', '6.5', TRUE, '2024-05-10'),
(4, 'IT', 'MOS Word', 'Pass', TRUE, '2024-06-25');

-- Student ID 7 (chưa có chứng chỉ)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(7, 'ENGLISH', 'TOEIC', '550', TRUE, '2024-08-12'),
(7, 'IT', 'IC3', 'Pass', TRUE, '2024-09-05');

-- Student ID 10 (chưa có chứng chỉ)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(10, 'ENGLISH', 'TOEIC', '520', TRUE, '2024-07-18'),
(10, 'IT', 'ICDL', 'Pass', TRUE, '2024-08-22');

-- Student ID 11 (chưa có chứng chỉ)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(11, 'ENGLISH', 'IELTS', '5.5', TRUE, '2024-06-30'),
(11, 'IT', 'IC3', 'Pass', TRUE, '2024-07-15');

-- Student ID 16 (chưa có chứng chỉ)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(16, 'ENGLISH', 'TOEIC', '480', TRUE, '2024-09-10'),
(16, 'IT', 'MOS Excel', 'Pass', TRUE, '2024-10-05');

-- BƯỚC 5: Thêm 1 chứng chỉ cho một số sinh viên khác (chưa đủ 2 chứng chỉ)
-- Student ID 8 (thêm chứng chỉ Tiếng Anh)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(8, 'ENGLISH', 'IELTS', '6.5', TRUE, '2024-05-20');

-- Student ID 12 (thêm chứng chỉ IT)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(12, 'IT', 'ICDL', 'Pass', TRUE, '2024-08-15');

-- Student ID 13 (thêm chứng chỉ Tiếng Anh)
INSERT INTO student_certificate (user_id, certificate_type, certificate_name, score_or_level, verified, issue_date) 
VALUES
(13, 'ENGLISH', 'TOEIC', '520', TRUE, '2024-07-25');

COMMIT;

-- Kiểm tra kết quả
SELECT 'Tổng số sinh viên:', COUNT(*) FROM user WHERE role = 'STUDENT';
SELECT 'Tổng số chứng chỉ:', COUNT(*) FROM student_certificate;
SELECT 'Sinh viên có đủ 2 chứng chỉ:', COUNT(DISTINCT user_id) 
FROM student_certificate 
GROUP BY user_id 
HAVING COUNT(*) >= 2;
