package com.example.restoranyonetici;

public class MSiparis {
    String id, tarih, masa_adi, urun_adlari, toplam_fiyat, aktifmi;

    MSiparis(){

    }

    public MSiparis(String id, String tarih, String masa_adi, String urun_adlari, String toplam_fiyat, String aktifmi) {
        this.id = id;
        this.tarih = tarih;
        this.masa_adi = masa_adi;
        this.urun_adlari = urun_adlari;
        this.toplam_fiyat = toplam_fiyat;
        this.aktifmi = aktifmi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getMasa_adi() {
        return masa_adi;
    }

    public void setMasa_adi(String masa_adi) {
        this.masa_adi = masa_adi;
    }

    public String getUrun_adlari() {
        return urun_adlari;
    }

    public void setUrun_adlari(String urun_adlari) {
        this.urun_adlari = urun_adlari;
    }

    public String getToplam_fiyat() {
        return toplam_fiyat;
    }

    public void setToplam_fiyat(String toplam_fiyat) {
        this.toplam_fiyat = toplam_fiyat;
    }

    public String getAktifmi() {
        return aktifmi;
    }

    public void setAktifmi(String aktifmi) {
        this.aktifmi = aktifmi;
    }
}
