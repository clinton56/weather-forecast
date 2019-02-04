package com.example.weather.forecast.data.controller;

import com.example.weather.forecast.data.model.WeatherResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@RestController
public class WeatherDataController {

    private final RestTemplate restTemplate;
    private final String forecastUrl;

    @Autowired
    public WeatherDataController(RestTemplate restTemplate, @Value(value = "${forecast.data.url}") final String forecastUrl) {
        this.restTemplate = restTemplate;
        this.forecastUrl = forecastUrl;
    }

    @RequestMapping(path = "/weather/forecast/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeatherResponse>> persistWeatherForecast() {
        ResponseEntity<List<WeatherResponse>> response = restTemplate.exchange(
                forecastUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WeatherResponse>>() {
                });
        return Optional.ofNullable(response.getBody())
                .filter(CollectionUtils::isNotEmpty)
                .map(it -> new ResponseEntity<>(it, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
