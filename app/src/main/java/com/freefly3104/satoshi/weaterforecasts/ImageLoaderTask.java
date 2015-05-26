package com.freefly3104.satoshi.weaterforecasts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by satoshi on 2015/05/26.
 */
public class ImageLoaderTask extends AsyncTask<ImageView, Void, Bitmap> {

    private ImageView imageView;

    @Override
    protected Bitmap doInBackground(ImageView... params) {
        imageView = params[0];
        String url = (String) imageView.getTag();
        try{
            return getImage(url);
        }catch(IOException e){
            return null;
        }

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getImage(String uri) throws IOException{

        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        try {

            con.setRequestMethod("GET");
            con.connect();

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return BitmapFactory.decodeStream (con.getInputStream());
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
        return null;
    }
}
