package com.easylearning.repository;

import com.easylearning.model.Result;
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
public class ResultDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Result> resultRowMapper = new RowMapper<Result>() {
        @Override
        public Result mapRow(ResultSet rs, int rowNum) throws SQLException {
            Result result = new Result();
            result.setId(rs.getLong("id"));
            result.setUserId(rs.getLong("user_id"));
            result.setQuizId(rs.getLong("quiz_id"));
            result.setScore(rs.getInt("score"));
            result.setSubmittedAt(rs.getTimestamp("submitted_at").toLocalDateTime());
            return result;
        }
    };

    public List<Result> findByUserId(Long userId) {
        String sql = "SELECT * FROM results WHERE user_id = ? ORDER BY submitted_at DESC";
        return jdbcTemplate.query(sql, resultRowMapper, userId);
    }

    public List<Result> findByQuizId(Long quizId) {
        String sql = "SELECT * FROM results WHERE quiz_id = ? ORDER BY submitted_at DESC";
        return jdbcTemplate.query(sql, resultRowMapper, quizId);
    }

    public Result findById(Long id) {
        String sql = "SELECT * FROM results WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, resultRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Result save(Result result) {
        String sql = "INSERT INTO results (user_id, quiz_id, score) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, result.getUserId());
            ps.setLong(2, result.getQuizId());
            ps.setInt(3, result.getScore());
            return ps;
        }, keyHolder);
        
        result.setId(keyHolder.getKey().longValue());
        return result;
    }
}
