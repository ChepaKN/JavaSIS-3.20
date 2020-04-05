package com.chepa.weather.WeatherService;

import com.chepa.weather.sql.SQLData;

public interface WeatherService {
    SQLData getWeather(String targetCity);
}
