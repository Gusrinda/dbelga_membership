package com.dbelgamembership.membersip.Screen.Katalog;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.ApiBanks;
import com.dbelgamembership.membersip.Model.Api_Banks.BriToken.BriToken;
import com.dbelgamembership.membersip.Model.ModelCompetitor;
import com.dbelgamembership.membersip.Screen.HomeActivity;
import com.dbelgamembership.membersip.Screen.Katalog.Model.AlamatPengiriman;
import com.dbelgamembership.membersip.Screen.Katalog.Model.modelPostLocation;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Screen.Maps.MapsActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.ListTransaksi;
import com.dbelgamembership.membersip.Screen.User.AkunSaya;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.PembayaranMembership;
import com.dbelgamembership.membersip.app.Adapter.AdapterListGudang;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.ModelResponseDistance.ModelResponseDistance;
import com.dbelgamembership.membersip.Model.ModelToko.ModelGudang;
import com.dbelgamembership.membersip.Model.ModelToko.ModelToko;
import com.dbelgamembership.membersip.Model.ModelToko.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ActivityGudangBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.maps.android.SphericalUtil;
import com.google.type.DateTime;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
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
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GudangActivity extends AppCompatActivity implements AdapterListGudang.AdapterListGudangCallback {

    private static final String TAG = "GudangActivity";
    private ActivityGudangBinding binding;
    private SessionManager sessionManager;
    ProgressDialog progressDialog;
    public static List<ModelGudang> modelGudangs = new ArrayList<>();
    public static List<ModelGudang> daftarGudangToko = new ArrayList<>();
    public static HashMap<String, String> daftarGudang = new HashMap<String, String>();
    public static double jarak;
    ModelResponseCart modelResponseCart;

    public static AlamatPengiriman alamatPengirimanPengguna;

    Location locationPublic;
    LatLng latLngPublick;

    private boolean isSetAlamat = false;

    private FusedLocationProviderClient fusedClient;

    private int cartSize = 0;
    private String idGudangCart = "";

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Log.e(TAG, "onBackPressed: BACK PRESSED !!!");
        finishAffinity();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            binding.txtVersi.setText("Version. " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        alamatPengirimanPengguna = new AlamatPengiriman(null, null, null);

        sessionManager = new SessionManager(this);

        try {
            permissionRequest();
        } catch (Exception e) {
            Toast.makeText(GudangActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onCreate: " + Arrays.toString(e.getStackTrace()));
        }

        binding.btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GudangActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        binding.btnAlamatPengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GudangActivity.this, MapsActivity.class);
                intent.putExtra("hasLocation", true);

                if (isSetAlamat) {
                    intent.putExtra("location", alamatPengirimanPengguna.getLatLng());
                } else {
                    intent.putExtra("location", latLngPublick);

                }
                startActivityForResult(intent, 1);
            }
        });

        binding.btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(GudangActivity.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout")
                        .setContentText("Anda akan keluar dari sesi aplikasi")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, GudangActivity.this)
                        .cancelButtonColor(R.color.grey_font, GudangActivity.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                                sessionManager.destroySession();
                                Intent intent = new Intent(GudangActivity.this, SplashActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

        setupTokenApiBanks();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                permissionRequest();
            }
        });

    }

    private void setupTokenApiBanks() {

        //Setup BRI
//        setupTokenBRI();

        //Setup BCA
        //Setup BNI
        //Setup Mandiri


    }

    private void setupTokenBRI() {
        APIInterface apiInterface = APIClient.getClient(ApiBanks.urlBRI).create(APIInterface.class);
        Call<BriToken> call = apiInterface.getTokenBRI(ApiBanks.BRI_CUNSOMER_KEY, ApiBanks.BRI_CONSUMER_SECRET);

        call.enqueue(new Callback<BriToken>() {
            @Override
            public void onResponse(Call<BriToken> call, Response<BriToken> response) {
                try {
                    if (response != null) {
                        ApiBanks.BRI_TOKEN = response.body();
                        Log.e(TAG, "BRI RESPONSE :: BERHASIL !!");

                        assert response.body() != null;
                        ApiBanks.BRI_TOKEN_STRING = response.body().getAccessToken();
                        sessionManager.setKeyTokenBriApi(response.body().getAccessToken());
                        Log.e(TAG, "TOKEN BRI :: " + ApiBanks.BRI_TOKEN.getAccessToken());

                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    Toast.makeText(GudangActivity.this, "Error Get Token BRI !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BriToken> call, Throwable t) {
                Toast.makeText(GudangActivity.this, "Error Get Token BRI !", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void permissionRequest() {
        binding.swipeRefresh.setRefreshing(false);
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();

                            Log.e(TAG, "onCreate: finally");
                            fusedClient = LocationServices.getFusedLocationProviderClient(GudangActivity.this);
                            getLastLocation();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    private void setupDataUser() {
        if (sessionManager.isLoggedIn()) {
            binding.btnLoginRegister.setVisibility(View.GONE);
            binding.btnKeluar.setVisibility(View.VISIBLE);
            Log.e(TAG, "setupDataUser: " + sessionManager.getImage());

            if (!sessionManager.getImage().equals("") && !sessionManager.getImage().equals("null")) {
                Log.d(TAG, "setupDataUser: MASUK SINI");
                Glide.with(GudangActivity.this).asBitmap().load(sessionManager.getImage()).centerCrop().error(R.drawable.user_kosong).into(binding.imgCustomer);
            } else {
                Log.d(TAG, "setupDataUser: MASUK SITU");
                @SuppressLint("UseCompatLoadingForDrawables") Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
                binding.imgCustomer.setImageDrawable(image);
            }

            binding.txtHi.setText("Hi there, " + sessionManager.getName());

            sendTokenUser();


        } else {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
            binding.imgCustomer.setImageDrawable(image);
            binding.btnKeluar.setVisibility(View.GONE);
            binding.btnLoginRegister.setVisibility(View.VISIBLE);
        }

        setupListGudang();
    }

    private void sendTokenUser() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(TAG, token);
                        setTokenCustomer(token);
//                        Toast.makeText(GudangActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setTokenCustomer(String s) {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doSetTokenCustomer(
                sessionManager.getPID(),
                s
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JsonObject root = new JsonParser().parse(String.valueOf(response.body())).getAsJsonObject();
                        boolean check = root.get("success").getAsBoolean();
                        if (!check) {
                            Toast.makeText(GudangActivity.this, jsonObject.getString("msgServer"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onResponse: " + response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupListGudang() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelToko> call = apiInterface.doGetToko();
        call.enqueue(new Callback<ModelToko>() {
            @Override
            public void onResponse(Call<ModelToko> call, Response<ModelToko> response) {

                binding.rvGudang.setAdapter(null);

                StringBuilder locDestinations = new StringBuilder();

                modelGudangs.clear();
                daftarGudangToko.clear();

                for (int i = 0; i < response.body().getMsgServer().size(); i++) {
                    MsgServer dataGudang = response.body().getMsgServer().get(i);

                    daftarGudangToko.add(new ModelGudang(
                            dataGudang.getName(),
                            dataGudang.getAddress(),
                            dataGudang.getId().toString(),
                            dataGudang.getGeoLat(),
                            dataGudang.getGeoLng(),
                            "", 0,
                            dataGudang.getNoTelp()));

                    if (dataGudang.getId() == 8 || dataGudang.getId() == 9) {
                        String desti = dataGudang.getGeoLat() + "," + dataGudang.getGeoLng() + "|";

                        locDestinations.append(desti);
                        modelGudangs.add(new ModelGudang(
                                dataGudang.getName(),
                                dataGudang.getAddress(),
                                dataGudang.getId().toString(),
                                dataGudang.getGeoLat(),
                                dataGudang.getGeoLng(),
                                "", 0,
                                dataGudang.getNoTelp()));
                    }

                }

                for (int i = 0; i < modelGudangs.size(); i++) {
                    daftarGudang.put(String.valueOf(modelGudangs.get(i).getIdGudang()), modelGudangs.get(i).getNamaGudang());
                }

                List<ModelCompetitor> daftarLokasiCompetitor = new ArrayList<>();

                LatLng latlngSahabat = new LatLng(
                        -8.053179, 111.887217
                );

                LatLng latlngNikisae = new LatLng(
                        -8.055057, 111.898706
                );

                LatLng latlngGolden = new LatLng(
                        -8.065288, 111.904139
                );

                LatLng latlngBravo = new LatLng(
                        -8.076315, 111.916993
                );

                daftarLokasiCompetitor.add(new ModelCompetitor(
                        "Sahabat",
                        latlngSahabat
                ));
                daftarLokasiCompetitor.add(new ModelCompetitor(
                        "Nikisae",
                        latlngNikisae
                ));
                daftarLokasiCompetitor.add(new ModelCompetitor(
                        "Bravo",
                        latlngBravo
                ));
                daftarLokasiCompetitor.add(new ModelCompetitor(
                        "Golden",
                        latlngGolden
                ));

                modelPostLocation postData = new modelPostLocation();

                boolean isOnArea = false;

                for (int i = 0; i < daftarLokasiCompetitor.size(); i++) {
                    HashMap<String, String> mapLog = new HashMap<String, String>();
                    Double distance = SphericalUtil.computeDistanceBetween(
                            new LatLng(locationPublic.getLatitude(), locationPublic.getLongitude())
                            , daftarLokasiCompetitor.get(i).getLokasi());

                    Log.e(TAG, "JARAK LOKASI KE " + daftarLokasiCompetitor.get(i).getName() + " = " + distance + " m");

                    if (distance < 100) {
                        postData.setCompetitor(daftarLokasiCompetitor.get(i).getName());
                        isOnArea = true;
                        Log.e(TAG, "IN SCOPE");
                    } else {
                        Log.e(TAG, "OUT OF SCOPE");
                    }
                }

                postData.setIdCustomer(sessionManager.getPID().equals("") ? "0" : sessionManager.getPID());
                postData.setNameCustomer(sessionManager.getName().equals("") ? "Guest" : sessionManager.getName());
                postData.setLattitude(String.valueOf(locationPublic.getLatitude()));
                postData.setLongitude(String.valueOf(locationPublic.getLongitude()));
                postData.setAddress(alamatPertamaTetap);
                postData.setOn_area(isOnArea ? true : false);

                Call<String> postLocation = apiInterface.doPostLocation(postData);

                postLocation.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e(TAG, "Response : \n" + response.body());
                        if (isSetAlamat) {
                            String locSetAlamat = alamatPengirimanPengguna.getLatLng().latitude + "," + alamatPengirimanPengguna.getLatLng().longitude;
                            sessionManager.setLatLong(String.valueOf(alamatPengirimanPengguna.getLatLng().latitude), String.valueOf(alamatPengirimanPengguna.getLatLng().longitude));
                            gettingDistance(locSetAlamat, locDestinations.toString());
                        } else {
                            String locOrigins = locationPublic.getLatitude() + "," + locationPublic.getLongitude();
                            sessionManager.setLatLong(String.valueOf(locationPublic.getLatitude()), String.valueOf(locationPublic.getLongitude()));
                            gettingDistance(locOrigins, locDestinations.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<ModelToko> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }

        });
    }

    private void gettingDistance(String origin, String destinasi) {

        APIInterface apiInterface = APIClient.getClient(Http.mapsGoogle).create(APIInterface.class);
        Call<ModelResponseDistance> getMaps = apiInterface.doGetDistance(origin, destinasi, "driving", "AIzaSyC0NMGZYXcRkiWqPGU5hJZ2wOi4Vl7DtRY");

        getMaps.enqueue(new Callback<ModelResponseDistance>() {
            @Override
            public void onResponse(Call<ModelResponseDistance> call, Response<ModelResponseDistance> response) {
                Log.e(TAG, "onResponse: " + response.body());

                ModelResponseDistance modelResponseDistance = response.body();

                boolean isThereFalseAddress = false;

                for (int i = 0; i < modelResponseDistance.getRows().get(0).getElements().size(); i++) {
                    ModelGudang baru = modelGudangs.get(i);

                    if (modelResponseDistance.getRows().get(0).getElements().get(i).getStatus().equals("ZERO_RESULTS")) {
                        isThereFalseAddress = true;
                    } else {
                        baru.setTextJarak(modelResponseDistance.getRows().get(0).getElements().get(i).getDistance().getText());
                        baru.setValueJarak(modelResponseDistance.getRows().get(0).getElements().get(i).getDistance().getValue());
                        modelGudangs.set(i, baru);
                    }

                }

                if (isThereFalseAddress) {
                    progressDialog.dismiss();

//                    Toast.makeText(GudangActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(GudangActivity.this).create();
                    alertDialog.setTitle("Error GOOGLE MAPS : ");
                    alertDialog.setMessage("Pastikan aplikasi maps sudah terinstall dan dapat dijalankan ! Check Maps ?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q="));
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(mapIntent);
                                    } else {
                                        Snackbar.make(binding.lnContent, "Google apps is not installed", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog.show();


                } else {
                    Log.e(TAG, "onResponse SIZE : " + modelGudangs.size());

                    AdapterListGudang adapterListGudang = new AdapterListGudang(GudangActivity.this, modelGudangs, GudangActivity.this);
                    binding.rvGudang.setAdapter(adapterListGudang);

                    if (isSetAlamat) {
                        binding.txtAlamatPengiriman.setText(alamatPengirimanPengguna.getAlamatPengiriman());
                        sessionManager.setAlamatPengiriman(alamatPengirimanPengguna.getAlamatPengiriman());
                    } else {
                        binding.txtAlamatPengiriman.setText(alamatPertamaTetap);
                        sessionManager.setAlamatPengiriman(alamatPertamaTetap);

                    }


                    if (sessionManager.isLoggedIn()) {
                        SearchingCart();
                    } else {
                        progressDialog.dismiss();
                    }
                }


            }

            @Override
            public void onFailure(Call<ModelResponseDistance> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
                finish();
            }
        });
    }

    private void SearchingCart() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doDetailCart(sessionManager.getPID());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                progressDialog.dismiss();

                try {
                    JSONObject obj = new JSONObject(response.body());

                    boolean success = obj.getBoolean("success");
                    String msgServer = obj.get("msgServer").toString();

                    if (success) {
                        Gson gson = new Gson();
                        modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);
                        assert modelResponseCart != null;

                        for (int i = 0; i < modelGudangs.size(); i++) {
                            if (modelGudangs.get(i).getIdGudang().equals(String.valueOf(modelResponseCart.getMsgServer().getIdGudang()))) {
                                jarak = modelGudangs.get(i).getValueJarak() / 1000;
                            }
                        }

                        cartSize = modelResponseCart.getMsgServer().getDetailItemCart().size();
                        idGudangCart = String.valueOf(modelResponseCart.getMsgServer().getIdGudang());

                        AlertDialog alertDialogAwal = new AlertDialog.Builder(GudangActivity.this).create();
                        alertDialogAwal.setCanceledOnTouchOutside(false);
                        alertDialogAwal.setTitle("Hi, " + (sessionManager.isLoggedIn() ? sessionManager.getName() : "Customer"));
                        alertDialogAwal.setMessage("Anda sudah punya cart di toko " + modelResponseCart.getMsgServer().getNamaGudang() + ", Lanjut melihat katalog disana ?");
                        alertDialogAwal.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(GudangActivity.this, KatalogActivity.class);
                                        Intent intent = new Intent(GudangActivity.this, NewMainActivity.class);
                                        sessionManager.setKeySetGudangPencarian(String.valueOf(modelResponseCart.getMsgServer().getIdGudang()));
                                        sessionManager.setKeyGudangPilihan(String.valueOf(modelResponseCart.getMsgServer().getIdGudang()));
                                        intent.putExtra("hasExtra", true);
                                        intent.putExtra("idGudang", String.valueOf(modelResponseCart.getMsgServer().getIdGudang()));
                                        intent.putExtra("namaGudang", modelResponseCart.getMsgServer().getNamaGudang());
                                        startActivity(intent);
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                        alertDialogAwal.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialogAwal.setButton(AlertDialog.BUTTON_NEUTRAL, "HAPUS CART", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog alertDialog = new AlertDialog.Builder(GudangActivity.this).create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.setTitle("Peringatan");
                                alertDialog.setMessage("Memilih 'HAPUS' akan menghapus cart yang dibuat pada toko sebelumnya !\nAnda yakin ?");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                                clearingCart();
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                                alertDialogAwal.show();
                                            }
                                        });
                                alertDialog.show();
                            }
                        });
                        alertDialogAwal.show();

                    } else {

                        Log.e(TAG, "onResponse: " + msgServer);
                        cartSize = 0;
                        idGudangCart = "";

                        if (!sessionManager.getKeyGudangPilihan().isEmpty()) {

                            String idGudang = sessionManager.getKeyGudangPilihan();
                            Log.e(TAG, "onResponse PUNYA ID GUDANG: : " + idGudang);

                            String namaGudang = "";

                            for (int i = 0; i < daftarGudangToko.size(); i++) {
                                if (daftarGudangToko.get(i).getIdGudang().equals(idGudang)) {
                                    namaGudang = daftarGudangToko.get(i).getNamaGudang();
                                }
                            }

                            Intent intent = new Intent(GudangActivity.this, NewMainActivity.class);
                            sessionManager.setKeySetGudangPencarian(idGudang);
                            intent.putExtra("hasExtra", true);
                            intent.putExtra("idGudang", idGudang);
                            intent.putExtra("namaGudang", namaGudang);
                            startActivity(intent);
                            finish();

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GudangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
                finish();
            }
        });

    }

    private void clearingCart() {
        final ProgressDialog progressDialog = ProgressDialog.show(GudangActivity.this, "Loading", "Deleting data ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doEmptyCart(sessionManager.getPID());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GudangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private String alamatPertamaTetap = "";

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        progressDialog = ProgressDialog.show(GudangActivity.this, "Loading", "Please Wait...");
        fusedClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            locationPublic = location;
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            latLngPublick = new LatLng(latitude, longitude);

                            Log.e(TAG, "onSuccess LAT : " + latitude);
                            Log.e(TAG, "onSuccess LONG : " + longitude);

                            Geocoder geocoder = new Geocoder(GudangActivity.this, Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                Address selectedAddress = null;
                                boolean alreadySelected = false;

                                for (int i = 0; i < addresses.size(); i++) {
                                    if (!alreadySelected) {
                                        if (addresses.get(i).getThoroughfare() != null) {
                                            alreadySelected = true;
                                            selectedAddress = addresses.get(i);
                                        }
                                    }
                                }

                                alamatPertamaTetap = selectedAddress.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                                setupDataUser();

                            } catch (IOException e) {
                                progressDialog.dismiss();
                                e.printStackTrace();
                                Toast.makeText(GudangActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                AlertDialog alertDialog = new AlertDialog.Builder(GudangActivity.this).create();
                                alertDialog.setTitle("Error GOOGLE MAPS : " + e.getLocalizedMessage());
                                alertDialog.setMessage("Pastikan aplikasi maps sudah terinstall dan dapat dijalankan ! Check Maps ?");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
//                                                getLastLocation();
                                                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latitude + "," + longitude));
                                                mapIntent.setPackage("com.google.android.apps.maps");
                                                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(mapIntent);
                                                } else {
                                                    Snackbar.make(binding.lnContent, "Google apps is not installed", Snackbar.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                alertDialog.show();

                            }

                        } else {
                            progressDialog.dismiss();
                            LocationRequest locationRequest = new LocationRequest()
                                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                    .setInterval(10000)
                                    .setFastestInterval(1000)
                                    .setNumUpdates(1);

                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    finish();
                                    startActivity(new Intent(GudangActivity.this, GudangActivity.class));
                                }
                            };

                            AlertDialog alertDialog = new AlertDialog.Builder(GudangActivity.this).create();
                            alertDialog.setTitle("Hi, " + sessionManager.getName());
                            alertDialog.setMessage("Lokasi belum diambil, ambil sekarang ?");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                            fusedClient = LocationServices.getFusedLocationProviderClient(GudangActivity.this);

                                            fusedClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                            alertDialog.show();

                        }
                    }
                });
    }


    @Override
    public void AdapterListGudang(int position) {

        if (cartSize > 0 && !modelGudangs.get(position).getIdGudang().equals(idGudangCart)) {

            AlertDialog alertDialogAwal = new AlertDialog.Builder(GudangActivity.this).create();
            alertDialogAwal.setCanceledOnTouchOutside(false);
            alertDialogAwal.setTitle("Hi, " + (sessionManager.isLoggedIn() ? sessionManager.getName() : "Customer"));
            alertDialogAwal.setMessage("Anda sudah punya cart di toko " + modelResponseCart.getMsgServer().getNamaGudang() + ", Lanjut melihat katalog disana ?");
            alertDialogAwal.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(GudangActivity.this, KatalogActivity.class);
                            Intent intent = new Intent(GudangActivity.this, NewMainActivity.class);
                            sessionManager.setKeySetGudangPencarian(String.valueOf(modelResponseCart.getMsgServer().getIdGudang()));
                            intent.putExtra("hasExtra", true);
                            intent.putExtra("idGudang", String.valueOf(modelResponseCart.getMsgServer().getIdGudang()));
                            intent.putExtra("namaGudang", modelResponseCart.getMsgServer().getNamaGudang());
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialogAwal.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialogAwal.setButton(AlertDialog.BUTTON_NEUTRAL, "HAPUS CART", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog alertDialog = new AlertDialog.Builder(GudangActivity.this).create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setTitle("Peringatan");
                    alertDialog.setMessage("Memilih 'HAPUS' akan menghapus cart yang dibuat pada toko sebelumnya !\nAnda yakin ?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                    clearingCart();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                    alertDialogAwal.show();
                                }
                            });
                    alertDialog.show();
                }
            });
            alertDialogAwal.show();
        } else {
            if (cartSize == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(GudangActivity.this).create();
                alertDialog.setTitle("Hi, " + (sessionManager.isLoggedIn() ? sessionManager.getName() : "Customer"));
                alertDialog.setMessage("Anda akan memilih gudang " + modelGudangs.get(position).getNamaGudang() + " ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GudangActivity.this, NewMainActivity.class);
//                        Intent intent = new Intent(GudangActivity.this, KatalogActivity.class);
                                intent.putExtra("hasExtra", true);
                                sessionManager.setKeySetGudangPencarian(modelGudangs.get(position).getIdGudang());
                                intent.putExtra("idGudang", modelGudangs.get(position).getIdGudang());
                                intent.putExtra("namaGudang", modelGudangs.get(position).getNamaGudang());
                                jarak = modelGudangs.get(position).getValueJarak() / 1000;
                                startActivity(intent);
                                dialog.dismiss();
                                finish();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            } else {
                Intent intent = new Intent(GudangActivity.this, NewMainActivity.class);
                sessionManager.setKeySetGudangPencarian(String.valueOf(modelResponseCart.getMsgServer().getIdGudang()));
                intent.putExtra("hasExtra", true);
                intent.putExtra("idGudang", String.valueOf(modelResponseCart.getMsgServer().getIdGudang()));
                intent.putExtra("namaGudang", modelResponseCart.getMsgServer().getNamaGudang());
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                if (resultCode == -1) {
                    Log.e(TAG, "onActivityResult: " + data);
                    if (data != null) {
                        if (data.hasExtra("hasSetAlamat")) {
                            isSetAlamat = true;
                            getLastLocation();
                        }
                    } else {
                        Log.e(TAG, "onActivityResult: data " + data);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onActivityResult: Exception " + e.getMessage());
        }
    }

}