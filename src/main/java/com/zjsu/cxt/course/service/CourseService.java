package com.zjsu.cxt.course.service;

import com.zjsu.cxt.course.model.Course;
import com.zjsu.cxt.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    public Course createCourse(Course course) {
        // 检查课程代码是否已存在
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new IllegalArgumentException("课程代码已存在: " + course.getCourseCode());
        }
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // 检查课程代码是否被修改且是否冲突
            if (!course.getCourseCode().equals(courseDetails.getCourseCode()) &&
                    courseRepository.existsByCourseCode(courseDetails.getCourseCode())) {
                throw new IllegalArgumentException("课程代码已存在: " + courseDetails.getCourseCode());
            }

            course.setCourseCode(courseDetails.getCourseCode());
            course.setTitle(courseDetails.getTitle());
            course.setDescription(courseDetails.getDescription());
            course.setCapacity(courseDetails.getCapacity());
            course.setInstructor(courseDetails.getInstructor());
            course.setSchedule(courseDetails.getSchedule());

            return courseRepository.save(course);
        }
        return null;
    }

    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Course> getAvailableCourses() {
        return courseRepository.findAvailableCourses();
    }

    public List<Course> searchCoursesByTitle(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Course> getCoursesByInstructor(String instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
}