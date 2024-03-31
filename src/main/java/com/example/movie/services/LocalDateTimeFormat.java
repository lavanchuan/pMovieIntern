package com.example.movie.services;


import java.time.LocalDateTime;

public class LocalDateTimeFormat {

    public static final String PATTERN_DATE = "/";
    public static final String PATTERN_TIME = ":";
    public static final String PATTERN_DATE_TIME = " ";

    // dd/MM/yyyy hh:mm:ss
    // dd/MM/yyyy hh:mm


    public static LocalDateTime toLocalDateTime(String data){
        String[] dateTime = data.split(PATTERN_DATE_TIME);
        if(dateTime[1].split(PATTERN_TIME).length == 2)
            return LocalDateTime.of(
                    Integer.parseInt(dateTime[0].split(PATTERN_DATE)[0]),
                    Integer.parseInt(dateTime[0].split(PATTERN_DATE)[1]),
                    Integer.parseInt(dateTime[0].split(PATTERN_DATE)[2]),
                    Integer.parseInt(dateTime[1].split(PATTERN_TIME)[0]),
                    Integer.parseInt(dateTime[1].split(PATTERN_TIME)[1])
            );
        return LocalDateTime.of(
                Integer.parseInt(dateTime[0].split(PATTERN_DATE)[0]),
                Integer.parseInt(dateTime[0].split(PATTERN_DATE)[1]),
                Integer.parseInt(dateTime[0].split(PATTERN_DATE)[2]),
                Integer.parseInt(dateTime[1].split(PATTERN_TIME)[0]),
                Integer.parseInt(dateTime[1].split(PATTERN_TIME)[1]),
                Integer.parseInt(dateTime[1].split(PATTERN_TIME)[2])
        );
    }

    public static String toString(LocalDateTime dateTime){
        return String.format("%d/%d/%d %d:%d:%d",
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute(),
                dateTime.getSecond());
    }
}
