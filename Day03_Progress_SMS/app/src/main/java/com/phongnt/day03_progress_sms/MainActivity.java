package com.phongnt.day03_progress_sms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button btnSetup;
ProgressDialog myPro_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSetup =findViewById(R.id.btnSetup);
        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPro_bar = new ProgressDialog(MainActivity.this);
                myPro_bar.setTitle("Please wait....");
                myPro_bar.setMessage("Loading...");
                myPro_bar.setProgressStyle(myPro_bar.STYLE_HORIZONTAL);
                myPro_bar.setProgress(0);
                myPro_bar.setMax(100);
                myPro_bar.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(myPro_bar.getProgress() < myPro_bar.getMax()) {
                                Thread.sleep(1000);
                                handler.sendMessage(handler.obtainMessage()); //gui message tu handler voi noi dung laf tang 10 don vi
                                if(myPro_bar.getProgress() == myPro_bar.getMax()) {
                                    myPro_bar.dismiss();
                                }
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    myPro_bar.incrementProgressBy(10); //tang progress ba len 10 don vi
                }
            };
        });
    }

    public void clickToSMSActivity(View view) {
        Intent intent = new Intent(this, SMSActivity.class);
        startActivity(intent);

    }

}
