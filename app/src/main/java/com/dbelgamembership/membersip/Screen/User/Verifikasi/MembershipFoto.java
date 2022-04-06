package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Screen.Maps.MapsActivity;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.databinding.ActivityMembershipFotoBinding;
import com.developer.kalert.KAlertDialog;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.hardware.Camera;


public class MembershipFoto extends AppCompatActivity {

    private static final String TAG = "MEMBERSHIP FOTO";
    private ActivityMembershipFotoBinding binding;
    private Bitmap bitmap;
    private SessionManager sessionManager;

    private static boolean selfie = false;
    private ImagePopup imagePopup;
    private ProgressDialog progressDialog;

    private Uri uriFotoIdentitas, uriFotoWajah, uriFotoSelfie, uriFotoRumah;

    Location locationRumah;
    private String alamatRumah;
    private LatLng latLngRumah;

    String fotoIdentitas = "";
    String fotoWajah = "";
    String fotoSelfie = "";
    String fotoRumah = "";

    boolean flagFotoKTP = false;
    boolean flagFotoSelfie = false;
    boolean flagFotoSelfiedanKTP = false;
    boolean flagFotoRumah = false;
    boolean flagAlamatRumah = false;

    private FusedLocationProviderClient fusedClient;

    boolean isHaveCamera = true;

    @Override
    public void onBackPressed() {
        new KAlertDialog(MembershipFoto.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Keluar")
                .setContentText("Keluar dari halaman ini akan menyebabkan semua foto yang diambil hilang. Anda yakin ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MembershipFoto.this)
                .cancelButtonColor(R.color.grey_font, MembershipFoto.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMembershipFotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(this);

        try {
            permissionRequest();


            int numCameras = Camera.getNumberOfCameras();

            Log.e(TAG, "CAMERA JUMLAH :  " + numCameras);

            if (numCameras > 0) {
                isHaveCamera = true;
            } else {
                isHaveCamera = false;
            }

            Log.e(TAG, "IS HAVE CAMERA : " + isHaveCamera);


        } catch (Exception e) {
            Toast.makeText(MembershipFoto.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onCreate: " + Arrays.toString(e.getStackTrace()));
        }

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(MembershipFoto.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Keluar")
                        .setContentText("Keluar dari halaman ini akan menyebabkan semua proses pendaftaran member debet hilang. Anda yakin ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, MembershipFoto.this)
                        .cancelButtonColor(R.color.grey_font, MembershipFoto.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
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

        binding.btnUploadFotoIdentitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isHaveCamera) {
                    Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                    selfie = false;
                    intent.putExtra("kode_guide", 1);
                    startActivityForResult(intent, 101);
                } else {
                    ImagePicker.Companion.with(MembershipFoto.this)
                            .galleryOnly()
                            .crop()                    //Crop image(Optional), Check Customization for more option
                            .compress(1024)            //Final image size will be less than 1 MB(Optional)
                            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                            .start(101);
                }


            }
        });

        binding.btnUploadFotoWajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isHaveCamera) {
                    Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                    selfie = true;
                    intent.putExtra("kode_guide", 2);
//                startActivity(intent);
                    startActivityForResult(intent, 102);
                } else {
                    ImagePicker.Companion.with(MembershipFoto.this)
                            .galleryOnly()
                            .crop()                    //Crop image(Optional), Check Customization for more option
                            .compress(1024)            //Final image size will be less than 1 MB(Optional)
                            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                            .start(102);
                }


            }
        });

