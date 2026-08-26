package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelListTagihan.DaftarTagihanPeriode;
import com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan.DaftarTransaksi;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ItemRiwayatSubBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterDetailPelunasanTagihan extends RecyclerView.Adapter<AdapterDetailPelunasanTagihan.ViewHolder> {

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private Context context;
    private List<DaftarTransaksi> list;
    private AdapterDetailPelunasanTagihan.AdapterListTransactionCallback mAdapterCallback;

    public AdapterDetailPelunasanTagihan(Context context, List<DaftarTransaksi> list, AdapterDetailPelunasanTagihan.AdapterListTransactionCallback mAdapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = mAdapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRiwayatSubBinding itemBinding = ItemRiwayatSubBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView kodeTransaksi;
        TextView nominalTransaksi;
        TextView tanggalTransaksi;
        TextView dendaTransaksi;
        CardView cardPelunasan;
        RelativeLayout relativeCardPelunasan;
        ImageView iconPelunasan;
        TextView judulPelunasan;
        CardView cardDetailTransaksi;

        public ViewHolder(ItemRiwayatSubBinding itemView) {
            super(itemView.getRoot());

            kodeTransaksi = itemView.txtNomorOrder;
            nominalTransaksi = itemView.txtTotalOrder;
            dendaTransaksi = itemView.txtTotalDenda;
            tanggalTransaksi = itemView.txtTanggalOrder;
            cardPelunasan = itemView.cardLunasi;
            relativeCardPelunasan = itemView.relativeCardPelunasan;
            iconPelunasan = itemView.iconLunasi;
            judulPelunasan = itemView.txtJudulLunasi;
            cardDetailTransaksi = itemView.cardInformasi;

        }

    }

    @SuppressLint({"NewApi", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DaftarTransaksi item = list.get(position);

        holder.kodeTransaksi.setText(item.getCodePembayaran());
        holder.nominalTransaksi.setText("Rp. " + nf.format(Double.parseDouble(item.getTotalTagihan())));

        Double denda = Double.parseDouble(item.getTotalDenda());

        if (denda > 0) {
            holder.dendaTransaksi.setVisibility(View.VISIBLE);
            holder.dendaTransaksi.setText("+ Rp. " + nf.format(denda) + " ( Denda )");
        } else {
            holder.dendaTransaksi.setVisibility(View.GONE);
        }

        holder.tanggalTransaksi.setVisibility(View.GONE);
        holder.cardDetailTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onRowDetailTransaksi(item);
            }
        });

    }

    public void addItems(List<DaftarTransaksi> items) {
        this.list.addAll(this.list.size(), items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    public void clear() {
        int size = this.list.size();
        this.list.clear();
        notifyItemRangeRemoved(0, size);
    }

    public interface AdapterListTransactionCallback {
        void onRowDetailTransaksi(DaftarTransaksi item);
    }

}