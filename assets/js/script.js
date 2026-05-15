// =============================================
// MENU NAVIGATION
// =============================================

const listButton = document.querySelectorAll(".inner-menu li a");
listButton.forEach(button => {
  button.addEventListener("click", () => {
    const buttonActiveCurrent = document.querySelector(".inner-menu a.active");
    if(buttonActiveCurrent) {
      buttonActiveCurrent.classList.remove("active");
    }
    button.classList.add("active");
  });
});

// Set active class based on current page
const currentPath = window.location.pathname.split('/').pop();
listButton.forEach(button => {
  if (button.getAttribute('href') === currentPath) {
    button.classList.add('active');
  }
});

// =============================================
// PAGE INITIALIZATION
// =============================================

document.addEventListener('DOMContentLoaded', function() {
    // Detect current page and initialize
    const pageName = currentPath.replace('.html', '');
    
    switch(pageName) {
        case 'learning-progress':
            initLearningProgressPage();
            break;
        case 'missing-subjects':
            initMissingSubjectsPage();
            break;
        case 'predict-grade':
            initPredictGradePage();
            break;
    }
});

// =============================================
// LEARNING PROGRESS PAGE
// =============================================

let allStudentsData = [];
const STUDENT_IDS = [2, 3, 4, 5, 7, 9, 10, 11, 15, 16]; // IDs có data

async function initLearningProgressPage() {
    console.log('Initializing Learning Progress page...');
    
    try {
        await loadAllStudentsProgress();
        setupLearningProgressFilters();
        
        console.log('Learning Progress page loaded successfully!');
    } catch (error) {
        console.error('Error loading learning progress:', error);
        API.showError('Không thể tải dữ liệu tiến độ học tập');
    }
}

async function loadAllStudentsProgress() {
    const loading = API.showLoading('Đang tải danh sách sinh viên...');
    
    try {
        // Load all students reports in parallel
        const promises = STUDENT_IDS.map(id => API.getStudentReport(id));
        const results = await Promise.all(promises);
        
        // Transform data
        allStudentsData = results.map(student => ({
            id: student.studentId,
            studentCode: student.studentCode,
            name: student.studentName,
            className: student.className || 'N/A',
            departmentName: student.departmentName || 'N/A',
            credits: student.academicPerformance.totalCredits,
            gpa: student.academicPerformance.overallGPA,
            standing: student.academicPerformance.academicStanding,
            atRisk: student.academicPerformance.atRisk
        }));
        
        // Render table
        renderLearningProgressTable(allStudentsData);
        
        API.hideLoading(loading);
        API.showSuccess(`Đã tải ${allStudentsData.length} sinh viên`);
        
    } catch (error) {
        API.hideLoading(loading);
        throw error;
    }
}

