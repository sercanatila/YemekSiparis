package com.example.yemeksiparisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private Button btnkayit,btngiris;
    private EditText edtkad,edtsif;
    VtHelper vthelp;
    static int b=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vthelp = new VtHelper(this);
        //Edit textler atandı..
        edtkad = findViewById(R.id.editTextKullaniciAdi);
        edtsif = findViewById(R.id.editTextSifre);
        // kayıt butonu atandı..
        btnkayit = findViewById(R.id.kayitbtn);
        btngiris = findViewById(R.id.girisbtn);
        //Butona basınca sayfa yönlendirilmesi..
        btnkayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kayit = new Intent(MainActivity.this, Kayit.class);
                startActivity(kayit);
            }
        });

        btngiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Yönetici kontrolü yapılır..
                if (edtkad.getText().toString().equals("Yönetici") && edtsif.getText().toString().equals("1506")){
                    b = 1;
                    Intent yonetici = new Intent(MainActivity.this, YoneticiActivity.class);
                    startActivity(yonetici);
                }
               //Yönetici değilse vthelper sınfıından giriş kontrolü yapılır
                else {
                int kontrol = vthelp.kullanicikontrol(edtkad.getText().toString().trim(),edtsif.getText().toString().trim());
                if (kontrol == 1){
                    b = 2;
                    Intent anasayfa = new Intent(MainActivity.this, AnayemekActivity.class);
                    anasayfa.putExtra("KAD",edtkad.getText().toString());
                    startActivity(anasayfa);
                }
                else {
                    Toast.makeText(MainActivity.this, "Kullanıcı Adı veya Şifre yanlış !!!", Toast.LENGTH_SHORT).show();
                }
            }}
        });

    }
}