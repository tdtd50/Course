package com.zjsu.cxt.course.repository;

import com.zjsu.cxt.course.model.Enrollment;
import com.zjsu.cxt.course.model.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // 按课程ID查询 - 返回列表
    List<Enrollment> findByCourseId(Long courseId);

    // 按学生ID查询 - 返回列表
    List<Enrollment> findByStudentId(Long studentId);

    // 按状态查询 - 返回列表
    List<Enrollment> findByStatus(EnrollmentStatus status);

    // 按课程和学生查询 - 这里应该返回 Optional，因为课程+学生组合应该是唯一的
    Optional<Enrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);

    // 按课程和状态查询 - 返回列表
    List<Enrollment> findByCourseIdAndStatus(Long courseId, EnrollmentStatus status);

    // 统计课程活跃人数
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId AND e.status = 'ACTIVE'")
    Long countActiveEnrollmentsByCourseId(@Param("courseId") Long courseId);

    // 判断学生是否已选课
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId AND e.status = 'ACTIVE'")
    boolean existsActiveEnrollment(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // 查询学生的活跃选课 - 返回列表
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.status = 'ACTIVE'")
    List<Enrollment> findActiveEnrollmentsByStudentId(@Param("studentId") Long studentId);
}