package com.example.weather.forecast.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WeatherForecastClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherForecastClientApplication.class, args);
    }

}

