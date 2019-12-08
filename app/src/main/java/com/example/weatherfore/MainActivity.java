package com.example.weatherfore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherfore.model.CityList;
import com.example.weatherfore.model.WeatherInfo;
import com.example.weatherfore.util.GetWeatherInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean finsh_net_loading = false;

    List<CityList> cityListList = null;

    WeatherInfo weatherInfo = null;

    final int FINSH_NET_LOADING = 92;


    private Handler han = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == FINSH_NET_LOADING) {
                init();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Thread() {
            @Override
            public void run() {
                cityListList = GetWeatherInfo.getCityList();
                finsh_net_loading = true;
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                weatherInfo = GetWeatherInfo.getWeatherInfo("");
            }
        }.start();
        FileInputStream in = null;
        BufferedReader reader = null ;
        StringBuilder sb = new StringBuilder();
        try{
            in = openFileInput("dat");
            reader  = new BufferedReader(new InputStreamReader(in));
            String tt = null;
            while ((tt = reader.readLine())!=null){
                sb.append(tt);
            }
            if(sb.length()!=0){
            getWeatherInfo(sb.toString());}

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                reader.close();
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
        LinearLayout scrollView = findViewById(R.id.weatherinfo_linearlayout);
        scrollView.removeAllViews();
        for(WeatherInfo.HeWeather6Bean.DailyForecastBean tempp : tt){
            View view = getLayoutInflater().inflate(R.layout.weatherinfo,null);
            TextView textView = view.findViewById(R.id.weatherinfo_day);
            textView.setText(tempp.getDate());
            ImageView imageView = view.findViewById(R.id.weatherinfo_image);
            imageView.setImageResource(getResources().getIdentifier("p"+tempp.getCond_code_d(),"drawable",getPackageName()));
            TextView textView1 = view.findViewById(R.id.weatherinfo_hig_tem);
            textView1.setText(tempp.getTmp_max());
            TextView textView2 = view.findViewById(R.id.weatherinfo_low_tem);
            textView2.setText(tempp.getTmp_min());
            scrollView.addView(view);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy:Destroy ");
        FileOutputStream outputStream = null;
        BufferedWriter writer = null;
        try{
            outputStream = openFileOutput("dat",Context.MODE_PRIVATE);
            writer= new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(weatherInfo.getHeWeather6().get(0).getBasic().getCid());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                writer.close();
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
