package com.weather.report.model;

public class MainDataModel {

    /* {
        "temp": 283.45,
        "pressure": 1025,
        "humidity": 71,
        "temp_min": 282.04,
        "temp_max": 285.37
    }*/

    String temp;
    String pressure;
    String humidity;
    String temp_min;
    String temp_max;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }



}
