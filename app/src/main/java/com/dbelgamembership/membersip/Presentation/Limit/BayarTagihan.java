package com.dbelgamembership.membersip.Presentation.Limit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ActivityBayarTagihanBinding;

public class BayarTagihan extends AppCompatActivity {

    private ActivityBayarTagihanBinding bayarTagihanBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bayarTagihanBinding = ActivityBayarTagihanBinding.inflate(getLayoutInflater());
        View view = bayarTagihanBinding.getRoot();
        setContentView(view);

        bayarTagihanBinding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        bayarTagihanBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}