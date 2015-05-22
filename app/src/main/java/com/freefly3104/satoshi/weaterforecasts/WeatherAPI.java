package com.freefly3104.satoshi.weaterforecasts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPI {

    private static final String URL_TEXT = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";

    public static String getWeather(String pointId)throws IOException {

        URL url = new URL(URL_TEXT + pointId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        StringBuilder sb = new StringBuilder();
        try {

            con.setRequestMethod("GET");
            con.connect();

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }

        }catch (Exception e){

        }finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception e) {
                }
            }
        }
        return sb.toString();
    }
}
