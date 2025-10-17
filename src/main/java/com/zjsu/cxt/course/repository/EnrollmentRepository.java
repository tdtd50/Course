package com.zjsu.cxt.course.repository;

import com.zjsu.cxt.course.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepository {

    // 使用 ConcurrentHashMap 在内存中存储选课数据
    private final Map<String, Enrollment> enrollments = new ConcurrentHashMap<>();

    // 按课程ID索引，用于快速查询某课程的选课记录
    private final Map<String, Set<String>> courseEnrollments = new ConcurrentHashMap<>();

    // 按学生ID索引，用于快速查询某学生的选课记录
    private final Map<String, Set<String>> studentEnrollments = new ConcurrentHashMap<>();

    // 按课程ID和学生ID联合索引，用于检查重复选课
    private final Map<String, Set<String>> courseStudentIndex = new ConcurrentHashMap<>();

    // 保存选课记录
    public Enrollment save(Enrollment enrollment) {
        // 如果选课记录没有ID，生成一个
        if (enrollment.getId() == null || enrollment.getId().trim().isEmpty()) {
            enrollment.setId(UUID.randomUUID().toString());
        }

        // 保存到主存储
        enrollments.put(enrollment.getId(), enrollment);

        // 更新课程索引
        courseEnrollments
                .computeIfAbsent(enrollment.getCourseId(), k -> ConcurrentHashMap.newKeySet())
                .add(enrollment.getId());

        // 更新学生索引
        studentEnrollments
                .computeIfAbsent(enrollment.getStudentId(), k -> ConcurrentHashMap.newKeySet())
                .add(enrollment.getId());

        // 更新课程-学生联合索引
        String courseStudentKey = enrollment.getCourseId() + ":" + enrollment.getStudentId();
        courseStudentIndex
                .computeIfAbsent(courseStudentKey, k -> ConcurrentHashMap.newKeySet())
                .add(enrollment.getId());

        return enrollment;
    }

    // 根据ID查找选课记录
    public Optional<Enrollment> findById(String id) {
        return Optional.ofNullable(enrollments.get(id));
    }

    // 查找所有选课记录
    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments.values());
    }

    // 根据课程ID查找选课记录
    public List<Enrollment> findByCourseId(String courseId) {
        Set<String> enrollmentIds = courseEnrollments.get(courseId);
        if (enrollmentIds == null) {
            return new ArrayList<>();
        }
        return enrollmentIds.stream()
                .map(enrollments::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // 根据学生ID查找选课记录
    public List<Enrollment> findByStudentId(String studentId) {
        Set<String> enrollmentIds = studentEnrollments.get(studentId);
        if (enrollmentIds == null) {
            return new ArrayList<>();
        }
        return enrollmentIds.stream()
                .map(enrollments::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // 检查学生是否已经选了某门课程
    public boolean existsByCourseIdAndStudentId(String courseId, String studentId) {
        String courseStudentKey = courseId + ":" + studentId;
        Set<String> enrollmentIds = courseStudentIndex.get(courseStudentKey);
        if (enrollmentIds == null) {
            return false;
        }
        // 检查是否有活跃的选课记录（状态为ENROLLED）
        return enrollmentIds.stream()
                .map(enrollments::get)
                .filter(Objects::nonNull)
                .anyMatch(enrollment -> "ENROLLED".equals(enrollment.getStatus()));
    }

    // 获取某课程的选课人数
    public int countByCourseId(String courseId) {
        Set<String> enrollmentIds = courseEnrollments.get(courseId);
        if (enrollmentIds == null) {
            return 0;
        }
        // 只统计活跃的选课记录
        return (int) enrollmentIds.stream()
                .map(enrollments::get)
                .filter(Objects::nonNull)
                .filter(enrollment -> "ENROLLED".equals(enrollment.getStatus()))
                .count();
    }

    // 获取某学生的选课数量
    public int countByStudentId(String studentId) {
        Set<String> enrollmentIds = studentEnrollments.get(studentId);
        if (enrollmentIds == null) {
            return 0;
        }
        // 只统计活跃的选课记录
        return (int) enrollmentIds.stream()
                .map(enrollments::get)
                .filter(Objects::nonNull)
                .filter(enrollment -> "ENROLLED".equals(enrollment.getStatus()))
                .count();
    }

    // 删除选课记录（软删除，将状态改为DROPPED）
    public boolean deleteById(String id) {
        Enrollment enrollment = enrollments.get(id);
        if (enrollment != null) {
            enrollment.setStatus("DROPPED");
            return true;
        }
        return false;
    }

    // 硬删除选课记录（从所有索引中移除）
    public boolean hardDeleteById(String id) {
        Enrollment enrollment = enrollments.get(id);
        if (enrollment != null) {
            // 从主存储中移除
            enrollments.remove(id);

            // 从课程索引中移除
            Set<String> courseEnrollmentSet = courseEnrollments.get(enrollment.getCourseId());
            if (courseEnrollmentSet != null) {
                courseEnrollmentSet.remove(id);
            }

            // 从学生索引中移除
            Set<String> studentEnrollmentSet = studentEnrollments.get(enrollment.getStudentId());
            if (studentEnrollmentSet != null) {
                studentEnrollmentSet.remove(id);
            }

            // 从课程-学生联合索引中移除
            String courseStudentKey = enrollment.getCourseId() + ":" + enrollment.getStudentId();
            Set<String> courseStudentSet = courseStudentIndex.get(courseStudentKey);
            if (courseStudentSet != null) {
                courseStudentSet.remove(id);
            }

            return true;
        }
        return false;
    }

    // 检查选课记录是否存在
    public boolean existsById(String id) {
        return enrollments.containsKey(id);
    }
}