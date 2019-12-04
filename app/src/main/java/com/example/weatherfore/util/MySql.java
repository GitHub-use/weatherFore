package com.example.weatherfore.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySql extends SQLiteOpenHelper {
    private final String CREATE_TABLE_WEATHER_INFO="CREATE TABLE city_info(";

    Context mContext = null;

    public MySql(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
