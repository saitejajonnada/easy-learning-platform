package com.easylearning.dto;

import java.time.LocalDateTime;

public class CertificateDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long courseId;
    private String courseTitle;
    private String certificateUrl;
    private LocalDateTime issuedAt;

    // Constructors
    public CertificateDto() {}

    public CertificateDto(Long id, Long userId, String userName, Long courseId, String courseTitle, 
                         String certificateUrl, LocalDateTime issuedAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.certificateUrl = certificateUrl;
        this.issuedAt = issuedAt;
    }

    // Getters and Setters
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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