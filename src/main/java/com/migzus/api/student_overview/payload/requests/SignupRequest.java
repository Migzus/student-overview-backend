package com.migzus.api.student_overview.payload.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(max = 100)
    private String firstName;
    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Size(max = 10)
    private String phone;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
