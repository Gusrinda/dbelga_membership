package com.dbelgamembership.membersip.app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Screen.User.ListVoucher;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer;
import com.dbelgamembership.membersip.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListVoucher extends
        RecyclerView.Adapter<AdapterListVoucher.ViewHolder> {

    private static final String TAG = AdapterListVoucher.class.getSimpleName();

    private Context context;
    private List<MsgServer> list;
    private ListVoucher mAdapterCallback;
    private int result = -1;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterListVoucher(Context context, List<MsgServer> list, ListVoucher adapterCallback) {
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
            final MsgServer item = list.get(position);

            holder.tvNamaVoucher.setText(item.getName());
            holder.tvDeskripsiVoucher.setText(item.getDeskripsi());
            holder.tvExpiredVoucher.setText("Expired : " + item.getExpired() + " hari");
            holder.tvTipeVoucher.setText(item.getTipe());
            holder.btnKlaimVoucher.setText("KLAIM VOUCHER [ " + item.getKlaim() +" poin ]");
            holder.btnKlaimVoucher.setOnClickListener(new View.OnClickListener() {
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
        void AdapterListBarangClicked(MsgServer position);
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}