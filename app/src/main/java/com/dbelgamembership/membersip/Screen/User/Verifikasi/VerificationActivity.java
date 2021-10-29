package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.databinding.ActivityVerificationBinding;
import com.developer.kalert.KAlertDialog;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    private ActivityVerificationBinding verifBinding;
    private static final String TAG = "VerificationActivity";
    SessionManager sessionManager;
    SimpleDateFormat formatExp;
    String tanggalDeadline, tanggalSekarang;

    @Override
    public void onBackPressed() {
        new KAlertDialog(VerificationActivity.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Logout")
                .setContentText("Anda akan dihitung logout ketika kembali dari halaman OTP")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, VerificationActivity.this)
                .cancelButtonColor(R.color.grey_font, VerificationActivity.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                        sessionManager.destroySession();
                        Intent intent = new Intent(VerificationActivity.this, SplashActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifBinding = ActivityVerificationBinding.inflate(getLayoutInflater());
        View view = verifBinding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(this);

        formatExp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        getCountDown();

        verifBinding.txtAlamatEmail.setText(sessionManager.getEmail());

        verifBinding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24);
//        verifBinding.toolbar.setNavigationIcon
        verifBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(VerificationActivity.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout")
                        .setContentText("Anda akan dihitung logout ketika kembali dari halaman OTP")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, VerificationActivity.this)
                        .cancelButtonColor(R.color.grey_font, VerificationActivity.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                                sessionManager.destroySession();
                                Intent intent = new Intent(VerificationActivity.this, SplashActivity.class);
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
        verifBinding.edKode1.requestFocus();
        verifBinding.edKode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    verifBinding.edKode2.requestFocus();
                }
            }
        });
        verifBinding.edKode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    verifBinding.edKode3.requestFocus();
                }
            }
        });
        verifBinding.edKode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    verifBinding.edKode4.requestFocus();
                }
            }
        });
        verifBinding.edKode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {

                    InputMethodManager inputManager = (InputMethodManager) VerificationActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(VerificationActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    verifBinding.edKode4.clearFocus();
                }
            }
        });

        verifBinding.btnKirimUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int randomPin = (int) (Math.random() * 9000) + 1000;
                String otp = String.valueOf(randomPin);

                Log.e(TAG, "onClick NEW : " + otp );
                final Calendar expOTP = Calendar.getInstance();
                expOTP.add(Calendar.MINUTE, 15);
                Date deadlineOTP = expOTP.getTime();
                String deadlenOTP = formatExp.format(deadlineOTP);

                sendNewOTP(otp, deadlenOTP);


            }
        });

        verifBinding.btnSubmitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(verifBinding.edKode1.getText()) ||
                        TextUtils.isEmpty(verifBinding.edKode2.getText()) ||
                        TextUtils.isEmpty(verifBinding.edKode3.getText()) ||
                        TextUtils.isEmpty(verifBinding.edKode4.getText())) {

                        PeringatanDialog("Peringatan !", "Harap isi semua field OTP");

                } else {

                    String otp = verifBinding.edKode1.getText().toString() + verifBinding.edKode2.getText().toString() +
                                verifBinding.edKode3.getText().toString() + verifBinding.edKode4.getText().toString();

                    Log.e(TAG, "onClick SUBMIT : " + otp );

                    submitOTP(otp);
                }
            }
        });


    }

    private void sendNewOTP(String otp, String deadlineOTP) {
        final ProgressDialog progressDialog = ProgressDialog.show(VerificationActivity.this, "Loading", "Please Wait...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doUpdateOTP(
                sessionManager.getPID(),
                otp,
                deadlineOTP
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
//                Log.e("onResponse: ", response.body());
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JsonObject root = new JsonParser().parse(String.valueOf(response.body())).getAsJsonObject();
                    boolean check = root.get("success").getAsBoolean();
                    if (!check) {
                        PeringatanDialog("Error" , jsonObject.getString("msgServer"));
                    } else {
                        PeringatanDialog("Berhasil" , jsonObject.getString("msgServer"));
                        sessionManager.setKeyExpotp(deadlineOTP);

                        getCountDown();
                        verifBinding.btnKirimUlang.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitOTP(String otp) {
        final ProgressDialog progressDialog = ProgressDialog.show(VerificationActivity.this, "Loading", "Please Wait...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doVerifikasiOTP(
                sessionManager.getPID(),
                otp
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
//                Log.e("onResponse: ", response.body());
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JsonObject root = new JsonParser().parse(String.valueOf(response.body())).getAsJsonObject();
                    boolean check = root.get("success").getAsBoolean();
                    if (!check) {
                        PeringatanDialog("Error" , jsonObject.getString("msgServer"));
                    } else {
                        Toast.makeText(VerificationActivity.this, "Berhasil terverifikasi !", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(VerificationActivity.this, MembershipPilih.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCountDown() {
        final Calendar baru = Calendar.getInstance();
        Date tanggalNow = baru.getTime();
        String tanggal = formatExp.format(tanggalNow);

        tanggalSekarang = tanggal;
        tanggalDeadline = sessionManager.getKeyExpotp();
        try {
            Date sekarangDate = formatExp.parse(tanggalSekarang);
            Date deadlineDate = formatExp.parse(tanggalDeadline);
            long millisecondsDateNow = sekarangDate.getTime();
            long millisecondsDeadline = deadlineDate.getTime();
            long count = millisecondsDeadline - millisecondsDateNow;
            Log.e(TAG, "getCountDown 1: " + millisecondsDateNow);
            Log.e(TAG, "getCountDown 2: " + millisecondsDeadline);
            Log.e(TAG, "getCountDown 3: " + count);

            if (count >= 0) {
                verifBinding.countdown.start(count);
                verifBinding.countdown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        verifBinding.btnKirimUlang.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                verifBinding.btnKirimUlang.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void PeringatanDialog(String judul, String Pesan) {
        Timer timer = new Timer();
        final long DELAY = 2000; // milliseconds
        AlertDialog alertDialog = new AlertDialog.Builder(VerificationActivity.this).create();
        alertDialog.setTitle(judul);
        alertDialog.setMessage(Pesan);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                            }
                        });

                    }
                },
                DELAY
        );

    }
}