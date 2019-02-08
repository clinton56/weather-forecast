package com.example.weather.forecast.data.controller;

import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.WeatherResponseDTO;
import com.example.weather.forecast.data.service.WeatherService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/data")
public class WeatherDataController {

    private final RestTemplate restTemplate;
    private final String forecastUrl;
    private final WeatherService weatherService;

    @Autowired
    public WeatherDataController(RestTemplate restTemplate, @Value(value = "${forecast.data.url}") final String forecastUrl, WeatherService weatherService) {
        this.restTemplate = restTemplate;
        this.forecastUrl = forecastUrl;
        this.weatherService = weatherService;
    }

    @HystrixCommand(fallbackMethod = "weatherDataFallback")
    @RequestMapping(path = "/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> persistWeatherForecast() {
        ResponseEntity<List<WeatherResponseDTO>> response = restTemplate.exchange(
                forecastUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WeatherResponseDTO>>() {
                });
        return Optional.ofNullable(response.getBody())
                .filter(CollectionUtils::isNotEmpty)
                .map(it -> {
                    weatherService.save(it);
                    return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>("FAILURE", HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/cities")
    public ResponseEntity<List<String>> getAvailableCities() {
        List<String> cities = weatherService.getAvailableCities();
        if (CollectionUtils.isNotEmpty(cities)) {
            return new ResponseEntity<>(cities, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/search/city")
    public ResponseEntity<List<WeatherForecast>> searchForecastByCity(@Param("city") String city) {
        List<WeatherForecast> weatherForecastList = weatherService.findByCity(city);
        if (CollectionUtils.isNotEmpty(weatherForecastList)) {
            return new ResponseEntity<>(weatherForecastList, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> weatherDataFallback(Throwable e) {
        Optional.ofNullable(e)
                .map(Throwable::getMessage)
                .ifPresent(log::error);
        return new ResponseEntity<>("FAILURE", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
