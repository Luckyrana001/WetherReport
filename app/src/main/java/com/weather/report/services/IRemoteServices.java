package com.weather.report.services;


import com.weather.report.model.WeatherApiDataResponseModel;

import io.reactivex.Observable;


public interface IRemoteServices {


    Observable<WeatherApiDataResponseModel> getMobileDataUsage(String url);

}


