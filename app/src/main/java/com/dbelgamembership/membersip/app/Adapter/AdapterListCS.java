package com.dbelgamembership.membersip.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelResponseCS.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ItemCsBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListCS extends
        RecyclerView.Adapter<AdapterListCS.ViewHolder> {

    private static final String TAG = AdapterListCS.class.getSimpleName();

    private Context context;
    private List<MsgServer> list;
    private AdapterListCSCallback mAdapterCallback;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AdapterListCS(Context context, List<MsgServer> list, AdapterListCSCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCsBinding itemBinding = ItemCsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        MsgServer detailCS = list.get(position);

        holder.namaCS.setText(detailCS.getName());
        holder.kontakCS.setText(detailCS.getContact());


        if (detailCS.getType().equals("EMAIL")) {
            holder.icon.setImageResource(R.drawable.ic_baseline_email_24);
        } else {
            holder.icon.setImageResource(R.drawable.whatsapp);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onClickCallback(detailCS);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }


    public interface AdapterListCSCallback {
        void onClickCallback(MsgServer detailItemCart);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView namaCS;
        TextView kontakCS;
        CardView layout;


        public ViewHolder(ItemCsBinding binding) {
            super(binding.getRoot());
            icon = binding.icon;
            namaCS = binding.txtNamaCS;
            kontakCS = binding.txtKontakCS;
            layout = binding.layout;
        }
    }
}