package com.dbelgamembership.membersip.Screen.Limit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.ApiBanks;
import com.dbelgamembership.membersip.Helper.Constant;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelDataLimit.DetailLimitUser;
import com.dbelgamembership.membersip.Model.ModelListBank.Datum;
import com.dbelgamembership.membersip.Model.ModelListBank.ModelListBank;
import com.dbelgamembership.membersip.Model.ModelListTagihan.DaftarTagihanDenda;
import com.dbelgamembership.membersip.Model.ModelListTagihan.DaftarTagihanPeriode;
import com.dbelgamembership.membersip.Model.ModelListTagihan.ModelListTagihan;
import com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart;
import com.dbelgamembership.membersip.Model.ModelTagihanUser.ModelTagihanUser;
import com.dbelgamembership.membersip.Model.ModelTagihanUser.MsgServer;
import com.dbelgamembership.membersip.Model.ResponseBayarTagihan.ResponseBayarTagihan;
import com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.ResponseCekVerifikasi;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.Adapter.SpinnerBankAdapter;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Katalog.Model.PostBNI;
import com.dbelgamembership.membersip.Screen.Katalog.Model.PostBRI;
import com.dbelgamembership.membersip.Screen.Limit.Dummy.AdapterListDummyTagihan;
import com.dbelgamembership.membersip.Screen.Limit.Dummy.ModelItemBayarTagihan;
import com.dbelgamembership.membersip.Screen.Limit.ModelPelunasan.DetailTagihan;
import com.dbelgamembership.membersip.Screen.Limit.ModelPelunasan.ModelPelunasan;
import com.dbelgamembership.membersip.Screen.PembayaranTransfer.TransferTagihan;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintFakturActivity;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.PembayaranMembership;
import com.dbelgamembership.membersip.Screen.Voucher.VoucherActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterListDenda;
import com.dbelgamembership.membersip.app.Adapter.AdapterListTagihan;
import com.dbelgamembership.membersip.databinding.ActivityBayarTagihanBinding;
import com.dbelgamembership.membersip.databinding.FrameFotoBinding;
import com.dbelgamembership.membersip.databinding.PopupPilihPelunasanBinding;
import com.developer.kalert.KAlertDialog;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
//import com.midtrans.sdk.corekit.core.MidtransSDK;
//import com.midtrans.sdk.corekit.core.TransactionRequest;
//import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
//import com.midtrans.sdk.corekit.models.CustomerDetails;
//import com.midtrans.sdk.corekit.models.snap.TransactionResult;
//import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarTagihan extends AppCompatActivity implements AdapterListTagihan.AdapterListTransactionCallback, AdapterListDenda.AdapterListTransactionCallback {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityBayarTagihanBinding bayarTagihanBinding;
    private SessionManager sessionManager;

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    Bitmap bitmap;
    private String imageString = "";
    private Uri ImageUri;

    private boolean isUploadBuktiTransfer = false;
    ClipboardManager clipboardManager;
    private ImagePopup imagePopup;

    private boolean isBayarTransferDenda = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bayarTagihanBinding = ActivityBayarTagihanBinding.inflate(getLayoutInflater());
        View view = bayarTagihanBinding.getRoot();
        sessionManager = new SessionManager(this);
        setContentView(view);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        bayarTagihanBinding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        bayarTagihanBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (ContextCompat.checkSelfPermission(BayarTagihan.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BayarTagihan.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
        }

        getDataTagihanPeriode();
        getDaftarBankTagihan();

        bayarTagihanBinding.btnBayarTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                popUpPelunasanTagihan();
            }
        });

        bayarTagihanBinding.tabLayout.addTab(bayarTagihanBinding.tabLayout.newTab().setText("Tagihan"));
        bayarTagihanBinding.tabLayout.addTab(bayarTagihanBinding.tabLayout.newTab().setText("Denda"));

        bayarTagihanBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    bayarTagihanBinding.layoutTabTagihan.setVisibility(View.VISIBLE);
                    bayarTagihanBinding.layoutTabDenda.setVisibility(View.GONE);
                } else {
                    bayarTagihanBinding.layoutTabTagihan.setVisibility(View.GONE);
                    bayarTagihanBinding.layoutTabDenda.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bayarTagihanBinding.btnLunasiTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                Call<JsonElement> call = apiInterface.doGetDateServer();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                        if (response != null) {
                            String responseX = String.valueOf(response.body());
                            JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                            boolean success = root.get("success").getAsBoolean();
                            Log.e("", "Test : " + success);
                            if (!success) {
                                Toast.makeText(BayarTagihan.this, "ERROR :: " + root.get("msgServer").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String tanggalServer = root.get("msgServer").getAsString();
                                    Date checkJamServer = formatter.parse(tanggalServer);
                                    assert checkJamServer != null;
                                    Log.e(TAG, "onResponse TANGGAL SERVER : " + formatter.format(checkJamServer).toString());

                                    final Calendar batasan = Calendar.getInstance();
                                    batasan.set(Calendar.HOUR_OF_DAY, 8);
                                    batasan.set(Calendar.MINUTE, 30);
                                    batasan.set(Calendar.SECOND, 0);
                                    batasan.set(Calendar.MILLISECOND, 0);
                                    Date jamBukaHariIni = batasan.getTime();

                                    batasan.set(Calendar.HOUR_OF_DAY, 21);
                                    batasan.set(Calendar.MINUTE, 45);
                                    batasan.set(Calendar.SECOND, 0);
                                    batasan.set(Calendar.MILLISECOND, 0);
                                    Date jamTutupHariIni = batasan.getTime();

                                    Log.e(TAG, "onResponse BATAS JAM BUKA SERVER :  " + formatter.format(jamBukaHariIni).toString());
                                    Log.e(TAG, "onResponse BATAS JAM TUTUP SERVER :  " + formatter.format(jamTutupHariIni).toString());

                                    if (checkJamServer.getTime() >= jamBukaHariIni.getTime() && checkJamServer.getTime() <= jamTutupHariIni.getTime()) {
                                        popUpPelunasanTagihanAkhir(daftarTagihan);
                                    } else {
                                        Toast.makeText(BayarTagihan.this, "Tidak bisa pelunasan, tidak dalam jam pelayanan !", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (ParseException e) {
                                   e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                        Toast.makeText(BayarTagihan.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });

        bayarTagihanBinding.btnLunasiTagihanDenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                Call<JsonElement> call = apiInterface.doGetDateServer();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                        if (response != null) {
                            String responseX = String.valueOf(response.body());
                            JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                            boolean success = root.get("success").getAsBoolean();
                            Log.e("", "Test : " + success);
                            if (!success) {
                                Toast.makeText(BayarTagihan.this, "ERROR :: " + root.get("msgServer").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String tanggalServer = root.get("msgServer").getAsString();
                                    Date checkJamServer = formatter.parse(tanggalServer);
                                    assert checkJamServer != null;
                                    Log.e(TAG, "onResponse TANGGAL SERVER : " + formatter.format(checkJamServer).toString());

                                    final Calendar batasan = Calendar.getInstance();
                                    batasan.set(Calendar.HOUR_OF_DAY, 8);
                                    batasan.set(Calendar.MINUTE, 30);
                                    batasan.set(Calendar.SECOND, 0);
                                    batasan.set(Calendar.MILLISECOND, 0);
                                    Date jamBukaHariIni = batasan.getTime();

                                    batasan.set(Calendar.HOUR_OF_DAY, 21);
                                    batasan.set(Calendar.MINUTE, 45);
                                    batasan.set(Calendar.SECOND, 0);
                                    batasan.set(Calendar.MILLISECOND, 0);
                                    Date jamTutupHariIni = batasan.getTime();

                                    Log.e(TAG, "onResponse BATAS JAM BUKA SERVER :  " + formatter.format(jamBukaHariIni).toString());
                                    Log.e(TAG, "onResponse BATAS JAM TUTUP SERVER :  " + formatter.format(jamTutupHariIni).toString());

                                    if (checkJamServer.getTime() >= jamBukaHariIni.getTime() && checkJamServer.getTime() <= jamTutupHariIni.getTime()) {
                                        popUpPelunasanDendaAkhir(daftarTagihanDendas);
                                    } else {
                                        Toast.makeText(BayarTagihan.this, "Tidak bisa pelunasan, tidak dalam jam pelayanan !", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                        Toast.makeText(BayarTagihan.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        bayarTagihanBinding.btnHubungiAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = getResources().getString(R.string.nomor_wa_admin_1);
                String message = "Halo saya adalah Member Belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName() + " . . .";
                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mobileNumber + "&text=" + message));
                    startActivity(intent);
                } else {
                    Toast.makeText(BayarTagihan.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Create method appInstalledOrNot
    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    List<Datum> daftarBank = new ArrayList<>();

    private void getDaftarBankTagihan() {

//        '12','175','176','20','23','18', '25'

        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelListBank> call = apiInterface.doGetListBankTagihan();

        call.enqueue(new Callback<ModelListBank>() {
            @Override
            public void onResponse(Call<ModelListBank> call, Response<ModelListBank> response) {

                if (response.code() == 200) {

                    ModelListBank dataResponse = response.body();

                    if (dataResponse.getData().size() > 0) {

                        for (int i = 0; i < dataResponse.getData().size(); i++) {
                            Datum dataBank = dataResponse.getData().get(i);

                            int idCOA = dataBank.getIdCoa();

                            if (idCOA == 12 || idCOA == 175 || idCOA == 176 || idCOA == 20 || idCOA == 23 || idCOA == 18 || idCOA == 25) {
                                daftarBank.add(dataBank);
                            }

                        }

                        Log.e(TAG, "SIZE BANK :: " + daftarBank.size());

                    }

                }

            }

            @Override
            public void onFailure(Call<ModelListBank> call, Throwable t) {
                Toast.makeText(BayarTagihan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }

        });

    }

    ProgressDialog progressDialog = null;

    private void getDataTagihanPeriode() {

        progressDialog = ProgressDialog.show(BayarTagihan.this, "Getting Data . . .", "Please Wait...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelTagihanUser> callTagihan = apiInterface.doGetTagihanPeriodeIni(sessionManager.getPID());

        callTagihan.enqueue(new Callback<ModelTagihanUser>() {
            @Override
            public void onResponse(Call<ModelTagihanUser> call, Response<ModelTagihanUser> response) {
                if (response.code() == 200) {

                    ModelTagihanUser modelTagihanUser = response.body();

                    if (modelTagihanUser.getSuccess()) {

                        MsgServer dataTagihanUser = modelTagihanUser.getMsgServer();

                        getListTagihan();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(BayarTagihan.this, "Error :: TIDAK SUKSES", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(BayarTagihan.this, "Error :: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelTagihanUser> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BayarTagihan.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + " :: " + t.getStackTrace());
            }
        });
    }

    List<DaftarTagihanPeriode> daftarTagihan = new ArrayList<>();
    List<DaftarTagihanDenda> daftarTagihanDendas = new ArrayList<>();

    List<DaftarTagihanPeriode> daftarTagihanFinal = new ArrayList<>();
    List<DaftarTagihanDenda> daftarDendaFinal = new ArrayList<>();

    private void getListTagihan() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        int dayNow = c.get(Calendar.DAY_OF_MONTH);

        if (dayNow > 25) {
            month += 1;
        }

        Log.e(TAG, "getListTagihan HARI SEKARANG TANGGAL : " + dayNow);

        SimpleDateFormat tanggalPeriode = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tanggalBiarJelas = new SimpleDateFormat("dd-MM-yyyy");

        String tanggalSekarang = tanggalPeriode.format(c.getTime());
        String tanggalSekarangJelas = tanggalBiarJelas.format(c.getTime());

        bayarTagihanBinding.txtStatusTagihan.setText("Tagihan : Periode " + tanggalSekarangJelas + "");

        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelListTagihan> callListTagihan = apiInterface.doGetListTagihanUser(sessionManager.getPID(), tanggalSekarang);

        callListTagihan.enqueue(new Callback<ModelListTagihan>() {
            @Override
            public void onResponse(Call<ModelListTagihan> call, Response<ModelListTagihan> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {

                    ModelListTagihan modelDaftarTagihan = response.body();
                    bayarTagihanBinding.rvListTagihan.setAdapter(null);

                    boolean isThereIsWaiting = false;
                    double tagihanWaiting = 0;

                    if (modelDaftarTagihan.getSuccess()) {

                        if (modelDaftarTagihan.getMsgServer().getDaftarTagihanPeriode().size() > 0) {

                            daftarTagihan = modelDaftarTagihan.getMsgServer().getDaftarTagihanPeriode();


                            for (int i = 0; i < daftarTagihan.size(); i++) {

                                if (daftarTagihan.get(i).getStatusPayment() != null) {

                                    if (daftarTagihan.get(i).getStatusPayment().equals("WAITING") || daftarTagihan.get(i).getStatusPayment().equals("TRANSFER")) {
                                        isThereIsWaiting = true;
                                        tagihanWaiting += Double.parseDouble(daftarTagihan.get(i).getTotal());
                                    } else {
                                        daftarTagihanFinal.add(daftarTagihan.get(i));
                                    }
                                } else {
                                    daftarTagihanFinal.add(daftarTagihan.get(i));
                                }

                            }

                            AdapterListTagihan adapterListDummyTagihan = new AdapterListTagihan(BayarTagihan.this, daftarTagihanFinal, BayarTagihan.this);
                            bayarTagihanBinding.rvListTagihan.setAdapter(adapterListDummyTagihan);

                            if (daftarTagihanFinal.size() > 0) {
                                bayarTagihanBinding.rvListTagihan.setVisibility(View.VISIBLE);
                                bayarTagihanBinding.btnLunasiTagihan.setVisibility(View.VISIBLE);
                                bayarTagihanBinding.layoutKosongTagihan.setVisibility(View.GONE);
                            } else {
                                bayarTagihanBinding.txtTotalTabTagihan.setText("Total Tagihan : 0");
                                bayarTagihanBinding.rvListTagihan.setVisibility(View.GONE);
                                bayarTagihanBinding.btnLunasiTagihan.setVisibility(View.GONE);
                                bayarTagihanBinding.layoutKosongTagihan.setVisibility(View.VISIBLE);
                            }

                        } else {
                            bayarTagihanBinding.txtTotalTabTagihan.setText("Total Tagihan : 0");
                            bayarTagihanBinding.rvListTagihan.setVisibility(View.GONE);
                            bayarTagihanBinding.btnLunasiTagihan.setVisibility(View.GONE);
                            bayarTagihanBinding.layoutKosongTagihan.setVisibility(View.VISIBLE);
                        }

                        if (modelDaftarTagihan.getMsgServer().getDaftarTagihanDenda().size() > 0) {
                            daftarTagihanDendas = modelDaftarTagihan.getMsgServer().getDaftarTagihanDenda();

                            for (int i = 0; i < daftarTagihanDendas.size(); i++) {

                                if (daftarTagihanDendas.get(i).getStatusPayment() != null) {

                                    if (daftarTagihanDendas.get(i).getStatusPayment().equals("WAITING") || daftarTagihanDendas.get(i).getStatusPayment().equals("TRANSFER")) {
                                        isThereIsWaiting = true;
                                        tagihanWaiting += (Double.parseDouble(daftarTagihanDendas.get(i).getTotal()) + daftarTagihanDendas.get(i).getTotalDenda());
                                    } else {
                                        daftarDendaFinal.add(daftarTagihanDendas.get(i));
                                    }
                                } else {
                                    daftarDendaFinal.add(daftarTagihanDendas.get(i));
                                }
                            }

                            AdapterListDenda adapterListTagihanDenda = new AdapterListDenda(BayarTagihan.this, daftarDendaFinal, BayarTagihan.this);
                            bayarTagihanBinding.rvListDenda.setAdapter(adapterListTagihanDenda);

                            if (daftarDendaFinal.size() > 0) {
                                bayarTagihanBinding.rvListDenda.setVisibility(View.VISIBLE);
                                bayarTagihanBinding.btnLunasiTagihanDenda.setVisibility(View.VISIBLE);
                                bayarTagihanBinding.layoutKosongDenda.setVisibility(View.GONE);
                            } else {
                                bayarTagihanBinding.txtTotalTabDenda.setText("Total Denda : 0");
                                bayarTagihanBinding.rvListDenda.setVisibility(View.GONE);
                                bayarTagihanBinding.btnLunasiTagihanDenda.setVisibility(View.GONE);
                                bayarTagihanBinding.layoutKosongDenda.setVisibility(View.VISIBLE);
                            }

                        } else {
                            bayarTagihanBinding.txtTotalTabDenda.setText("Total Denda : 0");
                            bayarTagihanBinding.rvListDenda.setVisibility(View.GONE);
                            bayarTagihanBinding.btnLunasiTagihanDenda.setVisibility(View.GONE);
                            bayarTagihanBinding.layoutKosongDenda.setVisibility(View.VISIBLE);
                        }

                        bayarTagihanBinding.txtTotalTagihan.setText("Rp. " + nf.format(modelDaftarTagihan.getMsgServer().getLimitPenggunaan() + modelDaftarTagihan.getMsgServer().getTagihanDenda()));

                        if (isThereIsWaiting) {
                            bayarTagihanBinding.txtJikaAdaTagihanWaiting.setVisibility(View.VISIBLE);
                            bayarTagihanBinding.txtJikaAdaTagihanWaiting.setText("PEMBAYARAN WAITING APPROVAL : Rp. " + nf.format(tagihanWaiting));
                        }

                    } else {
                        Toast.makeText(BayarTagihan.this, "Error :: TIDAK SUKSES", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(BayarTagihan.this, "Error :: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelListTagihan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BayarTagihan.this, "ERROR :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                finish();
            }
        });

    }

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    private AlertDialog.Builder dialogBuilderFoto;
    private AlertDialog alertDialogFoto;

    private PopupPilihPelunasanBinding popupPilihPelunasanBinding;
    private FrameFotoBinding frameFotoBinding;

    ArrayAdapter<String> adapterBankPelunasan;
    //    Datum selectedBank;
    private String selectedBank = "";


    private void popUpPelunasanTagihanAkhir(List<DaftarTagihanPeriode> itemTagihan) {
        popupPilihPelunasanBinding = PopupPilihPelunasanBinding.inflate(getLayoutInflater());
        final View view = popupPilihPelunasanBinding.getRoot();

        dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        frameFotoBinding = FrameFotoBinding.inflate(getLayoutInflater());
        final View viewFoto = frameFotoBinding.getRoot();

        dialogBuilderFoto = new AlertDialog.Builder(BayarTagihan.this);

        dialogBuilderFoto.setView(viewFoto);
        alertDialogFoto = dialogBuilderFoto.create();
        alertDialogFoto.setCanceledOnTouchOutside(false);

//        popupPilihPelunasanBinding.layoutSpinnerBank.setVisibility(View.GONE);

        selectedBank = "";

//        String[] namaBanks = {"BRI VA", "BCA VA", "BNI VA", "MANDIRI VA"};
//        int iconBanks[] = {R.drawable.bri_icon, R.drawable.bca_icon, R.drawable.bni_icon, R.drawable.mandiri_icon};
        String[] namaBanks = {"BNI VA"};
        int iconBanks[] = { R.drawable.bni_icon};


        SpinnerBankAdapter customAdapter = new SpinnerBankAdapter(getApplicationContext(), iconBanks, namaBanks);
        popupPilihPelunasanBinding.spinnerBankVa.setAdapter(customAdapter);

        popupPilihPelunasanBinding.spinnerBankVa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBank = namaBanks[popupPilihPelunasanBinding.spinnerBankVa.getSelectedItemPosition()];
                Log.e(TAG, "onItemSelected BANK SELECTED :: " + selectedBank);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        double tagihanTransaksi = 0;
        double tagihanDenda = 0;
        double totalTagihan = 0;

        for (int i = 0; i < daftarTagihanFinal.size(); i++) {

            tagihanTransaksi += Double.parseDouble(daftarTagihanFinal.get(i).getTotalBersih());
            totalTagihan += (Double.parseDouble(daftarTagihanFinal.get(i).getTotalBersih()));

        }

        Log.e(TAG, "popUpPelunasanDendaAkhir :: TAGIHAN TRANSAKSI :: " + tagihanTransaksi);
        Log.e(TAG, "popUpPelunasanDendaAkhir :: TAGIHAN DENDA :: " + tagihanDenda);
        Log.e(TAG, "popUpPelunasanDendaAkhir :: TAGIHAN TOTAL :: " + totalTagihan);

        popupPilihPelunasanBinding.txtNominalTagihan.setText("Rp. " + nf.format(totalTagihan));

        popupPilihPelunasanBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == popupPilihPelunasanBinding.radioCOD.getId()) {
                    popupPilihPelunasanBinding.radioCOD.setChecked(true);
                    popupPilihPelunasanBinding.radioTransfer.setChecked(false);
                } else {
                    popupPilihPelunasanBinding.radioTransfer.setChecked(true);
                    popupPilihPelunasanBinding.radioCOD.setChecked(false);
                }
            }
        });

        popupPilihPelunasanBinding.radioCOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    popupPilihPelunasanBinding.layoutLangsung.setVisibility(View.VISIBLE);
                } else {
                    popupPilihPelunasanBinding.layoutLangsung.setVisibility(View.GONE);
                }
            }
        });

        popupPilihPelunasanBinding.radioTransfer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    popupPilihPelunasanBinding.layoutTransfer.setVisibility(View.VISIBLE);
                } else {
                    popupPilihPelunasanBinding.layoutTransfer.setVisibility(View.GONE);
                }
            }
        });

        popupPilihPelunasanBinding.produkClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        frameFotoBinding.closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogFoto.dismiss();
            }
        });

        popupPilihPelunasanBinding.layoutBtnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(BayarTagihan.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Lihat Transaksi")
                        .setContentText("Lunasi tagihan anda ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.material_deep_orange_600, BayarTagihan.this)
                        .cancelButtonColor(R.color.merahBelga, BayarTagihan.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                if (popupPilihPelunasanBinding.radioCOD.isChecked()) {
                                    pelunasanTagihan("LANGSUNG", daftarTagihan);
                                } else {
                                    pelunasanTagihan("TRANSFER", daftarTagihan);
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
        });
    }

    private void popUpPelunasanDendaAkhir(List<DaftarTagihanDenda> daftarTagihanDendas) {
        popupPilihPelunasanBinding = PopupPilihPelunasanBinding.inflate(getLayoutInflater());
        final View view = popupPilihPelunasanBinding.getRoot();

        dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        frameFotoBinding = FrameFotoBinding.inflate(getLayoutInflater());
        final View viewFoto = frameFotoBinding.getRoot();

        dialogBuilderFoto = new AlertDialog.Builder(BayarTagihan.this);

        dialogBuilderFoto.setView(viewFoto);
        alertDialogFoto = dialogBuilderFoto.create();
        alertDialogFoto.setCanceledOnTouchOutside(false);

//        String[] namaBanks = {"BRI VA", "BCA VA", "BNI VA", "Mandiri VA"};
//        int iconBanks[] = {R.drawable.bri_icon, R.drawable.bca_icon, R.drawable.bni_icon, R.drawable.mandiri_icon};

        String[] namaBanks = {"BNI VA"};
        int iconBanks[] = { R.drawable.bni_icon};

        SpinnerBankAdapter customAdapter = new SpinnerBankAdapter(getApplicationContext(), iconBanks, namaBanks);
        popupPilihPelunasanBinding.spinnerBankVa.setAdapter(customAdapter);

        popupPilihPelunasanBinding.spinnerBankVa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBank = namaBanks[popupPilihPelunasanBinding.spinnerBankVa.getSelectedItemPosition()];
                Log.e(TAG, "onItemSelected BANK SELECTED :: " + selectedBank);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        double tagihanTransaksi = 0;
        double tagihanDenda = 0;
        double totalTagihan = 0;

        for (int i = 0; i < daftarDendaFinal.size(); i++) {

            tagihanTransaksi += Double.parseDouble(daftarDendaFinal.get(i).getTotalBersih());
            tagihanDenda += daftarDendaFinal.get(i).getTotalDenda();

            totalTagihan += (Double.parseDouble(daftarDendaFinal.get(i).getTotalBersih()) + daftarDendaFinal.get(i).getTotalDenda());

        }

        Log.e(TAG, "popUpPelunasanDendaAkhir :: TAGIHAN TRANSAKSI :: " + tagihanTransaksi);
        Log.e(TAG, "popUpPelunasanDendaAkhir :: TAGIHAN DENDA :: " + tagihanDenda);
        Log.e(TAG, "popUpPelunasanDendaAkhir :: TAGIHAN TOTAL :: " + totalTagihan);

        popupPilihPelunasanBinding.txtNominalTagihan.setText("Rp. " + nf.format(totalTagihan));

        popupPilihPelunasanBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == popupPilihPelunasanBinding.radioCOD.getId()) {
                    popupPilihPelunasanBinding.radioCOD.setChecked(true);
                    popupPilihPelunasanBinding.radioTransfer.setChecked(false);
                } else {
                    popupPilihPelunasanBinding.radioTransfer.setChecked(true);
                    popupPilihPelunasanBinding.radioCOD.setChecked(false);
                }
            }
        });

        popupPilihPelunasanBinding.radioCOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    popupPilihPelunasanBinding.layoutLangsung.setVisibility(View.VISIBLE);
                } else {
                    popupPilihPelunasanBinding.layoutLangsung.setVisibility(View.GONE);
                }
            }
        });

        popupPilihPelunasanBinding.radioTransfer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    popupPilihPelunasanBinding.layoutTransfer.setVisibility(View.VISIBLE);
                } else {
                    popupPilihPelunasanBinding.layoutTransfer.setVisibility(View.GONE);
                }
            }
        });

        popupPilihPelunasanBinding.produkClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        frameFotoBinding.closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogFoto.dismiss();
            }
        });

        popupPilihPelunasanBinding.layoutBtnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isLate = false;

                APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                Call<JsonElement> call = apiInterface.doGetDateServer();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                        if (response != null) {
                            String responseX = String.valueOf(response.body());
                            JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                            boolean success = root.get("success").getAsBoolean();
                            Log.e("", "Test : " + success);
                            if (!success) {
                                Toast.makeText(BayarTagihan.this, "ERROR :: " + root.get("msgServer").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {

                                boolean bisaOrder = false;
                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String tanggalServer = root.get("msgServer").getAsString();
                                    Date checkJamServer = formatter.parse(tanggalServer);
                                    Log.e(TAG, "onResponse TANGGAL SERVER : " + formatter.format(checkJamServer).toString());

                                    final Calendar batasan = Calendar.getInstance();
                                    batasan.set(Calendar.HOUR_OF_DAY, 8);
                                    batasan.set(Calendar.MINUTE, 30);
                                    batasan.set(Calendar.SECOND, 0);
                                    batasan.set(Calendar.MILLISECOND, 0);
                                    Date jamBukaHariIni = batasan.getTime();

                                    batasan.set(Calendar.HOUR_OF_DAY, 21);
                                    batasan.set(Calendar.MINUTE, 45);
                                    batasan.set(Calendar.SECOND, 0);
                                    batasan.set(Calendar.MILLISECOND, 0);
                                    Date jamTutupHariIni = batasan.getTime();

                                    Log.e(TAG, "onResponse BATAS JAM BUKA SERVER :  " + formatter.format(jamBukaHariIni).toString());
                                    Log.e(TAG, "onResponse BATAS JAM TUTUP SERVER :  " + formatter.format(jamTutupHariIni).toString());

                                    if (checkJamServer.getTime() >= jamBukaHariIni.getTime() && checkJamServer.getTime() <= jamTutupHariIni.getTime()) {
                                        new KAlertDialog(BayarTagihan.this, KAlertDialog.WARNING_TYPE)
                                                .setTitleText("Lihat Transaksi")
                                                .setContentText("Lunasi tagihan anda ?")
                                                .setConfirmText("Ya")
                                                .confirmButtonColor(R.color.material_deep_orange_600, BayarTagihan.this)
                                                .cancelButtonColor(R.color.merahBelga, BayarTagihan.this)
                                                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                                    @Override
                                                    public void onClick(KAlertDialog sDialog) {
                                                        sDialog.dismissWithAnimation();
                                                        if (popupPilihPelunasanBinding.radioCOD.isChecked()) {
                                                            pelunasanTagihanDenda("LANGSUNG", daftarTagihanDendas);
                                                        } else {
                                                            pelunasanTagihanDenda("TRANSFER", daftarTagihanDendas);

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

                                    } else {
                                        Toast.makeText(BayarTagihan.this, "Tidak bisa membuat pelunasan, melebihi jam batasan 1", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                        Toast.makeText(BayarTagihan.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void pelunasanTagihanDenda(String caraBayar, List<DaftarTagihanDenda> daftarTagihanDendas) {

        ModelPelunasan modelPelunasanTagihan = new ModelPelunasan();

        String buktiGambar = imageString;
        modelPelunasanTagihan.setId_customer(sessionManager.getPID());
        modelPelunasanTagihan.setTipe_payment(caraBayar);
        modelPelunasanTagihan.setTipe_pelunasan("denda");

        if (caraBayar.equals("LANGSUNG")) {
            modelPelunasanTagihan.setId_bank(null);
            modelPelunasanTagihan.setNama_bank(null);
            modelPelunasanTagihan.setCoa_bank(null);
            modelPelunasanTagihan.setString_bukti(null);
        } else {
            modelPelunasanTagihan.setId_bank(null);
            modelPelunasanTagihan.setNama_bank(null);
            modelPelunasanTagihan.setCoa_bank(null);
            modelPelunasanTagihan.setString_bukti(null);
            modelPelunasanTagihan.setBank_payment(selectedBank.substring(0, selectedBank.length() - 3));

        }


        List<DetailTagihan> detailTagihansList = new ArrayList<>();

        double tagihanTransaksi = 0;
        double tagihanDenda = 0;
        double totalTagihan = 0;

        for (int i = 0; i < daftarDendaFinal.size(); i++) {
            DetailTagihan detailTagihan = new DetailTagihan();

            detailTagihan.setId_transaksi(String.valueOf(daftarDendaFinal.get(i).getId()));
            detailTagihan.setCode_transaksi(daftarDendaFinal.get(i).getPembayaranCode());
            detailTagihan.setTotal_transaksi(daftarDendaFinal.get(i).getTotalBersih());
            detailTagihan.setTotal_denda(String.valueOf(daftarDendaFinal.get(i).getTotalDenda()));

            tagihanTransaksi += Double.parseDouble(daftarDendaFinal.get(i).getTotalBersih());
            tagihanDenda += daftarDendaFinal.get(i).getTotalDenda();
            totalTagihan += (Double.parseDouble(daftarDendaFinal.get(i).getTotalBersih()) + daftarDendaFinal.get(i).getTotalDenda());

            detailTagihan.setGrand_total(String.valueOf(Double.parseDouble(daftarDendaFinal.get(i).getTotalBersih()) + daftarDendaFinal.get(i).getTotalDenda()));

            detailTagihansList.add(detailTagihan);
        }

        modelPelunasanTagihan.setDetail(detailTagihansList);
        modelPelunasanTagihan.setTotal_tagihan(tagihanTransaksi);
        modelPelunasanTagihan.setTotal_denda(tagihanDenda);
        modelPelunasanTagihan.setGrand_total(totalTagihan);

        sendDataPelunasanTagihan(modelPelunasanTagihan, caraBayar);

    }


    private void pelunasanTagihan(String caraBayar, List<DaftarTagihanPeriode> daftarTagihanPeriodes) {

        ModelPelunasan modelPelunasanTagihan = new ModelPelunasan();

        String buktiGambar = imageString;
        modelPelunasanTagihan.setId_customer(sessionManager.getPID());
        modelPelunasanTagihan.setTipe_payment(caraBayar);
        modelPelunasanTagihan.setTipe_pelunasan("tagihan");

        if (caraBayar.equals("LANGSUNG")) {
            modelPelunasanTagihan.setId_bank(null);
            modelPelunasanTagihan.setNama_bank(null);
            modelPelunasanTagihan.setCoa_bank(null);
            modelPelunasanTagihan.setString_bukti(null);
        } else {
            modelPelunasanTagihan.setId_bank(null);
            modelPelunasanTagihan.setNama_bank(null);
            modelPelunasanTagihan.setCoa_bank(null);
            modelPelunasanTagihan.setString_bukti(null);
            modelPelunasanTagihan.setBank_payment(selectedBank.substring(0, selectedBank.length() - 3));
        }


        List<DetailTagihan> detailTagihansList = new ArrayList<>();

        double tagihanTransaksi = 0;
        double tagihanDenda = 0;
        double totalTagihan = 0;

        for (int i = 0; i < daftarTagihanFinal.size(); i++) {
            DetailTagihan detailTagihan = new DetailTagihan();

            detailTagihan.setId_transaksi(String.valueOf(daftarTagihanFinal.get(i).getId()));
            detailTagihan.setCode_transaksi(daftarTagihanFinal.get(i).getPembayaranCode());
            detailTagihan.setTotal_transaksi(daftarTagihanFinal.get(i).getTotalBersih());

            tagihanTransaksi += Double.parseDouble(daftarTagihanFinal.get(i).getTotalBersih());
            tagihanDenda += 0;
            totalTagihan += (Double.parseDouble(daftarTagihanFinal.get(i).getTotalBersih()) + 0);

            detailTagihan.setTotal_denda("0");
            detailTagihan.setGrand_total(String.valueOf(Double.parseDouble(daftarTagihanFinal.get(i).getTotalBersih()) + 0));

            detailTagihansList.add(detailTagihan);
        }

        modelPelunasanTagihan.setDetail(detailTagihansList);
        modelPelunasanTagihan.setTotal_tagihan(tagihanTransaksi);
        modelPelunasanTagihan.setTotal_denda(tagihanDenda);
        modelPelunasanTagihan.setGrand_total(totalTagihan);

        sendDataPelunasanTagihan(modelPelunasanTagihan, caraBayar);

    }

    private void sendDataPelunasanTagihan(ModelPelunasan modelPelunasan, String caraBayar) {

        try {

            String jsonInString = new Gson().toJson(modelPelunasan);
            JSONObject mJSONObject = new JSONObject(jsonInString);

            Log.e(TAG, "POST DATA :: " + mJSONObject.toString());

            Writer output = null;
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File pdfFile = new File(folder, "contohPostDataTagihan.json");
            output = new BufferedWriter(new FileWriter(pdfFile));
            output.write(mJSONObject.toString());
            output.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog progressDialog = ProgressDialog.show(BayarTagihan.this, "Loading", "Setting Up Payment ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ResponseBayarTagihan> call = apiInterface.doPelunasanTagihan(modelPelunasan);

        call.enqueue(new Callback<ResponseBayarTagihan>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<ResponseBayarTagihan> call, Response<ResponseBayarTagihan> response) {
                try {
                    progressDialog.dismiss();
                    alertDialog.dismiss();
                    if (!caraBayar.equals("LANGSUNG")) {

                        if (!selectedBank.isEmpty()) {

                            if (selectedBank.equals("BRI VA")) {

                                //BRI
                                setupPembuatanVA(response.body());
                            }
                            else if (selectedBank.equals("BNI VA")) {

                                //BRI
                                setupPembuatanVABNI(response.body());
                            }
                            else if (selectedBank.equals("BCA VA")) {

                            } else if (selectedBank.equals("MANDIRI VA")) {

                            }

                        }

                    } else {
                        finish();
                    }
//                    finish();
                    Log.e(TAG, "onResponse: " + response.toString());

                } catch (Exception e) {
                    progressDialog.dismiss();
                    alertDialog.dismiss();
                    finish();
                    Toast.makeText(BayarTagihan.this, "ERROR !", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse Error message : " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBayarTagihan> call, Throwable t) {
                progressDialog.dismiss();
                alertDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupPembuatanVA(ResponseBayarTagihan responseBayarTagihan) {

        com.dbelgamembership.membersip.Model.ResponseBayarTagihan.MsgServer dataTagihan = responseBayarTagihan.getMsgServer().get(0);

        String lastTwo = dataTagihan.getPdCode().substring(dataTagihan.getPdCode().length() - 2);

        String lastTwoDate = dataTagihan.getPaymentDate().substring(dataTagihan.getPaymentDate().length() - 2);

        String customerCode = "09" + sessionManager.getPID() + lastTwoDate + lastTwo;

        double grandTotal = 0;

        for (int i = 0; i < responseBayarTagihan.getMsgServer().size(); i++) {
            grandTotal += Double.parseDouble(responseBayarTagihan.getMsgServer().get(i).getGrandTotal());
        }

        Log.e(TAG, "setupPembuatanVA :: " + grandTotal);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, 1);

        String dateExpired = formatter.format(cal.getTime());


        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        String formattedDate = now.toString();

        Log.e(TAG, "onCreate: FORMATTED DATE :: " + formattedDate);

        try {

            StringBuilder stringPayload = new StringBuilder();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("amount", String.valueOf((int) Math.round(grandTotal)));
            jsonObject.put("brivaNo", "77777");
            jsonObject.put("custCode", customerCode);
            jsonObject.put("expiredDate", dateExpired);
            jsonObject.put("institutionCode", "J104408");
            jsonObject.put("keterangan", "Pembayaran Tagihan :: " + dataTagihan.getPdCode());
            jsonObject.put("nama", sessionManager.getName());


            Log.e(TAG, "setupPembuatanVATransfer :: " + jsonObject.toString());

            stringPayload.append("path=/v1/briva")
                    .append("&verb=POST")
                    .append("&token=Bearer ")
                    .append(ApiBanks.BRI_TOKEN.getAccessToken()).append("&timestamp=").append(formattedDate).append("&body=")
                    .append(jsonObject.toString());

            PostBRI postBRI = new PostBRI(
                    "J104408",
                    "77777",
                    customerCode,
                    sessionManager.getName(),
                    String.valueOf((int) Math.round(grandTotal)),
                    "Pembayaran Tagihan :: " + dataTagihan.getPdCode(),
                    dateExpired
            );

            Log.e(TAG, "setupPembuatanVATransfer STRING PAYLOAD :: " + stringPayload);

            String sign = hash_hmac(stringPayload.toString(), ApiBanks.BRI_CONSUMER_SECRET);
            String bearerToken = "Bearer " + ApiBanks.BRI_TOKEN.getAccessToken();

            final ProgressDialog progressDialog = ProgressDialog.show(BayarTagihan.this, "Loading", "Pembuatan VA ...");
            APIInterface apiInterface = APIClient.getClient(ApiBanks.urlBRI).create(APIInterface.class);
            Call<JsonElement> call = apiInterface.bri_createEndPointVA(
                    bearerToken,
                    formattedDate,
                    sign,
                    "application/json",
                    postBRI
            );

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                    progressDialog.dismiss();
                    try {
                        if (response != null) {
                            Toast.makeText(BayarTagihan.this, "Pembuatan VA berhasil, Lakukan Pelunasan Transfer !", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(BayarTagihan.this, TransferTagihan.class);
                            intent.putExtra("hasExtra", true);
                            intent.putExtra("banks", "BRI");
                            intent.putExtra("kode_payment", customerCode);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                        Toast.makeText(BayarTagihan.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, "onResponse: " + t.getMessage());
                    Toast.makeText(BayarTagihan.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupPembuatanVABNI(ResponseBayarTagihan responseBayarTagihan) {

        com.dbelgamembership.membersip.Model.ResponseBayarTagihan.MsgServer dataTagihan = responseBayarTagihan.getMsgServer().get(0);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        Date expiredLocalTime = calendar.getTime();

        String offset = "+07:00";
        LocalDateTime ldt = LocalDateTime.ofInstant(expiredLocalTime.toInstant(), ZoneId.systemDefault());
        ZoneOffset zoneOffset = ZoneOffset.of(offset);
        OffsetDateTime odt = OffsetDateTime.of(ldt, zoneOffset);


        String timeStamp = String.valueOf(System.currentTimeMillis());
        String formattedDate = odt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        double grandTotal = 0;

        for (int i = 0; i < responseBayarTagihan.getMsgServer().size(); i++) {
            Log.e(TAG, "setupPembuatanVABNI: ------ " + i );
            Log.e(TAG, "setupPembuatanVABNI: GRAND TOTAL SEBELUM :: " + grandTotal);
            Log.e(TAG, "setupPembuatanVABNI: GRAND TOTAL TAMBAH :: " + responseBayarTagihan.getMsgServer().get(i).getGrandTotal());
            grandTotal += Double.parseDouble(responseBayarTagihan.getMsgServer().get(i).getGrandTotal());
            Log.e(TAG, "setupPembuatanVABNI: GRAND TOTAL SETELAH :: " + grandTotal);
        }

        Log.e(TAG, "setupPembuatanVABNI: GRAND TOTAL AKHIR :: " + grandTotal);

//        double grandTotal = Double.parseDouble(responseBayarTagihan.getMsgServer().get(0).getTotalTagihan());

        try {

            String lastFoutStamp = timeStamp.substring(timeStamp.length() - 4);
            int idCustomer = Integer.parseInt(sessionManager.getPID());

            String with4digits = String.format("%04d", idCustomer);

            String templateVANumber = "98816055" + with4digits.substring(0, 4) + lastFoutStamp;

            PostBNI postBNI = new PostBNI(
                    "TAGIHAN",
                    dataTagihan.getPdCode(),
                    "createbilling",
                    "16055",
                    dataTagihan.getPdCode() + "-" + timeStamp,
                    String.valueOf(Math.round(grandTotal)),
                    "c",
                    sessionManager.getName(),
                    sessionManager.getEmail(),
                    sessionManager.getKeyTelefonMember(),
                    templateVANumber,
                    formattedDate,
                    "TRANSAKSI PELUNASAN DEBIT : " + dataTagihan.getPdCode()
            );


            final ProgressDialog progressDialog = ProgressDialog.show(BayarTagihan.this, "Loading", "Pembuatan VA ...");
            APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
            Call<JsonElement> call = apiInterface.bni_createEndPointVA(
                    postBNI
            );

            double finalGrandTotal = grandTotal;
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                    try {
                        progressDialog.dismiss();
                        if (response != null) {
                            Toast.makeText(BayarTagihan.this, "Pembuatan VA berhasil, Lakukan Pelunasan Transfer !", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(BayarTagihan.this, TransferTagihan.class);
                            intent.putExtra("hasExtra", true);
                            intent.putExtra("banks", "BNI");
                            intent.putExtra("dataTagihan", String.valueOf((int) finalGrandTotal));
                            intent.putExtra("kode_payment", dataTagihan.getPdCode() + "-" + timeStamp);
                            intent.putExtra("kode_tagihan", dataTagihan.getPdCode());
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                        Toast.makeText(BayarTagihan.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, "onResponse: " + t.getMessage());
                    Toast.makeText(BayarTagihan.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String hash_hmac(String str, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        String hash = Base64.encodeToString(sha256_HMAC.doFinal(str.getBytes()), Base64.NO_WRAP);

        return hash;
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP);
    }

    @Override
    public void onRowLunasi(DaftarTagihanPeriode item) {
//        popUpPelunasanTagihan(item);
    }

    @Override
    public void onRowDetailTransaksi(DaftarTagihanPeriode item) {
        new KAlertDialog(BayarTagihan.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Lihat Transaksi")
                .setContentText("Anda ingin melihat detail transaksi " + item.getPembayaranCode() + " ?\n\n")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.material_deep_orange_600, BayarTagihan.this)
                .cancelButtonColor(R.color.merahBelga, BayarTagihan.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(BayarTagihan.this, PrintFakturActivity.class);
                        String DataOOS = item.getPembayaranCode();
                        Log.e(TAG, "onRowAdapterListTransactionClicked: " + DataOOS);
                        intent.putExtra("DATAPRINT", DataOOS);
                        intent.putExtra("FAKTUR", true);
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

    @Override
    public void onRowLunasi(DaftarTagihanDenda item) {
//        popUpPelunasanDenda(item);
    }

    @Override
    public void onRowDetailTransaksi(DaftarTagihanDenda item) {
        new KAlertDialog(BayarTagihan.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Lihat Transaksi")
                .setContentText("Anda ingin melihat detail transaksi " + item.getPembayaranCode() + " ?\n\n")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.material_deep_orange_600, BayarTagihan.this)
                .cancelButtonColor(R.color.merahBelga, BayarTagihan.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(BayarTagihan.this, PrintFakturActivity.class);
                        String DataOOS = item.getPembayaranCode();
                        Log.e(TAG, "onRowAdapterListTransactionClicked: " + DataOOS);
                        intent.putExtra("DATAPRINT", DataOOS);
                        intent.putExtra("FAKTUR", true);
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

}