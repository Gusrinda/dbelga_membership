package com.dbelgamembership.membersip.Screen.User.Membership;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Screen.User.BoardingMemberDebet;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.databinding.ActivityMembershipPilihBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MembershipPilih extends AppCompatActivity {

    SessionManager sessionManager;
    Spinner sp_Membership;
    TextView infoLanjut;
    LinearLayout layoutDetail;
    ScrollView memberRegular, memberGold;
    Button pilihMember;
    String choosenMembership;
    public String url = Http.server, jsonResult, type, user;
    String namaMember, alamatMember, nomorMember, tanggalMember, deadlinePayment, passwordMember, emailMember, expiredMembership;
    private String TAG = "";

    ImageView backArrow;
    SimpleDateFormat formatExp, formatter, formatExpDate;

    private ActivityMembershipPilihBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMembershipPilihBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        formatExp = new SimpleDateFormat("MM/yyyy");
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatExpDate = new SimpleDateFormat("yyyy-MM-dd");
        namaMember = getIntent().getStringExtra("NAMA_MEMBER");
        alamatMember = getIntent().getStringExtra("ALAMAT_MEMBER");
        nomorMember = getIntent().getStringExtra("NOMOR_MEMBER");
        emailMember = getIntent().getStringExtra("EMAIL_MEMBER");
        tanggalMember = getIntent().getStringExtra("TANGGAL_MEMBER");
        passwordMember = getIntent().getStringExtra("PASSWORD_MEMBER");

        findID();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(MembershipPilih.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Keluar ?")
                        .setContentText("Keluar dari halaman ini membuat anda menjadi member 'Reguler' secara default\nAnda yakin ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, MembershipPilih.this)
                        .cancelButtonColor(R.color.grey_font, MembershipPilih.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                                Intent intent = new Intent(MembershipPilih.this, SplashActivity.class);
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

        layoutDetail.setVisibility(View.GONE);

        final Calendar tanggal1 = Calendar.getInstance();
        tanggal1.add(Calendar.YEAR, 1);
        Date tanggalTahun = tanggal1.getTime();
        String deadlen = formatExp.format(tanggalTahun);
        Log.e(TAG, "Tanggal Tahun : " + deadlen);

        sp_Membership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String yangDipilih = sp_Membership.getSelectedItem().toString();
                Drawable image;
                if (yangDipilih.equals("Reguler")) {
                    layoutDetail.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_reguler);
                    binding.layoutCardMember.setBackground(image);
                    memberRegular.setVisibility(View.VISIBLE);
                    memberGold.setVisibility(View.GONE);
                    binding.layoutExpired.setVisibility(View.GONE);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("RGL_" + sessionManager.getPID());
                    choosenMembership = "REGULER";
                } else {
                    layoutDetail.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_debet);
                    binding.layoutCardMember.setBackground(image);
                    memberRegular.setVisibility(View.GONE);
                    memberGold.setVisibility(View.VISIBLE);
                    binding.layoutExpired.setVisibility(View.VISIBLE);
                    binding.txtExpDate.setText(deadlen);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("DBT_" + sessionManager.getPID());
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
                if (choosenMembership.equals("DEBET")) {
                    Intent intent = new Intent(MembershipPilih.this, BoardingMemberDebet.class);
                    startActivity(intent);
                } else {
                    registerUser();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        new KAlertDialog(MembershipPilih.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Keluar ?")
                .setContentText("Keluar dari halaman ini membuat anda menjadi member 'Reguler' secara default\nAnda yakin ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MembershipPilih.this)
                .cancelButtonColor(R.color.grey_font, MembershipPilih.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                        Intent intent = new Intent(MembershipPilih.this, SplashActivity.class);
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

    private void registerUser() {
        if (isOnline() == true) {
            accessWebService();
        } else {
            Toast.makeText(MembershipPilih.this, "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void accessWebService() {
        pilihMember.setEnabled(false);
        new KAlertDialog(MembershipPilih.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Anda akan memilih membership \n'" + choosenMembership + "' ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MembershipPilih.this)
                .cancelButtonColor(R.color.grey_font, MembershipPilih.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        pilihMember.setEnabled(true);
                        sDialog.dismissWithAnimation();
                        if (isOnline()) {
                            url = Http.server;
                            url = url + "update-status/" + sessionManager.getPID();
                            type = "post";
                            JSONObject postData = new JSONObject();
                            try {
                                final Calendar baru = Calendar.getInstance();
                                baru.add(Calendar.DATE, 1);
                                Date deadlineBayar = baru.getTime();
                                String deadlen = formatter.format(deadlineBayar);

                                final Calendar expired = Calendar.getInstance();
                                if (choosenMembership.equals("REGULER")) {
                                    expired.add(Calendar.YEAR, 100);
                                } else {
                                    expired.add(Calendar.YEAR, 1);
                                }

                                Date expiredDate = expired.getTime();
                                String expDate = formatExpDate.format(expiredDate);

                                deadlinePayment = deadlen;
                                expiredMembership = expDate;

                                postData.put("status_member", choosenMembership);
                                postData.put("expired_date", expiredMembership);
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
                })
                .setCancelText("Tidak")
                .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        kAlertDialog.dismissWithAnimation();
                        pilihMember.setEnabled(true);
                    }
                })
                .show();

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
//                                    String createdDate = dataPengguna.getString("created_at");
                                    String deadlinePay = dataPengguna.getString("pay_date");
                                    String dateExpired = dataPengguna.getString("expired_date");

                                    Date created = formatExpDate.parse(dateExpired);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(created);
                                    Log.e(TAG, "Expired member : " + cal.getTime());
                                    Date expiredMember = cal.getTime();
                                    String expDate = formatExp.format(expiredMember);

                                    Log.e("", "id User: " + id);
                                    Log.e("", "nama User: " + name);
                                    Log.e("", "email User: " + email);
                                    Log.e("", "membership: " + membership);
                                    Log.e("", "expired: " + expDate);

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
        sp_Membership = findViewById(R.id.spinnerMembership);
        infoLanjut = findViewById(R.id.infoLanjut);
        memberRegular = findViewById(R.id.infoMemberReguler);
        memberGold = findViewById(R.id.infoMemberGold);
        pilihMember = findViewById(R.id.btnPilihMembership);
        backArrow = findViewById(R.id.backArrow);

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