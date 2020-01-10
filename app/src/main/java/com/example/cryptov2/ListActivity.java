package com.example.cryptov2;

import android.content.Intent;
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
                JSONArray mJsonArray = new JSONArray(s);
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject mJsonObject = mJsonArray.getJSONObject(i);

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
                showDetails();
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        myCryptoList = findViewById(R.id.cryptoList);

        DownloadTask task = new DownloadTask();
        task.execute("https://api.coinmarketcap.com/v1/ticker/");

        createList();
    }

    public void sendIntent(){
        intent.putExtra("Nazwa",name);
        intent.putExtra("Cena",price_usd);
    }

    public void showDetails() {
        intent = new Intent(this, DetailsView.class);

        startActivity(intent);
    }
}
