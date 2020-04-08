package com.chepa.weather.data;

import com.chepa.weather.sql.SQLData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;

class WeatherDataServiceJdbcImplTest {
    @Autowired
    WeatherDataService weatherDataService = new WeatherDataServiceJdbcImpl(new JdbcTemplate());

    @Test
    void fillDataBaseTest(){
      /*  int secPerYear = 31536000;
        double temp = 36.6;
        WeatherDataService weatherDataService = new WeatherDataServiceJdbcImpl(new JdbcTemplate());
        SQLData sqlData = new SQLData();
        sqlData.setDate(Instant.now().getEpochSecond() +
                (long) (Math.random() * secPerYear) - secPerYear/2);
        sqlData.setTemperature(String.valueOf(temp + Math.random()*4 - 2));
        sqlData.setCity("Astana");
        sqlData.setWind("Test field");
        sqlData.setHumidity("Test field");

        weatherDataService.save(sqlData);

       */
    }


}