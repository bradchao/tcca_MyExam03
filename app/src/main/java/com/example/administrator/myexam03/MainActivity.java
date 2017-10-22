package com.example.administrator.myexam03;

import android.app.ListActivity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
                try {
                    URL url = new URL("http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx");
                    HttpURLConnection conn =
                            (HttpURLConnection) url.openConnection();
                    conn.connect();

                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(conn.getInputStream()));

                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    while ( (line = reader.readLine()) != null){
                        sb.append(line);
                    }

                    reader.close();
                    //Log.i("brad", sb.toString());
                    parseJSON(sb.toString());
                }catch(Exception e){
                    Log.i("brad", e.toString());
                }
            }
        }.start();
    }


    private void parseJSON(String json){
        try {
            data = new LinkedList<>();
            JSONArray root = new JSONArray(json);
            for (int i=0; i<root.length(); i++){
                JSONObject obj = root.getJSONObject(i);

                HashMap<String,String> row = new HashMap<>();
                row.put(from[0], obj.getString("Name"));
                row.put(from[1], obj.getString("Address"));
                row.put("tel", obj.getString("Tel"));
                row.put("imgfile", obj.getString("PicURL"));

                data.add(row);

            }

            uiHandler.sendEmptyMessage(0);
        }catch(Exception e){

        }
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
