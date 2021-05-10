package com.example.yemeksiparisi;

public class Kullanicilar {
    public static final String TABLO_ADI = "KULLANICILAR";
    //Kullanıcı bilgileri ve sütun atamaları
    public static final String K_ID = "KID";
    public static final String K_AD = "KAD";
    public static final String K_SOYAD = "KSOYAD";
    public static final String K_KAD = "KKAD";
    public static final String K_SIFRE = "KSIFRE";
    public static final String K_TELEFON = "KTELEFON";
    public static final String K_ADRES = "KADRES";
    public static final String K_YAD = "KYAD";
    public static final String K_ADET = "KADET";
    //sql query
    public static final String KULLANICI_OLUSTUR = " CREATE TABLE " + TABLO_ADI + "("
            + K_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + K_AD + " TEXT,"
            + K_SOYAD + " TEXT,"
            + K_KAD + " TEXT,"
            + K_SIFRE + " TEXT,"
            + K_TELEFON + " TEXT,"
            + K_ADRES + " TEXT,"
            + K_YAD + " TEXT,"
            + K_ADET + " TEXT"
            +")";

}
