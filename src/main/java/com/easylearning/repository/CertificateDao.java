package com.easylearning.repository;

import com.easylearning.model.Certificate;
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
public class CertificateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Certificate> certificateRowMapper = new RowMapper<Certificate>() {
        @Override
        public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
            Certificate certificate = new Certificate();
            certificate.setId(rs.getLong("id"));
            certificate.setUserId(rs.getLong("user_id"));
            certificate.setCourseId(rs.getLong("course_id"));
            certificate.setCertificateUrl(rs.getString("certificate_url"));
            certificate.setIssuedAt(rs.getTimestamp("issued_at").toLocalDateTime());
            return certificate;
        }
    };

    public List<Certificate> findByUserId(Long userId) {
        String sql = "SELECT * FROM certificates WHERE user_id = ? ORDER BY issued_at DESC";
        return jdbcTemplate.query(sql, certificateRowMapper, userId);
    }

    public Certificate findByUserIdAndCourseId(Long userId, Long courseId) {
        String sql = "SELECT * FROM certificates WHERE user_id = ? AND course_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, certificateRowMapper, userId, courseId);
        } catch (Exception e) {
            return null;
        }
    }

    public Certificate findById(Long id) {
        String sql = "SELECT * FROM certificates WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, certificateRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Certificate save(Certificate certificate) {
        String sql = "INSERT INTO certificates (user_id, course_id, certificate_url) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, certificate.getUserId());
            ps.setLong(2, certificate.getCourseId());
            ps.setString(3, certificate.getCertificateUrl());
            return ps;
        }, keyHolder);
        
        certificate.setId(keyHolder.getKey().longValue());
        return certificate;
    }
}
