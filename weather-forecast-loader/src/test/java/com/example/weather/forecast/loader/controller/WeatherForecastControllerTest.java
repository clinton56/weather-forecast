package com.example.weather.forecast.loader.controller;

import com.example.weather.forecast.loader.model.WeatherResponse;
import com.example.weather.forecast.loader.service.WeatherService;
import org.apache.commons.lang3.StringUtils;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

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

    @Test
    public void testFallbackMethodForOneCity() {
        // given - when
        Throwable throwableMock = Mockito.mock(Throwable.class);
        given(throwableMock.getMessage()).willReturn("Some error message");
        ResponseEntity<WeatherResponse> result = controller.weatherResponseSingleCityFallback(StringUtils.EMPTY, throwableMock);

        // then
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
        verify(throwableMock, times(ONCE)).getMessage();
    }

    @Test
    public void testFallbackMethodForOneCityWhenThrowableIsNull() {
        // given - when
        ResponseEntity<WeatherResponse> result = controller.weatherResponseSingleCityFallback(StringUtils.EMPTY, null);

        // then
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    public void testGettingForecastForAllCitiesHappyPath() {
        // given
        given(weatherServiceMock.getDailyFor(WeatherService.City.LONDON)).willReturn(weatherResponseMock);
        given(weatherServiceMock.getDailyFor(WeatherService.City.KRAKOW)).willReturn(weatherResponseMock);
        given(weatherServiceMock.getDailyFor(WeatherService.City.WARSAW)).willReturn(weatherResponseMock);
        given(weatherServiceMock.getDailyFor(WeatherService.City.MADRID)).willReturn(weatherResponseMock);
        given(weatherResponseMock.isSuccess()).willReturn(true).willReturn(true).willReturn(true).willReturn(true);

        // when
        ResponseEntity<List<WeatherResponse>> result = controller.getWeatherForecastForAllCities();

        // then
        assertThat(result.getBody(), is(notNullValue()));
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.OK)));
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.LONDON);
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.KRAKOW);
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.WARSAW);
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.MADRID);
    }

    @Test
    public void testGettingForecastForAllCitiesWhenResponseIsNull() {
        // given
        given(weatherServiceMock.getDailyFor(WeatherService.City.LONDON)).willReturn(null);
        given(weatherServiceMock.getDailyFor(WeatherService.City.KRAKOW)).willReturn(null);
        given(weatherServiceMock.getDailyFor(WeatherService.City.WARSAW)).willReturn(null);
        given(weatherServiceMock.getDailyFor(WeatherService.City.MADRID)).willReturn(null);

        // when
        ResponseEntity<List<WeatherResponse>> result = controller.getWeatherForecastForAllCities();

        // then
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.LONDON);
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.WARSAW);
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.KRAKOW);
        verify(weatherServiceMock, times(ONCE)).getDailyFor(WeatherService.City.MADRID);
    }

    @Test
    public void testFallbackMethodForAllCities() {
        // given - when
        Throwable throwableMock = Mockito.mock(Throwable.class);
        given(throwableMock.getMessage()).willReturn("Some error message");
        ResponseEntity<List<WeatherResponse>> result = controller.weatherResponseAllCitiesFallback(throwableMock);

        // then
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
        verify(throwableMock, times(ONCE)).getMessage();
    }

    @Test
    public void testFallbackMethodForAllCitiesWhenThrowableIsNull() {
        // given - when
        ResponseEntity<List<WeatherResponse>> result = controller.weatherResponseAllCitiesFallback(null);

        // then
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

}