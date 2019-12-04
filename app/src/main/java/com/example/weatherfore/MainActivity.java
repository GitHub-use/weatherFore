package com.example.weatherfore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.WeatherInfo;
import com.example.weatherfore.util.GetWeatherInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean finsh_net_loading = false;

    List<CityList> cityListList = null;

    WeatherInfo weatherInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Thread(){
            @Override
            public void run() {
                cityListList = GetWeatherInfo.getCityList();
                finsh_net_loading = true;
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                weatherInfo = GetWeatherInfo.getWeatherInfo("");
            }
        }.start();
    }

    public void init(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_change_city){
            if(finsh_net_loading){
                getSupportFragmentManager().beginTransaction().add(R.id.main_layout_fragment,new SelectCityFragment(SelectCityFragment.FIRST_STATUS,cityListList)).addToBackStack(null).commit();
                System.out.println(cityListList.size());
                Log.d("MainActivity:", "onOptionsItemSelected: loading fragment");
            }else{
                Toast.makeText(MainActivity.this,"还未加载完成",Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
