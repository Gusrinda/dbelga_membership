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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ItemCartBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private SessionManager sessionManager;

    public AdapterListCart(Context context, List<DetailItemCart> list, AdapterListGudangCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCartBinding itemBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        sessionManager = new SessionManager(context.getApplicationContext());

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
        double jumlahBarangDiskon = 0;
        double totalDiskon = 0;
        String hargaFix = "0";

        double diskon = 0;
        boolean cekTanggal = false;

        boolean isPromo = false;
        double stokPromo = 0;

        if (detailItemCart.getProdukPromo() != null) {
            if (detailItemCart.getProdukPromo()) {
                isPromo = true;
//                                stokPromo = Double.parseDouble(itemSaatIni.getStokPromo() == null ? "0" : String.valueOf(itemSaatIni.getStokPromo()));
                stokPromo = detailItemCart.getStokPromo();
            } else {
                isPromo = false;
            }
        }

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

        double hargaNormal = 0;
        double hargaPromo = 0;


        if (detailItemCart.getProdukPromo() && detailItemCart.getStokPromo() > 0 && cekTanggal) {
            Log.e(TAG, "MASUK PROMO");
             hargaNormal = Double.parseDouble(detailItemCart.getHarga().getHarga());
             hargaPromo = Double.parseDouble(detailItemCart.getPricePromo());

            diskon = hargaNormal - hargaPromo;

            if (jumlahBarangDibeli <= detailItemCart.getStokPromo()) {
                jumlahBarangDiskon = jumlahBarangDibeli;
            } else if (jumlahBarangDibeli > detailItemCart.getStokPromo()) {
                jumlahBarangDiskon = detailItemCart.getStokPromo();
            }

        } else {
            if (batasan1 == batasan2) {
                hargaFix = harga1;
            } else {
                if (jumlahBarangDibeli < batasan2) {
                    hargaFix = harga1;
                } else if (jumlahBarangDibeli >= batasan2 && jumlahBarangDibeli < batasan3) {
                    hargaFix = harga2;
                } else if (jumlahBarangDibeli >= batasan3) {
                    hargaFix = harga3;
                }
            }
            diskon = Double.parseDouble(harga1) - Double.parseDouble(hargaFix);

            hargaNormal = Double.parseDouble(harga1);
            hargaPromo = Double.parseDouble(hargaFix);

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

        double diskonMember = 0;
        double totalDiskonMember = 0;
        double diskonMemberWithPromo = 0;
        double totalDiskonMemberWithPromo = 0;
        double totalDiskonPromoMember = 0;
        double persentaseDiskonMembership = 0;

        if (detailItemCart.getProdukPromoMember()) {

            holder.layoutDiskonMembership.setVisibility(View.VISIBLE);



            if (sessionManager.getMembership().equals("SILVER")) {
                persentaseDiskonMembership = Double.parseDouble(detailItemCart.getPersenPromoMemberSilver());
            } else if (sessionManager.getMembership().equals("GOLD")) {
                persentaseDiskonMembership = Double.parseDouble(detailItemCart.getPersenPromoMemberGold());
            } else if (sessionManager.getMembership().equals("PLATINUM")) {
                persentaseDiskonMembership = Double.parseDouble(detailItemCart.getPersenPromoMemberPlatinum());
            }

            holder.textDiskonMembership.setText(nf.format(persentaseDiskonMembership) + "% x " + nf.format(jumlahBarangDibeli));

            if (isPromo) {

                Log.e(TAG, "MASUK KE BAGIAN CHECK ADA PROMO PERIODE");

                if (jumlahBarangDibeli <= detailItemCart.getStokPromo()) {

                    Log.e(TAG, "setupTestPerulanganPenggantianMember MASUK JUMLAH BARANG DIBELI IF");

                    //Tentukan stok beli normal
                    //jumlah Barang dibeli
                    //Hitung diskon member persen dari harga nomral
                    diskonMember = (double) (persentaseDiskonMembership * hargaPromo) / 100;

                    Log.e(TAG, "DISKON MEMBER :: " + diskonMember);

                    //Hitung total diskonMember
                    totalDiskonMember = diskonMember * jumlahBarangDibeli;
                    Log.e(TAG, "TOTAL DISKON MEMBER :: " + totalDiskonMember);

                    //jumlahkan hasil perhitungan diskon membership
                    totalDiskonPromoMember = totalDiskonMember + totalDiskonMemberWithPromo;

                    Log.e(TAG, "TOTAL DISKON PROMO MEMBER :: " + totalDiskonPromoMember);
                } else if (jumlahBarangDibeli > detailItemCart.getStokPromo()) {

                    Log.e(TAG, "setupTestPerulanganPenggantianMember MASUK JUMLAH BARANG DIBELI ELSE IF");

                    //Tentukan stok beli normal
                    double jumlahNormal = jumlahBarangDibeli - detailItemCart.getStokPromo();
                    //Hitung diskon member persen dari harga nomral
                    diskonMember = (double) (persentaseDiskonMembership * hargaNormal) / 100;
                    //Hitung total diskonMember
                    totalDiskonMember = diskonMember * jumlahNormal;

                    //Tentukan stok beli promo
                    double jumlahPromo = detailItemCart.getStokPromo();
                    //Hitung diskon member persen dari harga promo
                    diskonMemberWithPromo = (double) (persentaseDiskonMembership * hargaPromo) / 100;
                    //Hitung total diskonMember promo
                    totalDiskonMemberWithPromo = diskonMemberWithPromo * jumlahPromo;

                    //jumlahkan hasil perhitungan diskon membership
                    totalDiskonPromoMember = totalDiskonMember + totalDiskonMemberWithPromo;
                }

                holder.textTotalDiskonMembership.setText("- Rp. " + nf.format(totalDiskonPromoMember));

            } else {


                double hargaBarangSetelahDiskon = Double.parseDouble(harga1) - diskon;
                diskonMember = hargaBarangSetelahDiskon * persentaseDiskonMembership / 100;

                totalDiskonMember = diskonMember * jumlahBarangDibeli;

                holder.textTotalDiskonMembership.setText("- Rp. " + nf.format(totalDiskonMember));

            }

        } else  {
            holder.layoutDiskonMembership.setVisibility(View.GONE);
        }



        holder.totalBarang.setText("Rp. " + nf.format(qty * Double.parseDouble(harga1)));

        if (detailItemCart.getProdukPromo()) {
            holder.stokItem.setText(" [ " + nf.format(detailItemCart.getStok()) + " stok ( " + nf.format(detailItemCart.getStokPromo()) + " promo )]");
        } else {
            holder.stokItem.setText(" [ " + nf.format(detailItemCart.getStok()) + " stok ]");
        }

//        Glide.with(context).load(detailItemCart.getImages()).error(R.drawable.not_found).into(holder.gambarItem);

        Glide.with(context)
                .asBitmap()
                .load(detailItemCart.getImages())
                .error(R.drawable.not_found)
                .into(holder.gambarItem);

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

        holder.layoutButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mAdapterCallback.editBarang(detailItemCart);
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

        void editBarang(DetailItemCart detailItemCart);
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
        LinearLayout layoutDiskonBarang;
        TextView textDiskonBarang;
        TextView textTotalDiskonBarang;

        LinearLayout layoutDiskonMembership;
        TextView textDiskonMembership;
        TextView textTotalDiskonMembership;

        LinearLayout layoutButtonEdit;


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
            layoutDiskonBarang = binding.layoutDiskonBarang;
            ;
            textDiskonBarang = binding.txtPotonganDiskon;
            textTotalDiskonBarang = binding.txtTotalDiskonBarang;
            layoutDiskonMembership = binding.layoutDiskonMembership;
            textDiskonMembership = binding.txtPotonganDiskonMembership;
            textTotalDiskonMembership = binding.txtTotalDiskonMembership;

            layoutButtonEdit = binding.layoutEditBarang;

//            edtQty.addTextChangedListener(this);
        }
    }
}