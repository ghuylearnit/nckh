# 🎓 SGU Student Management System

Full-Stack Student Management System for Saigon University

## 🚀 Features

- 📊 **Dashboard** - Overview statistics and charts
- 📈 **Learning Progress** - Track student academic performance
- 🎓 **Graduation Prediction** - Estimate graduation timeline
- 📝 **Report Generation** - Student and Department reports
- 🔍 **Search & Filter** - Find students by various criteria

## 🛠️ Tech Stack

### Backend
- **Java 17** + **Spring Boot 3.2.5**
- **Spring Data JPA** (Hibernate)
- **MySQL 8.0**
- **Spring Security**
- **Swagger/OpenAPI**

### Frontend
- **HTML5** + **CSS3** + **JavaScript (ES6+)**
- **Font Awesome** icons
- **Responsive Design**

## 📦 Installation

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Node.js (optional, for development server)

### Setup

1. **Clone repository:**
```bash
git clone <repository-url>
cd sgu-student-management
```

2. **Setup database:**
```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/sample_data.sql
```

3. **Configure database connection:**

Create `src/main/resources/application-dev.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sgu_student_management
spring.datasource.username=root
spring.datasource.password=your_password
```

4. **Run backend:**
```bash
mvn spring-boot:run
```

5. **Open frontend:**
- Open `dashboard.html` in browser
- Or use Live Server in VS Code
- Or run: `python -m http.server 8080`

## 📚 API Documentation

After running the application, access Swagger UI:
```
http://localhost:8081/swagger-ui/index.html
```

## 🗄️ Database

- **19 students** across 3 departments
- **87 grades** with complete academic records
- **3 departments:** Computer Science, Business Administration, English Education
- **UTF-8 encoding** for Vietnamese names

## 📊 Project Status

**Current:** 80% Complete

### ✅ Completed
- Backend REST APIs (13/14 working)
- Frontend integration
- Dashboard with real-time data
- Student and Department reports
- UTF-8 encoding support
- CORS configuration

### 🚧 In Progress
- Filters and search functionality
- Email notifications
- Export to PDF/Excel
- Grade prediction (ML)
- Missing courses page

## 🤝 Contributing

1. Fork the project
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Team

SGU Development Team

## 📞 Support

For issues and questions, please create an issue on GitHub.

---

**Made with ❤️ for SGU Students**
