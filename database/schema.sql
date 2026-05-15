-- =============================================
-- SGU Student Management System - Database Schema
-- =============================================

DROP DATABASE IF EXISTS sgu_student_management;
CREATE DATABASE sgu_student_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sgu_student_management;

-- =============================================
-- 1. DEPARTMENT (Khoa/Ngành)
-- =============================================
CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 2. CLASS (Lớp học)
-- =============================================
CREATE TABLE class (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    department_id BIGINT NOT NULL,
    year VARCHAR(10) NOT NULL COMMENT 'K22, K23, K24',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 3. USER (Sinh viên và Admin)
-- =============================================
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'ADMIN') NOT NULL DEFAULT 'STUDENT',
    student_code VARCHAR(50) UNIQUE COMMENT 'MSSV',
    department_id BIGINT,
    class_id BIGINT,
    phone VARCHAR(20),
    date_of_birth DATE,
    address TEXT,
    status ENUM('ACTIVE', 'SUSPENDED', 'EXPELLED', 'GRADUATED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL,
    FOREIGN KEY (class_id) REFERENCES class(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 4. PROGRAM (Chương trình đào tạo)
-- =============================================
CREATE TABLE program (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    department_id BIGINT NOT NULL,
    total_credits_required INT NOT NULL DEFAULT 120,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 5. GRADUATION_REQUIREMENT (Yêu cầu tốt nghiệp)
-- =============================================
CREATE TABLE graduation_requirement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    program_id BIGINT NOT NULL,
    requirement_type ENUM('total_credits', 'english_cert', 'it_cert', 'physical_education', 'defense_education', 'gpa_minimum') NOT NULL,
    requirement_value VARCHAR(255) NOT NULL COMMENT 'Giá trị yêu cầu: 120, TOEIC 450, MOS, ...',
    description TEXT,
    is_mandatory BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (program_id) REFERENCES program(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 6. STUDENT_PROGRAM (Sinh viên thuộc chương trình)
-- =============================================
CREATE TABLE student_program (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    program_id BIGINT NOT NULL,
    start_year INT NOT NULL,
    expected_graduation_year INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (program_id) REFERENCES program(id) ON DELETE CASCADE,
    UNIQUE KEY unique_student_program (user_id, program_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 7. COURSE (Môn học)
-- =============================================
CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    credits INT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 8. COURSE_PREREQUISITE (Môn tiên quyết)
-- =============================================
CREATE TABLE course_prerequisite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL COMMENT 'Môn hiện tại',
    prerequisite_id BIGINT NOT NULL COMMENT 'Môn tiên quyết',
    is_mandatory BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    FOREIGN KEY (prerequisite_id) REFERENCES course(id) ON DELETE CASCADE,
    UNIQUE KEY unique_prerequisite (course_id, prerequisite_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 9. PROGRAM_COURSE (Môn học thuộc chương trình)
-- =============================================
CREATE TABLE program_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    program_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    course_type ENUM('mandatory', 'elective', 'general') NOT NULL DEFAULT 'mandatory',
    semester_recommended INT COMMENT 'Học kỳ đề xuất: 1, 2, 3...',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (program_id) REFERENCES program(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    UNIQUE KEY unique_program_course (program_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 10. TERM (Học kỳ)
-- =============================================
CREATE TABLE term (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_term (name, year)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 11. ENROLLMENT (Đăng ký môn học)
-- =============================================
CREATE TABLE enrollment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    term_id BIGINT NOT NULL,
    status ENUM('enrolled', 'completed', 'dropped', 'failed') DEFAULT 'enrolled',
    attempt_number INT DEFAULT 1 COMMENT 'Lần học thứ mấy',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    FOREIGN KEY (term_id) REFERENCES term(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 12. GRADE (Điểm số)
-- =============================================
CREATE TABLE grade (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    enrollment_id BIGINT NOT NULL,
    midterm_score DECIMAL(4,2) COMMENT 'Điểm giữa kỳ',
    final_score DECIMAL(4,2) COMMENT 'Điểm cuối kỳ',
    total_score DECIMAL(4,2) COMMENT 'Tổng điểm',
    grade_letter VARCHAR(5) COMMENT 'A+, A, B+, B, C+, C, D+, D, F',
    gpa_point DECIMAL(3,2) COMMENT '4.0, 3.5, 3.0, ...',
    passed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(id) ON DELETE CASCADE,
    UNIQUE KEY unique_grade (enrollment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 13. STUDENT_CERTIFICATE (Chứng chỉ sinh viên)
-- =============================================
CREATE TABLE student_certificate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    certificate_type ENUM('english', 'it', 'physical_education', 'defense_education', 'other') NOT NULL,
    certificate_name VARCHAR(255) NOT NULL COMMENT 'TOEIC, IELTS, MOS, ...',
    score_or_level VARCHAR(100) COMMENT '450, 5.0, Pass, ...',
    issue_date DATE,
    expiry_date DATE,
    verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 14. ACADEMIC_WARNING (Cảnh báo học vụ)
-- =============================================
CREATE TABLE academic_warning (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    term_id BIGINT,
    warning_level INT NOT NULL COMMENT '1, 2, 3 (buộc thôi học)',
    reason TEXT NOT NULL,
    status ENUM('active', 'resolved') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP NULL,
    notes TEXT,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (term_id) REFERENCES term(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 15. CHAT_HISTORY (Lịch sử chat với AI)
-- =============================================
CREATE TABLE chat_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    response TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- INDEXES for Performance
-- =============================================
CREATE INDEX idx_user_student_code ON user(student_code);
CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_user_role ON user(role);
CREATE INDEX idx_user_status ON user(status);
CREATE INDEX idx_enrollment_user ON enrollment(user_id);
CREATE INDEX idx_enrollment_course ON enrollment(course_id);
CREATE INDEX idx_enrollment_term ON enrollment(term_id);
CREATE INDEX idx_enrollment_status ON enrollment(status);
CREATE INDEX idx_grade_enrollment ON grade(enrollment_id);
CREATE INDEX idx_academic_warning_user ON academic_warning(user_id);
CREATE INDEX idx_academic_warning_status ON academic_warning(status);
CREATE INDEX idx_student_certificate_user ON student_certificate(user_id);
CREATE INDEX idx_student_certificate_type ON student_certificate(certificate_type);
