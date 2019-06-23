package com.weather.report;

import com.google.gson.Gson;
import com.weather.report.model.WeatherApiDataResponseModel;
import com.weather.report.services.WeatherRestService;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import static androidx.test.InstrumentationRegistry.getInstrumentation;

public class MockServiceTest implements WeatherRestService {

    private final BehaviorDelegate<WeatherRestService> delegate;

    public MockServiceTest(BehaviorDelegate<WeatherRestService> service) {
        this.delegate = service;
    }


    @Override
    public Call<WeatherApiDataResponseModel> getWeatherApiData(String url) {

        String fileName = "weather_report_200_response.json";
        String data = null;
        try {
            data = RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WeatherApiDataResponseModel weatherApiDataResponseModel = new Gson().fromJson(data, WeatherApiDataResponseModel.class);

        return delegate.returningResponse(weatherApiDataResponseModel).getWeatherApiData();

    }

    @Override
    public Call<WeatherApiDataResponseModel> getWeatherApiData() {
        return null;
    }


}
