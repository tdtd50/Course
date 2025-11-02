package com.zjsu.cxt.course.controller;

import com.zjsu.cxt.course.model.ApiResponse;
import com.zjsu.cxt.course.model.Course;
import com.zjsu.cxt.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(course -> ResponseEntity.ok(ApiResponse.success(course)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((ApiResponse<Course>) ApiResponse.error(404, "Course not found")));
    }

    @GetMapping("/code/{courseCode}")
    public ResponseEntity<ApiResponse<Course>> getCourseByCode(@PathVariable String courseCode) {
        return courseService.getCourseByCode(courseCode)
                .map(course -> ResponseEntity.ok(ApiResponse.success(course)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((ApiResponse<Course>) ApiResponse.error(404, "Course not found")));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody Course course) {
        try {
            Course createdCourse = courseService.createCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdCourse));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body((ApiResponse<Course>) ApiResponse.error(409, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id,
                                                            @RequestBody Course courseDetails) {
        try {
            Course updatedCourse = courseService.updateCourse(id, courseDetails);
            if (updatedCourse != null) {
                return ResponseEntity.ok(ApiResponse.success(updatedCourse));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((ApiResponse<Course>) ApiResponse.error(404, "Course not found"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body((ApiResponse<Course>) ApiResponse.error(409, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCourse(@PathVariable Long id) {
        boolean deleted = courseService.deleteCourse(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Course deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Course not found"));
        }
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<Course>>> getAvailableCourses() {
        List<Course> courses = courseService.getAvailableCourses();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Course>>> searchCourses(@RequestParam String keyword) {
        List<Course> courses = courseService.searchCoursesByTitle(keyword);
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<ApiResponse<List<Course>>> getCoursesByInstructor(@PathVariable String instructorId) {
        List<Course> courses = courseService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(ApiResponse.success(courses));
    }
}