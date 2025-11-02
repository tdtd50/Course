package com.zjsu.cxt.course.repository;

import com.zjsu.cxt.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // 按课程代码查询
    Optional<Course> findByCourseCode(String courseCode);

    // 按讲师ID查询
    @Query("SELECT c FROM Course c WHERE c.instructor.instructorId = :instructorId")
    List<Course> findByInstructorId(@Param("instructorId") String instructorId);

    // 查询有剩余容量的课程
    @Query("SELECT c FROM Course c WHERE c.enrolledCount < c.capacity")
    List<Course> findAvailableCourses();

    // 标题关键字模糊查询
    List<Course> findByTitleContainingIgnoreCase(String keyword);

    // 检查课程代码是否存在
    boolean existsByCourseCode(String courseCode);
}