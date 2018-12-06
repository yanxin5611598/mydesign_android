package com.yx.aircheck.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite数据库连接*/
public class DBUtil extends SQLiteOpenHelper{
    private static final String insertSQL = "create table IF NOT EXISTS checkResult(id integer primary key autoincrement,"+
                                            "tem text,hum text,choh text,pm25 text,pm10 text,range text,username text,gpsinfo text,deviceID text," +
                                            "time timestamp not null default (datetime('now','localtime')))";

    private static final String db_name = "db_check_data";
    public DBUtil(Context context) {
        super(context, db_name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(insertSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
