package com.weather.report.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.weather.report.services.ILocalServices;
import com.weather.report.services.IRemoteServices;
import com.weather.report.services.LocalServices;

import static com.weather.report.helper.Constants.VIEWMODEL_EXPECTED;


public class WeatherReportViewModelProvider implements ViewModelProvider.Factory {


    private IRemoteServices mRs;
    private ILocalServices mls;
    private Context context;

    public WeatherReportViewModelProvider(IRemoteServices mRs, LocalServices mls, Context ctx) {
        this.mRs = mRs;
        this.mls = mls;
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherReportViewModel.class)) {
            return (T) new WeatherReportViewModel(mRs, mls, context);
        }
        throw new IllegalArgumentException(VIEWMODEL_EXPECTED);
    }
}
