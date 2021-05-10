package com.example.yemeksiparisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AnayemekActivity extends AppCompatActivity {
        private RecyclerView rcYemekler;
        YVTHelper yvtHelper;
        static String id,kad;
        KayitAdaptor kayitAdaptor;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_anayemek);
            rcYemekler=findViewById(R.id.rvana);
            yvtHelper = new YVTHelper(this);
            verileriYukle();
            Intent intent = getIntent();
            id= intent.getStringExtra("KAD");
            kad = id;
        }
        //Verilerin çekildiği metod..
        private void verileriYukle() {
            //Kayıtadaptor kullanarak yvthelperdaki butunkayitlariAl medtodu çağrılır
            kayitAdaptor = new KayitAdaptor(AnayemekActivity.this,yvtHelper.butunKayitlariAl());
            rcYemekler.setAdapter(kayitAdaptor);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ornek_menu, menu);
        MenuItem ara = menu.findItem(R.id.arama);
        SearchView searchView = (SearchView) ara.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                kayitAdaptor.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}