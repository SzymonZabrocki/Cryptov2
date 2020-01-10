package com.example.cryptov2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static String name;
    public static String price_usd;
    ListView myCryptoList;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> currencyList;
    Intent intent;
    JSONObject mJsonObject;
    JSONObject intentJsonArray;
    JSONArray mJsonArray;
    String intentName;
    String intentPrice_usd;
    SharedPreferences sharedPreferences;

    public class DownloadTask extends AsyncTask<String, Void, String> {

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
                mJsonArray = new JSONArray(s);
                for (int i = 0; i < mJsonArray.length(); i++) {
                    mJsonObject = mJsonArray.getJSONObject(i);

                    //Przypisanie elementu json do zmiennej
                    name = mJsonObject.getString("name");
                    price_usd = mJsonObject.getString("price_usd");

                    //Dodawanie kolejnej pozycji na liście
                    myCryptoList.setAdapter(arrayAdapter);
                    currencyList.add(name + "   " + price_usd);


                    Log.i("namexD", name);
                    Log.i("pricexD", price_usd);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void createList() {
        currencyList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencyList);

        myCryptoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                try {

                    intentJsonArray = mJsonArray.getJSONObject(position);
                    Log.i("wybranaPozycjaNaLiscie", String.valueOf(mJsonArray.getJSONObject(position)));
                    intentName = intentJsonArray.getString("name");
                    intentPrice_usd = intentJsonArray.getString("price_usd");
                    sendIntent(intentName,intentPrice_usd);
                    showDetails();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void readFavorite(){
        sharedPreferences = this.getSharedPreferences("com.example.cryptov2", Context.MODE_PRIVATE);
        ArrayList<String> savedFavorites = new ArrayList<>();
        try {
            savedFavorites = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("favorites", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("savedFavorites ", savedFavorites.toString());

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        myCryptoList = findViewById(R.id.cryptoList);

        DownloadTask task = new DownloadTask();
        task.execute("https://api.coinmarketcap.com/v1/ticker/");
        intent = new Intent(this, DetailsView.class);
        createList();
        readFavorite();
    }

    public void sendIntent(String intentname, String intentprice_usd){
        intent.putExtra("Nazwa",intentname);
        intent.putExtra("Cena",intentprice_usd);
    }

    public void showDetails() {

        startActivity(intent);

    }
}
