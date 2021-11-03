package com.example.restoranyonetici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class YGiris extends AppCompatActivity {
    EditText YAdi, YSifre;
    Button Giris;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Geri buttonuna basıldığında
    @Override
    public void onBackPressed() {
        Intent Girisgit = new Intent(YGiris.this, Giris.class);
        startActivity(Girisgit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_giris);

        YAdi=(EditText) findViewById(R.id.YGS_tbx_yadi);
        YSifre=(EditText) findViewById(R.id.YGS_tbx_ysifre);

        Giris=(Button) findViewById(R.id.YGS_btn_giris);

        //Giriş tutma
        SharedPreferences Sp=getSharedPreferences("shared_pref_ys", MODE_PRIVATE);

        Intent YoneticicGiris=new Intent(YGiris.this, YAnasayfa.class);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        if(!Sp.getString("yoneticiadi", "").equals(""))
            startActivity(YoneticicGiris);

        Giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(YAdi.getText().toString().length()<1)
                    YAdi.setError("Lütfen bir yönetici adı giriniz");
                if(YSifre.getText().toString().length()<8)
                    YSifre.setError("En az 8 hanli bir Şifre giriniz");
                else{
                    //Veri tabanı bilgi getirme
                    Ref.child("Yoneticiler").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot yoneticiler : snapshot.getChildren()) {
                                MYonetici yonetici = yoneticiler.getValue(MYonetici.class);

                                if(yonetici.getAd().equals(YAdi.getText().toString()) && yonetici.getSifre().equals(YSifre.getText().toString())){
                                    SharedPreferences.Editor editor= Sp.edit();
                                    editor.putString("yoneticiadi", YAdi.getText().toString());
                                    editor.putString("yoneticisuper", yonetici.getSupery());
                                    editor.commit();

                                    startActivity(YoneticicGiris);

                                }
                            }
                            if(Sp.getString("yoneticiadi", "").equals(""))
                                Toast.makeText(YGiris.this, "Yönetici adı veya şifre yanlış", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }
        });
    }
}