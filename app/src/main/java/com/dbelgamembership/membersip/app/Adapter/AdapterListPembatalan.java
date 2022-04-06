package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.modelListTransaksi.DetailKekurangan;
import com.dbelgamembership.membersip.databinding.ItemPembatalanBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListPembatalan extends
        RecyclerView.Adapter<AdapterListPembatalan.ViewHolder> {

    private static final String TAG = AdapterListPembatalan.class.getSimpleName();

    private Context context;
    private List<DetailKekurangan> list;
    private AdapterListGudangCallback mAdapterCallback;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
    private SessionManager sessionManager;

    public AdapterListPembatalan(Context context, List<DetailKekurangan> list, AdapterListGudangCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPembatalanBinding itemBinding = ItemPembatalanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        sessionManager = new SessionManager(context.getApplicationContext());

        return new ViewHolder(itemBinding);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            DetailKekurangan detailKekurangan = list.get(position);

            holder.namaItem.setText(detailKekurangan.getNamaProduk());
            holder.barcodeItem.setText(detailKekurangan.getCodeProduk());
            holder.hargaItem.setText("Rp. " + nf.format(Double.parseDouble(detailKekurangan.getHargaProduk())));
            holder.qtyItem.setText("jumlah beli : " + detailKekurangan.getQtyProduk());
            holder.totalBarang.setText("Rp. " + nf.format((Double.parseDouble(detailKekurangan.getQtyProduk()) * Double.parseDouble(detailKekurangan.getHargaProduk()))));
            holder.AlasanGagal.setText(detailKekurangan.getAlasanProduk());
    }


    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }


    public interface AdapterListGudangCallback {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaItem;
        TextView barcodeItem;
        TextView hargaItem;
        TextView qtyItem;
        TextView totalBarang;
        TextView AlasanGagal;


        public ViewHolder(ItemPembatalanBinding binding) {
            super(binding.getRoot());
            namaItem = binding.txtNamaBarang;
            barcodeItem = binding.txtKodeBarang;
            hargaItem = binding.txtHargaBarang;
            qtyItem = binding.txtStokBarang;
            totalBarang = binding.txtTotalBarang;
            AlasanGagal = binding.txtAlasanPembatalan;
        }
    }
}