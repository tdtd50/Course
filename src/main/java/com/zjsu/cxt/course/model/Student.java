package com.zjsu.cxt.course.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Student {
    // 私有属性
    private String id;              // 系统自动生成 UUID
    private String studentId;       // 学号，必须全局唯一
    private String name;            // 学生姓名
    private String major;           // 专业名称
    private Integer grade;          // 入学年份
    private String email;           // 邮箱地址
    private LocalDateTime createdAt; // 创建时间戳


    public Student(String studentId, String name, String major, Integer grade, String email) {
        this.id = UUID.randomUUID().toString();
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.grade = grade;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}