package com.dbelgamembership.membersip.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelPayment.Datum;
import com.dbelgamembership.membersip.Model.ModelPayment.Item;
import com.dbelgamembership.membersip.Model.ModelPayment.OrderDetail;
import com.dbelgamembership.membersip.R;

import java.text.NumberFormat;
import java.util.HashMap;
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

        TextView kodeBelanja, tanggalBelanja, statusBelanja, totalBelanja, poinBelanja, lihatDetail;
        LinearLayout lnContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
        sessionManager = new SessionManager(context);
        com.dbelgamembership.membersip.Model.ModelPayment.Datum item = list.get(position);
        holder.kodeBelanja.setText(item.getPembayaranCode());
        holder.tanggalBelanja.setText(item.getDateTransaction());

        String status = item.getStatus();
        if (status.equals("approved")) {
            status = "Sudah Dibayar";
            totalTransaksi++;
            holder.statusBelanja.setBackgroundResource(R.drawable.button_round_confirm);
        } else {
            status = item.getStatus();
            holder.statusBelanja.setBackgroundResource(R.drawable.button_round_cancel);
        }

        Log.e("TAG", "status: " + status );
        holder.statusBelanja.setText(status);
        int total = 0;
//        int GTotal = 0;
        for (OrderDetail orderDetail : item.getOrderDetail()) {
            List<Item> items = orderDetail.getItems();
            for (int i = 0; i < items.size(); i++) {
                Item barang = items.get(i);
                total += (Integer.parseInt(barang.getTotal()) - Integer.parseInt(barang.getTotalDiskon()));
//                GTotal += (Integer.parseInt(barang.getTotal()) - Integer.parseInt(barang.getTotalDiskon()));
            }
        }
        int grandCOK = total;
        holder.totalBelanja.setText("Rp. " + nf.format(grandCOK));

        int totalBelanja = grandCOK;
        int totalPoin = totalBelanja / 10000;

        poinBelanja += totalPoin;

        holder.poinBelanja.setText(totalPoin + " Poin");
        Log.e(TAG, "Total Transaksi : " + totalTransaksi );
        Log.e(TAG, "Total Poin Belanja : " + poinBelanja );
        sessionManager.setKeyPoinbelanja(String.valueOf(poinBelanja));
        sessionManager.setKeyTotaltransaksi(String.valueOf(totalTransaksi));
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
