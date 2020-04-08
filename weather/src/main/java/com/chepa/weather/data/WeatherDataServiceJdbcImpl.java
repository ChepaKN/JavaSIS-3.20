package com.chepa.weather.data;

import com.chepa.weather.sql.SQLData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class WeatherDataServiceJdbcImpl implements WeatherDataService {

   private final JdbcTemplate jdbcTemplate;

    public WeatherDataServiceJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Value("${zoneId}")
    private String zoneIdentifier;

    @Override
    public void save(SQLData sqlData) {
        jdbcTemplate.update("INSERT INTO WeatherData (Date, City, Temp, Wind, Humidity) VALUES (?, ?, ?, ?, ?)",
                sqlData.getDate(),
                sqlData.getCity(),
                sqlData.getTemperature(),
                sqlData.getWind(),
                sqlData.getHumidity());
    }

    @Override
    public List<String> getAll() {
        String rowSeparator = "; ";
        return jdbcTemplate.query("SELECT * FROM WeatherData",
                (rs, rowNum) -> rs.getString("Date") + rowSeparator +
                        rs.getString("City") + ": " +
                        rs.getString("Temp") + rowSeparator +
                        rs.getString("Wind") + rowSeparator +
                        rs.getString("Humidity"));
    }

    @Override
    public String getAverageWeather(String city, LocalDate startDate, LocalDate stopDate){

        List<String> dbQuery = queryDataBase(city, startDate, stopDate);
        if(dbQuery.isEmpty()){
            throw new RuntimeException("There are no entries in the database that satisfy the condition.");
        }

        return city + ": " + buildStringFromDB(dbQuery);

    }

    @Override
    public List<String> getCityList(){
        return jdbcTemplate.query("SELECT DISTINCT  City FROM WeatherData",
                (rs, rowNum) -> rs.getString("City"));
    }

    private List<String> queryDataBase(String city, LocalDate startDate, LocalDate stopDate){
        ZoneId zoneId = ZoneId.of(zoneIdentifier);
        long startDateUnix = Date.from(startDate.atStartOfDay(zoneId).toInstant()).getTime()/1000;
        long stopDateUnix = Date.from(stopDate.atStartOfDay(zoneId).toInstant()).getTime()/1000;

        if(stopDateUnix <= startDateUnix) {
            throw new RuntimeException("Incorrect time interval");
        }

        String rowSeparator = "; ";
        String sqlQuery = String.format("SELECT * FROM WeatherData WHERE City = '%s' AND Date >= %d AND Date <= %d",
                city,
                startDateUnix,
                stopDateUnix);

        return jdbcTemplate.query(sqlQuery,
                (rs, rowNum) -> rs.getString("Date") + rowSeparator +
                        rs.getString("City") + "; " +
                        rs.getString("Temp") + rowSeparator +
                        rs.getString("Wind") + rowSeparator +
                        rs.getString("Humidity"));
    }

    private String buildStringFromDB(List<String> dbQuery){
        Map<String, Double> monthTempContainer = new HashMap<>();
        Map<String, Integer> monthCountContainer = new HashMap<>();
        List<String> keys = new ArrayList<>();

        SQLStringParser sqlStringParser = new SQLStringParser();

        String key;
        for(String q : dbQuery){
            sqlStringParser.parse(q);
            key = sqlStringParser.getDate();

            if(!keys.contains(key)){
                keys.add(key);
            }
            if(monthTempContainer.containsKey(key)){
                monthTempContainer.put(key,
                        monthTempContainer.get(key) +
                                sqlStringParser.getTemp());
                monthCountContainer.put(key,monthCountContainer.get(key) + 1);
            }else{
                monthTempContainer.put(sqlStringParser.getDate(),
                        sqlStringParser.getTemp());
                monthCountContainer.put(key, 1);
            }
        }

        StringBuilder toReturn = new StringBuilder();
        for(String k : keys){
            toReturn.append(k).append(": ");
            toReturn.append(monthTempContainer.get(k) / monthCountContainer.get(k));
            toReturn.append(System.lineSeparator());
        }
        return toReturn.toString();
    }

    public void fillDataBase(){
        int secPerYear = 31536000;
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
    }

}
