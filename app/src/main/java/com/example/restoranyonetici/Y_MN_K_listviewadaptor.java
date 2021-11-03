package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Y_MN_K_listviewadaptor extends BaseAdapter {
    Y_MN_K_listviewadaptor(Activity activity, List<MMKategori> kategorilistesi ){
        this.activity=activity;
        this.kategorilistesi=kategorilistesi;
    }

    Activity activity;
    List<MMKategori> kategorilistesi;
    LayoutInflater Li;

    @Override
    public int getCount() {
        return kategorilistesi.size();
    }

    @Override
    public Object getItem(int i) {
        return kategorilistesi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Li=(LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ozel_listview=Li.inflate(R.layout.y_mn_k_ozel_listview, null);

        TextView ad=ozel_listview.findViewById(R.id.textView1);

        ad.setText(kategorilistesi.get(i).getAd());

        return ozel_listview;
    }
}
