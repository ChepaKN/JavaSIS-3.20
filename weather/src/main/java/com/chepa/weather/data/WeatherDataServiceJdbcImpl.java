package com.chepa.weather.data;

import com.chepa.weather.dto.weatherDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDataServiceJdbcImpl implements WeatherDataService {

   private final JdbcTemplate jdbcTemplate;

    public WeatherDataServiceJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String weather) {
        jdbcTemplate.update("INSERT INTO Weather (value) VALUES (?)", weather);
    }

    @Override
    public List<String> getAll() {
        return jdbcTemplate.query("SELECT * FROM Weather",
                (rs, rowNum) -> rs.getString("value"));
    }

}
