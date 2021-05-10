package com.example.yemeksiparisi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VtHelper extends SQLiteOpenHelper {

    public VtHelper(@Nullable Context context) {
        super(context, "KULLANİCİ", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tablolar oluşur..
        db.execSQL(Kullanicilar.KULLANICI_OLUSTUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Tablo güncellenir..
        db.execSQL(" Drop TABLE IF EXISTS " + Kullanicilar.TABLO_ADI);
        onCreate(db);
    }
    //Kullanıcı ekle metodu
    public long kullaniciEkle(String ad, String soyad, String kad, String sifre, String telefon, String adres) {
        // Yazılabilir Veritabanı açılır..
        SQLiteDatabase db = this.getWritableDatabase();
        //İçerik değerleri..
        ContentValues degerler = new ContentValues();
        //Eklemeler yapılır..
        degerler.put(Kullanicilar.K_AD, ad);
        degerler.put(Kullanicilar.K_SOYAD, soyad);
        degerler.put(Kullanicilar.K_KAD, kad);
        degerler.put(Kullanicilar.K_SIFRE, sifre);
        degerler.put(Kullanicilar.K_TELEFON, telefon);
        degerler.put(Kullanicilar.K_ADRES, adres);
        //Id'ye göre satır ekleme
        long id = db.insert(Kullanicilar.TABLO_ADI, null, degerler);

        db.close();

        return id;
    }
    //Sipariş ekle metodu..
    public void siparisEkle(String kad, String ad, String adet){
        // Yazılabilir Veritabanı açılır..
        SQLiteDatabase db = this.getWritableDatabase();
        //İçerik değerleri..
        ContentValues degerler = new ContentValues();
        //Eklemeler yapılır..
        degerler.put(Kullanicilar.K_YAD, ad);
        degerler.put(Kullanicilar.K_ADET, adet);
        //kada göre satır güncelleme
        db.update(Kullanicilar.TABLO_ADI, degerler,Kullanicilar.K_KAD + " =?", new String[]{kad});
        db.close();
    }
    //Giriş kontrol..
    public int kullanicikontrol(String kad, String sifre) {
        //Okunulabilir tablo oluşturduk..
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor kullanp tüm satırları tarattırdık..
        Cursor cursor = db.rawQuery("SELECT * FROM KULLANICILAR WHERE KKAD=? and KSIFRE=?", new String[]{kad, sifre});
        if (cursor.getCount() > 0) return 1;
        else return 0;

    }
    //kullanıcı adı alınmış mı kontrol edilir..
    public int kadkontrol(String kad) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from KULLANICILAR where KKAD=? ", new String[]{kad});
        if (cursor.getCount() > 0) return 1;
        else return 0;
    }
    public ArrayList<OrnekSiparis> butunKayitlariAl(){
        //Veriyi döngü ile bütün tablodan alıp listeye aktarım işlemi..
        ArrayList<OrnekSiparis> kayitlarListesi = new ArrayList<>();
        String secimSorgusu = " SELECT * FROM KULLANICILAR ";
        //Veritabanı açılır..
        SQLiteDatabase db = this.getWritableDatabase();
        //Bütün satırları taramak için cursor..
        Cursor cursor = db.rawQuery(secimSorgusu,null);
        if (cursor.moveToFirst()){
            do {
                OrnekSiparis ornekSiparis = new OrnekSiparis(
                        ""+cursor.getInt(cursor.getColumnIndex(Kullanicilar.K_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Kullanicilar.K_AD)),
                        ""+cursor.getString(cursor.getColumnIndex(Kullanicilar.K_TELEFON)),
                        ""+cursor.getString(cursor.getColumnIndex(Kullanicilar.K_ADRES)),
                        ""+cursor.getString(cursor.getColumnIndex(Kullanicilar.K_YAD)),
                        ""+cursor.getString(cursor.getColumnIndex(Kullanicilar.K_ADET)));
                //Listeye ekleme
                kayitlarListesi.add(ornekSiparis);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return kayitlarListesi;
    }}
