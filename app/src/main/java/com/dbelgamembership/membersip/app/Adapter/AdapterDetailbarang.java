package com.dbelgamembership.membersip.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.dbelgamembership.membersip.Model.modelListTransaksi.Detail;
import com.dbelgamembership.membersip.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterDetailbarang extends
        RecyclerView.Adapter<AdapterDetailbarang.ViewHolder> {

    private static final String TAG = AdapterDetailbarang.class.getSimpleName();
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private Context context;
    private List<Detail> list;
    private AdapterDetailbarangCallback mAdapterCallback;
    private int result = -1;

    public AdapterDetailbarang(Context context, int result, List<Detail> list, AdapterDetailbarangCallback adapterCallback) {
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
        Detail item = list.get(position);
        holder.tvNama.setText(item.getName());
        holder.tvCode.setText(item.getCodeProduct());
        double qty = Double.parseDouble(item.getQtyOutlet()) + Double.parseDouble(item.getQtyStore()) + item.getIndentValue();
        double qtyOutlet = Double.parseDouble(item.getQtyOutlet());
        double qtyIndent = item.getIndentValue();
        if (item.getIndentValue() != 0) {
                if (qtyOutlet > 0 ){
                    holder.tvQty.setText(String.valueOf(nf.format((int) qtyOutlet)) + " (STOK) + " + String.valueOf(nf.format((int) qtyIndent)) +" (INDENT)");
                }   else {
                    holder.tvQty.setText(String.valueOf(nf.format((int) qty)) + " (INDENT)");
                }
        } else {
            holder.tvQty.setText(String.valueOf(nf.format((int) qty)) +" (STOK)");
        }
        holder.tvKeterangan.setVisibility(View.GONE);
        holder.tvHarga.setText("Rp. " + nf.format(Double.parseDouble(item.getRealPrice() == null ? item.getPrice() : item.getRealPrice())));

        double totalDiskon =  Double.parseDouble(item.getTotalDiskon() == null ? "0" : item.getTotalDiskon());

        if (totalDiskon > 0) {
            double qtyDiskon = Double.parseDouble(item.getQtyDiskon());
            double potonganDiskon =  (totalDiskon / qtyDiskon);

            holder.tvDiskonan.setText("Rp. " + nf.format(totalDiskon) + " ( " + nf.format(qtyDiskon) + " x " + nf.format(potonganDiskon) + " )");
            holder.tvTotal.setText("Rp. " + nf.format(Double.parseDouble(item.getTotalSetelahDiskon())));
            holder.layoutDiskonPrint.setVisibility(View.VISIBLE);
        } else {
            holder.tvTotal.setText("Rp. " + nf.format(Double.parseDouble(item.getTotalSetelahDiskon() == null ? item.getTotal() : item.getTotalSetelahDiskon())));
            holder.layoutDiskonPrint.setVisibility(View.GONE);
        }
        if (item.getIsDiskonMembership() != null) {
            if (item.getIsDiskonMembership()) {
                holder.layoutDiskonPrintMember.setVisibility(View.VISIBLE);
                double persenDiskon = Double.parseDouble(item.getPresentaseDiskonMembership());
                holder.tvDiskonanMember.setText("Rp. " + nf.format(Double.parseDouble(item.getTotalDiskonMembership())) + " ( " +  nf.format(qty) + " x " + nf.format(persenDiskon) + "% )");
            } else {
                holder.layoutDiskonPrintMember.setVisibility(View.GONE);
            }
        } else {
            holder.layoutDiskonPrintMember.setVisibility(View.GONE);
        }

    }

    public void addItems(List<Detail> items) {
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
        @BindView(R.id.tvKeterangan)
        TextView tvKeterangan;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.tvHarga)
        TextView tvHarga;
        @BindView(R.id.tvDiskonan)
        TextView tvDiskonan;
        @BindView(R.id.layoutDiskonPrint)
        LinearLayout layoutDiskonPrint;

        @BindView(R.id.tvDiskonanMember)
        TextView tvDiskonanMember;
        @BindView(R.id.layoutDiskonPrintMember)
        LinearLayout layoutDiskonPrintMember;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}