package com.example.weather.forecast.data.controller;

import com.example.weather.forecast.data.entity.WeatherForecast;
import com.example.weather.forecast.data.model.WeatherResponseDTO;
import com.example.weather.forecast.data.service.WeatherService;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class WeatherDataControllerTest {

    private static final int ONCE = 1;
    private static final int ZERO = 0;
    @Mock
    private RestTemplate restTemplateMock;
    @Mock
    private WeatherService weatherServiceMock;
    private WeatherDataController controller;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new WeatherDataController(restTemplateMock, StringUtils.EMPTY, weatherServiceMock);
    }

    @AfterMethod
    public void resetMocks() {
        Mockito.reset(restTemplateMock, weatherServiceMock);
    }

    @Test
    public void testGettingWeatherDataHappyPath() {
        // given
        WeatherResponseDTO weatherResponseDTOMock = Mockito.mock(WeatherResponseDTO.class);
        ResponseEntity<List<WeatherResponseDTO>> responseEntityStub =
                new ResponseEntity<>(Collections.singletonList(weatherResponseDTOMock), HttpStatus.OK);
        given(restTemplateMock.exchange(StringUtils.EMPTY, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherResponseDTO>>() {}))
                .willReturn(responseEntityStub);

        // when
        ResponseEntity<String> result = controller.persistWeatherForecast();

        // then
        verify(restTemplateMock, times(ONCE)).exchange(StringUtils.EMPTY, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherResponseDTO>>() {});
        verify(weatherServiceMock, times(ONCE)).save(responseEntityStub.getBody());
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(result.getBody(), is(notNullValue()));
    }

    @Test
    public void testGettingWeatherDataResponseIsEmpty() {
        // given
        ResponseEntity<List<WeatherResponseDTO>> responseEntityStub =
                new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        given(restTemplateMock.exchange(StringUtils.EMPTY, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherResponseDTO>>() {}))
                .willReturn(responseEntityStub);

        // when
        ResponseEntity<String> result = controller.persistWeatherForecast();

        // then
        verify(restTemplateMock, times(ONCE)).exchange(StringUtils.EMPTY, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherResponseDTO>>() {});
        verify(weatherServiceMock, times(ZERO)).save(responseEntityStub.getBody());
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
        assertThat(result.getBody(), is(equalTo("FAILURE")));
    }

    @Test
    public void testFallbackMethodWhenError() {
        // given - when
        Throwable throwableMock = Mockito.mock(Throwable.class);
        given(throwableMock.getMessage()).willReturn("Some error message");
        ResponseEntity<String> result = controller.weatherDataFallback(throwableMock);

        // then
        assertThat(result.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
        verify(throwableMock, times(ONCE)).getMessage();
    }

    @Test
    public void testFallbackMethodWhenThrowableIsNull() {
        // given - when
        ResponseEntity<String> result = controller.weatherDataFallback(null);

        // then
        assertThat(result.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    public void testSearchingForecastByCityHappyPath() {
        // given
        String city = "London";
        WeatherForecast weatherForecastMock = Mockito.mock(WeatherForecast.class);
        given(weatherServiceMock.findByCity(city)).willReturn(Collections.singletonList(weatherForecastMock));

        // when
        ResponseEntity<List<WeatherForecast>> result = controller.searchForecastByCity(city);

        // then
        verify(weatherServiceMock, times(ONCE)).findByCity(city);
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    public void testSearchingForecastByCityNoResults() {
        // given
        String city = "London";
        given(weatherServiceMock.findByCity(city)).willReturn(Collections.emptyList());

        // when
        ResponseEntity<List<WeatherForecast>> result = controller.searchForecastByCity(city);

        // then
        verify(weatherServiceMock, times(ONCE)).findByCity(city);
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
    }

}