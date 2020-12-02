package com.dbelgamembership.membersip.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.R;

public class AdapterListVoucher extends RecyclerView.Adapter<AdapterListVoucher.ViewHolder> {

    String name[], date, detail[], type[];
    Context context;

    public AdapterListVoucher(Context ct, String[] namaVoucher, String tanggalVoucher, String[] detailVoucher, String[] tipe) {

        name = namaVoucher;
        date = tanggalVoucher;
        detail = detailVoucher;
        type = tipe;
        context = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_list_voucher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.namaVoucher.setText(name[position]);
        holder.detailVoucher.setText(detail[position]);
        holder.tipeVoucher.setText(type[position]);
        holder.tanggalVoucher.setText("Expired : " + date);

    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaVoucher, tanggalVoucher, detailVoucher;
        Button tipeVoucher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaVoucher = itemView.findViewById(R.id.namaVoucher);
            tanggalVoucher = itemView.findViewById(R.id.tanggalVoucher);
            detailVoucher = itemView.findViewById(R.id.detailVoucher);
            tipeVoucher = itemView.findViewById(R.id.typeVoucher);
        }
    }
}
