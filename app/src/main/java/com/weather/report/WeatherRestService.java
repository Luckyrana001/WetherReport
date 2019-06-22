package com.weather.report;


import com.weather.report.model.WeatherApiDataResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherRestService {

    @GET
    Call<WeatherApiDataResponseModel> getMobileDataUsage(@Url String url);


    Call<WeatherApiDataResponseModel> getMobileDataUsage();

    Call<WeatherApiDataResponseModel> getMobileDataUsage(String resourceId, String limit);
}
