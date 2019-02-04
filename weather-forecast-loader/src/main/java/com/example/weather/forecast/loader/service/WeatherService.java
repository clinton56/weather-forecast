package com.example.weather.forecast.loader.service;

import com.example.weather.forecast.loader.model.WeatherResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@FunctionalInterface
public interface WeatherService {

    WeatherResponse getDailyFor(City city);

    @Getter
    @ToString
    @AllArgsConstructor
    public enum City {
        KRAKOW("Krakow", "pl"),
        LONDON("London", "gb"),
        WARSAW("Warsaw", "pl"),
        MADRID("Madrid", "es");

        private final String name;
        private final String country;

        public static City getCity(String cityName) {
            for (City city : values()) {
                if (city.name.equalsIgnoreCase(cityName)) {
                    return city;
                }
            }
            return City.KRAKOW;
        }
    }
}
