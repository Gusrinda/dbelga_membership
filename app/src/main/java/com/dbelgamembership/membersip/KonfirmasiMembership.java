package com.dbelgamembership.membersip;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelUser.MsgServer;
import com.developer.kalert.KAlertDialog;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;

public class KonfirmasiMembership extends AppCompatActivity {

    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass;

    TextView judul, nomorWA;
    LinearLayout infoTransfer, infoBukti, infoHubungi, uploadFoto, infoReferral;
    TextInputEditText textKodeReferral;
    ImageView fotoProduk;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    String tanggalDeadline, tanggalSekarang;
    Button btnKonfirmasi, btnBatal, btnUploadFoto, btnHubungi, btnKodeRef, btnShowUpload;
    private static final String FILE_NAME = "example.txt";
    String x, choosenMembership, paydate;

    SimpleDateFormat formatExp, formatter;
    private String TAG = "";
    String image;

    CountdownView countdownView;

    private Bitmap bitmap;
    Boolean checkImage;

    int loop = 0;

    Handler handler = new Handler();

    Runnable myRunnable = new Runnable() {
        public void run() {
            // do something
            //call function
            loop++;
            cekUserLoop();
            Log.e(TAG, "run: " + loop);
            handler.postDelayed(this, 10000);
        }
    };

