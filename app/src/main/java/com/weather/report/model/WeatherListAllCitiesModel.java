package com.weather.report.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class WeatherListAllCitiesModel {

    String name;


    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    long dt;
    String id;
    @JsonProperty("clouds")
    Clouds clouds;
    @JsonProperty("wind")
    Wind wind = new Wind();
    @JsonProperty("weather")
    ArrayList<WeatherDataList> weatherDataListArrayList = new ArrayList<>();
    @JsonProperty("main")
    Main main;
    @JsonProperty("sys")
    Sys sys;
    @JsonProperty("coord")
    Coordinate coord;
    @JsonIgnoreProperties("visibility")
    String visibility;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

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


}
