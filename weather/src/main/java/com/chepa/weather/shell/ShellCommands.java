package com.chepa.weather.shell;

import com.chepa.weather.WeatherService.WeatherService;
import com.chepa.weather.data.WeatherDataService;
import com.chepa.weather.sql.SQLData;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@ShellComponent
public class ShellCommands {

    public ShellCommands(WeatherService weatherService, WeatherDataService weatherDataService) {
        this.weatherService = weatherService;
        this.weatherDataService = weatherDataService;
    }

    private WeatherService weatherService;
    private final WeatherDataService weatherDataService;

    @ShellMethod("Запросить погоду")
    public String get(
        @ShellOption(defaultValue = "Minusinsk") String targetCity){
        SQLData sqlData = weatherService.getWeather(targetCity);

        //Сохраним результат в базу
        weatherDataService.save(sqlData);
        return Objects.requireNonNull(sqlData).toString();
    }

    @ShellMethod("Загрузить записи из базы")
    public String show(){
        List<String> fields = weatherDataService.getAll();
        StringBuilder toReturn = new StringBuilder();
        String[] str;
        for(String f : fields){
            str = f.split(";");
            str[0] = new java.util.Date(Long.parseLong(str[0])*1000).toString();
            str[2] = "Ветер " + str[2] + "m/s";
            str[3] = "Влажность: " + str[3] + "%";
            toReturn.append(Arrays.toString(str)).append(System.lineSeparator());
        }
        return toReturn.toString();
    }

    @ShellMethod("Среднемесячная погода. Введите Город, интервал месяцев в формате: 'dd-MM-yyyy  dd-MM-yyyy'")
    public String getAverage(
            @ShellOption(defaultValue = "Available") String targetCity,
            @ShellOption(defaultValue = "01-01-1970") String startMonth,
            @ShellOption(defaultValue = "01-01-2070") String stopMonth){
        StringBuilder toReturn = new StringBuilder();
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startMonth, FORMATTER);
        LocalDate stop = LocalDate.parse(stopMonth, FORMATTER);

        if(targetCity.equals("Available")){
            List<String> availableCities = weatherDataService.getCityList();
            for(String city : availableCities){
                toReturn.append(weatherDataService.getAverageWeather(city, start, stop));
            }
        }else{
            toReturn = new StringBuilder(weatherDataService.getAverageWeather(targetCity, start, stop));
        }
        return toReturn.toString();
    }
}

