package com.dbelgamembership.membersip.Screen.Voucher.Dummy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ItemListVoucherBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class DummyAdapterListVoucherOmset extends RecyclerView.Adapter<DummyAdapterListVoucherOmset.myViewHolder> {

    List<DummyModelVoucher> item;
    private Context context;
    private Intent intent;
    private boolean isFromVoucherMember = false;
    private AdapterListVoucherDummyCallback mAdapterCallback;

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public DummyAdapterListVoucherOmset(Context context, List<DummyModelVoucher> menuList, boolean isFromVoucherMember, AdapterListVoucherDummyCallback adapterListDaftarSharedOmset) {
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

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, final int position) {


        DummyModelVoucher dataShareOmset = item.get(position);

        myViewHolder.tipeVoucher.setText(dataShareOmset.getTipeVoucher());
        myViewHolder.expVoucher.setText(dataShareOmset.getExpVoucher());
        myViewHolder.deskripsiVoucher.setText(dataShareOmset.getDeskripsiVoucher());

        if (dataShareOmset.getTipeVoucher().equals("diskon")) {
            myViewHolder.layoutTipeVoucher.setBackgroundColor(context.getResources().getColor(R.color.merahBelga));
        } else if (dataShareOmset.getTipeVoucher().equals("ongkir")){
            myViewHolder.layoutTipeVoucher.setBackgroundColor(context.getResources().getColor(R.color.hijauBelga));
        } else {
            myViewHolder.layoutTipeVoucher.setBackgroundColor(context.getResources().getColor(R.color.biruBelga));
        }

        myViewHolder.btnRedeemVoucher.setText("redeem\n" + String.valueOf(dataShareOmset.getPoinVoicher()) + " poin");
        myViewHolder.btnRedeemVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onRowDaftarVoucherDummy(dataShareOmset, position );
            }
        });

        if (isFromVoucherMember) {
            myViewHolder.btnRedeemVoucher.setVisibility(View.GONE);
        } else {
            myViewHolder.btnRedeemVoucher.setVisibility(View.VISIBLE);
        }


    }
    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<DummyModelVoucher> items) {
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
        private TextView deskripsiVoucher;
        private Button btnRedeemVoucher;
        private RelativeLayout layoutTipeVoucher;

        public myViewHolder(ItemListVoucherBinding itemView) {
            super(itemView.getRoot());
            tipeVoucher = itemView.txtTipeDiskon;
            expVoucher = itemView.txtExpDiskon;
            deskripsiVoucher = itemView.txtDeskripsiVoucher;
            btnRedeemVoucher = itemView.btnRedeem;
            layoutTipeVoucher = itemView.layoutTipeVoucher;

        }
    }

    public interface AdapterListVoucherDummyCallback {
        void onRowDaftarVoucherDummy(DummyModelVoucher item, int posisi);
    }

}
