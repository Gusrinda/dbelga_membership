package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UbahPassword extends AppCompatActivity {
    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass;
    LinearLayout mainLayout;
    String password, passwordBaruUser, passwordBaruUser2, passwordUser;
    TextInputEditText passwordLama, passwordBaru, passwordBaru2;
    Button btnSimpanPassword, btnKembali;
    private String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        sessionManager = new SessionManager(this);

        findID();
        getDataUser();

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSimpanPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordUser = passwordLama.getText().toString();
                passwordBaruUser = passwordBaru.getText().toString();
                passwordBaruUser2 = passwordBaru2.getText().toString();

                if (TextUtils.isEmpty(passwordUser)) {
                    Toast.makeText(UbahPassword.this, "Tolong isi password baru anda . . .", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passwordBaruUser)) {
                    Toast.makeText(UbahPassword.this, "Tolong isi kembali password baru anda . . .", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passwordBaruUser2)) {
                    Toast.makeText(UbahPassword.this, "Tolong isi kembali password baru anda", Toast.LENGTH_SHORT).show();
                } else {
                    if (!passwordUser.equals(password)) {
                        Toast.makeText(UbahPassword.this, "Pastikan anda mengisi password lama anda dengan benar !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (passwordBaruUser.equals(passwordUser)) {
                            Toast.makeText(UbahPassword.this, "Pastikan password baru beda dengan password lama !", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!passwordBaruUser.equals(passwordBaruUser2)) {
                                Toast.makeText(UbahPassword.this, "Pastikan anda mengisi field password baru sama !", Toast.LENGTH_SHORT).show();
                            } else {
                                updateAkunUser();
                            }
                        }
                    }
                }

            }
        });

    }

    private void getDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(UbahPassword.this);
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
                                JSONObject jsonObject = response.getJSONObject("msgServer");
                                password = jsonObject.getString("password");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(UbahPassword.this, "Tidak ada response", Toast.LENGTH_LONG).show();
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

    private void updateAkunUser() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UbahPassword.this);
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Simpan perubahan password anda ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (isOnline()) {
                            url = Http.server;
                            url = url + "update-password/" + sessionManager.getPID();
                            type = "post";
                            JSONObject postData = new JSONObject();
                            try {
                                postData.put("password", passwordBaruUser);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            if (isOnline()) {
                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClickSubmit: " + postData);
                                simpanPassword(postData);
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

    private void simpanPassword(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(UbahPassword.this);
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
                                    Toast.makeText(UbahPassword.this, "Ubah password berhasil !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UbahPassword.this, MainActivity.class);
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
                    Intent intent = new Intent(getApplicationContext(), UbahPassword.class);
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

    private void findID() {
        passwordLama = findViewById(R.id.passwordLama);
        passwordBaru = findViewById(R.id.passwordBaru);
        passwordBaru2 = findViewById(R.id.passwordBaru2);
        btnSimpanPassword = findViewById(R.id.btnSimpanPassword);
        btnKembali = findViewById(R.id.btnKembali);
        mainLayout = findViewById(R.id.mainLayout);
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(mainLayout, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        snackbar.show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}