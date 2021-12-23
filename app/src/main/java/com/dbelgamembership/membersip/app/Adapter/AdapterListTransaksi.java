package com.dbelgamembership.membersip.app.Adapter;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

        TextView kodeBelanja, tanggalBelanja, statusBelanja, totalBelanja, poinBelanja, namaToko;
        CardView cardLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kodeBelanja = itemView.findViewById(R.id.txt_kodeTransaksi);
            tanggalBelanja = itemView.findViewById(R.id.txt_tanggalTransaksi);
            totalBelanja = itemView.findViewById(R.id.txt_nominalTransaksi);
            poinBelanja = itemView.findViewById(R.id.txt_poinTransaksi);
            statusBelanja = itemView.findViewById(R.id.status_transaksi);
            cardLayout = itemView.findViewById(R.id.cardLayout);
            namaToko = itemView.findViewById(R.id.txtNamaToko);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum item = list.get(position);
        holder.kodeBelanja.setText(item.getCode());
        holder.tanggalBelanja.setText(item.getCreatedAt());

        String status = item.getStatus();
        if (status.equals("approval")) {
            status = "Transaksi Dalam Approval Admin";
            holder.statusBelanja.setTextColor(context.getColor(R.color.prangeBelha));
        } else if (status.equals("cancel")) {
            status = "Transaksi dicancel";
            holder.statusBelanja.setTextColor(context.getColor(R.color.merahBelga));
        } else if (status.equals("confirmation")) {
            status = "Pembayaran dalam proses konfirmasi";
            holder.statusBelanja.setTextColor(context.getColor(R.color.hijauBelga));
        } else if (status.equals("payment")) {
            status = "Pilih metode pembayaran";
            holder.statusBelanja.setTextColor(context.getColor(R.color.merahBelga));
        } else if (status.equals("shipment")) {
            status = "Sedang proses pengiriman";
            holder.statusBelanja.setTextColor(context.getColor(R.color.hijauBelga));
        } else if (status.equals("approved")) {
            status = "Transaksi Dalam Penutupan Kasir";
            holder.statusBelanja.setTextColor(context.getColor(R.color.hijauBelga));
        }

        String idGudang = String.valueOf(item.getGudang());

        for (int i = 0; i < modelGudangs.size(); i++) {
            if (idGudang == modelGudangs.get(i).getIdGudang()) {
                holder.namaToko.setText(" : Toko " + modelGudangs.get(i).getNamaGudang());
            }
        }

        holder.statusBelanja.setText(status);
        holder.totalBelanja.setText("Rp. " + nf.format(item.getGrandtotal()));
        int totalBelanja = item.getGrandtotal();
        int totalPoin = totalBelanja / 10000;
        holder.poinBelanja.setText("+ " + totalPoin + " Poin");

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onRowAdapterListTransactionClicked(item);
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
        void onRowAdapterListTransactionClicked(Datum item);
    }

}
