package com.example.movie.models.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConfirmEmailRequest {
    private String username;
    private String confirmCode;
    private LocalDateTime requestTime = LocalDateTime.now();
}
