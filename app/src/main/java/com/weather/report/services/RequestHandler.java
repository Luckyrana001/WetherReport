package com.weather.report.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weather.report.Constants;
import com.weather.report.WeatherRestService;
import com.weather.report.helper.BaseFlyContext;
import com.weather.report.helper.IResponseReceivedNotifyInterface;
import com.weather.report.helper.RequestType;
import com.weather.report.helper.ResponseArgs;
import com.weather.report.helper.ResponseStatus;
import com.weather.report.helper.Utils;
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

    private static final int TIMEOUT_VALUE = 60000;
    public static RequestHandler requestHandler;
    public static Utils utils;
    private Retrofit retrofit;
    private WeatherRestService service;

    public RequestHandler() {


        try {
            utils = new Utils(BaseFlyContext.getInstant().getApplicationContext());

           /* OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor( utils.REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addNetworkInterceptor(utils.REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .cache(cache)
                    .build();*/


            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();


          /*retrofit =  new Retrofit.Builder()
                    .baseUrl( Constants.BASE_URL )
                    .client( provideOkHttpClient() )
                    .addConverterFactory(JacksonConverterFactory.create() )
                    .build();*/
            service = retrofit.create(WeatherRestService.class);


        } catch (Exception e) {
            Log.e("RequestHandler except", e + "");
        }
    }
   /* private  OkHttpClient provideOkHttpClient ()
    {
        return new OkHttpClient.Builder()
                .addInterceptor( utils.provideHttpLoggingInterceptor() )
                .addInterceptor( utils.provideOfflineCacheInterceptor() )
                .addNetworkInterceptor(utils. provideCacheInterceptor() )
                .cache( utils.provideCache() )
                .build();
    }*/


    public static RequestHandler getRequestHandler() {

        if (requestHandler == null) {
            requestHandler = new RequestHandler();
        }
        return requestHandler;
    }


    public void getMobileUsageDataRequest(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, String url) {


        Call<WeatherApiDataResponseModel> call = service.getMobileDataUsage(url);
        call.enqueue(new Callback<WeatherApiDataResponseModel>() {
            @Override
            public void onResponse(Call<WeatherApiDataResponseModel> call, Response<WeatherApiDataResponseModel> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, response.toString());
                    Log.i("formattedData", new Gson().toJson(response) + "");

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

                        Log.e("IOException error:", new Gson().toJson(error.toString()));
                    } catch (Exception e) {
                        Log.e(TAG, "IOException parsing error:", e);
                    }
                }


            }

            @Override
            public void onFailure(Call<WeatherApiDataResponseModel> call, Throwable t) {
                // Log error here since request failed
                iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(null, ResponseStatus.badRequest, RequestType.errorResponse));
                Log.e(TAG, t.toString());
            }
        });
    }







/*
    public void getQuoteOfTheDay() {
        Call<QuoteOfTheDayResponse> call = service.getQuoteOfTheDay();

        call.enqueue(new Callback<QuoteOfTheDayResponse>() {

            @Override
            public void onResponse(Call<QuoteOfTheDayResponse> call, Response<QuoteOfTheDayResponse> response) {
                if (response.isSuccessful()) {

                    //textViewQuoteOfTheDay.setText(response.body().getContents().getQuotes().get(0).getQuote());
                    Log.i("Response:", new Gson().toJson(response.toString()));

                } else {
                    try {
                        Converter<ResponseBody, QuoteOfTheDayErrorResponse> errorConverter = retrofit.responseBodyConverter(QuoteOfTheDayErrorResponse.class, new Annotation[0]);
                        QuoteOfTheDayErrorResponse error = errorConverter.convert(response.errorBody());
                        //showRetry(error.getError().getMessage());

                        Log.e("IOException error:", new Gson().toJson(error.toString()).toString());

                    } catch (IOException e) {
                        Log.e(TAG, "IOException parsing error:", e);
                    }

                }
            }

            @Override
            public void onFailure(Call<QuoteOfTheDayResponse> call, Throwable t) {
                //Transport level errors such as no internet etc.
            }
        });


    }*/
}
