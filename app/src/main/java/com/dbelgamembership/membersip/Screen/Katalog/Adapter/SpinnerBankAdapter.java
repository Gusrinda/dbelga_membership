package com.dbelgamembership.membersip.Screen.Katalog.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dbelgamembership.membersip.R;

public class SpinnerBankAdapter extends BaseAdapter {

    Context context;
    int logoBanks[];
    String[] namaBanks;
    LayoutInflater inflter;

    public SpinnerBankAdapter(Context context, int[] logoBanks, String[] namaBanks) {
        this.context = context;
        this.logoBanks = logoBanks;
        this.namaBanks = namaBanks;
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return logoBanks.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_va_bank, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imgLogoBank);
        TextView names = (TextView) view.findViewById(R.id.textNamaBank);
        icon.setImageResource(logoBanks[i]);
        names.setText(namaBanks[i]);
        return view;
    }
}
