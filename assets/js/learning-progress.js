// =============================================
// LEARNING PROGRESS PAGE - LOAD STUDENT DATA
// =============================================

// Student IDs to load (excluding ID 6 which has issues)
const STUDENT_IDS = [2, 3, 4, 5, 7, 9, 10, 11, 15, 16];

let allStudentsData = [];

// Wait for DOM to be ready
document.addEventListener('DOMContentLoaded', async function() {
    console.log('Learning Progress page loading...');
    
    try {
        // Load all students data
        await loadAllStudents();
        
        // Render table
        renderStudentsTable(allStudentsData);
        
        // Setup filters
        setupFilters();
        
        console.log('Learning Progress page loaded successfully!');
    } catch (error) {
        console.error('Error loading learning progress:', error);
        showError('Không thể tải dữ liệu sinh viên. Vui lòng kiểm tra kết nối Backend.');
    }
});

// =============================================
// LOAD ALL STUDENTS DATA
// =============================================

async function loadAllStudents() {
    try {
        const promises = STUDENT_IDS.map(id => API.getStudentReport(id));
        const results = await Promise.all(promises);
        
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
        
        console.log(`Loaded ${allStudentsData.length} students`);
    } catch (error) {
        console.error('Error loading students:', error);
        throw error;
    }
}

// =============================================
// RENDER STUDENTS TABLE
// =============================================

function renderStudentsTable(students) {
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

// =============================================
// SETUP FILTERS
// =============================================

function setupFilters() {
    // Reset filter button
    const resetButton = document.querySelector('.inner-reset');
    if (resetButton) {
        resetButton.addEventListener('click', () => {
            renderStudentsTable(allStudentsData);
        });
    }
    
    // Department filter
    const deptSelect = document.querySelector('.section-4 select:nth-child(4)');
    if (deptSelect) {
        deptSelect.addEventListener('change', (e) => {
            const selectedDept = e.target.value;
            if (!selectedDept) {
                renderStudentsTable(allStudentsData);
                return;
            }
            
            const filtered = allStudentsData.filter(s => 
                s.departmentName.includes(selectedDept)
            );
            renderStudentsTable(filtered);
        });
    }
}

// =============================================
// VIEW STUDENT DETAIL
// =============================================

async function viewStudentDetail(studentId) {
    try {
        const student = await API.getStudentReport(studentId);
        
        // Create modal or redirect to detail page
        alert(`
Thông tin chi tiết sinh viên:

MSSV: ${student.studentCode}
Họ tên: ${student.studentName}
Khoa: ${student.departmentName}
Lớp: ${student.className}

Học tập:
- Tổng tín chỉ: ${student.academicPerformance.totalCredits}
- GPA: ${API.formatGPA(student.academicPerformance.overallGPA)}
- Xếp loại: ${student.academicPerformance.academicStanding}
- Môn đạt: ${student.academicPerformance.passedCourses}
- Môn trượt: ${student.academicPerformance.failedCourses}

Tốt nghiệp:
- Đủ điều kiện: ${student.graduationStatus.canGraduate ? 'Có' : 'Không'}
- Tín chỉ còn thiếu: ${student.graduationStatus.remainingCredits}
- Học kỳ dự kiến: ${student.graduationStatus.estimatedGraduationTerm}

Chứng chỉ: ${student.certificates.length} chứng chỉ
Cảnh báo: ${student.academicWarnings.length} cảnh báo
        `);
    } catch (error) {
        console.error('Error loading student detail:', error);
        alert('Không thể tải thông tin chi tiết sinh viên');
    }
}

// Make function available globally
window.viewStudentDetail = viewStudentDetail;

// =============================================
// UPDATE PAGINATION
// =============================================

function updatePagination(total) {
    const paginationText = document.querySelector('.section-7 .inner-text');
    if (paginationText) {
        paginationText.textContent = `Hiển thị 1 - ${total} của ${total}`;
    }
}

// =============================================
// ERROR HANDLING
// =============================================

function showError(message) {
    const main = document.querySelector('.main');
    if (main) {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'alert alert-error';
        errorDiv.style.cssText = 'background: #f44336; color: white; padding: 15px; margin: 20px 0; border-radius: 4px;';
        errorDiv.innerHTML = `
            <i class="fa-solid fa-triangle-exclamation"></i>
            <strong>Lỗi:</strong> ${message}
        `;
        main.insertBefore(errorDiv, main.firstChild);
    }
}
