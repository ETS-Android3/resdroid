package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class K_GSE_Lv_listviewadaptor extends BaseAdapter {
    K_GSE_Lv_listviewadaptor(Activity activity, List<MGSELv> siparislistesi ){
        this.activity=activity;
        this.siparislistesi=siparislistesi;
    }

    Activity activity;
    List<MGSELv> siparislistesi;
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
        View ozel_listview=Li.inflate(R.layout.k_gse_ozel_listview, null);

        TextView ad=ozel_listview.findViewById(R.id.textView1);
        TextView adeti=ozel_listview.findViewById(R.id.textView5);
        TextView fiyat=ozel_listview.findViewById(R.id.textView8);

        ad.setText(siparislistesi.get(i).getAd());
        adeti.append(siparislistesi.get(i).getAdet()+" Adet");
        fiyat.append(siparislistesi.get(i).getFiyat()+" TL");

        return ozel_listview;
    }
}
