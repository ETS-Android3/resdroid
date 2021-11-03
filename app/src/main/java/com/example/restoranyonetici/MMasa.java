package com.example.restoranyonetici;

public class MMasa {
    private String mid, ad, kisi_sayisi, aktif_siparis;

    MMasa(){

    }

    public MMasa(String mid, String ad, String kisi_sayisi, String asiparis) {
        this.mid = mid;
        this.ad = ad;
        this.kisi_sayisi = kisi_sayisi;
        this.aktif_siparis=asiparis;
    }


    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getKisi_sayisi() {
        return kisi_sayisi;
    }

    public void setKisi_sayisi(String kisi_sayisi) {
        this.kisi_sayisi = kisi_sayisi;
    }

    public String getAktif_siparis() {
        return aktif_siparis;
    }

    public void setAktif_siparis(String aktif_siparis) {
        this.aktif_siparis = aktif_siparis;
    }
}
