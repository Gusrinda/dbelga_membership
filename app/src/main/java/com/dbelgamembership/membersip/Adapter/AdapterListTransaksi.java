package com.dbelgamembership.membersip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.R;

public class AdapterListTransaksi extends RecyclerView.Adapter<AdapterListTransaksi.ViewHolder> {

    String id[];
    String detailTransaksi[];
    Context context;

    public AdapterListTransaksi(Context ct, String[] idTransaksi, String[] detail) {

        id = idTransaksi;
        detailTransaksi = detail;
        context = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_list_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.detailTrans.setText(detailTransaksi[position]);
        holder.jumlahBeli.setText("Jumlah beli : " + id[position]);

    }

    @Override
    public int getItemCount() {
        return detailTransaksi.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView detailTrans, jumlahBeli;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailTrans = itemView.findViewById(R.id.detailTransaksi);
            jumlahBeli = itemView.findViewById(R.id.jumlahBeli);
        }
    }
}