function renderLearningProgressTable(students) {
    const tbody = document.querySelector('.section-3 table tbody');
    if (!tbody) return;
    
    if (students.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="10" style="text-align: center; padding: 40px;">
                    Không tìm thấy sinh viên nào
                </td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = students.map((student, index) => `
        <tr>
            <td>
                <div class="checkbox-wrapper-28">
                    <input class="promoted-input-checkbox" type="checkbox" id="checkbox${index}"/>
                    <svg>
                        <use xlink:href="#checkmark-28"></use>
                    </svg>
                    <label for="checkbox${index}"></label>
                    <svg style="display: none" xmlns="http://www.w3.org/2000/svg">
                        <symbol id="checkmark-28" viewBox="0 0 24 24">
                            <path d="M22.9 3.7l-15.2 16.6-6.6-7.1" fill="none" stroke-miterlimit="10" stroke-linecap="round"></path>
                        </symbol>
                    </svg>
                </div>
            </td>
            <td>${student.studentCode}</td>
            <td>${student.name}</td>
            <td>${student.className}</td>
            <td>${student.departmentName}</td>
            <td>K22</td>
            <td>${student.credits}</td>
            <td>${API.formatGPA(student.gpa)}</td>
            <td>${API.getSeverityIcon(student.gpa)}</td>
            <td>
                <div class="box-actions">
                    <a class="inner-detail" href="#" onclick="viewStudentDetail(${student.id}); return false;">
                        Xem chi tiết
                    </a>
                </div>
            </td>
        </tr>
    `).join('');
    
    // Update pagination
    updatePagination(students.length);
}

function setupLearningProgressFilters() {
    // Reset filter button
    const resetButton = document.querySelector('.inner-reset');
    if (resetButton) {
        resetButton.addEventListener('click', () => {
            renderLearningProgressTable(allStudentsData);
        });
    }
    
    // Department filter
    const deptSelect = document.querySelector('.section-4 select:nth-child(4)');
    if (deptSelect) {
        deptSelect.addEventListener('change', (e) => {
            const selectedDept = e.target.value;
            if (!selectedDept) {
                renderLearningProgressTable(allStudentsData);
                return;
            }
            
            const filtered = allStudentsData.filter(s => 
                s.departmentName.includes(selectedDept)
            );
            renderLearningProgressTable(filtered);
        });
    }
}

async function viewStudentDetail(studentId) {
    const loading = API.showLoading('Đang tải thông tin chi tiết...');
    
    try {
        const student = await API.getStudentReport(studentId);
        
        // Show detailed info in alert (you can replace with modal)
        alert(`
THÔNG TIN CHI TIẾT SINH VIÊN

MSSV: ${student.studentCode}
Họ tên: ${student.studentName}
Khoa: ${student.departmentName}
Lớp: ${student.className}

HỌC TẬP:
- Tổng tín chỉ: ${student.academicPerformance.totalCredits}
- GPA: ${API.formatGPA(student.academicPerformance.overallGPA)}
- Xếp loại: ${student.academicPerformance.academicStanding}
- Môn đạt: ${student.academicPerformance.passedCourses}
- Môn trượt: ${student.academicPerformance.failedCourses}

TỐT NGHIỆP:
- Đủ điều kiện: ${student.graduationStatus.canGraduate ? 'Có' : 'Không'}
- Tín chỉ còn thiếu: ${student.graduationStatus.remainingCredits}
- Học kỳ dự kiến: ${student.graduationStatus.estimatedGraduationTerm}

Chứng chỉ: ${student.certificates.length} chứng chỉ
Cảnh báo: ${student.academicWarnings.length} cảnh báo
        `);
        
        API.hideLoading(loading);
        
    } catch (error) {
        API.hideLoading(loading);
        API.showError('Không thể tải thông tin chi tiết sinh viên');
    }
}

// Make function available globally
window.viewStudentDetail = viewStudentDetail;

// =============================================
// MISSING SUBJECTS PAGE
// =============================================

let currentStudentId = 2; // Default student ID

async function initMissingSubjectsPage() {
    console.log('Initializing Missing Subjects page...');
    
    try {
        await loadMissingSubjectsData(currentStudentId);
        setupMissingSubjectsSearch();
        
        console.log('Missing Subjects page loaded successfully!');
    } catch (error) {
        console.error('Error loading missing subjects:', error);
        API.showError('Không thể tải dữ liệu môn học còn thiếu');
    }
}

async function loadMissingSubjectsData(studentId) {
    const loading = API.showLoading('Đang tải dữ liệu...');
    
    try {
        // Load student info and missing subjects in parallel
        const [studentReport, missingSubjects] = await Promise.all([
            API.getStudentReport(studentId),
            API.getMissingSubjects(studentId)
        ]);
        
        // Update student info section
        updateStudentInfoSection(studentReport);
        
        // Render missing subjects table
        renderMissingSubjectsTable(missingSubjects);
        
        // Update requirements status
        updateRequirementsStatus(studentReport);
        
        API.hideLoading(loading);
        API.showSuccess('Đã tải dữ liệu thành công');
        
    } catch (error) {
        API.hideLoading(loading);
        throw error;
    }
}

function updateStudentInfoSection(student) {
    const nameEl = document.querySelector('.section-9 .inner-name');
    const idEl = document.querySelector('.section-9 .inner-id');
    const classEl = document.querySelector('.section-9 .inner-class');
    const majorEl = document.querySelector('.section-9 .inner-major');
    const creditEl = document.querySelector('.section-9 .inner-credit');
    const missingEl = document.querySelector('.section-9 .inner-missing');
    const gpaEl = document.querySelector('.section-9 .inner-gpa');
    
    if (nameEl) nameEl.textContent = student.studentName;
    if (idEl) idEl.textContent = student.studentCode;
    if (classEl) classEl.textContent = student.className || 'N/A';
    if (majorEl) majorEl.textContent = student.departmentName;
    
    const totalCredits = student.academicPerformance.totalCredits;
    const requiredCredits = 120; // Assuming 120 is required
    const remainingCredits = student.graduationStatus.remainingCredits;
    
    if (creditEl) creditEl.textContent = `Tín chỉ đã tích luỹ: ${totalCredits}/${requiredCredits}`;
    if (missingEl) missingEl.textContent = `Tín chỉ còn thiếu: ${remainingCredits}`;
    if (gpaEl) gpaEl.textContent = `GPA hiện tại: ${API.formatGPA(student.academicPerformance.overallGPA)}`;
}

function renderMissingSubjectsTable(missingSubjects) {
    const tbody = document.querySelector('.section-3 table tbody');
    if (!tbody) return;
    
    if (!missingSubjects || missingSubjects.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="text-align: center; padding: 40px;">
                    <div class="severity severity-green"><i class="fa-solid fa-check"></i></div>
                    <p>Sinh viên đã hoàn thành tất cả môn học bắt buộc!</p>
                </td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = missingSubjects.map(subject => `
        <tr>
            <td>${subject.courseCode || 'N/A'}</td>
            <td>${subject.courseName}</td>
            <td>${subject.credits}</td>
            <td>
                <div class="tag ${subject.isRequired ? 'tag-red' : 'tag-yellow'}">
                    ${subject.isRequired ? 'Bắt buộc' : 'Tự chọn'}
                </div>
            </td>
            <td>${subject.expectedTerm || 'Chưa xác định'}</td>
        </tr>
    `).join('');
}

function updateRequirementsStatus(student) {
    const tbody = document.querySelectorAll('.section-3 table tbody')[1];
    if (!tbody) return;
    
    // Check certificates/requirements
    const certificates = student.certificates || [];
    
    const hasEnglish = certificates.some(c => c.type === 'ENGLISH');
    const hasIT = certificates.some(c => c.type === 'IT');
    const hasPE = certificates.some(c => c.type === 'PE');
    const hasDefense = certificates.some(c => c.type === 'DEFENSE');
    
    tbody.innerHTML = `
        <tr>
            <td>
                <div class="severity ${hasEnglish ? 'severity-green' : 'severity-red'}">
                    <i class="fa-solid fa-${hasEnglish ? 'check' : 'xmark'}"></i>
                </div>
            </td>
            <td>
                <div class="severity ${hasIT ? 'severity-green' : 'severity-red'}">
                    <i class="fa-solid fa-${hasIT ? 'check' : 'xmark'}"></i>
                </div>
            </td>
            <td>
                <div class="severity ${hasPE ? 'severity-green' : 'severity-red'}">
                    <i class="fa-solid fa-${hasPE ? 'check' : 'xmark'}"></i>
                </div>
            </td>
            <td>
                <div class="severity ${hasDefense ? 'severity-green' : 'severity-red'}">
                    <i class="fa-solid fa-${hasDefense ? 'check' : 'xmark'}"></i>
                </div>
            </td>
        </tr>
    `;
}

function setupMissingSubjectsSearch() {
    const searchForm = document.querySelector('.section-8 .inner-search');
    if (!searchForm) return;
    
    searchForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const searchInput = searchForm.querySelector('input');
        const searchValue = searchInput.value.trim();
        
        if (!searchValue) {
            API.showError('Vui lòng nhập MSSV hoặc tên sinh viên');
            return;
        }
        
        // Try to find student by code
        // For demo, we'll use student ID 2-16
        const studentId = parseInt(searchValue) || 2;
        
        try {
            await loadMissingSubjectsData(studentId);
            currentStudentId = studentId;
        } catch (error) {
            API.showError('Không tìm thấy sinh viên');
        }
    });
}

