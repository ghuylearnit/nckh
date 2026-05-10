# 🚀 HƯỚNG DẪN PUSH LÊN GITHUB

## ✅ ĐÃ CHUẨN BỊ

- ✅ `.gitignore` đã loại trừ ~50 file `.md` tạm thời
- ✅ `application-dev.properties` (có password) đã được loại trừ
- ✅ `README.md` đã sẵn sàng cho GitHub
- ✅ Temporary SQL files đã được loại trừ

## 📝 CÁC BƯỚC PUSH

### **Bước 1: Stage tất cả files**
```powershell
git add .
```

### **Bước 2: Commit với message**
```powershell
git commit -m "feat: Complete Phase 5 - Full-Stack Integration

- Implement Report Generation APIs (Student & Department)
- Connect Backend with Frontend
- Add Dashboard with real-time data
- Add Learning Progress page
- Add Expected Graduate page
- Fix UTF-8 encoding for Vietnamese names
- Configure CORS for Frontend-Backend communication
- Add comprehensive .gitignore

Status: 80% complete (core features working)"
```

### **Bước 3: Push lên GitHub**
```powershell
git push origin main
```

Hoặc nếu branch khác:
```powershell
git push origin <branch-name>
```

## 🔍 KIỂM TRA TRƯỚC KHI PUSH

### **Xem files sẽ được commit:**
```powershell
git status
```

### **Xem chi tiết thay đổi:**
```powershell
git diff
```

### **Xem files đã staged:**
```powershell
git diff --cached
```

## ⚠️ LƯU Ý

### **Files KHÔNG được push (đã loại trừ):**
- ❌ `application-dev.properties` (có password)
- ❌ ~50 file `.md` tạm thời (ACTION_PLAN.md, CHECKLIST.md, etc.)
- ❌ Temporary SQL files (`check_*.sql`, `fix_*.sql`)
- ❌ Build files (`target/`, `.idea/`)
- ❌ `README_GITHUB.md` (đã copy thành README.md)

### **Files SẼ được push:**
- ✅ Source code (`src/`)
- ✅ Database schema & sample data (`database/`)
- ✅ Frontend files (HTML, CSS, JS)
- ✅ `README.md` (clean version cho GitHub)
- ✅ `pom.xml`
- ✅ `.gitignore`

## 🎯 SAU KHI PUSH

### **Kiểm tra trên GitHub:**
1. Vào repository trên GitHub
2. Kiểm tra README.md hiển thị đúng
3. Kiểm tra không có file sensitive (password, etc.)
4. Kiểm tra không có file `.md` tạm thời

### **Clone về máy khác để test:**
```powershell
git clone <repository-url>
cd sgu-student-management
```

## 🤝 CHO TEAM

Sau khi push, team có thể:

1. **Clone project:**
```powershell
git clone <repository-url>
cd sgu-student-management
```

2. **Setup database:**
```powershell
mysql -u root -p < database/schema.sql
mysql -u root -p < database/sample_data.sql
```

3. **Tạo file config:**
Tạo `src/main/resources/application-dev.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sgu_student_management
spring.datasource.username=root
spring.datasource.password=your_password
```

4. **Run backend:**
```powershell
mvn spring-boot:run
```

5. **Open frontend:**
- Mở `dashboard.html` trong browser
- Hoặc dùng Live Server trong VS Code
- Hoặc chạy: `python -m http.server 8080`

## 📞 HỖ TRỢ

Nếu gặp lỗi khi push:

### **Lỗi: "Updates were rejected"**
```powershell
git pull origin main --rebase
git push origin main
```

### **Lỗi: "Permission denied"**
- Kiểm tra SSH key hoặc Personal Access Token
- Kiểm tra quyền truy cập repository

### **Lỗi: "Large files"**
- Kiểm tra file size
- Thêm vào `.gitignore` nếu cần

---

**Chúc may mắn! 🎉**
