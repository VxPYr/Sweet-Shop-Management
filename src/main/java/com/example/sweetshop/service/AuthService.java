package com.example.sweetshop.service;

import com.example.sweetshop.DTO.AuthRequest;
import com.example.sweetshop.DTO.AuthResponse;
import com.example.sweetshop.DTO.RegisterRequest;
import com.example.sweetshop.entity.Role;
import com.example.sweetshop.entity.User;
import com.example.sweetshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        Role role = request.getRole() != null ? request.getRole() : Role.USER;

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);


        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtService.generateToken(String.valueOf(user));
        return new AuthResponse(token);
    }
}