    private void cekUserLoop() {
        String url = Http.server + "search-customer/" + sessionManager.getPID();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelUser> callUser = apiInterface.doLoopCustomer(url);
        callUser.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, retrofit2.Response<ModelUser> response) {
                ModelUser object = response.body();

                Log.e(TAG, "onResponse: " + object.getMsgServer().get(0).getName() );
                Log.e(TAG, "onResponse: " + object.getMsgServer().get(0).getStatusPayment() );


                boolean status_pay = Boolean.parseBoolean(object.getMsgServer().get(0).getStatusPayment());
                if (status_pay) {
                    Toast.makeText(KonfirmasiMembership.this, "Selamat bergabung menjadi member debet dbelga !", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(KonfirmasiMembership.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: DESTROY" );
        handler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onDestroy: PAUSE" );
        handler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: RESUME" );
        handler.postDelayed(myRunnable, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_membership);

        sessionManager = new SessionManager(this);
        choosenMembership = sessionManager.getMembership();
        checkImage = false;
        image = null;

        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatExp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar baru = Calendar.getInstance();
        Date tanggalNow = baru.getTime();
        String tanggal = formatExp.format(tanggalNow);

        tanggalDeadline = getIntent().getStringExtra("TANGGAL_DEADLINE");
        tanggalSekarang = tanggal;
        Log.e(TAG, "tanggal deadline : " + tanggalDeadline);
        Log.e(TAG, "tanggal sekarang : " + tanggalSekarang);

        findID();
        getDataUser();
        getCountDown();

        btnUploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(KonfirmasiMembership.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

//                OpenGallery();
            }
        });

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "checkImage belum kode : " + checkImage);

                if (!textKodeReferral.getText().toString().isEmpty() && textKodeReferral.getText().toString() != null) {
                    checkImage = true;
                }

                Log.e(TAG, "checkImage setelah kode : " + checkImage);

                if (!checkImage) {
                    Log.e(TAG, "onClick: " + sessionManager.getPID());
                    Toast.makeText(KonfirmasiMembership.this, "Pastikan anda memilih foto bukti transaksi terlebih dahulu !", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "onClick: " + sessionManager.getPID());
                    accessWebService();
                }
            }
        });

        btnShowUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUploadFoto.setVisibility(View.VISIBLE);
                infoTransfer.setVisibility(View.VISIBLE);
                infoReferral.setVisibility(View.GONE);
            }
        });

        btnKodeRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUploadFoto.setVisibility(View.GONE);
                infoTransfer.setVisibility(View.GONE);
                infoReferral.setVisibility(View.VISIBLE);
            }
        });

        btnHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //So you can get the edittext value
                String mobileNumber = nomorWA.getText().toString();
                String message = "Halo saya adalah Member Belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName();
                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mobileNumber + "&text=" + message));
                    startActivity(intent);
                } else {
                    Toast.makeText(KonfirmasiMembership.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = Http.server;
                url = url + "update-status/" + sessionManager.getPID();
                updateDataUser();
            }
        });

    }

    private void getDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(KonfirmasiMembership.this);
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
                            Gson gson = new Gson();
                            ModelUser modelMember = gson.fromJson(String.valueOf(response), ModelUser.class);
                            MsgServer dataMember = modelMember.getMsgServer().get(0);

                            image = dataMember.getImagePay().equals("null") ? "" : dataMember.getImagePay();
                            Log.e(TAG, "checkPembayaran: " + image);

                            if (!image.equals("")) {
                                infoTransfer.setVisibility(View.GONE);
                                infoBukti.setVisibility(View.GONE);
                                uploadFoto.setVisibility(View.GONE);
                                btnKonfirmasi.setVisibility(View.GONE);
                                btnHubungi.setVisibility(View.VISIBLE);
                                infoHubungi.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Toast.makeText(KonfirmasiMembership.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        dialog1.dismiss();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void updateDataUser() {
//        pilihMember.setEnabled(false);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(KonfirmasiMembership.this);
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Anda yakin untuk membatalkan proses membership ?\n(Status membership anda akan berubah menjadi Reguler)");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
//                        pilihMember.setEnabled(true);
                        dialog.dismiss();
                        if (isOnline()) {
                            type = "post";
                            JSONObject postData = new JSONObject();
                            Log.e(TAG, "choosenMember : " + choosenMembership);
                            choosenMembership = "REGULER";

                            try {
                                postData.put("status_member", choosenMembership);
                                postData.put("pay_date", "");
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            if (isOnline()) {
                                Log.e(TAG, "URL : " + url);
                                SimpanPost(postData);
                            }
                        } else {
                            Snack("Cek Koneksi Internet Anda");
                        }
                    }
                });

        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
//                        pilihMember.setEnabled(true);
                    }
                });

        final AlertDialog alert11 = builder1.create();
        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        alert11.show();
    }

    private void SimpanPost(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(KonfirmasiMembership.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog1.dismiss();
//                            pilihMember.setClickable(true);
                            if (response != null) {
                                Log.e(TAG, "URL " + url);
                                Log.e(TAG, "onResponseSimpan: " + response);
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test : " + success);
                                if (success == false) {
                                    Snack(response.getJSONArray("msgServer").toString());
                                } else {
                                    JSONObject dataPengguna = response.getJSONObject("msgServer");
                                    String id = dataPengguna.getString("id");
                                    String name = dataPengguna.getString("name");
                                    String email = dataPengguna.getString("main_email");
                                    String membership = dataPengguna.getString("status_member");
                                    String statusPayment = dataPengguna.getString("status_payment");
                                    Log.e("", "id User: " + id);
                                    Log.e("", "nama User: " + name);
                                    Log.e("", "email User: " + email);
                                    Log.e("", "membership: " + membership);
                                    Log.e("", "statusPayment: " + statusPayment);
                                    sessionManager.setMembership(membership);
                                    if (statusPayment.equals("TRUE")) {
                                        Intent intent = new Intent(KonfirmasiMembership.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(KonfirmasiMembership.this, KonfirmasiMembership.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Snack(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);
                dialog1.dismiss();
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getApplicationContext(), KonfirmasiMembership.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    pilihMember.setClickable(true);
                    startActivity(intent);
                } else if (error instanceof ServerError) {
                    Snack("Terjadi Kesalahan.");
                } else if (error instanceof NetworkError) {
                    Snack("Tidak Ada Koneksi Internet");
                } else if (error instanceof ParseError) {
                    Snack(error.getMessage());
                } else {
                    Snack(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
//                params.put("type", "create");
                params.put("Authorization", "Bearer " + sessionManager.getKeyToken());
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
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

    private void getCountDown() {

        try {
            Date sekarangDate = formatExp.parse(tanggalSekarang);
            Date deadlineDate = formatExp.parse(tanggalDeadline);
            long millisecondsDateNow = sekarangDate.getTime();
            long millisecondsDeadline = deadlineDate.getTime();
            long count = millisecondsDeadline - millisecondsDateNow;
            Log.e(TAG, "getCountDown 1: " + millisecondsDateNow);
            Log.e(TAG, "getCountDown 2: " + millisecondsDeadline);
            Log.e(TAG, "getCountDown 3: " + count);

            if (count >= 0) {
                countdownView.start(count);
                countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        url = Http.server;
                        url = url + "update-status/" + sessionManager.getPID();
                        type = "post";
                        JSONObject postData = new JSONObject();
                        Log.e(TAG, "choosenMember : " + choosenMembership);
                        choosenMembership = "REGULER";

                        try {
                            postData.put("status_member", choosenMembership);
                            postData.put("pay_date", "");
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        if (isOnline()) {
                            Log.e(TAG, "URL : " + url);
                            SimpanPost(postData);
                        }
                    }
                });
            } else {
                Timer timer = new Timer();
                final long DELAY = 2000; // milliseconds
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(KonfirmasiMembership.this).create();
                alertDialog.setTitle("PERHATIAN");
                alertDialog.setMessage("Akun anda melebihi batas waktu untuk proses verifikasi membership DEBET, akun anda akan dialihkan menjadi akun REGULER.\nanda dapat mengubah kembali menjadi member debet dalam pengaturan akun aplikasi");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                url = Http.server;
                                url = url + "update-status/" + sessionManager.getPID();
                                type = "post";
                                JSONObject postData = new JSONObject();
                                Log.e(TAG, "choosenMember : " + choosenMembership);
                                choosenMembership = "REGULER";

                                try {
                                    postData.put("status_member", choosenMembership);
                                    postData.put("pay_date", "");
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                if (isOnline()) {
                                    Log.e(TAG, "URL : " + url);
                                    SimpanPost(postData);
                                }
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void findID() {
        fotoProduk = findViewById(R.id.fotoBukti);
        infoBukti = findViewById(R.id.layoutGambarBukti);
        infoTransfer = findViewById(R.id.layout_infoBayar);
        countdownView = findViewById(R.id.countdown);
        btnUploadFoto = findViewById(R.id.btn_UploadFoto);
        btnKonfirmasi = findViewById(R.id.btn_Konfirmasi);
        btnBatal = findViewById(R.id.btn_Batal);
        infoHubungi = findViewById(R.id.layout_hubungiKonfirmasi);
        btnHubungi = findViewById(R.id.btn_Hubungi);
        judul = findViewById(R.id.judul);
        nomorWA = findViewById(R.id.text_NomorWA);
        uploadFoto = findViewById(R.id.layout_UploadFoto);
        infoReferral = findViewById(R.id.layout_kodeReferral);
        btnKodeRef = findViewById(R.id.btn_KodeRef);
        textKodeReferral = findViewById(R.id.txt_kodeRefMember);
        btnShowUpload = findViewById(R.id.btn_showUpload);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.e("TAG", "Path:" + ImagePicker.Companion.getFilePath(data));
            Uri uri = data.getData();
            ImageUri = uri;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            fotoProduk.setImageURI(ImageUri);
            infoTransfer.setVisibility(View.GONE);
            infoBukti.setVisibility(View.VISIBLE);
            checkImage = true;
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }

    private void accessWebService() {

        new KAlertDialog(KonfirmasiMembership.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Kirim bukti konfirmasi pembayaran ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, KonfirmasiMembership.this)
                .cancelButtonColor(R.color.grey_font, KonfirmasiMembership.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismiss();
                        if (isOnline()) {
                            url = Http.server;
                            url = url + "upload-payment/" + sessionManager.getPID();
                            type = "post";
                            JSONObject postData = new JSONObject();


                            String gambarPayment;
                            String kodeReferal;

                            if (bitmap == null) {
                                gambarPayment = "";
                            } else {
                                gambarPayment = imageToString(bitmap);
                            }

                            if (textKodeReferral.getText().toString() == null || textKodeReferral.getText().toString().isEmpty()) {
                                kodeReferal = "";
                            } else {
                                kodeReferal = textKodeReferral.getText().toString();
                            }

                            try {
                                postData.put("image_pay", gambarPayment);
                                postData.put("code_refferal", kodeReferal);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            if (isOnline()) {
                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClickSubmit: " + postData);
                                konfirmasiPembayaran(postData);
                            }
                        } else {
                            Snack("Cek Koneksi Internet Anda");
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

    public void save() {
        String text = x;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(x.getBytes());
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

    private void konfirmasiPembayaran(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(KonfirmasiMembership.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        Log.e(TAG, "check PostData : " + postData);
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog1.dismiss();
                            if (response != null) {
                                Log.e(TAG, "URL " + url);
                                Log.e(TAG, "onResponseSimpan: " + response);
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test : " + success);
                                if (success == false) {
                                    Snack(response.getJSONArray("msgServer").toString());
                                } else {
                                    JSONObject dataPengguna = response.getJSONObject("msgServer");
                                    String id = dataPengguna.getString("id");
                                    String name = dataPengguna.getString("name");
                                    String email = dataPengguna.getString("main_email");
                                    String membership = dataPengguna.getString("status_member");
                                    String image_pay = dataPengguna.getString("image_pay");
                                    String statusPAY = dataPengguna.getString("status_payment");
                                    Log.e("", "id User: " + id);
                                    Log.e("", "nama User: " + name);
                                    Log.e("", "email User: " + email);
                                    Log.e("", "membership: " + membership);
                                    Log.e(TAG, "status payment: " + statusPAY);
                                    if (statusPAY.equals("TRUE")) {
                                        Intent intent = new Intent(KonfirmasiMembership.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        if (!image_pay.equals(null)) {
                                            judul.setText("Menunggu Konfirmasi Pembayaran");
                                            btnKonfirmasi.setVisibility(View.GONE);
                                            uploadFoto.setVisibility(View.GONE);
                                            btnHubungi.setVisibility(View.VISIBLE);
                                            infoBukti.setVisibility(View.GONE);
                                            infoReferral.setVisibility(View.GONE);
                                            infoHubungi.setVisibility(View.VISIBLE);
                                        }
                                    }

                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Snack(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);
                dialog1.dismiss();
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getApplicationContext(), MembershipPilih.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (error instanceof ServerError) {
                    Snack("Terjadi Kesalahan.");
                } else if (error instanceof NetworkError) {
                    Snack("Tidak Ada Koneksi Internet");
                } else if (error instanceof ParseError) {
                    Snack(error.getMessage());
                } else {
                    Snack(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Authorization", "Bearer " + sessionManager.getKeyToken());
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.NO_WRAP);
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(btnUploadFoto, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        snackbar.show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}