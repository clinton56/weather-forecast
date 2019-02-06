package com.example.weather.forecast.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CITY")
@Entity
public class City {

    @Id
    @Column(name = "CITY_ID", unique = true)
    private long cityId;
    @Column(name = "CITY_NAME", unique = true)
    private String cityName;
    @Column(name = "COUNTRY_CODE")
    private String countryCode;

}
