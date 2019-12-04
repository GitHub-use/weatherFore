package com.example.weatherfore;

import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.CityNextList;
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
        List<CityNextList> cityLists = null;
        try {
            cityLists = GetWeatherInfo.getCityNext(2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(cityLists.get(0).getName());

    }
}