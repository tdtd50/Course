package com.zjsu.cxt.course.service;

import com.zjsu.cxt.course.model.Student;
import com.zjsu.cxt.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentService enrollmentService;

    // 邮箱验证正则表达式
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Autowired
    public StudentService(StudentRepository studentRepository, @Lazy EnrollmentService enrollmentService) {
        this.studentRepository = studentRepository;
        this.enrollmentService = enrollmentService;
    }

    // 创建学生
    public Student createStudent(Student student) {
        // 验证必填字段
        validateStudentFields(student);

        // 验证邮箱格式
        if (!EMAIL_PATTERN.matcher(student.getEmail()).matches()) {
            throw new IllegalArgumentException("邮箱格式不正确");
        }

        // 检查学号是否已存在
        if (studentRepository.existsByStudentId(student.getStudentId())) {
            throw new IllegalArgumentException("学号已存在: " + student.getStudentId());
        }

        return studentRepository.save(student);
    }

    // 获取所有学生
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 根据ID获取学生
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("学生不存在，ID: " + id));
    }

    // 根据学号获取学生
    public Student getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("学生不存在，学号: " + studentId));
    }

    // 更新学生信息
    public Student updateStudent(String id, Student student) {
        // 检查学生是否存在
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("学生不存在，ID: " + id);
        }

        // 验证必填字段
        validateStudentFields(student);

        // 验证邮箱格式
        if (!EMAIL_PATTERN.matcher(student.getEmail()).matches()) {
            throw new IllegalArgumentException("邮箱格式不正确");
        }

        // 设置ID确保更新的是正确的学生
        student.setId(id);

        return studentRepository.update(student);
    }

    // 删除学生
    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("学生不存在，ID: " + id);
        }

        // 检查学生是否有活跃的选课记录
        if (enrollmentService.hasActiveEnrollments(id)) {
            throw new IllegalArgumentException("无法删除：该学生存在选课记录");
        }

        studentRepository.deleteById(id);
    }

    // 验证学生字段
    private void validateStudentFields(Student student) {
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("学号不能为空");
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("学生姓名不能为空");
        }
        if (student.getMajor() == null || student.getMajor().trim().isEmpty()) {
            throw new IllegalArgumentException("专业不能为空");
        }
        if (student.getGrade() == null) {
            throw new IllegalArgumentException("入学年份不能为空");
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("邮箱不能为空");
        }
    }

    // 检查学生是否存在
    public boolean studentExists(String id) {
        return studentRepository.existsById(id);
    }
}