package com.chepa.weather.WeatherService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.web.client.RestTemplate;

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
        String getWeather = weatherService.getWeather(targetCity);
        assertTrue(getWeather.contains(targetCity));
    }
}