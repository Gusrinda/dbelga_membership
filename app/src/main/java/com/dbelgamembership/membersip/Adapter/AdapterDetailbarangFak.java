package com.dbelgamembership.membersip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.modelListFaktur.Item;
import com.dbelgamembership.membersip.PrintFakturActivity;
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
    public static ArrayList<HashMap<String, String>> list;
    private AdapterDetailbarangCallback mAdapterCallback;
    private int result = -1;

    public AdapterDetailbarangFak(Context context, int result,  ArrayList<HashMap<String, String>> list, AdapterDetailbarangCallback adapterCallback) {
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
        HashMap<String, String> item = list.get(position);

        holder.tvNama.setText(item.get("namaBrg"));
//        Log.e(TAG, "onBindViewHolder: " + item.getName() );
        holder.tvCode.setText(item.get("Code"));
        holder.tvQty.setText(item.get("qtyUnit"));

        holder.tvKeterangan.setText(item.get("keterangan"));
        holder.tvHargaBarang.setText("Rp. " + nf.format(Double.parseDouble(item.get("harga"))));

        holder.tvTotal.setText("Rp. " + nf.format(Double.parseDouble(item.get("total"))));

//        holder.tvNama.setText(item.getName());
//        holder.tvCode.setText(item.getCodeProduct());
//        holder.tvHargaBarang.setText("Rp. " + nf.format(Integer.parseInt(item.getRealPrice())));
//        int qty = Integer.parseInt(item.getQtyOutlet()) + Integer.parseInt(item.getQtyStore()) + item.getIndentValue();
//        holder.tvQty.setText(qty + "");
//        holder.tvTotal.setText("Rp. " + nf.format(Integer.parseInt(item.getTotal()) - Integer.parseInt(item.getTotalDiskon())));
//        holder.tvDiskon.setText("Rp. " + nf.format(Integer.parseInt(item.getTotalDiskon())));

    }

    public void addItems(ArrayList<HashMap<String, String>> items) {
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}