package com.chepa.weather.WeatherService;

import com.chepa.weather.dto.weatherDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class WeatherServiceImplTest {

    @Autowired
    WeatherServiceImpl weatherService = new WeatherServiceImpl(new RestTemplate());

    @Test
    void getWeather() {
        String targetCity = "Abakan";
//        ResponseEntity<weatherDTO> getWeather = weatherService.getWeather(targetCity);
//        assertTrue(Objects.requireNonNull(getWeather.getBody()).getName().contains(targetCity));
    }
}