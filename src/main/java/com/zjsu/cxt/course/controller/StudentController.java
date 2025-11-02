package com.zjsu.cxt.course.controller;

import com.zjsu.cxt.course.model.ApiResponse;
import com.zjsu.cxt.course.model.Student;
import com.zjsu.cxt.course.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(student -> ResponseEntity.ok(ApiResponse.success(student)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((ApiResponse<Student>) ApiResponse.error(404, "Student not found")));
    }

    @GetMapping("/student-id/{studentId}")
    public ResponseEntity<ApiResponse<Student>> getStudentByStudentId(@PathVariable String studentId) {
        return studentService.getStudentByStudentId(studentId)
                .map(student -> ResponseEntity.ok(ApiResponse.success(student)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((ApiResponse<Student>) ApiResponse.error(404, "Student not found")));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdStudent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body((ApiResponse<Student>) ApiResponse.error(409, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable Long id,
                                                              @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            if (updatedStudent != null) {
                return ResponseEntity.ok(ApiResponse.success(updatedStudent));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((ApiResponse<Student>) ApiResponse.error(404, "Student not found"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body((ApiResponse<Student>) ApiResponse.error(409, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Student not found"));
        }
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<ApiResponse<List<Student>>> getStudentsByMajor(@PathVariable String major) {
        List<Student> students = studentService.getStudentsByMajor(major);
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @GetMapping("/grade/{grade}")
    public ResponseEntity<ApiResponse<List<Student>>> getStudentsByGrade(@PathVariable String grade) {
        List<Student> students = studentService.getStudentsByGrade(grade);
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Student>>> getStudentsByMajorAndGrade(
            @RequestParam String major,
            @RequestParam String grade) {
        List<Student> students = studentService.getStudentsByMajorAndGrade(major, grade);
        return ResponseEntity.ok(ApiResponse.success(students));
    }
}