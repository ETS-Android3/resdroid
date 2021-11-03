package com.example.restoranyonetici;

public class MMKategori {
    String kid, ad;

    MMKategori(){

    }

    public MMKategori(String kid, String ad) {
        this.kid = kid;
        this.ad = ad;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
}
