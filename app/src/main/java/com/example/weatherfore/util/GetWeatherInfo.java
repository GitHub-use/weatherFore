package com.example.weatherfore.util;

import com.example.weatherfore.R;
import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.CityNextList;
import com.example.weatherfore.model.LocalId;
import com.example.weatherfore.model.WeatherInfo;
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

    public static List<LocalId> getLocalId(int parent,int id){
        List<LocalId> localIds = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://guolin.tech/api/china/"+String.valueOf(parent)+"/"+String.valueOf(id)).build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        String temp_json_Local_id = null;
        Gson gson = new Gson();
        try{
            temp_json_Local_id = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        localIds = gson.fromJson(temp_json_Local_id,new TypeToken<List<LocalId>>(){}.getType());
        return localIds;
    }

    public static WeatherInfo getWeatherInfo(String locationId){
        WeatherInfo weatherInfo = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://free-api.heweather.net/s6/weather/?location="+locationId+"&key=5b0719c109194c8687237cd0c0554917").build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            String temp_json_weather_info = response.body().string();
            Gson gson = new Gson();
            weatherInfo = gson.fromJson(temp_json_weather_info,WeatherInfo.class);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return weatherInfo;
    }

}
