package com.dbelgamembership.membersip.Screen.User.Membership;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ImageView;
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
import com.dbelgamembership.membersip.databinding.ActivityMembershipChooseBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MembershipChoose extends AppCompatActivity {

    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass;
    Spinner sp_Membership;
    CardView cardMember;
    Button pilihMember;
    ConstraintLayout constraintLayout;
    String choosenMembership;
    CardView cardA, cardB, cardC, cardD, cardE, cardF;
    int idCard = 0;
    private String TAG = "";
    String paydate, expdate;
    SimpleDateFormat formatExp, formatter, formatExpDate;
    ImageView backArrow;

    private ActivityMembershipChooseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMembershipChooseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        formatExp = new SimpleDateFormat("MM/yyyy");
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatExpDate = new SimpleDateFormat("yyyy-MM-dd");

        findID();
        snackClicker();

        getDataUser();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cardMember.setVisibility(View.GONE);

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
                    choosenMembership = "REGULER";
                    cardMember.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_reguler);
                    binding.layoutCardMember.setBackground(image);
                    binding.layoutMemberGold.setVisibility(View.GONE);
                    binding.layoutExpired.setVisibility(View.GONE);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("RGL_" + sessionManager.getPID());
                } else {
                    choosenMembership = "DEBET";
                    cardMember.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_debet);
                    binding.layoutCardMember.setBackground(image);
                    binding.layoutMemberGold.setVisibility(View.VISIBLE);
                    binding.layoutExpired.setVisibility(View.VISIBLE);
                    binding.txtExpDate.setText(deadlen);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("DBT_" + sessionManager.getPID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pilihMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.getMembership().equals(choosenMembership)) {
                    Snack("Anda telah menjadi member dengan status yang dipilih !");
                } else if (choosenMembership.equals("DEBET")) {
                    Intent intent = new Intent(MembershipChoose.this, BoardingMemberDebet.class);
                    startActivity(intent);
                } else {
                    url = Http.server;
                    url = url + "update-status/" + sessionManager.getPID();
                    updateDataUser();
                }
            }
        });

    }

    private void updateDataUser() {
        pilihMember.setEnabled(false);

        new KAlertDialog(MembershipChoose.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Anda akan memilih membership \n'" + choosenMembership + "' ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MembershipChoose.this)
                .cancelButtonColor(R.color.grey_font, MembershipChoose.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        pilihMember.setEnabled(true);
                        sDialog.dismiss();
                        if (isOnline()) {
                            type = "post";
                            JSONObject postData = new JSONObject();
                            Log.e(TAG, "choosenMember : " + choosenMembership);

                            final Calendar baru = Calendar.getInstance();
                            final Calendar expired = Calendar.getInstance();
                            baru.add(Calendar.DATE, 1);
                            Date deadlineBayar = baru.getTime();
                            String deadlen = formatter.format(deadlineBayar);
                            expired.add(Calendar.YEAR, 1);
                            Date expiredMembership = expired.getTime();
                            String expiredMber = formatExpDate.format(expiredMembership);

                            if (choosenMembership.equals("DEBET")) {
                                paydate = deadlen;
                                expdate = expiredMber;
                            } else {
                                paydate = "";
                                expdate = "";
                            }

                            final Calendar expiredMember = Calendar.getInstance();
                            if (choosenMembership.equals("REGULER")) {
                                expiredMember.add(Calendar.YEAR, 100);
                            } else {
                                expiredMember.add(Calendar.YEAR, 1);
                            }

                            Date expiredDate = expiredMember.getTime();
                            String expDate = formatExpDate.format(expiredDate);

                            try {
                                postData.put("status_member", choosenMembership);
                                postData.put("expired_date", expDate);
                                postData.put("pay_date", paydate);
                            } catch (Exception e) {
                                e.getMessage();
                            }

                            if (isOnline()) {
                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClick: " + postData);
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
        final ProgressDialog dialog1 = new ProgressDialog(MembershipChoose.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
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
                                    String statusPayment = dataPengguna.getString("status_payment");
                                    String deadlinePay = dataPengguna.getString("pay_date");
                                    String dateExpired = dataPengguna.getString("expired_date");
                                    Log.e("", "id User: " + id);
                                    Log.e("", "nama User: " + name);
                                    Log.e("", "email User: " + email);
                                    Log.e("", "membership: " + membership);
                                    Log.e("", "statusPayment: " + statusPayment);
                                    Log.e("", "expired membership: " + dateExpired);
                                    Log.e(TAG, "onResponse: " + paydate);
                                    sessionManager.setMembership(membership);
                                    if (statusPayment.equals("TRUE")) {
                                        Intent intent = new Intent(MembershipChoose.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(MembershipChoose.this, KonfirmasiMembership.class);
                                        intent.putExtra("TANGGAL_DEADLINE", deadlinePay);
                                        startActivity(intent);
                                        finish();
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

    private void getDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(MembershipChoose.this);
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
                                    Toast.makeText(MembershipChoose.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    JSONObject jsonObject = response.getJSONObject("msgServer");
                                    String status_member = jsonObject.getString("status_member");
                                    String updated_at = jsonObject.getString("updated_at");
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                    Date created = formatter.parse(updated_at);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(created);
                                    Log.e(TAG, "Today : " + cal.getTime());
                                    cal.add(Calendar.YEAR, 1);
                                    Log.e(TAG, "Next year expired : " + cal.getTime());
                                    Date nextYear = cal.getTime();
                                    String expDate = formatExp.format(nextYear);
                                    Log.e("", "email User: " + status_member);
                                    Log.e("", "membership: " + updated_at);

                                    sessionManager.setMembership(status_member);
                                    sessionManager.setExpiredDate(updated_at);
                                }
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(MembershipChoose.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        dialog1.dismiss();
                        Toast.makeText(MembershipChoose.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void snackClicker() {
        cardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCard = 1;
                snacker();
            }
        });
        cardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCard = 2;
                snacker();
            }
        });
        cardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCard = 3;
                snacker();
            }
        });
        cardD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCard = 4;
                snacker();
            }
        });
        cardE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCard = 5;
                snacker();
            }
        });
        cardF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCard = 6;
                snacker();
            }
        });
    }

    private void snacker() {

        final Snackbar snackbar = Snackbar.make(constraintLayout, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(R.layout.snackbar_custom, null);

        String judulSnack = "";
        switch (idCard) {
            case 1:
                judulSnack = "Diskon Member";
                break;
            case 2:
                judulSnack = "Poin Member";
                break;
            case 3:
                judulSnack = "Undian Member";
                break;
            case 4:
                judulSnack = "Plafon Member";
                break;
            case 5:
                judulSnack = "Asuransi Member";
                break;
            case 6:
                judulSnack = "Gratis Ongkir";
                break;
        }

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.addView(custom, 0);

        ImageView closeSnack = custom.findViewById(R.id.closeSnack);
        TextView namaSnack = custom.findViewById(R.id.namaSnack);
        namaSnack.setText(judulSnack);
        closeSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }

    private void findID() {
        constraintLayout = findViewById(R.id.constraintLayout);
        cardMember = findViewById(R.id.cardMember);
        sp_Membership = findViewById(R.id.spinnerMembership);
        pilihMember = findViewById(R.id.btnPilihMembership);
        cardA = findViewById(R.id.cardA);
        cardB = findViewById(R.id.cardB);
        cardC = findViewById(R.id.cardC);
        cardD = findViewById(R.id.cardD);
        cardE = findViewById(R.id.cardE);
        cardF = findViewById(R.id.cardF);
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
        Snackbar snackbar = Snackbar.make(pilihMember, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        snackbar.show();
    }
}