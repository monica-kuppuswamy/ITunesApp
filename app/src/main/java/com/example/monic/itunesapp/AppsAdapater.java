package com.example.monic.itunesapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by monic on 10/23/2017.
 */

public class AppsAdapater extends ArrayAdapter<Application> {
    List<Application> mData;
    Context mContext;
    int mResource;
    int p;


    public AppsAdapater(Context context, int resource, List<Application>objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
        this.mData=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        p = position;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mResource,parent,false);
        }
        final Application mu = mData.get(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView4);
        TextView appName = (TextView)convertView.findViewById(R.id.appName);
        TextView appPrice = (TextView)convertView.findViewById(R.id.appPrice);
        ImageView imageView1 = (ImageView) convertView.findViewById(R.id.imageView5);

        appName.setText(mu.getAppName());
        appPrice.setText("Price: " +Double.toString(mu.getPrice()));

        if (mu.price >= 0 && mu.price <= 1.99)
            imageView1.setImageResource(R.drawable.price_low);
        else if (mu.price >= 2 && mu.price <= 5.99)
            imageView1.setImageResource(R.drawable.price_medium);
        else if (mu.price >= 6)
            imageView1.setImageResource(R.drawable.price_high);

        String imageURL =IsNullorEmpty(mu.getSmallImghUrl())?"":mu.getSmallImghUrl().trim();

        if(!IsNullorEmpty(imageURL)){
            Picasso.with(convertView.getContext()).load(imageURL).into(imageView);
        }

        return convertView;

    }

    private boolean IsNullorEmpty(String str) {
        return (str == null || str.isEmpty());
    }
}
