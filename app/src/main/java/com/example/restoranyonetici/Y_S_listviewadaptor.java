package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Y_S_listviewadaptor extends BaseAdapter {
    Y_S_listviewadaptor(Activity activity, List<MSiparis> siparislistesi ){
        this.activity=activity;
        this.siparislistesi=siparislistesi;
    }

    Activity activity;
    List<MSiparis> siparislistesi;
    LayoutInflater Li;

    @Override
    public int getCount() {
        return siparislistesi.size();
    }

    @Override
    public Object getItem(int i) {
        return siparislistesi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Li=(LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ozel_listview=Li.inflate(R.layout.y_s_ozel_listview, null);

        TextView Tarih=ozel_listview.findViewById(R.id.textView1);
        TextView Kazanc=ozel_listview.findViewById(R.id.textView8);
        TextView Urunler=ozel_listview.findViewById(R.id.textView5);

        Tarih.setText(siparislistesi.get(i).getTarih());
        Kazanc.setText(siparislistesi.get(i).getToplam_fiyat()+"TL");
        Urunler.setText(siparislistesi.get(i).getUrun_adlari());

        return ozel_listview;
    }
}
