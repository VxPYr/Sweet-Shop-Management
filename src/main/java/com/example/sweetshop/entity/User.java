package com.example.sweetshop.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Document(collection = "users")
@Data
public class User implements UserDetails {
    @Id
    private String id;

    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return java.util.List.of(() -> "ROLE_" + role);
    }

}
