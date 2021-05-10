package com.example.yemeksiparisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SepetActivity extends AppCompatActivity {
    private EditText adet;
    private TextView add,fiyati;
    private String kad,id,ad,fiyat,adett;
    private FloatingActionButton siparisbtn;
    private Uri resimuri;
    private CircularImageView yemekft;
    VtHelper vtHelper;
    AnayemekActivity main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Gerekli atamalar..
        setContentView(R.layout.activity_sepet);
        adet=findViewById(R.id.siparisadet);
        siparisbtn=findViewById(R.id.siparisbtn);
        yemekft = findViewById(R.id.ivyemekresmi);
        add = findViewById(R.id.siparis_yemekadi);
        fiyati = findViewById(R.id.siparis_fiyat);
        yemekft = findViewById(R.id.sptyemekresmi);
        vtHelper = new VtHelper(this);
        main = new AnayemekActivity();
        //Veritabanından verileri çek..
        Intent intent = getIntent();
        id= intent.getStringExtra("YID");
        ad= intent.getStringExtra("YAD");
        fiyat= intent.getStringExtra("YFIYAT");
        resimuri= Uri.parse(intent.getStringExtra("YRESIM"));
        kad = main.kad;

        //Textlere atamalar
        add.setText(ad);
        fiyati.setText(fiyat);
        yemekft.setImageURI(resimuri);
        adet.setText("1");
        //Buton işlevi..
        siparisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adett = adet.getText().toString().trim();
                vtHelper.siparisEkle(kad,ad,adett);
                Toast.makeText(SepetActivity.this, "Siparişiniz Oluşturuldu..", Toast.LENGTH_SHORT).show();
            }
        });

    }
}