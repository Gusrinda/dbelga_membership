package com.dbelgamembership.membersip.Screen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ResponseLogin.ResponseLogin;
import com.dbelgamembership.membersip.Model.ResponseUser.ResponseUser;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.Log.model.LogModel;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.Registrasi.RegistrasiNext;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.VerificationActivity;
import com.dbelgamembership.membersip.databinding.DialogBirthPelangganBinding;
import com.dbelgamembership.membersip.databinding.PopupPembatalanTransaksiBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.type.DateTime;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    public static boolean cekPreAccess;
    SessionManager sessionManager;

    public String url = Http.server, jsonResult, type, user, pass;

    public CoordinatorLayout relativeLayout;
    TextInputEditText usernameInput, passwordInput;
    Button btnLogin;
    LinearLayout btnRegister;
    ImageView logoBelga;

    private Snackbar currentlyshownSnackbar;
    SimpleDateFormat formatExp;
    private String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        cekPreAccess = false;

        formatExp = new SimpleDateFormat("MM/yyyy");
        findId();
        getSession();

//        logoBelga.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(LoginActivity.this, NewMainActivity.class);
//                startActivity(intent);
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findId() {
        relativeLayout = findViewById(R.id.relativeLayout);
        logoBelga = findViewById(R.id.logoBelgaX);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btn_Login);
        btnRegister = findViewById(R.id.btnDaftar);
    }

    private void loginUser() {
        try {
            String usernamePelanggan = usernameInput.getText().toString();
            String passwordPelanggan = null;

            passwordPelanggan = URLEncoder.encode(passwordInput.getText().toString(), StandardCharsets.UTF_8.toString());

            if (TextUtils.isEmpty(usernamePelanggan)) {
                Toast.makeText(this, "Tolong isi username anda . . . ", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(passwordPelanggan)) {
                Toast.makeText(this, "Tolong isi password anda . . . ", Toast.LENGTH_SHORT).show();
            } else {
                if (isOnline() == true) {
                    url = Http.server;
                    url = url + "login-customer?email=" + usernamePelanggan + "&password=" + passwordPelanggan;
                    Log.e("URL: ", url);
                    accessWebService();
                } else {
                    Toast.makeText(LoginActivity.this, "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    DialogBirthPelangganBinding dialogBirthPelangganBinding;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat af = new SimpleDateFormat("yyyy-MM-dd");

    public void accessWebService() {
        final ProgressDialog dialog1 = new ProgressDialog(LoginActivity.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog1.dismiss();
                        if (response != null) {
                            Log.e("", "onResponse: " + response);
                            String responseX = String.valueOf(response);
                            JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                            boolean success = root.get("success").getAsBoolean();
                            Log.e("", "Test : " + success);
                            if (!success) {
                                String message = root.get("msgServer").getAsString();
                                Snack(message);
                                Log.e("TAG", "onResponse:  " + message);
                            } else {
                                Gson gson = new Gson();
                                ResponseLogin modelUser = gson.fromJson(String.valueOf(response), ResponseLogin.class);
                                String idUser = String.valueOf(modelUser.getMsgServer().getId());
                                String identitasPelanggan = String.valueOf(modelUser.getMsgServer().getIdentitas());
                                String namaUser = modelUser.getMsgServer().getName();
                                String emailUser = modelUser.getMsgServer().getMainEmail();
                                String membershipUser = modelUser.getMsgServer().getStatusMember();
                                String jatuhTempo = modelUser.getMsgServer().getJatuhTempo();
                                String idGudang = modelUser.getMsgServer().getMainGudang() == null ? "" : modelUser.getMsgServer().getMainGudang();
                                Log.e("", "id User: " + idUser);
                                Log.e("", "nama User: " + namaUser);
                                Log.e("", "email User: " + emailUser);
                                Log.e("", "membership: " + membershipUser);
                                Log.e("", "membership: " + identitasPelanggan);
                                Log.e("", "jatuh Tempo: " + jatuhTempo);

                                boolean status_pay = Boolean.parseBoolean(modelUser.getMsgServer().getStatusPayment());


                                if (modelUser.getMsgServer().getEmailVerification()) {

                                    sessionManager.setAwalListLogHistory();
                                    sessionManager.setLogin(true, idUser, identitasPelanggan, namaUser, emailUser, membershipUser, jatuhTempo, idGudang);
                                    sessionManager.setAccountUser(namaUser, emailUser, modelUser.getMsgServer().getMainAddress(), modelUser.getMsgServer().getMainPhone1());
                                    sessionManager.setKeyExpotp(modelUser.getMsgServer().getExpOtp());
                                    sessionManager.setKeyDeadlinePayment(modelUser.getMsgServer().getPayDate());

                                    sessionManager.addLogHistory(new LogModel(
                                            "AKUN", Calendar.getInstance().getTime(), "Akun anda LOGIN pada saat ini"
                                    ));

                                    finish();
                                    Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {

                                    String tipeNMember = modelUser.getMsgServer().getType();

                                    if (tipeNMember.equals("Pelanggan")) {
                                        Toast.makeText(LoginActivity.this, "Ini pelanggan dari sales !!!", Toast.LENGTH_SHORT).show();

                                        dialogBirthPelangganBinding = DialogBirthPelangganBinding.inflate(getLayoutInflater());
                                        View view = dialogBirthPelangganBinding.getRoot();


                                        dialogBuilder = new AlertDialog.Builder(LoginActivity.this);

                                        dialogBuilder.setView(view);
                                        alertDialog = dialogBuilder.create();
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.show();


                                        dialogBirthPelangganBinding.inputDateBirth.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                                                final Calendar c = Calendar.getInstance();
                                                int mYear = c.get(Calendar.YEAR);
                                                int mMonth = c.get(Calendar.MONTH);
                                                int mDay = c.get(Calendar.DAY_OF_MONTH);

                                                Date curDate = c.getTime();

                                                datePickerDialog = new DatePickerDialog(LoginActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                                                            Toast.makeText(LoginActivity.this, "Tanggal lahir tidak bisa lebih dari hari ini !", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            dialogBirthPelangganBinding.inputDateBirth.setText(year + "-" + formattedMonth + "-" + formattedDayOfMonth);
                                                        }

                                                    }
                                                }, mYear, mMonth, mDay);
                                                datePickerDialog.show();
                                            }
                                        });

                                        dialogBirthPelangganBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                alertDialog.dismiss();
                                                Toast.makeText(LoginActivity.this, "Login gagal, tidak melengkapi data !", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        dialogBirthPelangganBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                new KAlertDialog(LoginActivity.this, KAlertDialog.WARNING_TYPE)
                                                        .setTitleText("Konfirmasi")
                                                        .setContentText("Anda yakin ini tanggal lahir anda ?")
                                                        .setConfirmText("Ya")
                                                        .confirmButtonColor(R.color.biruBelga, LoginActivity.this)
                                                        .cancelButtonColor(R.color.grey_font, LoginActivity.this)
                                                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                                            @Override
                                                            public void onClick(KAlertDialog sDialog) {
                                                                alertDialog.dismiss();
                                                                sDialog.dismissWithAnimation();
                                                                Toast.makeText(LoginActivity.this, "Update data", Toast.LENGTH_SHORT).show();

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

                                                                String TanggalLahir = dialogBirthPelangganBinding.inputDateBirth.getText().toString();

                                                                final Calendar expired = Calendar.getInstance();
                                                                expired.add(Calendar.YEAR, 100);

                                                                Date expiredDate = expired.getTime();
                                                                String expDate = formatExpDate.format(expiredDate);

                                                                String statusMember = "SILVER";

                                                                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Loading", "Updating status . . .");
                                                                APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);

                                                                Call<JsonElement> call = apiInterface.doUpdatePelanggan(
                                                                        TanggalLahir,
                                                                        String.valueOf(modelUser.getMsgServer().getId()),
                                                                        statusMember,
                                                                        expDate,
                                                                        deadlenPembayaran,
                                                                        otp,
                                                                        deadlenOTP
                                                                );

                                                                call.enqueue(new Callback<JsonElement>() {
                                                                    @Override
                                                                    public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {

                                                                        progressDialog.dismiss();
                                                                        if (response != null) {
                                                                            String responseX = String.valueOf(response.body());
                                                                            JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                                                            boolean success = root.get("success").getAsBoolean();
                                                                            Log.e("", "Test : " + success);
                                                                            if (!success) {
                                                                                Toast.makeText(LoginActivity.this, "Error : " + root.get("msgServer").getAsString(), Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                try {
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

                                                                                    Date created = null;

                                                                                    created = formatExpDate.parse(dateExpired);

                                                                                    Calendar cal = Calendar.getInstance();
                                                                                    cal.setTime(created);
                                                                                    Log.e(TAG, "Expired member : " + cal.getTime());
                                                                                    Date expiredMember = cal.getTime();
                                                                                    String expDate = formatExp.format(expiredMember);

                                                                                    sessionManager.setRegister(true, id, identitasPelanggan, name, email, membership, expDate);
                                                                                    sessionManager.setKeyExpotp(modelUser.getMsgServer().getExpOtp());


                                                                                    sessionManager.setLogin(true, idUser, identitasPelanggan, namaUser, emailUser, membershipUser, jatuhTempo, idGudang);
                                                                                    sessionManager.setAccountUser(namaUser, emailUser, modelUser.getMsgServer().getMainAddress(), modelUser.getMsgServer().getMainPhone1());
                                                                                    sessionManager.setKeyExpotp(modelUser.getMsgServer().getExpOtp());
                                                                                    sessionManager.setKeyDeadlinePayment(modelUser.getMsgServer().getPayDate());

                                                                                    Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                                                                                    intent.putExtra("EXPIRED_OTP", count);
                                                                                    startActivity(intent);
                                                                                } catch (ParseException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<JsonElement> call, Throwable t) {
                                                                        progressDialog.dismiss();
                                                                        Log.e(TAG, "onFailure: ERROR UPDATING STATUS :: " + Arrays.toString(t.getStackTrace()));
                                                                    }
                                                                });


                                                            }
                                                        })
                                                        .setCancelText("Tidak")
                                                        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                                                            @Override
                                                            public void onClick(KAlertDialog kAlertDialog) {
                                                                kAlertDialog.dismissWithAnimation();
                                                                Toast.makeText(LoginActivity.this, "Bukan tanggal lahir, dialog close.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                        .show();
                                            }
                                        });

                                    } else {

                                        sessionManager.setLogin(true, idUser, identitasPelanggan, namaUser, emailUser, membershipUser, jatuhTempo, idGudang);
                                        sessionManager.setAccountUser(namaUser, emailUser, modelUser.getMsgServer().getMainAddress(), modelUser.getMsgServer().getMainPhone1());
                                        sessionManager.setKeyExpotp(modelUser.getMsgServer().getExpOtp());
                                        sessionManager.setKeyDeadlinePayment(modelUser.getMsgServer().getPayDate());

//                                    sessionManager.setLogin(true, idUser, namaUser, emailUser, membershipUser);
                                        Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                                        startActivity(intent);
                                    }

                                }

                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        dialog1.dismiss();
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);

    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void getSession() {
        Log.e("", "sessionCondition: Username Login? " + sessionManager.isLoggedIn());
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, GudangActivity.class);
            startActivity(intent);
            cekPreAccess = true;
        } else {
            cekPreAccess = false;
        }
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(relativeLayout, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        snackbar.show();
        currentlyshownSnackbar = snackbar;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.currentlyshownSnackbar = null;
    }
}