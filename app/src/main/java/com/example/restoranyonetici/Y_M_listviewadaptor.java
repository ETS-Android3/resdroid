package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Y_M_listviewadaptor extends BaseAdapter {
    Y_M_listviewadaptor(Activity activity, List<MMasa> masalistesi ){
        this.activity=activity;
        this.masalistesi=masalistesi;
    }

    Activity activity;
    List<MMasa> masalistesi;
    LayoutInflater Li;

    @Override
    public int getCount() {
        return masalistesi.size();
    }

    @Override
    public Object getItem(int i) {
        return masalistesi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Li=(LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ozel_listview=Li.inflate(R.layout.y_m_ozel_listview, null);

        TextView ad=ozel_listview.findViewById(R.id.textView1);
        TextView kisissayisis=ozel_listview.findViewById(R.id.textView2);

        ad.setText(masalistesi.get(i).getAd());
        kisissayisis.append(masalistesi.get(i).getKisi_sayisi());

        return ozel_listview;
    }
}
