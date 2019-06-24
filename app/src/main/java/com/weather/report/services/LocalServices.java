package com.weather.report.services;


import com.google.gson.Gson;
import com.weather.report.db.dao.RecordsDao;
import com.weather.report.db.entity.RecordsDataDao;
import com.weather.report.model.WeatherApiDataResponseModel;
import com.weather.report.model.WeatherListAllCitiesModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public class LocalServices implements ILocalServices {

    public LocalServices() {
    }

    public Observable<ArrayList<WeatherListAllCitiesModel>> getDataFromDatabase(RecordsDao mRecordsDao) {
        return Observable.defer(() -> Observable.just(queryDatafromDatabase(mRecordsDao)));
    }

    @Override
    public Observable<Long[]> insertDataIntoDatabase(RecordsDao recordsDao, String result) {
        return Observable.defer(() -> Observable.just(insertDataIntoDb(recordsDao, result)));
    }


    public Long[] insertDataIntoDb(RecordsDao recordsDao, String result) {
        recordsDao.deleteAll();

        RecordsDataDao user = new RecordsDataDao();
        user.setRecords(result);
        Long[] value = recordsDao.insertAll(user);
        return value;
    }

    public ArrayList<WeatherListAllCitiesModel> queryDatafromDatabase(RecordsDao mRecordsDao) {
        List<RecordsDataDao> data = mRecordsDao.getAll();
        if (data.isEmpty()) {
            ArrayList<WeatherListAllCitiesModel> weatherDatalist = new ArrayList<>();
            return weatherDatalist;
        } else {
            RecordsDataDao value = data.get(0);
            String records = value.getRecords();
            WeatherApiDataResponseModel recordData = new Gson().fromJson(records, WeatherApiDataResponseModel.class);
            ArrayList<WeatherListAllCitiesModel> weatherDatalist = recordData.getList();
            return weatherDatalist;
        }

    }


}
