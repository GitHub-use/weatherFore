package com.example.weatherfore.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WeatherInfoAdapter extends ArrayAdapter<WeatherInfo> {
    int resourceId;
    public WeatherInfoAdapter(Context context, int reId, List<WeatherInfo> objects){
        super(context,reId,objects);
        resourceId = reId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        return view;
    }
}
