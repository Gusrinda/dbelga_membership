package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    public static boolean cekPreAccess;
    SessionManager sessionManager;

    public String url = Http.server, jsonResult, type, user, pass;

    Button btnLogin, btnDaftar;
    private String TAG = "";
    SimpleDateFormat formatExp;
    ProgressDialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        cekPreAccess = false;

        findId();
        dialog1 = new ProgressDialog(HomeActivity.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        getDataUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


    private void getDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        Log.e(TAG, "URL : " + url);
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
//                                    Toast.makeText(HomeActivity.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    JSONObject jsonObject = response.getJSONObject("msgServer");
                                    Boolean status_pay = jsonObject.getBoolean("status_payment");
                                    if (status_pay == true) {
                                        getSession();
                                    } else {
                                        String deadlinePay = jsonObject.getString("pay_date");
                                        Intent intent = new Intent(HomeActivity.this, KonfirmasiMembership.class);
                                        Log.e(TAG, "onResponse: " + deadlinePay);
                                        intent.putExtra("TANGGAL_DEADLINE", deadlinePay);
                                        startActivity(intent);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
//                            Toast.makeText(HomeActivity.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        dialog1.dismiss();
//                        Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void findId() {
        btnDaftar = findViewById(R.id.btn_Daftar);
        btnLogin = findViewById(R.id.btn_Login);
    }

    public void getSession() {
        Log.e("", "cek Nama session : " + sessionManager.getName());
        Log.e("", "cek PID session : " + sessionManager.getPID());
        Log.e("", "cek Email session : " + sessionManager.getEmail());
        Log.e("", "sessionCondition: Username Login? " + sessionManager.isLoggedIn());
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            cekPreAccess = true;
        } else {
            cekPreAccess = false;
        }
    }
}