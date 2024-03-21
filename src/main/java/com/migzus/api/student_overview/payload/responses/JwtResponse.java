package com.migzus.api.student_overview.payload.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private int id;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, int id, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}