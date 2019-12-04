package com.example.weatherfore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.WeatherInfo;
import com.example.weatherfore.util.GetWeatherInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean finsh_net_loading = false;

    List<CityList> cityListList = null;

    WeatherInfo weatherInfo = null;

    final int FINSH_NET_LOADING = 92;


    private Handler han = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==FINSH_NET_LOADING){
                init();
            }
        }
    };

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
        TextView textView_city_info = findViewById(R.id.city_info);
        textView_city_info.setText(weatherInfo.getHeWeather6().get(0).getBasic().getParent_city()+"--"+weatherInfo.getHeWeather6().get(0).getBasic().getLocation());
        ImageView imageView_info_show = findViewById(R.id.info_show_image);
        System.out.println(weatherInfo.getHeWeather6().get(0).getNow().getCond_txt());
        imageView_info_show.setImageResource(getResources().getIdentifier("p"+weatherInfo.getHeWeather6().get(0).getNow().getCond_code(),"drawable",getBaseContext().getPackageName()));
        TextView textView_info_ = findViewById(R.id.info_show_text);
        textView_info_.setText(weatherInfo.getHeWeather6().get(0).getNow().getFl());
        List<WeatherInfo.HeWeather6Bean.DailyForecastBean> tt = weatherInfo.getHeWeather6().get(0).getDaily_forecast();
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
                //getSupportFragmentManager().beginTransaction().add(R.id.main_layout_fragment,new SelectCityFragment(SelectCityFragment.FIRST_STATUS,cityListList)).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.main_layout_fragment,new SelectCityFragment(SelectCityFragment.FIRST_STATUS)).commit();
                System.out.println(cityListList.size());
                Log.d("MainActivity:", "onOptionsItemSelected: loading fragment");
            }else{
                Toast.makeText(MainActivity.this,"还未加载完成",Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public void getWeatherInfo(final String localId){
        new Thread(){
            @Override
            public void run() {
                weatherInfo = GetWeatherInfo.getWeatherInfo(localId);
                Message msg = new Message();
                msg.what=FINSH_NET_LOADING;
                han.sendMessage(msg);

            }
        }.start();
    }
}
