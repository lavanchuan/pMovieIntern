package com.example.movie.models.request;

import lombok.Data;

@Data
public class PayBillRequest {
    private String token;
    private int billId;
    private int promotionId;
    private double totalMoney = 500000; // so du tai khoan
}
