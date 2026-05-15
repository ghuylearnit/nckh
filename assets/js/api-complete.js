// =============================================
// API CONFIGURATION
// =============================================

const API_BASE_URL = 'http://localhost:8081/api';

// =============================================
// API HELPER FUNCTIONS
// =============================================

/**
 * Fetch data from API with comprehensive error handling
 * @param {string} endpoint - API endpoint
 * @param {Object} options - Fetch options (method, headers, body)
 * @returns {Promise} - API response
 */
async function fetchAPI(endpoint, options = {}) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: options.method || 'GET',
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            body: options.body ? JSON.stringify(options.body) : undefined
        });

        if (!response.ok) {
            // Handle different HTTP error codes
            switch (response.status) {
                case 400:
                    throw new Error('Dữ liệu không hợp lệ (Bad Request)');
                case 401:
                    throw new Error('Chưa đăng nhập (Unauthorized)');
                case 403:
                    throw new Error('Không có quyền truy cập (Forbidden)');
                case 404:
                    throw new Error('Không tìm thấy dữ liệu (Not Found)');
                case 500:
                    throw new Error('Lỗi server (Internal Server Error)');
                default:
                    throw new Error(`HTTP error! status: ${response.status}`);
            }
        }

        return await response.json();
    } catch (error) {
        console.error(`Error fetching ${endpoint}:`, error);
        throw error;
    }
}

// =============================================
// STUDENT MANAGEMENT APIs
// =============================================

/**
 * Get all students
 * @returns {Promise<Array>} - List of all students
 */
async function getAllStudents() {
    return await fetchAPI('/students');
}

/**
 * Get student transcript by ID
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} - Student transcript data
 */
async function getStudentTranscript(studentId) {
    return await fetchAPI(`/students/${studentId}/transcript`);
}

/**
 * Get graduation status for all students
 * @returns {Promise<Array>} - List of students with graduation status
 */
async function getGraduationStatus() {
    return await fetchAPI('/students/graduation-status');
}

/**
 * Get student by ID
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} - Student data
 */
async function getStudentById(studentId) {
    return await fetchAPI(`/students/${studentId}`);
}

// =============================================
// ENROLLMENT MANAGEMENT APIs
// =============================================

/**
 * Register for a course
 * @param {Object} enrollmentData - Enrollment data {studentId, courseId, termId}
 * @returns {Promise<Object>} - Enrollment result
 */
async function registerCourse(enrollmentData) {
    return await fetchAPI('/enrollments/register', {
        method: 'POST',
        body: enrollmentData
    });
}

/**
 * Withdraw from a course
 * @param {number} enrollmentId - Enrollment ID
 * @returns {Promise<Object>} - Withdrawal result
 */
async function withdrawCourse(enrollmentId) {
    return await fetchAPI(`/enrollments/${enrollmentId}`, {
        method: 'DELETE'
    });
}

/**
 * Get student enrollments
 * @param {number} studentId - Student ID
 * @returns {Promise<Array>} - List of enrollments
 */
async function getStudentEnrollments(studentId) {
    return await fetchAPI(`/enrollments/student/${studentId}`);
}

// =============================================
// DASHBOARD STATISTICS APIs
// =============================================

/**
 * Get students grouped by status
 * @returns {Promise<Object>} - Students by status (active, graduated, suspended, etc.)
 */
async function getStudentsByStatus() {
    return await fetchAPI('/dashboard/students-by-status');
}

/**
 * Get students grouped by department
 * @returns {Promise<Object>} - Students by department
 */
async function getStudentsByDepartment() {
    return await fetchAPI('/dashboard/students-by-department');
}

/**
 * Get overall dashboard statistics
 * @returns {Promise<Object>} - Overall stats (total students, avg GPA, etc.)
 */
async function getDashboardStats() {
    return await fetchAPI('/dashboard/stats');
}

/**
 * Get GPA distribution data
 * @returns {Promise<Object>} - GPA distribution (ranges and counts)
 */
