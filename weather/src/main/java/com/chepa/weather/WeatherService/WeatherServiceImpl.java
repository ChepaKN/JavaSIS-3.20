package com.chepa.weather.WeatherService;

import com.chepa.weather.dto.WeatherDTO;
import com.chepa.weather.sql.SQLData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private static Logger logger
            = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Value("${host}")
    private String apiHost;

    @Value("${key}")
    private String apiKey;

    @Value("${url}")
    private String apiURL;

    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private boolean transactionIsValid(ResponseEntity<WeatherDTO> response){

        //Метод проверяет ключевые поля на достоверность данных:
        String code = Objects.requireNonNull(response.getBody()).getCod();
        if(!code.equals(Integer.toString(HttpStatus.OK.value()))){
            return false;
        }
        //дата
        long time = (response.getHeaders().getFirstDate("Date"));
        if(time == -1){
            return false;
        }
        //Город
        Optional<String> city = Optional.ofNullable(Objects.requireNonNull(response.getBody()).getName());
        if(!city.isPresent()){
           return false;
        }
        //Температура
        Optional<String> temperature = Optional.ofNullable(response.getBody().getMain().getTemp());

        //Остальные поля необязательные, проверим их при заполнении таблицы
        return temperature.isPresent();
    }

    @Override
    public SQLData getWeather(String targetCity) {

        String url = String.format("%s%s", apiURL, targetCity);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", apiHost);
        headers.set("x-rapidapi-key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<WeatherDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherDTO.class);

        SQLData sqlData;
        if(transactionIsValid(response)){
            sqlData = new SQLData().fillSqlDataFromDto(Objects.requireNonNull(response.getBody()));
            logger.info(sqlData.toString());
            return sqlData;
        }else {
            logger.warn("Запрос к сервису завершился некорректно.");
            throw new RuntimeException("Запрос к сервису завершился некорректно, пожалуйста, проверьте исходные данные.");
        }
    }
}
