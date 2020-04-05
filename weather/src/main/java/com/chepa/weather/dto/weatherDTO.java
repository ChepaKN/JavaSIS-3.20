package com.chepa.weather.dto;

import lombok.Getter;

@Getter
public class weatherDTO {

    private tempDTO main;
    private windDTO wind;
    private String cod;
    private String name;
}
