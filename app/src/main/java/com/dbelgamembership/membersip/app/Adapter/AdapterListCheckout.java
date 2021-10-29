package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart;
import com.dbelgamembership.membersip.databinding.ItemCheckoutBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListCheckout extends
        RecyclerView.Adapter<AdapterListCheckout.ViewHolder> {

    private static final String TAG = AdapterListCheckout.class.getSimpleName();

    private Context context;
    private List<DetailItemCart> list;

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterListCheckout(Context context, List<DetailItemCart> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCheckoutBinding itemBinding = ItemCheckoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        DetailItemCart detailItemCart = list.get(position);
        int qty = detailItemCart.getQty();

        int batasan1 = detailItemCart.getHarga().getQtyHarga1();
        int batasan2 = detailItemCart.getHarga().getQtyHarga2();
        int batasan3 = detailItemCart.getHarga().getQtyHarga3();

        String harga1 = detailItemCart.getHarga().getHarga();
        String harga2 = detailItemCart.getHarga().getQtyHarga2() == null ? "0" : detailItemCart.getHarga().getHargaDua();
        String harga3 = detailItemCart.getHarga().getQtyHarga3() == null ? "0" : detailItemCart.getHarga().getHargaTiga();
        int jumlahBarangDibeli = qty;
        String hargaFix = "0";

        if (batasan1 == batasan2) {
//                            Log.e(TAG, "TambahkanKeListBarang: " + pm.getHarga_barang());
            hargaFix = harga1;
        } else {
            if (jumlahBarangDibeli < batasan2) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga1());
                hargaFix = harga1;
            } else if (jumlahBarangDibeli >= batasan2 && jumlahBarangDibeli < batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga2());
                hargaFix = harga2;
            } else if (jumlahBarangDibeli >= batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga3());
                hargaFix = harga3;
            }
        }

        int diskon = (int) (Double.parseDouble(harga1) - Double.parseDouble(hargaFix));

        holder.namaItem.setText(detailItemCart.getNamaProduk());
        holder.barcodeItem.setText(detailItemCart.getCode());
        holder.merkItem.setText(detailItemCart.getMerek());
        holder.hargaItem.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
        holder.stokItem.setText(" ( " + String.valueOf(detailItemCart.getQty()) + " item )");

        int total = (int) (Double.parseDouble(hargaFix) * detailItemCart.getQty());

        holder.totalItem.setText("Rp. " + nf.format(total));


    }


    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaItem;
        TextView barcodeItem;
        TextView merkItem;
        TextView hargaItem;
        TextView stokItem;
        TextView totalItem;

        public ViewHolder(ItemCheckoutBinding binding) {
            super(binding.getRoot());

            namaItem = binding.txtNamaBarang;
            barcodeItem = binding.txtKodeBarang;
            merkItem = binding.txtMerekBarang;
            stokItem = binding.txtStokBarang;
            hargaItem = binding.txtHargaBarang;
            totalItem = binding.txtTotalBarang;

        }
    }
}