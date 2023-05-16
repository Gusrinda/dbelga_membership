package com.dbelgamembership.membersip.Screen.Registrasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelDataRegister;
import com.dbelgamembership.membersip.Model.ResponseUser.ResponseUser;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.VerificationActivity;
import com.dbelgamembership.membersip.databinding.ActivityRegisterBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ProgressDialog LoadingDialog;

    DatePickerDialog datePickerDialog;
    private String TAG = "";
    String tanggal = "";
    public String url = Http.server, jsonResult, type, user;

    String NamaPelanggan = "";
    String TanggalLahir = "";
    String NomorPelanggan = "";
    String EmailPelanggan = "";
    String PasswordPelanggan = "";
    String PasswordUlangPelanggan = "";
    String jenisKelamin = "";


    //    ImageView backArrow;
    SimpleDateFormat formatExp, formatter, formatExpDate;
    SessionManager sessionManager;

    public static ModelDataRegister dataRegisterPelanggan;

    private ActivityRegisterBinding binding;
    private boolean isLakilaki = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        Date c = new Date();
        SimpleDateFormat af = new SimpleDateFormat("yyyy-MM-dd");
        formatExp = new SimpleDateFormat("MM/yyyy");
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatExpDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date

        tanggal = af.format(cal.getTime());
        LoadingDialog = new ProgressDialog(this);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.txtTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                Date curDate = c.getTime();

                datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        String formattedMonth = "" + month;
                        String formattedDayOfMonth = "" + dayOfMonth;

                        if (month < 10) {
                            formattedMonth = "0" + month;
                        }

                        if (dayOfMonth < 10) {
                            formattedDayOfMonth = "0" + dayOfMonth;
                        }

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, Integer.parseInt(formattedMonth) - 1);
                        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(formattedDayOfMonth));

                        Date tanggalBorn = c.getTime();
                        String tanggalX = af.format(tanggalBorn);

                        Log.e(TAG, "tanggal Sekarang : " + af.format(curDate));
                        Log.e(TAG, "tanggal Lahir : " + tanggalX);

                        if (tanggalBorn.after(curDate)) {
                            Toast.makeText(RegisterActivity.this, "Tanggal lahir tidak bisa lebih dari hari ini !", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.txtTanggalLahir.setText(year + "-" + formattedMonth + "-" + formattedDayOfMonth);
                        }

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.radioGroupKelamin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioJenisLaki) {
                    isLakilaki = true;
                } else {
                    isLakilaki = false;
                }
            }
        });

    }

    private void registerUser() {

        NamaPelanggan = binding.txtNamaPelanggan.getText().toString();
        TanggalLahir = binding.txtTanggalLahir.getText().toString();
        NomorPelanggan = "62" + binding.txtNomorHandphonePelanggan.getText().toString();
        EmailPelanggan = binding.txtEmailPelanggan.getText().toString();
        PasswordPelanggan = binding.txtPasswordPelanggan.getText().toString();
        PasswordUlangPelanggan = binding.txtPasswordUlangPelanggan.getText().toString();
        jenisKelamin = (isLakilaki ? "Laki-laki" : "Perempuan");


        if (TextUtils.isEmpty(NamaPelanggan)) {
            Toast.makeText(this, "Tolong isi nama anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(NomorPelanggan)) {
            Toast.makeText(this, "Tolong isi nomor telepon anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PasswordPelanggan)) {
            Toast.makeText(this, "Tolong isi password anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PasswordUlangPelanggan)) {
            Toast.makeText(this, "Tolong tulis kembali password anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(EmailPelanggan)) {
            Toast.makeText(this, "Tolong isi email anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(TanggalLahir)) {
            Toast.makeText(this, "Tolong tanggal lahir anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (!PasswordPelanggan.equals(PasswordUlangPelanggan)) {
            Toast.makeText(this, "Password tidak sama !", Toast.LENGTH_SHORT).show();
        } else {
            new KAlertDialog(RegisterActivity.this, KAlertDialog.WARNING_TYPE)
                    .setTitleText("Konfirmasi")
                    .setContentText("Lanjutkan pengisian data akun anda ?")
                    .setConfirmText("Ya")
                    .confirmButtonColor(R.color.biruBelga, RegisterActivity.this)
                    .cancelButtonColor(R.color.grey_font, RegisterActivity.this)
                    .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                        @Override
                        public void onClick(KAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();


                            CheckingEmailandPhone(EmailPelanggan, NomorPelanggan);


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

    private void CheckingEmailandPhone(String email, String telefon) {
        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Loading", "Getting data . . .");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doCheckEmaildanHp(telefon, email);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                if (response != null) {
                    String responseX = String.valueOf(response.body());
                    JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                    boolean success = root.get("success").getAsBoolean();
                    Log.e("", "Test : " + success);
                    if (!success) {
                        PeringatanDialog("Error", root.get("msgServer").getAsString());
                    } else {
                        dataRegisterPelanggan = new ModelDataRegister(
                                NamaPelanggan,
                                jenisKelamin,
                                TanggalLahir,
                                NomorPelanggan,
                                EmailPelanggan,
                                PasswordPelanggan,
                                null,
                                null,
                                null,
                                null,
                                null
                        );

                        Intent intent = new Intent(RegisterActivity.this, RegistrasiNext.class);
                        intent.putExtra("hasData", true);
                        intent.putExtra("dataRegister", dataRegisterPelanggan);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                PeringatanDialog("Error", t.getMessage());
                Log.e(TAG, "onFailure: " + t.getMessage());
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
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
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