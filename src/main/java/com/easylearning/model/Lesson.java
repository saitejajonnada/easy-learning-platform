package com.easylearning.model;

import java.time.LocalDateTime;

public class Lesson {
    private Long id;
    private Long courseId;
    private String title;
    private String content;
    private Integer orderIndex;
    private LocalDateTime createdAt;

    public Lesson() {}

    public Lesson(Long courseId, String title, String content, Integer orderIndex) {
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
