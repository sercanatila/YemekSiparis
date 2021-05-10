package com.example.yemeksiparisi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SiparisAdaptor extends RecyclerView.Adapter<SiparisAdaptor.SiparisTutucu> {
    private Context context;
    private final ArrayList<OrnekSiparis> ornekList;
    VtHelper vtHelper;

    public SiparisAdaptor(Context context, ArrayList<OrnekSiparis> ornekList) {
        this.context = context;
        this.ornekList = ornekList;
        vtHelper = new VtHelper(context);
    }

    @NonNull
    @Override
    public SiparisTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sp_satirgorunumu,parent,false);
        return new SiparisAdaptor.SiparisTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiparisTutucu holder, int position) {
        //Verileri alma
        OrnekSiparis ornekSiparis = ornekList.get(position);
        //Ornekkayıttan atamalar yapılır..
        String add = ornekSiparis.getAd();
        String telefonn = ornekSiparis.getTelefon();
        String adress = ornekSiparis.getAdres();
        String yadd = ornekSiparis.getYad();
        String adett = ornekSiparis.getAdet();
        //Verileri yazdırma..
        holder.ad.setText(add);
        holder.telefon.setText(telefonn);
        holder.adres.setText(adress);
        holder.yad.setText(yadd);
        holder.adet.setText(adett);
    }

    @Override
    public int getItemCount() {
        return ornekList.size();
    }

    public class SiparisTutucu extends RecyclerView.ViewHolder {
        TextView ad,telefon,adres,yad,adet;
        public SiparisTutucu(@NonNull View itemView) {
            super(itemView);
            ad = itemView.findViewById(R.id.spanaad);
            telefon = itemView.findViewById(R.id.spanatelefon);
            adres = itemView.findViewById(R.id.spanaadres);
            yad = itemView.findViewById(R.id.spanayad);
            adet = itemView.findViewById(R.id.spanaadet);
        }
    }
}
