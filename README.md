# Weather Forecast

Service for getting weather forecast from `openweathermap.org` with microservices architecture.

## Instructions

* Clone the repo
* Build every module separately
* In order to get forecast from `openweathermap.org` you need to have a token from their api.
* Then when you run this service on local machine, you need to create `weather-forecast-loader.properties` under `${HOME}/config` directory. 
* Next within `weather-forecast-loader.properties` put property `app.id=YOUR_KEY` where `YOUR_KEY` is a key from `openweathermap.org`.

## How to run

* First run `weather-forecast-eureka`
* Next run `weather-forecast-config-server`
* Next sequence of builds can be made in any order
* Then go to `localhost:8080/client/start`
* Next you can check available cities under `localhost:8080/client/cities`
* Next to check weather forecast for 
available cities go to `localhost:8080/client/forecast/search?city=YOUR_CITY` where `YOUR_CITY` is one from available cities.

## Contributor

DL
