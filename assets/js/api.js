// =============================================
// API CONFIGURATION
// =============================================

const API_BASE_URL = 'http://localhost:8081/api';

// =============================================
// API HELPER FUNCTIONS
// =============================================

/**
 * Fetch data from API
 * @param {string} endpoint - API endpoint
 * @returns {Promise} - API response
 */
async function fetchAPI(endpoint) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error(`Error fetching ${endpoint}:`, error);
        throw error;
    }
}

// =============================================
// STUDENT REPORT API
// =============================================

/**
 * Get student report by ID
 * @param {number} studentId - Student ID
 * @returns {Promise} - Student report data
 */
async function getStudentReport(studentId) {
    return await fetchAPI(`/reports/student/${studentId}`);
}

// =============================================
// DEPARTMENT REPORT API
// =============================================

/**
 * Get department report by ID
 * @param {number} departmentId - Department ID
 * @returns {Promise} - Department report data
 */
async function getDepartmentReport(departmentId) {
    return await fetchAPI(`/reports/department/${departmentId}`);
}

// =============================================
// USER API (if available)
// =============================================

/**
 * Get all students
 * @returns {Promise} - List of students
 */
async function getAllStudents() {
    return await fetchAPI('/users?role=STUDENT');
}

/**
 * Get student by ID
 * @param {number} studentId - Student ID
 * @returns {Promise} - Student data
 */
async function getStudentById(studentId) {
    return await fetchAPI(`/users/${studentId}`);
}

// =============================================
// DEPARTMENT API (if available)
// =============================================

/**
 * Get all departments
 * @returns {Promise} - List of departments
 */
async function getAllDepartments() {
    return await fetchAPI('/departments');
}

// =============================================
// UTILITY FUNCTIONS
// =============================================

/**
 * Format GPA to 2 decimal places
 * @param {number} gpa - GPA value
 * @returns {string} - Formatted GPA
 */
function formatGPA(gpa) {
    return gpa ? gpa.toFixed(2) : '0.00';
}

/**
 * Get status badge HTML
 * @param {string} standing - Academic standing
 * @returns {string} - HTML for status badge
 */
function getStatusBadge(standing) {
    const statusMap = {
        'Xuất sắc': 'tag-green',
        'Giỏi': 'tag-blue',
        'Khá': 'tag-yellow',
        'Trung bình': 'tag-orange',
        'Yếu': 'tag-red',
        'Chưa có điểm': 'tag-grey'
    };
    
    const className = statusMap[standing] || 'tag-grey';
    return `<div class="tag ${className}">${standing}</div>`;
}

/**
 * Get severity icon HTML
 * @param {number} gpa - GPA value
 * @returns {string} - HTML for severity icon
 */
function getSeverityIcon(gpa) {
    if (gpa >= 3.6) {
        return '<div class="severity severity-green"><i class="fa-solid fa-check"></i></div>';
    } else if (gpa >= 2.5) {
        return '<div class="severity severity-blue"><i class="fa-solid fa-rotate"></i></div>';
    } else if (gpa >= 2.0) {
        return '<div class="severity severity-yellow"><i class="fa-solid fa-triangle-exclamation"></i></div>';
    } else {
        return '<div class="severity severity-red"><i class="fa-solid fa-xmark"></i></div>';
    }
}

/**
 * Calculate progress percentage
 * @param {number} completed - Completed credits
 * @param {number} required - Required credits
 * @returns {number} - Progress percentage
 */
function calculateProgress(completed, required) {
    return Math.round((completed / required) * 100);
}

/**
 * Get graduation status tag
 * @param {boolean} canGraduate - Can graduate flag
 * @param {number} remainingCredits - Remaining credits
 * @returns {string} - HTML for graduation status tag
 */
function getGraduationStatusTag(canGraduate, remainingCredits) {
    if (canGraduate) {
        return '<div class="tag tag-green">Đủ điều kiện tốt nghiệp</div>';
    } else if (remainingCredits <= 15) {
        return '<div class="tag tag-blue">Sắp tốt nghiệp</div>';
    } else if (remainingCredits <= 30) {
        return '<div class="tag tag-yellow">Đúng tiến độ</div>';
    } else {
        return '<div class="tag tag-red">Nguy cơ trễ tiến độ</div>';
    }
}

// =============================================
// EXPORT FOR USE IN OTHER FILES
// =============================================

// Make functions available globally
window.API = {
    getStudentReport,
    getDepartmentReport,
    getAllStudents,
    getStudentById,
    getAllDepartments,
    formatGPA,
    getStatusBadge,
    getSeverityIcon,
    calculateProgress,
    getGraduationStatusTag
};
