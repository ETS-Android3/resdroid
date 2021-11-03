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

public class YMasa extends AppCompatActivity {
    //Widgetler
    Button btnEkle, btnGuncelle, btnSil;
    EditText Madi, MKSayisi;
    ListView Bilgiler;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Listview array
    private List<MMasa> list_masalar= new ArrayList<>();

    private  MMasa secilimasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_masa);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        btnEkle = (Button) findViewById(R.id.YM_btn_ekle);
        btnGuncelle = (Button) findViewById(R.id.YM_btn_guncelle);
        btnSil = (Button) findViewById(R.id.YM_btn_sil);

        Madi = (EditText) findViewById(R.id.YM_tbx_adi);
        MKSayisi = (EditText) findViewById(R.id.YM_tbx_sifre);

        Bilgiler = (ListView) findViewById(R.id.YM_lv_bilgiler);

        //Listview satıra basılınca seçme
        Bilgiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MMasa smasa = (MMasa) adapterView.getItemAtPosition(i);
                secilimasa = smasa;

                Madi.setText(smasa.getAd());
                MKSayisi.setText(smasa.getKisi_sayisi());

                for (int j = 0; j < adapterView.getChildCount(); j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                view.setBackgroundColor(Color.GRAY);
            }
        });

        //Veri tabanı bilgi getirme
        Ref.child("Masalar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list_masalar.size() > 0)
                    list_masalar.clear();
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MMasa masalar = veriler.getValue(MMasa.class);
                    list_masalar.add(masalar);
                }

                Y_M_listviewadaptor adaptor= new Y_M_listviewadaptor(YMasa.this, list_masalar);
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
                if(Madi.getText().toString().length()==0 || MKSayisi.getText().toString().length()==0)
                    Toast.makeText(YMasa.this,"Lütfen tüm alanları doldurunuz", Toast.LENGTH_LONG).show();
                else {
                    MMasa masa = new MMasa(UUID.randomUUID().toString(), Madi.getText().toString(), MKSayisi.getText().toString(), "Yok");

                    Ref.child("Masalar").child(masa.getMid()).setValue(masa);

                    IslemSonrasiTemizle();
                }
            }
        });

        //guncelle
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Madi.getText().toString().length()==0 || MKSayisi.getText().toString().length()==0 || secilimasa==null)
                    Toast.makeText(YMasa.this,"Lütfen tüm alanları doldurunuz veya bir masa seçiniz", Toast.LENGTH_LONG).show();
                else {
                    MMasa masa = new MMasa(secilimasa.getMid(), Madi.getText().toString(), MKSayisi.getText().toString(), "Yok");

                    Ref.child("Masalar").child(masa.getMid()).child("ad").setValue(masa.getAd());
                    Ref.child("Masalar").child(masa.getMid()).child("kisi_sayisi").setValue(masa.getKisi_sayisi());
                    secilimasa=null;
                    IslemSonrasiTemizle();
                }
            }
        });

        //sil
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(secilimasa==null)
                    Toast.makeText(YMasa.this,"Lütfen bir masa seçiniz", Toast.LENGTH_LONG).show();
                else {
                    Ref.child("Masalar").child(secilimasa.getMid()).removeValue();
                    IslemSonrasiTemizle();
                    secilimasa=null;
                }
            }
        });
    }
    public void IslemSonrasiTemizle(){
        Madi.setText("");
        MKSayisi.setText("");
    }
}