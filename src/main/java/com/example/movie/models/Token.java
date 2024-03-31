package com.example.movie.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Token {
    private String subject;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date isSuedAt;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date expiredAt;
    private long usage;
    private String value;
}
