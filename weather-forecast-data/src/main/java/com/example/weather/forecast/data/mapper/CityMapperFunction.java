package com.example.weather.forecast.data.mapper;

import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.model.CityDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CityMapperFunction implements Function<CityDTO, City> {
    @Override
    public City apply(CityDTO city) {
        return new City(city.getId(), city.getName(), city.getCountry());
    }
}
