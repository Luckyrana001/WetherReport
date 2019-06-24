package com.weather.report.services;


import com.weather.report.db.dao.RecordsDao;
import com.weather.report.model.WeatherListAllCitiesModel;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface ILocalServices {

    Observable<ArrayList<WeatherListAllCitiesModel>> getDataFromDatabase(RecordsDao recordsDao);


    Observable<Long[]> insertDataIntoDatabase(RecordsDao recordsDao, String result);


}
