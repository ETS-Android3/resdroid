package com.example.restoranyonetici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class YGarson extends AppCompatActivity {

    //Widgetler
    Button btnEkle, btnGuncelle, btnSil;
    EditText Gadi, Gsifre;
    ListView Bilgiler;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Listview array
    private List<MKullanici> list_garsonlar= new ArrayList<>();

    private  MKullanici seciliKullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_garson);
        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        btnEkle = (Button) findViewById(R.id.YG_btn_ekle);
        btnGuncelle = (Button) findViewById(R.id.YG_btn_guncelle);
        btnSil = (Button) findViewById(R.id.YG_btn_sil);

        Gadi = (EditText) findViewById(R.id.YG_tbx_adi);
        Gsifre = (EditText) findViewById(R.id.YG_tbx_sifre);

        Bilgiler = (ListView) findViewById(R.id.YG_lv_bilgiler);

        //Listview satıra basılınca seçme
        Bilgiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MKullanici sgarson = (MKullanici) adapterView.getItemAtPosition(i);
                seciliKullanici = sgarson;

                Gadi.setText(sgarson.getAd());
                Gsifre.setText(sgarson.getSifre());

                for (int j = 0; j < adapterView.getChildCount(); j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                view.setBackgroundColor(Color.GRAY);
            }
        });

        //Veri tabanı bilgi getirme
        Ref.child("Garsonlar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list_garsonlar.size() > 0)
                    list_garsonlar.clear();
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MKullanici garsonlar = veriler.getValue(MKullanici.class);
                    list_garsonlar.add(garsonlar);
                }

                ListviewAdaptor adaptor= new ListviewAdaptor(YGarson.this, list_garsonlar);
                Bilgiler.setAdapter(adaptor);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //ekle
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Gadi.getText().toString().length()==0 || Gsifre.getText().toString().length()==0)
                    Toast.makeText(YGarson.this,"Lütfen tüm alanları doldurunuz", Toast.LENGTH_LONG).show();
                else {

                    MKullanici garson = new MKullanici(UUID.randomUUID().toString(), Gadi.getText().toString(), Gsifre.getText().toString());

                    Ref.child("Garsonlar").child(garson.getUid()).setValue(garson);

                    IslemSonrasiTemizle();
                }
            }
        });

        //guncelle
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Gadi.getText().toString().length()==0 || Gsifre.getText().toString().length()==0 || seciliKullanici==null)
                    Toast.makeText(YGarson.this,"Lütfen tüm alanları doldurunuz veya bir garson seçiniz", Toast.LENGTH_LONG).show();
                else {
                    MKullanici garson = new MKullanici(seciliKullanici.getUid(), Gadi.getText().toString(), Gsifre.getText().toString());

                    Ref.child("Garsonlar").child(garson.getUid()).child("ad").setValue(garson.getAd());
                    Ref.child("Garsonlar").child(garson.getUid()).child("sifre").setValue(garson.getSifre());

                    IslemSonrasiTemizle();
                }
            }
        });

        //sil
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seciliKullanici==null)
                    Toast.makeText(YGarson.this,"Lütfen bir garson seçiniz", Toast.LENGTH_LONG).show();
                else {
                    Ref.child("Garsonlar").child(seciliKullanici.getUid()).removeValue();
                    seciliKullanici=null;
                    IslemSonrasiTemizle();
                }
            }
        });
    }
    public void IslemSonrasiTemizle(){
        Gadi.setText("");
        Gsifre.setText("");
    }

}