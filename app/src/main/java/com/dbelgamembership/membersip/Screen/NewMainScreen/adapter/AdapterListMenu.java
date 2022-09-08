package com.dbelgamembership.membersip.Screen.NewMainScreen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.Model.ModelToko.ModelGudang;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ItemGudangBinding;
import com.dbelgamembership.membersip.databinding.ItemPilihanMenuBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AdapterListMenu extends
        RecyclerView.Adapter<AdapterListMenu.ViewHolder> {

    private static final String TAG = AdapterListMenu.class.getSimpleName();

    private Context context;
    private ArrayList<HashMap<String, Object>> list;
    private AdapterListMenuCallback mAdapterCallback;

    public AdapterListMenu(Context context, ArrayList<HashMap<String, Object>> list, AdapterListMenuCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPilihanMenuBinding itemBinding = ItemPilihanMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        HashMap<String, Object> menu = list.get(position);

        holder.namaMenu.setText(Objects.requireNonNull(menu.get("nama")).toString());
        holder.iconMenu.setImageResource((Integer) menu.get("img"));

        boolean isSelected = (boolean) menu.get("isSelected");

        Log.e(TAG, "onBindViewHolder:  MENU :: " + isSelected);

        if (isSelected) {
            holder.namaMenu.setTextColor(Color.BLACK);
            holder.layoutParent.setBackgroundResource(R.drawable.rounded_selected);
        } else  {
            holder.namaMenu.setTextColor(Color.GRAY);
            holder.layoutParent.setBackgroundResource(R.drawable.rounded_not_selected);
        }

        holder.layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.AdapterListMenu((Integer) menu.get("id"));
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }


    public interface AdapterListMenuCallback {
        void AdapterListMenu(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutParent;
        TextView namaMenu;
        ImageView iconMenu;


        public ViewHolder(ItemPilihanMenuBinding binding) {
            super(binding.getRoot());
            layoutParent = binding.layoutParent;
            iconMenu = binding.imgIcon;
            namaMenu = binding.txtNamaMenu;
        }
    }
}