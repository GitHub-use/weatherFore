package com.example.weatherfore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherfore.model.CityItemAdapter;
import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.CityNextList;
import com.example.weatherfore.model.LocalId;

import java.util.List;

public class SelectCityFragment extends Fragment {
    View mView = null;

    public static final int FIRST_STATUS = 0;
    public static final int SECOND_STATUS =1;
    public static final int THIRD_STATUS=2;

    List<CityList> cityLists=null;
    List<CityNextList> cityNextLists=null;
    List<LocalId> localIds=null;

    int tag;
    public SelectCityFragment(int t, List<?> objects){
        switch (t){
            case FIRST_STATUS:cityLists = (List<CityList>) objects;break;
            case SECOND_STATUS:cityNextLists = (List<CityNextList>) objects;break;
            case THIRD_STATUS:localIds = (List<LocalId>) objects;break;
        }
        tag=t;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = getActivity().findViewById(R.id.listview_selectcity);
        ArrayAdapter arrayAdapter =null;
        if(tag==FIRST_STATUS){
            System.out.println("创建适配器");
            arrayAdapter = new CityItemAdapter(getContext(),R.layout.city_item,cityLists,CityItemAdapter.FIRST_STATUS);
            System.out.println(cityLists.size());
        }
        if(tag==SECOND_STATUS){

        }
        if(tag==THIRD_STATUS){

        }
        listView.setAdapter(arrayAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selectcityfragment,container,false);
        mView = view;
        return view;
    }
}
