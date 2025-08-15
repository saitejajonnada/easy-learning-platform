package com.easylearning.repository;

import com.easylearning.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class QuizDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Quiz> quizRowMapper = new RowMapper<Quiz>() {
        @Override
        public Quiz mapRow(ResultSet rs, int rowNum) throws SQLException {
            Quiz quiz = new Quiz();
            quiz.setId(rs.getLong("id"));
            quiz.setCourseId(rs.getLong("course_id"));
            quiz.setTitle(rs.getString("title"));
            quiz.setDescription(rs.getString("description"));
            quiz.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return quiz;
        }
    };

    public List<Quiz> findByCourseId(Long courseId) {
        String sql = "SELECT * FROM quizzes WHERE course_id = ? ORDER BY created_at";
        return jdbcTemplate.query(sql, quizRowMapper, courseId);
    }

    public Quiz findById(Long id) {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, quizRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Quiz save(Quiz quiz) {
        String sql = "INSERT INTO quizzes (course_id, title, description) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, quiz.getCourseId());
            ps.setString(2, quiz.getTitle());
            ps.setString(3, quiz.getDescription());
            return ps;
        }, keyHolder);
        
        quiz.setId(keyHolder.getKey().longValue());
        return quiz;
    }

    public void update(Quiz quiz) {
        String sql = "UPDATE quizzes SET title = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, quiz.getTitle(), quiz.getDescription(), quiz.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM quizzes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