async function getGPADistribution() {
    return await fetchAPI('/dashboard/gpa-distribution');
}

// =============================================
// DEPARTMENT MANAGEMENT APIs
// =============================================

/**
 * Get all departments
 * @returns {Promise<Array>} - List of all departments
 */
async function getAllDepartments() {
    return await fetchAPI('/departments');
}

/**
 * Get department by ID
 * @param {number} departmentId - Department ID
 * @returns {Promise<Object>} - Department data
 */
async function getDepartmentById(departmentId) {
    return await fetchAPI(`/departments/${departmentId}`);
}

/**
 * Count total departments
 * @returns {Promise<number>} - Total number of departments
 */
async function countDepartments() {
    return await fetchAPI('/departments/count');
}

/**
 * Get department by code
 * @param {string} code - Department code (e.g., "CNTT", "QTKD")
 * @returns {Promise<Object>} - Department data
 */
async function getDepartmentByCode(code) {
    return await fetchAPI(`/departments/code/${code}`);
}

// =============================================
// GRADE MANAGEMENT APIs
// =============================================

/**
 * Get all grades for a student
 * @param {number} studentId - Student ID
 * @returns {Promise<Array>} - List of grades
 */
async function getStudentGrades(studentId) {
    return await fetchAPI(`/grades/student/${studentId}`);
}

/**
 * Get student grade transcript
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} - Complete transcript with all grades
 */
async function getStudentGradeTranscript(studentId) {
    return await fetchAPI(`/grades/student/${studentId}/transcript`);
}

/**
 * Get student grades by term
 * @param {number} studentId - Student ID
 * @param {number} termId - Term ID
 * @returns {Promise<Array>} - Grades for specific term
 */
async function getGradesByTerm(studentId, termId) {
    return await fetchAPI(`/grades/student/${studentId}/term/${termId}`);
}

/**
 * Get student GPA
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} - GPA data (overall, term, cumulative)
 */
async function getStudentGPA(studentId) {
    return await fetchAPI(`/grades/student/${studentId}/gpa`);
}

// =============================================
// REPORT GENERATION APIs
// =============================================

/**
 * Generate student report
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} - Comprehensive student report
 */
async function getStudentReport(studentId) {
    return await fetchAPI(`/reports/student/${studentId}`);
}

/**
 * Generate department report
 * @param {number} departmentId - Department ID
 * @returns {Promise<Object>} - Comprehensive department report
 */
async function getDepartmentReport(departmentId) {
    return await fetchAPI(`/reports/department/${departmentId}`);
}

// =============================================
// GRADUATION PREDICTION APIs
// =============================================

/**
 * Predict graduation timeline for student
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} - Graduation prediction (estimated term, probability)
 */
async function predictGraduation(studentId) {
    return await fetchAPI(`/graduation/student/${studentId}/prediction`);
}

/**
 * Get missing subjects for graduation
 * @param {number} studentId - Student ID
 * @returns {Promise<Array>} - List of missing required subjects
 */
async function getMissingSubjects(studentId) {
    return await fetchAPI(`/graduation/student/${studentId}/missing-subjects`);
}

/**
 * Check graduation requirements
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} - Graduation check result (eligible, missing credits, etc.)
 */
async function checkGraduationRequirements(studentId) {
    return await fetchAPI(`/graduation/student/${studentId}/check`);
}

// =============================================
// ACADEMIC WARNING APIs
// =============================================

/**
 * Check academic warning status for student
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} - Warning status and details
 */
async function checkAcademicWarning(studentId) {
    return await fetchAPI(`/academic-warning/student/${studentId}/check`);
}

/**
 * Get list of at-risk students
 * @returns {Promise<Array>} - List of students at academic risk
 */
async function getAtRiskStudents() {
    return await fetchAPI('/academic-warning/at-risk-students');
}

// =============================================
// COURSE MANAGEMENT APIs
// =============================================

/**
 * Get all courses
 * @returns {Promise<Array>} - List of all courses
 */
