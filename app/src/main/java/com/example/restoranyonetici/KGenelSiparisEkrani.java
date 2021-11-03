package com.example.restoranyonetici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class KGenelSiparisEkrani extends AppCompatActivity {
    TextView MasaAdi, ToplamFiyat, AdetGoster;
    Spinner Kategoriler, Yemekler;
    ListView Bilgiler;
    Button Ekle, Sil, Onayla, Kapat, Adet;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    //Listview array
    private List<MGSELv> list_siparisler= new ArrayList<>();

    private  int seciliurun_sira;

    private List<String> SeciliUrunFiyat= new ArrayList<>();

    private int ToplamUcret=0;

    private List<MSiparis> siparis_kontrol= new ArrayList<>();

    private int UrunAdeti=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_genel_siparis_ekrani);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();
        //Siparişleri tutmak için


        MasaAdi=(TextView)findViewById(R.id.K_GSE_lbl_masaadi);
        ToplamFiyat=(TextView)findViewById(R.id.K_GSE_lbl_toplamfiyat);
        AdetGoster=(TextView)findViewById(R.id.K_GSE_tbx_adet);

        Kategoriler=(Spinner)findViewById(R.id.K_GSE_sp_kategoriler);
        Yemekler=(Spinner)findViewById(R.id.K_GSE_sp_yemekler);

        Adet=(Button) findViewById(R.id.K_GSE_btn_adet);

        Bilgiler=(ListView) findViewById(R.id.K_GSE_lv_siparisler);

        Ekle=(Button) findViewById(R.id.K_GSE_btn_urunekle);
        Sil=(Button) findViewById(R.id.K_GSE_btn_urunsil);
        Onayla=(Button) findViewById(R.id.K_GSE_btn_onayla);
        Kapat=(Button) findViewById(R.id.K_GSE_btn_kapat);

        //Aktfi siparişleri getirmek için
        SharedPreferences Sp=getSharedPreferences(getIntent().getExtras().getString("masa_id"), MODE_PRIVATE);
        Gson gson= new Gson();
        String json=Sp.getString("siparisler", null);
        if(json!=null){
            Type type= new TypeToken<ArrayList<MGSELv>>(){}.getType();
            list_siparisler= gson.fromJson(json, type);
            K_GSE_Lv_listviewadaptor adaptor= new K_GSE_Lv_listviewadaptor(KGenelSiparisEkrani.this, list_siparisler);

            for(MGSELv urun: list_siparisler){
                ToplamUcret+=Integer.valueOf(urun.getFiyat())*Integer.valueOf(urun.getAdet());
            }
            ToplamFiyat.setText("TOPLAM: "+String.valueOf(ToplamUcret)+"TL");

            Bilgiler.setAdapter(adaptor);
        }


        MasaAdi.append(getIntent().getExtras().getString("masa_adi"));

        AdetGoster.setText("Adet: "+ String.valueOf(UrunAdeti));

        //Spinner için kategori bilgi getirme ve ekleme
        ArrayList<String> list_sp_kategoriler = new ArrayList<String>();

        Ref.child("Kategoriler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_sp_kategoriler.clear();
                SeciliUrunFiyat.clear();
                list_sp_kategoriler.add("Kategoriler");

                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MMKategori k_list = veriler.getValue(MMKategori.class);
                    list_sp_kategoriler.add(k_list.getAd());
                }
                ArrayAdapter<String> adaptor = new ArrayAdapter<String>(KGenelSiparisEkrani.this, android.R.layout.simple_spinner_item, list_sp_kategoriler);
                adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Kategoriler.setAdapter(adaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Kategoriye göre urngetirme
        Kategoriler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Spinner için urun bilgi getirme ve ekleme
                ArrayList<String> list_sp_yemekler = new ArrayList<String>();

                Ref.child("Yemekler").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list_sp_yemekler.clear();
                        SeciliUrunFiyat.clear();
                        list_sp_yemekler.add("Ürünler");

                        for (DataSnapshot veriler : snapshot.getChildren()) {
                            MMYemek y_list = veriler.getValue(MMYemek.class);
                            if(y_list.getKategori_ad().equals(Kategoriler.getSelectedItem().toString())){
                                list_sp_yemekler.add(y_list.getAd());
                                SeciliUrunFiyat.add(y_list.getFiyat());
                            }
                        }
                        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(KGenelSiparisEkrani.this, android.R.layout.simple_spinner_item, list_sp_yemekler);
                        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Yemekler.setAdapter(adaptor);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Listview satıra basılınca seçme
        Bilgiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MGSELv surun = (MGSELv) adapterView.getItemAtPosition(i);
                seciliurun_sira = i;

                for (int j = 0; j < adapterView.getChildCount(); j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                view.setBackgroundColor(Color.GRAY);
            }
        });

        Adet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdetAc();
            }
        });



        Ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Kategoriler.getSelectedItem().toString().equals("Kategoriler") && !Yemekler.getSelectedItem().toString().equals("Ürünler")) {
                    String urun_adi = Yemekler.getSelectedItem().toString();
                    String urun_adet = String.valueOf(UrunAdeti);
                    String Urun_fiyat = SeciliUrunFiyat.get(Yemekler.getSelectedItemPosition() - 1);
                    ToplamUcret += Integer.valueOf(Urun_fiyat) * Integer.valueOf(urun_adet);

                    MGSELv eklenen_siparis = new MGSELv(urun_adi, urun_adet, Urun_fiyat);
                    list_siparisler.add(eklenen_siparis);
                    K_GSE_Lv_listviewadaptor adaptor = new K_GSE_Lv_listviewadaptor(KGenelSiparisEkrani.this, list_siparisler);
                    Bilgiler.setAdapter(adaptor);
                    SPGuncelle(list_siparisler);

                    ToplamFiyat.setText("TOPLAM: " + String.valueOf(ToplamUcret) + "TL");

                    UrunAdeti=1;
                    AdetGoster.setText("Adet: "+String.valueOf(UrunAdeti));
                }
                else
                    Toast.makeText(KGenelSiparisEkrani.this, "Lütfen boş alanları seçin", Toast.LENGTH_SHORT).show();
            }
        });

        Sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list_siparisler.size()>0) {
                    if(seciliurun_sira!=-1) {
                        ToplamUcret -= Integer.valueOf(list_siparisler.get(seciliurun_sira).getFiyat()) * Integer.valueOf(list_siparisler.get(seciliurun_sira).getAdet());
                        list_siparisler.remove(seciliurun_sira);
                        seciliurun_sira=-1;

                        K_GSE_Lv_listviewadaptor adaptor = new K_GSE_Lv_listviewadaptor(KGenelSiparisEkrani.this, list_siparisler);
                        Bilgiler.setAdapter(adaptor);
                        SPGuncelle(list_siparisler);

                        ToplamFiyat.setText("TOPLAM: " + String.valueOf(ToplamUcret) + "TL");
                    }
                    else
                        Toast.makeText(KGenelSiparisEkrani.this, "Lütfen bir ürün seçiniz", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(KGenelSiparisEkrani.this, "Listede ürün yok", Toast.LENGTH_SHORT).show();
           }
        });

        //siparişleri kontrol için alıyor
        Ref.child("Siparisler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot veriler : snapshot.getChildren()) {
                    MSiparis siparis = veriler.getValue(MSiparis.class);
                    siparis_kontrol.add(siparis);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list_siparisler.size()>0) {
                    SimpleDateFormat ozel_format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date tarih = new Date();
                    String Tarih = ozel_format.format(tarih);
                    String Urunler = "";

                    for (MGSELv siparis : list_siparisler) {
                        Urunler += siparis.getAdet() + "Adet-" + siparis.getAd()+ " ";
                    }

                    int sayac = 0;
                    if (siparis_kontrol.size() == 0) {
                        MSiparis siparis = new MSiparis(UUID.randomUUID().toString(), Tarih, getIntent().getExtras().getString("masa_adi"), Urunler, String.valueOf(ToplamUcret), "Aktif");
                        Ref.child("Siparisler").child(siparis.getId()).setValue(siparis);

                        Toast.makeText(KGenelSiparisEkrani.this, "Sipariş alındı", Toast.LENGTH_SHORT).show();
                    } else {
                        for (MSiparis siparisk : siparis_kontrol) {
                            if (siparisk.getMasa_adi().equals(getIntent().getExtras().getString("masa_adi")) && siparisk.getAktifmi().equals("Aktif")) {

                                MSiparis siparis = new MSiparis(siparisk.getId(), Tarih, siparisk.getMasa_adi(), Urunler, String.valueOf(ToplamUcret), "Aktif");

                                Ref.child("Siparisler").child(siparis.getId()).child("tarih").setValue(siparis.getTarih());
                                Ref.child("Siparisler").child(siparis.getId()).child("urun_adlari").setValue(siparis.getUrun_adlari());
                                Ref.child("Siparisler").child(siparis.getId()).child("toplam_fiyat").setValue(siparis.getToplam_fiyat());

                                Toast.makeText(KGenelSiparisEkrani.this, "Sipariş güncellendi", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            sayac++;
                            if (siparis_kontrol.size() == sayac) {
                                MSiparis siparis = new MSiparis(UUID.randomUUID().toString(), Tarih, getIntent().getExtras().getString("masa_adi"), Urunler, String.valueOf(ToplamUcret), "Aktif");
                                Ref.child("Siparisler").child(siparis.getId()).setValue(siparis);

                                Toast.makeText(KGenelSiparisEkrani.this, "Sipariş alındı", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    Intent Anasayfagit = new Intent(KGenelSiparisEkrani.this, KAnasayfa.class);
                    startActivity(Anasayfagit);
                }
                else
                    Toast.makeText(KGenelSiparisEkrani.this, "Lütfen ürün seçiniz", Toast.LENGTH_SHORT).show();
            }
        });

        Kapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sayac=0;
                if (siparis_kontrol.size() == 0)
                    Toast.makeText(KGenelSiparisEkrani.this, "Masaya ait herhangibir aktif kayıt yok", Toast.LENGTH_SHORT).show();

                else {
                    for (MSiparis siparisk : siparis_kontrol) {
                        if (siparisk.getMasa_adi().equals(getIntent().getExtras().getString("masa_adi")) && siparisk.getAktifmi().equals("Aktif")) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(KGenelSiparisEkrani.this);
                            builder.setTitle ("Uyarı");
                            builder.setMessage ("Siparişi kapatmak istediğinize eminmisiniz?");
                            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MSiparis siparis = new MSiparis(siparisk.getId(), siparisk.getTarih(), siparisk.getMasa_adi(), siparisk.getUrun_adlari(), siparisk.getToplam_fiyat(), "Eski");
                                    Ref.child("Siparisler").child(siparis.getId()).child("aktifmi").setValue(siparis.getAktifmi());

                                    list_siparisler.clear();
                                    Sp.edit().clear().commit();

                                    Toast.makeText(KGenelSiparisEkrani.this, "Sipariş kapandı", Toast.LENGTH_SHORT).show();

                                    Intent Anasayfagit = new Intent(KGenelSiparisEkrani.this, KAnasayfa.class);
                                    startActivity(Anasayfagit);
                                }
                            }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();

                            break;
                        }
                        sayac++;
                        if (sayac == siparis_kontrol.size())
                            Toast.makeText(KGenelSiparisEkrani.this, "Masaya ait herhangi bir aktif kayıt yok", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void SPGuncelle(List<MGSELv> liste){
        SharedPreferences Sp=getSharedPreferences(getIntent().getExtras().getString("masa_id"), MODE_PRIVATE);
        SharedPreferences.Editor editor= Sp.edit();
        Gson gson= new Gson();
        String json= gson.toJson(liste);
        editor.putString("siparisler", json);
        editor.apply();
    }

    public void AdetAc()
    {
        final Dialog d = new Dialog(KGenelSiparisEkrani.this);
        d.setTitle("Adet Seç");
        d.setContentView(R.layout.k_gse_adet_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(100); // max value 100
        np.setMinValue(1);   // min value 0
        np.setWrapSelectorWheel(false);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                UrunAdeti=np.getValue();
                AdetGoster.setText("Adet: "+String.valueOf(UrunAdeti));
                d.dismiss();
            }
        });
       d.show();
    }
}