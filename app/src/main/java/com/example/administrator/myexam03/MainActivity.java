package com.example.administrator.myexam03;

import android.app.ListActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        listView = getListView();

        data = new LinkedList<>();
        HashMap<String,String> d0 = new HashMap<>();
        d0.put(from[0], "湖莓宴餐坊");
        d0.put(from[1], "苗栗縣大湖鄉富興村八寮彎2-7號4樓");
        data.add(d0);

        HashMap<String,String> d1 = new HashMap<>();
        d1.put(from[0], "神雕邨複合式茶棧");
        d1.put(from[1], "苗栗縣三義鄉廣盛村廣聲新城38鄰2巷26號");
        data.add(d1);


        adapter = new SimpleAdapter(
                this,data, R.layout.item, from, to);
        listView.setAdapter(adapter);


    }
}
