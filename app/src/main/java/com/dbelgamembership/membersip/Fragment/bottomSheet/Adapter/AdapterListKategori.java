package com.dbelgamembership.membersip.Fragment.bottomSheet.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.dbelgamembership.membersip.R;

import static com.dbelgamembership.membersip.Fragment.bottomSheet.BottomSheetFilterFragment.katekategori;
//import static com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity.kategoriDipilih;

import java.util.List;
import java.util.Random;


public class AdapterListKategori extends RecyclerView.Adapter<AdapterListKategori.ViewHolder> {

    private static final String TAG = "Adpater";
    private List<String> mData;
    private LayoutInflater mInflater;

    private AdapterListKategori.AdapterListKategoriCallback mAdapterCallback;

    public static int selected_position = 0;



    // data is passed into the constructor
    public AdapterListKategori(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
//        this.mAdapterCallback = mAdapterCallback;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_kategori, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.namaKategori.setText(mData.get(position));
        holder.lnContent.setCardBackgroundColor(selected_position == position ? Color.GREEN : Color.WHITE);
        holder.lnContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick Adapter : " + holder.namaKategori.getText().toString() );
                if (position == RecyclerView.NO_POSITION) return;
                // Updating old as well as new positions
                notifyItemChanged(selected_position);
                selected_position = position;
                katekategori = mData.get(position);
                Log.e(TAG, "onClick: " + katekategori );
                notifyItemChanged(selected_position);
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaKategori;
        CardView lnContent;


        ViewHolder(View itemView) {
            super(itemView);
            namaKategori = itemView.findViewById(R.id.namaKategori);
            lnContent = itemView.findViewById(R.id.lnContent);
//            itemView.setOnClickListener(this);

        }
//
//        @Override
//        public void onClick(View view) {
//            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
//            // Updating old as well as new positions
//            notifyItemChanged(selected_position);
//            selected_position = getAdapterPosition();
//            kategoriDipilih = mData.get(getAdapterPosition());
//            katekategori = kategoriDipilih;
//            Log.e(TAG, "onClick: " + kategoriDipilih );
//            Log.e(TAG, "onClick: " + katekategori );
//            Log.e(TAG, "onClick: " + selected_position );
//            notifyItemChanged(selected_position);
//        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }


    public interface AdapterListKategoriCallback {
        void onRowAdapterListKategoriClicked(int position);
    }

}



