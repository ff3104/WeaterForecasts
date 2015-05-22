package com.freefly3104.satoshi.weaterforecasts;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {

    private Handler handler;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        textView = (TextView) findViewById(R.id.textView1);

        Thread thread = new Thread() {

            @Override
            public void run() {

                try{
                    // インナークラスなのでfinalが必要
                    final String date = WeatherAPI.getWeather("400040");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(date);
                        }
                    });

                } catch (final IOException e){

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }

        };
        thread.start();
    }

}
