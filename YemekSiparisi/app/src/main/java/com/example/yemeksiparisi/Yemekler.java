package com.example.yemeksiparisi;

public class Yemekler {
    public static final String TABLO_ADI = "YEMEKLER";
    //Yemek bilgileri ve sütun atamaları
    public static final String Y_ID = "YID";
    public static final String Y_AD = "YAD";
    public static final String Y_FIYAT = "YFIYAT";
    public static final String Y_RESIM = "YRESIM";

    //sql query
    public static final String YEMEK_OLUSTUR = " CREATE TABLE " + TABLO_ADI + "("
            + Y_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Y_AD + " TEXT,"
            + Y_FIYAT + " TEXT,"
            + Y_RESIM + " TEXT"
            +")";
}
