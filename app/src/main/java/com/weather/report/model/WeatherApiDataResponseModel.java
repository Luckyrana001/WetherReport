package com.weather.report.model;

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


}
