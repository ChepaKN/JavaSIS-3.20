package com.chepa.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void createWeatherTable(){
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS WeatherData (Date integer," +
				"City text," +
				"Temp text, " +
				"Wind text," +
				"Humidity text )");
	}

}
