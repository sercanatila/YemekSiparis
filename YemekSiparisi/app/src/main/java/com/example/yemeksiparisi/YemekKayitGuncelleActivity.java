package com.example.yemeksiparisi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class YemekKayitGuncelleActivity extends AppCompatActivity {
    // Gerekli atamalar
    YVTHelper yvthelp;
    private String id;
    private String ad;
    private String fiyat;
    private CircularImageView yemekft;
    private EditText yad,yfiyat;
    private FloatingActionButton yemekeklebtn;
    //Güncelleme durum kontrolü..
    private boolean guncellemeDurumu = false;
    //Talep sabitleri..
    private static final int KAMERA_TALEP=100;
    private static final int DEPOLAMA_TALEP=101;
    //resim seçme sabitleri..
    private static final int GALERIDEN_IZIN=102;
    private static final int KAMERADAN_IZIN=103;
    //izinler dizisi..
    private String[] kameraizinleri;
    private String[] depolamaizinleri;
    //Resim tutmak için gerekli atamalar
    private Uri resimuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemek_kayit_guncelle);
        //Ythelper..
        yvthelp = new YVTHelper(this);
        //gerekli atamalar yapıldı..
        yemekeklebtn=findViewById(R.id.yemekeklebtn);
        yemekft = findViewById(R.id.ivyemekresmi);
        yad = findViewById(R.id.et_yemekadı);
        yfiyat = findViewById(R.id.et_fiyat);
        //İzindizileri tanımlamaları
        kameraizinleri= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        depolamaizinleri= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //Gelen intenti al..
        Intent intent = getIntent();
        guncellemeDurumu = intent.getBooleanExtra("GÜNCELLEME DURUMU",false);
        //Güncelleme durumunu kontrol et
        if (guncellemeDurumu){
            //Veritabanından verileri çek..
            id= intent.getStringExtra("ID");
            ad= intent.getStringExtra("YAD");
            fiyat= intent.getStringExtra("YFIYAT");
            resimuri= Uri.parse(intent.getStringExtra("YRESIM"));
            //Alınanları kontrollere at
            yad.setText(ad);
            yfiyat.setText(fiyat);
            yemekft.setImageURI(resimuri);
        }
        //Resim seçme butonu işlevi
        yemekft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimSecmeDialog();
            }
        });
        //Yemek ekle ve güncelleme butonu
        yemekeklebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriEkle();
            }
        });
        }
    private void veriEkle() {
        //Atamalar..
        ad = yad.getText().toString().trim();
        fiyat = yfiyat.getText().toString().trim();
        //Boş alan bırakılıp bırakılmadığı kontrol edilir..
        if (guncellemeDurumu){
            if (ad.isEmpty() ||  fiyat.isEmpty()) {
                Toast.makeText(YemekKayitGuncelleActivity.this, "Boş alan bırakmayınız..", Toast.LENGTH_SHORT).show();
            } else {
                     yvthelp.yemekGuncelle(
                            ""+id,
                            "" + ad,
                            "" + fiyat,
                            "" + resimuri);

                    Toast.makeText(YemekKayitGuncelleActivity.this, "Güncelleme Başarılı..", Toast.LENGTH_SHORT).show();
                }}

        else{
        if (ad.isEmpty() ||  fiyat.isEmpty()) {
            Toast.makeText(YemekKayitGuncelleActivity.this, "Boş alan bırakmayınız..", Toast.LENGTH_SHORT).show();
        } else {
            //yadkontrol metoduyla yemek adı kontrolu yapılır..
            int kontrol = yvthelp.yadkontrol(ad);
            if (kontrol == 1){
                Toast.makeText(YemekKayitGuncelleActivity.this, "Yemek adı alınmış..", Toast.LENGTH_SHORT).show();
            }
            else {
                //Ekleme işlemi..
                long id = yvthelp.yemekEkle(
                        "" + ad,
                        "" + fiyat,
                        "" + resimuri);

                Toast.makeText(YemekKayitGuncelleActivity.this, "Kayıt Başarılı..", Toast.LENGTH_SHORT).show();
            }}}}
//Alertdialog sayesinde kullanıcıya hangisini istersin sorusu yöneltilir..
    private void resimSecmeDialog() {
        String[] ogeler = {"Kamera","Galeri"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resim seç");
        builder.setItems(ogeler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                   //Kameraya erişim izni yoksa talep etme..
                    if(!kameraIzin()){
                        kameratalep();
                    }
                    else{
                        //Kameradan resim seçme..
                        kameradanSec();
                    }
                }
                if (i == 1) {
                    //Depolama erişim izni yoksa talep etme..
                    if(!depolamaIzin()){
                        depolamatalep();
                    }
                    else{
                        //Depolamadan resim seçme..
                        galeridenSec();
                    }
                }
            }
        });
        builder.create().show();
    }
    //Kamerayı açma
    private void kameradanSec() {
        //değerleri image title ve description ile resimuriye insert edildi..
        ContentValues degerler = new ContentValues();
        degerler.put(MediaStore.Images.Media.TITLE,"Image Title");
        degerler.put(MediaStore.Images.Media.DESCRIPTION,"Image Description");
        resimuri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,degerler);

        //Hangi dosya türünü açmak istendiği yazıldı..
        Intent kameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        kameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, resimuri);
        startActivityForResult(kameraIntent,KAMERADAN_IZIN);
    }
    //Galeriyi açma
    private void galeridenSec() {
        //Hangi dosya türünü açmak istendiği yazıldı..
        Intent galeriIntent = new Intent(Intent.ACTION_PICK);
        galeriIntent.setType("image/*");
        startActivityForResult(galeriIntent,GALERIDEN_IZIN);
    }
    //Depolama için izin kontrolü
    private boolean depolamaIzin(){
        boolean sonuc = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return sonuc;
    }
    //Depolama izin talebi
    private void depolamatalep(){
        ActivityCompat.requestPermissions(this,depolamaizinleri,DEPOLAMA_TALEP);
    }
    //Kamera için izin kontrolü
    private boolean kameraIzin(){
        boolean sonuc1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean sonuc2 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return sonuc1&&sonuc2;
    }
    //Kamera izin talebi
    private void kameratalep(){
        ActivityCompat.requestPermissions(this,kameraizinleri,KAMERA_TALEP);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Kamera ya da galeriden alınacak resmi işleme..
        if(resultCode==RESULT_OK){
            if ((resultCode==GALERIDEN_IZIN)){
                //Cropimage kütüphanesi ile kırpma işlemi..
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode==KAMERADAN_IZIN){
                //Cropimage kütüphanesi ile kırpma işlemi..
                CropImage.activity(resimuri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //kırpılan resim sonuca alınır
                CropImage.ActivityResult sonuc = CropImage.getActivityResult(data);
                if (resultCode==RESULT_OK){
                    resimuri = sonuc.getUri();
                    //Yemek resmine alma
                    yemekft.setImageURI(resimuri);}
                else{
                    Exception hata = sonuc.getError();
                    Toast.makeText(this,""+hata,Toast.LENGTH_SHORT).show();
                    }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

