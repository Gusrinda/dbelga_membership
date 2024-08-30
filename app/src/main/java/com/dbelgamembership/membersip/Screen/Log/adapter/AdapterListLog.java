package com.dbelgamembership.membersip.Screen.Log.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Log.model.LogModel;
import com.dbelgamembership.membersip.databinding.ItemLogHistoryBinding;
import com.dbelgamembership.membersip.databinding.ItemPilihanMenuBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AdapterListLog extends
        RecyclerView.Adapter<AdapterListLog.ViewHolder> {

    private static final String TAG = AdapterListLog.class.getSimpleName();

    private Context context;
    private List<LogModel> list;

    public AdapterListLog(Context context, List<LogModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLogHistoryBinding itemBinding = ItemLogHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        LogModel log = list.get(position);

        holder.deskripsiLog.setText(log.getTextLog());
        holder.typeLog.setText(log.getTypeLog());

        String time = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(log.getDateLog());

        holder.tanggalLog.setText(time);

        if (log.getTypeLog().equals("AKUN")) {
            holder.layoutTypeLog.setBackgroundColor(context.getResources().getColor(R.color.merahBelga));
        } else if (log.getTypeLog().equals("WISHLIST")) {
            holder.layoutTypeLog.setBackgroundColor(context.getResources().getColor(R.color.orange));
        } else if (log.getTypeLog().equals("CART")) {
            holder.layoutTypeLog.setBackgroundColor(context.getResources().getColor(R.color.biruBelga));
        } else {
            holder.layoutTypeLog.setBackgroundColor(context.getResources().getColor(R.color.green3));
        }



    }


    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }


    public interface AdapterListMenuCallback {
        void AdapterListMenu(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tanggalLog;
        TextView deskripsiLog;
        TextView typeLog;
        LinearLayout layoutTypeLog;


        public ViewHolder(ItemLogHistoryBinding binding) {
            super(binding.getRoot());
            tanggalLog = binding.txtTanggalLog;
            deskripsiLog = binding.txtDeskripsiLog;
            typeLog = binding.txtTypeLog;
            layoutTypeLog = binding.layoutTypeLog;
        }
    }
}