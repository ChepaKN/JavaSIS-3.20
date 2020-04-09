package com.chepa.weather.meteo;

public class MeteoService {

    private String[] windDirections = {"N", "NNE", "NE", "ENE",
                                        "E", "SE", "SE", "SSE",
                                        "S", "SSW", "SW", "WSW",
                                        "W", "WNW", "NW", "NNW"};

    public String degreeToWindDirection(String deg){
        double step = 22.5;
        double direction = Double.parseDouble(deg);
        int points = (int) (360 / step);
        int index = (int)(((direction - step/2) / step + 1) % points);
        return windDirections[index];
    }
}
