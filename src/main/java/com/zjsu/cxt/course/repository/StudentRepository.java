package com.zjsu.cxt.course.repository;

import com.zjsu.cxt.course.model.Student;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StudentRepository {


    private final Map<String, Student> students = new ConcurrentHashMap<>();

    // 按学号索引，用于检查学号是否重复
    private final Map<String, String> studentIdIndex = new ConcurrentHashMap<>();

    // 保存学生
    public Student save(Student student) {
        // 检查学号是否已存在
        if (studentIdIndex.containsKey(student.getStudentId())) {
            throw new IllegalArgumentException("学号已存在: " + student.getStudentId());
        }

        // 如果学生没有ID，生成一个
        if (student.getId() == null || student.getId().trim().isEmpty()) {
            student.setId(UUID.randomUUID().toString());
        }

        // 设置创建时间
        if (student.getCreatedAt() == null) {
            student.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toLocalDateTime());
        }

        // 保存到主存储
        students.put(student.getId(), student);
        // 更新学号索引
        studentIdIndex.put(student.getStudentId(), student.getId());

        return student;
    }

    // 根据ID查找学生
    public Optional<Student> findById(String id) {
        return Optional.ofNullable(students.get(id));
    }

    // 根据学号查找学生
    public Optional<Student> findByStudentId(String studentId) {
        String id = studentIdIndex.get(studentId);
        if (id != null) {
            return Optional.ofNullable(students.get(id));
        }
        return Optional.empty();
    }

    // 查找所有学生
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }


    // 更新学生信息
    public Student update(Student student) {
        String id = student.getId();
        if (!students.containsKey(id)) {
            throw new IllegalArgumentException("学生不存在，ID: " + id);
        }

        Student existingStudent = students.get(id);
        String oldStudentId = existingStudent.getStudentId();
        String newStudentId = student.getStudentId();

        // 如果学号发生变化，检查新学号是否已被使用
        if (!oldStudentId.equals(newStudentId) && studentIdIndex.containsKey(newStudentId)) {
            throw new IllegalArgumentException("学号已存在: " + newStudentId);
        }

        // 更新学号索引
        if (!oldStudentId.equals(newStudentId)) {
            studentIdIndex.remove(oldStudentId);
            studentIdIndex.put(newStudentId, id);
        }

        // 更新学生信息（保留原有的创建时间）
        student.setCreatedAt(existingStudent.getCreatedAt());
        students.put(id, student);

        return student;
    }

    // 删除学生
    public boolean deleteById(String id) {
        Student student = students.get(id);
        if (student != null) {
            // 从学号索引中移除
            studentIdIndex.remove(student.getStudentId());
            // 从主存储中移除
            students.remove(id);
            return true;
        }
        return false;
    }

    // 检查学生是否存在
    public boolean existsById(String id) {
        return students.containsKey(id);
    }

    // 检查学号是否存在
    public boolean existsByStudentId(String studentId) {
        return studentIdIndex.containsKey(studentId);
    }

    // 获取学生数量（用于测试）
    public int count() {
        return students.size();
    }
}