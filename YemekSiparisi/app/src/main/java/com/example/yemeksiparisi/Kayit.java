package com.example.yemeksiparisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Kayit extends AppCompatActivity {

    private EditText edtad, edtsoyad, edtkad, edtsifre, edtadres, edttelefon;
    private String id, ad, soyad, kad, sifre, telefon, adres;
    private Button donbtn, kayitbtn;
    VtHelper vthelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        //buton atamaları..
        donbtn = findViewById(R.id.geribtn);
        kayitbtn = findViewById(R.id.kytbtn);
        //Edittext atamaları..
        edtad = findViewById(R.id.editTextisim);
        edtsoyad = findViewById(R.id.editsoyad);
        edtkad = findViewById(R.id.editTextkad);
        edtsifre = findViewById(R.id.editTextsifresi);
        edttelefon = findViewById(R.id.editTexttelefon);
        edtadres = findViewById(R.id.editTextadres);
        vthelp = new VtHelper(this);
        //buton işlevleri
        kayitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //veritabanına kayıt gönderme..
                veriEkle();
            }

            private void veriEkle() {
                //Atamalar..
                ad = edtad.getText().toString().trim();
                soyad = edtsoyad.getText().toString().trim();
                kad = edtkad.getText().toString().trim();
                sifre = edtsifre.getText().toString().trim();
                telefon = edttelefon.getText().toString().trim();
                adres = edtadres.getText().toString().trim();
                //Boş alan bırakılıp bırakılmadığı kontrol edilir..
                if (ad.isEmpty() ||  soyad.isEmpty() || kad.isEmpty() ||  sifre.isEmpty() ||  telefon.isEmpty() || adres.isEmpty()) {
                    Toast.makeText(Kayit.this, "Boş alan bırakmayınız..", Toast.LENGTH_SHORT).show();
                } else {
                    //kadkontrol metoduyla kullanıcı adı kontrolu yapılır..
                    int kontrol = vthelp.kadkontrol(kad);
                    if (kontrol == 1){
                        Toast.makeText(Kayit.this, "Kullanıcı adı alınmış..", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    //Ekleme işlemi..
                    long id = vthelp.kullaniciEkle(
                            "" + ad,
                            "" + soyad,
                            "" + kad,
                            "" + sifre,
                            "" + telefon,
                            "" + adres);

                    Toast.makeText(Kayit.this, "Kayıt Başarılı..", Toast.LENGTH_SHORT).show();
                }}
            }});

        donbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Giriş dön..
                Intent giris = new Intent(Kayit.this, MainActivity.class);
                startActivity(giris);
            }
        });
    }
}