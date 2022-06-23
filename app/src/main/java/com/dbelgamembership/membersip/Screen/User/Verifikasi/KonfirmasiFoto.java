package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelUser.MsgServer;
import com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.ResponseCekVerifikasi;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.databinding.ActivityKonfirmasiFotoBinding;
import com.developer.kalert.KAlertDialog;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiFoto extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityKonfirmasiFotoBinding binding;
    private SessionManager sessionManager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKonfirmasiFotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.btnHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = binding.textNomorWA.getText().toString();
                String message = "Halo saya adalah calon member belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName();
                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mobileNumber + "&text=" + message));
                    startActivity(intent);
                } else {
                    Toast.makeText(KonfirmasiFoto.this, "Whatsapp not installed on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(KonfirmasiFoto.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout")
                        .setContentText("Anda akan keluar dari sesi aplikasi")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, KonfirmasiFoto.this)
                        .cancelButtonColor(R.color.grey_font, KonfirmasiFoto.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                                sessionManager.destroySession();
                                Intent intent = new Intent(KonfirmasiFoto.this, SplashActivity.class);
                                startActivity(intent);
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


    private void getUserData() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ResponseCekVerifikasi> call = apiInterface.doCekVerifikasiUser(sessionManager.getPID());
        call.enqueue(new Callback<ResponseCekVerifikasi>() {
            @Override
            public void onResponse(Call<ResponseCekVerifikasi> call, Response<ResponseCekVerifikasi> response) {
                ResponseCekVerifikasi dataResponse = response.body();
                if (response.code() == 200) {
                    if (dataResponse.getSuccess()) {
                        com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.MsgServer dataVerifikasi = dataResponse.getMsgServer();
                        if (dataVerifikasi.getVeirifikasiFoto()) {
                            if (!dataVerifikasi.getIsTherePayment() || !dataVerifikasi.getVeirifikasiPayment()) {
                                Intent intent = new Intent(KonfirmasiFoto.this, PembayaranMembership.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(KonfirmasiFoto.this, SplashActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(KonfirmasiFoto.this, "Error network getting data.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(KonfirmasiFoto.this, "Error Server !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseCekVerifikasi> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                Toast.makeText(KonfirmasiFoto.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


    int loop = 0;
    Handler handler = new Handler();

    Runnable myRunnable = new Runnable() {
        public void run() {
            loop++;
            getUserData();
            Log.e(TAG, "run: " + loop);
            handler.postDelayed(this, 10000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: DESTROY");
        handler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onDestroy: PAUSE");
        handler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: RESUME");
        handler.postDelayed(myRunnable, 2000);
    }

}