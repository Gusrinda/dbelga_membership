package com.dbelgamembership.membersip.Screen.Transaksi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ActivityWebViewPembayaranBinding;

public class WebViewPembayaranActivity extends AppCompatActivity {

    private ActivityWebViewPembayaranBinding binding;
    private SessionManager sessionManager;
    private String urlAwal = "";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewPembayaranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().hasExtra("url")) {
            urlAwal = getIntent().getStringExtra("url");
            WebSettings webSettings = binding.webviewPembayaran.getSettings();
            webSettings.setJavaScriptEnabled(true);
            binding.webviewPembayaran.loadUrl(urlAwal);
        } else {
            finish();
        }

    }
}