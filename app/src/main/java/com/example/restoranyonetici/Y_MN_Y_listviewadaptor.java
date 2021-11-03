package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Y_MN_Y_listviewadaptor extends BaseAdapter {
    Y_MN_Y_listviewadaptor(Activity activity, List<MMYemek> yemeklistesi ){
        this.activity=activity;
        this.yemeklistesi=yemeklistesi;
    }

    Activity activity;
    List<MMYemek> yemeklistesi;
    LayoutInflater Li;

    @Override
    public int getCount() {
        return yemeklistesi.size();
    }

    @Override
    public Object getItem(int i) {
        return yemeklistesi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Li=(LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ozel_listview=Li.inflate(R.layout.y_mn_y_ozle_listview, null);

        TextView ad=ozel_listview.findViewById(R.id.textView1);
        TextView kategori=ozel_listview.findViewById(R.id.textView2);
        TextView fiyat=ozel_listview.findViewById(R.id.textView7);

        ad.setText(yemeklistesi.get(i).getAd());
        kategori.setText(yemeklistesi.get(i).getKategori_ad());
        fiyat.append(yemeklistesi.get(i).getFiyat());

        return ozel_listview;
    }
}
