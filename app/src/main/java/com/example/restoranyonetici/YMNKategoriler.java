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

public class YMNKategoriler extends AppCompatActivity {
    //Widgetler
    Button btnEkle, btnGuncelle, btnSil;
    EditText Kadi;
    ListView Bilgiler;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Listview array
    private List<MMKategori> list_kategoriler= new ArrayList<>();

    private  MMKategori secilikategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_m_n_kategoriler);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        btnEkle = (Button) findViewById(R.id.YMNK_btn_ekle);
        btnGuncelle = (Button) findViewById(R.id.YMNK_btn_guncelle);
        btnSil = (Button) findViewById(R.id.YMNK_btn_sil);

        Kadi = (EditText) findViewById(R.id.YMNK_tbx_adi);

        Bilgiler = (ListView) findViewById(R.id.YMNK_lv_bilgiler);

        //Listview satıra basılınca seçme
        Bilgiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MMKategori skategori = (MMKategori) adapterView.getItemAtPosition(i);
                secilikategori = skategori;

                Kadi.setText(skategori.getAd());

                for (int j = 0; j < adapterView.getChildCount(); j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                view.setBackgroundColor(Color.GRAY);
            }
        });

        //Veri tabanı bilgi getirme
        Ref.child("Kategoriler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list_kategoriler.size() > 0)
                    list_kategoriler.clear();
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MMKategori kategoriler = veriler.getValue(MMKategori.class);
                    list_kategoriler.add(kategoriler);
                }

                Y_MN_K_listviewadaptor adaptor= new Y_MN_K_listviewadaptor(YMNKategoriler.this, list_kategoriler);
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
                if(Kadi.getText().toString().length()==0)
                    Toast.makeText(YMNKategoriler.this,"Lütfen bir kategori adı giriniz", Toast.LENGTH_LONG).show();
                else{
                    MMKategori kategori = new MMKategori(UUID.randomUUID().toString(), Kadi.getText().toString());

                    Ref.child("Kategoriler").child(kategori.getKid()).setValue(kategori);

                    IslemSonrasiTemizle();
                }
            }
        });

        //guncelle
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Kadi.getText().toString().length()==0 || secilikategori==null)
                    Toast.makeText(YMNKategoriler.this,"Lütfen bir kategori seçiniz veya kategori adı giriniz", Toast.LENGTH_LONG).show();
                else {
                    MMKategori kategori = new MMKategori(secilikategori.getKid(), Kadi.getText().toString());

                    Ref.child("Kategoriler").child(kategori.getKid()).child("ad").setValue(kategori.getAd());
                    secilikategori=null;
                    IslemSonrasiTemizle();
                }
            }
        });

        //sil
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(secilikategori==null)
                    Toast.makeText(YMNKategoriler.this,"Lütfen bir kategori seçiniz", Toast.LENGTH_LONG).show();
                else {
                    Ref.child("Kategoriler").child(secilikategori.getKid()).removeValue();
                    IslemSonrasiTemizle();

                    secilikategori=null;
                }
            }
        });
    }
    public void IslemSonrasiTemizle(){
        Kadi.setText("");
    }
}