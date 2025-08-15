package com.easylearning.controller;

import com.easylearning.dto.QuizDto;
import com.easylearning.model.Quiz;
import com.easylearning.model.Result;
import com.easylearning.model.User;
import com.easylearning.response.ApiResponse;
import com.easylearning.service.QuizService;
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
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @PostMapping("/courses/{courseId}/quizzes")
    public ResponseEntity<ApiResponse<QuizDto>> createQuiz(
            @PathVariable Long courseId, @RequestBody QuizDto quizDto) {
        try {
            Quiz quiz = new Quiz();
            quiz.setCourseId(courseId);
            quiz.setTitle(quizDto.getTitle());
            quiz.setDescription(quizDto.getDescription());

            Quiz savedQuiz = quizService.createQuiz(quiz);
            QuizDto result = quizService.getQuizById(savedQuiz.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, result, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to create quiz: " + e.getMessage()));
        }
    }

    @GetMapping("/courses/{courseId}/quizzes")
    public ResponseEntity<ApiResponse<List<QuizDto>>> getCourseQuizzes(@PathVariable Long courseId) {
        try {
            List<QuizDto> quizzes = quizService.getQuizzesByCourse(courseId);
            return ResponseEntity.ok(new ApiResponse<>(true, quizzes, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to fetch quizzes: " + e.getMessage()));
        }
    }

    @PostMapping("/quizzes/{quizId}/submit")
    public ResponseEntity<ApiResponse<Map<String, Object>>> submitQuiz(
            @PathVariable Long quizId, @RequestBody Map<String, Object> submission, Authentication auth) {
        try {
            String email = auth.getName();
            User user = userService.findByEmail(email);
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> answers = (List<Map<String, Object>>) submission.get("answers");
            
            int score = quizService.calculateScore(quizId, answers);
            
            Result result = new Result();
            result.setUserId(user.getId());
            result.setQuizId(quizId);
            result.setScore(score);
            
            quizService.saveResult(result);
            
            Map<String, Object> response = Map.of(
                "score", score,
                "total_questions", quizService.getQuestionCount(quizId),
                "percentage", (score * 100.0) / quizService.getQuestionCount(quizId)
            );
            
            return ResponseEntity.ok(new ApiResponse<>(true, response, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to submit quiz: " + e.getMessage()));
        }
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<ApiResponse<QuizDto>> getQuizById(@PathVariable Long id) {
        try {
            QuizDto quiz = quizService.getQuizById(id);
            if (quiz == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(true, quiz, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to fetch quiz: " + e.getMessage()));
        }
    }
}
