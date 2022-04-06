package com.dbelgamembership.membersip.app.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.DetailTransaksi;
import com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan.DaftarPelunasan;
import com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan.DaftarTransaksi;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Limit.DaftarTagihan;
import com.dbelgamembership.membersip.Screen.Limit.RiwayatTagihan;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintFakturActivity;
import com.dbelgamembership.membersip.databinding.ItemRiwayatBinding;
import com.dbelgamembership.membersip.databinding.ItemTagihanBinding;
import com.developer.kalert.KAlertDialog;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterRiwayatPelunasanTagihan extends RecyclerView.Adapter<AdapterRiwayatPelunasanTagihan.ViewHolder> implements AdapterDetailPelunasanTagihan.AdapterListTransactionCallback {

    private final String TAG = this.getClass().getSimpleName();
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private Context context;
    private List<DaftarPelunasan> list;
    private AdapterRiwayatPelunasan mAdapterCallback;

    private List<Boolean> daftarIsUp = new ArrayList<>();

    public AdapterRiwayatPelunasanTagihan(Context context, List<DaftarPelunasan> list, AdapterRiwayatPelunasan mAdapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = mAdapterCallback;

        for (int i = 0; i < list.size(); i++) {
            this.daftarIsUp.add(i, false);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRiwayatBinding itemBinding = ItemRiwayatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onRowDetailTransaksi(DaftarTransaksi item) {
        new KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                .setTitleText("Lihat Transaksi")
                .setContentText("Anda ingin melihat detail transaksi " + item.getCodePembayaran() + " ?\n\n")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.material_deep_orange_600, context)
                .cancelButtonColor(R.color.merahBelga, context)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(context, PrintFakturActivity.class);
                        String DataOOS = item.getCodePembayaran();
                        Log.e(TAG, "onRowAdapterListTransactionClicked: " + DataOOS);
                        intent.putExtra("DATAPRINT", DataOOS);
                        intent.putExtra("FAKTUR", true);
                        context.startActivity(intent);
                    }
                })
                .setCancelText("Tidak")
                .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        kAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tanggalTagihan;
        TextView nominalTagihan;
        TextView kodeTagihan;
        TextView statusTagihan;

        LinearLayout layoutBtnShow;
        RecyclerView rvDaftarTransaksi;

        CardView lnContent;

        public ViewHolder(ItemRiwayatBinding itemView) {
            super(itemView.getRoot());

            tanggalTagihan = itemView.cardBulanTagihan;
            nominalTagihan = itemView.cardNominalTagihan;
            kodeTagihan = itemView.textKodeTransaksi;
            statusTagihan = itemView.cardStatusTagihan;
            layoutBtnShow = itemView.btnShowDetail;
            rvDaftarTransaksi = itemView.rvRiwayatUtama;
            lnContent = itemView.lnContent;
        }
    }

    @SuppressLint({"NewApi", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DaftarPelunasan item = list.get(position);

        String dateFromItem = item.getPaymentDate();
        String tanggalAkhir = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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

        holder.kodeTagihan.setText(item.getCodePelunasan());
        holder.nominalTagihan.setText("Rp. " + nf.format(Double.parseDouble(item.getTotalPelunasan())));

        if (item.getStatusPelunasan().equals("lunas")) {
            holder.statusTagihan.setText("Tagihan selesai");
        } else {
            holder.statusTagihan.setText("Tagihan belum selesai");
        }

        holder.layoutBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean isVisible = daftarIsUp.get(position);
                Log.d(TAG, "onClick: " + isVisible);
                if (isVisible) {
                    holder.rvDaftarTransaksi.setVisibility(View.GONE);
                } else {
                    holder.rvDaftarTransaksi.setVisibility(View.VISIBLE);
                }

                isVisible = !isVisible;

                daftarIsUp.set(position, isVisible);

            }
        });

        List<DaftarTransaksi> daftarTransaksis = item.getDaftarTransaksi();
        AdapterDetailPelunasanTagihan adapterDetailPelunasanTagihan = new AdapterDetailPelunasanTagihan(context, daftarTransaksis, AdapterRiwayatPelunasanTagihan.this);
        holder.rvDaftarTransaksi.setAdapter(adapterDetailPelunasanTagihan);

    }


    public void addItems(List<DaftarPelunasan> items) {
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

    public interface AdapterRiwayatPelunasan {
        void onRowRiwayatPelunasan(DaftarPelunasan item);
    }

}
