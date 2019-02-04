package com.example.weather.forecast.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "WEATHER_FORECAST", uniqueConstraints={
        @UniqueConstraint(columnNames = {"DATE_TIME", "CITY_ID"})
})
@ToString
@Entity
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "DATE_TIME")
    private String dateTime;
    @Column(name = "TEMPERATURE")
    private double temperature;
    @Column(name = "MIN_TEMPERATURE")
    private double minTemperature;
    @Column(name = "MAX_TEMPERATURE")
    private double maxTemperature;
    @Column(name = "PRESSURE")
    private double pressure;
    @Column(name = "HUMIDITY")
    private int humidity;
    @Column(name = "WIND_SPEED")
    private double windSpeed;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CITY_ID", nullable = false, updatable = false)
    private City city;

    public WeatherForecast(String dateTime, double temperature, double minTemperature,
                           double maxTemperature, double pressure, int humidity, double windSpeed, City city) {
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.city = city;
    }
}
