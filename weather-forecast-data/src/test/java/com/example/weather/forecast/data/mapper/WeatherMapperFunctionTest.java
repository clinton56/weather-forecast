package com.example.weather.forecast.data.mapper;

import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class WeatherMapperFunctionTest {

    @Mock
    private Function<CityDTO, City> cityMapperMock;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWeatherForecastConversion() {
        // given
        long id = 0L;
        String cityName = "Krakow";
        String countryCode = "pl";
        int cod = 200;
        double temp = 2.0;
        double tempMin = 1.0;
        double tempMax = 3.0;
        double pressure = 995.60;
        int humidity = 80;
        double windSpeed = 4.5;
        String timeStamp = "2019-02-04 21:00:00";
        WeatherForecastDTO weatherForecastDTO = new WeatherForecastDTO(temp, tempMin, tempMax, pressure, humidity);
        WeatherDataDTO weatherDataDTO = new WeatherDataDTO(weatherForecastDTO, new WindDTO(windSpeed), timeStamp);
        CityDTO cityDTO = new CityDTO(id, cityName, countryCode);
        City city = new City(id, cityName, countryCode);
        WeatherResponseDTO weatherResponseDTO = new WeatherResponseDTO(cod, Collections.singletonList(weatherDataDTO), cityDTO);
        given(cityMapperMock.apply(cityDTO)).willReturn(city);
        WeatherForecast expectedResult = new WeatherForecast(timeStamp, temp, tempMin, tempMax, pressure, humidity, windSpeed, city);

        // when
        List<WeatherForecast> result = new WeatherMapperFunction(cityMapperMock).apply(weatherResponseDTO);

        // then
        assertThat(result.get(0), is(equalTo(expectedResult)));
    }
}