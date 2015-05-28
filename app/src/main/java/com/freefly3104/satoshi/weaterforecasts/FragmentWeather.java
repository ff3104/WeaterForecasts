package com.freefly3104.satoshi.weaterforecasts;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by satoshi on 2015/05/27.
 */
public class FragmentWeather extends Fragment {

    private static final String KEY_CITY_CODE = "key_city_code";

    private TextView tv_location;
    private LinearLayout forecastLayout;
    private ProgressBar progress;

    public static FragmentWeather newInstance(String city_code){
        FragmentWeather fragment = new FragmentWeather();
        Bundle args = new Bundle();
        args.putString(KEY_CITY_CODE, city_code);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);

        tv_location = (TextView) view.findViewById(R.id.tv_location);
        forecastLayout = (LinearLayout) view.findViewById(R.id.ll_forecasts);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        new GetWeatherForecastTask().execute(getArguments().getString(KEY_CITY_CODE));

        return view;
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
                    View view = View.inflate (getActivity(), R.layout.forecasts_row,null);

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
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
