package com.example.restoranyonetici;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Y_Y_listviewadaptor extends BaseAdapter {
    Y_Y_listviewadaptor(Activity activity, List<MYonetici> yoneticiclistesi ){
        this.activity=activity;
        this.yoneticiclistesi=yoneticiclistesi;
    }

    Activity activity;
    List<MYonetici> yoneticiclistesi;
    LayoutInflater Li;

    @Override
    public int getCount() {
        return yoneticiclistesi.size();
    }

    @Override
    public Object getItem(int i) {
        return yoneticiclistesi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Li=(LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ozel_listview=Li.inflate(R.layout.y_y_ozel_listview, null);

        TextView ad=ozel_listview.findViewById(R.id.textView1);
        TextView sifre=ozel_listview.findViewById(R.id.textView2);

        ad.setText(yoneticiclistesi.get(i).getAd());
        sifre.setText(yoneticiclistesi.get(i).getSifre());

        return ozel_listview;
    }
}
