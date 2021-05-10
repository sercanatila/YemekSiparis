package com.example.yemeksiparisi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KayitAdaptor extends RecyclerView.Adapter<KayitAdaptor.KayitTutucu> implements Filterable {
    private Context context;
    private final ArrayList<OrnekKayit> ornekList;
    private final ArrayList<OrnekKayit> ornekListFull;
    YVTHelper yvtHelper;
    MainActivity main;
    VtHelper vtHelper;
    AnayemekActivity mainn;
    private String kad;


    public KayitAdaptor(Context context, ArrayList<OrnekKayit> ornekList) {
        this.context = context;
        this.ornekList = ornekList;
        ornekListFull = new ArrayList<>(ornekList);
        yvtHelper = new YVTHelper(context);
        mainn = new AnayemekActivity();
        kad = mainn.kad;
    }
    //Satır görünümünü adaptöre bağlama..
    @NonNull
    @Override
    public KayitTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_satirgorunumu,parent,false);
        return new KayitTutucu(view);
    }
    //Verileri alma, kontrollere aktarma, satırlara tıklama işlemleri..
    @Override
    public void onBindViewHolder(@NonNull KayitTutucu holder, int position) {
    //Verileri alma
        OrnekKayit ornekKayit = ornekList.get(position);
        //Ornekkayıttan atamalar yapılır..
        String id = ornekKayit.getId();
        String ad = ornekKayit.getAd();
        String fiyat = ornekKayit.getFiyat();
        String resim = ornekKayit.getResim();
        //Verileri yazdırma..
        holder.twanaisim.setText(ad);
        holder.twanafiyat.setText(fiyat);
        //Resim uriye çevrilip aktarılır
        holder.yemekfoto.setImageURI(Uri.parse(resim));
        holder.morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (main.b==1){
                guncellemeSilmePenceresiOlustur(
                        ""+position,
                        ""+id,
                        ""+ad,
                        ""+fiyat,
                        ""+resim
                );}
                else if (main.b==2){
                    kullaniciPenceresiOlustur(
                            ""+position,
                            ""+id,
                            ""+ad,
                            ""+fiyat,
                            ""+resim
                    );}
                }
        });
    }
    //Sepete ekle sepeti görünütle metodu..
    private void kullaniciPenceresiOlustur(String position, String id, String ad, String fiyat, String resim) {
        String [] secenekler = {"Sepete Ekle","Hızlı Sipariş"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(secenekler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i==0){
                    //Sepet sayfası yönlendirme..
                    Intent kayitVeriGonder = new Intent(context,SepetActivity.class);
                    kayitVeriGonder.putExtra("ID",id);
                    kayitVeriGonder.putExtra("YAD",ad);
                    kayitVeriGonder.putExtra("YFIYAT",fiyat);
                    kayitVeriGonder.putExtra("YRESIM",resim);
                    context.startActivity(kayitVeriGonder);
                }
                if ((i==1)){
                    //Hızlı sipariş verme
                    vtHelper.siparisEkle(kad,ad,"1");
                }
            }
        });
        builder.create().show();
    }
    //Güncelleme silme metodu..
    private void guncellemeSilmePenceresiOlustur(String position, String id, String ad, String fiyat, String resim) {
        String [] secenekler = {"Güncelle","Sil"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(secenekler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i==0){
                    //Güncelle sayfası aktarma
                    Intent kayitVeriGonder = new Intent(context,YemekKayitGuncelleActivity.class);
                    kayitVeriGonder.putExtra("ID",id);
                    kayitVeriGonder.putExtra("YAD",ad);
                    kayitVeriGonder.putExtra("YFIYAT",fiyat);
                    kayitVeriGonder.putExtra("YRESIM",resim);
                    //Kayıtın güncellenip güncellenmediği için..
                    kayitVeriGonder.putExtra("GÜNCELLEME DURUMU",true);
                    context.startActivity(kayitVeriGonder);
                }
                if ((i==1)){
                    //Sil
                    yvtHelper.veriSil(id);
                }
            }
        });

        builder.create().show();
    }

    //Satır sayısı..
    @Override
    public int getItemCount() {
        return ornekList.size();
    }
    //Arama işlemi için Filter oluşumu..
    @Override
    public Filter getFilter() {
        return ornekFilter;
    }
    private Filter ornekFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<OrnekKayit> filteredList = new ArrayList<>();
            //Aratmama durumunda tüm kayıtlar geledek..
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(ornekListFull);
            }
            else {
                //Aratma durumundaki charları aldık ve filteredListe attık..
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (OrnekKayit kayit : ornekListFull){
                    if (kayit.getAd().toLowerCase().contains(filterPattern)){
                        filteredList.add(kayit);
                    }
                }
            }
            //sonuçları dönderdik..
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ornekList.clear();
            ornekList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    //Kontrol Tanımlamaları
    public class KayitTutucu extends RecyclerView.ViewHolder {
        ImageView yemekfoto;
        TextView twanaisim,twanafiyat;
        ImageButton morebtn;
        public KayitTutucu(@NonNull View itemView) {
            super(itemView);
            //Atamalar yapılır..
            yemekfoto= itemView.findViewById(R.id.yemekfoto);
            twanaisim= itemView.findViewById(R.id.twanaisim);
            twanafiyat= itemView.findViewById(R.id.twanafiyat);
            morebtn= itemView.findViewById(R.id.morebtn);
        }
    }
}
