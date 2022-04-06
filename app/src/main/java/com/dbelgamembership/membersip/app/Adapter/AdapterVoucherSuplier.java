package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Screen.Katalog.Model.modelArrayVoucherSuplierBelanja;
import com.dbelgamembership.membersip.databinding.ItemListVoucherSuplierBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterVoucherSuplier extends
        RecyclerView.Adapter<AdapterVoucherSuplier.ViewHolder> {

    private static final String TAG = AdapterVoucherSuplier.class.getSimpleName();

    private Context context;
    private List<modelArrayVoucherSuplierBelanja> list;
    private AdapterVoucherSuplierCallback mAdapterCallback;
    private int result = -1;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterVoucherSuplier(Context context, List<modelArrayVoucherSuplierBelanja> list, AdapterVoucherSuplierCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListVoucherSuplierBinding itemBinding = ItemListVoucherSuplierBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder myViewHolder, @SuppressLint("RecyclerView") final int position) {
        modelArrayVoucherSuplierBelanja dataVoucher = list.get(position);

        myViewHolder.kodeVoucher.setText(dataVoucher.getKodeVoucher());
        myViewHolder.minimalVoucher.setText("Rp. " + nf.format(dataVoucher.getMinimalVoucher()));
        myViewHolder.potonganVoucher.setText("Rp. " + nf.format(dataVoucher.getPotonganBelanjaSuplier()));

        myViewHolder.btnAmbilVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onRowDaftarVoucher(dataVoucher, position);
            }
        });
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


    public interface AdapterVoucherSuplierCallback {
        void onRowDaftarVoucher(modelArrayVoucherSuplierBelanja item, int posisi);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView kodeVoucher;
        private TextView potonganVoucher;
        private TextView minimalVoucher;
        private Button btnAmbilVoucher;

        public ViewHolder(ItemListVoucherSuplierBinding itemView) {
            super(itemView.getRoot());
            kodeVoucher = itemView.txtKodeVoucher;
            potonganVoucher = itemView.txtNominalPotongan;
            minimalVoucher = itemView.txtMinimalBelanja;
            btnAmbilVoucher = itemView.btnRedeem;
        }
    }
}