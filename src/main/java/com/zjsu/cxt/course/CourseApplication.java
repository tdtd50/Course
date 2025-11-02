package com.zjsu.cxt.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.zjsu.cxt.course")
@EntityScan("com.zjsu.cxt.course.model")
@EnableJpaRepositories("com.zjsu.cxt.course.repository")
@EnableTransactionManagement
public class CourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }
}