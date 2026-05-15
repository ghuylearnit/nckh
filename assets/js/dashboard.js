// =============================================
// DASHBOARD PAGE - LOAD DATA FROM BACKEND
// =============================================

// Wait for DOM to be ready
document.addEventListener('DOMContentLoaded', async function() {
    console.log('Dashboard loading...');
    
    try {
        // Load department statistics
        await loadDepartmentStatistics();
        
        // Load recent activities (mock for now)
        loadRecentActivities();
        
        console.log('Dashboard loaded successfully!');
    } catch (error) {
        console.error('Error loading dashboard:', error);
        showError('Không thể tải dữ liệu dashboard. Vui lòng kiểm tra kết nối Backend.');
    }
});

// =============================================
// LOAD DEPARTMENT STATISTICS
// =============================================

async function loadDepartmentStatistics() {
    try {
        // Load all 3 departments
        const dept1 = await API.getDepartmentReport(1); // CNTT
        const dept2 = await API.getDepartmentReport(2); // QTKD
        const dept3 = await API.getDepartmentReport(3); // SPA
        
        // Calculate totals
        const totalStudents = 
            dept1.studentStatistics.totalStudents +
            dept2.studentStatistics.totalStudents +
            dept3.studentStatistics.totalStudents;
        
        const totalEligible = 
            dept1.graduationStatistics.eligibleForGraduation +
            dept2.graduationStatistics.eligibleForGraduation +
            dept3.graduationStatistics.eligibleForGraduation;
        
        const totalAtRisk = 
            dept1.atRiskStudents.length +
            dept2.atRiskStudents.length +
            dept3.atRiskStudents.length;
        
        const totalTopStudents = 
            dept1.topStudents.filter(s => s.gpa >= 3.6).length +
            dept2.topStudents.filter(s => s.gpa >= 3.6).length +
            dept3.topStudents.filter(s => s.gpa >= 3.6).length;
        
        // Update UI
        updateStatisticsCards(totalStudents, totalEligible, totalAtRisk, totalTopStudents);
        
        // Update charts (placeholder)
        updateCharts(dept1, dept2, dept3);
        
    } catch (error) {
        console.error('Error loading department statistics:', error);
        throw error;
    }
}

// =============================================
// UPDATE STATISTICS CARDS
// =============================================

function updateStatisticsCards(total, eligible, atRisk, topStudents) {
    const section1 = document.querySelector('.section-1');
    if (!section1) return;
    
    const eligiblePercent = Math.round((eligible / total) * 100);
    const atRiskPercent = Math.round((atRisk / total) * 100);
    const topPercent = Math.round((topStudents / total) * 100);
    const notEligiblePercent = 100 - eligiblePercent - atRiskPercent - topPercent;
    
    section1.innerHTML = `
        <div class="inner-item"> 
          <div class="inner-icon green"><i class="fa-solid fa-graduation-cap"></i></div>
          <div class="inner-text"> 
            <div class="inner-title">Dự kiến tốt nghiệp đúng hạn</div>
            <div class="inner-number">${eligible}</div>
            <div class="inner-note">Chiếm ${eligiblePercent}% tổng sinh viên</div>
          </div>
        </div>
        <div class="inner-item"> 
          <div class="inner-icon yellow"><i class="fa-solid fa-hourglass-half"></i></div>
          <div class="inner-text"> 
            <div class="inner-title">Nguy cơ trễ tiến độ tốt nghiệp</div>
            <div class="inner-number">${atRisk}</div>
            <div class="inner-note">Chiếm ${atRiskPercent}% tổng sinh viên</div>
          </div>
        </div>
        <div class="inner-item"> 
          <div class="inner-icon purple"><i class="fa-solid fa-rocket"></i></div>
          <div class="inner-text"> 
            <div class="inner-title">Sinh viên xuất sắc</div>
            <div class="inner-number">${topStudents}</div>
            <div class="inner-note">Chiếm ${topPercent}% tổng sinh viên</div>
          </div>
        </div>
        <div class="inner-item"> 
          <div class="inner-icon grey"><i class="fa-solid fa-shield"></i></div>
          <div class="inner-text"> 
            <div class="inner-title">Chưa đủ điều kiện tốt nghiệp</div>
            <div class="inner-number">${total - eligible - atRisk - topStudents}</div>
            <div class="inner-note">Chiếm ${notEligiblePercent}% tổng sinh viên</div>
          </div>
        </div>
    `;
}

