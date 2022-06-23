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
import com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.ResponseCekVerifikasi;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.Screen.Notifikasi.Model.DataNotifikasi;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintFakturActivity;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiFoto;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.PembayaranMembership;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Log.e("", "cek UserIdentitas session : " + sessionManager.getKeyUseridentitas());
        Log.e("", "sessionCondition: Username Login? " + sessionManager.isLoggedIn());


        if (sessionManager.isLoggedIn()) {
            cekPreAccess = true;
            cekVerifikasiUser();
        } else {
            Intent intent = new Intent(SplashActivity.this, GudangActivity.class);
            startActivity(intent);
        }

    }

    private void cekVerifikasiUser() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ResponseCekVerifikasi> call = apiInterface.doCekVerifikasiUser(sessionManager.getPID());
        call.enqueue(new Callback<ResponseCekVerifikasi>() {
            @Override
            public void onResponse(Call<ResponseCekVerifikasi> call, Response<ResponseCekVerifikasi> response) {
                ResponseCekVerifikasi dataResponse = response.body();
                if (response.code() == 200) {
                    if (dataResponse.getSuccess()) {
                        com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.MsgServer dataVerifikasi = dataResponse.getMsgServer();
                        if (!dataVerifikasi.getVeirifikasiEmail()) {
                            Intent intent = new Intent(SplashActivity.this, VerificationActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (!dataVerifikasi.getIsThereFoto()) {
                                Intent intent = new Intent(SplashActivity.this, MembershipPilih.class);
                                intent.putExtra("pilihan_membership", dataResponse.getMsgServer().getMembership());
                                startActivity(intent);
                                finish();
                            } else {
                                if (!dataVerifikasi.getVeirifikasiFoto()) {
                                    Intent intent = new Intent(SplashActivity.this, KonfirmasiFoto.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (!dataVerifikasi.getIsTherePayment() || !dataVerifikasi.getVeirifikasiPayment()) {
//                                        Intent intent = new Intent(SplashActivity.this, KonfirmasiMembership.class);
                                        Intent intent = new Intent(SplashActivity.this, PembayaranMembership.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        onNewIntent(getIntent());
//                                        Intent intent = new Intent(SplashActivity.this, GudangActivity.class);
//                                        startActivity(intent);
//                                        finish();
                                    }

                                }
                            }
                        }
                    } else {
                        Toast.makeText(SplashActivity.this, "Error network getting data.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(SplashActivity.this, "Error Server !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseCekVerifikasi> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                Toast.makeText(SplashActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        Bundle extras = null;
        Intent goIntent;

        if (intent != null && intent.getExtras() != null && intent.getExtras().size() > 0) {
            extras = intent.getExtras();
            Log.e(TAG, "MASUK EXTRA");

            for (String key : extras.keySet()) {
                Log.e("myApplication", key + " is a key in the bundle");
            }

            String type;
            String context;
            String id;
            String codeContext;

            DataNotifikasi dataNotifikasi = null;

            if (extras.getBoolean("has_extra")) {
                Log.e(TAG, "TRUE");
                dataNotifikasi = extras.getParcelable("data_notifikasi");
                type = dataNotifikasi.getTipe();
                context = dataNotifikasi.getContext();
                id = dataNotifikasi.getId();
                codeContext = dataNotifikasi.getCode();
            } else {
                Log.e(TAG, "FALSE");
                type = extras.getString("tipe");
                context = extras.getString("context");
                id = extras.getString("id_context");
                codeContext = extras.getString("code");
            }

            Log.e(TAG, "CEK EXTRA CONTEXT : " + context);
            Log.e(TAG, "CEK EXTRA TYPE : " + type);
            Log.e(TAG, "CEK EXTRA ID : " + id);

            if (type != null) {
                if (type.equals("transaction")) {
                    if (context.equals("update")) {
                        goIntent = new Intent(this, PrintActivity.class);
                        goIntent.putExtra("hasExtra", true);
                        goIntent.putExtra("DATAPRINT", codeContext);
                        goIntent.putExtra("isFromNotifikasi", true);
                        finish();
                        startActivity(goIntent);
                    } else if (context.equals("shipment_dikirim") || context.equals("shipment_terkirim")) {
                        goIntent = new Intent(this, PrintActivity.class);
                        goIntent.putExtra("hasExtra", true);
                        goIntent.putExtra("DATAPRINT", codeContext);
                        goIntent.putExtra("isFromNotifikasi", true);
                        finish();
                        startActivity(goIntent);
                    } else if (context.equals("payment")) {
                        goIntent = new Intent(this, PrintFakturActivity.class);
                        goIntent.putExtra("hasExtra", true);
                        goIntent.putExtra("FAKTUR", true);
                        goIntent.putExtra("DATAPRINT", codeContext);
                        goIntent.putExtra("isFromNotifikasi", true);
                        finish();
                        startActivity(goIntent);
                    } else {
                        goIntent = new Intent(this, GudangActivity.class);
                        finish();
                        startActivity(goIntent);
                    }
                }
            } else {
                goIntent = new Intent(this, GudangActivity.class);
                finish();
                startActivity(goIntent);
            }

        } else {
            Log.e(TAG, "TAK PUNYA EXTRA");

            if (sessionManager.isLoggedIn()) {
                goIntent = new Intent(SplashActivity.this, GudangActivity.class);
                startActivity(goIntent);
                finish();
            } else {
                goIntent = new Intent(SplashActivity.this, GudangActivity.class);
                startActivity(goIntent);
                finish();
            }

        }

    }

}