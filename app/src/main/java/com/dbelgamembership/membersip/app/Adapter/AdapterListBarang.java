package com.dbelgamembership.membersip.app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.Model.ModelKatalog;
import com.dbelgamembership.membersip.R;


import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListBarang extends
        RecyclerView.Adapter<AdapterListBarang.ViewHolder> {

    private static final String TAG = AdapterListBarang.class.getSimpleName();

    SessionManager sessionManager;
    String member;

    private Context context;
    private List<ModelKatalog> list;
    private KatalogActivity mAdapterCallback;
    private int result = -1;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);


    public AdapterListBarang(Context context, List<ModelKatalog> list, KatalogActivity adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        sessionManager = new SessionManager(context);
        member = sessionManager.getMembership();
        Log.e(TAG, "Status Membership : " + member );
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            final ModelKatalog item = list.get(position);
            boolean isClickable = true;
            Log.e(TAG, "onBindViewHolder ADAPTER IMAGE : " + item.getImages() );

            Glide.with(context)
                    .asBitmap()
                    .load(item.getImages())
                    .into(holder.imageBarang);

            if (item.getNama_barang().length() > 0) {
                holder.namaBarang.setText(item.getNama_barang());
            }

            if (item.getKategori_barang().length() > 0) {
                holder.kategoriBarang.setText(item.getKategori_barang());
            } else {
                holder.kategoriBarang.setVisibility(View.GONE);
            }

            if (item.getMerk_barang().length() > 0) {
                holder.merkBarang.setText(item.getMerk_barang());
            } else {
                holder.merkBarang.setVisibility(View.GONE);
            }

            if (item.getKode_barang().length() > 0) {
                holder.tvKodeBarang.setText(item.getKode_barang());
            }

            int cekStok = Integer.parseInt(item.getStok());


            if (cekStok > 0 && cekStok < 10) {
                holder.tvStokOutlet.setText(" < 10 " + item.getSatuan_kemasan());
            } else if (cekStok >= 10 && cekStok < 25) {
                holder.tvStokOutlet.setText(" < 25 " + item.getSatuan_kemasan());
            } else if (cekStok >= 25 && cekStok < 50) {
                holder.tvStokOutlet.setText(" < 50 "+ item.getSatuan_kemasan());
            } else if (cekStok >= 50) {
                holder.tvStokOutlet.setText(" > 50 "+ item.getSatuan_kemasan());
            } else if (cekStok == 0 || cekStok < 0){
                holder.tvStokOutlet.setText("KOSONG");
                isClickable = false;
            }


            Log.e(TAG, "NAMA BARANG : " + item.getNama_barang() );
            Log.e(TAG, "STOK : " + cekStok );

            holder.hargaDiskonBarang.setVisibility(View.GONE);



            if (holder.tvStokOutlet.getText().toString().equals("KOSONG") || item.getHarga_barang() == null ) {
                isClickable = false;
                Double hargaBarang = Double.parseDouble(item.getHarga_barang() == null ? "0" : item.getHarga_barang());
                Double hargaBarang2 = Double.parseDouble(item.getHarga_2() == null ? "0" : item.getHarga_2());

                if (member.equals("REGULER")) {
                    holder.hargaBarang.setText("? (Harga Stok Akhir : " + nf.format(hargaBarang) +" )");
                } else {
                    holder.hargaBarang.setText("? (Harga Stok Akhir : " +  nf.format(hargaBarang2)  +" )");
                }
                holder.hargaBarang2.setVisibility(View.GONE);
                holder.hargaBarang3.setVisibility(View.GONE);
                holder.tvStokOutlet.setText("KOSONG");
            } else {

                Log.e(TAG, "MASUK 6");
                int hargaBarang = (int) Double.parseDouble(item.getHarga_barang());
                int hargaBarang2 = (int) Double.parseDouble(item.getHarga_2());
                int hargaBarang3 = (int) Double.parseDouble(item.getHarga_3());

                String testHarga = "Rp. " + nf.format(hargaBarang)+" [1]";
                String testHarga2 = "Rp. " + nf.format(hargaBarang2)+" ["+ item.getBatasan2() +"]";
                String testHarga3 = "Rp. " + nf.format(hargaBarang3)+" ["+ item.getBatasan3() +"]";
                holder.hargaBarang.setText(testHarga);
                holder.hargaBarang2.setText(testHarga2);
                holder.hargaBarang3.setText(testHarga3);
                holder.hargaDiskonBarang.setText(testHarga2);

                if (item.getBatasan1().equals(item.getBatasan2())) {
                    holder.hargaBarang2.setVisibility(View.GONE);
                    holder.hargaBarang3.setVisibility(View.GONE);
                }

            }

            boolean finalIsClickable = isClickable;
            holder.barangItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                    if (finalIsClickable) {
                        
                    mAdapterCallback.AdapterListBarangClicked(item);
                    } else {
                        Toast.makeText(context, "Barang tidak bisa dibeli !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: error" + e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<ModelKatalog> items) {
        this.list.addAll(this.list.size(), items);
        notifyDataSetChanged();
    }

    public interface AdapterListBarangCallback {
        void AdapterListBarangClicked(ModelKatalog position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageBarang)
        ImageView imageBarang;
        @BindView(R.id.barangItem)
        CardView barangItem;
        @BindView(R.id.namaBarang)
        TextView namaBarang;
        @BindView(R.id.kategoriBarang)
        TextView kategoriBarang;
        @BindView(R.id.merkBarang)
        TextView merkBarang;
        @BindView(R.id.tvKodeBarang)
        TextView tvKodeBarang;
        @BindView(R.id.hargaDiskonBarang)
        TextView hargaDiskonBarang;
        @BindView(R.id.hargaBarang)
        TextView hargaBarang;

        @BindView(R.id.hargaBarang2)
        TextView hargaBarang2;

        @BindView(R.id.hargaBarang3)
        TextView hargaBarang3;

        @BindView(R.id.tvStok)
        TextView tvStok;
        @BindView(R.id.textStokOutlet)
        TextView tvStokOutlet;
        @BindView(R.id.tvBarcodeBarang)
        TextView tvBarcodeBarang;
        @BindView(R.id.tvUri)
        TextView tvUri;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}