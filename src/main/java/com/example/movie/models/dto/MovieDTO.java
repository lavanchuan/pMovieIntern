package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private int id;
    private int movieDuration;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime endTime;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime premiereDate;
    private String description;
    private String director;
    private String image;
    private String heroImage;
    private String language;
    private int movieTypeId;
    private String name;
    private int rateId;
    private String trailer;
    private boolean isActive;
}
