package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import com.example.movie.models.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDAO extends BaseEntity {
    private String code;
    @Enumerated(EnumType.STRING)
    private Role roleName;

    public RoleDAO(Role role){
        this.roleName = role;
    }
}
