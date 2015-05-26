package com.freefly3104.satoshi.weaterforecasts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView1);

        new GetWeatherForecastTask().execute("400040");
    }

    private class GetWeatherForecastTask extends GetWeatherForecastApiTask {
        @Override
        protected void onPostExecute(WeatherForecast data) {
            super.onPostExecute(data);

            if(data != null){
                textView.setText(data.location.area + " " + data.location.prefecture + " " + data.location.city);

                // 予報を一覧表示
                for(WeatherForecast.Forecast forecast : data.forecastList){
                    textView.append("\n");
                    textView.append(forecast.dateLabel + " " + forecast.telop);
                }

            }else{
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
