package com.example.monic.itunesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by monic on 10/23/2017.
 */

public class DatabaseDataManager {
    private Context context;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private FilterDAO noteDAO;

    public DatabaseDataManager(Context context) {
        this.context = context;
        dbOpenHelper = new DatabaseOpenHelper(this.context);
        db = dbOpenHelper.getWritableDatabase();
        noteDAO = new FilterDAO(db);
    }

    public void close() {
        if(db!=null) {
            db.close();
        }
    }

    public FilterDAO getNoteDAO() {
        return this.noteDAO;
    }

    public long saveNote(Application note) {
        return this.noteDAO.save(note);
    }

    public boolean updateNote(Application note) {
        return this.noteDAO.update(note);
    }

    public boolean deleteNote(Application note) {
        return this.noteDAO.delete(note);
    }

    public Application getNote(long id) {
        return this.noteDAO.get(id);
    }

    public List<Application> getAllNotes() {
        return this.noteDAO.getAll();
    }
}
