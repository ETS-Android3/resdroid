package com.example.restoranyonetici;

public class MMYemek {
    String yid, ad, kategori_ad, fiyat;

    MMYemek(){

    }

    public MMYemek(String yid, String ad, String kategori_ad, String fiyat) {
        this.yid = yid;
        this.ad = ad;
        this.kategori_ad = kategori_ad;
        this.fiyat = fiyat;
    }

    public String getYid() {
        return yid;
    }

    public void setYid(String yid) {
        this.yid = yid;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getKategori_ad() {
        return kategori_ad;
    }

    public void setKategori_ad(String kategori_ad) {
        this.kategori_ad = kategori_ad;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }
}
