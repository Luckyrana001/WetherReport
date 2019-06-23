package com.weather.report.services;


import com.weather.report.helper.RequestType;
import com.weather.report.helper.ServiceRuntimeException;
import com.weather.report.model.WeatherApiDataResponseModel;

import io.reactivex.Observable;


public class RemoteServices implements IRemoteServices {

    public RemoteServices() {
    }

    @Override
    public Observable<WeatherApiDataResponseModel> getMobileDataUsage(String url) {

        return Observable.create(emitter -> {
            RequestHandler.getRequestHandler()
                    .getMobileUsageDataRequest(responseArgs -> {
                        if (responseArgs.requestType == RequestType.getWeatherApiResponse) {
                            WeatherApiDataResponseModel response = (WeatherApiDataResponseModel) responseArgs.args;


                            emitter.onNext(response);
                            emitter.onComplete();

                        } else if (responseArgs.requestType == RequestType.errorResponse) {
                            ServiceRuntimeException ex = new ServiceRuntimeException("0", "Please try again later");
                            emitter.onError(ex);
                        }
                    }, url);
        });
    }
}

