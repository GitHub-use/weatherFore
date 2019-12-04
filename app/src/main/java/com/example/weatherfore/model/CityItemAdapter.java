package com.example.weatherfore.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherfore.*;

import java.util.List;

public class CityItemAdapter extends ArrayAdapter{
    int reid;
    List<CityList> cityLists=null;
    List<CityNextList> cityNextLists=null;
    List<LocalId> localIds=null;
    int tag;
    public static final int FIRST_STATUS = 0;
    public static final int SECOND_STATUS =1;
    public static final int THIRD_STATUS=2;
    public CityItemAdapter(Context context,int resourceid,List<?> object,int t){
        super(context,resourceid,object);
        reid = resourceid;
        tag=t;
        switch (t){
            case FIRST_STATUS:cityLists = (List<CityList>) object;System.out.println("shoudao!");break;
            case SECOND_STATUS:cityNextLists = (List<CityNextList>) object;break;
            case THIRD_STATUS:localIds = (List<LocalId>) object;break;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        System.out.println("hahahaha");
        View view = LayoutInflater.from(getContext()).inflate(reid,parent,false);
        TextView textView = view.findViewById(R.id.city_item_textview);
        switch (tag){
            case FIRST_STATUS:textView.setText(cityLists.get(position).getName());break;
            case SECOND_STATUS:textView.setText(cityNextLists.get(position).getName());break;
            case THIRD_STATUS:textView.setText(localIds.get(position).getName());break;
        }
        return view;
    }
}
