package com.example.weather.forecast.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class WeatherForecastRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastRegistryApplication.class, args);
	}

}

