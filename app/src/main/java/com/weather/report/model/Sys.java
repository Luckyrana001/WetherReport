package com.weather.report.model;

public class Sys {


    String country;
    int timezone;
    String sunrise, sunset;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
