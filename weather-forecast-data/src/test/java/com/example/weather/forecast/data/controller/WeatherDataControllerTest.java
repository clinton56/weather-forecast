package com.example.weather.forecast.data.controller;

import com.example.weather.forecast.data.model.WeatherResponse;
import org.apache.commons.lang3.StringUtils;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class WeatherDataControllerTest {

    private static final int ONCE = 1;
    @Mock
    private RestTemplate restTemplateMock;
    private WeatherDataController controller;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new WeatherDataController(restTemplateMock, StringUtils.EMPTY);
    }

    @AfterMethod
    public void resetMocks() {
        Mockito.reset(restTemplateMock);
    }

    @Test
    public void testGettingWeatherDataHappyPath() {
        // given
        WeatherResponse weatherResponseMock = Mockito.mock(WeatherResponse.class);
        ResponseEntity<List<WeatherResponse>> responseEntityStub =
                new ResponseEntity<>(Collections.singletonList(weatherResponseMock), HttpStatus.OK);
        given(restTemplateMock.exchange(StringUtils.EMPTY, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherResponse>>() {}))
                .willReturn(responseEntityStub);

        // when
        ResponseEntity<List<WeatherResponse>> result = controller.persistWeatherForecast();

        // then
        verify(restTemplateMock, times(ONCE)).exchange(StringUtils.EMPTY, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherResponse>>() {});
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(result.getBody(), is(notNullValue()));
    }

    @Test
    public void testGettingWeatherDataResponseIsEmpty() {
        // given
        ResponseEntity<List<WeatherResponse>> responseEntityStub =
                new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        given(restTemplateMock.exchange(StringUtils.EMPTY, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherResponse>>() {}))
                .willReturn(responseEntityStub);

        // when
        ResponseEntity<List<WeatherResponse>> result = controller.persistWeatherForecast();

        // then
        verify(restTemplateMock, times(ONCE)).exchange(StringUtils.EMPTY, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WeatherResponse>>() {});
        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
        assertThat(result.getBody(), is(nullValue()));
    }

}