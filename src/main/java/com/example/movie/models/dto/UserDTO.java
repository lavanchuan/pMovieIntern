package com.example.movie.models.dto;

import com.example.movie.models.Role;
import com.example.movie.models.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private int id;
    private int point;
    private String username;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private int rankCustomerId;
    private int userStatusId;
    private boolean isActive = true;
    private int roleId;

    //
    private Role role = Role.USER;
    private UserStatus userStatus = UserStatus.NOT_ACTIVE;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
