// =============================================
// EXPECTED GRADUATE PAGE - LOAD GRADUATION DATA
// =============================================

// Student IDs to load (excluding ID 6 which has issues)
const STUDENT_IDS = [2, 3, 4, 5, 7, 9, 10, 11, 15, 16];

let allGraduationData = [];

// Wait for DOM to be ready
document.addEventListener('DOMContentLoaded', async function() {
    console.log('Expected Graduate page loading...');
    
    try {
        // Load all students graduation data
        await loadAllGraduationData();
        
        // Render table
        renderGraduationTable(allGraduationData);
        
        // Setup filters
        setupFilters();
        
        console.log('Expected Graduate page loaded successfully!');
    } catch (error) {
        console.error('Error loading graduation data:', error);
        showError('Không thể tải dữ liệu tốt nghiệp. Vui lòng kiểm tra kết nối Backend.');
    }
});

// =============================================
// LOAD ALL GRADUATION DATA
// =============================================

async function loadAllGraduationData() {
    try {
        const promises = STUDENT_IDS.map(id => API.getStudentReport(id));
        const results = await Promise.all(promises);
        
        allGraduationData = results.map(student => ({
            id: student.studentId,
            studentCode: student.studentCode,
            name: student.studentName,
            className: student.className || 'N/A',
            departmentName: student.departmentName || 'N/A',
            completedCredits: student.graduationStatus.completedCredits,
            requiredCredits: student.graduationStatus.requiredCredits,
            gpa: student.academicPerformance.overallGPA,
            canGraduate: student.graduationStatus.canGraduate,
            remainingCredits: student.graduationStatus.remainingCredits,
            estimatedTerm: student.graduationStatus.estimatedGraduationTerm,
            missingRequirements: student.graduationStatus.missingRequirements
        }));
        
        console.log(`Loaded ${allGraduationData.length} students graduation data`);
    } catch (error) {
        console.error('Error loading graduation data:', error);
        throw error;
    }
}

// =============================================
// RENDER GRADUATION TABLE
// =============================================

function renderGraduationTable(students) {
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
    
    tbody.innerHTML = students.map((student, index) => {
        const progress = `${student.completedCredits}/${student.requiredCredits}`;
        const statusTag = getGraduationStatusTag(student);
        const confidence = calculateConfidence(student);
        
        return `
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
                <td>${progress}</td>
                <td>${API.formatGPA(student.gpa)}</td>
                <td>${student.estimatedTerm}</td>
                <td>${statusTag}</td>
                <td>${confidence}%</td>
                <td>
                    <div class="box-actions">
                        <a class="inner-distance" href="#" onclick="viewGraduationPath(${student.id}); return false;">
                            Xem lộ trình
                        </a>
                    </div>
                </td>
            </tr>
        `;
    }).join('');
    
    // Update pagination
    updatePagination(students.length);
}

// =============================================
// GET GRADUATION STATUS TAG
// =============================================

function getGraduationStatusTag(student) {
    if (student.canGraduate) {
        return '<div class="tag tag-green">Đủ điều kiện tốt nghiệp</div>';
    } else if (student.remainingCredits === 0 && student.gpa < 2.0) {
        return '<div class="tag tag-red">Chưa đủ điều kiện (GPA thấp)</div>';
    } else if (student.remainingCredits <= 15) {
        return '<div class="tag tag-blue">Sắp tốt nghiệp</div>';
    } else if (student.remainingCredits <= 30) {
        return '<div class="tag tag-yellow">Đúng tiến độ</div>';
    } else {
        return '<div class="tag tag-red">Nguy cơ trễ tiến độ</div>';
    }
}

// =============================================
// CALCULATE CONFIDENCE
// =============================================

function calculateConfidence(student) {
    let confidence = 100;
    
    // Reduce confidence based on remaining credits
    if (student.remainingCredits > 0) {
        confidence -= Math.min(student.remainingCredits / 2, 30);
    }
    
    // Reduce confidence based on GPA
    if (student.gpa < 2.0) {
        confidence -= 20;
    } else if (student.gpa < 2.5) {
        confidence -= 10;
    }
    
    // Reduce confidence based on missing requirements
    if (student.missingRequirements.length > 0) {
        confidence -= student.missingRequirements.length * 5;
    }
    
    return Math.max(Math.round(confidence), 0);
}

