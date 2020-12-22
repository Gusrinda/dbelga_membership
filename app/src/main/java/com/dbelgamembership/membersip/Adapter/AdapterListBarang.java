package com.dbelgamembership.membersip.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.KatalogActivity;
import com.dbelgamembership.membersip.Model.ModelKatalog;
import com.dbelgamembership.membersip.R;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListBarang extends
        RecyclerView.Adapter<AdapterListBarang.ViewHolder> {

    private static final String TAG = AdapterListBarang.class.getSimpleName();


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            final ModelKatalog item = list.get(position);
            Drawable image;
            if (!item.getImages().equals("http://54.254.194.122/upload/barang/")) {
                Glide.with(context)
                        .asBitmap()
                        .load(item.getImages())
                        .into(holder.imageBarang);
            } else {
                image = context.getResources().getDrawable(R.drawable.not_found);
                holder.imageBarang.setImageDrawable(image);
            }

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
                holder.tvStokOutlet.setText(" < 10");
            } else if (cekStok >= 10 && cekStok < 25) {
                holder.tvStokOutlet.setText(" < 25");
            } else if (cekStok >= 25 && cekStok < 50) {
                holder.tvStokOutlet.setText(" < 50");
            } else if (cekStok > 50) {
                holder.tvStokOutlet.setText(" > 50");
            } else {
                holder.tvStokOutlet.setText("KOSONG");
            }

            holder.hargaDiskonBarang.setVisibility(View.GONE);

            String testHarga = "Rp. " + nf.format(Long.parseLong(item.getHarga_barang()));
//            Log.e(TAG, "hargaBarang: " + testHarga );
            holder.hargaBarang.setText(testHarga);


            holder.barangItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterCallback.AdapterListBarangClicked(item);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: error" + e.getMessage());
        }
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

//    public void clear() {
//        int size = this.list.size();
//        this.list.clear();
//        notifyItemRangeRemoved(0, size);
//    }

//    public void addItems(List<ModelKatalog> items) {
//        this.list.addAll(this.list.size(), items);
//        notifyDataSetChanged();
//    }


    public interface AdapterListBarangCallback {
        void AdapterListBarangClicked(ModelKatalog position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageBarang)
        ImageView imageBarang;
        @BindView(R.id.barangItem)
        LinearLayout barangItem;
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