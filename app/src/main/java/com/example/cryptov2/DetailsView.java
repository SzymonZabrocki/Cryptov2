package com.example.cryptov2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DetailsView extends AppCompatActivity {

    Button addToFavorite;
    TextView currencyName;
    TextView value;
    TextView change_1h;
    TextView change_24h;
    TextView change_7d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        addToFavorite = findViewById(R.id.addToFavorite);
        currencyName = findViewById(R.id.currencyName);
        value = findViewById(R.id.value);
        change_1h = findViewById(R.id.change_1h);
        change_24h = findViewById(R.id.change_24h);
        change_7d = findViewById(R.id.change_7d);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            String Nazwa = extras.getString("Nazwa");
            String Cena = extras.getString("Cena");
            float cenaF = Float.valueOf(Cena);
            String prettyCena = String.format("%.2f", cenaF);
            String OneH = extras.getString("1h");
            String TwentyFourH = extras.getString("24h");
            String SevenD = extras.getString("7d");

            currencyName.setText(Nazwa);
            value.setText(prettyCena + " $");
            change_1h.setText(OneH + "%");
            change_24h.setText(TwentyFourH+ "%");
            change_7d.setText(SevenD+"%");
        }

    }


    public void addToFav(View view) {
        //Todo: dodawanie wybranej waluty do ulubionych

    }
}
