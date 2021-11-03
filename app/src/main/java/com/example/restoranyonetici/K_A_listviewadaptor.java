package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class K_A_listviewadaptor extends BaseAdapter {
    K_A_listviewadaptor(Activity activity, List<MMasa> masalar ){
        this.activity=activity;
        this.masalar=masalar;
    }

    Activity activity;
    List<MMasa> masalar;
    LayoutInflater Li;

    @Override
    public int getCount() {
        return masalar.size();
    }

    @Override
    public Object getItem(int i) {
        return masalar.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Li=(LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ozel_listview=Li.inflate(R.layout.k_a_ozel_listview, null);

        TextView masa_adi=ozel_listview.findViewById(R.id.textView1);
        TextView kisi_sayisi=ozel_listview.findViewById(R.id.textView2);
        TextView aktif_siparis_durumu=ozel_listview.findViewById(R.id.textView9);

        masa_adi.setText(masalar.get(i).getAd());
        kisi_sayisi.setText(masalar.get(i).getKisi_sayisi()+" Kişilik");

        if(masalar.get(i).getAktif_siparis().equals("Var"))
            aktif_siparis_durumu.setTextColor(Color.parseColor("#FFF44336"));

        aktif_siparis_durumu.append(masalar.get(i).getAktif_siparis());

        return ozel_listview;
    }
}
