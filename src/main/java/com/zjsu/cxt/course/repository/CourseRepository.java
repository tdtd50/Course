package com.zjsu.cxt.course.repository;

import com.zjsu.cxt.course.model.Course;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CourseRepository {

    // 使用 ConcurrentHashMap 在内存中存储课程数据
    private final Map<String, Course> courses = new ConcurrentHashMap<>();

    // 按课程代码索引，用于检查课程代码是否重复
    private final Map<String, String> courseCodeIndex = new ConcurrentHashMap<>();

    // 保存课程
    public Course save(Course course) {
        // 检查课程代码是否已存在
        if (courseCodeIndex.containsKey(course.getCode())) {
            throw new IllegalArgumentException("课程代码已存在: " + course.getCode());
        }

        // 如果课程没有ID，生成一个
        if (course.getId() == null || course.getId().trim().isEmpty()) {
            course.setId(UUID.randomUUID().toString());
        }

        // 保存到主存储
        courses.put(course.getId(), course);
        // 更新课程代码索引
        courseCodeIndex.put(course.getCode(), course.getId());

        return course;
    }

    // 根据ID查找课程
    public Optional<Course> findById(String id) {
        return Optional.ofNullable(courses.get(id));
    }

    // 根据课程代码查找课程
    public Optional<Course> findByCode(String code) {
        String id = courseCodeIndex.get(code);
        if (id != null) {
            return Optional.ofNullable(courses.get(id));
        }
        return Optional.empty();
    }

    // 查找所有课程
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    // 更新课程信息
    public Course update(Course course) {
        String id = course.getId();
        if (!courses.containsKey(id)) {
            throw new IllegalArgumentException("课程不存在，ID: " + id);
        }

        Course existingCourse = courses.get(id);
        String oldCode = existingCourse.getCode();
        String newCode = course.getCode();

        // 如果课程代码发生变化，检查新代码是否已被使用
        if (!oldCode.equals(newCode) && courseCodeIndex.containsKey(newCode)) {
            throw new IllegalArgumentException("课程代码已存在: " + newCode);
        }

        // 更新课程代码索引
        if (!oldCode.equals(newCode)) {
            courseCodeIndex.remove(oldCode);
            courseCodeIndex.put(newCode, id);
        }

        // 更新课程信息（保留原有的已选人数）
        course.setEnrolled(existingCourse.getEnrolled());
        courses.put(id, course);

        return course;
    }

    // 删除课程
    public boolean deleteById(String id) {
        Course course = courses.get(id);
        if (course != null) {
            // 从课程代码索引中移除
            courseCodeIndex.remove(course.getCode());
            // 从主存储中移除
            courses.remove(id);
            return true;
        }
        return false;
    }

    // 检查课程是否存在
    public boolean existsById(String id) {
        return courses.containsKey(id);
    }

    // 检查课程代码是否存在
    public boolean existsByCode(String code) {
        return courseCodeIndex.containsKey(code);
    }

    // 获取课程数量（用于测试）
    public int count() {
        return courses.size();
    }
}