// =============================================
// PREDICT GRADE PAGE
// =============================================

let currentPredictStudentId = 2; // Default student ID

async function initPredictGradePage() {
    console.log('Initializing Predict Grade page...');
    
    try {
        await loadPredictGradeData(currentPredictStudentId);
        setupPredictGradeSearch();
        
        console.log('Predict Grade page loaded successfully!');
    } catch (error) {
        console.error('Error loading predict grade:', error);
        API.showError('Không thể tải dữ liệu dự đoán điểm');
    }
}

async function loadPredictGradeData(studentId) {
    const loading = API.showLoading('Đang tải dữ liệu dự đoán...');
    
    try {
        // Load student info and enrollments
        const [studentReport, enrollments] = await Promise.all([
            API.getStudentReport(studentId),
            API.getStudentEnrollments(studentId).catch(() => []) // Handle if not available
        ]);
        
        // Update student info
        updatePredictStudentInfo(studentReport);
        
        // Render prediction table
        renderPredictionTable(studentReport, enrollments);
        
        API.hideLoading(loading);
        API.showSuccess('Đã tải dữ liệu dự đoán');
        
    } catch (error) {
        API.hideLoading(loading);
        throw error;
    }
}

function updatePredictStudentInfo(student) {
    const nameEl = document.querySelector('.section-9 .inner-name');
    const idEl = document.querySelector('.section-9 .inner-id');
    const classEl = document.querySelector('.section-9 .inner-class');
    const majorEl = document.querySelector('.section-9 .inner-major');
    
    if (nameEl) nameEl.textContent = student.studentName;
    if (idEl) idEl.textContent = student.studentCode;
    if (classEl) classEl.textContent = student.className || 'N/A';
    if (majorEl) majorEl.textContent = student.departmentName;
}

