package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Model.ModelGetKategori.ModelGetKategori;
import com.dbelgamembership.membersip.Model.ModelGetKategori.MsgServer;
import com.dbelgamembership.membersip.Model.ModelGetSlider.Datum;
import com.dbelgamembership.membersip.Model.ModelGetSlider.ModelGetSlider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH";
    public static String[] listKategori;
    public static HashMap<String, String> listCategory = new HashMap<String, String>();
    public static List<MsgServer> daftarKategori;
    public static List<String> listArrayKategori = new ArrayList<>();


    public static String[] listGambarSlider;
    public static HashMap<String, String> listImageSlider = new HashMap<String, String>();
    public static List<Datum> daftarGambarSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getDataSlider();
        getDataKategori();



    }

    private void getDataSlider() {
        final ProgressDialog dialog1 = new ProgressDialog(SplashActivity.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelGetSlider> call = apiInterface.doGetDataSlider();
        call.enqueue(new Callback<ModelGetSlider>() {
            @Override
            public void onResponse(Call<ModelGetSlider> call, retrofit2.Response<ModelGetSlider> response) {
                dialog1.dismiss();
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
                dialog1.dismiss();
                Toast.makeText(getApplicationContext(), "Gagal Terhubung, Coba Lagi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataKategori() {
        final ProgressDialog dialog1 = new ProgressDialog(SplashActivity.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelGetKategori> call = apiInterface.doGetDataKategori();
        call.enqueue(new Callback<ModelGetKategori>() {
            @Override
            public void onResponse(Call<ModelGetKategori> call, retrofit2.Response<ModelGetKategori> response) {
                dialog1.dismiss();
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

                    Thread timerThread = new Thread() {
                        public void run() {
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    };
                    timerThread.start();


                } else {
                    Toast.makeText(getApplicationContext(), "Kesalahan memuat data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelGetKategori> call, Throwable t) {
                dialog1.dismiss();
                Toast.makeText(getApplicationContext(), "Gagal Terhubung, Coba Lagi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}