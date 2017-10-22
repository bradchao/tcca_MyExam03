package com.example.administrator.myexam03;

import android.app.ListActivity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends ListActivity {
    private ListView listView;

    private SimpleAdapter adapter;
    private LinkedList<HashMap<String,String>> data;
    private String[] from = {"name","address"};
    private int[] to = {R.id.name, R.id.address};
    private UIHandler uiHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        uiHandler = new UIHandler();

        listView = getListView();

        getJSONData();



    }

    private void getJSONData(){
        new Thread(){
            @Override
            public void run() {

                parseJSON("json");
            }
        }.start();
    }


    private void parseJSON(String json){


        uiHandler.sendEmptyMessage(0);
    }

    private void initListView(){
        adapter = new SimpleAdapter(
                this,data, R.layout.item, from, to);
        listView.setAdapter(adapter);
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initListView();
        }
    }

}
