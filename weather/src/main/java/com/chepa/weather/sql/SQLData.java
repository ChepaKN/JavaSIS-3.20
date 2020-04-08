package com.chepa.weather.sql;

import com.chepa.weather.dto.WeatherDTO;
import com.chepa.weather.meteo.MeteoService;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
public class SQLData {
    private long    date;
    private String  city;
    private String  temperature;
    private String  wind;
    private String  humidity;

    @Override
    public String toString(){
        String customSeparator = "; ";
        return new java.util.Date(date * 1000) + customSeparator +
                city + ": " +
                temperature + customSeparator +
                "Wind " + wind + " m/s" + customSeparator +
                "Humidity: " + humidity + "%";
    }

    public SQLData fillSqlDataFromDto(WeatherDTO response) {
        SQLData sqlData = new SQLData();
        MeteoService meteoService = new MeteoService();

        //дата
        LocalDateTime localDateTime = LocalDateTime.now();
        long timeInSeconds = localDateTime.toEpochSecond(ZoneOffset.UTC);
        sqlData.setDate(timeInSeconds);
        //Город
        sqlData.setCity(Objects.requireNonNull(response.getName()));
        //Температура
        sqlData.setTemperature(response.getMain().getTemp());

        //Отсутствие следующих полей не фатально, если что, запишем туда "NAN":
        //Ветер
        Optional<String> windDirection = Optional.ofNullable(response.getWind().getDeg());
        Optional<String> windSpeed = Optional.ofNullable(response.getWind().getSpeed());
        if(windDirection.isPresent() && windSpeed.isPresent()){
            sqlData.setWind(meteoService.degreeToWindDirection(windDirection.get()) + ": " +  windSpeed.get());
        }else{
            sqlData.setWind("NAN");
        }
        //Влажность
        Optional<String> airHumidity = Optional.ofNullable(response.getMain().getHumidity());
        if(airHumidity.isPresent()){
            sqlData.setHumidity(airHumidity.get());
        }else{
            sqlData.setHumidity("NAN");
        }
        return sqlData;
    }
}
