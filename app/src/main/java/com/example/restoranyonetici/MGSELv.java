package com.example.restoranyonetici;

public class MGSELv {
    String ad, adet, fiyat;

    MGSELv(){

    }

    public MGSELv(String ad, String adet, String fiyat) {
        this.ad = ad;
        this.adet = adet;
        this.fiyat = fiyat;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAdet() {
        return adet;
    }

    public void setAdet(String adet) {
        this.adet = adet;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }
}
