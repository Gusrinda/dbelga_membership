package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dbelgamembership.membersip.databinding.ActivityBoardingMemberDebetBinding;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import org.jetbrains.annotations.Nullable;

public class BoardingMemberDebet extends AppIntro {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: MASUK HALAMAN BOARDING");

        addSlide(AppIntroFragment.newInstance("Foto",
                "Lakukan foto sesuai dengan petunjuk (Foto Wajah, Foto KTP, dan Foto Selfie dengan KTP)",
                R.drawable.bg_foto, ContextCompat.getColor(getApplicationContext(), R.color.boarding1)));

        addSlide(AppIntroFragment.newInstance("Tunggu",
                "Tunggu sebentar, admin akan memproses foto yang kamu upload untuk memverifikasi",
                R.drawable.bg_wait, ContextCompat.getColor(getApplicationContext(), R.color.boarding2)));

        addSlide(AppIntroFragment.newInstance("Konfirmasi Pembayaran",
                "Upload foto pembayaran dan tunggu proses konfirmasi dari admin pembayaran DBELGA",
                R.drawable.bg_payment, ContextCompat.getColor(getApplicationContext(), R.color.boarding3)));

        addSlide(AppIntroFragment.newInstance("Selesai",
                "Selamat anda berhasil menjadi member Debet DBELGA !",
                R.drawable.bg_finish, ContextCompat.getColor(getApplicationContext(), R.color.boarding4)));

        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);

    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
        startActivity(new Intent(getApplicationContext(), MembershipFoto.class));
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        startActivity(new Intent(getApplicationContext(), MembershipFoto.class));
    }


}