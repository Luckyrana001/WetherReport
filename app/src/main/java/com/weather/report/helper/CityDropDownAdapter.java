package com.weather.report.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weather.report.R;
import com.weather.report.model.WeatherListAllCitiesModel;

import java.util.ArrayList;


public class CityDropDownAdapter extends BaseAdapter {
    Context context;
    ArrayList<WeatherListAllCitiesModel> cityList;

    LayoutInflater inflter;

    public CityDropDownAdapter(Context context, ArrayList<WeatherListAllCitiesModel> cityList) {
        this.context = context;
        this.cityList = cityList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.city_drop_down_adapter, null);

        WeatherListAllCitiesModel model = cityList.get(i);
        TextView cityNameTv = view.findViewById(R.id.cityNameTv);
        cityNameTv.setText(model.getName());

        return view;
    }


}
