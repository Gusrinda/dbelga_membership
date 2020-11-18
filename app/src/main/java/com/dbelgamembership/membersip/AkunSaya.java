package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.dbelgamembership.membersip.MainActivity.statusMember;

public class AkunSaya extends AppCompatActivity {

    Button btnUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_saya);

        findID();

        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgradeStatus();
            }
        });

    }

    private void upgradeStatus() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AkunSaya.this);
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Anda akan upgrade Membership anda !");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        btnUpgrade.setEnabled(true);
                        dialog.dismiss();
                        statusMember = "gold";
                        Intent intent = new Intent(AkunSaya.this, MainActivity.class);
                        startActivity(intent);

                    }
                });
        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        btnUpgrade.setEnabled(true);
                    }
                });

        final AlertDialog alert11 = builder1.create();
        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        alert11.show();
    }

    private void findID() {
        btnUpgrade = findViewById(R.id.btb_upgrade);
    }
}