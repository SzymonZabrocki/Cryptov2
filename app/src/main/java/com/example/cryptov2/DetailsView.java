package com.example.cryptov2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsView extends AppCompatActivity {

    TextView currencyName;
    TextView value;
    TextView change_1h;
    TextView change_24h;
    TextView change_7d;
    String Nazwa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        currencyName = findViewById(R.id.currencyName);
        value = findViewById(R.id.value);
        change_1h = findViewById(R.id.change_1h);
        change_24h = findViewById(R.id.change_24h);
        change_7d = findViewById(R.id.change_7d);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            Nazwa = extras.getString("Nazwa");
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

}
