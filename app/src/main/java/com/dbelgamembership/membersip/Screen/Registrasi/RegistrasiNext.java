package com.dbelgamembership.membersip.Screen.Registrasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelDataRegister;
import com.dbelgamembership.membersip.Model.ResponseUser.ResponseUser;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.VerificationActivity;
import com.dbelgamembership.membersip.databinding.ActivityRegistrasiNextBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiNext extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    ActivityRegistrasiNextBinding binding;
    SessionManager sessionManager;

    ModelDataRegister modelDataRegisterFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrasiNextBinding.inflate(getLayoutInflater());
        sessionManager = new SessionManager(this);
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("hasData")) {
            modelDataRegisterFinal = getIntent().getParcelableExtra("dataRegister");
            Log.e(TAG, "onCreate: " + modelDataRegisterFinal.getNamaPelanggan());
            Log.e(TAG, "onCreate: " + modelDataRegisterFinal.getNomorHandphone());
        } else {
            finish();
        }

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekData();
            }
        });

        binding.txtPersyaratanMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrasiNext.this, TermOfService.class);
                startActivityForResult(intent, 1);
            }
        });

        binding.checkboxSetuju.setChecked(false);

        binding.checkboxSetuju.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NewApi")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.btnRegister.setEnabled(b);
                if (b) {
                    binding.btnRegister.setBackgroundTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.biruBelga)));
                } else {
                    binding.btnRegister.setBackgroundTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.greyBelha)));
                }
            }
        });

        binding.checkboxAlamatSama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!TextUtils.isEmpty(binding.txtAlamatKTP.getText().toString())) {
                        String alamatKTP = binding.txtAlamatKTP.getText().toString();
                        binding.txtAlamatPelanggan.setText(alamatKTP);
                    }
                }
            }
        });

        binding.txtAlamatKTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.checkboxAlamatSama.isChecked()) {
                    binding.txtAlamatPelanggan.setText(editable);
                }
            }
        });

    }

    private void cekData() {
        if (
                TextUtils.isEmpty(binding.txtNomorIdentitasPelanggan.getText().toString()) ||
                        TextUtils.isEmpty(binding.txtAlamatPelanggan.getText().toString()) ||
                        TextUtils.isEmpty(binding.txtAlamatKotaPelanggan.getText().toString()) ||
                        TextUtils.isEmpty(binding.txtKodePos.getText().toString()) ||
                        TextUtils.isEmpty(binding.txtAlamatKTP.getText().toString()) ||
                        TextUtils.isEmpty(binding.txtAlamatPelanggan.getText().toString())
        ) {
            Toast.makeText(RegistrasiNext.this, "Pastikan semua field data lengkap !", Toast.LENGTH_SHORT).show();
        } else {

            modelDataRegisterFinal.setNomorIdentitas(binding.txtNomorIdentitasPelanggan.getText().toString());
            modelDataRegisterFinal.setAlamatKtp(binding.txtAlamatKTP.getText().toString());
            modelDataRegisterFinal.setAlamatPelanggan(binding.txtAlamatPelanggan.getText().toString());
            modelDataRegisterFinal.setKota(binding.txtAlamatKotaPelanggan.getText().toString());
            modelDataRegisterFinal.setKodePos(binding.txtKodePos.getText().toString());

            new KAlertDialog(RegistrasiNext.this, KAlertDialog.WARNING_TYPE)
                    .setTitleText("Konfirmasi")
                    .setContentText("Register akun anda ?")
                    .setConfirmText("Ya")
                    .confirmButtonColor(R.color.biruBelga, RegistrasiNext.this)
                    .cancelButtonColor(R.color.grey_font, RegistrasiNext.this)
                    .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                        @Override
                        public void onClick(KAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            if (isOnline()) {
                                sendData();
                            } else {
                                Snack("Cek Koneksi Internet Anda");
                            }
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
    }

    private void sendData() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatExpDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatExp = new SimpleDateFormat("MM/yyyy");

        int randomPin = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomPin);

        final Calendar baru = Calendar.getInstance();
        final Calendar expOTP = Calendar.getInstance();
        expOTP.add(Calendar.MINUTE, 15);
        baru.add(Calendar.DATE, 1);
        Date deadlineBayar = baru.getTime();
        Date deadlineOTP = expOTP.getTime();
        String deadlenPembayaran = formatter.format(deadlineBayar);
        String deadlenOTP = formatter.format(deadlineOTP);

        final Calendar expired = Calendar.getInstance();
        expired.add(Calendar.YEAR, 100);

        Date expiredDate = expired.getTime();
        String expDate = formatExpDate.format(expiredDate);

        final ProgressDialog progressDialog = ProgressDialog.show(RegistrasiNext.this, "Loading", "Getting data . . .");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doRegistrasiMember(
                modelDataRegisterFinal.getNamaPelanggan(),
                modelDataRegisterFinal.getJenisKelamin(),
                modelDataRegisterFinal.getTanggalLahir(),
                modelDataRegisterFinal.getNomorHandphone(),
                modelDataRegisterFinal.getEmailPelanggan(),
                modelDataRegisterFinal.getPassword(),
                modelDataRegisterFinal.getNomorIdentitas(),
                modelDataRegisterFinal.getAlamatKtp(),
                modelDataRegisterFinal.getAlamatPelanggan(),
                modelDataRegisterFinal.getKota(),
                modelDataRegisterFinal.getKodePos(),
                "SILVER",
                expDate,
                deadlenPembayaran,
                otp,
                deadlenOTP
        );

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    progressDialog.dismiss();
                    if (response != null) {
                        String responseX = String.valueOf(response.body());
                        JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                        boolean success = root.get("success").getAsBoolean();
                        Log.e("", "Test : " + success);
                        if (!success) {
                            PeringatanDialog("Error", root.get("msgServer").getAsString());
                        } else {
                            Gson gson = new Gson();
                            ResponseUser modelUser = gson.fromJson(String.valueOf(responseX), ResponseUser.class);
                            String id = String.valueOf(modelUser.getMsgServer().getId());
                            //sementara pakai ID
                            String identitasPelanggan = String.valueOf(modelUser.getMsgServer().getId());
                            String name = modelUser.getMsgServer().getName();
                            String email = modelUser.getMsgServer().getMainEmail();
                            String membership = modelUser.getMsgServer().getStatusMember();
                            String deadlinePay = modelUser.getMsgServer().getPayDate();
                            String dateExpired = modelUser.getMsgServer().getExpiredDate();

                            String count = modelUser.getMsgServer().getExpOtp();

                            Date created = formatExpDate.parse(dateExpired);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(created);
                            Log.e(TAG, "Expired member : " + cal.getTime());
                            Date expiredMember = cal.getTime();
                            String expDate = formatExp.format(expiredMember);

                            sessionManager.setRegister(true, id, identitasPelanggan,name, email, membership, expDate);
                            sessionManager.setKeyExpotp(modelUser.getMsgServer().getExpOtp());

                            Intent intent = new Intent(RegistrasiNext.this, VerificationActivity.class);
                            intent.putExtra("EXPIRED_OTP", count);
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage() + Arrays.toString(e.getStackTrace()));
                    Snack(e.getMessage());

                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrasiNext.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + "\n" + Arrays.toString(t.getStackTrace()));
            }
        });


    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(binding.btnRegister, string, Snackbar.LENGTH_LONG).setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }


    private void PeringatanDialog(String judul, String Pesan) {
        Timer timer = new Timer();
        final long DELAY = 2000; // milliseconds
        AlertDialog alertDialog = new AlertDialog.Builder(RegistrasiNext.this).create();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                if (resultCode == -1) {
                    Log.e(TAG, "onActivityResult: " + data);
                    if (data != null) {
                        boolean checked = data.getBooleanExtra("isAccepted", false);
                        binding.checkboxSetuju.setChecked(checked);
                    } else {
                        Log.e(TAG, "onActivityResult: data " + data);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onActivityResult: Exception " + e.getMessage());
        }
    }

}