async function getAllCourses() {
    return await fetchAPI('/courses');
}

/**
 * Get course by ID
 * @param {number} courseId - Course ID
 * @returns {Promise<Object>} - Course data
 */
async function getCourseById(courseId) {
    return await fetchAPI(`/courses/${courseId}`);
}

/**
 * Count total courses
 * @returns {Promise<number>} - Total number of courses
 */
async function countCourses() {
    return await fetchAPI('/courses/count');
}

/**
 * Get course by code
 * @param {string} code - Course code (e.g., "CS101", "MATH201")
 * @returns {Promise<Object>} - Course data
 */
async function getCourseByCode(code) {
    return await fetchAPI(`/courses/code/${code}`);
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
 * Get severity icon HTML based on GPA
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

/**
 * Show error message to user
 * @param {string} message - Error message
 */
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

/**
 * Show success message to user
 * @param {string} message - Success message
 */
function showSuccess(message) {
    const main = document.querySelector('.main');
    if (main) {
        const successDiv = document.createElement('div');
        successDiv.className = 'alert alert-success';
        successDiv.style.cssText = 'background: #4CAF50; color: white; padding: 15px; margin: 20px 0; border-radius: 4px;';
        successDiv.innerHTML = `
            <i class="fa-solid fa-check-circle"></i>
            <strong>Thành công:</strong> ${message}
        `;
        main.insertBefore(successDiv, main.firstChild);
        
        // Auto remove after 3 seconds
        setTimeout(() => {
            successDiv.remove();
        }, 3000);
    }
}

/**
 * Show loading indicator
 * @param {string} message - Loading message
 * @returns {HTMLElement} - Loading element (to remove later)
 */
function showLoading(message = 'Đang tải dữ liệu...') {
    const main = document.querySelector('.main');
    if (main) {
        const loadingDiv = document.createElement('div');
        loadingDiv.className = 'alert alert-info';
        loadingDiv.style.cssText = 'background: #2196F3; color: white; padding: 15px; margin: 20px 0; border-radius: 4px;';
        loadingDiv.innerHTML = `
            <i class="fa-solid fa-spinner fa-spin"></i>
            <strong>${message}</strong>
        `;
        main.insertBefore(loadingDiv, main.firstChild);
        return loadingDiv;
    }
    return null;
}

/**
 * Hide loading indicator
 * @param {HTMLElement} loadingElement - Loading element to remove
 */
function hideLoading(loadingElement) {
    if (loadingElement) {
        loadingElement.remove();
    }
}

// =============================================
// EXPORT FOR USE IN OTHER FILES
// =============================================

// Make all functions available globally
window.API = {
    // Student APIs
    getAllStudents,
    getStudentById,
    getStudentTranscript,
    getGraduationStatus,
    
    // Enrollment APIs
    registerCourse,
    withdrawCourse,
    getStudentEnrollments,
    
    // Dashboard APIs
    getStudentsByStatus,
    getStudentsByDepartment,
    getDashboardStats,
    getGPADistribution,
    
    // Department APIs
    getAllDepartments,
    getDepartmentById,
    countDepartments,
    getDepartmentByCode,
    
    // Grade APIs
    getStudentGrades,
    getStudentGradeTranscript,
    getGradesByTerm,
    getStudentGPA,
    
    // Report APIs
    getStudentReport,
    getDepartmentReport,
    
    // Graduation APIs
    predictGraduation,
    getMissingSubjects,
    checkGraduationRequirements,
    
    // Academic Warning APIs
    checkAcademicWarning,
    getAtRiskStudents,
    
    // Course APIs
    getAllCourses,
    getCourseById,
    countCourses,
    getCourseByCode,
    
    // Utility functions
    formatGPA,
    getStatusBadge,
    getSeverityIcon,
    calculateProgress,
    getGraduationStatusTag,
    showError,
    showSuccess,
    showLoading,
    hideLoading
};

console.log('API Module loaded successfully!');
