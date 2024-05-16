package com.kasumov.med_ci.config.security.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;

    private String type = "Bearer ";

    private Long id;

    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String email, List<String> roles) {
        this.token = type + accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

}
