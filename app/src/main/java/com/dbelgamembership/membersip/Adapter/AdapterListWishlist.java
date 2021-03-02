package com.dbelgamembership.membersip.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.KatalogActivity;
import com.dbelgamembership.membersip.Model.ModelKatalog;
import com.dbelgamembership.membersip.Model.ModelWish.Price;
import com.dbelgamembership.membersip.Model.ModelWish.WishlistDetail;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.WishlishActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListWishlist extends
        RecyclerView.Adapter<AdapterListWishlist.ViewHolder> {

    private static final String TAG = AdapterListWishlist.class.getSimpleName();


    SessionManager sessionManager;
    String member;

    private Context context;
    private List<WishlistDetail> list;
    private WishlishActivity mAdapterCallback;
    private int result = -1;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterListWishlist(Context context, List<WishlistDetail> list, WishlishActivity adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        sessionManager = new SessionManager(context);
        member = sessionManager.getMembership();
        Log.e(TAG, "Status member : " + member );
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            final WishlistDetail item = list.get(position);
            Drawable image;
            if (!item.getGambar().equals("http://54.254.194.122/upload/barang/")) {
                Glide.with(context)
                        .asBitmap()
                        .load(item.getGambar())
                        .into(holder.imageBarang);
            } else {
                image = context.getResources().getDrawable(R.drawable.not_found);
                holder.imageBarang.setImageDrawable(image);
            }

            if (item.getName().length() > 0) {
                holder.namaBarang.setText(item.getName());
            }

            if (item.getCodeProduct().length() > 0) {
                holder.kodeBarang.setText(item.getCodeProduct());
            } else {
                holder.kodeBarang.setVisibility(View.GONE);
            }

            Log.e(TAG, "MASUK 1");
            Price harga = item.getPrice();

//            Log.e(TAG, "onBindViewHolder: " + harga.getHarga() );



//            String testHarga = "Rp. " + nf.format(harga.getHarga());
//            holder.hargaBarang.setText(testHarga);



            Log.e(TAG, "MASUK 2");
            int cekStok = item.getQtyStok();
            Log.e(TAG, "onBindViewHolder: " + cekStok );
            Log.e(TAG, "MASUK 3");
            if (cekStok > 0 && cekStok < 10) {
                holder.stokOutlet.setText(" < 10");
            } else if (cekStok >= 10 && cekStok < 25) {
                holder.stokOutlet.setText(" < 25");
            } else if (cekStok >= 25 && cekStok < 50) {
                holder.stokOutlet.setText(" < 50");
            } else if (cekStok >= 50) {
                holder.stokOutlet.setText(" > 50");
            } else {
                holder.stokOutlet.setText("KOSONG");
            }

            Log.e(TAG, "MASUK 5");

            if (holder.stokOutlet.getText().toString().equals("KOSONG")) {
                holder.hargaBarang.setText("? (Harga Belum Diketahui)");
            } else {
                long hargaBarang = 0;
                if (member.equals("REGULER")) {
                    Log.e(TAG, "harga 1" );
                    hargaBarang = harga.getHarga();
                } else if (member.equals("DEBET")) {
                    Log.e(TAG, "harga 2" );
                    hargaBarang = harga.getHargaDua();
                }
                String testHarga = "Rp. " + nf.format(hargaBarang);
                holder.hargaBarang.setText(testHarga);
            }

            Log.e(TAG, "MASUK 6");

            holder.barangItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterCallback.AdapterListWishlistClicked(item);
                }
            });

            holder.hapusWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdapterCallback.AdapterListDelete(item);
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


    public interface AdapterListWishlistCallback {
        void AdapterListWishlistClicked(WishlistDetail position);
    }

    public interface AdapterListWishlistCallbackDelete {
        void AdapterListDelete(WishlistDetail position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageBarang)
        ImageView imageBarang;
        @BindView(R.id.barangItem)
        LinearLayout barangItem;
        @BindView(R.id.namaBarang)
        TextView namaBarang;
        @BindView(R.id.tvKodeBarang)
        TextView kodeBarang;
        @BindView(R.id.hargaBarang)
        TextView hargaBarang;
        @BindView(R.id.textStokOutlet)
        TextView stokOutlet;
        @BindView(R.id.hapusItemWishlist)
        RelativeLayout hapusWishlist;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}