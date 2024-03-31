package com.example.movie.models.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class ConfirmForgotPasswordRequest {
    @NonNull
    private String email;
    @NonNull
    private String otp;
    @NonNull
    private String password;
}
