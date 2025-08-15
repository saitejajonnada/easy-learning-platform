package com.easylearning.model;

import java.time.LocalDateTime;

public class Result {
    private Long id;
    private Long userId;
    private Long quizId;
    private Integer score;
    private LocalDateTime submittedAt;

    public Result() {}

    public Result(Long userId, Long quizId, Integer score) {
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
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

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
