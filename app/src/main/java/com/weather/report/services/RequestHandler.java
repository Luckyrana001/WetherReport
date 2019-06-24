package com.weather.report.services;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.weather.report.helper.Constants;
import com.weather.report.helper.IResponseReceivedNotifyInterface;
import com.weather.report.helper.RequestType;
import com.weather.report.helper.ResponseArgs;
import com.weather.report.helper.ResponseStatus;
import com.weather.report.model.WeatherApiDataResponseModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RequestHandler {
    private static final String TAG = RequestHandler.class.getName();

    public static RequestHandler requestHandler;
    private Retrofit retrofit;
    private WeatherRestService service;

    public RequestHandler() {


        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();

            service = retrofit.create(WeatherRestService.class);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static RequestHandler getRequestHandler() {

        if (requestHandler == null) {
            requestHandler = new RequestHandler();
        }
        return requestHandler;
    }


    public void getMobileUsageDataRequest(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, String url) {


        Call<WeatherApiDataResponseModel> call = service.getWeatherApiData(url);
        call.enqueue(new Callback<WeatherApiDataResponseModel>() {
            @Override
            public void onResponse(Call<WeatherApiDataResponseModel> call, Response<WeatherApiDataResponseModel> response) {
                if (response.isSuccessful()) {
                    Type type = new TypeToken<WeatherApiDataResponseModel>() {
                    }.getType();
                    iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(response.body(), ResponseStatus.success, RequestType.getWeatherApiResponse));


                } else {
                    try {
                        Converter<ResponseBody, WeatherApiDataResponseModel> errorConverter = retrofit.responseBodyConverter(WeatherApiDataResponseModel.class, new Annotation[0]);
                        WeatherApiDataResponseModel error = errorConverter.convert(response.errorBody());
                        //showRetry(error.getError().getMessage());
                        Type type = new TypeToken<WeatherApiDataResponseModel>() {
                        }.getType();
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(error, ResponseStatus.success, RequestType.errorResponse));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<WeatherApiDataResponseModel> call, Throwable t) {
                iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(null, ResponseStatus.badRequest, RequestType.errorResponse));
                Log.e(TAG, t.toString());
            }
        });
    }


}
