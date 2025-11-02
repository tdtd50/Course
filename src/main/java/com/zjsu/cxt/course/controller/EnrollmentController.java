package com.zjsu.cxt.course.controller;

import com.zjsu.cxt.course.model.ApiResponse;
import com.zjsu.cxt.course.model.Enrollment;
import com.zjsu.cxt.course.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Enrollment>>> getAllEnrollments(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long studentId) {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments(courseId, studentId);
        return ResponseEntity.ok(ApiResponse.success(enrollments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id)
                .map(enrollment -> ResponseEntity.ok(ApiResponse.success(enrollment)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((ApiResponse<Enrollment>) ApiResponse.error(404, "Enrollment not found")));
    }

    @PostMapping("/enroll")
    public ResponseEntity<ApiResponse<Enrollment>> enrollStudent(@RequestBody EnrollmentRequest request) {
        try {
            Enrollment enrollment = enrollmentService.enrollStudent(request.getCourseId(), request.getStudentId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(enrollment));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body((ApiResponse<Enrollment>) ApiResponse.error(400, e.getMessage()));
        }
    }

    @PostMapping("/{id}/drop")
    public ResponseEntity<ApiResponse<Enrollment>> dropEnrollment(@PathVariable Long id) {
        try {
            Enrollment enrollment = enrollmentService.dropEnrollment(id);
            return ResponseEntity.ok(ApiResponse.success(enrollment));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body((ApiResponse<Enrollment>) ApiResponse.error(400, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteEnrollment(@PathVariable Long id) {
        boolean deleted = enrollmentService.deleteEnrollment(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Enrollment deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Enrollment not found"));
        }
    }

    @GetMapping("/student/{studentId}/active")
    public ResponseEntity<ApiResponse<List<Enrollment>>> getActiveEnrollmentsByStudent(@PathVariable Long studentId) {
        List<Enrollment> enrollments = enrollmentService.getActiveEnrollmentsByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(enrollments));
    }

    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<ApiResponse<Long>> getActiveEnrollmentCountByCourse(@PathVariable Long courseId) {
        Long count = enrollmentService.getActiveEnrollmentCountByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    // 内部请求类
    public static class EnrollmentRequest {
        private Long courseId;
        private Long studentId;

        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }

        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }
    }
}