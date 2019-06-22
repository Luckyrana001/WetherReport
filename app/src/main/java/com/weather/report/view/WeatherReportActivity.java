package com.weather.report.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.report.Presenter.WeatherReportViewModel;
import com.weather.report.Presenter.WeatherReportViewModelProvider;
import com.weather.report.R;
import com.weather.report.helper.BaseFlyContext;
import com.weather.report.helper.CityDropDownAdapter;
import com.weather.report.helper.LCEStatus;
import com.weather.report.model.DataUpdateModel;
import com.weather.report.model.WeatherListAllCitiesModel;
import com.weather.report.services.IRemoteServices;
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


    @Bind(R.id.citySpinner)
    Spinner citySpinner;
    private WeatherReportViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Initializing  Butterknife library
        ButterKnife.bind(this);
        BaseFlyContext.getInstant().setActivity(this);


        // Initializing remote services and view model provide instance
        IRemoteServices remoteServices = new RemoteServices();
        WeatherReportViewModelProvider postProvider = new WeatherReportViewModelProvider(remoteServices);

        setupViewModel(postProvider);

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

    private WeatherReportViewModel setupViewModel(WeatherReportViewModelProvider postProvider) {
        return viewModel = ViewModelProviders.of(this, postProvider).get(WeatherReportViewModel.class);

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

        CityDropDownAdapter customAdapter = new CityDropDownAdapter(this, weatherDataList);
        citySpinner.setAdapter(customAdapter);
    }


    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void showLceStatus(LCEStatus status) {
        if (status.getStatus() == LCEStatus.Status.SUCCESS) {
            showToast("New Data Available.");
        } else if (status.getStatus() == LCEStatus.Status.ERROR) {
             showToast("Error! Please try Again Later.");
        } else if (status.getStatus() == LCEStatus.Status.LOADING) {
            showToast("Loading Data...");
        }
    }


}
