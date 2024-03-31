package com.example.movie.models.dto;

import com.example.movie.services.LocalDateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private int id;
    private double price;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime startAt;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime endAt;
    private String code;
    private int movieId;
    private String name;
    private int roomId;
    private boolean isActive;

    public void setStartAt(String dateTime){
        System.out.println("String ***");
        this.startAt = LocalDateTimeFormat.toLocalDateTime(dateTime);
    }

    public void setStartAt(LocalDateTime dateTime){
        this.startAt = dateTime;
    }

    public void setEndAt(String dateTime){
        System.out.println("String ***");
        this.endAt = LocalDateTimeFormat.toLocalDateTime(dateTime);
    }

    public void setEndAt(LocalDateTime dateTime){
        this.endAt = dateTime;
    }
}
