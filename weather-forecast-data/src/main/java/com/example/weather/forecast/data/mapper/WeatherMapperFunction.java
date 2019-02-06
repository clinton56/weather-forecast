package com.example.weather.forecast.data.mapper;

import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.CityDTO;
import com.example.weather.forecast.data.model.WeatherResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class WeatherMapperFunction implements Function<WeatherResponseDTO, List<WeatherForecast>> {

    private final Function<CityDTO, City> cityMapperFunction;

    @Override
    public List<WeatherForecast> apply(WeatherResponseDTO weatherResponseDTO) {
        City city = cityMapperFunction.apply(weatherResponseDTO.getCity());
        return weatherResponseDTO.getList().stream()
                .map(it -> new WeatherForecast(it.getDt_txt(), it.getMain().getTemp(),
                        it.getMain().getTemp_min(), it.getMain().getTemp_max(), it.getMain().getPressure(),
                        it.getMain().getHumidity(), it.getWind().getSpeed(), city))
                .collect(Collectors.toList());
    }
}
