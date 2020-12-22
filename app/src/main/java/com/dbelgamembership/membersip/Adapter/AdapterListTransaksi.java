package com.dbelgamembership.membersip.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.modelListTransaksi.Datum;
import com.dbelgamembership.membersip.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListTransaksi extends RecyclerView.Adapter<AdapterListTransaksi.ViewHolder> {

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private Context context;
    private List<Datum> list;
    private AdapterListTransaksi.AdapterListTransactionCallback mAdapterCallback;
    private int result = -1;

    public AdapterListTransaksi(Context context, int result, List<Datum> list, AdapterListTransaksi.AdapterListTransactionCallback mAdapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = mAdapterCallback;
        this.result = result;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_list_transaksi, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView kodeBelanja, tanggalBelanja, statusBelanja, totalBelanja, poinBelanja, lihatDetail, text_poinBel;
        LinearLayout lnContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_poinBel = itemView.findViewById(R.id.txt_poin);
            kodeBelanja = itemView.findViewById(R.id.txt_kodeBelanja);
            tanggalBelanja = itemView.findViewById(R.id.txt_tanggalBelanja);
            statusBelanja = itemView.findViewById(R.id.txt_statusBelanja);
            totalBelanja = itemView.findViewById(R.id.txt_totalBelanja);
            poinBelanja = itemView.findViewById(R.id.txt_poinBelanja);
            lihatDetail = itemView.findViewById(R.id.txt_lihatDetail);
            lnContent = itemView.findViewById(R.id.lnContent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum item = list.get(position);
        holder.kodeBelanja.setText(item.getCode());
        holder.tanggalBelanja.setText(item.getCreatedAt());
        holder.text_poinBel.setText("Poin yang Bisa Didapatkan");

        String status = item.getStatus();
        if (status.equals("approved")) {
            status = "Belum Dibayar";
            holder.statusBelanja.setBackgroundResource(R.drawable.button_round_cancel);
        } else {
            status = "Sudah Dibayar";
            holder.statusBelanja.setBackgroundResource(R.drawable.button_round_confirm);
        }

        Log.e("TAG", "status: " + status );
        holder.statusBelanja.setText(status);
        holder.totalBelanja.setText("Rp. " + nf.format(item.getGrandtotal()));
        int totalBelanja = item.getGrandtotal();
        int totalPoin = totalBelanja / 10000;
        holder.poinBelanja.setText(totalPoin + " Poin");

        holder.lnContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onRowAdapterListTransactionClicked(position);
            }
        });

    }

    public void addItems(List<Datum> items) {
        this.list.addAll(this.list.size(), items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        } else {
            if (result >= 1) {
                return Math.min(list.size(), result);
            } else {
                return list.size();
            }
        }
    }

    public void clear() {
        int size = this.list.size();
        this.list.clear();
        notifyItemRangeRemoved(0, size);
    }

    public interface AdapterListTransactionCallback {
        void onRowAdapterListTransactionClicked(int position);
    }

}
