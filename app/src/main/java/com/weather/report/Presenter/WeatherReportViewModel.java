package com.weather.report.Presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.weather.report.db.AppDatabase;
import com.weather.report.db.dao.RecordsDao;
import com.weather.report.db.entity.RecordsDataDao;
import com.weather.report.helper.BaseFlyContext;
import com.weather.report.helper.LCEStatus;
import com.weather.report.helper.ServiceRuntimeException;
import com.weather.report.helper.Utils;
import com.weather.report.model.DataUpdateModel;
import com.weather.report.model.WeatherApiDataResponseModel;
import com.weather.report.model.WeatherListAllCitiesModel;
import com.weather.report.services.IRemoteServices;

import java.util.ArrayList;
import java.util.List;

import static com.weather.report.helper.Constants.START_REQUEST;


public class WeatherReportViewModel extends ViewModel {
    private IRemoteServices mRemoteServices;


    private MutableLiveData<LCEStatus> mlLceStatus = new MutableLiveData<>();
    private MutableLiveData<String> mlWarningStatus = new MutableLiveData<>();
    private MutableLiveData<DataUpdateModel> mlUpdateData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<WeatherListAllCitiesModel>> mlWeatherData = new MutableLiveData<ArrayList<WeatherListAllCitiesModel>>();
    private RecordsDao recordsDao;

    public WeatherReportViewModel(IRemoteServices rservice) {

        mRemoteServices = rservice;
        recordsDao = AppDatabase.getInstance(BaseFlyContext.getInstant().getApplicationContext()).userDao();

        getDataFromAPiOrCacheOrFromDb();


    }

    public void insertDataIntoDB(final String result) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                recordsDao.deleteAll();

                RecordsDataDao user = new RecordsDataDao();
                user.setRecords(result);
                recordsDao.insertAll(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                System.out.print("Table data deleted");
            }
        }.execute();
    }

    public void getDataFromDb() {
        new AsyncTask<Void, Void, ArrayList<WeatherListAllCitiesModel>>() {
            @Override
            protected ArrayList<WeatherListAllCitiesModel> doInBackground(Void... params) {
                try {
                    List<RecordsDataDao> data = recordsDao.getAll();
                    RecordsDataDao value = data.get(0);
                    String records = value.getRecords();
                    WeatherApiDataResponseModel recordData = new Gson().fromJson(records, WeatherApiDataResponseModel.class);
                    ArrayList<WeatherListAllCitiesModel> weatherDatalist = recordData.getList();

                    return weatherDatalist;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<WeatherListAllCitiesModel> getWeatherData) {


                if (getWeatherData == null || getWeatherData.isEmpty()) {
                    mlLceStatus.postValue(LCEStatus.error("Data Load Failed", "Please check your internet connection and retry again."));
                    LCEStatus.success();
                } else {
                    LCEStatus.success();
                    mlWeatherData.setValue(getWeatherData);
                }
            }
        }.execute();
    }

    public void getDataFromAPiOrCacheOrFromDb() {
        Utils utils = new Utils(BaseFlyContext.getInstant().getApplicationContext());
        if (utils.isNetworkAvailable()) {
            mRemoteServices
                    .getMobileDataUsage(START_REQUEST)
                    .doOnSubscribe(disposable -> LCEStatus.loading("Loading Data Data ..."))
                    .doOnTerminate(() -> LCEStatus.success())
                    .subscribe(result -> {

                        WeatherApiDataResponseModel mobileDataConsumptionYearlyModel = new WeatherApiDataResponseModel();
                        mobileDataConsumptionYearlyModel.setCnt(result.getCnt());
                        mobileDataConsumptionYearlyModel.setList(result.getList());

                        mlWarningStatus.postValue("Weather Data Updated Sucessfuly.");
                        mlWeatherData.setValue(result.getList());

                        insertDataIntoDB(new Gson().toJson(result));

                    }, throwable -> {

                        if (throwable instanceof ServiceRuntimeException) {
                            getDataFromDb();
                        } else {
                            mlLceStatus.postValue(LCEStatus.error("Data Error", "Data Load Failed."));
                        }
                    });

        } else {
            getDataFromDb();
        }
    }


    public LiveData<LCEStatus> getLceStatus() {
        return mlLceStatus;
    }


    public LiveData<String> getWarning() {
        return mlWarningStatus;
    }

    public LiveData<DataUpdateModel> setCityWeatherData() {
        return mlUpdateData;
    }


    public LiveData<ArrayList<WeatherListAllCitiesModel>> getMobileDataUsageData() {
        return mlWeatherData;
    }

    public void updateSelectedSpinnerIndex(int index) {

        if (!mlWeatherData.getValue().isEmpty()) {
            DataUpdateModel dataUpdateModel = new DataUpdateModel();
            dataUpdateModel.setCityName(mlWeatherData.getValue().get(index).getName());
            dataUpdateModel.setTemprature((int) Math.round(mlWeatherData.getValue().get(index).getMain().getTemp()) + " \u2103");
            dataUpdateModel.setUpdatedTime(Utils.getDate((mlWeatherData.getValue().get(index).getDt() *  1000), "EEEE hh:mm a"));
            dataUpdateModel.setWeather(mlWeatherData.getValue().get(index).getWeatherDataListArrayList().get(0).getDescription());
            dataUpdateModel.setWindSpeed(mlWeatherData.getValue().get(index).getWind().getSpeed() + " km/h");
            mlUpdateData.setValue(dataUpdateModel);
        }
    }
}
