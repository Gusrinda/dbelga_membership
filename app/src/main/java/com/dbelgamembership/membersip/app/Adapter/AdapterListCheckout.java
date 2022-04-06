package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart;
import com.dbelgamembership.membersip.databinding.ItemCheckoutBinding;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        double qty = Double.parseDouble(detailItemCart.getQty());

        int batasan1 = detailItemCart.getHarga().getQtyHarga1();
        int batasan2 = detailItemCart.getHarga().getQtyHarga2();
        int batasan3 = detailItemCart.getHarga().getQtyHarga3();

        String harga1 = detailItemCart.getHarga().getHarga();
        String harga2 = detailItemCart.getHarga().getQtyHarga2() == null ? "0" : detailItemCart.getHarga().getHargaDua();
        String harga3 = detailItemCart.getHarga().getQtyHarga3() == null ? "0" : detailItemCart.getHarga().getHargaTiga();

        double jumlahBarangDibeli = qty;
        String hargaFix = "0";
        double jumlahBarangDiskon = 0;
        double totalDiskon = 0;

        double diskon = 0;
        boolean cekTanggal = false;

        if (detailItemCart.getProdukPromo()) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Calendar baru = Calendar.getInstance();

            try {
                Date tanggalNow = baru.getTime();
                Date tanggalAkhir = formatter.parse(detailItemCart.getAkhirPromo());

                long mlNow = tanggalNow.getTime();
                long mlAkhir = tanggalAkhir.getTime();

                if (mlNow <= mlAkhir) {
                    cekTanggal = true;
                } else {
                    cekTanggal = false;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if (detailItemCart.getProdukPromo() && detailItemCart.getStokPromo() > 0 && cekTanggal) {
            Log.e(TAG, "MASUK PROMO");
            double hargaNormal = Double.parseDouble(detailItemCart.getHarga().getHarga());
            double hargaPromo = Double.parseDouble(detailItemCart.getPricePromo());

            diskon = hargaNormal - hargaPromo;

            if (jumlahBarangDibeli <= detailItemCart.getStokPromo()) {
//                diskon = hargaNormal - hargaPromo;
                jumlahBarangDiskon = jumlahBarangDibeli;
//                hargaFix = String.valueOf(hargaPromo);
            } else if (jumlahBarangDibeli > detailItemCart.getStokPromo()) {
                jumlahBarangDiskon = detailItemCart.getStokPromo();
            }

        } else {
            if (batasan1 == batasan2) {
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
            diskon = (Double.parseDouble(harga1) - Double.parseDouble(hargaFix));
            jumlahBarangDiskon = jumlahBarangDibeli;
        }

        totalDiskon = diskon * jumlahBarangDiskon;

        if (diskon > 0) {
            holder.layoutDiskonBarang.setVisibility(View.VISIBLE);
            holder.textDiskonBarang.setText("Rp. " + nf.format(diskon) + " x " + nf.format(jumlahBarangDiskon));
            holder.textTotalDiskonBarang.setText("- Rp. " + nf.format(totalDiskon));
        } else {
            holder.layoutDiskonBarang.setVisibility(View.GONE);
        }

        holder.namaItem.setText(detailItemCart.getNamaProduk());
        holder.barcodeItem.setText(detailItemCart.getCode());
        holder.merkItem.setText(detailItemCart.getMerek());
        holder.hargaItem.setText("Rp. " + nf.format(Double.parseDouble(detailItemCart.getHarga().getHarga())));
        holder.stokItem.setText(" ( " + String.valueOf(detailItemCart.getQty()) + " item )");

        double total = Double.parseDouble(detailItemCart.getHarga().getHarga()) * Double.parseDouble(detailItemCart.getQty());

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
        LinearLayout layoutDiskonBarang;
        TextView textDiskonBarang;
        TextView textTotalDiskonBarang;

        public ViewHolder(ItemCheckoutBinding binding) {
            super(binding.getRoot());

            namaItem = binding.txtNamaBarang;
            barcodeItem = binding.txtKodeBarang;
            merkItem = binding.txtMerekBarang;
            stokItem = binding.txtStokBarang;
            hargaItem = binding.txtHargaBarang;
            totalItem = binding.txtTotalBarang;
            layoutDiskonBarang = binding.layoutDiskonBarang;;
            textDiskonBarang = binding.txtPotonganDiskon;
            textTotalDiskonBarang = binding.txtTotalDiskonBarang;
        }
    }
}