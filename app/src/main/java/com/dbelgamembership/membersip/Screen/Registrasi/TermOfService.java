package com.dbelgamembership.membersip.Screen.Registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.databinding.ActivityTermOfServiceBinding;
import com.developer.kalert.KAlertDialog;

public class TermOfService extends AppCompatActivity {

    private ActivityTermOfServiceBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermOfServiceBinding.inflate(getLayoutInflater());
        sessionManager = new SessionManager(this);
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.txtAgreement.setText(Html.fromHtml(getString(R.string.tos)));

        binding.btnApproveTOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(TermOfService.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Accept")
                        .setContentText("Anda menerima TOS Membership ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, TermOfService.this)
                        .cancelButtonColor(R.color.grey_font, TermOfService.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {

                                Intent intent = new Intent();
                                intent.putExtra("isAccepted", true);
                                setResult(RESULT_OK, intent);
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

        binding.btnRejectTOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(TermOfService.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Accept")
                        .setContentText("Anda menolak TOS Membership ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, TermOfService.this)
                        .cancelButtonColor(R.color.grey_font, TermOfService.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {

                                Intent intent = new Intent();
                                intent.putExtra("isAccepted", false);
                                setResult(RESULT_OK, intent);
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

    }
}