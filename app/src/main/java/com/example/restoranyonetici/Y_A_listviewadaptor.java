package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Y_A_listviewadaptor extends BaseAdapter {

    Y_A_listviewadaptor(Activity activity, List<MSiparis> siparislistesi ){
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
        View ozel_listview=Li.inflate(R.layout.y_a_asiparis_ozel_listview, null);

        TextView masa=ozel_listview.findViewById(R.id.textView1);
        TextView urunler=ozel_listview.findViewById(R.id.textView5);
        TextView toplamfiyat=ozel_listview.findViewById(R.id.textView8);

        masa.setText(siparislistesi.get(i).getMasa_adi());
        toplamfiyat.setText(siparislistesi.get(i).getToplam_fiyat()+"TL");

        return ozel_listview;
    }
}
