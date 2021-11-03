package com.example.restoranyonetici;

public class MKullanici {
    private String uid, ad, sifre;

    MKullanici(){

    }

    public MKullanici(String uid, String ad, String sifre) {
        this.uid = uid;
        this.ad = ad;
        this.sifre = sifre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}