// =============================================
// SETUP FILTERS
// =============================================

function setupFilters() {
    // Reset filter button
    const resetButton = document.querySelector('.inner-reset');
    if (resetButton) {
        resetButton.addEventListener('click', () => {
            renderGraduationTable(allGraduationData);
        });
    }
    
    // Status filter
    const statusForm = document.querySelector('.inner-change-status');
    if (statusForm) {
        statusForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const select = statusForm.querySelector('select');
            const selectedStatus = select.value;
            
            if (!selectedStatus || selectedStatus === 'Tất cả') {
                renderGraduationTable(allGraduationData);
                return;
            }
            
            const filtered = allGraduationData.filter(s => {
                if (selectedStatus === 'Đúng hạn') {
                    return s.canGraduate || s.remainingCredits <= 15;
                } else if (selectedStatus === 'Nguy cơ trễ tiến độ') {
                    return s.remainingCredits > 30;
                } else if (selectedStatus === 'Tốt nghiệp sớm') {
                    return s.canGraduate && s.gpa >= 3.6;
                } else if (selectedStatus === 'Chưa đủ điều kiện tốt nghiệp') {
                    return !s.canGraduate && s.remainingCredits === 0;
                }
                return true;
            });
            
            renderGraduationTable(filtered);
        });
    }
    
    // Search form
    const searchForm = document.querySelector('.inner-search');
    if (searchForm) {
        const searchInput = searchForm.querySelector('input');
        searchForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const searchTerm = searchInput.value.toLowerCase().trim();
            
            if (!searchTerm) {
                renderGraduationTable(allGraduationData);
                return;
            }
            
            const filtered = allGraduationData.filter(s => 
                s.studentCode.toLowerCase().includes(searchTerm) ||
                s.name.toLowerCase().includes(searchTerm)
            );
            
            renderGraduationTable(filtered);
        });
    }
}

// =============================================
// VIEW GRADUATION PATH
// =============================================

async function viewGraduationPath(studentId) {
    try {
        const student = await API.getStudentReport(studentId);
        
        let message = `
Lộ trình tốt nghiệp - ${student.studentName}

MSSV: ${student.studentCode}
Khoa: ${student.departmentName}
Lớp: ${student.className}

TIẾN ĐỘ HỌC TẬP:
- Tín chỉ đã hoàn thành: ${student.graduationStatus.completedCredits}/${student.graduationStatus.requiredCredits}
- Tín chỉ còn thiếu: ${student.graduationStatus.remainingCredits}
- GPA hiện tại: ${API.formatGPA(student.academicPerformance.overallGPA)}
- GPA yêu cầu: ${student.graduationStatus.requiredGPA}

DỰ KIẾN TỐT NGHIỆP:
- Học kỳ dự kiến: ${student.graduationStatus.estimatedGraduationTerm}
- Số học kỳ còn lại: ${student.graduationStatus.remainingTerms}

TRẠNG THÁI:
${student.graduationStatus.canGraduate ? '✅ Đủ điều kiện tốt nghiệp' : '❌ Chưa đủ điều kiện'}
`;

        if (student.graduationStatus.missingRequirements.length > 0) {
            message += `\nYÊU CẦU CÒN THIẾU:\n`;
            student.graduationStatus.missingRequirements.forEach(req => {
                message += `- ${req}\n`;
            });
        }
        
        message += `\nCHỨNG CHỈ: ${student.certificates.length} chứng chỉ`;
        message += `\nCẢNH BÁO: ${student.academicWarnings.length} cảnh báo`;
        
        alert(message);
    } catch (error) {
        console.error('Error loading graduation path:', error);
        alert('Không thể tải lộ trình tốt nghiệp');
    }
}

// Make function available globally
window.viewGraduationPath = viewGraduationPath;

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
