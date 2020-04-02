package com.chepa.weather.WeatherService;

import com.chepa.weather.dto.weatherDTO;
import com.chepa.weather.meteo.MeteoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private static Logger logger
            = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private MeteoService meteoService;

    @Value("${host}")
    private String apiHost;

    @Value("${key}")
    private String apiKey;

    @Value("${url}")
    private String apiURL;

    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.meteoService = new MeteoService();
    }


    @Override
    public String getWeather(String targetCity) {

        String toReturn = "";
        String url = String.format("%s%s", apiURL, targetCity);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", apiHost);
        headers.set("x-rapidapi-key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<weatherDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, weatherDTO.class);

        String code = Objects.requireNonNull(response.getBody()).getCod();
        if(code.equals("200")){
            toReturn = DTO2stringConverter(response);
        }else toReturn = String.format("Incorrect response from the service code=%s", code);

        return toReturn;

    }

    private String toSignedTemp(String input){
        double output = Double.parseDouble(input);
        if(output > 0) return "+" + output;
        else return Double.toString(output);
    }

    private String DTO2stringConverter(ResponseEntity<weatherDTO> response){

        StringBuilder toReturn = new StringBuilder();
        String customSeparator = ";  ";

        //Время + дата
        toReturn.append(new java.util.Date(response.getHeaders().getFirstDate("Date")))
                .append(customSeparator);
        //Город + погода
        toReturn.append(Objects.requireNonNull(response.getBody()).getName())
                .append(": ")
                .append(toSignedTemp(response.getBody().getMain().getTemp()))
                .append(customSeparator);
        //Ветер (Направление + скорость)
        toReturn.append("Ветер: ").append(meteoService.degreeToWindDirection(response.getBody().getWind().getDeg()))
                .append(" ")
                .append(response.getBody().getWind().getSpeed())
                .append(" м/с")
                .append(customSeparator);
        //Влажность
        toReturn.append("Влажность: ")
                .append(response.getBody().getMain().getHumidity())
                .append("%")
                .append(customSeparator);

        return toReturn.toString();
    }
}
