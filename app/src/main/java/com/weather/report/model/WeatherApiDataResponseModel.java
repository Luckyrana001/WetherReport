package com.weather.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class WeatherApiDataResponseModel {

    int cnt;
    ArrayList<WeatherListAllCitiesModel> list = new ArrayList<>();



    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<WeatherListAllCitiesModel> getList() {
        return list;
    }

    public void setList(ArrayList<WeatherListAllCitiesModel> list) {
        this.list = list;
    }




    /*

    {
    "cnt": 3,
    "list": [
        {
            "coord": {
                "lon": 151.21,
                "lat": -33.87
            },
            "sys": {
                "country": "AU",
                "timezone": 36000,
                "sunrise": 1561150805,
                "sunset": 1561186429
            },
            "weather": [
                {
                    "id": 520,
                    "main": "Rain",
                    "description": "light intensity shower rain",
                    "icon": "09n"
                }
            ],
            "main": {
                "temp": 10.37,
                "pressure": 1025,
                "humidity": 71,
                "temp_min": 8.89,
                "temp_max": 12.22
            },
            "visibility": 10000,
            "wind": {
                "speed": 5.1,
                "deg": 240
            },
            "clouds": {
                "all": 40
            },
            "dt": 1561198041,
            "id": 2147714,
            "name": "Sydney"
        },
        {
            "coord": {
                "lon": 144.96,
                "lat": -37.81
            },
            "sys": {
                "country": "AU",
                "timezone": 36000,
                "sunrise": 1561152945,
                "sunset": 1561187290
            },
            "weather": [
                {
                    "id": 801,
                    "main": "Clouds",
                    "description": "few clouds",
                    "icon": "02n"
                }
            ],
            "main": {
                "temp": 6.93,
                "pressure": 1030,
                "humidity": 93,
                "temp_min": 4.44,
                "temp_max": 8.89
            },
            "visibility": 10000,
            "wind": {
                "speed": 2.6,
                "deg": 310
            },
            "clouds": {
                "all": 20
            },
            "dt": 1561198155,
            "id": 2158177,
            "name": "Melbourne"
        },
        {
            "coord": {
                "lon": 150.88,
                "lat": -34.43
            },
            "sys": {
                "country": "AU",
                "timezone": 36000,
                "sunrise": 1561150971,
                "sunset": 1561186422
            },
            "weather": [
                {
                    "id": 520,
                    "main": "Rain",
                    "description": "light intensity shower rain",
                    "icon": "09n"
                }
            ],
            "main": {
                "temp": 10.22,
                "pressure": 1025,
                "humidity": 71,
                "temp_min": 7.78,
                "temp_max": 12.22
            },
            "visibility": 10000,
            "wind": {
                "speed": 5.1,
                "deg": 240
            },
            "clouds": {
                "all": 40
            },
            "dt": 1561198155,
            "id": 2171507,
            "name": "Wollongong"
        }
    ]
}

*/
}
