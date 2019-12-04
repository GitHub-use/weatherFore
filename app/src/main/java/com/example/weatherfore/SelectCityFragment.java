package com.example.weatherfore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherfore.model.CityItemAdapter;
import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.CityNextList;
import com.example.weatherfore.model.LocalId;
import com.example.weatherfore.util.GetWeatherInfo;

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
    int fomfrom;//来自哪里
    int netfrom;


    int FINISH_NET_LOADING = 17;

    public Handler han = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==FINISH_NET_LOADING){
                init();
            }
        }
    };

    public SelectCityFragment(int t, List<?> objects){
        switch (t){
            case FIRST_STATUS:
                new Thread(){
                    @Override
                    public void run() {
                        cityLists = GetWeatherInfo.getCityList();
                        if(cityLists!=null){
                            Message msg = new Message();
                            msg.what=FINISH_NET_LOADING;
                            han.sendMessage(msg);
                        }
                    }
                }.start();
                ;
//                cityLists = (List<CityList>) objects;
                break;
            case SECOND_STATUS:cityNextLists = (List<CityNextList>) objects;break;
            case THIRD_STATUS:localIds = (List<LocalId>) objects;break;
        }
        tag=t;
    }
    public SelectCityFragment(int t){
        tag=t;
        new Thread(){
            @Override
            public void run() {
                cityLists = GetWeatherInfo.getCityList();
                if(cityLists!=null){
                    Message msg = new Message();
                    msg.what=FINISH_NET_LOADING;
                    han.sendMessage(msg);
                }
            }
        }.start();
    }

    public SelectCityFragment(int t,final int j){
        tag=t;
        fomfrom=j;
        new Thread(){
            @Override
            public void run() {
                cityNextLists = GetWeatherInfo.getCityNext(j);
                if(cityNextLists!=null){
                    Message msg = new Message();
                    msg.what=FINISH_NET_LOADING;
                    han.sendMessage(msg);
                }
            }
        }.start();
    }

    public SelectCityFragment(int t,final int j,final int i){
        tag=t;
        fomfrom=j;
        netfrom=i;
        new Thread(){
            @Override
            public void run() {
                localIds=GetWeatherInfo.getLocalId(j,i);
                System.out.println(localIds.size());
                if(localIds!=null){
                    Message msg = new Message();
                    msg.what=FINISH_NET_LOADING;
                    han.sendMessage(msg);
                }
            }
        }.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selectcityfragment,container,false);
        mView = view;
        return view;
    }

    private void init(){
        ListView listView = getActivity().findViewById(R.id.listview_selectcity);
        ArrayAdapter arrayAdapter =null;
        if(tag==FIRST_STATUS){
            System.out.println("创建适配器");
            arrayAdapter = new CityItemAdapter(getContext(),R.layout.city_item,cityLists,CityItemAdapter.FIRST_STATUS);
            System.out.println(cityLists.size());
        }
        if(tag==SECOND_STATUS){
            arrayAdapter = new CityItemAdapter(getContext(),R.layout.city_item,cityNextLists,CityItemAdapter.SECOND_STATUS);
        }
        if(tag==THIRD_STATUS){
            System.out.println(localIds.size());
            arrayAdapter = new CityItemAdapter(getContext(),R.layout.city_item,localIds,CityItemAdapter.THIRD_STATUS);
        }
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (tag){
                    case FIRST_STATUS:
                        System.out.println("-----------------");
                        CityList temp = (CityList) parent.getItemAtPosition(position);
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_layout_fragment,new SelectCityFragment(SelectCityFragment.SECOND_STATUS,temp.getId())).commit();
                        break;
                    case SECOND_STATUS:
                        System.out.println("this");
                        CityNextList temp1 = (CityNextList) parent.getItemAtPosition(position);
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_layout_fragment,new SelectCityFragment(SelectCityFragment.THIRD_STATUS,fomfrom,temp1.getId())).commit();
                        break;
                    case THIRD_STATUS:
                        LocalId dd = (LocalId) parent.getItemAtPosition(position);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.getWeatherInfo(dd.getWeather_id());
                        getActivity().getSupportFragmentManager().beginTransaction().remove(SelectCityFragment.this).commit();
                        break;
                }
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
