package com.example.restoranyonetici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KAnasayfa extends AppCompatActivity {
    ListView Masalistesi;
    TextView KAdi;
    ImageButton Cikis;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Listview array
    private List<MMasa> list_masalar= new ArrayList<>();

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_anasayfa);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        //Giriş tutma
        SharedPreferences Sp=getSharedPreferences("shared_pref_gs", MODE_PRIVATE);

        Masalistesi = (ListView) findViewById(R.id.KA_lv_bilgiler);
        KAdi=(TextView)findViewById(R.id.KA_lbl_hosgeldiniz);
        Cikis=(ImageButton)findViewById(R.id.KA_imgbtn_cikis);

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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //İlgili masa için kapanmamış kayıt olup olmadığı kontrol ediliyor
        Ref.child("Siparisler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MSiparis siparis = veriler.getValue(MSiparis.class);
                    if(siparis.getAktifmi().equals("Aktif")){
                        for (MMasa masalar : list_masalar) {
                            if(masalar.getAd().equals(siparis.getMasa_adi()))
                                masalar.setAktif_siparis("Var");
                        }
                    }
                }

                K_A_listviewadaptor adaptor= new K_A_listviewadaptor(KAnasayfa.this, list_masalar);
                Masalistesi.setAdapter(adaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        KAdi.append(Sp.getString("garsonadi", ""));

        //Listview satıra basılınca seçme
        Masalistesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MMasa masa_siparis = (MMasa) adapterView.getItemAtPosition(i);

                Intent MyIntent = new Intent(getApplicationContext(), KGenelSiparisEkrani.class);
                MyIntent.putExtra("masa_id", masa_siparis.getMid());
                MyIntent.putExtra("masa_adi", masa_siparis.getAd());
                MyIntent.putExtra("aktif_siparis", masa_siparis.getAktif_siparis());

                startActivity(MyIntent);
            }
        });

        Cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KAnasayfa.this);
                builder.setTitle ("Uyarı");
                builder.setMessage ("Çıkış yapmak istediğnize eminmisiniz?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor= Sp.edit();
                        editor.putString("garsonadi", "");
                        editor.commit();
                        Intent Garsongiris=new Intent(KAnasayfa.this, KGiris.class);
                        startActivity(Garsongiris);
                    }
                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });
    }
}