package com.weather.report.model;

public class WeatherDataList {

    /*"weather": [
        {
            "id": 520,
            "main": "Rain",
            "description": "light intensity shower rain",
            "icon": "09n"
        }
    ],*/


    String id;
    String main;
    String description;
    String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
