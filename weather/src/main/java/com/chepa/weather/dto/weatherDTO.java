package com.chepa.weather.dto;

public class weatherDTO {

    public String getCod() {
        return cod;
    }

    public String getName() {
        return name;
    }


    public tempDTO getMain() {
        return main;
    }

    public windDTO getWind() {
        return wind;
    }

    public String getDate() {
        return Date;
    }

    private tempDTO main;
    private windDTO wind;
    private String cod;
    private String name;
    private String Date;

}
