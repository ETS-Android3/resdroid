package com.example.restoranyonetici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class YAnasayfa extends AppCompatActivity {

    ImageButton Cikis, SideBarAc;
    ListView ASBilgiler;
    TextView YA_gs, YA_ms, YA_ks, YA_ys, YA_ss, YA_yns, YAdi;

    int gs=0, ms=0, ks=0, ys=0, ss=0, yns=0;
    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Sidemenu
    private DrawerLayout DLayout;
    private NavigationView NView;

    private List<MSiparis> list_aktif_sipris= new ArrayList<>();

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_anasayfa);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        //Giriş tutma
        SharedPreferences Sp=getSharedPreferences("shared_pref_ys", MODE_PRIVATE);

        //SideMenu
        DLayout=findViewById(R.id.Y_A_DL);
        NView=findViewById(R.id.Y_A_NW);
        NView.setItemIconTintList(null);

        Cikis=(ImageButton) findViewById(R.id.YA_btn_cikis);
        SideBarAc=(ImageButton) findViewById(R.id.YA_btn_side);

        YA_gs=(TextView)findViewById(R.id.YA_lbl_gsayisis);
        YA_ms=(TextView)findViewById(R.id.YA_lbl_msayisi);
        YA_ks=(TextView)findViewById(R.id.YA_lbl_ksayisi);
        YA_ys=(TextView)findViewById(R.id.YA_lbl_ysayisi);
        YA_ss=(TextView)findViewById(R.id.YA_lbl_sipariss);
        YA_yns=(TextView)findViewById(R.id.YA_lbl_yosayisi);


        YAdi=(TextView)findViewById(R.id.YA_lbl_ad);

        ASBilgiler=(ListView)findViewById(R.id.YA_lv_aktifs);

        if(Sp.getString("yoneticisuper", "").equals("Super"))
            YAdi.append(Sp.getString("yoneticiadi", "")+" -Süper");
        else
            YAdi.append(Sp.getString("yoneticiadi", ""));

        //Aktif sipariş getirme
        Ref.child("Siparisler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list_aktif_sipris.size() > 0)
                    list_aktif_sipris.clear();
                for (DataSnapshot siparisler : snapshot.getChildren()) {
                    MSiparis siparis = siparisler.getValue(MSiparis.class);
                    if(siparis.getAktifmi().equals("Aktif"))
                        list_aktif_sipris.add(siparis);
                }

                Y_A_listviewadaptor adaptor= new Y_A_listviewadaptor(YAnasayfa.this, list_aktif_sipris);
                ASBilgiler.setAdapter(adaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Listview satıra basılınca seçme
        ASBilgiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MSiparis ssiparis = (MSiparis) adapterView.getItemAtPosition(i);

                System.out.println(("Siparis tarihi: " + ssiparis.getTarih()));

                final Dialog d = new Dialog(YAnasayfa.this);
                d.setTitle("Sipariş detay");
                d.setContentView(R.layout.y_asb_dialog);
                TextView masa_adi = (TextView) d.findViewById(R.id.y_asb_lbl_madi);
                TextView tarih = (TextView) d.findViewById(R.id.y_asb_lbl_tarih);
                TextView icerik = (TextView) d.findViewById(R.id.y_asb_lbl_sdetay);
                TextView fiyat = (TextView) d.findViewById(R.id.y_asb_lbl_ucret);

                masa_adi.setText(ssiparis.getMasa_adi());
                tarih.setText(ssiparis.getTarih());

                String urunler= ssiparis.getUrun_adlari().replace(" ", "\n");
                icerik.setText(urunler);

                fiyat.setText("TOPLAM: "+ ssiparis.getToplam_fiyat()+"TL");
                d.show();
            }
        });


        //Veri tabanı bilgi getirme
        Ref.child("Garsonlar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gs=0;
                for (DataSnapshot veriler : snapshot.getChildren()) {
                   gs++;
                }
                YA_gs.setText("Garsonlar: "+String.valueOf(gs));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Masa sayısıs
        Ref.child("Masalar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ms=0;
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    ms++;
                }
                YA_ms.setText("Masalar: "+String.valueOf(ms));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Kategori sayısıs
        Ref.child("Kategoriler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ks=0;
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    ks++;
                }
                YA_ks.setText("Kategoriler: "+String.valueOf(ks));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Yemek sayısıs
        Ref.child("Yemekler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ys=0;
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    ys++;
                }
                YA_ys.setText("Ürünler: "+String.valueOf(ys));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //kapanmış sipariş getirme
        Ref.child("Siparisler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               ss=0;
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MSiparis siparis = veriler.getValue(MSiparis.class);
                    if(siparis.getAktifmi().equals("Eski"))
                        ss++;
                }

                YA_ss.setText("Siparişler: "+ String.valueOf(ss));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //yöneticic sayısıs
        Ref.child("Yoneticiler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                yns=0;
                for (DataSnapshot siparisler : snapshot.getChildren()) {
                    yns++;
                }

                YA_yns.setText("Yöneticiler: "+ String.valueOf(yns));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        NView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.sb_menu:
                        Intent menu = new Intent(getApplicationContext(), YMenu.class);
                        startActivity(menu);
                        break;
                    case R.id.sb_garson:
                        Intent menu1 = new Intent(getApplicationContext(), YGarson.class);
                        startActivity(menu1);
                        break;
                    case R.id.sb_masa:
                        Intent menu2 = new Intent(getApplicationContext(), YMasa.class);
                        startActivity(menu2);
                        break;
                    case R.id.sb_siparis:
                        Intent menu3 = new Intent(getApplicationContext(), YSiparisler.class);
                        startActivity(menu3);
                        break;
                    case R.id.sb_yonetici:
                        if(!Sp.getString("yoneticisuper", "").equals("Super"))
                            Toast.makeText(YAnasayfa.this,"Normal bir yönetici olduğunuz için yönetici işlemleri yapamazsınız", Toast.LENGTH_LONG).show();
                        else {
                            Intent yonetici = new Intent(getApplicationContext(), YYonetici.class);
                            startActivity(yonetici);
                        }
                        break;
                }
                return false;
            }
        });

        SideBarAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLayout.openDrawer(GravityCompat.START);
            }
        });

        Cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(YAnasayfa.this);
                builder.setTitle ("Uyarı");
                builder.setMessage ("Çıkış yapmak istediğnize eminmisiniz?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor= Sp.edit();
                        editor.putString("yoneticiadi", "");
                        editor.putString("yoneticisuper", "");
                        editor.commit();
                        Intent Garsongiris=new Intent(YAnasayfa.this, YGiris.class);
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