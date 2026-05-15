# SGU Student Management System - Database

## 📋 Tổng quan

Database cho hệ thống quản lý sinh viên Đại học Sài Gòn (SGU), bao gồm:
- Quản lý thông tin sinh viên, môn học, điểm số
- Theo dõi tiến độ học tập và yêu cầu tốt nghiệp
- Cảnh báo học vụ
- Chứng chỉ sinh viên
- Lịch sử chat với AI

## 🗄️ Cấu trúc Database

### Các bảng chính:

1. **department** - Khoa/Ngành (CNTT, QTKD, SPA)
2. **class** - Lớp học
3. **user** - Sinh viên và Admin
4. **program** - Chương trình đào tạo
5. **graduation_requirement** - Yêu cầu tốt nghiệp
6. **student_program** - Sinh viên thuộc chương trình
7. **course** - Môn học
8. **course_prerequisite** - Môn tiên quyết
9. **program_course** - Môn học thuộc chương trình
10. **term** - Học kỳ
11. **enrollment** - Đăng ký môn học
12. **grade** - Điểm số
13. **student_certificate** - Chứng chỉ sinh viên
14. **academic_warning** - Cảnh báo học vụ
15. **chat_history** - Lịch sử chat

## 🚀 Cài đặt

### Bước 1: Tạo Database và Schema

```bash
mysql -u root -p < schema.sql
```

### Bước 2: Import Sample Data

```bash
mysql -u root -p < sample_data.sql
mysql -u root -p < sample_enrollments_grades.sql
```

### Hoặc chạy tất cả cùng lúc:

```bash
mysql -u root -p < schema.sql && \
mysql -u root -p < sample_data.sql && \
mysql -u root -p < sample_enrollments_grades.sql
```

## 👥 Sample Data

### Admin Account
- **Email**: admin@sgu.edu.vn
- **Password**: admin123
- **Role**: admin

### Student Accounts
Tất cả sinh viên có password: **student123**

#### Công nghệ thông tin (7 sinh viên):
1. **Nguyễn Văn An** (3122410001) - Sinh viên xuất sắc, GPA ~3.6
2. **Trần Thị Bình** (3122410002) - Sinh viên khá, GPA ~3.0
3. **Lê Hoàng Cường** (3122410003)
4. **Phạm Thị Dung** (3122410004)
5. **Hoàng Văn Em** (3122410005) - Sinh viên yếu, có cảnh báo học vụ, GPA ~1.2
6. **Võ Thị Phương** (3122410006)
7. **Đặng Văn Giang** (3122410007)

#### Quản trị kinh doanh (6 sinh viên):
1. **Bùi Thị Hoa** (3122420001) - Sinh viên tốt, GPA ~3.6
2. **Ngô Văn Inh** (3122420002)
3. **Đinh Thị Kim** (3122420003)
4. **Trương Văn Long** (3122420004)
5. **Lý Thị Mai** (3122420005)
6. **Phan Văn Nam** (3122420006)

#### Sư phạm Anh (6 sinh viên):
1. **Cao Thị Oanh** (3122430001) - Sinh viên giỏi, GPA ~3.9
2. **Dương Văn Phúc** (3122430002)
3. **Huỳnh Thị Quỳnh** (3122430003)
4. **Mai Văn Rồng** (3122430004)
5. **Tô Thị Sương** (3122430005)
6. **Vũ Văn Tài** (3122430006)

## 📊 Dữ liệu mẫu

### Môn học:
- **CNTT**: 21 môn (bao gồm môn chung + chuyên ngành)
- **QTKD**: 17 môn
- **SPA**: 16 môn

### Học kỳ:
- Từ HK1 2022 đến HK2 2025 (8 học kỳ)

### Yêu cầu tốt nghiệp:
- **Tổng tín chỉ**: 120 tín chỉ
- **GPA tối thiểu**: 
  - CNTT & QTKD: 2.0
  - SPA: 2.5 (cao hơn do là sư phạm)
- **Chứng chỉ tiếng Anh**:
  - CNTT: TOEIC 450+
  - QTKD: TOEIC 500+
  - SPA: IELTS 6.5+ hoặc TOEIC 750+
