package com.chepa.weather.data;

import java.util.List;

public interface WeatherDataService {
    void save(String weather);
    List<String> getAll();
}
