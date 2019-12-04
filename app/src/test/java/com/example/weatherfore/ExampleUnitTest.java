package com.example.weatherfore;

import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.CityNextList;
import com.example.weatherfore.model.LocalId;
import com.example.weatherfore.model.WeatherInfo;
import com.example.weatherfore.util.GetWeatherInfo;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        List<LocalId> cityLists = null;
        try {
            cityLists = GetWeatherInfo.getLocalId(2,2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        for(LocalId temp : cityLists){
            System.out.println(temp.getWeather_id());
        }
        WeatherInfo weatherInfo = GetWeatherInfo.getWeatherInfo("CN101021200");
        if(weatherInfo!=null){
            System.out.println(weatherInfo.getHeWeather6().get(0).getNow().getWind_dir());
        }else{
            System.out.println("数据获取错误");
        }

    }
}