package com.zjsu.cxt.course.model;

public class ScheduleSlot {
    private String dayOfWeek;  // 星期几：MONDAY, TUESDAY, etc.
    private String startTime;  // 开始时间：HH:mm
    private String endTime;    // 结束时间：HH:mm
    private Integer expectedAttendance; // 预计出勤人数

    // 构造方法
    public ScheduleSlot() {
    }

    public ScheduleSlot(String dayOfWeek, String startTime, String endTime, Integer expectedAttendance) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expectedAttendance = expectedAttendance;
    }

    // Getter 和 Setter
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getExpectedAttendance() {
        return expectedAttendance;
    }

    public void setExpectedAttendance(Integer expectedAttendance) {
        this.expectedAttendance = expectedAttendance;
    }

    @Override
    public String toString() {
        return "ScheduleSlot{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", expectedAttendance=" + expectedAttendance +
                '}';
    }
}