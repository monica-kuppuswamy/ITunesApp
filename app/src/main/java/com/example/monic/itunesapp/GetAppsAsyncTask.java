package com.example.monic.itunesapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by monic on 10/23/2017.
 */

public class GetAppsAsyncTask extends AsyncTask<String, Integer, ArrayList<Application>> {
    IData activity;
    Context mContect;

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public GetAppsAsyncTask(IData activity) {
        this.activity = activity;
        mContect = (Context) activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Application> doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();
        int count=0;
        try {

            Log.d("URL", params[0]);
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = reader.readLine();
            publishProgress(25);
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            publishProgress(75);
            return ParseApps.AppsJSONParser.parseApps(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<Application> s) {
        super.onPostExecute(s);
        activity.setupData(s);
    }

    public interface IData {
        void setupData(ArrayList<Application> s);
    }
}
