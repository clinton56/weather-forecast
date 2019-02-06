package com.example.weather.forecast.data.service;


import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.CityDTO;
import com.example.weather.forecast.data.model.WeatherResponseDTO;
import com.example.weather.forecast.data.repository.CityRepository;
import com.example.weather.forecast.data.repository.WeatherRepository;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import java.util.List;
import java.util.function.Function;

public class WeatherServiceBeanTest {

    @Mock
    private Function<CityDTO, City> cityMapperFunctionMock;
    @Mock
    private Function<WeatherResponseDTO, List<WeatherForecast>> weatherMapperFunctionMock;
    @Mock
    private CityRepository cityRepositoryMock;
    @Mock
    private WeatherRepository weatherRepositoryMock;
    private WeatherService service;


    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new WeatherServiceBean(cityMapperFunctionMock, weatherMapperFunctionMock, cityRepositoryMock, weatherRepositoryMock);
    }

    @AfterMethod
    public void restMocks() {
        Mockito.reset(cityMapperFunctionMock, weatherMapperFunctionMock, cityRepositoryMock, weatherRepositoryMock);
    }


}