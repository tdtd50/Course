package com.zjsu.cxt.course.repository;

import com.zjsu.cxt.course.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 按学号查询
    Optional<Student> findByStudentId(String studentId);

    // 按邮箱查询
    Optional<Student> findByEmail(String email);

    // 按专业查询
    List<Student> findByMajor(String major);

    // 按年级查询
    List<Student> findByGrade(String grade);

    // 检查学号是否存在
    boolean existsByStudentId(String studentId);

    // 检查邮箱是否存在
    boolean existsByEmail(String email);

    // 按专业和年级查询
    @Query("SELECT s FROM Student s WHERE s.major = :major AND s.grade = :grade")
    List<Student> findByMajorAndGrade(@Param("major") String major, @Param("grade") String grade);
}