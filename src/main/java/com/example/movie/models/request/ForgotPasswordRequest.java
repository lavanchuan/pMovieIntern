package com.example.movie.models.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String username;
    private String email;
}
