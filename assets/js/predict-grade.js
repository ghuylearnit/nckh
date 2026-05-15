// =============================================
// PREDICT GRADE PAGE
// =============================================

let currentStudentId = 2;

document.addEventListener('DOMContentLoaded', async function () {
    console.log('Predict Grade page loading...');
    try {
        await loadStudentInfo(currentStudentId);
        await loadPredictData(currentStudentId);
        setupSearch();
        console.log('Predict Grade page loaded!');
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

    const innerInfo = document.querySelector('.section-9 .inner-info');
    if (innerInfo) {
        innerInfo.innerHTML = `
            <div class="inner-name">${student.studentName}</div>
            <div class="inner-id">${student.studentCode}</div>
            <div class="inner-class">${student.className || 'N/A'}</div>
            <div class="inner-major">${student.departmentName || 'N/A'}</div>
        `;
    }

    return student;
}

// =============================================
// LOAD PREDICT DATA (dùng transcript + graduation prediction)
// =============================================

async function loadPredictData(studentId) {
    const [transcript, prediction] = await Promise.all([
        fetchAPI(`/students/${studentId}/transcript`),
        fetchAPI(`/graduation/student/${studentId}/prediction`)
    ]);

    const tbody = document.querySelector('.section-3 table tbody');
    if (!tbody) return;

    // Lấy danh sách môn đã đăng ký từ transcript
    const courses = transcript?.enrollments || [];

    if (courses.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="text-align:center; padding:30px;">
                    Không có dữ liệu môn học
                </td>
            </tr>`;
        updatePagination(0);
        return;
    }

    tbody.innerHTML = courses.map(course => {
        const predictedGPA = predictGrade(course);
        return `
            <tr>
                <td>${course.courseCode || 'N/A'}</td>
                <td>${course.courseName || 'N/A'}</td>
                <td>${course.credits || 0}</td>
                <td>
                    <div class="tag tag-green">Đã đăng ký</div>
                </td>
                <td>
                    <div class="box-actions">
                        <a class="inner-detail" href="#"
                           onclick="showPrediction(${JSON.stringify(course).replace(/"/g, '&quot;')}); return false;">
                            Xem chi tiết
                        </a>
                    </div>
                </td>
            </tr>
        `;
    }).join('');

    updatePagination(courses.length);
}

// =============================================
// SIMPLE GRADE PREDICTION (dựa trên điểm lịch sử)
// =============================================

function predictGrade(course) {
    if (course.totalScore) {
        return course.totalScore.toFixed(1);
    }
    return 'Chưa có';
}

// =============================================
// SHOW PREDICTION DETAIL
// =============================================

function showPrediction(course) {
    const score = course.totalScore ? course.totalScore.toFixed(1) : 'Chưa có';
    const gpa = course.gpaPoint ? course.gpaPoint.toFixed(1) : 'N/A';
    const passed = course.passed ? '✅ Đạt' : (course.passed === false ? '❌ Không đạt' : 'Chưa có kết quả');

    alert(`
Phân tích & Dự đoán môn học

Môn: ${course.courseName}
Mã môn: ${course.courseCode}
Số tín chỉ: ${course.credits}

Điểm giữa kỳ: ${course.midtermScore ?? 'N/A'}
Điểm cuối kỳ: ${course.finalScore ?? 'N/A'}
Điểm tổng kết: ${score}
Điểm GPA: ${gpa}
Kết quả: ${passed}
    `);
}

window.showPrediction = showPrediction;

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
            await loadPredictData(currentStudentId);
            return;
        }

        try {
            const students = await fetchAPI('/students');
            const found = students.find(s =>
                s.studentCode?.toLowerCase().includes(term.toLowerCase()) ||
                s.name?.toLowerCase().includes(term.toLowerCase())
            );
            if (found) {
                currentStudentId = found.id;
                await loadStudentInfo(currentStudentId);
                await loadPredictData(currentStudentId);
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
