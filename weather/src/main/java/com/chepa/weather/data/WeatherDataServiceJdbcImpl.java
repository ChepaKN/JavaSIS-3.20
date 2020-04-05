package com.chepa.weather.data;

import com.chepa.weather.meteo.MeteoService;
import com.chepa.weather.sql.SQLData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class WeatherDataServiceJdbcImpl implements WeatherDataService {

   private final JdbcTemplate jdbcTemplate;

    public WeatherDataServiceJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    MeteoService meteoService = new MeteoService();

    @Value("${zoneId}")
    private String zoneIdentifier;

    @Override
    public void save(SQLData sqlData) {

        jdbcTemplate.update("INSERT INTO WeatherData (Date, City, Temp, Wind, Humidity) VALUES (?, ?, ?, ?, ?)",
//                sqlData.getDate().toString(),
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

        List<String> dbQuery = jdbcTemplate.query(sqlQuery,
                (rs, rowNum) -> rs.getString("Date") + rowSeparator +
                        rs.getString("City") + "; " +
                        rs.getString("Temp") + rowSeparator +
                        rs.getString("Wind") + rowSeparator +
                        rs.getString("Humidity"));

        if(dbQuery.isEmpty()){
            throw new RuntimeException("There are no entries in the database that satisfy the condition.");
        }

        double tempAccumulator = 0.0;
        String[] queryBuf;
        for(String q : dbQuery){
            queryBuf = q.split(rowSeparator);
            tempAccumulator += Double.parseDouble(queryBuf[2]);
        }
        return String.format("Average weather in %s: %5.4f", city, tempAccumulator/dbQuery.size());
    }

}
