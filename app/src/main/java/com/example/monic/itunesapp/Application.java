package com.example.monic.itunesapp;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by monic on 10/23/2017.
 */

public class Application implements Comparator<Application>{
    private long _id;
    String appName;
    double price;
    String smallImghUrl;
    String bigImgUrl;

    public Application() {

    }
    public Application(String appName, double price, String smallImghUrl, String bigImgUrl) {
        this.appName = appName;
        this.price = price;
        this.smallImghUrl = smallImghUrl;
        this.bigImgUrl = bigImgUrl;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSmallImghUrl() {
        return smallImghUrl;
    }

    public void setSmallImghUrl(String smallImghUrl) {
        this.smallImghUrl = smallImghUrl;
    }

    public String getBigImgUrl() {
        return bigImgUrl;
    }

    public void setBigImgUrl(String bigImgUrl) {
        this.bigImgUrl = bigImgUrl;
    }

    @Override
    public int compare(Application o1, Application o2) {
        if (o1.getPrice() > o2.getPrice()) {
            return -1;
        } else if (o1.getPrice() < o2.getPrice()) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Application)){
            return false;
        }
        Application tobj = (Application) obj;
        return this.getAppName().equals(tobj.getAppName());
    }
}
