package com.example.monic.itunesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monic on 10/23/2017.
 */

public class FilterDAO {
    private SQLiteDatabase db;

    public FilterDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Application note) {
        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_NAME, note.getAppName());
        values.put(FilterTable.COLUMN_PRICE, note.getPrice());
        values.put(FilterTable.COLUMN_THUMBURL, note.getBigImgUrl());
        return db.insert(FilterTable.TABLENAME, null, values);
    }

    public boolean update(Application note) {
        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_NAME, note.getAppName());
        values.put(FilterTable.COLUMN_PRICE, note.getPrice());
        values.put(FilterTable.COLUMN_THUMBURL, note.getBigImgUrl());
        return db.update(FilterTable.TABLENAME, values, FilterTable.COLUMN_ID + "=?",
                new String[]{note.get_id()+""}) > 0;
    }

    public boolean delete(Application note) {
        return db.delete(FilterTable.TABLENAME, FilterTable.COLUMN_ID + "=?",
                new String[]{note.get_id()+""}) > 0;
    }

    public Application get(long id) {
        Application note = null;
        Cursor c = db.query(true, FilterTable.TABLENAME, new String[]{FilterTable.COLUMN_ID,
                        FilterTable.COLUMN_NAME, FilterTable.COLUMN_PRICE, FilterTable.COLUMN_THUMBURL},
                FilterTable.COLUMN_ID+"=?", new String[]{id+""}, null, null, null, null, null);
        if(c != null && c.moveToFirst()) {
            note = buildNoteFromCursor(c);
            if(!c.isClosed())
                c.close();
        }
        return note;
    }

    public List<Application> getAll() {
        ArrayList<Application> notes = new ArrayList<Application>();
        Cursor c = db.query(FilterTable.TABLENAME, new String[]{FilterTable.COLUMN_ID,
                FilterTable.COLUMN_NAME, FilterTable.COLUMN_PRICE, FilterTable.COLUMN_THUMBURL}, null, null, null, null, null);

        if(c!= null && c.moveToFirst()){
            do{
                Application note = buildNoteFromCursor(c);
                notes.add(note);
            }while (c.moveToNext());

            if(!c.isClosed())
                c.close();
        }
        return notes;
    }

    private Application buildNoteFromCursor(Cursor c) {
        Application note = null;
        if(c!= null) {
            note = new Application();
            note.set_id(c.getLong(0));
            note.setAppName(c.getString(1));
            note.setPrice(c.getDouble(2));
            note.setBigImgUrl(c.getString(3));
        }
        return note;
    }
}
