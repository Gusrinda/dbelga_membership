package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dbelgamembership.membersip.databinding.ActivityMembershipFotoBinding;
import com.developer.kalert.KAlertDialog;

public class MembershipFoto extends AppCompatActivity {

    private ActivityMembershipFotoBinding binding;

    @Override
    public void onBackPressed() {
        new KAlertDialog(MembershipFoto.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Keluar")
                .setContentText("Keluar dari halaman ini akan menyebabkan semua proses pendaftaran member debet hilang. Anda yakin ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MembershipFoto.this)
                .cancelButtonColor(R.color.grey_font, MembershipFoto.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                })
                .setCancelText("Tidak")
                .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        kAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMembershipFotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24);
//        verifBinding.toolbar.setNavigationIcon
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(MembershipFoto.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Keluar")
                        .setContentText("Keluar dari halaman ini akan menyebabkan semua proses pendaftaran member debet hilang. Anda yakin ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, MembershipFoto.this)
                        .cancelButtonColor(R.color.grey_font, MembershipFoto.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .setCancelText("Tidak")
                        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                kAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        binding.btnUploadFotoIdentitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                intent.putExtra("kode_guide", 1);
                startActivity(intent);
            }
        });

        binding.btnUploadFotoWajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                intent.putExtra("kode_guide", 2);
                startActivity(intent);
            }
        });

        binding.btnUploadFotoSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                intent.putExtra("kode_guide", 3);
                startActivity(intent);
            }
        });


    }
}