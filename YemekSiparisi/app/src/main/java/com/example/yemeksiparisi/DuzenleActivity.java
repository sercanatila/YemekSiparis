package com.example.yemeksiparisi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DuzenleActivity extends AppCompatActivity {
    private FloatingActionButton btnyemekekleduzenle;
    private RecyclerView rcYemekler;
    YVTHelper yvtHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duzenle);
        rcYemekler=findViewById(R.id.rvyonetici);
        yvtHelper = new YVTHelper(this);
        btnyemekekleduzenle = findViewById(R.id.yemekekle);
        verileriYukle();
        btnyemekekleduzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yemekekle = new Intent(DuzenleActivity.this, YemekKayitGuncelleActivity.class);
                startActivity(yemekekle);
            }
        });
    }
    //Verilerin çekildiği metod..
    private void verileriYukle() {
        //Kayıtadaptor kullanarak yvthelperdaki butunkayitlariAl medtodu çağrılır
        KayitAdaptor kayitAdaptor = new KayitAdaptor(DuzenleActivity.this,yvtHelper.butunKayitlariAl());

        rcYemekler.setAdapter(kayitAdaptor);
    }
}