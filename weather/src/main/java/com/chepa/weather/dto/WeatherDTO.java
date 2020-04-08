package com.chepa.weather.dto;

import lombok.Getter;

@Getter
public class WeatherDTO {

    private TempDTO main;
    private WindDTO wind;
    private String cod;
    private String name;
}
