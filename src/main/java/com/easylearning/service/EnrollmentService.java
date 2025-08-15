package com.easylearning.service;

import com.easylearning.dto.EnrollmentDto;
import com.easylearning.model.Course;
import com.easylearning.model.Enrollment;
import com.easylearning.repository.CourseDao;
import com.easylearning.repository.EnrollmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentDao enrollmentDao;

    @Autowired
    private CourseDao courseDao;

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentDao.save(enrollment);
    }

    public List<EnrollmentDto> getUserEnrollments(Long userId) {
        return enrollmentDao.findByUserId(userId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    public EnrollmentDto getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentDao.findById(id);
        return enrollment != null ? convertToDto(enrollment) : null;
    }

    public boolean isUserEnrolledInCourse(Long userId, Long courseId) {
        return enrollmentDao.findByUserIdAndCourseId(userId, courseId) != null;
    }

    public void updateProgress(Long enrollmentId, Double progress) {
        enrollmentDao.updateProgress(enrollmentId, progress);
    }

    private EnrollmentDto convertToDto(Enrollment enrollment) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(enrollment.getId());
        dto.setUserId(enrollment.getUserId());
        dto.setCourseId(enrollment.getCourseId());
        dto.setProgress(enrollment.getProgress());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        dto.setCompletedAt(enrollment.getCompletedAt());

        // Load course details
        Course course = courseDao.findById(enrollment.getCourseId());
        if (course != null) {
            dto.setCourseTitle(course.getTitle());
        }

        return dto;
    }
}
