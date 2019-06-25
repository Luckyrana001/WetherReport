package com.weather.report.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.weather.report.db.AppDatabase;
import com.weather.report.db.dao.RecordsDao;
import com.weather.report.helper.LCEStatus;
import com.weather.report.helper.ServiceRuntimeException;
import com.weather.report.helper.Utils;
import com.weather.report.model.DataUpdateModel;
import com.weather.report.model.WeatherApiDataResponseModel;
import com.weather.report.model.WeatherListAllCitiesModel;
import com.weather.report.services.ILocalServices;
import com.weather.report.services.IRemoteServices;

import java.util.ArrayList;

import io.andref.rx.network.RxNetwork;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.weather.report.helper.Constants.DATA_INSERT_FAILED;
import static com.weather.report.helper.Constants.DATA_LOAD_FAILED_TITLE;
import static com.weather.report.helper.Constants.LOADING_DATA;
import static com.weather.report.helper.Constants.RETRY_AGAIN;
import static com.weather.report.helper.Constants.START_REQUEST;
import static com.weather.report.helper.Constants.TABLE_DATA_UPDATED;
import static com.weather.report.helper.Constants.TABLE_INSERT_ERROR;


public class WeatherReportViewModel extends ViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private IRemoteServices mRemoteServices;
    private ILocalServices mLocalServices;
    private MutableLiveData<LCEStatus> mlLceStatus = new MutableLiveData<>();
    private MutableLiveData<String> mlWarningStatus = new MutableLiveData<>();
    private MutableLiveData<DataUpdateModel> mlUpdateData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<WeatherListAllCitiesModel>> mlWeatherData = new MutableLiveData<>();
    private RecordsDao recordsDao;
    private int selectedSpinerIndex = 0;
    private Context context;

    public WeatherReportViewModel(IRemoteServices rservice, ILocalServices lservice, Context ctx) {

        mRemoteServices = rservice;
        mLocalServices = lservice;
        context = ctx;

        recordsDao = AppDatabase.getInstance(context).userDao();


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        RxNetwork.connectivityChanges(context, connectivityManager)
                .subscribe(connected -> {
                    if (connected)
                        getDataFromApi();
                    else
                        getDataFromDb();

                }, throwable -> {
                    if (throwable instanceof ServiceRuntimeException) {
                        throwable.printStackTrace();
                    }
                });


    }

    public void getDataFromApi() {
        Utils utils = new Utils(context);
        if (utils.isNetworkAvailable()) {
            mRemoteServices
                    .getMobileDataUsage(START_REQUEST)
                    .doOnSubscribe(disposable -> LCEStatus.loading(LOADING_DATA))
                    .doOnTerminate(() -> LCEStatus.success())
                    .subscribe(result -> {

                        WeatherApiDataResponseModel mobileDataConsumptionYearlyModel = new WeatherApiDataResponseModel();
                        mobileDataConsumptionYearlyModel.setCnt(result.getCnt());
                        mobileDataConsumptionYearlyModel.setList(result.getList());

                        mlWeatherData.setValue(result.getList());
                        insertDataIntoDB(new Gson().toJson(result));

                    }, throwable -> {

                        if (throwable instanceof ServiceRuntimeException) {
                            getDataFromDb();
                        } else {
                            mlLceStatus.postValue(LCEStatus.error(DATA_LOAD_FAILED_TITLE, RETRY_AGAIN));
                        }
                    });

        } else {
            getDataFromDb();
        }
    }

    public void insertDataIntoDB(final String result) {
        Disposable dbDataDisposable = mLocalServices.insertDataIntoDatabase(recordsDao, result)
                .subscribeOn(Schedulers.single())
                .subscribe(getWeatherData -> {
                    System.out.print(TABLE_DATA_UPDATED);
                }, throwable -> {
                    mlLceStatus.postValue(LCEStatus.error(DATA_INSERT_FAILED, TABLE_INSERT_ERROR));
                });


        compositeDisposable.add(dbDataDisposable);

    }

    public void getDataFromDb() {
        Disposable dbDataDisposable = mLocalServices.getDataFromDatabase(recordsDao)
                .subscribeOn(Schedulers.single())
                .subscribe(getWeatherData -> {
                    if (getWeatherData == null || getWeatherData.isEmpty()) {
                        mlLceStatus.postValue(LCEStatus.error(DATA_LOAD_FAILED_TITLE, RETRY_AGAIN));
                    } else {
                        LCEStatus.success();
                        mlWeatherData.postValue(getWeatherData);
                    }

                });


        compositeDisposable.add(dbDataDisposable);


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
        // update selected index
        selectedSpinerIndex = index;
        Utils utils = new Utils(context);
        if (utils.isNetworkAvailable()) {
            // get new Data from api
            getDataFromApi();
        } else {
            // update data from db
            updateLatestData();
        }

    }

    public void updateLatestData() {

        if (!mlWeatherData.getValue().isEmpty()) {
            DataUpdateModel dataUpdateModel = new DataUpdateModel();
            dataUpdateModel.setCityName(mlWeatherData.getValue().get(selectedSpinerIndex).getName());
            dataUpdateModel.setTemprature((int) Math.round(mlWeatherData.getValue().get(selectedSpinerIndex).getMain().getTemp()) + " \u2103");
            dataUpdateModel.setUpdatedTime(Utils.getDate((mlWeatherData.getValue().get(selectedSpinerIndex).getDt() * 1000), "EEEE hh:mm a"));
            dataUpdateModel.setWeather(mlWeatherData.getValue().get(selectedSpinerIndex).getWeatherDataListArrayList().get(0).getDescription());
            dataUpdateModel.setWindSpeed(mlWeatherData.getValue().get(selectedSpinerIndex).getWind().getSpeed() + " km/h");
            mlUpdateData.setValue(dataUpdateModel);
        } else {
            mlWarningStatus.postValue(RETRY_AGAIN);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
