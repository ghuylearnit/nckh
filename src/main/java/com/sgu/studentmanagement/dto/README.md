# 📦 DTO (Data Transfer Object) Package

## 🎯 Mục đích

Package này chứa các DTO classes để:
- Tách biệt Entity layer và Presentation layer
- Kiểm soát dữ liệu trả về cho client
- Tránh lỗi LazyInitializationException
- Tối ưu performance (chỉ load dữ liệu cần thiết)

## 📂 Cấu trúc

```
dto/
├── request/          # DTOs cho request body
│   └── (chưa có)
└── response/         # DTOs cho response body
    └── StudentResponse.java
```

## 🔍 DTO Pattern

### Vấn đề khi trả về Entity trực tiếp:

```java
// ❌ BAD: Trả về Entity
@GetMapping
public ResponseEntity<List<User>> getAllStudents() {
    return ResponseEntity.ok(userRepository.findAll());
}
```

**Vấn đề:**
1. **LazyInitializationException:** Entity có quan hệ @ManyToOne LAZY
2. **Circular reference:** Entity có thể tham chiếu lẫn nhau
3. **Expose sensitive data:** Password, internal fields
4. **Performance:** Load toàn bộ Entity graph

### Giải pháp với DTO:

```java
// ✅ GOOD: Trả về DTO
@GetMapping
@Transactional(readOnly = true)
public ResponseEntity<List<StudentResponse>> getAllStudents() {
    List<StudentResponse> students = userRepository.findAll()
        .stream()
        .map(StudentResponse::fromEntity)
        .toList();
    return ResponseEntity.ok(students);
}
```

**Lợi ích:**
1. ✅ Không có LazyInitializationException
2. ✅ Kiểm soát dữ liệu trả về
3. ✅ Không expose sensitive data
4. ✅ Performance tốt hơn

## 📝 Cách tạo DTO

### Bước 1: Tạo DTO class

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    // ... các field cần thiết
    
    // Không có Entity references!
    private Long departmentId;      // Chỉ lưu ID
    private String departmentName;  // Chỉ lưu name
}
```

### Bước 2: Tạo method convert

```java
public static StudentResponse fromEntity(User user) {
    StudentResponseBuilder builder = StudentResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail());
    
    // Safely access lazy properties
    if (user.getDepartment() != null) {
        builder.departmentId(user.getDepartment().getId())
               .departmentName(user.getDepartment().getName());
    }
    
    return builder.build();
}
```

### Bước 3: Sử dụng trong Controller

```java
@GetMapping
@Transactional(readOnly = true)  // Important!
public ResponseEntity<List<StudentResponse>> getAllStudents() {
    return ResponseEntity.ok(
        userRepository.findAll()
            .stream()
            .map(StudentResponse::fromEntity)
            .toList()
    );
}
```

## 🎨 Naming Convention

### Response DTOs:
- `{Entity}Response` - Cho single entity
- `{Entity}ListResponse` - Cho list of entities
- `{Entity}DetailResponse` - Cho detailed view
- `{Entity}SummaryResponse` - Cho summary view

**Ví dụ:**
- `StudentResponse` - Basic student info
- `StudentDetailResponse` - Student with grades, enrollments
- `StudentSummaryResponse` - Student with GPA only

### Request DTOs:
- `Create{Entity}Request` - Cho create operation
- `Update{Entity}Request` - Cho update operation
- `{Entity}FilterRequest` - Cho filtering/search

**Ví dụ:**
- `CreateStudentRequest`
- `UpdateStudentRequest`
- `StudentFilterRequest`

## 📊 DTO vs Entity

| Aspect | Entity | DTO |
|--------|--------|-----|
| Purpose | Database mapping | Data transfer |
| Annotations | JPA annotations | Lombok only |
| Relationships | @ManyToOne, @OneToMany | Primitive types, IDs |
| Validation | Database constraints | @Valid annotations |
| Serialization | Can cause issues | Safe to serialize |
| Exposure | Internal use | External API |

## 🔧 Best Practices

### 1. Không expose Entity trực tiếp
```java
// ❌ BAD
@GetMapping
public ResponseEntity<User> getStudent() { ... }

// ✅ GOOD
@GetMapping
public ResponseEntity<StudentResponse> getStudent() { ... }
```

### 2. Sử dụng @Transactional khi convert
```java
// ✅ GOOD
@GetMapping
@Transactional(readOnly = true)
public ResponseEntity<StudentResponse> getStudent() {
    User user = userRepository.findById(id).orElseThrow();
    return ResponseEntity.ok(StudentResponse.fromEntity(user));
}
```

### 3. Null-safe conversion
```java
// ✅ GOOD
if (user.getDepartment() != null) {
    builder.departmentId(user.getDepartment().getId());
}
```

### 4. Tách DTO theo use case
```java
// List view - minimal data
StudentSummaryResponse

// Detail view - full data
StudentDetailResponse

// Create/Update - only editable fields
CreateStudentRequest
UpdateStudentRequest
```

## 📚 Ví dụ thực tế

### StudentResponse.java
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private String studentCode;
    
    // Department info (not Entity!)
    private Long departmentId;
    private String departmentName;
    private String departmentCode;
    
    // Class info (not Entity!)
    private Long classId;
    private String className;
    
    public static StudentResponse fromEntity(User user) {
        StudentResponseBuilder builder = StudentResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .studentCode(user.getStudentCode());
        
        if (user.getDepartment() != null) {
            builder.departmentId(user.getDepartment().getId())
                   .departmentName(user.getDepartment().getName())
                   .departmentCode(user.getDepartment().getCode());
        }
        
        if (user.getClassEntity() != null) {
            builder.classId(user.getClassEntity().getId())
                   .className(user.getClassEntity().getName());
        }
        
        return builder.build();
    }
}
```

## 🚀 Bước tiếp theo

Tạo thêm DTOs cho:
1. **CourseResponse** - Môn học
2. **DepartmentResponse** - Khoa
3. **GradeResponse** - Điểm
4. **EnrollmentResponse** - Đăng ký môn học
5. **StudentDetailResponse** - Chi tiết sinh viên (với grades, enrollments)

## 📖 Tài liệu tham khảo

- [DTO Pattern](https://martinfowler.com/eaaCatalog/dataTransferObject.html)
- [Spring Boot Best Practices](https://spring.io/guides)
- [Lombok Documentation](https://projectlombok.org/)

---

**Lưu ý:** Luôn sử dụng DTO cho API responses, không bao giờ expose Entity trực tiếp!
