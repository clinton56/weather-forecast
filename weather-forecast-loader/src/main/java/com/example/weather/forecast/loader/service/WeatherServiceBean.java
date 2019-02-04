package com.example.weather.forecast.loader.service;

import com.example.weather.forecast.loader.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherServiceBean implements WeatherService {

    private final RestTemplate restTemplate;
    @Value("${app.id}")
    private String appId;

    @Autowired
    public WeatherServiceBean(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherResponse getDailyFor(City city) {
        return restTemplate.getForObject("http://api.openweathermap.org/data/2.5/forecast?q={city},{country}&mode=json&appid={appId}&units=metric", WeatherResponse.class, city.getName(), city.getCountry(), appId);
    }
}
