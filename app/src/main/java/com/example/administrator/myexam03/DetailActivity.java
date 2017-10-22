package com.example.administrator.myexam03;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private TextView name, addr, tel;
    private String imgfile;
    private ImageView img;
    private Bitmap bmp;
    private UIHandler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        uiHandler = new UIHandler();

        name = (TextView)findViewById(R.id.name);
        addr = (TextView)findViewById(R.id.addr);
        tel = (TextView)findViewById(R.id.tel);
        img = (ImageView)findViewById(R.id.imageView);


        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        tel.setText(intent.getStringExtra("tel"));
        addr.setText(intent.getStringExtra("addr"));

        imgfile = intent.getStringExtra("imgfile");
        getImage(imgfile);


    }

    private void getImage(final String urlString){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    bmp = BitmapFactory.decodeStream(conn.getInputStream());
                    uiHandler.sendEmptyMessage(0);
                }catch(Exception e){

                }
            }
        }.start();
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            img.setImageBitmap(bmp);

        }
    }


}
