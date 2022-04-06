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
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Limit.Dummy.ModelItemBayarTagihan;
import com.dbelgamembership.membersip.databinding.ItemRiwayatSubBinding;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListTagihan extends RecyclerView.Adapter<AdapterListTagihan.ViewHolder> {

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private Context context;
    private List<DaftarTagihanPeriode> list;
    private AdapterListTagihan.AdapterListTransactionCallback mAdapterCallback;

    public AdapterListTagihan(Context context, List<DaftarTagihanPeriode> list, AdapterListTagihan.AdapterListTransactionCallback mAdapterCallback) {
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
        DaftarTagihanPeriode item = list.get(position);

        holder.dendaTransaksi.setVisibility(View.GONE);
        holder.kodeTransaksi.setText(item.getPembayaranCode());
        holder.nominalTransaksi.setText("Rp. " + nf.format(Double.parseDouble(item.getTotal())));
        holder.tanggalTransaksi.setText(item.getCreatedAt());

        if (item.getFlagLunas().equals("true")){
            holder.relativeCardPelunasan.setBackgroundColor(context.getResources().getColor(R.color.hijauBelga));
            holder.iconPelunasan.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_24));
            holder.judulPelunasan.setText("Lunas");
            holder.nominalTransaksi.setTextColor(R.color.hijauBelga);
        } else {
            holder.relativeCardPelunasan.setBackgroundColor(context.getResources().getColor(R.color.merahBelga));
            holder.iconPelunasan.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_money));
            holder.judulPelunasan.setText("Lunasi ?");
            holder.nominalTransaksi.setTextColor(R.color.merahBelga);
        }

        holder.cardDetailTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onRowDetailTransaksi(item);
            }
        });

        holder.cardPelunasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!item.getFlagLunas().equals("true")){
                    mAdapterCallback.onRowLunasi(item);
                }
            }
        });

    }

    public void addItems(List<DaftarTagihanPeriode> items) {
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
        void onRowLunasi(DaftarTagihanPeriode item);

        void onRowDetailTransaksi(DaftarTagihanPeriode item);
    }

}
