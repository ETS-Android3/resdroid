package com.example.restoranyonetici;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Giris extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        Button btnY, btnG;

        btnY=(Button)findViewById(R.id.btnYGiris);
        btnG=(Button)findViewById(R.id.btnGGiris);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent YGec = new Intent(getApplicationContext(), YGiris.class);
                startActivity(YGec);
            }
        });

        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GGec = new Intent(getApplicationContext(), KGiris.class);
                startActivity(GGec);
            }
        });

    }
}