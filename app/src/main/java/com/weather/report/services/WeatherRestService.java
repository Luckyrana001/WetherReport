package com.weather.report.services;


import com.weather.report.model.WeatherApiDataResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherRestService {

    @GET
    Call<WeatherApiDataResponseModel> getWeatherApiData(@Url String url);


    Call<WeatherApiDataResponseModel> getWeatherApiData();

}
