package com.freefly3104.satoshi.weaterforecasts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView tv_location;
    private LinearLayout forecastLayout;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_location = (TextView) findViewById(R.id.tv_location);
        forecastLayout = (LinearLayout) findViewById(R.id.ll_forecasts);
        progress = (ProgressBar) findViewById(R.id.progress);

        new GetWeatherForecastTask().execute("400040");
    }

    private class GetWeatherForecastTask extends GetWeatherForecastApiTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(WeatherForecast data) {
            super.onPostExecute(data);

            progress.setVisibility(View.GONE);

            if(data != null){
                tv_location.setText(data.location.area + " " + data.location.prefecture + " " + data.location.city);

                // 予報を一覧表示
                for(WeatherForecast.Forecast forecast : data.forecastList){
                    View view = View.inflate(MainActivity.this, R.layout.forecasts_row,null);

                    TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
                    tv_date.setText(forecast.dateLabel);

                    TextView tv_telop = (TextView) view.findViewById(R.id.tv_telop);
                    tv_telop.setText(forecast.telop);

                    TextView tv_temperature = (TextView) view.findViewById(R.id.tv_temperature);
                    tv_temperature.setText(forecast.temperature.toString());


                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_weather);
                    imageView.setTag(forecast.image.url); // 読み込むurlをセット

                    // 画像の読み込み処理の実行
                    new ImageLoaderTask().execute(imageView);

                    forecastLayout.addView(view);

                }

            }else{
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
