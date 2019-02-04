package com.example.weather.forecast.data.mapper;

import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.model.CityModel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CityMapperFunction implements Function<CityModel, City> {
    @Override
    public City apply(CityModel city) {
        return new City(city.getId(), city.getName(), city.getCountry());
    }
}
