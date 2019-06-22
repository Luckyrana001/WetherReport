package com.weather.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class WeatherListAllCitiesModel {

    String name;
    @JsonProperty("help")
    Wind wind = new Wind();
    @JsonProperty("weather")
    ArrayList<WeatherDataList> weatherDataListArrayList = new ArrayList<>();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public ArrayList<WeatherDataList> getWeatherDataListArrayList() {
        return weatherDataListArrayList;
    }

    public void setWeatherDataListArrayList(ArrayList<WeatherDataList> weatherDataListArrayList) {
        this.weatherDataListArrayList = weatherDataListArrayList;
    }

    public MainDataModel getMainDataModel() {
        return mainDataModel;
    }

    public void setMainDataModel(MainDataModel mainDataModel) {
        this.mainDataModel = mainDataModel;
    }

    @JsonProperty("main")
    MainDataModel mainDataModel = new MainDataModel();

}
