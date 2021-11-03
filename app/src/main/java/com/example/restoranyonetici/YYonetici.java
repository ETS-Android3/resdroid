package com.example.restoranyonetici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class YYonetici extends AppCompatActivity {
    //Widgetler
    Button btnEkle, btnGuncelle, btnSil;
    EditText Yadi, Ysifre;
    ListView Bilgiler;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Listview array
    private List<MYonetici> list_yoneticiler= new ArrayList<>();

    private  MYonetici seciliYonetici;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_yonetici);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        btnEkle = (Button) findViewById(R.id.YY_btn_ekle);
        btnGuncelle = (Button) findViewById(R.id.YY_btn_guncelle);
        btnSil = (Button) findViewById(R.id.YY_btn_sil);

        Yadi = (EditText) findViewById(R.id.YY_tbx_yadi);
        Ysifre = (EditText) findViewById(R.id.YY_tbx_ysifre);

        Bilgiler = (ListView) findViewById(R.id.YY_lv_bilgiler);

        //Listview satıra basılınca seçme
        Bilgiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MYonetici syonetici = (MYonetici) adapterView.getItemAtPosition(i);
                seciliYonetici = syonetici;

                Yadi.setText(syonetici.getAd());
                Ysifre.setText(syonetici.getSifre());

                for (int j = 0; j < adapterView.getChildCount(); j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                view.setBackgroundColor(Color.GRAY);
            }
        });

        //Veri tabanı bilgi getirme
        Ref.child("Yoneticiler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list_yoneticiler.size() > 0)
                    list_yoneticiler.clear();
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MYonetici garsonlar = veriler.getValue(MYonetici.class);
                    list_yoneticiler.add(garsonlar);
                }

                Y_Y_listviewadaptor adaptor= new Y_Y_listviewadaptor(YYonetici.this, list_yoneticiler);
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
                if(Yadi.getText().toString().length()==0 || Ysifre.getText().toString().length()<8) {
                    if (Ysifre.getText().toString().length() < 8)
                        Ysifre.setError("En az 8 hanli bir Şifre giriniz");

                    Toast.makeText(YYonetici.this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_LONG).show();
                }
                else {

                    MYonetici yonetici = new MYonetici(UUID.randomUUID().toString(), Yadi.getText().toString(), Ysifre.getText().toString(), "Normal");

                    Ref.child("Yoneticiler").child(yonetici.getUid()).setValue(yonetici);

                    IslemSonrasiTemizle();
                }
            }
        });

        //guncelle
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Yadi.getText().toString().length()==0 || Ysifre.getText().toString().length()<8 || seciliYonetici==null){
                    if (Ysifre.getText().toString().length() < 8)
                        Ysifre.setError("En az 8 hanli bir Şifre giriniz");

                    Toast.makeText(YYonetici.this,"Lütfen tüm alanları doldurunuz veya bir yönetici seçiniz", Toast.LENGTH_LONG).show();
                }
                else {
                    MYonetici yonetici = new MYonetici(seciliYonetici.getUid(), Yadi.getText().toString(), Ysifre.getText().toString(), "Normal");

                    Ref.child("Yoneticiler").child(yonetici.getUid()).child("ad").setValue(yonetici.getAd());
                    Ref.child("Yoneticiler").child(yonetici.getUid()).child("sifre").setValue(yonetici.getSifre());
                    seciliYonetici=null;
                    IslemSonrasiTemizle();
                }
            }
        });

        //sil
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seciliYonetici==null)
                    Toast.makeText(YYonetici.this,"Lütfen bir yönetici seçiniz", Toast.LENGTH_LONG).show();
                else {
                    Ref.child("Yoneticiler").child(seciliYonetici.getUid()).removeValue();
                    IslemSonrasiTemizle();
                    seciliYonetici=null;
                }
            }
        });
    }
    public void IslemSonrasiTemizle(){
        Yadi.setText("");
        Ysifre.setText("");
    }
}