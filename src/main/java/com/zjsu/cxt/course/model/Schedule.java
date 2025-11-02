package com.zjsu.cxt.course.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Schedule {
    @Column(name = "schedule_day", length = 10)
    private String dayOfWeek;

    @Column(name = "start_time", length = 10)
    private String startTime;

    @Column(name = "end_time", length = 10)
    private String endTime;

    @Column(name = "classroom", length = 50)
    private String classroom;

    public Schedule() {}

    public Schedule(String dayOfWeek, String startTime, String endTime, String classroom) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
    }

    // Getter和Setter方法
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getClassroom() { return classroom; }
    public void setClassroom(String classroom) { this.classroom = classroom; }
}