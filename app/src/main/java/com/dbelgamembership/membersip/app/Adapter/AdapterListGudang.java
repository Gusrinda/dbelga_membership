package com.dbelgamembership.membersip.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelToko.ModelGudang;
import com.dbelgamembership.membersip.databinding.ItemGudangBinding;

import java.util.List;

public class AdapterListGudang extends
        RecyclerView.Adapter<AdapterListGudang.ViewHolder> {

    private static final String TAG = AdapterListGudang.class.getSimpleName();

    private Context context;
    private List<ModelGudang> list;
    private AdapterListGudangCallback mAdapterCallback;

    public AdapterListGudang(Context context, List<ModelGudang> list, AdapterListGudangCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGudangBinding itemBinding = ItemGudangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ModelGudang modelGudang = list.get(position);

        holder.namaGudang.setText(modelGudang.getNamaGudang());
        holder.alamatGudang.setText(modelGudang.getAlamatGudang());
        holder.jarakGudang.setText("± " + modelGudang.getTextJarak());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mAdapterCallback.AdapterListGudang(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }


    public interface AdapterListGudangCallback {
        void AdapterListGudang(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaGudang;
        TextView alamatGudang;
        TextView jarakGudang;
        ConstraintLayout constraintLayout;


        public ViewHolder(ItemGudangBinding binding) {
            super(binding.getRoot());
            constraintLayout = binding.layout;
            namaGudang = binding.namaGudang;
            alamatGudang = binding.alamatGudang;
            jarakGudang = binding.txtJarak;
        }
    }
}