        binding.btnUploadFotoSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isHaveCamera) {
                    Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                    selfie = true;
                    intent.putExtra("kode_guide", 3);
//                startActivity(intent);
                    startActivityForResult(intent, 103);
                } else {
                    ImagePicker.Companion.with(MembershipFoto.this)
                            .galleryOnly()
                            .crop()                    //Crop image(Optional), Check Customization for more option
                            .compress(1024)            //Final image size will be less than 1 MB(Optional)
                            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                            .start(103);
                }


            }
        });

        binding.btnUploadFotoRumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isHaveCamera) {
                    Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                    selfie = false;
                    intent.putExtra("kode_guide", 4);
//                startActivity(intent);
                    startActivityForResult(intent, 104);
                } else {
                    ImagePicker.Companion.with(MembershipFoto.this)
                            .galleryOnly()
                            .crop()                    //Crop image(Optional), Check Customization for more option
                            .compress(1024)            //Final image size will be less than 1 MB(Optional)
                            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                            .start(104);
                }


            }
        });

        binding.btnSetAlamatRumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembershipFoto.this, SetAlamatRumah.class);
                intent.putExtra("hasLocation", true);
                intent.putExtra("location", latLngRumah);
                startActivityForResult(intent, 1);
            }
        });

        Log.e(TAG, "onCreate SELECTED MEMBERSHIP : " + MembershipPilih.selectedMembership);

        if (sessionManager.getMembership().equals("SILVER")) {
            binding.linearLayoutFotoRumah.setVisibility(View.GONE);
            flagFotoRumah = true;
            flagAlamatRumah = true;
        } else {
            flagFotoRumah = false;
            flagAlamatRumah = false;
        }

        imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(1200); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setBackgroundColor(Color.TRANSPARENT);  // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional

        binding.imgFotoIdentitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(binding.imgFotoIdentitas.getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup();
            }
        });

        binding.imgFotoWajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(binding.imgFotoWajah.getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup();
            }
        });

        binding.imgFotoSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(binding.imgFotoSelfie.getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup();
            }
        });

        binding.btnKirimIdentitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uriFotoIdentitas == null) {
                    Toast.makeText(MembershipFoto.this, "Ambil foto identitas terlebih dahulu !", Toast.LENGTH_SHORT).show();
                } else if (uriFotoWajah == null) {
                    Toast.makeText(MembershipFoto.this, "Ambil foto wajah terlebih dahulu !", Toast.LENGTH_SHORT).show();
                } else if (uriFotoSelfie == null) {
                    Toast.makeText(MembershipFoto.this, "Ambil foto selfie dengan identitas terlebih dahulu !", Toast.LENGTH_SHORT).show();
                } else {
                    new KAlertDialog(MembershipFoto.this, KAlertDialog.WARNING_TYPE)
                            .setTitleText("Verifikasi")
                            .setContentText("Anda akan mengirim foto anda untuk proses verifikasi member ?")
                            .setConfirmText("Ya")
                            .confirmButtonColor(R.color.biruBelga, MembershipFoto.this)
                            .cancelButtonColor(R.color.grey_font, MembershipFoto.this)
                            .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    sendDataVerifikasi(fotoIdentitas, fotoWajah, fotoSelfie, fotoRumah);
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
        });

        checkAllData();
    }

    @SuppressLint("NewApi")
    private void checkAllData() {

        if (flagFotoSelfie && flagFotoKTP && flagFotoRumah && flagFotoSelfiedanKTP && flagAlamatRumah) {
            binding.btnKirimIdentitas.setBackgroundTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.biruBelga)));
            binding.btnKirimIdentitas.setEnabled(true);
        } else {
            binding.btnKirimIdentitas.setBackgroundTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.greyBelha)));
            binding.btnKirimIdentitas.setEnabled(false);
        }

    }

    private void permissionRequest() {
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
                            fusedClient = LocationServices.getFusedLocationProviderClient(MembershipFoto.this);
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

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        progressDialog = ProgressDialog.show(MembershipFoto.this, "Loading", "Please Wait...");
        fusedClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            progressDialog.dismiss();
                            locationRumah = location;
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            latLngRumah = new LatLng(latitude, longitude);

                            Log.e(TAG, "onSuccess LAT : " + latitude);
                            Log.e(TAG, "onSuccess LONG : " + longitude);

                            Geocoder geocoder = new Geocoder(MembershipFoto.this, Locale.getDefault());
                            List<Address> addresses = null; // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            try {
                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                String address = addresses.get(0).getAddressLine(0);
                                alamatRumah = address;
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
                                    startActivity(new Intent(MembershipFoto.this, MembershipFoto.class));
                                }
                            };

                            AlertDialog alertDialog = new AlertDialog.Builder(MembershipFoto.this).create();
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


    public void save(String fileText, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName + ".txt", MODE_PRIVATE);
            fos.write(fileText.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendDataVerifikasi(String fotoIdentitas, String fotoWajah, String fotoSelfie, String fotoRumah) {
        progressDialog = ProgressDialog.show(MembershipFoto.this, "Loading", "Please Wait...");

        Writer output = null;
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        JSONObject postData = new JSONObject();
        try {
            postData.put("id_member", sessionManager.getPID());
            postData.put("img_identitas", fotoIdentitas);
            postData.put("img_wajah", fotoWajah);
            postData.put("img_full", fotoSelfie);
            postData.put("img_rumah", fotoRumah);
            postData.put("lat_rumah", latLngRumah.latitude);
            postData.put("lon_rumah", latLngRumah.longitude);
            postData.put("address_rumah", alamatRumah);

            File pdfFile = new File(folder, "postData.json");
            output = new BufferedWriter(new FileWriter(pdfFile));
            output.write(postData.toString());
            output.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doSendVerification(
                sessionManager.getPID(),
                fotoIdentitas,
                fotoWajah,
                fotoSelfie,
                fotoRumah,
                String.valueOf(latLngRumah.latitude),
                String.valueOf(latLngRumah.longitude),
                alamatRumah
        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JsonObject root = new JsonParser().parse(String.valueOf(response.body())).getAsJsonObject();
                    boolean check = root.get("success").getAsBoolean();
                    if (!check) {
                        PeringatanDialog("Error", jsonObject.getString("msgServer"));
                    } else {
//                        Toast.makeText(MembershipFoto.this, "Tunggu verifikasi admin dalam 1x24 jam !", Toast.LENGTH_SHORT).show();
//                        finish();
//                        Intent intent = new Intent(MembershipFoto.this, NewMainActivity.class);
//                        startActivity(intent);

//                        if (sessionManager.getMembership().equals("SILVER")) {
                        finish();
                        Intent intent = new Intent(MembershipFoto.this, SplashActivity.class);
                        startActivity(intent);
//                        } else {
//                            Intent intent = new Intent(MembershipFoto.this, KonfirmasiMembership.class);
//                            startActivity(intent);
//                            finish();
//                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.NO_WRAP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            Uri uriGallery = data.getData();

            boolean isThereOk = true;

            if (requestCode == 101) {
                //CAMERA FOTO IDENTITAS


                if (isHaveCamera) {

                    final String result = data.getStringExtra(String.valueOf(CameraGuideline.EXTRA_DATA));
                    Log.e(TAG, "onActivityResult: FOTO IDENTITAS  -> " + result);
                    Uri myUri = Uri.parse(data.getStringExtra("imageUri"));
                    Log.e(TAG, "onActivityResult: URI FOTO  -> " + myUri);

                    uriFotoIdentitas = myUri;
                } else {


                    if (resultCode == Activity.RESULT_OK) {
                        uriFotoIdentitas = uriGallery;
                    } else if (resultCode == ImagePicker.RESULT_ERROR) {
                        isThereOk = false;
                    } else {
                        isThereOk = false;
                    }

                }

                try {

                    if (isThereOk) {
                        Bitmap thisFotoBitmap = handleSamplingAndRotationBitmap(this, uriFotoIdentitas);
                        fotoIdentitas = imageToString(thisFotoBitmap);
                        save(fotoIdentitas, "fotoIdentitas");
                        setView(101, thisFotoBitmap);
                        flagFotoKTP = true;
                    } else {
                        flagFotoKTP = false;
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    flagFotoKTP = false;
                    e.printStackTrace();
                }
            } else if (requestCode == 102) {
                //CAMERA SELFIE
                if (isHaveCamera) {
                    final String result = data.getStringExtra(String.valueOf(CameraGuideline.EXTRA_DATA));
                    Log.e(TAG, "onActivityResult: FOTO SELFIE  -> " + result);
                    Uri myUri = Uri.parse(data.getStringExtra("imageUri"));
                    Log.e(TAG, "onActivityResult: URI FOTO  -> " + myUri);
                    uriFotoWajah = myUri;
                } else {

                    if (resultCode == Activity.RESULT_OK) {
                        uriFotoWajah = uriGallery;
                    } else if (resultCode == ImagePicker.RESULT_ERROR) {
                        isThereOk = false;
                    } else {
                        isThereOk = false;
                    }

                }

                try {

                    if (isThereOk) {

                        Bitmap thisFotoBitmap = handleSamplingAndRotationBitmap(this, uriFotoWajah);
                        fotoWajah = imageToString(thisFotoBitmap);
                        save(fotoWajah, "fotoWajah");
                        setView(102, thisFotoBitmap);
                        flagFotoSelfie = true;
                    } else {
                        flagFotoSelfie = false;
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    flagFotoSelfie = false;
                }

            } else if (requestCode == 103) {
                //CAMERA SELFIE WITH ID
                if (isHaveCamera) {
                    final String result = data.getStringExtra(String.valueOf(CameraGuideline.EXTRA_DATA));
                    Log.e(TAG, "onActivityResult: FOTO SELFIE ID  -> " + result);
                    Uri myUri = Uri.parse(data.getStringExtra("imageUri"));
                    Log.e(TAG, "onActivityResult: URI FOTO  -> " + myUri);
                    uriFotoSelfie = myUri;
                } else {
                    if (resultCode == Activity.RESULT_OK) {
                        uriFotoSelfie = uriGallery;
                    } else if (resultCode == ImagePicker.RESULT_ERROR) {
                        isThereOk = false;
                    } else {
                        isThereOk = false;
                    }

                }

                try {

                    if (isThereOk) {
                        Bitmap thisFotoBitmap = handleSamplingAndRotationBitmap(this, uriFotoSelfie);
                        fotoSelfie = imageToString(thisFotoBitmap);
                        save(fotoSelfie, "fotoSelfie");
                        setView(103, thisFotoBitmap);
                        flagFotoSelfiedanKTP = true;
                    } else {
                        flagFotoSelfiedanKTP = false;
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    flagFotoSelfiedanKTP = false;
                }

            } else if (requestCode == 104) {
                //CAMERA SELFIE WITH ID
                if (isHaveCamera) {
                    final String result = data.getStringExtra(String.valueOf(CameraGuideline.EXTRA_DATA));
                    Log.e(TAG, "onActivityResult: FOTO RUMAH ID  -> " + result);
                    Uri myUri = Uri.parse(data.getStringExtra("imageUri"));
                    Log.e(TAG, "onActivityResult: URI FOTO RUMAH  -> " + myUri);
                    uriFotoRumah = myUri;
                } else {
                    if (resultCode == Activity.RESULT_OK) {

                        uriFotoRumah = uriGallery;
                    } else if (resultCode == ImagePicker.RESULT_ERROR) {
                        isThereOk = false;
                    } else {
                        isThereOk = false;
                    }

                }

                try {

                    if (isThereOk) {
                        Bitmap thisFotoBitmap = handleSamplingAndRotationBitmap(this, uriFotoRumah);
                        fotoRumah = imageToString(thisFotoBitmap);
                        save(fotoRumah, "fotoSelfie");
                        setView(104, thisFotoBitmap);
                        flagFotoRumah = true;
                    } else {
                        flagFotoRumah = false;
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    flagFotoRumah = false;
                }

            } else if (requestCode == 1) {
                if (resultCode == -1) {
                    Log.e(TAG, "onActivityResult: " + data);
                    if (data != null) {
                        if (data.hasExtra("hasAlamatRumah")) {
                            String alamat = data.getStringExtra("alamat_address");
                            LatLng latlong = data.getParcelableExtra("alamat_latlng");

                            alamatRumah = alamat;
                            latLngRumah = latlong;
                            flagAlamatRumah = true;

                            Toast.makeText(MembershipFoto.this, "ALAMAT :: " + alamat + "\nLAT :: " + latLngRumah.latitude + "\nLNG :: " + latLngRumah.longitude, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.e(TAG, "onActivityResult: data " + data);
                    }
                }
            }

            checkAllData();
        }

    }

    private void setView(int i, Bitmap bitmap) {

        if (i == 101) {
            binding.imgFotoIdentitas.setImageBitmap(bitmap);
            binding.imgFotoIdentitas.setVisibility(View.VISIBLE);
            binding.imgCheckFotoIdentitas.setVisibility(View.VISIBLE);
        } else if (i == 102) {
            binding.imgFotoWajah.setImageBitmap(bitmap);
            binding.imgFotoWajah.setVisibility(View.VISIBLE);
            binding.imgCheckFotoWajah.setVisibility(View.VISIBLE);
        } else if (i == 103) {
            binding.imgFotoSelfie.setImageBitmap(bitmap);
            binding.imgFotoSelfie.setVisibility(View.VISIBLE);
            binding.imgCheckFotoSelfie.setVisibility(View.VISIBLE);
        } else if (i == 104) {
            binding.imgFotoRumah.setImageBitmap(bitmap);
            binding.imgFotoRumah.setVisibility(View.VISIBLE);
            binding.imgCheckFotoRumah.setVisibility(View.VISIBLE);
        }

        selfie = false;


    }


    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(img, selectedImage);
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;


            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        Log.e(TAG, "rotateImageIfRequired: " + orientation);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        if (selfie) {
            matrix.postRotate(degree + 180);
            matrix.postScale(-1, 1);
        } else {
            matrix.postRotate(degree);
        }
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    private void PeringatanDialog(String judul, String Pesan) {
        Timer timer = new Timer();
        final long DELAY = 2000; // milliseconds
        AlertDialog alertDialog = new AlertDialog.Builder(MembershipFoto.this).create();
        alertDialog.setTitle(judul);
        alertDialog.setMessage(Pesan);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                            }
                        });

                    }
                },
                DELAY
        );

    }


}