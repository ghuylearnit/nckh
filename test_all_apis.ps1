# =============================================
# TEST ALL REPORT APIs - PowerShell Script
# =============================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTING REPORT GENERATION APIs" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Base URL
$baseUrl = "http://localhost:8081"

# Function to test API endpoint
function Test-ApiEndpoint {
    param(
        [string]$url,
        [string]$description
    )
    
    Write-Host "Testing: $description" -ForegroundColor Yellow
    Write-Host "URL: $url" -ForegroundColor Gray
    
    try {
        $response = Invoke-WebRequest -Uri $url -Method Get -UseBasicParsing
        
        if ($response.StatusCode -eq 200) {
            Write-Host "✅ SUCCESS - HTTP $($response.StatusCode)" -ForegroundColor Green
            
            # Parse JSON and show key info
            $json = $response.Content | ConvertFrom-Json
            
            if ($json.studentName) {
                Write-Host "   Student: $($json.studentName)" -ForegroundColor White
                Write-Host "   GPA: $($json.academicPerformance.overallGPA)" -ForegroundColor White
                Write-Host "   Standing: $($json.academicPerformance.academicStanding)" -ForegroundColor White
                Write-Host "   Certificates: $($json.certificates.Count)" -ForegroundColor White
            }
            
            if ($json.departmentName) {
                Write-Host "   Department: $($json.departmentName)" -ForegroundColor White
                Write-Host "   Total Students: $($json.studentStatistics.totalStudents)" -ForegroundColor White
                Write-Host "   Average GPA: $($json.academicPerformance.averageGPA)" -ForegroundColor White
            }
            
            Write-Host ""
            return $true
        }
    }
    catch {
        Write-Host "❌ FAILED - $($_.Exception.Message)" -ForegroundColor Red
        Write-Host ""
        return $false
    }
}

# Test Health Check
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "1. HEALTH CHECK" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Test-ApiEndpoint "$baseUrl/actuator/health" "Application Health"

# Test Student Report APIs
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "2. STUDENT REPORT APIs" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$studentTests = @(
    @{Id=2; Name="Nguyễn Văn An (CNTT - GPA tốt)"},
    @{Id=3; Name="Trần Thị Bình (CNTT - GPA khá)"},
    @{Id=4; Name="Lê Văn Cường (CNTT - Original data)"},
    @{Id=5; Name="Phạm Thị Dung (CNTT - GPA xuất sắc)"},
    @{Id=6; Name="Hoàng Văn Em (CNTT - At risk)"},
    @{Id=7; Name="Võ Thị Giang (CNTT)"},
    @{Id=9; Name="Bùi Thị Hoa (QTKD)"},
    @{Id=10; Name="Đặng Văn Inh (QTKD)"},
    @{Id=11; Name="Trương Thị Kim (QTKD)"},
    @{Id=15; Name="Cao Thị Oanh (SPA)"},
    @{Id=16; Name="Đỗ Văn Phúc (SPA)"}
)

$successCount = 0
$failCount = 0

foreach ($test in $studentTests) {
    $result = Test-ApiEndpoint "$baseUrl/api/reports/student/$($test.Id)" $test.Name
    if ($result) { $successCount++ } else { $failCount++ }
}

# Test Department Report APIs
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "3. DEPARTMENT REPORT APIs" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$deptTests = @(
    @{Id=1; Name="Khoa Công nghệ Thông tin"},
    @{Id=2; Name="Khoa Quản trị Kinh doanh"},
    @{Id=3; Name="Khoa Sư phạm Anh ngữ"}
)

foreach ($test in $deptTests) {
    $result = Test-ApiEndpoint "$baseUrl/api/reports/department/$($test.Id)" $test.Name
    if ($result) { $successCount++ } else { $failCount++ }
}

# Summary
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TEST SUMMARY" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "✅ Passed: $successCount" -ForegroundColor Green
Write-Host "❌ Failed: $failCount" -ForegroundColor Red
Write-Host ""

if ($failCount -eq 0) {
    Write-Host "🎉 ALL TESTS PASSED! Phase 4 is COMPLETE!" -ForegroundColor Green
} else {
    Write-Host "⚠️  Some tests failed. Please check the errors above." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Press any key to exit..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
