# 🚀 HƯỚNG DẪN TẠO REPOSITORY TRÊN GITHUB

## 📝 BƯỚC 1: TẠO REPOSITORY TRÊN GITHUB.COM

### **Cách 1: Tạo qua Web (Dễ nhất)**

1. **Đăng nhập GitHub:**
   - Vào https://github.com
   - Đăng nhập tài khoản của bạn

2. **Tạo Repository mới:**
   - Click nút **"+"** ở góc trên bên phải
   - Chọn **"New repository"**

3. **Điền thông tin:**
   - **Repository name:** `sgu-student-management` (hoặc tên bạn muốn)
   - **Description:** `Full-Stack Student Management System for Saigon University`
   - **Visibility:** 
     - ✅ **Public** (nếu muốn public)
     - ✅ **Private** (nếu muốn riêng tư cho team)
   - **QUAN TRỌNG:** 
     - ❌ **KHÔNG** tick "Add a README file"
     - ❌ **KHÔNG** tick "Add .gitignore"
     - ❌ **KHÔNG** chọn "Choose a license"
     - (Vì project của bạn đã có sẵn các file này rồi)

4. **Click "Create repository"**

---

## 📝 BƯỚC 2: KẾT NỐI LOCAL VỚI GITHUB

Sau khi tạo repo, GitHub sẽ hiển thị hướng dẫn. Bạn chọn phần **"…or push an existing repository from the command line"**

### **Nếu chưa có remote origin:**

```powershell
# Thêm remote origin
git remote add origin https://github.com/<username>/sgu-student-management.git

# Đổi branch thành main (nếu đang là master)
git branch -M main

# Push lần đầu
git push -u origin main
```

### **Nếu đã có remote origin (kiểm tra):**

```powershell
# Kiểm tra remote hiện tại
git remote -v

# Nếu có origin cũ, xóa đi
git remote remove origin

# Thêm origin mới
git remote add origin https://github.com/<username>/sgu-student-management.git

# Push
git push -u origin main
```

---

## 📝 BƯỚC 3: PUSH CODE LÊN GITHUB

```powershell
# Bước 1: Stage tất cả files
git add .

# Bước 2: Commit
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

# Bước 3: Push lên GitHub
git push -u origin main
```

---

## 🔐 XÁC THỰC (AUTHENTICATION)

### **Nếu GitHub yêu cầu đăng nhập:**

#### **Cách 1: Personal Access Token (Khuyên dùng)**

1. Vào GitHub Settings: https://github.com/settings/tokens
2. Click **"Generate new token"** → **"Generate new token (classic)"**
3. Đặt tên: `SGU Project`
4. Chọn quyền: ✅ **repo** (full control)
5. Click **"Generate token"**
6. **COPY TOKEN** (chỉ hiện 1 lần!)
7. Khi push, dùng token làm password:
   - Username: `<your-github-username>`
   - Password: `<paste-token-here>`

#### **Cách 2: SSH Key (Nâng cao)**

```powershell
# Tạo SSH key
ssh-keygen -t ed25519 -C "your_email@example.com"

# Copy public key
cat ~/.ssh/id_ed25519.pub

# Thêm vào GitHub: Settings → SSH and GPG keys → New SSH key
```

Sau đó đổi remote URL:
```powershell
git remote set-url origin git@github.com:<username>/sgu-student-management.git
```

---

## ✅ KIỂM TRA SAU KHI PUSH

1. **Vào repository trên GitHub:**
   ```
   https://github.com/<username>/sgu-student-management
   ```

2. **Kiểm tra:**
   - ✅ README.md hiển thị đẹp
   - ✅ Source code đầy đủ
   - ✅ KHÔNG có file `application-dev.properties`
   - ✅ KHÔNG có file `.md` tạm thời (ACTION_PLAN.md, etc.)
   - ✅ KHÔNG có folder `target/`

---

## 🤝 CHIA SẺ VỚI TEAM

### **Nếu repository là Private:**

1. Vào **Settings** → **Collaborators**
2. Click **"Add people"**
3. Nhập username hoặc email của team members
4. Chọn quyền: **Write** (để họ có thể push code)

### **Nếu repository là Public:**

- Team có thể clone ngay:
```powershell
git clone https://github.com/<username>/sgu-student-management.git
```

---

## 📞 TROUBLESHOOTING

### **Lỗi: "remote origin already exists"**
```powershell
git remote remove origin
git remote add origin https://github.com/<username>/sgu-student-management.git
```

### **Lỗi: "Permission denied"**
- Kiểm tra username/password
- Hoặc dùng Personal Access Token

### **Lỗi: "Updates were rejected"**
```powershell
git pull origin main --rebase
git push origin main
```

### **Lỗi: "Large files detected"**
- Kiểm tra file size
- Thêm vào `.gitignore`

---

## 📋 CHECKLIST

- [ ] Tạo repository trên GitHub
- [ ] Chọn Public/Private
- [ ] KHÔNG tick "Add README"
- [ ] Copy repository URL
- [ ] Chạy `git remote add origin <url>`
- [ ] Chạy `git add .`
- [ ] Chạy `git commit -m "..."`
- [ ] Chạy `git push -u origin main`
- [ ] Kiểm tra trên GitHub
- [ ] Thêm collaborators (nếu cần)

---

## 🎯 TÓM TẮT NHANH

```powershell
# 1. Tạo repo trên GitHub (qua web)

# 2. Kết nối local với GitHub
git remote add origin https://github.com/<username>/sgu-student-management.git

# 3. Stage files
git add .

# 4. Commit
git commit -m "feat: Complete Phase 5 - Full-Stack Integration"

# 5. Push
git push -u origin main
```

---

**Chúc thành công! 🎉**

**Lưu ý:** Thay `<username>` bằng GitHub username của bạn!
