package com.example.weatherfore.util;

import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.CityNextList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;
import okhttp3.Request;
import okhttp3.OkHttpClient;

public class GetWeatherInfo {
    public static List<CityList> getCityList() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://guolin.tech/api/china").build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        Gson gson = new Gson();
        String temp_json_city_list = null;
        try {
            temp_json_city_list = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        List<CityList> cityLists = gson.fromJson(temp_json_city_list,new TypeToken<List<CityList>>(){}.getType());
        return cityLists;
    }

    public static List<CityNextList> getCityNext(int cityId){
        List<CityNextList> cityNextLists =null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://guolin.tech/api/china/"+String.valueOf(cityId)).build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        String temp_json_city_next_list = null;
        try{
            temp_json_city_next_list = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        cityNextLists = gson.fromJson(temp_json_city_next_list,new TypeToken<List<CityNextList>>(){}.getType());
        return cityNextLists;
    }


}
