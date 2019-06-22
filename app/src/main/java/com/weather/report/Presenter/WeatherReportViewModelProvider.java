package com.weather.report.Presenter;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.weather.report.services.IRemoteServices;


public class WeatherReportViewModelProvider implements ViewModelProvider.Factory {


    private IRemoteServices mRs;

    public WeatherReportViewModelProvider(IRemoteServices mRs) {
        this.mRs = mRs;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherReportViewModel.class)) {
            return (T) new WeatherReportViewModel(mRs);
        }
        throw new IllegalArgumentException("ViewModel expected");
    }
}
