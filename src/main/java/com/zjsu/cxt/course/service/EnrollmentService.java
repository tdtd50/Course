package com.zjsu.cxt.course.service;

import com.zjsu.cxt.course.model.Course;
import com.zjsu.cxt.course.model.Enrollment;
import com.zjsu.cxt.course.model.EnrollmentStatus;
import com.zjsu.cxt.course.model.Student;
import com.zjsu.cxt.course.repository.CourseRepository;
import com.zjsu.cxt.course.repository.EnrollmentRepository;
import com.zjsu.cxt.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             CourseRepository courseRepository,
                             StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Enrollment> getAllEnrollments(Long courseId, Long studentId) {
        if (courseId != null && studentId != null) {
            // 修改这里：使用 findByCourseIdAndStatus 或其他方法返回 List
            return enrollmentRepository.findByCourseIdAndStatus(courseId, EnrollmentStatus.ACTIVE);
        } else if (courseId != null) {
            return enrollmentRepository.findByCourseId(courseId);
        } else if (studentId != null) {
            return enrollmentRepository.findByStudentId(studentId);
        } else {
            return enrollmentRepository.findAll();
        }
    }

    // 或者添加一个新的方法来处理课程+学生的查询
    public Optional<Enrollment> getEnrollmentByCourseAndStudent(Long courseId, Long studentId) {
        return enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId);
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public Enrollment enrollStudent(Long courseId, Long studentId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalCourse.isEmpty()) {
            throw new IllegalArgumentException("课程不存在: " + courseId);
        }

        if (optionalStudent.isEmpty()) {
            throw new IllegalArgumentException("学生不存在: " + studentId);
        }

        Course course = optionalCourse.get();
        Student student = optionalStudent.get();

        // 检查是否已选课
        if (enrollmentRepository.existsActiveEnrollment(studentId, courseId)) {
            throw new IllegalStateException("学生已选此课程");
        }

        // 检查课程容量
        if (!course.hasAvailableSeats()) {
            throw new IllegalStateException("课程容量已满");
        }

        // 创建选课记录
        Enrollment enrollment = new Enrollment(course, student);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // 更新课程选课人数
        course.incrementEnrolledCount();
        courseRepository.save(course);

        return savedEnrollment;
    }

    public Enrollment dropEnrollment(Long enrollmentId) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);
        if (optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();

            if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
                throw new IllegalStateException("选课记录不是活跃状态，无法退课");
            }

            // 更新选课状态
            enrollment.setStatus(EnrollmentStatus.DROPPED);
            Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

            // 更新课程选课人数
            Course course = enrollment.getCourse();
            course.decrementEnrolledCount();
            courseRepository.save(course);

            return updatedEnrollment;
        }
        throw new IllegalArgumentException("选课记录不存在: " + enrollmentId);
    }

    public boolean deleteEnrollment(Long id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Enrollment> getActiveEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findActiveEnrollmentsByStudentId(studentId);
    }

    public Long getActiveEnrollmentCountByCourse(Long courseId) {
        return enrollmentRepository.countActiveEnrollmentsByCourseId(courseId);
    }

    public List<Enrollment> getEnrollmentsByStatus(EnrollmentStatus status) {
        return enrollmentRepository.findByStatus(status);
    }

    // 新增方法：按课程和学生获取选课记录（返回列表）
    public List<Enrollment> getEnrollmentsByCourseAndStudent(Long courseId, Long studentId) {
        Optional<Enrollment> enrollment = enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId);
        return enrollment.map(List::of).orElse(List.of());
    }
}