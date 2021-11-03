package com.example.restoranyonetici;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class YMenu extends AppCompatActivity {
    Button btnK, btnY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_menu);

        btnK=(Button)findViewById(R.id.YMN_btn_kislemleri);
        btnY=(Button)findViewById(R.id.YMN_btn_yislemleri);

        btnK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kategoriler = new Intent(getApplicationContext(), YMNKategoriler.class);
                startActivity(kategoriler);
            }
        });

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yemekler = new Intent(getApplicationContext(), YMNYemekler.class);
                startActivity(yemekler);
            }
        });
    }
}