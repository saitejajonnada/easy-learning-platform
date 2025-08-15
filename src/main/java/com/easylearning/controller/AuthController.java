package com.easylearning.controller;

import com.easylearning.config.JwtUtil;
import com.easylearning.dto.AuthRequest;
import com.easylearning.dto.AuthResponse;
import com.easylearning.model.User;
import com.easylearning.response.ApiResponse;
import com.easylearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody AuthRequest request) {
        try {
            if (userService.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, null, "Email already exists"));
            }

            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            userService.createUser(user);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        try {
            User user = userService.findByEmail(request.getEmail());
            if (user == null) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, null, "Invalid email or password"));
            }

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, null, "Invalid email or password"));
            }

            String token = jwtUtil.generateToken(user.getEmail());
            AuthResponse authResponse = new AuthResponse(token, token); // Using same token for both access and refresh
            
            return ResponseEntity.ok(new ApiResponse<>(true, authResponse, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Login failed: " + e.getMessage()));
        }
    }
}
