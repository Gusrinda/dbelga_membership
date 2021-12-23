package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.Katalog.Model.AlamatPengiriman;
import com.dbelgamembership.membersip.Screen.Maps.MapsActivity;
import com.dbelgamembership.membersip.databinding.ActivitySetAlamatRumahBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SetAlamatRumah extends FragmentActivity implements OnMapReadyCallback {

    private final String TAG = this.getClass().getSimpleName();
    private GoogleMap mMap;
    private ActivitySetAlamatRumahBinding binding;
    LatLng lokasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySetAlamatRumahBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("hasLocation")) {
            lokasi = getIntent().getParcelableExtra("location");
        } else {
            finish();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng position = markerFinal.getPosition();
                Geocoder geocoder = new Geocoder(SetAlamatRumah.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SetAlamatRumah.this);
                    builder.setIcon(R.drawable.dbelga)
                            .setTitle("Peringatan !")
                            .setMessage("Set Alamat Rumah Disini ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    Intent intent = new Intent();
                                    intent.putExtra("hasAlamatRumah", true);
                                    intent.putExtra("alamat_address", addresses.get(0).getAddressLine(0));
                                    intent.putExtra("alamat_latlng", position);
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
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    Marker markerFinal;
    MarkerOptions markedLocationPengguna = new MarkerOptions();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng lokasiSaatIni = new LatLng(lokasi.latitude, lokasi.longitude);

        Geocoder geocoder = new Geocoder(SetAlamatRumah.this, Locale.getDefault());

        try {
            Log.e(TAG, "onMapReady LAT :: " + lokasi.latitude);
            Log.e(TAG, "onMapReady LON :: " + lokasi.longitude);

            double lat = lokasi.latitude;
            double lon = lokasi.longitude;

            Log.e(TAG, "onMapReady LAT 2 :: " + lat);
            Log.e(TAG, "onMapReady LON 2 :: " + lon);

            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            Log.e(TAG, "onMapReady: " + addresses);
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

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
                        List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        Log.e(TAG, "onMapReady: " + addresses);
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
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

            binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    String location = binding.searchView.getQuery().toString();
                    List<Address> addressList = null;

                    if (location != null || !location.equals("")) {
                        Geocoder geocoder = new Geocoder(SetAlamatRumah.this);

                        try {
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (addressList.size() > 0) {
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            String alaamt = address.getAddressLine(0);
                            binding.txtAlamatPengiriman.setText(alaamt);
                            markerFinal.remove();
                            markedLocationPengguna = new MarkerOptions().position(latLng).title("Pindah kesini");
                            markerFinal = mMap.addMarker(markedLocationPengguna);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        }  else {
                            Toast.makeText(SetAlamatRumah.this, "Location not found !", Toast.LENGTH_SHORT).show();
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
}