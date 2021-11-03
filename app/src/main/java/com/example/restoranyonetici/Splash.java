package com.example.restoranyonetici;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        final MediaPlayer ses=MediaPlayer.create(getApplicationContext(), R.raw.opening);
        ses.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Giris = new Intent(Splash.this, Giris.class);
                startActivity(Giris);
            }
        },3000);
    }
}