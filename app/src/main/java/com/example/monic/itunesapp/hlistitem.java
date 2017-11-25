package com.example.monic.itunesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by monic on 10/23/2017.
 */

public class hlistitem extends LinearLayout {
    public TextView name,price;
    public ImageView iconimage,dollars, deleteImage;

    public hlistitem(Context context) {
        super(context);
        inflateXML(context);
    }

    private void inflateXML(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.bottomlistitem, this);
        this.name = (TextView) findViewById(R.id.name);
        this.price = (TextView) findViewById(R.id.price);
        this.iconimage = (ImageView) findViewById(R.id.imageView);
        this.dollars = (ImageView) findViewById(R.id.dollars);
        this.deleteImage = (ImageView) findViewById(R.id.deleteicon);
    }
}