// =============================================
// UPDATE CHARTS (PLACEHOLDER)
// =============================================

function updateCharts(dept1, dept2, dept3) {
    // Chart 1: GPA Distribution
    const chart1 = document.querySelector('.section-2 .inner-chart-box:first-child .inner-chart');
    if (chart1) {
        const avgGPA1 = dept1.academicPerformance.averageGPA.toFixed(2);
        const avgGPA2 = dept2.academicPerformance.averageGPA.toFixed(2);
        const avgGPA3 = dept3.academicPerformance.averageGPA.toFixed(2);
        
        chart1.innerHTML = `
            <div style="padding: 20px;">
                <h4>GPA Trung bình theo khoa:</h4>
                <ul style="list-style: none; padding: 0;">
                    <li style="margin: 10px 0;">
                        <strong>${dept1.departmentName}:</strong> ${avgGPA1}
                        <div style="background: #4CAF50; height: 20px; width: ${avgGPA1 * 25}%; border-radius: 4px;"></div>
                    </li>
                    <li style="margin: 10px 0;">
                        <strong>${dept2.departmentName}:</strong> ${avgGPA2}
                        <div style="background: #2196F3; height: 20px; width: ${avgGPA2 * 25}%; border-radius: 4px;"></div>
                    </li>
                    <li style="margin: 10px 0;">
                        <strong>${dept3.departmentName}:</strong> ${avgGPA3}
                        <div style="background: #FF9800; height: 20px; width: ${avgGPA3 * 25}%; border-radius: 4px;"></div>
                    </li>
                </ul>
            </div>
        `;
    }
    
    // Chart 2: Student Distribution
    const chart2 = document.querySelector('.section-2 .inner-chart-box:last-child .inner-chart');
    if (chart2) {
        const total1 = dept1.studentStatistics.totalStudents;
        const total2 = dept2.studentStatistics.totalStudents;
        const total3 = dept3.studentStatistics.totalStudents;
        const totalAll = total1 + total2 + total3;
        
        chart2.innerHTML = `
            <div style="padding: 20px;">
                <h4>Phân bố sinh viên theo khoa:</h4>
                <ul style="list-style: none; padding: 0;">
                    <li style="margin: 10px 0;">
                        <strong>${dept1.departmentName}:</strong> ${total1} SV (${Math.round(total1/totalAll*100)}%)
                        <div style="background: #4CAF50; height: 20px; width: ${total1/totalAll*100}%; border-radius: 4px;"></div>
                    </li>
                    <li style="margin: 10px 0;">
                        <strong>${dept2.departmentName}:</strong> ${total2} SV (${Math.round(total2/totalAll*100)}%)
                        <div style="background: #2196F3; height: 20px; width: ${total2/totalAll*100}%; border-radius: 4px;"></div>
                    </li>
                    <li style="margin: 10px 0;">
                        <strong>${dept3.departmentName}:</strong> ${total3} SV (${Math.round(total3/totalAll*100)}%)
                        <div style="background: #FF9800; height: 20px; width: ${total3/totalAll*100}%; border-radius: 4px;"></div>
                    </li>
                </ul>
            </div>
        `;
    }
}

// =============================================
// LOAD RECENT ACTIVITIES (MOCK DATA)
// =============================================

function loadRecentActivities() {
    // Keep existing mock data for now
    // In the future, this can be replaced with real API data
    console.log('Recent activities loaded (mock data)');
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
