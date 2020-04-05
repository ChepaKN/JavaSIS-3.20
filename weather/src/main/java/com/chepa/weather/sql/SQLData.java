package com.chepa.weather.sql;

import com.chepa.weather.dto.weatherDTO;
import com.chepa.weather.meteo.MeteoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

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
                "Wind " + wind + "[m/s]" + customSeparator +
                "Humidity: " + humidity + "%";
    }

    private String toSignedTemp(String input){
        double output = Double.parseDouble(input);
        if(output > 0) return "+" + output;
        else return Double.toString(output);
    }

    public SQLData fillSqlDataFromDto(ResponseEntity<weatherDTO> response) {
        SQLData sqlData = new SQLData();
        MeteoService meteoService = new MeteoService();

        //дата
        sqlData.setDate(response.getHeaders().getFirstDate("Date")/1000);
        //Город
        sqlData.setCity(Objects.requireNonNull(response.getBody()).getName());
        //Температура
        sqlData.setTemperature(toSignedTemp(response.getBody().getMain().getTemp()));

        //Отсутствие следующих полей не фатально, если что, запишем туда "NAN":
        //Ветер
        Optional<String> windDirection = Optional.ofNullable(response.getBody().getWind().getDeg());
        Optional<String> windSpeed = Optional.ofNullable(response.getBody().getWind().getSpeed());
        if(windDirection.isPresent() && windSpeed.isPresent()){
            sqlData.setWind(meteoService.degreeToWindDirection(windDirection.get()) + ": " +  windSpeed.get());
        }else{
            sqlData.setWind("NAN");
        }
        //Влажность
        Optional<String> airHumidity = Optional.ofNullable(response.getBody().getMain().getHumidity());
        if(airHumidity.isPresent()){
            sqlData.setHumidity(airHumidity.get());
        }else{
            sqlData.setHumidity("NAN");
        }
        return sqlData;
    }
}
