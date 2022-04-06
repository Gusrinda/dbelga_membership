package com.dbelgamembership.membersip.app.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Model.DummyPromo;
import com.dbelgamembership.membersip.Screen.User.ListVoucher;
import com.dbelgamembership.membersip.databinding.ItemCardPromoBinding;
import com.dbelgamembership.membersip.databinding.ItemCartBinding;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListPromo extends
        RecyclerView.Adapter<AdapterListPromo.ViewHolder> {

    private static final String TAG = AdapterListPromo.class.getSimpleName();

    private Context context;
    private List<Datum> list;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
    AdapterListPromo.AdapterListPromoCallback adapterListPromoCallback;

    public AdapterListPromo(Context context, List<Datum> list, AdapterListPromoCallback adapterListPromoCallback) {
        this.context = context;
        this.list = list;
        this.adapterListPromoCallback = adapterListPromoCallback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCardPromoBinding itemBinding = ItemCardPromoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterListPromo.ViewHolder(itemBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Datum dataPromo = list.get(position);

        Glide.with(context)
                .asBitmap()
                .load(dataPromo.getImage())
                .error(R.drawable.promo_banner_belga)
                .into(holder.gambarPromo);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        boolean isNew = false;

        final Calendar baru = Calendar.getInstance();
        try {
            Date tanggalNow = baru.getTime();
            Date tanggalCreated = formatter.parse(dataPromo.getCreatedAt());

            long millisecondsDateNow = tanggalNow.getTime();
            long millisecondsDateLast = tanggalCreated.getTime();

            long count = millisecondsDateNow - millisecondsDateLast;

            long days = count / (24 * 60 * 60 * 1000);

            if (days <= 3) {
                isNew = true;
            }

            Log.e(TAG, "onBindViewHolder: " + position + " :: " + isNew );

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (isNew) {
            holder.isNew.setVisibility(View.VISIBLE);
        } else {
            holder.isNew.setVisibility(View.GONE);
        }

        holder.tipePromo.setText(dataPromo.getKeterangan());
        holder.berlaku.setText(dataPromo.getDateEnd().substring(0,10));
        holder.cardPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListPromoCallback.AdapterListPromoClicked(dataPromo);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }


    public interface AdapterListPromoCallback {
        void AdapterListPromoClicked(Datum data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView berlaku;
        TextView tipePromo;
        LinearLayout isNew;
        ImageView gambarPromo;
        CardView cardPromo;

        public ViewHolder(ItemCardPromoBinding binding) {
            super(binding.getRoot());
            berlaku = binding.txtAkhirPeriode;
            tipePromo = binding.txtTipePromo;
            isNew = binding.layoutNew;
            gambarPromo = binding.gambarBanner;
            cardPromo = binding.cardPromo;
        }
    }
}