package com.dbelgamembership.membersip.app.Adapter;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelPayment.AddItem;
import com.dbelgamembership.membersip.Model.ModelPayment.Datum;
import com.dbelgamembership.membersip.Model.ModelPayment.Item;
import com.dbelgamembership.membersip.Model.ModelPayment.OrderDetail;
import com.dbelgamembership.membersip.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListTransaksiPayment extends RecyclerView.Adapter<AdapterListTransaksiPayment.ViewHolder> {

    SessionManager sessionManager;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private Context context;
    private List<com.dbelgamembership.membersip.Model.ModelPayment.Datum> list;
    private AdapterListTransaksiPayment.AdapterListTransactionCallback mAdapterCallback;
    private int result = -1;
    private int poinBelanja = 0;
    private int totalTransaksi = 0;
    private String TAG = "";

    public AdapterListTransaksiPayment(Context context, int result, List<com.dbelgamembership.membersip.Model.ModelPayment.Datum> list, AdapterListTransaksiPayment.AdapterListTransactionCallback mAdapterCallback) {
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
        sessionManager = new SessionManager(context);
        com.dbelgamembership.membersip.Model.ModelPayment.Datum item = list.get(position);
        holder.kodeBelanja.setText(item.getPembayaranCode());
        holder.tanggalBelanja.setText(item.getDateTransaction());


        String statusPengiriman = "";

        Log.e(TAG, "Data : " + item.getStatusPengiriman());

//        if (item.getFlagKirim()) {
            if (item.getStatus().equals("approved")) {
                statusPengiriman = "Transaksi Selesai";
                holder.statusBelanja.setTextColor(context.getColor(R.color.hijauBelga));
            } else {
                statusPengiriman = item.getStatusPengiriman();
                holder.statusBelanja.setTextColor(context.getColor(R.color.merahBelga));
            }

        String idGudang = String.valueOf(item.getGudang());

        for (int i = 0; i < modelGudangs.size(); i++) {
            if (idGudang == modelGudangs.get(i).getIdGudang()) {
                holder.namaToko.setText(" : Toko " + modelGudangs.get(i).getNamaGudang());
            }
        }


        Log.e("TAG", "status Pengiriman : " + statusPengiriman);
        holder.statusBelanja.setText(statusPengiriman);
        int total = (int) Double.parseDouble(item.getTotalBelanja());

        int grandCOK = total;
        holder.totalBelanja.setText("Rp. " + nf.format(grandCOK));

        int totalBelanja = grandCOK;
        int totalPoin = totalBelanja / 10000;

        poinBelanja += totalPoin;

        totalTransaksi++;

        holder.poinBelanja.setText("+ " + totalPoin + " Poin");
        Log.e(TAG, "Total Transaksi : " + totalTransaksi);
        Log.e(TAG, "Total Poin Belanja : " + poinBelanja);
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
        void onRowAdapterListTransactionClicked(Datum position);
    }

}
