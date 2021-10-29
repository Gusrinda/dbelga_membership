package com.dbelgamembership.membersip.Screen.Katalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Screen.Transaksi.ListTransaksi;
import com.dbelgamembership.membersip.Screen.User.AkunSaya;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    public static double jarak;

    Location locationPublic;

    private FusedLocationProviderClient fusedClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(this);

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

    }

    private void setupDataUser() {

        Log.e(TAG, "setupDataUser: " + sessionManager.getImage());

        if (!sessionManager.getImage().equals("")) {
            Glide.with(GudangActivity.this).asBitmap().load(sessionManager.getImage()).centerCrop().into(binding.imgCustomer);
        } else {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
            binding.imgCustomer.setImageDrawable(image);
        }

        binding.txtHi.setText("Hi there, " + sessionManager.getName());

        setupListGudang();
    }

    private void setupListGudang() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelToko> call = apiInterface.doGetToko();
        call.enqueue(new Callback<ModelToko>() {
            @Override
            public void onResponse(Call<ModelToko> call, Response<ModelToko> response) {

                binding.rvGudang.setAdapter(null);

                String locOrigins = locationPublic.getLatitude() + "," + locationPublic.getLongitude();
                StringBuilder locDestinations = new StringBuilder();

                modelGudangs.clear();

                for (int i = 0; i < response.body().getMsgServer().size(); i++) {
                    MsgServer dataGudang = response.body().getMsgServer().get(i);

                    if (dataGudang.getId() == 8 || dataGudang.getId() == 9) {
//                    if (dataGudang.getId() == 8 || dataGudang.getId() == 9 || dataGudang.getId() == 10) {

                        String desti = dataGudang.getGeoLat() + "," + dataGudang.getGeoLng() + "|";

                        locDestinations.append(desti);

                        modelGudangs.add(new ModelGudang(
                                dataGudang.getName(),
                                dataGudang.getAddress(),
                                dataGudang.getId().toString(),
                                dataGudang.getGeoLat(),
                                dataGudang.getGeoLng(),
                                "", 0));

                    }

                }

                gettingDistance(locOrigins, locDestinations.toString());
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


                for (int i = 0; i < modelResponseDistance.getRows().get(0).getElements().size(); i++) {
                    ModelGudang baru = modelGudangs.get(i);
                    baru.setTextJarak(modelResponseDistance.getRows().get(0).getElements().get(i).getDistance().getText());
                    baru.setValueJarak(modelResponseDistance.getRows().get(0).getElements().get(i).getDistance().getValue());
                    modelGudangs.set(i, baru);
                }

                Log.e(TAG, "onResponse SIZE : " + modelGudangs.size());

                AdapterListGudang adapterListGudang = new AdapterListGudang(GudangActivity.this, modelGudangs, GudangActivity.this);
                binding.rvGudang.setAdapter(adapterListGudang);

                SearchingCart();


            }

            @Override
            public void onFailure(Call<ModelResponseDistance> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
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
                        ModelResponseCart modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);
                        assert modelResponseCart != null;

                        for (int i = 0; i < modelGudangs.size(); i++) {
                            if (modelGudangs.get(i).getIdGudang().equals(String.valueOf(modelResponseCart.getMsgServer().getIdGudang()))) {

                                jarak = modelGudangs.get(i).getValueJarak() / 1000;
                            }
                        }

                        AlertDialog alertDialogAwal = new AlertDialog.Builder(GudangActivity.this).create();
                        alertDialogAwal.setCanceledOnTouchOutside(false);
                        alertDialogAwal.setTitle("Hi, " + sessionManager.getName());
                        alertDialogAwal.setMessage("Anda sudah punya cart di toko " + modelResponseCart.getMsgServer().getNamaGudang() + ", Lanjut melihat katalog disana ?");
                        alertDialogAwal.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(GudangActivity.this, KatalogActivity.class);
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
                                        AlertDialog alertDialog = new AlertDialog.Builder(GudangActivity.this).create();
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.setTitle("Peringatan");
                                        alertDialog.setMessage("Memilih 'TIDAK' akan menghapus cart yang dibuat pada toko sebelumnya !\nAnda yakin ?");
                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialogInterface, int which) {
                                                        dialogInterface.dismiss();
                                                        dialog.dismiss();
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
//                        setupWishlist();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GudangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
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

                            Log.e(TAG, "onSuccess LAT : " + latitude);
                            Log.e(TAG, "onSuccess LONG : " + longitude);

                            Geocoder geocoder = new Geocoder(GudangActivity.this, Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();

                                Log.e(TAG, "onClick ALAMAT : " + address);
                                Log.e(TAG, "onClick KECAMATAN : " + city);
                                Log.e(TAG, "onClick PROVINSI : " + state);
                                Log.e(TAG, "onClick NEGARA : " + country);
                                Log.e(TAG, "onClick KODEPOS : " + postalCode);
                                Log.e(TAG, "onClick KNOWNNAME : " + knownName);

                                setupDataUser();
                                sessionManager.setAlamatPengiriman(address);

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
        AlertDialog alertDialog = new AlertDialog.Builder(GudangActivity.this).create();
        alertDialog.setTitle("Hi, " + sessionManager.getName());
        alertDialog.setMessage("Lihat katalog gudang " + modelGudangs.get(position).getNamaGudang() + " ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(GudangActivity.this, KatalogActivity.class);
                        intent.putExtra("hasExtra", true);
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

    }
}