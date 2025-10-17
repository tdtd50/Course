package com.zjsu.cxt.course.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Enrollment {
    private String id;
    private String courseId;    // 课程ID
    private String studentId;   // 学生ID
    private LocalDateTime enrolledAt; // 选课时间
    private String status;      // 选课状态：ENROLLED, DROPPED

    // 构造方法
    public Enrollment() {
        this.status = "ENROLLED";
        this.enrolledAt = LocalDateTime.now();
    }

    public Enrollment(String courseId, String studentId) {
        this.id = UUID.randomUUID().toString();
        this.courseId = courseId;
        this.studentId = studentId;
        this.enrolledAt = LocalDateTime.now();
        this.status = "ENROLLED";
    }

    // Getter 和 Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", enrolledAt=" + enrolledAt +
                ", status='" + status + '\'' +
                '}';
    }
}