- **Chứng chỉ tin học**: MOS, IC3
- **GDTC & GDQP**: Pass

## 🔍 Queries hữu ích

### 1. Xem tất cả sinh viên và GPA

```sql
SELECT 
    u.student_code,
    u.name,
    d.name as department,
    c.name as class,
    ROUND(AVG(g.gpa_point), 2) as gpa
FROM user u
LEFT JOIN department d ON u.department_id = d.id
LEFT JOIN class c ON u.class_id = c.id
LEFT JOIN enrollment e ON u.id = e.user_id
LEFT JOIN grade g ON e.id = g.enrollment_id
WHERE u.role = 'student' AND g.passed = TRUE
GROUP BY u.id
ORDER BY gpa DESC;
```

### 2. Sinh viên có cảnh báo học vụ

```sql
SELECT 
    u.student_code,
    u.name,
    aw.warning_level,
    aw.reason,
    aw.status
FROM academic_warning aw
JOIN user u ON aw.user_id = u.id
WHERE aw.status = 'active'
ORDER BY aw.warning_level DESC;
```

### 3. Tín chỉ đã tích lũy của sinh viên

```sql
SELECT 
    u.student_code,
    u.name,
    SUM(c.credits) as total_credits
FROM user u
JOIN enrollment e ON u.id = e.user_id
JOIN course c ON e.course_id = c.id
JOIN grade g ON e.id = g.enrollment_id
WHERE u.role = 'student' AND g.passed = TRUE
GROUP BY u.id
ORDER BY total_credits DESC;
```

### 4. Sinh viên thiếu chứng chỉ

```sql
SELECT 
    u.student_code,
    u.name,
    d.name as department,
    CASE 
        WHEN NOT EXISTS (
            SELECT 1 FROM student_certificate sc 
            WHERE sc.user_id = u.id AND sc.certificate_type = 'english'
        ) THEN 'Thiếu chứng chỉ tiếng Anh'
        ELSE 'Đã có'
    END as english_status,
    CASE 
        WHEN NOT EXISTS (
            SELECT 1 FROM student_certificate sc 
            WHERE sc.user_id = u.id AND sc.certificate_type = 'it'
        ) THEN 'Thiếu chứng chỉ tin học'
        ELSE 'Đã có'
    END as it_status
FROM user u
JOIN department d ON u.department_id = d.id
WHERE u.role = 'student';
```

### 5. Môn học còn thiếu của sinh viên

```sql
-- Ví dụ: Sinh viên 3122410001
SELECT 
    c.code,
    c.name,
    c.credits,
    pc.course_type,
    pc.semester_recommended
FROM program_course pc
JOIN course c ON pc.course_id = c.id
WHERE pc.program_id = 1  -- CNTT program
AND c.id NOT IN (
    SELECT e.course_id 
    FROM enrollment e
    JOIN grade g ON e.id = g.enrollment_id
    WHERE e.user_id = 2 AND g.passed = TRUE
)
ORDER BY pc.semester_recommended;
```

## 🔐 Security Notes

- Passwords được hash bằng BCrypt
- Default password cho demo: 
  - Admin: `admin123`
  - Students: `student123`
- **⚠️ ĐỔI PASSWORD TRƯỚC KHI DEPLOY PRODUCTION!**

## 📝 Notes

- Database sử dụng charset `utf8mb4` để hỗ trợ tiếng Việt đầy đủ
- Tất cả bảng có `created_at` và `updated_at` timestamps
- Foreign keys có `ON DELETE CASCADE` hoặc `SET NULL` tùy logic
- Indexes được tạo cho các trường thường xuyên query

## 🛠️ Maintenance

### Backup Database

```bash
mysqldump -u root -p sgu_student_management > backup_$(date +%Y%m%d).sql
```

### Restore Database

```bash
mysql -u root -p sgu_student_management < backup_20250510.sql
```

## 📞 Support

Nếu có vấn đề với database, kiểm tra:
1. MySQL version >= 8.0
2. Character set: utf8mb4
3. Foreign key constraints enabled
4. Sufficient privileges cho user
