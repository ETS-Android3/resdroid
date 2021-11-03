package com.example.restoranyonetici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class YSiparisler extends AppCompatActivity {
    ListView Siparisler;

    //FireBase
    FirebaseDatabase Db;
    DatabaseReference Ref;

    private List<MSiparis> list_siparisler= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_siparisler);

        //Firebase
        //FirebaseApp.initializeApp(this);
        Db = FirebaseDatabase.getInstance();
        Ref = Db.getReference();

        Siparisler=(ListView)findViewById(R.id.YS_lv_siparisler);

        //Veri tabanı bilgi getirme
        Ref.child("Siparisler").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list_siparisler.size() > 0)
                    list_siparisler.clear();
                for (DataSnapshot siparisler : snapshot.getChildren()) {
                    MSiparis siparis = siparisler.getValue(MSiparis.class);
                    if(siparis.getAktifmi().equals("Eski"))
                        list_siparisler.add(siparis);
                }

                Y_S_listviewadaptor adaptor= new Y_S_listviewadaptor(YSiparisler.this, list_siparisler);
                Siparisler.setAdapter(adaptor);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}