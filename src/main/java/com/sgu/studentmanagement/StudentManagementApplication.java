package com.sgu.studentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * SGU Student Management System
 * Main Application Class
 * 
 * @author SGU Development Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
        System.out.println("\n" +
                "==============================================\n" +
                "  SGU Student Management System Started!\n" +
                "  Swagger UI: http://localhost:8081/swagger-ui.html\n" +
                "  API Docs: http://localhost:8081/api-docs\n" +
                "==============================================\n");
    }
}
