package com.weather.report.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.weather.report.Presenter.WeatherReportViewModel;
import com.weather.report.Presenter.WeatherReportViewModelProvider;
import com.weather.report.R;
import com.weather.report.helper.CityDropDownAdapter;
import com.weather.report.helper.LCEStatus;
import com.weather.report.model.DataUpdateModel;
import com.weather.report.model.WeatherListAllCitiesModel;
import com.weather.report.services.IRemoteServices;
import com.weather.report.services.LocalServices;
import com.weather.report.services.RemoteServices;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.cityValueTv)
    TextView cityValueTv;

    @Bind(R.id.updateTimeValueTv)
    TextView updateTimeValueTv;

    @Bind(R.id.wheatherValueTv)
    TextView wheatherValueTv;

    @Bind(R.id.tempratureValueTv)
    TextView tempratureValueTv;

    @Bind(R.id.windValueTv)
    TextView windValueTv;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    @Bind(R.id.citySpinner)
    Spinner citySpinner;
    private WeatherReportViewModel viewModel;
    private CityDropDownAdapter customAdapter;
    private ArrayList<WeatherListAllCitiesModel> weatherRecordList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Initializing  Butterknife library
        ButterKnife.bind(this);


        // Initializing remote services and view model provide instance
        IRemoteServices remoteServices = new RemoteServices();
        WeatherReportViewModelProvider postProvider = new WeatherReportViewModelProvider(remoteServices, new LocalServices(), getApplicationContext());

        viewModel = ViewModelProviders.of(this, postProvider).get(WeatherReportViewModel.class);

        weatherRecordList = new ArrayList<>();
        customAdapter = new CityDropDownAdapter(this, weatherRecordList);
        citySpinner.setAdapter(customAdapter);

        citySpinner.setOnItemSelectedListener(this);

        setUpObservers();


    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        Spinner spinner = (Spinner) arg0;
        if (spinner.getId() == R.id.citySpinner) {
            viewModel.updateSelectedSpinnerIndex(citySpinner.getSelectedItemPosition());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void setUpObservers() {


        viewModel.getMobileDataUsageData()
                .observe(this, this::updateDataset);

        viewModel.getLceStatus()
                .observe(this, this::showLceStatus);

        viewModel.getWarning()
                .observe(this, this::showToast);

        viewModel.setCityWeatherData()
                .observe(this, this::updateData);


    }

    private void updateData(DataUpdateModel dataUpdateModel) {

        cityValueTv.setText(dataUpdateModel.getCityName());
        updateTimeValueTv.setText(dataUpdateModel.getUpdatedTime());
        wheatherValueTv.setText(dataUpdateModel.getWeather());
        tempratureValueTv.setText(dataUpdateModel.getTemprature());
        windValueTv.setText(dataUpdateModel.getWindSpeed());
    }

    private void updateDataset(ArrayList<WeatherListAllCitiesModel> weatherDataList) {
        weatherRecordList.clear();
        weatherRecordList.addAll(weatherDataList);
        customAdapter.notifyDataSetChanged();

        viewModel.updateLatestData();

    }


    public void showToast(String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    private void showLceStatus(LCEStatus status) {
        if (status.getStatus() == LCEStatus.Status.SUCCESS) {
            showToast(status.getMsg());
        } else if (status.getStatus() == LCEStatus.Status.ERROR) {
            showToast(status.getMsg());
        } else if (status.getStatus() == LCEStatus.Status.LOADING) {
            showToast(status.getMsg());
        }
    }


}
