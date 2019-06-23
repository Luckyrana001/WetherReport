package com.weather.report.model;

public class Main {

    double temp;
    int pressure;
    int humidity;
    double temp_min;
    double temp_max;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
