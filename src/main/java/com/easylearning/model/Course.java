package com.easylearning.model;

import java.time.LocalDateTime;

public class Course {
    private Long id;
    private String title;
    private String description;
    private Integer duration; // in hours
    private String level; // beginner, intermediate, advanced
    private LocalDateTime createdAt;

    public Course() {}

    public Course(String title, String description, Integer duration, String level) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
