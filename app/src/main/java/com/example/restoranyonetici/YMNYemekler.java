package com.example.restoranyonetici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class YMNYemekler extends AppCompatActivity {
    //Widgetler
    Button btnEkle, btnGuncelle, btnSil;
    EditText Yadi, Yfiyat;
    Spinner Ykategori_sp;
    ListView Bilgiler;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Listview array
    private List<MMYemek> list_yemekler= new ArrayList<>();

    private  MMYemek seciliyemek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_m_n_yemekler);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        btnEkle = (Button) findViewById(R.id.YMNY_btn_ekle);
        btnGuncelle = (Button) findViewById(R.id.YMNY_btn_guncelle);
        btnSil = (Button) findViewById(R.id.YMNY_btn_sil);

        Yadi = (EditText) findViewById(R.id.YMNY_tbx_adi);
        Ykategori_sp=(Spinner)findViewById(R.id.YMNY_sp_kategoriler);
        Yfiyat = (EditText) findViewById(R.id.YMNY_tbx_fiyat);

        Bilgiler = (ListView) findViewById(R.id.YMNY_lv_bilgiler);

        //Spinner için kategori bilgi getirme ve ekleme
        ArrayList<String> list_sp_kategoriler = new ArrayList<String>();
        list_sp_kategoriler.add("Lütfen bir kategori seçiniz");

        Ref.child("Kategoriler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MMKategori k_list = veriler.getValue(MMKategori.class);
                    list_sp_kategoriler.add(k_list.getAd());
                }
                ArrayAdapter<String> adaptor = new ArrayAdapter<String>(YMNYemekler.this, android.R.layout.simple_spinner_item, list_sp_kategoriler);
                adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Ykategori_sp.setAdapter(adaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Listview satıra basılınca seçme
        Bilgiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MMYemek syemek = (MMYemek) adapterView.getItemAtPosition(i);
                seciliyemek = syemek;

                Yadi.setText(syemek.getAd());
                Ykategori_sp.setSelection(0);
                Yfiyat.setText(syemek.getFiyat());

                for (int j = 0; j < adapterView.getChildCount(); j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                view.setBackgroundColor(Color.GRAY);
            }
        });

        //Veri tabanı bilgi getirme
        Ref.child("Yemekler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list_yemekler.size() > 0)
                    list_yemekler.clear();
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MMYemek yemekler = veriler.getValue(MMYemek.class);
                    list_yemekler.add(yemekler);
                }

                Y_MN_Y_listviewadaptor adaptor= new Y_MN_Y_listviewadaptor(YMNYemekler.this, list_yemekler);
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
                if(Yadi.getText().toString().length()==0 || Ykategori_sp.getSelectedItem().toString().equals("Lütfen bir kategori seçiniz") || Yfiyat.getText().toString().length()==0)
                    Toast.makeText(YMNYemekler.this,"Lütfen tüm alanları doldurunuz", Toast.LENGTH_LONG).show();
                else {

                    MMYemek yemek = new MMYemek(UUID.randomUUID().toString(), Yadi.getText().toString(), Ykategori_sp.getSelectedItem().toString(), Yfiyat.getText().toString());

                    Ref.child("Yemekler").child(yemek.getYid()).setValue(yemek);

                    IslemSonrasiTemizle();
                }
            }
        });

        //guncelle
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Yadi.getText().toString().length()==0 || Ykategori_sp.getSelectedItem().toString().equals("Lütfen bir kategori seçiniz") || Yfiyat.getText().toString().length()==0 || seciliyemek==null)
                    Toast.makeText(YMNYemekler.this,"Lütfen tüm alanları doldurunuz veya bir ürün seçiniz", Toast.LENGTH_LONG).show();
                else {
                    MMYemek yemek = new MMYemek(seciliyemek.getYid(), Yadi.getText().toString(), Ykategori_sp.getSelectedItem().toString(), Yfiyat.getText().toString());

                    Ref.child("Yemekler").child(yemek.getYid()).child("ad").setValue(yemek.getAd());
                    Ref.child("Yemekler").child(yemek.getYid()).child("kategori_ad").setValue(yemek.getKategori_ad());
                    Ref.child("Yemekler").child(yemek.getYid()).child("fiyat").setValue(yemek.getFiyat());
                    seciliyemek=null;
                    IslemSonrasiTemizle();
                }
            }
        });

        //sil
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seciliyemek==null)
                    Toast.makeText(YMNYemekler.this,"Lütfen bir ürün seçiniz", Toast.LENGTH_LONG).show();
                else {
                    Ref.child("Yemekler").child(seciliyemek.getYid()).removeValue();

                    seciliyemek=null;
                    IslemSonrasiTemizle();
                }
            }
        });
    }
    public void IslemSonrasiTemizle(){
        Yadi.setText("");
        Ykategori_sp.setSelection(0);
        Yfiyat.setText("");
    }
}