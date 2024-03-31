package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "RefreshToken")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDAO extends BaseEntity {
    private String token;

    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime expiredTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDAO userDAO;
}
