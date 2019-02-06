package com.example.weather.forecast.data.mapper;


import com.example.weather.forecast.data.entity.City;
import com.example.weather.forecast.data.model.CityDTO;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CityMapperFunctionTest {

    @Test
    public void testCityConversion() {
        // given
        long id = 0L;
        String cityName = "Krakow";
        String countryCode = "pl";
        CityDTO cityDTO = new CityDTO(id, cityName, countryCode);
        City expectedResult = new City(id, cityName, countryCode);

        // when
        City result = new CityMapperFunction().apply(cityDTO);

        // then
        assertThat(result, is(equalTo(expectedResult)));
    }

}