package com.example.weather.forecast.loader.controller;

import com.example.weather.forecast.loader.model.WeatherResponse;
import com.example.weather.forecast.loader.service.WeatherService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WeatherForecastControllerTest {

    private static final int ONCE = 1;
    @Mock
    private WeatherService weatherServiceMock;
    @Mock
    private WeatherResponse weatherResponseMock;
    private WeatherForecastController controller;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new WeatherForecastController(weatherServiceMock);
    }

    @AfterMethod
    public void resetMocks() {
        Mockito.reset(weatherServiceMock, weatherResponseMock);
    }

    @Test
    public void testGettingForecastForOneCityHappyPath() {
        // given
        given(weatherServiceMock.getDailyFor(WeatherService.City.LONDON)).willReturn(weatherResponseMock);
        given(weatherResponseMock.isSuccess()).willReturn(true);

        // when
        ResponseEntity<WeatherResponse> result = controller.getWeatherForecastForCity(WeatherService.City.LONDON.getName());

        // then
        assertThat(result.getBody(), is(notNullValue()));
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.OK)));
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.LONDON);
    }

    @Test
    public void testGettingForecastForOneCityWhenResponseIsNull() {
        // given
        given(weatherServiceMock.getDailyFor(WeatherService.City.LONDON)).willReturn(null);

        // when
        ResponseEntity<WeatherResponse> result = controller.getWeatherForecastForCity(WeatherService.City.LONDON.getName());

        // then
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.LONDON);
    }

    @Test
    public void testGettingForecastForOneCityWhenResponseIsUnSuccessful() {
        // given
        given(weatherServiceMock.getDailyFor(WeatherService.City.LONDON)).willReturn(weatherResponseMock);
        given(weatherResponseMock.isSuccess()).willReturn(false);

        // when
        ResponseEntity<WeatherResponse> result = controller.getWeatherForecastForCity(WeatherService.City.LONDON.getName());

        // then
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.LONDON);
    }

}