package com.weather.report.model;

public class Wind {

    /*"wind": {
        "speed": 5.1,
        "deg": 240
    }*/


    String speed;
    String deg;

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }


}
