package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.dbelgamembership.membersip.Adapter.AdapterListVoucher;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.dbelgamembership.membersip.MainActivity.statusMember;

public class AkunSaya extends AppCompatActivity {

    //TestData
    String name[], date[], detail[], typeX[];


    TextView namaSaya, statusSaya, textCreditLimit, textSisaLimit, textPiutangBelanja;
    RelativeLayout kartuSaya;
    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass;
    RecyclerView rvVoucher;
    CircleImageView imageUser;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
    LinearLayout plafonDebet, plafonReguler;

    String limitPlafon, sisaPlafon, piutangBelanja;
    Button btnUpgrade;
    private String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_saya);

        sessionManager = new SessionManager(this);
        findID();
        accessDataUser();
        getDataUser();
        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, MembershipChoose.class);
                startActivity(intent);
            }
        });

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, EditAkun.class);
                startActivity(intent);
            }
        });

    }

    private void accessDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(AkunSaya.this);
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
                                JSONObject jsonObject = response.getJSONObject("msgServer");
                                limitPlafon = jsonObject.getString("credit_limit").replace("null", "");
                                sisaPlafon = jsonObject.getString("credit_limit_remain").replace("null", "");
                                piutangBelanja = jsonObject.getString("saldo_piutang").replace("null", "");
                                if (limitPlafon.equals("")){
                                    limitPlafon = "0";
                                }
                                if (sisaPlafon.equals("")) {
                                    sisaPlafon = "0";
                                }
                                if (piutangBelanja.equals("")) {
                                    piutangBelanja = "0";
                                }
                                Log.e(TAG, "onResponse: " + limitPlafon );
                                Log.e(TAG, "limit plafon: Rp. " + nf.format(Long.parseLong(limitPlafon)));
                                Log.e(TAG, "sisa plafon: Rp. " + nf.format(Long.parseLong(sisaPlafon)));
                                Log.e(TAG, "piutang belanja: Rp. " + nf.format(Long.parseLong(piutangBelanja)));

                                textCreditLimit.setText("Rp." + nf.format(Long.parseLong(limitPlafon)));
                                textSisaLimit.setText("Rp." + nf.format(Long.parseLong(sisaPlafon)));
                                textPiutangBelanja.setText("Rp." + nf.format(Long.parseLong(piutangBelanja)));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(AkunSaya.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        dialog1.dismiss();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void getDataUser() {
        namaSaya.setText(sessionManager.getName());
        statusSaya.setText(sessionManager.getMembership());
        Log.e(TAG, "getDataUser: " + sessionManager.getImage());
        if (sessionManager.getImage() != "" && sessionManager.getImage() != null) {
            Glide.with(this).asBitmap().load(sessionManager.getImage()).centerCrop().into(imageUser);
        }

        Drawable image;
        String cekStatus = sessionManager.getMembership();

        if (cekStatus.equals("DEBET")) {
            image = getResources().getDrawable(R.drawable.member_gold);
            kartuSaya.setBackground(image);
            plafonDebet.setVisibility(View.VISIBLE);
            plafonReguler.setVisibility(View.GONE);
        } else {
            image = getResources().getDrawable(R.drawable.member_premium);
            kartuSaya.setBackground(image);
            plafonDebet.setVisibility(View.GONE);
            plafonReguler.setVisibility(View.VISIBLE);
        }

        getdataVoucher();

    }

    private void getdataVoucher() {
        name = getResources().getStringArray(R.array.voucher_name);
        typeX = getResources().getStringArray(R.array.voucher_tipe);
        detail = getResources().getStringArray(R.array.voucher_deskripsi);
        String tanggal = "10-10-2020";

        AdapterListVoucher adapterListVoucher = new AdapterListVoucher(this, name, tanggal, detail, typeX);
        rvVoucher.setAdapter(adapterListVoucher);
        rvVoucher.setLayoutManager(new LinearLayoutManager(this));
    }


    private void findID() {
        plafonDebet = findViewById(R.id.plafonDebet);
        plafonReguler = findViewById(R.id.plafonReguler);
        textPiutangBelanja = findViewById(R.id.text_piutangBelanja);
        textSisaLimit = findViewById(R.id.text_sisaLimit);
        textCreditLimit = findViewById(R.id.text_creditLimit);
        imageUser = findViewById(R.id.profile_image);
        btnUpgrade = findViewById(R.id.btb_upgrade);
        namaSaya = findViewById(R.id.namaMember);
        statusSaya = findViewById(R.id.statusMembership);
        kartuSaya = findViewById(R.id.drawMember);
        rvVoucher = findViewById(R.id.rv_Voucher);
    }
}