package com.example.weather.forecast.loader.service;


import com.example.weather.forecast.loader.model.WeatherResponse;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WeatherServiceBeanTest {

    @Test
    public void testGettingForecastHappyPath() {
        // given
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        WeatherService service = new WeatherServiceBean(restTemplateMock);
        WeatherResponse weatherResponseMock = Mockito.mock(WeatherResponse.class);
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
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        WeatherService service = new WeatherServiceBean(restTemplateMock);
        given(restTemplateMock.getForObject(any(), any(), any(),
                any(), any())).willThrow(RestClientException.class);

        // when
        service.getDailyFor(WeatherService.City.KRAKOW);

        // then
        fail("Should throw exception.");
    }

}