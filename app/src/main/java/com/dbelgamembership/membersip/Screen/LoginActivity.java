package com.dbelgamembership.membersip.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ResponseLogin.ResponseLogin;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.VerificationActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        String usernamePelanggan = usernameInput.getText().toString();
        String passwordPelanggan = passwordInput.getText().toString();
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

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
                                Log.e("", "id User: " + idUser);
                                Log.e("", "nama User: " + namaUser);
                                Log.e("", "email User: " + emailUser);
                                Log.e("", "membership: " + membershipUser);
                                Log.e("", "membership: " + identitasPelanggan);

                                boolean status_pay = Boolean.parseBoolean(modelUser.getMsgServer().getStatusPayment());

                                sessionManager.setLogin(true, idUser, identitasPelanggan, namaUser, emailUser, membershipUser);
                                sessionManager.setAccountUser(namaUser, emailUser, modelUser.getMsgServer().getMainAddress(), modelUser.getMsgServer().getMainPhone1());
                                sessionManager.setKeyExpotp(modelUser.getMsgServer().getExpOtp());

                                if (modelUser.getMsgServer().getEmailVerification()) {
                                    finish();
                                    Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
//                                    sessionManager.setLogin(true, idUser, namaUser, emailUser, membershipUser);
                                    Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                                    startActivity(intent);
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);

    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void getSession() {
        Log.e("", "sessionCondition: Username Login? " + sessionManager.isLoggedIn());
        if (sessionManager.isLoggedIn()) {
//            Intent intent = new Intent(LoginActivity.this, NewMainActivity.class);
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