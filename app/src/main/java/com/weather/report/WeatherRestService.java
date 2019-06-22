package com.weather.report;


import com.weather.report.model.WeatherApiDataResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WeatherRestService {

    @GET
    Call<WeatherApiDataResponseModel> getMobileDataUsage(@Url String url);

    @GET("api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=5")
    Call<WeatherApiDataResponseModel> getMobileDataUsage();


    @GET("datastore_search")
    Call<WeatherApiDataResponseModel> getMobileDataUsage(
            @Query("resource_id") String resourceId,
            @Query("limit") String limit

    );

}
