package com.dbelgamembership.membersip.Screen.Voucher.Dummy;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.daftarGudangToko;
import static com.dbelgamembership.membersip.Screen.Voucher.VoucherActivity.voucherMember;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ItemListVoucherBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class AdapterListVoucherAvailable extends RecyclerView.Adapter<AdapterListVoucherAvailable.myViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    List<MsgServer> item;
    private Context context;
    private Intent intent;
    private boolean isFromVoucherMember = false;
    private AdapterListVoucherDummyCallback mAdapterCallback;

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterListVoucherAvailable(Context context, List<MsgServer> menuList, boolean isFromVoucherMember, AdapterListVoucherDummyCallback adapterListDaftarSharedOmset) {
        this.context = context;
        this.item = menuList;
        this.isFromVoucherMember = isFromVoucherMember;
        this.mAdapterCallback = adapterListDaftarSharedOmset;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemListVoucherBinding itemBinding = ItemListVoucherBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new myViewHolder(itemBinding);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, @SuppressLint("RecyclerView") final int position) {

        MsgServer dataShareOmset = item.get(position);

        myViewHolder.tipeVoucher.setText(dataShareOmset.getTipe());
        myViewHolder.expVoucher.setText(String.valueOf(dataShareOmset.getExpired()) + " hari");
        myViewHolder.deskripsiVoucher.setText(dataShareOmset.getName());

        if (dataShareOmset.getTipe().equals("DISKON")) {
            myViewHolder.layoutTipeVoucher.setBackgroundColor(context.getResources().getColor(R.color.merahBelga));
        } else if (dataShareOmset.getTipe().equals("ONGKIR")) {
            myViewHolder.layoutTipeVoucher.setBackgroundColor(context.getResources().getColor(R.color.hijauBelga));
        } else {
            myViewHolder.layoutTipeVoucher.setBackgroundColor(context.getResources().getColor(R.color.biruBelga));
        }

        myViewHolder.btnRedeemVoucher.setText("redeem\n" + String.valueOf(dataShareOmset.getKlaim()) + " poin");
        myViewHolder.btnRedeemVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onRowDaftarVoucher(dataShareOmset, position);
            }
        });

        myViewHolder.minimalBelanja.setText("Rp. " + nf.format(Double.parseDouble(dataShareOmset.getMinimalBelanja())));

        if (isFromVoucherMember) {
            myViewHolder.btnRedeemVoucher.setVisibility(View.GONE);
        } else {
            myViewHolder.btnRedeemVoucher.setVisibility(View.VISIBLE);
        }

        StringBuilder namaNamaToko = new StringBuilder();

        namaNamaToko.append("( ");

        for (int i = 0; i < dataShareOmset.getGudang().size(); i++) {

            int posisiTambah1 = i + 1;
            int posisiTerakhir = dataShareOmset.getGudang().size();

            String namaGudang = "-";
            Log.e(TAG, "GUDANG ID : " + dataShareOmset.getGudang().get(i) );

            for (int j = 0; j < daftarGudangToko.size(); j++) {

                Log.e(TAG, "GUDANG ID DAFTAR TOKO : " + daftarGudangToko.get(j).getIdGudang() );


                if (String.valueOf(dataShareOmset.getGudang().get(i)).equals(daftarGudangToko.get(j).getIdGudang())) {
                    namaGudang = daftarGudangToko.get(j).getNamaGudang();
                }

                Log.e(TAG, "NAMA GUDANG :: " + namaGudang );

            }


            if (posisiTambah1 != posisiTerakhir) {
                namaNamaToko.append(namaGudang).append(", ");
            } else {
                namaNamaToko.append(namaGudang);
            }

        }

        namaNamaToko.append(" )");

        myViewHolder.namaNamaToko.setText(namaNamaToko);


    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<MsgServer> items) {
        this.item.addAll(this.item.size(), items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return item.size() > 0 ? item.size() : 0;
    }


//    public void removeItem(List<DummyModelVoucher> items) {
//        items.remove(position);
//        notifyItemRemoved(position);
//    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView tipeVoucher;
        private TextView expVoucher;
        private TextView minimalBelanja;
        private TextView deskripsiVoucher;
        private TextView namaNamaToko;
        private Button btnRedeemVoucher;
        private RelativeLayout layoutTipeVoucher;

        public myViewHolder(ItemListVoucherBinding itemView) {
            super(itemView.getRoot());
            tipeVoucher = itemView.txtTipeDiskon;
            expVoucher = itemView.txtExpDiskon;
            minimalBelanja = itemView.txtMinimalBelanja;
            deskripsiVoucher = itemView.txtDeskripsiVoucher;
            namaNamaToko = itemView.txtTokoVoucher;
            btnRedeemVoucher = itemView.btnRedeem;
            layoutTipeVoucher = itemView.layoutTipeVoucher;

        }
    }

    public interface AdapterListVoucherDummyCallback {
        void onRowDaftarVoucher(MsgServer item, int posisi);
    }

}
