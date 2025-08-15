package com.easylearning.repository;

import com.easylearning.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

@Repository
public class QuestionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Question> questionRowMapper = new RowMapper<Question>() {
        @Override
        public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
            Question question = new Question();
            question.setId(rs.getLong("id"));
            question.setQuizId(rs.getLong("quiz_id"));
            question.setQuestionText(rs.getString("question_text"));
            
            Array optionsArray = rs.getArray("options");
            if (optionsArray != null) {
                String[] options = (String[]) optionsArray.getArray();
                question.setOptions(Arrays.asList(options));
            }
            
            question.setCorrectAnswer(rs.getString("correct_answer"));
            question.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return question;
        }
    };

    public List<Question> findByQuizId(Long quizId) {
        String sql = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY id";
        return jdbcTemplate.query(sql, questionRowMapper, quizId);
    }

    public Question findById(Long id) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, questionRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Question save(Question question) {
        String sql = "INSERT INTO questions (quiz_id, question_text, options, correct_answer) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, question.getQuizId());
            ps.setString(2, question.getQuestionText());
            
            Array optionsArray = connection.createArrayOf("text", question.getOptions().toArray());
            ps.setArray(3, optionsArray);
            
            ps.setString(4, question.getCorrectAnswer());
            return ps;
        }, keyHolder);
        
        question.setId(keyHolder.getKey().longValue());
        return question;
    }

    public void update(Question question) {
        String sql = "UPDATE questions SET question_text = ?, options = ?, correct_answer = ? WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, question.getQuestionText());
            
            Array optionsArray = connection.createArrayOf("text", question.getOptions().toArray());
            ps.setArray(2, optionsArray);
            
            ps.setString(3, question.getCorrectAnswer());
            ps.setLong(4, question.getId());
            return ps;
        });
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM questions WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
