package com.chepa.weather.data;

import com.chepa.weather.sql.SQLData;

import java.time.LocalDate;
import java.util.List;

public interface WeatherDataService {
    void save(SQLData sqlData);
    List<String> getAll();
    String getAverageWeather(String city, LocalDate startDate, LocalDate stopDate);
}
