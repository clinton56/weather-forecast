package com.example.weather.forecast.loader.service;


import com.example.weather.forecast.loader.model.WeatherResponse;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WeatherServiceBeanTest {

    @Mock
    private RestTemplate restTemplateMock;
    @Mock
    private WeatherResponse weatherResponseMock;
    private WeatherService service;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new WeatherServiceBean(restTemplateMock);
    }

    @AfterMethod
    public void resetMocks() {
        Mockito.reset(restTemplateMock, weatherResponseMock);
    }


    @Test
    public void testGettingForecastHappyPath() {
        // given
        given(restTemplateMock.getForObject(any(String.class), any(), any(),
                any(), any())).willReturn(weatherResponseMock);

        // when
        WeatherResponse result = service.getDailyFor(WeatherService.City.KRAKOW);

        // then
        assertThat(result).isNotNull();
        verify(restTemplateMock, times(1)).getForObject(any(String.class), any(), any(), any(), any());
    }

    @Test(expectedExceptions = RestClientException.class)
    public void testWhenGettingForecastThrowsException() {
        // given
        given(restTemplateMock.getForObject(any(), any(), any(),
                any(), any())).willThrow(RestClientException.class);

        // when
        service.getDailyFor(WeatherService.City.KRAKOW);

        // then
        fail("Should throw exception.");
    }

}