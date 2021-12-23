package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ItemCartBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AdapterListCart extends
        RecyclerView.Adapter<AdapterListCart.ViewHolder> {

    private static final String TAG = AdapterListCart.class.getSimpleName();

    private Context context;
    private List<DetailItemCart> list;
    private AdapterListGudangCallback mAdapterCallback;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterListCart(Context context, List<DetailItemCart> list, AdapterListGudangCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCartBinding itemBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        DetailItemCart detailItemCart = list.get(position);

        holder.namaItem.setText(detailItemCart.getNamaProduk());
        holder.barcodeItem.setText(detailItemCart.getBarcode());
        holder.merkItem.setText(detailItemCart.getMerek());
        holder.hargaItem.setText(nf.format(Double.parseDouble(detailItemCart.getHarga().getHarga())));
        holder.edtQty.setText(String.valueOf(detailItemCart.getQty()));

        double stokMax = detailItemCart.getStok();
        double qty = Double.parseDouble(holder.edtQty.getText().toString());

        int batasan1 = detailItemCart.getHarga().getQtyHarga1();
        int batasan2 = detailItemCart.getHarga().getQtyHarga2();
        int batasan3 = detailItemCart.getHarga().getQtyHarga3();

        String harga1 = detailItemCart.getHarga().getHarga();
        String harga2 = detailItemCart.getHarga().getQtyHarga2() == null ? "0" : detailItemCart.getHarga().getHargaDua();
        String harga3 = detailItemCart.getHarga().getQtyHarga3() == null ? "0" : detailItemCart.getHarga().getHargaTiga();

        if (qty >= stokMax) {
            holder.btnTambah.setEnabled(false);
            holder.btnTambah.setTextColor(context.getColor(R.color.greyBelha));
        } else {
            holder.btnTambah.setEnabled(true);
            holder.btnTambah.setTextColor(context.getColor(R.color.black));
        }

        double jumlahBarangDibeli = qty;
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

        if (diskon > 0) {
            holder.hargaReal.setVisibility(View.VISIBLE);
            holder.hargaReal.setText("Rp. " + nf.format(Double.parseDouble(harga1)));
            holder.hargaItem.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
        } else {
            holder.hargaReal.setVisibility(View.GONE);
            holder.hargaReal.setText("0");
            holder.hargaItem.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
        }

        holder.totalBarang.setText("Rp. " + nf.format(qty * Double.parseDouble(hargaFix)));

        holder.stokItem.setText(" ( " + String.valueOf(detailItemCart.getStok()) + " stok )");
        Glide.with(context).load(detailItemCart.getImages()).into(holder.gambarItem);

        holder.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double qty = Double.parseDouble(holder.edtQty.getText().toString());

                qty = qty + 1;

                DecimalFormat df = new DecimalFormat("#.##");
                String dx = df.format(qty);
                qty = Double.parseDouble(dx);
                Log.e(TAG, "onClick: " + qty);

                if (qty > detailItemCart.getStok()) {
                    holder.edtQty.setText(String.valueOf((detailItemCart.getStok())));
                } else {
                    holder.edtQty.setText(String.valueOf(qty));
                }

            }
        });

        holder.btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double qty = Double.parseDouble(holder.edtQty.getText().toString());

                qty = qty - 1;

                DecimalFormat df = new DecimalFormat("#.##");
                String dx = df.format(qty);
                qty = Double.parseDouble(dx);
                Log.e(TAG, "onClick: " + qty);

                if (qty <= 0) {
                    mAdapterCallback.deleteBarang(detailItemCart);
                } else {
                    holder.edtQty.setText(String.valueOf(qty));
                }

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAdapterCallback.deleteBarang(detailItemCart);
            }
        });

        holder.edtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1500; // milliseconds

            @SuppressLint("NewApi")
            @Override
            public void afterTextChanged(Editable editable) {

                double check = 0;

                if (editable.length() > 0) {
                    check = Double.parseDouble(editable.toString());
                } else {
                    check = 0;
                }

                double qty = check;

                if (qty >= stokMax) {
                    holder.btnTambah.setEnabled(false);
                    holder.btnTambah.setTextColor(context.getColor(R.color.greyBelha));
                } else {
                    holder.btnTambah.setEnabled(true);
//                    holder.btnKurang.setEnabled(true);
                    holder.btnTambah.setTextColor(context.getColor(R.color.black));
//                    holder.btnKurang.setTextColor(context.getColor(R.color.black));
                }

                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapterCallback.updateQtyBarang(list.get(position), qty);
                                    }
                                });

                            }
                        },
                        DELAY
                );
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }


    public interface AdapterListGudangCallback {
        void deleteBarang(DetailItemCart detailItemCart);

        void updateQtyBarang(DetailItemCart detailItemCart, double qtyItem);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView gambarItem;
        TextView namaItem;
        TextView barcodeItem;
        TextView merkItem;
        TextView hargaItem;
        TextView edtQty;
        TextView hargaReal;
        TextView totalBarang;
        TextView stokItem;
        Button btnTambah;
        Button btnKurang;
        ImageView btnDelete;
        ConstraintLayout constraintLayout;


        public ViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            constraintLayout = binding.layout;
            gambarItem = binding.imgGambar;
            namaItem = binding.txtNamaBarang;
            barcodeItem = binding.txtKodeBarang;
            merkItem = binding.txtMerekBarang;
            stokItem = binding.txtStokBarang;
            hargaItem = binding.txtHargaBarang;
            edtQty = binding.qty;
            hargaReal = binding.txtHargaBarangReal;
            totalBarang = binding.txtTotalBeliBarang;
            btnTambah = binding.increment;
            btnKurang = binding.decrement;
            btnDelete = binding.btnDeleteBarang;
//            edtQty.addTextChangedListener(this);
        }
    }
}