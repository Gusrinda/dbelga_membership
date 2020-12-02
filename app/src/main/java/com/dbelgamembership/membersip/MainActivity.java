package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public String url = Http.server, jsonResult, type, user, pass;
    public static String statusMember = "";
    TextView namaMember, nomorMember, expiredDate, statusMembership, namaUser, emailUser;
    RelativeLayout btnAkunSaya, btnBelanja, btnTransaksiSaya, btnKeluar;
    SessionManager sessionManager;

    CircleImageView profilUser;
    RelativeLayout layoutCardMember;
    LinearLayout bintangPremium, bintangGold;
    private String TAG = "";
    SimpleDateFormat formatExp;
    String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        formatExp = new SimpleDateFormat("MM/yyyy");

        findID();
        getDataUser();

        btnAkunSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AkunSaya.class);
                startActivity(intent);
            }
        });

        btnBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KatalogActivity.class);
                startActivity(intent);
            }
        });

        btnTransaksiSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DaftarTransaksi.class);
                startActivity(intent);
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        profilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditAkun.class);
                startActivity(intent);
            }
        });

    }

    private void getDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(MainActivity.this);
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
                                    Toast.makeText(MainActivity.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    JSONObject jsonObject = response.getJSONObject("msgServer");
                                    String status_member = jsonObject.getString("status_member");
                                    String updated_at = jsonObject.getString("updated_at");
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date created = formatter.parse(updated_at);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(created);
                                    Log.e(TAG, "Today : " + cal.getTime());
                                    cal.add(Calendar.YEAR, 1);
                                    Log.e(TAG, "Next year expired : " + cal.getTime());
                                    Date nextYear = cal.getTime();
                                    String expDate = formatExp.format(nextYear);
                                    Log.e("", "status member: " + status_member);
                                    Log.e("", "expired date: " + expDate);
                                    urlImage = jsonObject.getString("image_customer");

                                    if (urlImage.equals("http://54.254.194.122/upload/customer-photo/")) {
                                        urlImage = "";
                                    } else {
                                        urlImage = jsonObject.getString("image_customer");
                                    }

                                    Log.e(TAG, "url Image: " + urlImage);
                                    sessionManager.setMembership(status_member);
                                    sessionManager.setExpiredDate(expDate);
                                    statusMember = sessionManager.getMembership();
                                    cekMember();
                                }
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        dialog1.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void logout() {
        finish();
        Snack("Log Out Berhasil");
        sessionManager.destroySession();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
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
        Snackbar snackbar = Snackbar.make(btnKeluar, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        snackbar.show();
    }

    private void cekMember() {
        Log.e("TAG", "status Member: " + statusMember);
        Drawable image;
        String nama = sessionManager.getName();
        namaUser.setText(sessionManager.getName());
        emailUser.setText(sessionManager.getEmail());
        nomorMember.setText(sessionManager.getPID());
        expiredDate.setText(sessionManager.getExpiredDate());
        String namaPendek;
        if (nama.length() > 15) {
            namaPendek = nama.substring(0, 15);
        } else {
            namaPendek = nama;
        }
        namaMember.setText(namaPendek.toUpperCase());

        if (!urlImage.equals("")) {
            Glide.with(this).asBitmap().load(urlImage).centerCrop().into(profilUser);
        }

        if (statusMember.equals("REGULER")) {
            image = getResources().getDrawable(R.drawable.member_premium);
            bintangPremium.setVisibility(View.VISIBLE);
            bintangGold.setVisibility(View.GONE);
            statusMembership.setText("REGULER");
            layoutCardMember.setBackground(image);
        } else if (statusMember.equals("DEBET")) {
            image = getResources().getDrawable(R.drawable.member_gold);
            bintangPremium.setVisibility(View.GONE);
            bintangGold.setVisibility(View.VISIBLE);
            statusMembership.setText("DEBET");
            layoutCardMember.setBackground(image);
        }
    }

    private void findID() {
        namaUser = findViewById(R.id.namaUser);
        emailUser = findViewById(R.id.emailUser);
        namaMember = findViewById(R.id.txtNamaMember);
        nomorMember = findViewById(R.id.txtNomorMember);
        expiredDate = findViewById(R.id.txtExpDate);
        statusMembership = findViewById(R.id.txtStatusMember);
        btnAkunSaya = findViewById(R.id.akunSaya);
        btnBelanja = findViewById(R.id.belanjaMember);
        btnTransaksiSaya = findViewById(R.id.transaksiSaya);
//        btnInfoDiskon = findViewById(R.id.informasiDiskon);
        btnKeluar = findViewById(R.id.logoutAkun);
        layoutCardMember = findViewById(R.id.layoutCardMember);
        bintangPremium = findViewById(R.id.bintangPremium);
        bintangGold = findViewById(R.id.bintangGold);
        profilUser = findViewById(R.id.ppUser);
    }


}