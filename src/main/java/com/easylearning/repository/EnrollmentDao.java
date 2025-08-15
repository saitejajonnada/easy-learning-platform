package com.easylearning.repository;

import com.easylearning.model.Enrollment;
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
public class EnrollmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Enrollment> enrollmentRowMapper = new RowMapper<Enrollment>() {
        @Override
        public Enrollment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enrollment enrollment = new Enrollment();
            enrollment.setId(rs.getLong("id"));
            enrollment.setUserId(rs.getLong("user_id"));
            enrollment.setCourseId(rs.getLong("course_id"));
            enrollment.setProgress(rs.getDouble("progress"));
            enrollment.setEnrolledAt(rs.getTimestamp("enrolled_at").toLocalDateTime());
            if (rs.getTimestamp("completed_at") != null) {
                enrollment.setCompletedAt(rs.getTimestamp("completed_at").toLocalDateTime());
            }
            return enrollment;
        }
    };

    public List<Enrollment> findByUserId(Long userId) {
        String sql = "SELECT * FROM enrollments WHERE user_id = ? ORDER BY enrolled_at DESC";
        return jdbcTemplate.query(sql, enrollmentRowMapper, userId);
    }

    public Enrollment findByUserIdAndCourseId(Long userId, Long courseId) {
        String sql = "SELECT * FROM enrollments WHERE user_id = ? AND course_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, enrollmentRowMapper, userId, courseId);
        } catch (Exception e) {
            return null;
        }
    }

    public Enrollment findById(Long id) {
        String sql = "SELECT * FROM enrollments WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, enrollmentRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Enrollment save(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (user_id, course_id, progress) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, enrollment.getUserId());
            ps.setLong(2, enrollment.getCourseId());
            ps.setDouble(3, enrollment.getProgress());
            return ps;
        }, keyHolder);
        
        enrollment.setId(keyHolder.getKey().longValue());
        return enrollment;
    }

    public void updateProgress(Long enrollmentId, Double progress) {
        String sql = "UPDATE enrollments SET progress = ? WHERE id = ?";
        jdbcTemplate.update(sql, progress, enrollmentId);
        
        // If progress is 100%, mark as completed
        if (progress >= 100.0) {
            String completeSql = "UPDATE enrollments SET completed_at = CURRENT_TIMESTAMP WHERE id = ?";
            jdbcTemplate.update(completeSql, enrollmentId);
        }
    }
}
