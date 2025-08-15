package com.easylearning.controller;

import com.easylearning.dto.CourseDto;
import com.easylearning.model.Course;
import com.easylearning.response.ApiResponse;
import com.easylearning.service.CourseService;
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
    public ResponseEntity<ApiResponse<List<CourseDto>>> getAllCourses() {
        try {
            List<CourseDto> courses = courseService.getAllCourses();
            return ResponseEntity.ok(new ApiResponse<>(true, courses, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to fetch courses: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable Long id) {
        try {
            CourseDto course = courseService.getCourseById(id);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(true, course, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to fetch course: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(@RequestBody CourseDto courseDto) {
        try {
            Course course = new Course();
            course.setTitle(courseDto.getTitle());
            course.setDescription(courseDto.getDescription());
            course.setDuration(courseDto.getDuration());
            course.setLevel(courseDto.getLevel());

            Course savedCourse = courseService.createCourse(course);
            CourseDto result = courseService.getCourseById(savedCourse.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, result, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to create course: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        try {
            Course course = new Course();
            course.setId(id);
            course.setTitle(courseDto.getTitle());
            course.setDescription(courseDto.getDescription());
            course.setDuration(courseDto.getDuration());
            course.setLevel(courseDto.getLevel());

            courseService.updateCourse(course);
            CourseDto result = courseService.getCourseById(id);
            
            return ResponseEntity.ok(new ApiResponse<>(true, result, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to update course: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Course deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to delete course: " + e.getMessage()));
        }
    }
}
