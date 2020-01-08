package com.example.cryptov2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {


    public String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<String> currencyList = new ArrayList<>();

        ListView myCryptoList = findViewById(R.id.cryptoList);

        currencyList.add("dgdfkg");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencyList);
        myCryptoList.setAdapter(arrayAdapter);

        myCryptoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                showDetails();
            }
        });

    }

    public void showDetails() {
        Intent intent = new Intent(this, DetailsView.class);
        startActivity(intent);
    }
}
