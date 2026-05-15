// =============================================
// MISSING SUBJECTS PAGE
// =============================================

let currentStudentId = 2; // Default student
let allStudentsData = [];

document.addEventListener('DOMContentLoaded', async function () {
    console.log('Missing Subjects page loading...');
    try {
        await loadStudentInfo(currentStudentId);
        await loadMissingSubjects(currentStudentId);
        setupSearch();
        console.log('Missing Subjects page loaded!');
    } catch (error) {
        console.error('Error:', error);
        showError('Không thể tải dữ liệu. Vui lòng kiểm tra kết nối Backend.');
    }
});

// =============================================
// LOAD STUDENT INFO (section-9)
// =============================================

async function loadStudentInfo(studentId) {
    const student = await API.getStudentReport(studentId);

    // Update student info card
    const innerInfo = document.querySelector('.section-9 .inner-info');
    if (innerInfo) {
        innerInfo.innerHTML = `
            <div class="inner-name">${student.studentName}</div>
            <div class="inner-id">${student.studentCode}</div>
            <div class="inner-class">${student.className || 'N/A'}</div>
            <div class="inner-major">${student.departmentName || 'N/A'}</div>
        `;
    }

    // Update stats card
    const innerRight = document.querySelector('.section-9 .inner-right');
    if (innerRight) {
        const completed = student.graduationStatus.completedCredits;
        const required = student.graduationStatus.requiredCredits;
        const remaining = student.graduationStatus.remainingCredits;
        const gpa = API.formatGPA(student.academicPerformance.overallGPA);
        innerRight.innerHTML = `
            <div class="inner-credit">Tín chỉ đã tích luỹ: ${completed}/${required}</div>
            <div class="inner-missing">Tín chỉ còn thiếu: ${remaining}</div>
            <div class="inner-gpa">GPA hiện tại: ${gpa}</div>
        `;
    }

    return student;
}

// =============================================
// LOAD MISSING SUBJECTS TABLE
// =============================================

async function loadMissingSubjects(studentId) {
    const data = await fetchAPI(`/graduation/student/${studentId}/missing-subjects`);
    const tbody = document.querySelector('.section-3 table tbody');
    if (!tbody) return;

    if (!data || data.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="text-align:center; padding:30px;">
                    Sinh viên đã hoàn thành tất cả môn học yêu cầu 🎉
                </td>
            </tr>`;
        updatePagination(0);
        return;
    }

    tbody.innerHTML = data.map(subject => `
        <tr>
            <td>${subject.courseCode || 'N/A'}</td>
            <td>${subject.courseName || 'N/A'}</td>
            <td>${subject.credits || 0}</td>
            <td>
                <div class="tag ${subject.courseType === 'required' ? 'tag-red' : 'tag-orange'}">
                    ${subject.courseType === 'required' ? 'Bắt buộc' : 'Tự chọn'}
                </div>
            </td>
            <td>${subject.semesterRecommended || 'N/A'}</td>
        </tr>
    `).join('');

    updatePagination(data.length);
}

// =============================================
// SETUP SEARCH
// =============================================

function setupSearch() {
    const searchForm = document.querySelector('.inner-search');
    if (!searchForm) return;

    const input = searchForm.querySelector('input');
    searchForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const term = input.value.trim();
        if (!term) {
            await loadStudentInfo(currentStudentId);
            await loadMissingSubjects(currentStudentId);
            return;
        }

        // Search by student code
        try {
            const students = await fetchAPI('/students');
            const found = students.find(s =>
                s.studentCode?.toLowerCase().includes(term.toLowerCase()) ||
                s.name?.toLowerCase().includes(term.toLowerCase())
            );
            if (found) {
                currentStudentId = found.id;
                await loadStudentInfo(currentStudentId);
                await loadMissingSubjects(currentStudentId);
            } else {
                showError('Không tìm thấy sinh viên');
            }
        } catch (err) {
            console.error(err);
        }
    });
}

// =============================================
// HELPERS
// =============================================

function updatePagination(total) {
    const el = document.querySelector('.section-7 .inner-text');
    if (el) el.textContent = `Hiển thị 1 - ${total} của ${total}`;
}

function showError(message) {
    const main = document.querySelector('.main');
    if (!main) return;
    const div = document.createElement('div');
    div.style.cssText = 'background:#f44336;color:white;padding:15px;margin:20px 0;border-radius:4px;';
    div.innerHTML = `<i class="fa-solid fa-triangle-exclamation"></i> <strong>Lỗi:</strong> ${message}`;
    main.insertBefore(div, main.firstChild);
}
