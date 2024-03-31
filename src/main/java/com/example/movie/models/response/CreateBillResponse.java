package com.example.movie.models.response;

import com.example.movie.models.dto.BillDTO;
import lombok.Data;

@Data
public class CreateBillResponse {
    private int status = 200;
    private BillDTO data;

    public CreateBillResponse(int status){
        this.status = status;
        this.data = null;
    }

    public CreateBillResponse(BillDTO data){
        this.data = data;
    }


}
