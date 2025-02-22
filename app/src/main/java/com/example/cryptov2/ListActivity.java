package com.example.cryptov2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    JSONObject mJsonObject;
    JSONObject intentJsonArray;
    JSONArray mJsonArray;
    String intentName;
    String intentPrice_usd;
    String intentOne_Hour;
    String intentTwentyFour_Hour;
    String intentSeven_Days;
    Intent intent;
    ImageView loading;

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
            loading.setVisibility(View.INVISIBLE);
            try {
                mJsonArray = new JSONArray(s);
                for (int i = 0; i < mJsonArray.length(); i++) {
                    mJsonObject = mJsonArray.getJSONObject(i);

                    //Przypisanie elementu json do zmiennej
                    name = mJsonObject.getString("name");
                    price_usd = mJsonObject.getString("price_usd");

                    //Dodawanie kolejnej pozycji na liście
                    myCryptoList.setAdapter(arrayAdapter);
                    currencyList.add(name);


                    Log.i("namexD", name);
                    Log.i("pricexD", price_usd);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        myCryptoList = findViewById(R.id.cryptoList);
        loading = findViewById(R.id.loadingImg);
        loading.animate().rotation(100000).setDuration(100000);

        DownloadTask task = new DownloadTask();
        task.execute("https://api.coinmarketcap.com/v1/ticker/");
        intent = new Intent(this, DetailsView.class);
        createList();
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
                    intentOne_Hour = intentJsonArray.getString("percent_change_1h");
                    intentTwentyFour_Hour = intentJsonArray.getString("percent_change_24h");
                    intentSeven_Days = intentJsonArray.getString("percent_change_7d");
                    sendIntent(intentName, intentPrice_usd, intentOne_Hour, intentTwentyFour_Hour, intentSeven_Days);
                    showDetails();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void sendIntent(String intentName, String intentPrice_usd, String intentOne_Hour, String intentTwentyFour_Hour, String intentSeven_Days) {
        intent.putExtra("Nazwa", intentName);
        intent.putExtra("Cena", intentPrice_usd);
        intent.putExtra("1h", intentOne_Hour);
        intent.putExtra("24h", intentTwentyFour_Hour);
        intent.putExtra("7d", intentSeven_Days);
    }

    public void showDetails() {
        startActivity(intent);
    }
}
