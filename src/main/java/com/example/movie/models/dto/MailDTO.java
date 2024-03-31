package com.example.movie.models.dto;

import com.example.movie.services.MailServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO implements Serializable {
    private String to;
    private String subject;
    private String content;
    private String from = MailServices.FROM;
    private String personal = MailServices.PERSONAL;
}
