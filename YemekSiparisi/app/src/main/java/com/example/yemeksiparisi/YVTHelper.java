package com.example.yemeksiparisi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class YVTHelper extends SQLiteOpenHelper {
    public YVTHelper(@Nullable Context context) {
        super(context, "YEMEK", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Yemekler.YEMEK_OLUSTUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" Drop TABLE IF EXISTS " + Yemekler.TABLO_ADI);

        onCreate(db);
    }
    //yemek ekle metodu..
    public long yemekEkle(String ad, String fiyat, String resim) {
        // Yazılabilir Veritabanı açılır..
        SQLiteDatabase db = this.getWritableDatabase();
        //İçerik değerleri..
        ContentValues degerler = new ContentValues();
        //Eklemeler yapılır..
        degerler.put(Yemekler.Y_AD, ad);
        degerler.put(Yemekler.Y_FIYAT, fiyat);
        degerler.put(Yemekler.Y_RESIM, resim);
        //Id'ye göre satır ekleme
        long id = db.insert(Yemekler.TABLO_ADI, null, degerler);

        db.close();

        return id;
    }
    //Yemek silme metodu
    public void veriSil(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Yemekler.TABLO_ADI, Yemekler.Y_ID + " =? ", new String[]{id} );
        db.close();
    }
    //kayıt güncelleme metodu..
    public void yemekGuncelle(String id, String ad, String fiyat, String resim){
        // Yazılabilir Veritabanı açılır..
        SQLiteDatabase db = this.getWritableDatabase();
        //İçerik değerleri..
        ContentValues degerler = new ContentValues();
        //Eklemeler yapılır..
        degerler.put(Yemekler.Y_AD, ad);
        degerler.put(Yemekler.Y_FIYAT, fiyat);
        degerler.put(Yemekler.Y_RESIM, resim);
        //Id'ye göre satır güncelleme
        db.update(Yemekler.TABLO_ADI, degerler,Yemekler.Y_ID + " =? ", new String[]{id});
        db.close();
    }
    // Yemek daha önce eklenmiş mi kontrol edilir..
    public int yadkontrol(String yad) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from YEMEKLER where YAD=? ", new String[]{yad});
        if (cursor.getCount() > 0) return 1;
        else return 0;
    }
    //Veritabanından verileri çekme
    public ArrayList<OrnekKayit> butunKayitlariAl(){
        //Veriyi döngü ile bütün tablodan alıp listeye aktarım işlemi..
        ArrayList<OrnekKayit> kayitlarListesi = new ArrayList<>();
        String secimSorgusu = " SELECT * FROM YEMEKLER ";
        //Veritabanı açılır..
        SQLiteDatabase db = this.getWritableDatabase();
        //Bütün satırları taramak için cursor..
        Cursor cursor = db.rawQuery(secimSorgusu,null);
        if (cursor.moveToFirst()){
            do {
                OrnekKayit ornekKayit = new OrnekKayit(
                        ""+cursor.getInt(cursor.getColumnIndex(Yemekler.Y_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Yemekler.Y_AD)),
                        ""+cursor.getString(cursor.getColumnIndex(Yemekler.Y_FIYAT)),
                        ""+cursor.getString(cursor.getColumnIndex(Yemekler.Y_RESIM)));
                //Listeye ekleme..
                kayitlarListesi.add(ornekKayit);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return kayitlarListesi;
    }
}


