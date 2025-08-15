package com.easylearning.controller;

import com.easylearning.dto.CertificateDto;
import com.easylearning.model.Certificate;
import com.easylearning.model.User;
import com.easylearning.response.ApiResponse;
import com.easylearning.service.CertificateService;
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
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserService userService;

    @PostMapping("/certificates")
    public ResponseEntity<ApiResponse<CertificateDto>> generateCertificate(
            @RequestBody Map<String, Long> request, Authentication auth) {
        try {
            String email = auth.getName();
            User user = userService.findByEmail(email);
            Long courseId = request.get("courseId");

            // Check if certificate already exists
            if (certificateService.certificateExists(user.getId(), courseId)) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, null, "Certificate already exists for this course"));
            }

            CertificateDto certificate = certificateService.generateCertificate(user.getId(), courseId);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, certificate, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to generate certificate: " + e.getMessage()));
        }
    }

    @GetMapping("/users/me/certificates")
    public ResponseEntity<ApiResponse<List<CertificateDto>>> getUserCertificates(Authentication auth) {
        try {
            String email = auth.getName();
            User user = userService.findByEmail(email);
            List<CertificateDto> certificates = certificateService.getUserCertificates(user.getId());
            
            return ResponseEntity.ok(new ApiResponse<>(true, certificates, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to fetch certificates: " + e.getMessage()));
        }
    }

    @GetMapping("/certificates/{id}")
    public ResponseEntity<ApiResponse<CertificateDto>> getCertificateById(@PathVariable Long id) {
        try {
            CertificateDto certificate = certificateService.getCertificateById(id);
            if (certificate == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(true, certificate, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Failed to fetch certificate: " + e.getMessage()));
        }
    }
}