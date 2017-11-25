package com.example.monic.itunesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by monic on 10/23/2017.
 */

public class ParseApps {
    static public class AppsJSONParser
    {
        static ArrayList<Application> parseApps(String in) throws JSONException, UnsupportedEncodingException {

            ArrayList<Application> apps = new ArrayList<Application>();

            JSONObject root = new JSONObject(in);
            JSONArray trackJsonArray = root.getJSONObject("feed").getJSONArray("entry");
            for(int i = 0; i < trackJsonArray.length(); i++)
            {
                JSONObject appJsonobject = trackJsonArray.getJSONObject(i);
                Application t = new Application();
                t.setAppName(appJsonobject.getJSONObject("im:name").getString("label"));
                t.setPrice(Double.parseDouble(appJsonobject.getJSONObject("im:price").getString("label").substring(1)));
                t.setSmallImghUrl(appJsonobject.getJSONArray("im:image").getJSONObject(0).getString("label"));
                t.setBigImgUrl(appJsonobject.getJSONArray("im:image").getJSONObject(2).getString("label"));
                apps.add(t);
            }
            return apps;
        }
    }
}
