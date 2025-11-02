package com.zjsu.cxt.course.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "student_id"),
                @UniqueConstraint(columnNames = "email")
        })
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 50)
    private String major;

    @Column(length = 20)
    private String grade;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Student() {}

    public Student(String studentId, String name, String email,
                   String phone, String major, String grade) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.major = major;
        this.grade = grade;
    }

    // Getter和Setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(Set<Enrollment> enrollments) { this.enrollments = enrollments; }
}