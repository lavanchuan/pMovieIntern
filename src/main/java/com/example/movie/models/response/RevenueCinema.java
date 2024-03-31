package com.example.movie.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueCinema {
    private int id;
    private String cinemaName;
    private double revenueFood;
    private double revenueTicket;
    private double totalRevenue;

    public static final String MATCH_PATTERN = "---";

    public RevenueCinema(String revenueString){
        this.id = Integer.parseInt(revenueString.split(MATCH_PATTERN)[0]);
        this.cinemaName = revenueString.split(MATCH_PATTERN)[1];
        this.revenueFood = Double.parseDouble(revenueString.split(MATCH_PATTERN)[2]);
        this.revenueTicket = Double.parseDouble(revenueString.split(MATCH_PATTERN)[3]);
        this.totalRevenue = Double.parseDouble(revenueString.split(MATCH_PATTERN)[4]);
    }
}
