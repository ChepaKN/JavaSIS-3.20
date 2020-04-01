package com.chepa.weather.data;

import com.chepa.weather.dto.weatherDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WeatherDataService {
    void save(String weather);
    List<String> getAll();
}
