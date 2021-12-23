package com.dbelgamembership.membersip.Screen.User;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.MembershipFoto;
import com.dbelgamembership.membersip.R;
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

        addSlide(AppIntroFragment.newInstance("Peringatan",
                "Pastikan ketika kamu mengisi data verifikasi membership tidak keluar atau menutup aplikasi !\n( Apabila menutup / keluar maka anda akan secara default menjadi member SILVER )",
                R.drawable.bg_wait, ContextCompat.getColor(getApplicationContext(), R.color.merahBelga)));

        addSlide(AppIntroFragment.newInstance("Pilih Membership",
                "Pilih membership yang anda inginkan ! Benefit tiap membership dapat dilihat di bagian bawah saat memilih membership.",
                R.drawable.bg_foto, ContextCompat.getColor(getApplicationContext(), R.color.material_blue_200)));

        addSlide(AppIntroFragment.newInstance("Verifikasi Foto",
                "Upload foto verifikasi membership DBELGA",
                R.drawable.bg_foto, ContextCompat.getColor(getApplicationContext(), R.color.material_deep_orange_300)));

        addSlide(AppIntroFragment.newInstance("Verifikasi Pembayaran",
                "Jika anda memilih membership GOLD / PLATINUM maka anda akan diwajibkan membayar biaya administrasi bulanan dan upload bukti pembayaran",
                R.drawable.bg_payment, ContextCompat.getColor(getApplicationContext(), R.color.material_green_500)));

        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);

    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
        startActivity(new Intent(getApplicationContext(), MembershipPilih.class));
//        startActivity(new Intent(getApplicationContext(), MembershipFoto.class));
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        startActivity(new Intent(getApplicationContext(), MembershipPilih.class));
//        startActivity(new Intent(getApplicationContext(), MembershipFoto.class));
    }


}