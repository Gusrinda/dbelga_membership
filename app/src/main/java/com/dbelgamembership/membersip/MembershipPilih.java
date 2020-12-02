package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MembershipPilih extends AppCompatActivity {

    SessionManager sessionManager;
    Spinner sp_Membership;
    CardView cardMember;
    RelativeLayout layoutCardMember;
    TextView text_StatusMember, text_namaMember, infoLanjut;
    LinearLayout bintangPremium, bintangGold, layoutDetail;
    ScrollView memberRegular, memberGold;
    Button pilihMember;
    String choosenMembership;
    public String url = Http.server, jsonResult, type, user;
    String namaMember, alamatMember, nomorMember, tanggalMember, deadlinePayment, passwordMember, emailMember;
    private String TAG = "";

    SimpleDateFormat formatExp, formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_pilih);

        sessionManager = new SessionManager(this);

        formatExp = new SimpleDateFormat("MM/yyyy");
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        namaMember = getIntent().getStringExtra("NAMA_MEMBER");
        alamatMember = getIntent().getStringExtra("ALAMAT_MEMBER");
        nomorMember = getIntent().getStringExtra("NOMOR_MEMBER");
        emailMember = getIntent().getStringExtra("EMAIL_MEMBER");
        tanggalMember = getIntent().getStringExtra("TANGGAL_MEMBER");
        passwordMember = getIntent().getStringExtra("PASSWORD_MEMBER");

        findID();

        cardMember.setVisibility(View.GONE);
        layoutDetail.setVisibility(View.GONE);

        sp_Membership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MembershipPilih.this, "Membership dipilih : " + sp_Membership.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                String yangDipilih = sp_Membership.getSelectedItem().toString();
                Drawable image;
                if (yangDipilih.equals("Reguler")) {
                    cardMember.setVisibility(View.VISIBLE);
                    layoutDetail.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.member_premium);
                    bintangPremium.setVisibility(View.VISIBLE);
                    bintangGold.setVisibility(View.GONE);
                    text_StatusMember.setText("PREMIUM");
                    layoutCardMember.setBackground(image);
                    memberRegular.setVisibility(View.VISIBLE);
                    memberGold.setVisibility(View.GONE);
                    text_namaMember.setText("MEMBER REGULAR");
                    choosenMembership = "REGULER";
                } else {
                    cardMember.setVisibility(View.VISIBLE);
                    layoutDetail.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.member_gold);
                    bintangPremium.setVisibility(View.GONE);
                    bintangGold.setVisibility(View.VISIBLE);
                    text_StatusMember.setText("DEBET");
                    layoutCardMember.setBackground(image);
                    memberRegular.setVisibility(View.GONE);
                    memberGold.setVisibility(View.VISIBLE);
                    text_namaMember.setText("MEMBER DEBET");
                    choosenMembership = "DEBET";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pilihMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "kembali . . .", Toast.LENGTH_SHORT).show();
    }

    private void registerUser() {
        if (isOnline() == true) {
//            url = Http.server;
//            url = url + "member-daftar";
//            Log.e("URL: ", url);
            accessWebService();
        } else {
            Toast.makeText(MembershipPilih.this, "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void accessWebService() {
        pilihMember.setEnabled(false);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MembershipPilih.this);
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Anda akan memilih membership \n'" + choosenMembership + "' ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        pilihMember.setEnabled(true);
                        dialog.dismiss();
                        if (isOnline()) {
                            url = Http.server;
                            url = url + "register-customer";
                            type = "post";
                            JSONObject postData = new JSONObject();
                            try {
                                HashMap<String, String> map_order99 = new HashMap<String, String>();
                                postData.put("name", namaMember);
                                postData.put("main_address", alamatMember);
                                postData.put("main_phone_1", nomorMember);
                                postData.put("main_email", emailMember);
                                postData.put("password", passwordMember);
                                postData.put("date_birth", tanggalMember);
                                postData.put("status_member", choosenMembership);

                                final Calendar baru = Calendar.getInstance();
                                baru.add(Calendar.DATE, 1);
                                Date deadlineBayar = baru.getTime();
                                String deadlen = formatter.format(deadlineBayar);

                                deadlinePayment = deadlen;

                                postData.put("pay_date", deadlinePayment);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            if (isOnline()) {
                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClickSubmit: " + postData);
                                SimpanPost(postData);
                            }
                        } else {
                            Snack("Cek Koneksi Internet Anda");
                        }
                    }
                });

        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        pilihMember.setEnabled(true);
                    }
                });

        final AlertDialog alert11 = builder1.create();
        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        alert11.show();
    }

    private void SimpanPost(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(MembershipPilih.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        Log.e(TAG, "postData: " + postData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog1.dismiss();
                            pilihMember.setClickable(true);
                            if (response != null) {
                                Log.e(TAG, "URL " + url);
                                Log.e(TAG, "onResponseSimpan: " + response);
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test : " + success);
                                if (success == false) {
                                    Snack(response.getJSONArray("msgServer").toString());
                                } else {
                                    JSONObject dataPengguna = response.getJSONObject("msgServer");
                                    String id = dataPengguna.getString("id");
                                    String name = dataPengguna.getString("name");
                                    String email = dataPengguna.getString("main_email");
                                    String membership = dataPengguna.getString("status_member");
                                    String createdDate = dataPengguna.getString("created_at");
                                    String deadlinePay = dataPengguna.getString("pay_date");


                                    Date created = formatter.parse(createdDate);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(created);
                                    Log.e(TAG, "Today : " + cal.getTime());
                                    cal.add(Calendar.YEAR, 1);
                                    Log.e(TAG, "Next year expired : " + cal.getTime());
                                    Date nextYear = cal.getTime();
                                    String expDate = formatExp.format(nextYear);

                                    Log.e("", "id User: " + id);
                                    Log.e("", "nama User: " + name);
                                    Log.e("", "email User: " + email);
                                    Log.e("", "membership: " + membership);
                                    Log.e("", "expired: " + deadlinePay);

                                    Log.e("", "deadline: " + deadlinePay);
                                    sessionManager.setRegister(true, id, name, email, membership, expDate);
                                    if (membership.equals("REGULER")) {
                                        Intent intent = new Intent(MembershipPilih.this, MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(MembershipPilih.this, "Selamat bergabung menjadi member Dbelga !", Toast.LENGTH_LONG).show();
                                    } else {
                                        Intent intent = new Intent(MembershipPilih.this, KonfirmasiMembership.class);
                                        Log.e(TAG, "onResponse: " + deadlinePayment);
                                        intent.putExtra("TANGGAL_DEADLINE", deadlinePay);
                                        startActivity(intent);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Snack(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);
                dialog1.dismiss();
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getApplicationContext(), MembershipPilih.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    pilihMember.setClickable(true);
                    startActivity(intent);
                } else if (error instanceof ServerError) {
                    Snack("Terjadi Kesalahan.");
                } else if (error instanceof NetworkError) {
                    Snack("Tidak Ada Koneksi Internet");
                } else if (error instanceof ParseError) {
                    Snack(error.getMessage());
                } else {
                    Snack(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
//                params.put("type", "create");
                params.put("Authorization", "Bearer " + sessionManager.getKeyToken());
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void findID() {
        layoutDetail = findViewById(R.id.layoutDetailMembership);
        cardMember = findViewById(R.id.cardMember);
        sp_Membership = findViewById(R.id.spinnerMembership);
        layoutCardMember = findViewById(R.id.layoutCardMember);
        text_StatusMember = findViewById(R.id.txtStatusMember);
        text_namaMember = findViewById(R.id.namaMembership);
        infoLanjut = findViewById(R.id.infoLanjut);
        bintangPremium = findViewById(R.id.bintangPremium);
        bintangGold = findViewById(R.id.bintangGold);
        memberRegular = findViewById(R.id.infoMemberReguler);
        memberGold = findViewById(R.id.infoMemberGold);
        pilihMember = findViewById(R.id.btnPilihMembership);

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
        Snackbar snackbar = Snackbar.make(pilihMember, string, Snackbar.LENGTH_LONG).setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }
}