function renderPredictionTable(student, enrollments) {
    const tbody = document.querySelector('.section-3 table tbody');
    if (!tbody) return;
    
    // Get current term courses (mock data based on student report)
    const currentGPA = student.academicPerformance.overallGPA;
    const courses = generateMockEnrolledCourses(student);
    
    if (courses.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="text-align: center; padding: 40px;">
                    Sinh viên chưa đăng ký môn học nào trong học kỳ này
                </td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = courses.map(course => {
        const predictedGrade = predictGradeForCourse(currentGPA, course.difficulty);
        
        return `
            <tr>
                <td>${course.code}</td>
                <td>${course.name}</td>
                <td>${course.credits}</td>
                <td>
                    <div class="tag tag-green">Đã đăng ký</div>
                </td>
                <td>
                    <div class="box-actions">
                        <a class="inner-detail" href="#" onclick="showPredictionDetail('${course.code}', ${predictedGrade}); return false;">
                            Xem chi tiết
                        </a>
                    </div>
                </td>
            </tr>
        `;
    }).join('');
}

function generateMockEnrolledCourses(student) {
    // Generate mock courses based on student's progress
    const totalCredits = student.academicPerformance.totalCredits;
    
    if (totalCredits < 30) {
        return [
            { code: 'CS101', name: 'Lập trình căn bản', credits: 4, difficulty: 'medium' },
            { code: 'MATH101', name: 'Toán cao cấp 1', credits: 3, difficulty: 'hard' },
            { code: 'ENG101', name: 'Tiếng Anh 1', credits: 3, difficulty: 'easy' }
        ];
    } else if (totalCredits < 60) {
        return [
            { code: 'CS201', name: 'Cấu trúc dữ liệu', credits: 4, difficulty: 'hard' },
            { code: 'CS202', name: 'Cơ sở dữ liệu', credits: 4, difficulty: 'medium' },
            { code: 'CS203', name: 'Mạng máy tính', credits: 3, difficulty: 'medium' }
        ];
    } else {
        return [
            { code: 'CS301', name: 'Trí tuệ nhân tạo', credits: 4, difficulty: 'hard' },
            { code: 'CS302', name: 'Học máy', credits: 4, difficulty: 'hard' },
            { code: 'CS303', name: 'Đồ án tốt nghiệp', credits: 10, difficulty: 'medium' }
        ];
    }
}

function predictGradeForCourse(currentGPA, difficulty) {
    // Simple prediction algorithm
    let predictedGrade = currentGPA;
    
    if (difficulty === 'easy') {
        predictedGrade += 0.3;
    } else if (difficulty === 'hard') {
        predictedGrade -= 0.2;
    }
    
    // Keep in range 0-4
    return Math.max(0, Math.min(4, predictedGrade));
}

function showPredictionDetail(courseCode, predictedGrade) {
    const gradeLevel = predictedGrade >= 3.5 ? 'Xuất sắc' : 
                       predictedGrade >= 3.0 ? 'Giỏi' :
                       predictedGrade >= 2.5 ? 'Khá' :
                       predictedGrade >= 2.0 ? 'Trung bình' : 'Yếu';
    
    alert(`
DỰ ĐOÁN ĐIỂM CHI TIẾT

Môn học: ${courseCode}
Điểm dự đoán: ${API.formatGPA(predictedGrade)}
Xếp loại dự kiến: ${gradeLevel}

Phân tích:
- Dựa trên GPA hiện tại của sinh viên
- Độ khó của môn học
- Lịch sử học tập

Khuyến nghị:
${predictedGrade >= 2.5 ? 
    '✓ Sinh viên có khả năng đạt điểm tốt' : 
    '⚠ Sinh viên cần nỗ lực thêm để đạt điểm đạt'}
    `);
}

window.showPredictionDetail = showPredictionDetail;

function setupPredictGradeSearch() {
    const searchForm = document.querySelector('.section-8 .inner-search');
    if (!searchForm) return;
    
    searchForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const searchInput = searchForm.querySelector('input');
        const searchValue = searchInput.value.trim();
        
        if (!searchValue) {
            API.showError('Vui lòng nhập MSSV hoặc tên sinh viên');
            return;
        }
        
        // Try to find student by code
        const studentId = parseInt(searchValue) || 2;
        
        try {
            await loadPredictGradeData(studentId);
            currentPredictStudentId = studentId;
        } catch (error) {
            API.showError('Không tìm thấy sinh viên');
        }
    });
}

// =============================================
// UTILITY FUNCTIONS
// =============================================

function updatePagination(total) {
    const paginationText = document.querySelector('.section-7 .inner-text');
    if (paginationText) {
        paginationText.textContent = `Hiển thị 1 - ${total} của ${total}`;
    }
}

