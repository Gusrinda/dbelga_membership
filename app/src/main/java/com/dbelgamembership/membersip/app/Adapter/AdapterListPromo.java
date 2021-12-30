package com.dbelgamembership.membersip.app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Model.DummyPromo;
import com.dbelgamembership.membersip.Screen.User.ListVoucher;
import com.dbelgamembership.membersip.databinding.ItemCardPromoBinding;
import com.dbelgamembership.membersip.databinding.ItemCartBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListPromo extends
        RecyclerView.Adapter<AdapterListPromo.ViewHolder> {

    private static final String TAG = AdapterListPromo.class.getSimpleName();

    private Context context;
    private List<DummyPromo> list;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
    AdapterListPromo.AdapterListPromoCallback adapterListPromoCallback;

    public AdapterListPromo(Context context, List<DummyPromo> list, AdapterListPromoCallback adapterListPromoCallback) {
        this.context = context;
        this.list = list;
        this.adapterListPromoCallback = adapterListPromoCallback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCardPromoBinding itemBinding = ItemCardPromoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterListPromo.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        DummyPromo dataDummy = list.get(position);

        holder.gambarPromo.setImageResource(dataDummy.getAlamatGambar());

        if (dataDummy.isBaru()) {
            holder.isNew.setVisibility(View.VISIBLE);
        }
        holder.tipePromo.setText(dataDummy.getTipePromo());
        holder.berlaku.setText(dataDummy.getTanggalBerlaku());
        holder.cardPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListPromoCallback.AdapterListPromoClicked(dataDummy);
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
        void AdapterListPromoClicked(DummyPromo position);
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