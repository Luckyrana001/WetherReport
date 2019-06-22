package com.weather.report.model;

public class WeatherApiErrorResponseModel {


    int cod;
    String message;

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
