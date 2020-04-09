package com.chepa.weather.meteo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeteoServiceTest {
    @Test
    void degreeToWindDirection() {
        MeteoService meteoService = new MeteoService();
        String direction = "241.3";
        String wd = meteoService.degreeToWindDirection(direction);
        assertEquals("WSW", wd);
    }
}