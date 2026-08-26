package com.dbelgamembership.membersip.Screen.Maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelToko.ModelGudang;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.Katalog.Model.AlamatPengiriman;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.databinding.ActivityMapsBinding;
import com.dbelgamembership.membersip.databinding.PopupBarangBinding;
import com.dbelgamembership.membersip.databinding.PopupGetAlamatBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private final String TAG = this.getClass().getSimpleName();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LatLng lokasi;

    SessionManager sessionManager;
    private FusedLocationProviderClient fusedClient;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        searchView = binding.searchView;
        sessionManager = new SessionManager(this);

        if (getIntent().hasExtra("hasLocation")) {
            lokasi = getIntent().getParcelableExtra("location");
        } else {
            finish();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        binding.getPosisiSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fusedClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                getLastLocation();
            }
        });

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng position = markerFinal.getPosition();
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                    final AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setIcon(R.drawable.dbelga)
                            .setTitle("Peringatan !")
                            .setMessage("Set Alamat Pengiriman ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(position.latitude, position.longitude, 5);

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

                                        Intent intent = new Intent();
                                        GudangActivity.alamatPengirimanPengguna = new AlamatPengiriman(position, addresses, binding.txtAlamatPengiriman.getText().toString());
                                        sessionManager.setLatLong(String.valueOf(position.latitude), String.valueOf(position.longitude));
                                        assert selectedAddress != null;
                                        sessionManager.setAlamatPengiriman(selectedAddress.getAddressLine(0));
                                        intent.putExtra("hasSetAlamat", true);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    dialog.dismiss();
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();

            }
        });

    }

    ProgressDialog progressDialog;

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        progressDialog = ProgressDialog.show(MapsActivity.this, "Loading", "Please Wait...");
        fusedClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            LatLng latLng = new LatLng(latitude, longitude);

                            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                            try {
                                progressDialog.dismiss();
                                List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);

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

                                Log.e(TAG, "onMapReady: " + addresses);
                                String address = selectedAddress.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                binding.txtAlamatPengiriman.setText(address);
                                markerFinal.remove();
                                markedLocationPengguna = new MarkerOptions().position(latLng).title("Pindah kesini");
                                markerFinal = mMap.addMarker(markedLocationPengguna);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                            } catch (IOException e) {
                                e.printStackTrace();
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
                                    startActivity(new Intent(MapsActivity.this, MapsActivity.class));
                                }
                            };

                            AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
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

    Marker markerFinal;
    MarkerOptions markedLocationPengguna = new MarkerOptions();
    Marker markerToko;
    MarkerOptions markedLocationToko = new MarkerOptions();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(this::onMarkerClick);



        LatLng lokasiSaatIni = new LatLng(lokasi.latitude, lokasi.longitude);

        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

        for (int i = 0; i < GudangActivity.modelGudangs.size(); i++) {

            ModelGudang toko = GudangActivity.modelGudangs.get(i);

            double lat = Double.parseDouble(toko.getLatGudang());
            double lon = Double.parseDouble(toko.getLongGudang());

            boolean isGanjil = false;

            if ((i % 2) == 0) {
                isGanjil = false;
            } else {
                isGanjil = true;
            }

            LatLng latLngToko = new LatLng(lat, lon);

            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lon))
                    .radius(10000)
                    .strokeWidth(0)
                    .strokeColor(isGanjil ? Color.RED : Color.GREEN)
                    .fillColor(isGanjil ? Color.parseColor("#22e77d71") : Color.parseColor("#2271cce7")));
            markedLocationToko = new MarkerOptions().position(latLngToko).title(toko.getNamaGudang()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            markerToko = mMap.addMarker(markedLocationToko);
            markerToko.showInfoWindow();
        }

        try {
            Log.e(TAG, "onMapReady LAT :: " + lokasi.latitude);
            Log.e(TAG, "onMapReady LON :: " + lokasi.longitude);

            double lat = lokasi.latitude;
            double lon = lokasi.longitude;

            Log.e(TAG, "onMapReady LAT 2 :: " + lat);
            Log.e(TAG, "onMapReady LON 2 :: " + lon);

            List<Address> addresses = geocoder.getFromLocation(lat, lon, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            Log.e(TAG, "onMapReady: " + addresses);

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

            String address = selectedAddress.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            markedLocationPengguna.position(lokasiSaatIni).title(address).draggable(true);
            markerFinal = mMap.addMarker(markedLocationPengguna);
            binding.txtAlamatPengiriman.setText(address);
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiSaatIni, zoomLevel));

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(@NonNull LatLng latLng) {

                    // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);

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

                        Log.e(TAG, "onMapReady: " + addresses);
                        String address = selectedAddress.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        binding.txtAlamatPengiriman.setText(address);
                        markerFinal.remove();
                        markedLocationPengguna = new MarkerOptions().position(latLng).title("Pindah kesini");
                        markerFinal = mMap.addMarker(markedLocationPengguna);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    progressDialog = ProgressDialog.show(MapsActivity.this, "Loading", "Please Wait...");
                    String location = searchView.getQuery().toString();

                    Log.e(TAG, "onQueryTextSubmit: LOCATION :: " + location );

                    List<Address> addressList = null;

                    if (location != null || !location.equals("")) {
                        Geocoder geocoder = new Geocoder(MapsActivity.this);


                        try {
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (addressList != null) {
                            progressDialog.dismiss();
                            if (addressList.size() > 0) {
                                Address address = addressList.get(0);
                                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                String alaamt = address.getAddressLine(0);
                                binding.txtAlamatPengiriman.setText(alaamt);
                                markerFinal.remove();
                                markedLocationPengguna = new MarkerOptions().position(latLng).title("Pindah kesini");
                                markerFinal = mMap.addMarker(markedLocationPengguna);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                            } else {
                                Toast.makeText(MapsActivity.this, "Location not found !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MapsActivity.this, "Location not found !", Toast.LENGTH_SHORT).show();

                        }
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Log.e(TAG, "onMarkerClick: " + marker);
        if (marker.equals(markerFinal)) {
            LatLng position = marker.getPosition();
            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1);
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                popUpSettingAlamat(position, addresses);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }
        }
        return true;
    }

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private PopupGetAlamatBinding popupGetAlamatBinding;

    private void popUpSettingAlamat(LatLng position, List<Address> addresses) {
        dialogBuilder = new AlertDialog.Builder(this);
        popupGetAlamatBinding = PopupGetAlamatBinding.inflate(getLayoutInflater());
        final View alamatPop = popupGetAlamatBinding.getRoot();
        dialogBuilder.setView(alamatPop);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        String alamat = addresses.get(0).getAddressLine(0);
        popupGetAlamatBinding.txtAlamatPengiriman.setText(alamat);

        popupGetAlamatBinding.btnUbahAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupGetAlamatBinding.txtAlamatPengiriman.requestFocus();
                popupGetAlamatBinding.txtAlamatPengiriman.setFocusable(true);
                popupGetAlamatBinding.txtAlamatPengiriman.setFocusableInTouchMode(true);
            }
        });

        popupGetAlamatBinding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        popupGetAlamatBinding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setIcon(R.drawable.dbelga)
                        .setTitle("Peringatan !")
                        .setMessage("Set Alamat Pengiriman ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                alertDialog.dismiss();
                                Intent intent = new Intent();
                                GudangActivity.alamatPengirimanPengguna = new AlamatPengiriman(position, addresses, popupGetAlamatBinding.txtAlamatPengiriman.getText().toString());
                                sessionManager.setLatLong(String.valueOf(position.latitude), String.valueOf(position.longitude));
                                sessionManager.setAlamatPengiriman(addresses.get(0).getAddressLine(0));
                                intent.putExtra("hasSetAlamat", true);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                dialog.dismiss();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}