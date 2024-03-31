package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import com.example.movie.models.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserStatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusDAO extends BaseEntity {
    private String code;
    @Enumerated(EnumType.STRING)
    private UserStatus name;

    public UserStatusDAO(UserStatus status){
        this.name = status;
    }
}
