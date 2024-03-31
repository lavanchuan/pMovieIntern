package com.example.movie.models.response;

import com.example.movie.models.BillStatus;
import com.example.movie.models.dto.BillDTO;
import lombok.Data;

@Data
public class PayBillResponse {
    private int status = 200;
    private BillDTO data;

    public PayBillResponse(int status){
        this.status = status;
    }

    public PayBillResponse(BillDTO data){
        this.data = data;
    }
}
