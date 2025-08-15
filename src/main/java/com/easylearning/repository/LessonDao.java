package com.easylearning.repository;

import com.easylearning.model.Lesson;
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
public class LessonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Lesson> lessonRowMapper = new RowMapper<Lesson>() {
        @Override
        public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
            Lesson lesson = new Lesson();
            lesson.setId(rs.getLong("id"));
            lesson.setCourseId(rs.getLong("course_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setContent(rs.getString("content"));
            lesson.setOrderIndex(rs.getInt("order_index"));
            lesson.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return lesson;
        }
    };

    public List<Lesson> findByCourseId(Long courseId) {
        String sql = "SELECT * FROM lessons WHERE course_id = ? ORDER BY order_index";
        return jdbcTemplate.query(sql, lessonRowMapper, courseId);
    }

    public Lesson findById(Long id) {
        String sql = "SELECT * FROM lessons WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, lessonRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Lesson save(Lesson lesson) {
        String sql = "INSERT INTO lessons (course_id, title, content, order_index) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, lesson.getCourseId());
            ps.setString(2, lesson.getTitle());
            ps.setString(3, lesson.getContent());
            ps.setInt(4, lesson.getOrderIndex());
            return ps;
        }, keyHolder);
        
        lesson.setId(keyHolder.getKey().longValue());
        return lesson;
    }

    public void update(Lesson lesson) {
        String sql = "UPDATE lessons SET title = ?, content = ?, order_index = ? WHERE id = ?";
        jdbcTemplate.update(sql, lesson.getTitle(), lesson.getContent(), 
                           lesson.getOrderIndex(), lesson.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM lessons WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
