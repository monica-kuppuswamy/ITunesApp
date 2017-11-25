package com.example.monic.itunesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetAppsAsyncTask.IData{
    ArrayList<Application> appList = new ArrayList<Application>();
    AppsAdapater adapater;
    ListView listView;
    ArrayList<Application> favList = new ArrayList<Application>();
    DatabaseDataManager dm;
    TextView noAppMsg;
    String url = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DatabaseDataManager(MainActivity.this);
        additemstohorizontalList(dm.getAllNotes());

        final Switch simpleSwitch = (Switch) findViewById(R.id.switch1);

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    simpleSwitch.setText("Ascending");
                    Comparator<Application> compareAppsAsc = new Comparator<Application>() {
                        @Override
                        public int compare(Application o1, Application o2) {
                            return o1.getPrice() > o2.getPrice() ? 1 : -1;
                        }
                    };
                    Collections.sort(appList, compareAppsAsc);
                    listView.setAdapter(adapater);
                    adapater.notifyDataSetChanged();
                }else
                    simpleSwitch.setText("Descending");
                    Comparator<Application> compareAppsDesc = new Comparator<Application>() {
                        @Override
                        public int compare(Application o1, Application o2) {
                            return o1.getPrice() > o2.getPrice() ? -1 : 1;
                        }
                };
                Collections.sort(appList, compareAppsDesc);
                listView.setAdapter(adapater);
                adapater.notifyDataSetChanged();
            }
        });

        ImageView refresh = (ImageView) findViewById(R.id.imageView2);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAppsAsyncTask(MainActivity.this).execute(url);
            }
        });

        new GetAppsAsyncTask(MainActivity.this).execute(url);
    }

    public void removeRecords(Application a) {
        dm = new DatabaseDataManager(MainActivity.this);
        dm.deleteNote(a);
        additemstohorizontalList(dm.getAllNotes());
        new GetAppsAsyncTask(MainActivity.this).execute(url);
    }

    public void additemstohorizontalList(List<Application> appList) {

        HorizontalScrollView sv_main = (HorizontalScrollView) findViewById(R.id.horizontalscroll);
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.HORIZONTAL);
        sv_main.removeAllViews();
        noAppMsg = (TextView) findViewById(R.id.noApp);
        if(appList.size() == 0) {
            noAppMsg.setVisibility(View.VISIBLE);
        } else {
            noAppMsg.setVisibility(View.INVISIBLE);
            for(Application user : appList) {
                final Application a = user;
                hlistitem item = new hlistitem(MainActivity.this);
                View itemView = (View) item;
                item.name.setText(user.appName);
                item.price.setText(user.price+"");
                Picasso.with(MainActivity.this).load(user.getBigImgUrl()).into(item.iconimage);
                if (user.price >= 0 && user.price <= 1.99)
                    item.dollars.setImageResource(R.drawable.price_low);
                else if (user.price >= 2 && user.price <= 5.99)
                    item.dollars.setImageResource(R.drawable.price_medium);
                else if (user.price >= 6)
                    item.dollars.setImageResource(R.drawable.price_high);

                item.deleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeRecords(a);
                    }
                });
                container.addView(itemView);
            }
            sv_main.addView(container);
        }
    }

    @Override
    public void setupData(ArrayList<Application> s) {
        appList = s;
        dm = new DatabaseDataManager(MainActivity.this);
        List<Application> filterList = dm.getAllNotes();
        appList.removeAll(filterList);
        Comparator<Application> compareAppsAsc = new Comparator<Application>() {
            @Override
            public int compare(Application o1, Application o2) {
                return o1.getPrice() > o2.getPrice() ? 1 : -1;
            }
        };
        Collections.sort(appList, compareAppsAsc);
        listView = (ListView) findViewById(R.id.applist);
        adapater = new AppsAdapater(MainActivity.this, R.layout.app_item, appList);
        listView.setAdapter(adapater);
        adapater.notifyDataSetChanged();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dm = new DatabaseDataManager(MainActivity.this);
                Application a = new Application();
                a.setAppName(appList.get(position).getAppName());
                a.setPrice(appList.get(position).getPrice());
                a.setBigImgUrl(appList.get(position).getBigImgUrl());
                dm.saveNote(a);
                appList.remove(position);
                adapater.notifyDataSetChanged();
                List<Application> notes = dm.getAllNotes();
                additemstohorizontalList(notes);
                return false;
            }
        });
    }
}
