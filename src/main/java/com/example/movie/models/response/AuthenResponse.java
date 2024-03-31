package com.example.movie.models.response;

import com.example.movie.models.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenResponse {
    private Token token;

}
