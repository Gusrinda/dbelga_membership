package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
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
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelSearchWish.MsgServer;
import com.dbelgamembership.membersip.Model.ModelSearchWish.Price;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.Katalog.WishlishActivity;

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
    private List<com.dbelgamembership.membersip.Model.ModelSearchWish.MsgServer> list;
    private AdapterListWishlistCallback mAdapterCallback;
    private int result = -1;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterListWishlist(Context context, List<MsgServer> list, AdapterListWishlistCallback adapterCallback) {
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

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            final MsgServer item = list.get(position);
            Drawable image;
            if (!item.getGambar().equals( Http.serverNotApi + "upload/barang/")) {
                Glide.with(context)
                        .asBitmap()
                        .load(item.getGambar())
                        .error(R.drawable.not_found)
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

            Log.e(TAG, "MASUK 2");
            double cekStok = item.getQtyStok();
            Log.e(TAG, "onBindViewHolder: " + cekStok );
            Log.e(TAG, "MASUK 3");

            holder.btnTambahKeranjang.setEnabled(true);
            holder.txtButton.setText("+ Tambah Keranjang");
            holder.btnTambahKeranjang.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.biruBelga)));
            holder.txtButton.setTextColor(context.getColor(R.color.white));

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
                holder.btnTambahKeranjang.setEnabled(false);
                holder.txtButton.setText("Stok Kosong");
                holder.btnTambahKeranjang.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.greyTerang)));
            }

            Log.e(TAG, "MASUK 5");

            holder.txtStokWishlist.setText(String.valueOf(item.getQty()));

            if (holder.stokOutlet.getText().toString().equals("KOSONG")) {
                holder.hargaBarang.setText("? (Harga Belum Diketahui)");
            } else {
                int batasan1 = item.getPrice().getQtyHarga1();
                int batasan2 = item.getPrice().getQtyHarga2();
                int batasan3 = item.getPrice().getQtyHarga3();

                String harga1 = item.getPrice().getHarga();
                String harga2 = item.getPrice().getQtyHarga2() == null ? "0" : item.getPrice().getHargaDua();
                String harga3 = item.getPrice().getQtyHarga3() == null ? "0" : item.getPrice().getHargaTiga();

                double jumlahBarangDibeli = cekStok;
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
                    holder.hargaRealBarang.setVisibility(View.VISIBLE);
                    holder.hargaRealBarang.setText("Rp. "+ nf.format(Double.parseDouble(harga1)));
                    holder.hargaBarang.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
                } else {
                    holder.hargaRealBarang.setVisibility(View.GONE);
                    holder.hargaRealBarang.setText("0");
                    holder.hargaBarang.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
                }

            }

            Log.e(TAG, "MASUK 6");

            holder.hapusWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdapterCallback.AdapterListDelete(item);
                }
            });

            holder.btnTambahKeranjang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdapterCallback.AdapterListTambahKeranjang(item);
                }
            });

            holder.editWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdapterCallback.AdapterEditListWishlist(item);
                }
            });

            for (int i = 0; i < GudangActivity.modelGudangs.size(); i++) {
                if (GudangActivity.modelGudangs.get(i).getIdGudang().equals(String.valueOf(item.getIdGudang()))) {
                        holder.txtNamaToko.setText(GudangActivity.modelGudangs.get(i).getNamaGudang());
                }
            }

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
//        void AdapterListWishlistClicked(MsgServer position);

        void AdapterListDelete(MsgServer position);

        void AdapterListTambahKeranjang(MsgServer position);

        void AdapterEditListWishlist(MsgServer position);

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
        @BindView(R.id.btn_tambahKeranjang)
        LinearLayout btnTambahKeranjang;
        @BindView(R.id.text_Button)
        TextView txtButton;

        @BindView(R.id.hargaRealBarang)
        TextView hargaRealBarang;

        @BindView(R.id.textStokWishlist)
        TextView txtStokWishlist;
        @BindView(R.id.textNamaToko)
        TextView txtNamaToko;


        @BindView(R.id.hapusItemWishlist)
        RelativeLayout hapusWishlist;

        @BindView(R.id.editItemWishlist)
        RelativeLayout editWishlist;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}