package com.zjsu.cxt.course.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Instructor {
    @Column(name = "instructor_id", length = 20)
    private String instructorId;

    @Column(name = "instructor_name", length = 50)
    private String name;

    @Column(name = "instructor_email", length = 100)
    private String email;

    @Column(name = "instructor_department", length = 50)
    private String department;

    public Instructor() {}

    public Instructor(String instructorId, String name, String email, String department) {
        this.instructorId = instructorId;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    // Getter和Setter方法
    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}