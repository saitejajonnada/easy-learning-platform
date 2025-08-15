package com.easylearning.controller;

import com.easylearning.dto.EnrollmentDto;
import com.easylearning.model.Enrollment;
import com.easylearning.model.User;
import com.easylearning.response.ApiResponse;
import com.easylearning.service.EnrollmentService;
import com.easylearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private UserService userService;

    @PostMapping("/enrollments")
    public ResponseEntity<ApiResponse<EnrollmentDto>> createEnrollment(
            @RequestBody Map<String, Long> request, Authentication auth) {
        try {
            String email = auth.getName();
            User user = userService.findByEmail(email);
            Long courseId = request.get("courseId");

            if (enrollmentService.isUserEnrolledInCourse(user.getId(), courseId)) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, null, "User already enrolled in this course"));
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setUserId(user.getId());
            enrollment.setCourseId(courseId);
            enrollment.setProgress(0.0);

            Enrollment savedEnrollment = enrollmentService.createEnrollment(enrollment);
            EnrollmentDto result = enrollmentService.getEnrollmentById(savedEnrollment.getId());
            
            return ResponseEntity.ok(new ApiResponse<>(true, result, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to create enrollment: " + e.getMessage()));
        }
    }

    @GetMapping("/users/me/enrollments")
    public ResponseEntity<ApiResponse<List<EnrollmentDto>>> getUserEnrollments(Authentication auth) {
        try {
            String email = auth.getName();
            User user = userService.findByEmail(email);
            List<EnrollmentDto> enrollments = enrollmentService.getUserEnrollments(user.getId());
            
            return ResponseEntity.ok(new ApiResponse<>(true, enrollments, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to fetch enrollments: " + e.getMessage()));
        }
    }

    @PutMapping("/enrollments/{id}/progress")
    public ResponseEntity<ApiResponse<EnrollmentDto>> updateProgress(
            @PathVariable Long id, @RequestBody Map<String, Double> request, Authentication auth) {
        try {
            Double progress = request.get("progress");
            enrollmentService.updateProgress(id, progress);
            EnrollmentDto result = enrollmentService.getEnrollmentById(id);
            
            return ResponseEntity.ok(new ApiResponse<>(true, result, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to update progress: " + e.getMessage()));
        }
    }
}
