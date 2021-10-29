package com.dbelgamembership.membersip.Screen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.dbelgamembership.membersip.Model.ResponseUser.ResponseUser;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.VerificationActivity;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText txt_namaPelanggan, txt_alamatPelanggan, txt_nomorHandphone, txt_tanggalLahir, txt_password, txt_passwordUlang, txt_emailPelanggan;
    Button btnRegister;
    LinearLayout layoutTanggalLahir, btnLogin;

    private ProgressDialog LoadingDialog;

    DatePickerDialog datePickerDialog;
    private String TAG = "";
    String tanggal = "";
    public String url = Http.server, jsonResult, type, user;

    ImageView backArrow;
    SimpleDateFormat formatExp, formatter, formatExpDate;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager = new SessionManager(this);

        Date c = new Date();
        SimpleDateFormat af = new SimpleDateFormat("yyyy-MM-dd");
        formatExp = new SimpleDateFormat("MM/yyyy");
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatExpDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date

        tanggal = af.format(cal.getTime());
        LoadingDialog = new ProgressDialog(this);

        findID();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txt_tanggalLahir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int inType = txt_tanggalLahir.getInputType(); // backup the input type
                txt_tanggalLahir.setInputType(InputType.TYPE_NULL); // disable soft input
                txt_tanggalLahir.onTouchEvent(motionEvent); // call native handler
                txt_tanggalLahir.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        layoutTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_tanggalLahir.setFocusable(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                Date curDate = c.getTime();

                datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                            Toast.makeText(RegisterActivity.this, "Tanggal lahir tidak bisa lebih dari hari ini !", Toast.LENGTH_SHORT).show();
                        } else {
                            txt_tanggalLahir.setText(year + "-" + formattedMonth + "-" + formattedDayOfMonth);
                        }
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser() {

        String NamaPelanggan = txt_namaPelanggan.getText().toString();
        String NomorPelanggan = txt_nomorHandphone.getText().toString();
        String AlamatPelanggan = txt_alamatPelanggan.getText().toString();
        String EmailPelanggan = txt_emailPelanggan.getText().toString();
        String PasswordPelanggan = txt_password.getText().toString();
        String PasswordUlangPelanggan = txt_passwordUlang.getText().toString();
        String TanggalLahir = txt_tanggalLahir.getText().toString();

        if (TextUtils.isEmpty(NamaPelanggan)) {
            Toast.makeText(this, "Tolong isi nama anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(NomorPelanggan)) {
            Toast.makeText(this, "Tolong isi nomor telepon anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(AlamatPelanggan)) {
            Toast.makeText(this, "Tolong isi alamat anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PasswordPelanggan)) {
            Toast.makeText(this, "Tolong isi password anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PasswordUlangPelanggan)) {
            Toast.makeText(this, "Tolong tulis kembali password anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(EmailPelanggan)) {
            Toast.makeText(this, "Tolong isi email anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(TanggalLahir)) {
            Toast.makeText(this, "Tolong tanggal lahir anda . . . ", Toast.LENGTH_SHORT).show();
        } else {
            if (!PasswordPelanggan.equals(PasswordUlangPelanggan)) {
                Toast.makeText(this, "Password tidak sama !", Toast.LENGTH_SHORT).show();
            } else {
                int randomPin = (int) (Math.random() * 9000) + 1000;
                String otp = String.valueOf(randomPin);

                new KAlertDialog(RegisterActivity.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Register akun anda ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, RegisterActivity.this)
                        .cancelButtonColor(R.color.grey_font, RegisterActivity.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                if (isOnline()) {
                                    url = Http.server;
                                    url = url + "register-customer";
                                    type = "post";
                                    JSONObject postData = new JSONObject();
                                    try {
                                        HashMap<String, String> map_order99 = new HashMap<String, String>();
                                        postData.put("name", NamaPelanggan);
                                        postData.put("main_address", AlamatPelanggan);
                                        postData.put("main_phone_1", NomorPelanggan);
                                        postData.put("main_email", EmailPelanggan);
                                        postData.put("password", PasswordPelanggan);
                                        postData.put("date_birth", TanggalLahir);
                                        postData.put("status_member", "REGULER");

                                        final Calendar baru = Calendar.getInstance();
                                        final Calendar expOTP = Calendar.getInstance();
                                        expOTP.add(Calendar.MINUTE, 15);
                                        baru.add(Calendar.DATE, 1);
                                        Date deadlineBayar = baru.getTime();
                                        Date deadlineOTP = expOTP.getTime();
                                        String deadlen = formatter.format(deadlineBayar);
                                        String deadlenOTP = formatter.format(deadlineOTP);

                                        final Calendar expired = Calendar.getInstance();
                                        expired.add(Calendar.YEAR, 100);

                                        Date expiredDate = expired.getTime();
                                        String expDate = formatExpDate.format(expiredDate);

                                        postData.put("expired_date", expDate);
                                        postData.put("pay_date", deadlen);

                                        postData.put("otp", otp);
                                        postData.put("exp_otp", deadlenOTP);

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
                            }
                        })
                        .show();
            }
        }

    }

    private void findID() {
        txt_namaPelanggan = findViewById(R.id.txt_namaPelanggan);
        txt_alamatPelanggan = findViewById(R.id.txt_alamatPelanggan);
        txt_nomorHandphone = findViewById(R.id.txt_nomorHandphonePelanggan);
        txt_tanggalLahir = findViewById(R.id.txt_tanggalLahir);
        txt_password = findViewById(R.id.txt_passwordPelanggan);
        txt_passwordUlang = findViewById(R.id.txt_passwordUlangPelanggan);
        btnRegister = findViewById(R.id.btnRegister);
        layoutTanggalLahir = findViewById(R.id.layoutTanggalLahir);
        btnLogin = findViewById(R.id.btnLogin);
        txt_emailPelanggan = findViewById(R.id.txt_emailPelanggan);
        backArrow = findViewById(R.id.backArrow);
    }

    private void SimpanPost(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(RegisterActivity.this);
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
                            if (response != null) {
                                Log.e(TAG, "URL " + url);
                                Log.e(TAG, "onResponseSimpan: " + response);
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test : " + success);

                                if (!success) {
//                                    Snack(response.getJSONArray("msgServer").toString());
                                    PeringatanDialog("Error" , root.get("msgServer").getAsString());
                                } else {
                                    Gson gson = new Gson();
                                    ResponseUser modelUser = gson.fromJson(String.valueOf(response), ResponseUser.class);
                                    String id = String.valueOf(modelUser.getMsgServer().getId());
                                    String name = modelUser.getMsgServer().getName();
                                    String email = modelUser.getMsgServer().getMainEmail();
                                    String membership = modelUser.getMsgServer().getStatusMember();
                                    String deadlinePay = modelUser.getMsgServer().getPayDate();
                                    String dateExpired = modelUser.getMsgServer().getExpiredDate();

                                    String count = modelUser.getMsgServer().getExpOtp();

                                    Date created = formatExpDate.parse(dateExpired);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(created);
                                    Log.e(TAG, "Expired member : " + cal.getTime());
                                    Date expiredMember = cal.getTime();
                                    String expDate = formatExp.format(expiredMember);

                                    sessionManager.setRegister(true, id, name, email, membership, expDate);
                                    sessionManager.setKeyExpotp(modelUser.getMsgServer().getExpOtp());

                                    Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                                    //                intent.putExtra("NAMA_MEMBER", NamaPelanggan);
                                    intent.putExtra("EXPIRED_OTP", count);
                                    startActivity(intent);

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
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(btnRegister, string, Snackbar.LENGTH_LONG).setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }

    private void PeringatanDialog(String judul, String Pesan) {
        Timer timer = new Timer();
        final long DELAY = 2000; // milliseconds
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setTitle(judul);
        alertDialog.setMessage(Pesan);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                            }
                        });

                    }
                },
                DELAY
        );

    }
}