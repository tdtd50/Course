package com.zjsu.cxt.course.service;

import com.zjsu.cxt.course.model.Course;
import com.zjsu.cxt.course.model.Enrollment;
import com.zjsu.cxt.course.model.Student;
import com.zjsu.cxt.course.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             CourseService courseService,
                             StudentService studentService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    // 学生选课
    public Enrollment enrollStudent(String courseId, String studentId) {
        // 1. 验证课程是否存在
        Course course = courseService.getCourseById(courseId);

        // 2. 验证学生是否存在
        Student student = studentService.getStudentById(studentId);

        // 3. 检查课程容量
        if (!courseService.hasAvailableSeats(courseId)) {
            throw new IllegalArgumentException("课程容量已满，无法选课");
        }

        // 4. 检查是否重复选课
        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new IllegalArgumentException("学生已经选了该课程，不能重复选课");
        }

        // 5. 创建选课记录
        Enrollment enrollment = new Enrollment(courseId, studentId);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // 6. 更新课程的已选人数
        courseService.incrementEnrollment(courseId);

        return savedEnrollment;
    }

    // 学生退课
    public void dropCourse(String enrollmentId) {
        // 1. 验证选课记录是否存在
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("选课记录不存在"));

        // 2. 检查选课记录状态
        if ("DROPPED".equals(enrollment.getStatus())) {
            throw new IllegalArgumentException("选课记录已处于退课状态");
        }

        // 3. 更新选课记录状态
        enrollmentRepository.deleteById(enrollmentId);

        // 4. 更新课程的已选人数
        courseService.decrementEnrollment(enrollment.getCourseId());
    }

    // 获取所有选课记录
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // 根据课程ID获取选课记录
    public List<Enrollment> getEnrollmentsByCourseId(String courseId) {
        // 验证课程是否存在
        courseService.getCourseById(courseId);
        return enrollmentRepository.findByCourseId(courseId);
    }

    // 根据学生ID获取选课记录
    public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
        // 验证学生是否存在
        studentService.getStudentById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    // 根据ID获取选课记录
    public Enrollment getEnrollmentById(String id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("选课记录不存在"));
    }

    // 检查学生是否有选课记录
    public boolean hasActiveEnrollments(String studentId) {
        return enrollmentRepository.countByStudentId(studentId) > 0;
    }

    // 获取课程的选课人数
    public int getEnrollmentCountByCourseId(String courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }
}