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
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelUser.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.User.BoardingMemberDebet;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.MembershipFoto;
import com.dbelgamembership.membersip.databinding.ActivityMembershipChooseBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
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

    MsgServer detailUserSekarang;

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
                if (yangDipilih.equals("Silver")) {
                    choosenMembership = "SILVER";
                    cardMember.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_member_silver);
                    binding.layoutCardMember.setBackground(image);
                    binding.layoutMemberGold.setVisibility(View.GONE);
                    binding.layoutExpired.setVisibility(View.GONE);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("SLV_" + sessionManager.getPID());

                    binding.inputNominalPlafon.setVisibility(View.GONE);

                } else if (yangDipilih.equals("Gold")) {
                    choosenMembership = "GOLD";
                    cardMember.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_member_gold);
                    binding.layoutCardMember.setBackground(image);
                    binding.layoutMemberGold.setVisibility(View.VISIBLE);
                    binding.layoutExpired.setVisibility(View.VISIBLE);
                    binding.txtExpDate.setText(deadlen);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("GLD_" + sessionManager.getPID());
                    binding.inputNominalPlafon.setVisibility(View.VISIBLE);
                    binding.inputNominalPlafon.setErrorEnabled(true);
                    binding.inputNominalPlafon.setError("Batas plafon : Rp. 500.000 - Rp. 2.000.000");
                } else {
                    choosenMembership = "PLATINUM";
                    cardMember.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_member_platinum);
                    binding.layoutCardMember.setBackground(image);
                    binding.layoutMemberGold.setVisibility(View.VISIBLE);
                    binding.layoutExpired.setVisibility(View.VISIBLE);
                    binding.txtExpDate.setText(deadlen);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("PLT_" + sessionManager.getPID());

                    binding.inputNominalPlafon.setVisibility(View.VISIBLE);
                    binding.inputNominalPlafon.setErrorEnabled(true);
                    binding.inputNominalPlafon.setError("Batas plafon : Rp. 2.000.000 - Rp. 5.000.000");
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
                } else {

                    int nominalPlafon = Integer.parseInt(binding.edInputNominalPlafon.getText().toString());

                    int min = 0;
                    int max = 0;

                    if (choosenMembership.equals("GOLD")) {
                        min = 500000;
                        max = 2000000;
                    } else if (choosenMembership.equals("PLATINUM")) {
                        min = 2000000;
                        max = 5000000;
                    }

                    if (nominalPlafon < min) {
                        Toast.makeText(MembershipChoose.this, "Tidak bisa kurang dari minimal !", Toast.LENGTH_SHORT).show();
                        binding.edInputNominalPlafon.setText(String.valueOf(min));
                    } else if (nominalPlafon > max) {
                        Toast.makeText(MembershipChoose.this, "Tidak bisa lebih dari maksimal !", Toast.LENGTH_SHORT).show();
                        binding.edInputNominalPlafon.setText(String.valueOf(max));
                    } else {
                        url = Http.server;
                        url = url + "update-status/" + sessionManager.getPID();
                        updateDataUser();
                    }


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

                            if (!choosenMembership.equals("SILVER")) {
                                paydate = deadlen;
                                expdate = expiredMber;
                            } else {
                                paydate = "";
                                expdate = "";
                            }

                            String expDate = "";

                            if (detailUserSekarang.getStatusMember().equals("SILVER")) {
                                final Calendar expiredMember = Calendar.getInstance();
                                if (choosenMembership.equals("SILVER")) {
                                    expiredMember.add(Calendar.YEAR, 100);
                                } else {
                                    expiredMember.add(Calendar.YEAR, 1);
                                }

                                Date expiredDate = expiredMember.getTime();
                                expDate = formatExpDate.format(expiredDate);
                            } else {
                                expDate = detailUserSekarang.getExpiredDate();
                            }


                            String jatuhTempo = "";

                            if (binding.spinnerTanggalJatuhTempo.getSelectedItemPosition() == 0) {
                                jatuhTempo = "1";
                            } else if (binding.spinnerTanggalJatuhTempo.getSelectedItemPosition() == 1) {
                                jatuhTempo = "15";
                            } else if (binding.spinnerTanggalJatuhTempo.getSelectedItemPosition() == 2) {
                                jatuhTempo = "30";
                            }

                            try {
                                postData.put("status_member", choosenMembership);
                                postData.put("expired_date", expDate);
                                postData.put("pay_date", paydate);
                                postData.put("nominal_plafon", binding.edInputNominalPlafon.getText().toString());
                                postData.put("jatuh_tempo", jatuhTempo);

                                postData.put("is_data_lama", "true");
                                postData.put("membership_lama", detailUserSekarang.getStatusMember());
                                postData.put("jatuh_tempo_lama", detailUserSekarang.getJatuhTempo());
                                postData.put("nominal_plafon_lama", detailUserSekarang.getNominalPlafon());

                            } catch (Exception e) {
                                e.getMessage();
                            }

                            if (isOnline()) {
                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClick: " + postData);


//                                simpan di lokal dulu

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
                                finishAffinity();
                                sessionManager.setMembership(choosenMembership);
                                sessionManager.setKeyDeadlinePayment(paydate);
                                Intent intent = new Intent(MembershipChoose.this, SplashActivity.class);
                                startActivity(intent);
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
                                if (!success) {
                                    Toast.makeText(MembershipChoose.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {

                                    Gson gson = new Gson();
                                    ModelUser modelUser = gson.fromJson(String.valueOf(response), ModelUser.class);

                                    detailUserSekarang = modelUser.getMsgServer().get(0);

                                    String status_member = detailUserSekarang.getStatusMember();
                                    String updated_at = detailUserSekarang.getUpdatedAt();
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

                                    if (detailUserSekarang.getCreditLimit() != null) {
                                        binding.edInputNominalPlafon.setText(detailUserSekarang.getCreditLimit());
                                    }


                                    if (status_member.equals("SILVER")) {
                                        binding.spinnerMembership.setSelection(0);
                                    } else if (status_member.equals("GOLD")) {
                                        binding.spinnerMembership.setSelection(1);
                                    } else {
                                        binding.spinnerMembership.setSelection(2);
                                    }

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