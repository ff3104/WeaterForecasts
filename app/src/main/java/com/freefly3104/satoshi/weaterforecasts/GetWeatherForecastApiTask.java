package com.freefly3104.satoshi.weaterforecasts;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by satoshi on 2015/05/22.
 */
public class GetWeatherForecastApiTask extends AsyncTask<String, Void, WeatherForecast> {

    @Override
    protected WeatherForecast doInBackground(String... params) {
        try{
            return WeatherAPI.getWeather(params[0]);
        }catch (IOException e){
            return null;
        }catch (JSONException e){
            return null;
        }
    }
}
