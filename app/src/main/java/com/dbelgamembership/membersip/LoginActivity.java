package com.dbelgamembership.membersip;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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

        logoBelga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
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
                            try {
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test : " + success);
                                if (success == false) {
                                    String message = root.get("msgServer").getAsString();
                                    Snack(message);
                                    Log.e("TAG", "onResponse:  " + message);
//                                    Toast.makeText(LoginActivity.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    JSONObject jsonObject = response.getJSONObject("msgServer");
                                    String idUser = jsonObject.getString("id");
                                    String namaUser = jsonObject.getString("name");
                                    String emailUser = jsonObject.getString("main_email");
                                    String membershipUser = jsonObject.getString("status_member");
                                    Log.e("", "id User: " + idUser);
                                    Log.e("", "nama User: " + namaUser);
                                    Log.e("", "email User: " + emailUser);
                                    Log.e("", "membership: " + membershipUser);
                                    String status_pay = jsonObject.getString("status_payment");
                                    if (status_pay.equals("TRUE")) {
                                        sessionManager.setLogin(true, idUser, namaUser, emailUser, membershipUser);
                                        if (jsonObject.getString("image_customer") != null) {
                                            sessionManager.setImage("http://54.254.194.122/upload/customer-photo/"+jsonObject.getString("image_customer"));
                                        }
                                        getSession();
                                    } else {
                                        sessionManager.setLogin(true, idUser, namaUser, emailUser, membershipUser);
                                        if (jsonObject.getString("image_customer") != null) {
                                            sessionManager.setImage("http://54.254.194.122/upload/customer-photo/"+jsonObject.getString("image_customer"));
                                        }
                                        String deadlinePay = jsonObject.getString("pay_date");
                                        formatExp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        final Calendar baru = Calendar.getInstance();
                                        Date tanggalNow = baru.getTime();
                                        String tanggal = formatExp.format(tanggalNow);
                                        Intent intent = new Intent(LoginActivity.this, KonfirmasiMembership.class);
                                        Log.e(TAG, "onResponse: " + deadlinePay);
                                        Log.e(TAG, "onResponse: " + tanggal);
                                        intent.putExtra("TANGGAL_DEADLINE", deadlinePay);
                                        intent.putExtra("TANGGAL_1", tanggal);
                                        startActivity(intent);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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