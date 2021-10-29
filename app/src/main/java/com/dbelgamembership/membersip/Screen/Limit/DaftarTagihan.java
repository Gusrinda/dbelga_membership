package com.dbelgamembership.membersip.Screen.Limit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ActivityDaftarTagihanBinding;

public class DaftarTagihan extends AppCompatActivity {


    private ActivityDaftarTagihanBinding daftarTagihanBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daftarTagihanBinding = ActivityDaftarTagihanBinding.inflate(getLayoutInflater());
        View view = daftarTagihanBinding.getRoot();
        setContentView(view);

        daftarTagihanBinding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        daftarTagihanBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}