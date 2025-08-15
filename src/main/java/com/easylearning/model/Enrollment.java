package com.easylearning.model;

import java.time.LocalDateTime;

public class Enrollment {
    private Long id;
    private Long userId;
    private Long courseId;
    private Double progress; // 0.0 to 100.0
    private LocalDateTime enrolledAt;
    private LocalDateTime completedAt;

    public Enrollment() {}

    public Enrollment(Long userId, Long courseId, Double progress) {
        this.userId = userId;
        this.courseId = courseId;
        this.progress = progress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
