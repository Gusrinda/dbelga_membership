package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.dbelgamembership.membersip.Presentation.Limit.BayarTagihan;
import com.dbelgamembership.membersip.Presentation.Limit.DaftarTagihan;
import com.dbelgamembership.membersip.databinding.ActivityLimitPlafonBinding;

public class LimitPlafon extends AppCompatActivity {

    private ActivityLimitPlafonBinding limitPlafonBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        limitPlafonBinding = ActivityLimitPlafonBinding.inflate(getLayoutInflater());
        View view = limitPlafonBinding.getRoot();
        setContentView(view);

        limitPlafonBinding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        limitPlafonBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int X = (int) (Math.random() * 100);

        limitPlafonBinding.txtPersentasePenggunaan.setText(String.valueOf(X) + "%");

        limitPlafonBinding.progressHorizontal.setProgress(X);

        if (X < 30) {
            limitPlafonBinding.progressHorizontal.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

        limitPlafonBinding.btnTagihanBulanIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LimitPlafon.this, BayarTagihan.class);
                startActivity(intent);
            }
        });

        limitPlafonBinding.btnDaftarTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LimitPlafon.this, DaftarTagihan.class);
                startActivity(intent);
            }
        });

        limitPlafonBinding.btnRiwayatLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LimitPlafon.this, DaftarTagihan.class);
                startActivity(intent);
            }
        });



    }
}