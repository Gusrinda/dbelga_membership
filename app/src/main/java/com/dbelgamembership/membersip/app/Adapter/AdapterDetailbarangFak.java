package com.dbelgamembership.membersip.app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelPayment.Item;
import com.dbelgamembership.membersip.Model.modelArrayDetailBarangOrder;
import com.dbelgamembership.membersip.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterDetailbarangFak extends
        RecyclerView.Adapter<AdapterDetailbarangFak.ViewHolder> {

    private static final String TAG = AdapterDetailbarangFak.class.getSimpleName();
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

    private Context context;
//    public static List<Item> list;
    public static List<modelArrayDetailBarangOrder> list;
    private AdapterDetailbarangCallback mAdapterCallback;
    private int result = -1;

    public AdapterDetailbarangFak(Context context, int result,  List<modelArrayDetailBarangOrder> list, AdapterDetailbarangCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice_trans,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        modelArrayDetailBarangOrder item = list.get(position);



        holder.tvNama.setText(item.getNamaBrg());

        holder.tvCode.setText(item.getCode());
        holder.tvHargaBarang.setText("Rp. " + nf.format(Double.parseDouble(item.getHarga())));
        double qty = item.getQty();
        Log.e(TAG, "onBindViewHolder: " + qty );
        holder.tvQty.setText(String.valueOf(nf.format((int) qty)));
        holder.tvTotal.setText("Rp. " + nf.format(Double.parseDouble(item.getTotal()) - Double.parseDouble(item.getNominal_diskon())));
        Log.e(TAG, "onBindViewHolder: " + item.getTotal() );
        holder.tvKeterangan.setVisibility(View.GONE);
        if (Double.parseDouble(item.getNominal_diskon()) != 0) {
            holder.layoutDiskon.setVisibility(View.VISIBLE);
            double totalDiskon =  Double.parseDouble(item.getNominal_diskon());
            double diskonPerBarang = totalDiskon / qty;
            holder.tvDiskon.setText( "Rp. " + nf.format(totalDiskon) +" ( "  +String.valueOf(diskonPerBarang) + " x " + qty  +   " )");
        } else {
            holder.layoutDiskon.setVisibility(View.GONE);
        }
    }

    public void addItems(List<modelArrayDetailBarangOrder> items) {
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

    public interface AdapterDetailbarangCallback {
        void onRowAdapterDetailbarangClicked(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNama)
        TextView tvNama;
        @BindView(R.id.tvCode)
        TextView tvCode;
        @BindView(R.id.tvQty)
        TextView tvQty;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.tvKeterangan)
        TextView tvKeterangan;
        @BindView(R.id.tvHarga)
        TextView tvHargaBarang;
        @BindView(R.id.tvDiskonan)
        TextView tvDiskon;
        @BindView(R.id.layoutDiskonPrint)
        LinearLayout layoutDiskon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}