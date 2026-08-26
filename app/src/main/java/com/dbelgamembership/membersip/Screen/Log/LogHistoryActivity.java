package com.dbelgamembership.membersip.Screen.Log;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Log.adapter.AdapterListLog;
import com.dbelgamembership.membersip.Screen.Log.model.LogModel;
import com.dbelgamembership.membersip.databinding.ActivityLogHistoryBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LogHistoryActivity extends AppCompatActivity {

    private ActivityLogHistoryBinding binding;
    private SessionManager sessionManager;

    private List<LogModel> daftarLog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogHistoryBinding.inflate(getLayoutInflater());
        sessionManager = new SessionManager(this);

        daftarLog.clear();
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        setupListLog();

    }

    private void setupListLog() {

        daftarLog = sessionManager.getDaftarLogHistory();

        daftarLog.sort(Comparator.comparing(LogModel::getDateLog).reversed());

        AdapterListLog adapterListLog = new AdapterListLog(LogHistoryActivity.this, daftarLog);
        binding.rvLog.setAdapter(adapterListLog);


    }

}