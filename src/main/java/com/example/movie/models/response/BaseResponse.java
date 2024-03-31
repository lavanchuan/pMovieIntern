package com.example.movie.models.response;

import lombok.Data;

@Data
public abstract class BaseResponse<DATA> {
    public static final int SUCCESS_STATUS = 200;
    public static final int ERROR_STATUS = 400;

    private int status = SUCCESS_STATUS;
    private DATA data;

    public BaseResponse(int status){
        this.status = status;
    }

    public BaseResponse(DATA data){
        this.data = data;
    }
}
