package com.example.weather.forecast.data.mapper;

import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.CityModel;
import com.example.weather.forecast.data.model.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class WeatherMapperFunction implements Function<WeatherResponse, List<WeatherForecast>> {

    private final Function<CityModel, City> cityMapperFunction;

    @Override
    public List<WeatherForecast> apply(WeatherResponse weatherResponse) {
        City city = cityMapperFunction.apply(weatherResponse.getCity());
        return weatherResponse.getList().stream()
                .map(it -> new WeatherForecast(it.getDt_txt(), it.getMain().getTemp(),
                        it.getMain().getTemp_min(), it.getMain().getTemp_max(), it.getMain().getPressure(),
                        it.getMain().getHumidity(), it.getWind().getSpeed(), city))
                .collect(Collectors.toList());
    }
}
