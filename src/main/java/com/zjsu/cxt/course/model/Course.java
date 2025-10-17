package com.zjsu.cxt.course.model;

import java.util.UUID;

public class Course {
    private String id;
    private String code;        // 课程代码：CS101
    private String title;       // 课程名称
    private Instructor instructor; // 授课教师
    private ScheduleSlot schedule; // 课程安排
    private Integer capacity;   // 课程容量
    private Integer enrolled;   // 已选人数

    // 构造方法
    public Course() {
        this.enrolled = 0; // 默认已选人数为0
    }

    public Course(String code, String title, Instructor instructor,
                  ScheduleSlot schedule, Integer capacity) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.title = title;
        this.instructor = instructor;
        this.schedule = schedule;
        this.capacity = capacity;
        this.enrolled = 0;
    }

    // Getter 和 Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public ScheduleSlot getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleSlot schedule) {
        this.schedule = schedule;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Integer enrolled) {
        this.enrolled = enrolled;
    }

    // 增加选课人数
    public void incrementEnrolled() {
        this.enrolled++;
    }

    // 减少选课人数
    public void decrementEnrolled() {
        if (this.enrolled > 0) {
            this.enrolled--;
        }
    }

    // 检查是否还有空位
    public boolean hasAvailableSeats() {
        return enrolled < capacity;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", instructor=" + instructor +
                ", schedule=" + schedule +
                ", capacity=" + capacity +
                ", enrolled=" + enrolled +
                '}';
    }
}