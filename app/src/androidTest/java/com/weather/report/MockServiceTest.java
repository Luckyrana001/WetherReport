package com.weather.report;

import com.google.gson.Gson;
import com.weather.report.model.WeatherApiDataResponseModel;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class MockServiceTest implements WeatherRestService {

    private final BehaviorDelegate<WeatherRestService> delegate;

    public MockServiceTest(BehaviorDelegate<WeatherRestService> service) {
        this.delegate = service;
    }




    @Override
    public Call<WeatherApiDataResponseModel> getMobileDataUsage(String url) {

        String fileName = "weather_report_200_response.json";
        String data = null;
        try {
            data = RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
      WeatherApiDataResponseModel mobileDataUsageResponse = new Gson().fromJson(data,WeatherApiDataResponseModel.class);

        return delegate.returningResponse(mobileDataUsageResponse).getMobileDataUsage();

    }

    @Override
    public Call<WeatherApiDataResponseModel> getMobileDataUsage() {
        return null;
    }

    @Override
    public Call<WeatherApiDataResponseModel> getMobileDataUsage(String resourceId, String limit) {
        return null;
    }
}
