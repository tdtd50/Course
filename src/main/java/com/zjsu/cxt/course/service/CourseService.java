package com.zjsu.cxt.course.service;

import com.zjsu.cxt.course.model.Course;
import com.zjsu.cxt.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // 创建课程
    public Course createCourse(Course course) {
        // 验证必填字段
        validateCourseFields(course);

        return courseRepository.save(course);
    }

    // 获取所有课程
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 根据ID获取课程
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("课程不存在，ID: " + id));
    }

    // 根据课程代码获取课程
    public Course getCourseByCode(String code) {
        return courseRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("课程不存在，代码: " + code));
    }

    // 更新课程信息
    public Course updateCourse(String id, Course course) {
        // 检查课程是否存在
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("课程不存在，ID: " + id);
        }

        // 验证必填字段
        validateCourseFields(course);

        // 设置ID确保更新的是正确的课程
        course.setId(id);

        return courseRepository.update(course);
    }

    // 删除课程
    public void deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("课程不存在，ID: " + id);
        }

        // 注意：这里先不检查选课记录，等 EnrollmentService 实现后再添加
        courseRepository.deleteById(id);
    }

    // 验证课程字段
    private void validateCourseFields(Course course) {
        if (course.getCode() == null || course.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("课程代码不能为空");
        }
        if (course.getTitle() == null || course.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("课程名称不能为空");
        }
        if (course.getInstructor() == null) {
            throw new IllegalArgumentException("授课教师不能为空");
        }
        if (course.getInstructor().getId() == null || course.getInstructor().getId().trim().isEmpty()) {
            throw new IllegalArgumentException("教师ID不能为空");
        }
        if (course.getInstructor().getName() == null || course.getInstructor().getName().trim().isEmpty()) {
            throw new IllegalArgumentException("教师姓名不能为空");
        }
        if (course.getSchedule() == null) {
            throw new IllegalArgumentException("课程安排不能为空");
        }
        if (course.getCapacity() == null || course.getCapacity() <= 0) {
            throw new IllegalArgumentException("课程容量必须大于0");
        }
    }

    // 检查课程是否存在
    public boolean courseExists(String id) {
        return courseRepository.existsById(id);
    }

    // 增加课程选课人数
    public void incrementEnrollment(String courseId) {
        Course course = getCourseById(courseId);
        course.incrementEnrolled();
        courseRepository.update(course);
    }

    // 减少课程选课人数
    public void decrementEnrollment(String courseId) {
        Course course = getCourseById(courseId);
        course.decrementEnrolled();
        courseRepository.update(course);
    }

    // 检查课程是否有空位
    public boolean hasAvailableSeats(String courseId) {
        Course course = getCourseById(courseId);
        return course.hasAvailableSeats();
    }
}