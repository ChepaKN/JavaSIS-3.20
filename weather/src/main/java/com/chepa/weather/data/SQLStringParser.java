package com.chepa.weather.data;

import lombok.Getter;

import java.util.Date;

@Getter
public class SQLStringParser {

    private String date;
    private double temp;

    public void parse(String sqlQuery){
        String[] buffer = sqlQuery.split(";");
        temp = Double.parseDouble(buffer[2]);
        Date queryDate = new java.util.Date(Long.parseLong(buffer[0])*1000);
        buffer = queryDate.toString().split(" ");
        date = buffer[1] + " " + buffer[5];
    }

}
