package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntervalDTO {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDay;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDay;
}
