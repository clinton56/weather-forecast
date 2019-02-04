package com.example.weather.forecast.loader.controller;

import com.example.weather.forecast.loader.model.WeatherResponse;
import com.example.weather.forecast.loader.service.WeatherService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
public class WeatherForecastController {

    private final WeatherService weatherService;

    @HystrixCommand(fallbackMethod = "weatherResponseSingleCityFallback")
    @RequestMapping(value = "/weather/forecast/city", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> getWeatherForecastForCity(@RequestParam String city) {
        WeatherResponse weatherResponse = weatherService.getDailyFor(WeatherService.City.getCity(city));
        return Optional.ofNullable(weatherResponse)
                .filter(WeatherResponse::isSuccess)
                .map(it -> new ResponseEntity<>(it, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<WeatherResponse> weatherResponseSingleCityFallback(String city, Throwable e) {
        Optional.ofNullable(e)
                .map(Throwable::getMessage)
                .ifPresent(log::error);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
