package com.zjsu.cxt.course.model;

import com.zjsu.cxt.course.model.Enrollment;
import com.zjsu.cxt.course.model.Instructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses",
        uniqueConstraints = @UniqueConstraint(columnNames = "course_code"))
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false, length = 20)
    private String courseCode;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "enrolled_count")
    private Integer enrolledCount = 0;

    @Embedded
    private Instructor instructor;

    @Embedded
    private Schedule schedule;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (enrolledCount == null) {
            enrolledCount = 0;
        }
    }

    public Course() {}

    public Course(String courseCode, String title, String description,
                  Integer capacity, Instructor instructor, Schedule schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.instructor = instructor;
        this.schedule = schedule;
        this.enrolledCount = 0;
    }

    // Getter和Setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(Integer enrolledCount) { this.enrolledCount = enrolledCount; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(Set<Enrollment> enrollments) { this.enrollments = enrollments; }

    // 业务方法
    public boolean hasAvailableSeats() {
        return enrolledCount < capacity;
    }

    public void incrementEnrolledCount() {
        this.enrolledCount++;
    }

    public void decrementEnrolledCount() {
        if (this.enrolledCount > 0) {
            this.enrolledCount--;
        }
    }
}