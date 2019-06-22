package com.weather.report;


import android.support.test.filters.SmallTest;


import com.weather.report.model.WeatherApiDataResponseModel;
import com.weather.report.services.WeatherRestService;

import junit.framework.Assert;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static com.weather.report.helper.Constants.START_REQUEST;

@SuppressWarnings("ALL")
public class MockAdapterTest {
    private MockRetrofit mockRetrofit;
    private Retrofit retrofit;


   public MockAdapterTest(){
       try {
           setUp();
       }catch (Exception e){
           e.printStackTrace();
       }
     }

    public void setUp() throws Exception {
        retrofit = new Retrofit.Builder().baseUrl("http://test.com")
                .client(new OkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        NetworkBehavior behavior = NetworkBehavior.create();

        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }


    @SmallTest
    public void testRandomDataRetrieval() throws Exception {
        BehaviorDelegate<WeatherRestService> delegate = mockRetrofit.create(WeatherRestService.class);
        WeatherRestService mockDataService = new MockServiceTest(delegate);


        //Actual Test
        Call<WeatherApiDataResponseModel> mobileDataUsage = mockDataService.getWeatherApiData(START_REQUEST);
        Response<WeatherApiDataResponseModel> weatherMockResponse = mobileDataUsage.execute();



        //Asserting response
        Assert.assertTrue(weatherMockResponse.isSuccessful());

    }


}