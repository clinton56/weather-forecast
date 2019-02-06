package com.example.weather.forecast.data.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "WEATHER_FORECAST", uniqueConstraints={
        @UniqueConstraint(columnNames = {"DATE_TIME", "CITY_ID"})
})
@EqualsAndHashCode
@ToString
@Entity
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "DATE_TIME")
    @EqualsAndHashCode.Include
    private String dateTime;
    @Column(name = "TEMPERATURE")
    @EqualsAndHashCode.Exclude
    private double temperature;
    @Column(name = "MIN_TEMPERATURE")
    @EqualsAndHashCode.Exclude
    private double minTemperature;
    @Column(name = "MAX_TEMPERATURE")
    @EqualsAndHashCode.Exclude
    private double maxTemperature;
    @Column(name = "PRESSURE")
    @EqualsAndHashCode.Exclude
    private double pressure;
    @Column(name = "HUMIDITY")
    @EqualsAndHashCode.Exclude
    private int humidity;
    @EqualsAndHashCode.Exclude
    @Column(name = "WIND_SPEED")
    private double windSpeed;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CITY_ID", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
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


