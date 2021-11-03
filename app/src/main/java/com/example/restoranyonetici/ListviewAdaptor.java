package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.service.voice.VoiceInteractionSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListviewAdaptor extends BaseAdapter {

    ListviewAdaptor(Activity activity,List<MKullanici> kullaniciclistesi ){
        this.activity=activity;
        this.kullaniciclistesi=kullaniciclistesi;
    }

    Activity activity;
    List<MKullanici> kullaniciclistesi;
    LayoutInflater Li;

    @Override
    public int getCount() {
        return kullaniciclistesi.size();
    }

    @Override
    public Object getItem(int i) {
        return kullaniciclistesi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Li=(LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ozel_listview=Li.inflate(R.layout.ozelview, null);

        TextView ad=ozel_listview.findViewById(R.id.textView1);
        TextView sifre=ozel_listview.findViewById(R.id.textView2);

        ad.setText(kullaniciclistesi.get(i).getAd());
        sifre.setText(kullaniciclistesi.get(i).getSifre());

        return ozel_listview;
    }
}
