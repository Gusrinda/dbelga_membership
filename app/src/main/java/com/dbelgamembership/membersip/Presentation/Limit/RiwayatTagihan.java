package com.dbelgamembership.membersip.Presentation.Limit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ActivityRiwayatTagihanBinding;

public class RiwayatTagihan extends AppCompatActivity {

    private ActivityRiwayatTagihanBinding riwayatTagihanBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        riwayatTagihanBinding = ActivityRiwayatTagihanBinding.inflate(getLayoutInflater());
        View view = riwayatTagihanBinding.getRoot();
        setContentView(view);

        riwayatTagihanBinding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        riwayatTagihanBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}