package com.easylearning.model;

import java.time.LocalDateTime;

public class Certificate {
    private Long id;
    private Long userId;
    private Long courseId;
    private String certificateUrl;
    private LocalDateTime issuedAt;

    public Certificate() {}

    public Certificate(Long userId, Long courseId, String certificateUrl) {
        this.userId = userId;
        this.courseId = courseId;
        this.certificateUrl = certificateUrl;
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

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
}
