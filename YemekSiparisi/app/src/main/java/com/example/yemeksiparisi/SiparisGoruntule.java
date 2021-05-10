package com.example.yemeksiparisi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class SiparisGoruntule extends AppCompatActivity {
    private RecyclerView rcKullanici;
    VtHelper vtHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_goruntule);
        rcKullanici=findViewById(R.id.rvsp);
        vtHelper = new VtHelper(this);
        verileriYukle();
    }
    //Verilerin çekildiği metod..
    private void verileriYukle() {
        //Kayıtadaptor kullanarak yvthelperdaki butunkayitlariAl medtodu çağrılır
        SiparisAdaptor siparisAdaptor = new SiparisAdaptor(SiparisGoruntule.this,vtHelper.butunKayitlariAl());
        rcKullanici.setAdapter(siparisAdaptor);
    }}