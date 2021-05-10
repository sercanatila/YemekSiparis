package com.example.yemeksiparisi;

public class OrnekKayit {
    String id,ad,fiyat,resim;

    public OrnekKayit() {
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public OrnekKayit(String id, String ad, String fiyat, String resim) {
        this.id = id;
        this.ad = ad;
        this.fiyat = fiyat;
        this.resim = resim;
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


    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }
}
