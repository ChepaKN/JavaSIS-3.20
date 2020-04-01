package com.chepa.weather.shell;

import com.chepa.weather.WeatherService.WeatherService;
import com.chepa.weather.data.WeatherDataService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.stream.Collectors;

@ShellComponent
public class ShellCommands {

    public ShellCommands(WeatherService weatherService, WeatherDataService weatherDataService) {
        this.weatherService = weatherService;
        this.weatherDataService = weatherDataService;
    }

    private WeatherService weatherService;
    private final WeatherDataService weatherDataService;

    @ShellMethod("Запросить погоду")
    public String weather(
        @ShellOption(defaultValue = "Minusinsk") String targetCity){
        String lastRequest = weatherService.getWeather(targetCity);

        //Update database if request finish is correctly
        if(!lastRequest.contains("Incorrect response")){
            weatherDataService.save(lastRequest);
        }
        return lastRequest;
    }

    @ShellMethod("Загрузить записи из базы")
    public String show(){
        return weatherDataService.getAll().stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

