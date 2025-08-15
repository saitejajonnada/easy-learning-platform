package com.easylearning.repository;

import com.easylearning.model.Course;
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
public class CourseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Course> courseRowMapper = new RowMapper<Course>() {
        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setId(rs.getLong("id"));
            course.setTitle(rs.getString("title"));
            course.setDescription(rs.getString("description"));
            course.setDuration(rs.getInt("duration"));
            course.setLevel(rs.getString("level"));
            course.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return course;
        }
    };

    public List<Course> findAll() {
        String sql = "SELECT * FROM courses ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, courseRowMapper);
    }

    public Course findById(Long id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, courseRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Course save(Course course) {
        String sql = "INSERT INTO courses (title, description, duration, level) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getDuration());
            ps.setString(4, course.getLevel());
            return ps;
        }, keyHolder);
        
        course.setId(keyHolder.getKey().longValue());
        return course;
    }

    public void update(Course course) {
        String sql = "UPDATE courses SET title = ?, description = ?, duration = ?, level = ? WHERE id = ?";
        jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), 
                           course.getDuration(), course.getLevel(), course.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
