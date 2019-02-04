package com.example.weather.forecast.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
public class WeatherForecastDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeatherForecastDataApplication.class, args);
    }

}

