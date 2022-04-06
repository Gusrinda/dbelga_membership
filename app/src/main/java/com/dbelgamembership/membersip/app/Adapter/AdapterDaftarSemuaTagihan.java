package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.DetailTransaksi;
import com.dbelgamembership.membersip.Model.ModelListTagihan.DaftarTagihanPeriode;
import com.dbelgamembership.membersip.Model.ModelListWishlist.Detail;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Transaksi.DaftarTransaksi;
import com.dbelgamembership.membersip.databinding.ItemRiwayatSubBinding;
import com.dbelgamembership.membersip.databinding.ItemTagihanBinding;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterDaftarSemuaTagihan extends RecyclerView.Adapter<AdapterDaftarSemuaTagihan.ViewHolder> {

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private Context context;
    private List<DetailTransaksi> list;
    private AdapterListSemuaTagihan mAdapterCallback;

    public AdapterDaftarSemuaTagihan(Context context, List<DetailTransaksi> list, AdapterListSemuaTagihan mAdapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = mAdapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTagihanBinding itemBinding = ItemTagihanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tanggalTagihan;
        TextView nominalTagihan;
        TextView kodeTagihan;
        TextView lunasTagihan;
        TextView statusTagihan;
        LinearLayout layoutLunas;
        CardView cardTagihan;

        public ViewHolder(ItemTagihanBinding itemView) {
            super(itemView.getRoot());

            tanggalTagihan = itemView.cardBulanTagihan;
            nominalTagihan = itemView.cardNominalTagihan;
            kodeTagihan = itemView.textKodeTransaksi;
            lunasTagihan = itemView.statusLunas;
            statusTagihan = itemView.cardStatusTagihan;
            cardTagihan = itemView.lnContent;
            layoutLunas = itemView.layoutLunas;
        }
    }

    @SuppressLint({"NewApi", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailTransaksi item = list.get(position);

        String dateFromItem = item.getCreatedAt();
        String tanggalAkhir = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar baru = Calendar.getInstance();

        try {
            Date dateFormatted = formatter.parse(dateFromItem);

            String format = "dd MMMM yyyy";
            Locale locale = new Locale("id", "ID");
            SimpleDateFormat sdf = new SimpleDateFormat(format, locale);

            assert dateFormatted != null;
            tanggalAkhir = sdf.format(dateFormatted);

            holder.tanggalTagihan.setText(tanggalAkhir);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.kodeTagihan.setText(item.getPembayaranCode());
        holder.nominalTagihan.setText("Rp. " + nf.format(Double.parseDouble(item.getTotal())));

        if (item.getFlagLunas().equals("true")) {
            holder.lunasTagihan.setText("LUNAS");
            holder.layoutLunas.setBackgroundColor(context.getResources().getColor(R.color.hijauBelga));
            holder.statusTagihan.setText("Tagihan Lunas");
        } else {

            holder.lunasTagihan.setText("BELUM LUNAS");

            if (item.getStatusPayment().equals("WAITING")) {
                holder.layoutLunas.setBackgroundColor(context.getResources().getColor(R.color.material_deep_orange_500));
                holder.statusTagihan.setText("Menunggu konfirmasi admin");
            } else {
                holder.layoutLunas.setBackgroundColor(context.getResources().getColor(R.color.merahBelga));
                holder.statusTagihan.setText("Menunggu pembayaran anda");
            }

        }

        holder.cardTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onRowDetailTransaksi(item);
            }
        });

    }

    public void addItems(List<DetailTransaksi> items) {
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

    public interface AdapterListSemuaTagihan {
        void onRowDetailTransaksi(DetailTransaksi item);
    }

}
