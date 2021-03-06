package com.example.monic.itunesapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by monic on 10/23/2017.
 */

public class FilterTable {
    static final String TABLENAME = "Filter";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_THUMBURL = "thumb_url";

    static public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + "(");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_NAME + " text not null, ");
        sb.append(COLUMN_THUMBURL + " text not null, ");
        sb.append(COLUMN_PRICE + " text not null);");
        try {
            db.execSQL(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
        FilterTable.onCreate(db);
    }
}
