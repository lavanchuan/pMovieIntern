package com.example.movie.models.response;

import com.example.movie.models.dto.UserDTO;
import lombok.Data;


public class UserResponse extends BaseResponse<UserDTO>{
    public UserResponse(UserDTO userDTO) {
        super(userDTO);
    }

    public UserResponse(int status) {
        super(status);
    }
}
