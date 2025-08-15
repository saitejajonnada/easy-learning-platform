package com.easylearning.service;

import com.easylearning.dto.QuestionDto;
import com.easylearning.dto.QuizDto;
import com.easylearning.model.Question;
import com.easylearning.model.Quiz;
import com.easylearning.model.Result;
import com.easylearning.repository.QuestionDao;
import com.easylearning.repository.QuizDao;
import com.easylearning.repository.ResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private ResultDao resultDao;

    public Quiz createQuiz(Quiz quiz) {
        return quizDao.save(quiz);
    }

    public List<QuizDto> getQuizzesByCourse(Long courseId) {
        return quizDao.findByCourseId(courseId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    public QuizDto getQuizById(Long id) {
        Quiz quiz = quizDao.findById(id);
        return quiz != null ? convertToDto(quiz) : null;
    }

    public int calculateScore(Long quizId, List<Map<String, Object>> answers) {
        List<Question> questions = questionDao.findByQuizId(quizId);
        int score = 0;

        for (Map<String, Object> answer : answers) {
            Long questionId = Long.valueOf(answer.get("question_id").toString());
            String selectedAnswer = answer.get("selected_answer").toString();

            Question question = questions.stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .orElse(null);

            if (question != null && question.getCorrectAnswer().equals(selectedAnswer)) {
                score++;
            }
        }

        return score;
    }

    public void saveResult(Result result) {
        resultDao.save(result);
    }

    public int getQuestionCount(Long quizId) {
        return questionDao.findByQuizId(quizId).size();
    }

    private QuizDto convertToDto(Quiz quiz) {
        QuizDto dto = new QuizDto();
        dto.setId(quiz.getId());
        dto.setCourseId(quiz.getCourseId());
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());
        dto.setCreatedAt(quiz.getCreatedAt());

        // Load questions for this quiz
        List<QuestionDto> questions = questionDao.findByQuizId(quiz.getId()).stream()
            .map(question -> {
                QuestionDto questionDto = new QuestionDto();
                questionDto.setId(question.getId());
                questionDto.setQuizId(question.getQuizId());
                questionDto.setQuestionText(question.getQuestionText());
                questionDto.setOptions(question.getOptions());
                questionDto.setCorrectAnswer(question.getCorrectAnswer());
                return questionDto;
            })
            .collect(Collectors.toList());

        dto.setQuestions(questions);
        return dto;
    }
}
