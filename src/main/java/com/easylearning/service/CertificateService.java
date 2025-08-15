package com.easylearning.service;

import com.easylearning.dto.CertificateDto;
import com.easylearning.model.Certificate;
import com.easylearning.model.Course;
import com.easylearning.model.User;
import com.easylearning.repository.CertificateDao;
import com.easylearning.repository.CourseDao;
import com.easylearning.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    @Autowired
    private CertificateDao certificateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    public boolean certificateExists(Long userId, Long courseId) {
        return certificateDao.findByUserIdAndCourseId(userId, courseId) != null;
    }

    public CertificateDto generateCertificate(Long userId, Long courseId) {
        User user = userDao.findById(userId);
        Course course = courseDao.findById(courseId);
        
        if (user == null || course == null) {
            throw new RuntimeException("User or course not found");
        }

        // Generate certificate URL (in a real application, this would be a proper URL to a PDF or image)
        String certificateUrl = generateCertificateUrl(user, course);

        Certificate certificate = new Certificate();
        certificate.setUserId(userId);
        certificate.setCourseId(courseId);
        certificate.setCertificateUrl(certificateUrl);

        Certificate savedCertificate = certificateDao.save(certificate);
        return convertToDto(savedCertificate, user.getName(), course.getTitle());
    }

    public List<CertificateDto> getUserCertificates(Long userId) {
        return certificateDao.findByUserId(userId).stream()
            .map(certificate -> {
                User user = userDao.findById(certificate.getUserId());
                Course course = courseDao.findById(certificate.getCourseId());
                return convertToDto(certificate, 
                    user != null ? user.getName() : "Unknown User",
                    course != null ? course.getTitle() : "Unknown Course");
            })
            .collect(Collectors.toList());
    }

    public CertificateDto getCertificateById(Long id) {
        Certificate certificate = certificateDao.findById(id);
        if (certificate == null) {
            return null;
        }

        User user = userDao.findById(certificate.getUserId());
        Course course = courseDao.findById(certificate.getCourseId());
        
        return convertToDto(certificate, 
            user != null ? user.getName() : "Unknown User",
            course != null ? course.getTitle() : "Unknown Course");
    }

    private String generateCertificateUrl(User user, Course course) {
        // In a real application, this would generate an actual certificate
        // For now, we'll create a placeholder URL
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return String.format("/certificates/%s_%s_%s.pdf", 
            user.getName().replaceAll("\\s+", "_"), 
            course.getTitle().replaceAll("\\s+", "_"), 
            timestamp);
    }

    private CertificateDto convertToDto(Certificate certificate, String userName, String courseTitle) {
        CertificateDto dto = new CertificateDto();
        dto.setId(certificate.getId());
        dto.setUserId(certificate.getUserId());
        dto.setUserName(userName);
        dto.setCourseId(certificate.getCourseId());
        dto.setCourseTitle(courseTitle);
        dto.setCertificateUrl(certificate.getCertificateUrl());
        dto.setIssuedAt(certificate.getIssuedAt());
        return dto;
    }
}