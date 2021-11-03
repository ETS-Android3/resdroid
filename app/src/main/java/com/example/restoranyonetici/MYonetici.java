package com.example.restoranyonetici;

public class MYonetici {
    private String uid, ad, sifre, supery;

    MYonetici(){

    }

    public MYonetici(String uid, String ad, String sifre, String supery) {
        this.uid = uid;
        this.ad = ad;
        this.sifre = sifre;
        this.supery = supery;
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

    public String getSupery() {
        return supery;
    }

    public void setSupery(String supery) {
        this.supery = supery;
    }
}
