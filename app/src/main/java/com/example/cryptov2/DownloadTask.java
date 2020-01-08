package com.example.cryptov2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadTask extends AsyncTask<String, Void, String> {

    public String name;
    public String price_usd;
    public String symbol;

    @Override
    public String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            int data = reader.read();
            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onPostExecute(String s) {
        super.onPostExecute(s);
        //Log.i("JSON",s);
        try {
            JSONArray mJsonArray = new JSONArray(s);
            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject mJsonObject = mJsonArray.getJSONObject(i);

                name = mJsonObject.getString("name");
                price_usd = mJsonObject.getString("price_usd");
                symbol = mJsonObject.getString("symbol");

                Log.i("namexD", name);
                Log.i("pricexD", price_usd);
                Log.i("symbolxD", symbol);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}