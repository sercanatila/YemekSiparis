package com.example.yemeksiparisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class YoneticiActivity extends AppCompatActivity implements View.OnClickListener {
     private Button dznl,sprs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonetici);

        dznl = (Button) findViewById(R.id.duzenlebtn);
        sprs = (Button) findViewById(R.id.goruntulebtn);

        dznl.setOnClickListener(this);
        sprs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.duzenlebtn:
                Intent duzen = new Intent(YoneticiActivity.this, DuzenleActivity.class);
                startActivity(duzen);
                break;
            case R.id.goruntulebtn:
                Intent sp = new Intent(YoneticiActivity.this, SiparisGoruntule.class);
                startActivity(sp);
                break;
            default:
                break;
    }
}}