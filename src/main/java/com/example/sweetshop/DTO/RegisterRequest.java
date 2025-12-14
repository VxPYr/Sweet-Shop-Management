package com.example.sweetshop.DTO;

import com.example.sweetshop.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
