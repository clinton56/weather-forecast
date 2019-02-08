package com.example.weather.forecast.client.controller;

import com.example.weather.forecast.client.model.WeatherForecast;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RequestMapping(path = "/client")
@RestController
public class ClientController {

    private final RestTemplate restTemplate;

    @Autowired
    public ClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(path = "/start")
    public ResponseEntity<String> start() {
        return restTemplate.exchange("http://localhost:5005/data/update", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                });

    }

    @RequestMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> cities() {
        ResponseEntity<List<String>> result = restTemplate.exchange("http://localhost:5005/data/cities", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {
                });
        if (CollectionUtils.isNotEmpty(result.getBody())) {
            return new ResponseEntity<>(result.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);

    }

    @RequestMapping(path = "/forecast/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeatherForecast>> forecastForCity(@RequestParam("city") String city) {
        ResponseEntity<List<WeatherForecast>> result = restTemplate.exchange("http://localhost:5005/data/search/city?city={city}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherForecast>>() {
                }, city);
        if (CollectionUtils.isNotEmpty(result.getBody())) {
            return new ResponseEntity<>(result.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);

    }

}
