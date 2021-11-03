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

public class KGiris extends AppCompatActivity {
    EditText KAdi, KSifre;
    Button Giris;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Geri buttonuna basıldığında
    @Override
    public void onBackPressed() {
        Intent Girisgit = new Intent(KGiris.this, Giris.class);
        startActivity(Girisgit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_giris);

        KAdi=(EditText) findViewById(R.id.KG_tbx_kadi);
        KSifre=(EditText) findViewById(R.id.KG_tbx_ksifre);

        Giris=(Button) findViewById(R.id.KG_btn_giris);

        //Giriş tutma
        SharedPreferences Sp=getSharedPreferences("shared_pref_gs", MODE_PRIVATE);

        Intent GarsonGiris=new Intent(KGiris.this, KAnasayfa.class);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        if(!Sp.getString("garsonadi", "").equals(""))
            startActivity(GarsonGiris);

        Giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(KAdi.getText().toString().length()<1)
                    KAdi.setError("Lütfen bir kullanıcı adı giriniz");
                if(KSifre.getText().toString().length()<8)
                    KSifre.setError("En az 8 hanli bir şifre giriniz");
                else{
                    //Veri tabanı bilgi getirme
                    Ref.child("Garsonlar").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot garsonlar : snapshot.getChildren()) {
                                MKullanici garson = garsonlar.getValue(MKullanici.class);

                                if(garson.getAd().equals(KAdi.getText().toString()) && garson.getSifre().equals(KSifre.getText().toString())){
                                    SharedPreferences.Editor editor= Sp.edit();
                                    editor.putString("garsonadi", KAdi.getText().toString());
                                    editor.commit();

                                    startActivity(GarsonGiris);

                                }
                            }
                            if(Sp.getString("garsonadi", "").equals(""))
                                Toast.makeText(KGiris.this, "Kullanıcı adı veya şifre yanlış", Toast.LENGTH_LONG).show();
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