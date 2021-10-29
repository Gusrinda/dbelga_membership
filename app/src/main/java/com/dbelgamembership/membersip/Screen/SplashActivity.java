package com.dbelgamembership.membersip.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelGetKategori.ModelGetKategori;
import com.dbelgamembership.membersip.Model.ModelGetKategori.MsgServer;
import com.dbelgamembership.membersip.Model.ModelGetSlider.Datum;
import com.dbelgamembership.membersip.Model.ModelGetSlider.ModelGetSlider;
import com.dbelgamembership.membersip.Model.ModelResponseCS.ModelResponseCS;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.VerificationActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SplashActivity extends AppCompatActivity {
    public static boolean cekPreAccess;
    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass;

    private static final String TAG = "SPLASH";
    public static String[] listKategori;
    public static HashMap<String, String> listCategory = new HashMap<String, String>();
    public static List<MsgServer> daftarKategori;
    public static List<String> listArrayKategori = new ArrayList<>();

    public static String[] listGambarSlider;
    public static HashMap<String, String> listImageSlider = new HashMap<String, String>();
    public static List<Datum> daftarGambarSlider;

    public static List<com.dbelgamembership.membersip.Model.ModelResponseCS.MsgServer> daftarCS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        cekPreAccess = false;
        sessionManager = new SessionManager(this);

        getDataSlider();
        getDataKategori();
        getDataCS();

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    getSession();
//                    getDataUser();
                }
            }
        };
        timerThread.start();

    }

    private void getDataCS() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelResponseCS> call = apiInterface.doGetCs();
        call.enqueue(new Callback<ModelResponseCS>() {
            @Override
            public void onResponse(Call<ModelResponseCS> call, retrofit2.Response<ModelResponseCS> response) {
                if (response.code() == 200) {
                   ModelResponseCS responseCS = response.body();

                    assert responseCS != null;
                    daftarCS = responseCS.getMsgServer();


                } else {
                    Toast.makeText(getApplicationContext(), "Kesalahan memuat data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelResponseCS> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung, Coba Lagi!", Toast.LENGTH_SHORT).show();
            }
        });
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
                        if (response != null) {
                            Log.e("", "onResponse: " + response);
                            String responseX = String.valueOf(response);
                            JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                            boolean success = root.get("success").getAsBoolean();
                            Log.e("", "Test : " + success);
                            if (!success) {
//                                    Toast.makeText(SplashActivity.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                Log.e(TAG, "onResponse: " + root.get("msgServer").getAsString());
                                sessionManager.destroySession();
                                getSession();
                            } else {
                                Gson gson = new Gson();
                                ModelUser modelMember = gson.fromJson(String.valueOf(response), ModelUser.class);
                                com.dbelgamembership.membersip.Model.ModelUser.MsgServer dataMember = modelMember.getMsgServer().get(0);
                                boolean status_pay = Boolean.parseBoolean(dataMember.getStatusPayment());

                                if (dataMember.isEmailVerification()) {
                                    if (status_pay) {
                                        finish();
                                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        String deadlinePay = dataMember.getPayDate();
                                        Intent intent = new Intent(SplashActivity.this, KonfirmasiMembership.class);
                                        Log.e(TAG, "onResponse: " + deadlinePay);
                                        intent.putExtra("TANGGAL_DEADLINE", deadlinePay);
                                        startActivity(intent);
                                    }
                                } else {
                                    Intent intent = new Intent(SplashActivity.this, VerificationActivity.class);
                                    startActivity(intent);
                                }


                            }
                        } else {
//                            Toast.makeText(HomeActivity.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

//                        Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void getDataSlider() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelGetSlider> call = apiInterface.doGetDataSlider();
        call.enqueue(new Callback<ModelGetSlider>() {
            @Override
            public void onResponse(Call<ModelGetSlider> call, retrofit2.Response<ModelGetSlider> response) {
                if (response.code() == 200) {
                    ModelGetSlider modelGetKategori = response.body();

                    daftarGambarSlider = modelGetKategori.getData();

                    Log.e(TAG, "Size daftar divisi : " + daftarGambarSlider.size());

                    List<String> arraySlider = new ArrayList<String>();

                    for (int i = 0; i < daftarGambarSlider.size(); i++) {
                        Log.e(TAG, "Link : " + daftarGambarSlider.get(i).getImage());
                        arraySlider.add(daftarGambarSlider.get(i).getImage());
                    }

                    listGambarSlider = new ArrayList<String>(new LinkedHashSet<String>(arraySlider)).toArray(new String[0]);


                    Log.e(TAG, "Daftar Divisi" + listGambarSlider);


                } else {
                    Toast.makeText(getApplicationContext(), "Kesalahan memuat data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelGetSlider> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung, Coba Lagi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataKategori() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelGetKategori> call = apiInterface.doGetDataKategori();
        call.enqueue(new Callback<ModelGetKategori>() {
            @Override
            public void onResponse(Call<ModelGetKategori> call, retrofit2.Response<ModelGetKategori> response) {
                if (response.code() == 200) {
                    ModelGetKategori modelGetKategori = response.body();

                    daftarKategori = modelGetKategori.getMsgServer();

                    Log.e(TAG, "Size daftar divisi : " + daftarKategori.size());

                    List<String> arrayKategori = new ArrayList<String>();

                    for (int i = 0; i < daftarKategori.size(); i++) {
                        Log.e(TAG, "Nama divisi : " + daftarKategori.get(i).getName());
                        arrayKategori.add(daftarKategori.get(i).getName());
                    }

                    listKategori = new ArrayList<String>(new LinkedHashSet<String>(arrayKategori)).toArray(new String[0]);

                    listArrayKategori.add("SEMUA KATEGORI");

                    for (int i = 0; i < arrayKategori.size(); i++) {
                        listArrayKategori.add(arrayKategori.get(i));
                    }


                    Log.e(TAG, "Daftar Divisi" + listArrayKategori.toString());


                } else {
                    Toast.makeText(getApplicationContext(), "Kesalahan memuat data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelGetKategori> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung, Coba Lagi!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getSession() {
        Log.e("", "cek Nama session : " + sessionManager.getName());
        Log.e("", "cek PID session : " + sessionManager.getPID());
        Log.e("", "cek Email session : " + sessionManager.getEmail());
        Log.e("", "sessionCondition: Username Login? " + sessionManager.isLoggedIn());
        if (sessionManager.isLoggedIn()) {
            cekPreAccess = true;
            getDataUser();
        } else {
            cekPreAccess = false;
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }



}