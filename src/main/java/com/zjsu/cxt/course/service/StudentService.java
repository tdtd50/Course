package com.zjsu.cxt.course.service;

import com.zjsu.cxt.course.model.Student;
import com.zjsu.cxt.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public Student createStudent(Student student) {
        // 检查学号是否已存在
        if (studentRepository.existsByStudentId(student.getStudentId())) {
            throw new IllegalArgumentException("学号已存在: " + student.getStudentId());
        }

        // 检查邮箱是否已存在
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("邮箱已存在: " + student.getEmail());
        }

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            // 检查学号是否被修改且是否冲突
            if (!student.getStudentId().equals(studentDetails.getStudentId()) &&
                    studentRepository.existsByStudentId(studentDetails.getStudentId())) {
                throw new IllegalArgumentException("学号已存在: " + studentDetails.getStudentId());
            }

            // 检查邮箱是否被修改且是否冲突
            if (!student.getEmail().equals(studentDetails.getEmail()) &&
                    studentRepository.existsByEmail(studentDetails.getEmail())) {
                throw new IllegalArgumentException("邮箱已存在: " + studentDetails.getEmail());
            }

            student.setStudentId(studentDetails.getStudentId());
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setPhone(studentDetails.getPhone());
            student.setMajor(studentDetails.getMajor());
            student.setGrade(studentDetails.getGrade());

            return studentRepository.save(student);
        }
        return null;
    }

    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Student> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major);
    }

    public List<Student> getStudentsByGrade(String grade) {
        return studentRepository.findByGrade(grade);
    }

    public List<Student> getStudentsByMajorAndGrade(String major, String grade) {
        return studentRepository.findByMajorAndGrade(major, grade);
    }
}