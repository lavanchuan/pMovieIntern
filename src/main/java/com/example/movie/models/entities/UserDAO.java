package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import com.example.movie.models.Role;
import com.example.movie.models.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO extends BaseEntity {
    private int point;
    private String username;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rankCustomerId")
    private RankCustomerDAO rankCustomerDAO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userStatusId")
    private UserStatusDAO userStatusDAO;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private RoleDAO roleDAO;

    // temp
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
