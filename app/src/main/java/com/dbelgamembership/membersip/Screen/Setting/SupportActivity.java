package com.dbelgamembership.membersip.Screen.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.app.Adapter.AdapterListCS;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelResponseCS.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.databinding.ActivitySupportBinding;

import java.util.List;

public class SupportActivity extends AppCompatActivity implements AdapterListCS.AdapterListCSCallback {

    private static final String TAG = "SupportAct";
    private ActivitySupportBinding binding;
    private SessionManager sessionManager;
    private List<MsgServer> daftarCS = SplashActivity.daftarCS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        binding.btnWaAdmin1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String mobileNumber = binding.nomorWaAdmin1.getText().toString();
//                String message = "Halo saya adalah Member Belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName();
//                boolean installed = appInstalledOrNot("com.whatsapp");
//                if (installed) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mobileNumber + "&text=" + message));
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(SupportActivity.this, "Whatsapp not installed on your device", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        binding.btnWaAdmin2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String mobileNumber = binding.nomorWaAdmin2.getText().toString();
//                String message = "Halo saya adalah Member Belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName();
//                boolean installed = appInstalledOrNot("com.whatsapp");
//                if (installed) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mobileNumber + "&text=" + message));
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(SupportActivity.this, "Whatsapp not installed on your device", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        binding.btnEmailDbelga.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("IntentReset")
//            @Override
//            public void onClick(View view) {
//                String message = "Halo saya adalah Member Belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName();
//                String[] addresses = {binding.emailAdmin.getText().toString()};
//
//                Log.e(TAG, "onClick: "+ addresses );
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setData(Uri.parse("mailto:"));
//                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
//                intent.putExtra(Intent.EXTRA_SUBJECT, "SUPPORT DBELGA - CUSTOMER : " + sessionManager.getPID());
//                intent.putExtra(Intent.EXTRA_TEXT, message);
//
//                //need this to prompts email client only
//                intent.setType("text/plain");
//
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
//            }
//        });

        if (daftarCS != null) {

            binding.rvCS.setAdapter(null);
            AdapterListCS adapterListCS = new AdapterListCS(SupportActivity.this, daftarCS, SupportActivity.this);
            binding.rvCS.setAdapter(adapterListCS);
        } else {
            finish();
        }

    }

    //Create method appInstalledOrNot
    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    @SuppressLint("IntentReset")
    @Override
    public void onClickCallback(MsgServer detailCS) {

        if (detailCS.getType().equals("EMAIL")) {
            String message = "Halo saya adalah Member Belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName();
            String[] addresses = {detailCS.getContact().toString()};

            Log.e(TAG, "onClick: " + addresses);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, "SUPPORT DBELGA - CUSTOMER : " + sessionManager.getPID());
            intent.putExtra(Intent.EXTRA_TEXT, message);

            //need this to prompts email client only
            intent.setType("text/plain");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            String mobileNumber = detailCS.getContact();
            String message = "Halo saya adalah Member Belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName();
            boolean installed = appInstalledOrNot("com.whatsapp");
            if (installed) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mobileNumber + "&text=" + message));
                startActivity(intent);
            } else {
                Toast.makeText(SupportActivity.this, "Whatsapp not installed on your device", Toast.LENGTH_SHORT).show();
            }
        }

    }
}