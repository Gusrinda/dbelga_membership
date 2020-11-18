package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String statusMember = "premium";
    TextView namaMember, nomorMember, expiredDate, statusMembership;
    RelativeLayout btnAkunSaya, btnInfoMember, btnTransaksiSaya, btnInfoDiskon, btnKeluar;

    RelativeLayout layoutCardMember;
    LinearLayout bintangPremium, bintangGold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findID();
        cekMember();

        btnAkunSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AkunSaya.class);
                startActivity(intent);
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void cekMember() {

        Drawable image;
        if (statusMember.equals("premium")) {
            image = getResources().getDrawable(R.drawable.member_premium);
            bintangPremium.setVisibility(View.VISIBLE);
            bintangGold.setVisibility(View.GONE);
            statusMembership.setText("PREMIUM");
            layoutCardMember.setBackground(image);
        } else {
            image = getResources().getDrawable(R.drawable.member_gold);
            bintangPremium.setVisibility(View.GONE);
            bintangGold.setVisibility(View.VISIBLE);
            statusMembership.setText("GOLD");
            layoutCardMember.setBackground(image);
        }
    }

    private void findID() {
        namaMember = findViewById(R.id.txtNamaMember);
        nomorMember = findViewById(R.id.txtNomorMember);
        expiredDate = findViewById(R.id.txtExpDate);
        statusMembership = findViewById(R.id.txtStatusMember);
        btnAkunSaya = findViewById(R.id.akunSaya);
        btnInfoMember = findViewById(R.id.informasiMember);
        btnTransaksiSaya = findViewById(R.id.transaksiSaya);
//        btnInfoDiskon = findViewById(R.id.informasiDiskon);
        btnKeluar = findViewById(R.id.logoutAkun);
        layoutCardMember = findViewById(R.id.layoutCardMember);
        bintangPremium = findViewById(R.id.bintangPremium);
        bintangGold = findViewById(R.id.bintangGold);
    }


}