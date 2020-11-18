package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.LinearLayout;
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

public class LoginActivity extends AppCompatActivity {
    public static boolean cekPreAccess;
    SessionManager sessionManager;

    public String url = Http.server, jsonResult, type,user,pass;

    EditText usernameInput, passwordInput;
    Button btnLogin;
    LinearLayout btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        cekPreAccess = false;

        Log.e("", "cek Nama session : " + sessionManager.getName() );
        Log.e("", "cek PID session : " + sessionManager.getPID() );
        Log.e("", "cek Email session : " + sessionManager.getEmail() );

        findId();
        getSession();

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
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btn_Login);
        btnRegister = findViewById(R.id.btnDaftar);
    }

    private void loginUser() {
        String UsernamePelanggan = usernameInput.getText().toString();
        String PasswordPelanggan = passwordInput.getText().toString();

        if (TextUtils.isEmpty(UsernamePelanggan)) {
            Toast.makeText(this, "Tolong isi username anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PasswordPelanggan)) {
            Toast.makeText(this, "Tolong isi password anda . . . ", Toast.LENGTH_SHORT).show();
        } else {
            if(isOnline() == true){
                url = Http.server;
                url = url+"user-gudang-login?username="+user+"&password="+pass;
                Log.e( "URL: ", url);
                accessWebService();
            }else{
                Toast.makeText(LoginActivity.this, "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
            }
        }

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
                                if (success == false){
                                    Toast.makeText(LoginActivity.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    JSONObject jsonObject = response.getJSONObject("msgServer");
                                    String idUser = jsonObject.getString("id");
                                    String namaUser = jsonObject.getString("name");
                                    String emailUser = jsonObject.getString("email");
                                    String membershipUser = jsonObject.getString("membership");
                                    Log.e("", "id User: " + idUser);
                                    Log.e("", "nama User: " + namaUser);
                                    Log.e("", "email User: " + emailUser);
                                    Log.e("", "membership: " + membershipUser);
                                    sessionManager.setLogin(true,idUser,namaUser, emailUser, membershipUser);
                                    getSession();
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
        if (netInfo != null && netInfo.isConnectedOrConnecting()) { return true; }
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

}