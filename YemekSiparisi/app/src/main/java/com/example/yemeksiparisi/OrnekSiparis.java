package com.example.yemeksiparisi;

public class OrnekSiparis {
    String id, ad, telefon, adres, yad, adet;

    public OrnekSiparis() {
    }

    public OrnekSiparis(String id, String ad, String telefon, String adres, String yad, String adet) {
        this.id = id;
        this.ad = ad;
        this.telefon = telefon;
        this.adres = adres;
        this.yad = yad;
        this.adet = adet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getYad() {
        return yad;
    }

    public void setYad(String yad) {
        this.yad = yad;
    }

    public String getAdet() {
        return adet;
    }

    public void setAdet(String adet) {
        this.adet = adet;
    }
}