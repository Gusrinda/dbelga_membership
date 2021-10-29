package com.dbelgamembership.membersip.app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelVoucherCustomer.DaftarVoucher;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.User.VoucherMember;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterVoucherMember extends
        RecyclerView.Adapter<AdapterVoucherMember.ViewHolder> {

    private static final String TAG = AdapterVoucherMember.class.getSimpleName();

    private Context context;
    private List<DaftarVoucher> list;
    private VoucherMember mAdapterCallback;
    private int result = -1;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterVoucherMember(Context context, List<DaftarVoucher> list, VoucherMember adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_voucher,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            final DaftarVoucher item = list.get(position);
            holder.btnKlaimVoucher.setVisibility(View.GONE);

            holder.tvNamaVoucher.setText(item.getName());
            holder.tvDeskripsiVoucher.setText(item.getDeskripsi());
            holder.tvExpiredVoucher.setText("Expired : " + item.getExpiredDate());
            holder.tvTipeVoucher.setText(item.getTipe());

            holder.layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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


    public interface AdapterListBarangCallback {
        void AdapterListBarangClicked(DaftarVoucher position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.namaVoucher)
        TextView tvNamaVoucher;
        @BindView(R.id.tanggalVoucher)
        TextView tvExpiredVoucher;
        @BindView(R.id.typeVoucher)
        TextView tvTipeVoucher;
        @BindView(R.id.detailVoucher)
        TextView tvDeskripsiVoucher;
        @BindView(R.id.btn_AmbilVoucher)
        Button btnKlaimVoucher;
        @BindView(R.id.layoutMain)
        LinearLayout layoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}