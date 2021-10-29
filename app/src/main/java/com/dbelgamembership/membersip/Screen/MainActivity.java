package com.dbelgamembership.membersip.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.app.Adapter.AdapterListWishlist;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelWish.ModelWish;
import com.dbelgamembership.membersip.Model.ModelWish.MsgServer;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.Screen.Setting.EditAkun;
import com.dbelgamembership.membersip.Screen.Transaksi.ListTransaksi;
import com.dbelgamembership.membersip.Screen.User.AkunSaya;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.Setting.SupportActivity;
import com.dbelgamembership.membersip.Screen.Katalog.WishlishActivity;
import com.dbelgamembership.membersip.databinding.ActivityMainBinding;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.developer.kalert.KAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.dbelgamembership.membersip.Screen.SplashActivity.listGambarSlider;

public class MainActivity extends AppCompatActivity {

    public String url = Http.server, jsonResult, type, user, pass;
    public static String statusMember = "";
    TextView namaMember, nomorMember, expiredDate, statusMembership, namaUser, emailUser;
    RelativeLayout btnAkunSaya, btnBelanja, btnTransaksiSaya, btnKeluar;
    SessionManager sessionManager;

    private int barangStok, barangIndent;

    private boolean isOnCreate = true;

    CircleImageView profilUser;
    RelativeLayout layoutCardMember;
    LinearLayout bintangPremium, bintangGold, layoutExpired;
    private String TAG = "";
    SimpleDateFormat formatExp, formatExpDate, formatter;
    String urlImage;
    String birthMember;

    String idCustomer;

    String todayString, todayBirthday, birthday;

    ImageSlider imageSlider;

    AdapterListWishlist adapterListSearchBarang;
    List<String> arrayKategori = new ArrayList<String>();

    private ActivityMainBinding mainBinding;

    @Override
    protected void onResume() {
        super.onResume();
        getDateServer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(this);
        formatExp = new SimpleDateFormat("dd-MM-yyyy");
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatExpDate = new SimpleDateFormat("yyyy-MM-dd");

        findID();
        getDateServer();

        mainBinding.btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SupportActivity.class);
                startActivity(intent);
            }
        });

        btnAkunSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AkunSaya.class);
                startActivity(intent);
            }
        });

        btnBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GudangActivity.class);
                startActivity(intent);
            }
        });

        btnTransaksiSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListTransaksi.class);
                startActivity(intent);
            }
        });

        mainBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        profilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditAkun.class);
                startActivity(intent);
            }
        });

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
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void getDateServer() {
        url = Http.server;
        url = url + "get-date";

        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.e("", "onResponse: DATEDATE " + response);
                            try {
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test Tanggal : " + success);
                                if (success == false) {
                                    Toast.makeText(MainActivity.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {
//                                    JSONObject jsonObject = response.getJSONObject("msgServer");
                                    String tanggalServer = root.get("msgServer").getAsString();
                                    Log.e(TAG, "tanggal server : " + tanggalServer);
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date tanggal = formatter.parse(tanggalServer);
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    String strDate = dateFormat.format(tanggal);

                                    todayString = strDate;

                                    Log.e(TAG, "Tanggal sekarang fix sudah diformat : " + todayString);

                                    getDataUser();

                                }
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);

    }

    private void getDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(MainActivity.this);
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
                            Log.e("", "onResponse: GETDATAUSER " + response);
                            try {
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test : " + success);
                                if (!success) {
                                    Toast.makeText(MainActivity.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    Gson gson = new Gson();
                                    ModelUser modelMember = gson.fromJson(String.valueOf(response), ModelUser.class);
                                    com.dbelgamembership.membersip.Model.ModelUser.MsgServer dataMember = modelMember.getMsgServer().get(0);

                                    String status_member = dataMember.getStatusMember();
//                                    String updated_at = jsonObject.getString("updated_at");
                                    String expiredMember = dataMember.getExpiredDate();
                                    String ulangTahun = dataMember.getDateBirth();
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    SimpleDateFormat formatEXP = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat formatHariIni = new SimpleDateFormat("dd-MM-yyyy");
                                    Date birth = formatEXP.parse(ulangTahun);
                                    Date todayDate = formatHariIni.parse(todayString);
                                    SimpleDateFormat formatToday = new SimpleDateFormat("MM-dd");
                                    todayBirthday = formatToday.format(todayDate);
                                    birthday = formatToday.format(birth);

                                    Date created = formatter.parse(expiredMember);
                                    Calendar cal = Calendar.getInstance();

                                    Log.e(TAG, "Today : " + todayBirthday);
                                    Log.e(TAG, "Birthday : " + birthday);
                                    cal.setTime(created);

                                    Date expired = cal.getTime();
                                    String expDate = formatExp.format(expired);
                                    Log.e("", "status member: " + status_member);
                                    Log.e("", "expired date: " + expDate);
                                    urlImage = dataMember.getImageCustomer();

                                    if (urlImage.equals("http://52.77.225.163/upload/customer-photo/")) {
                                        urlImage = "";
                                    } else {
                                        urlImage = dataMember.getImageCustomer();
                                    }

                                    Log.e(TAG, "url Image: " + urlImage);
                                    birthMember = ulangTahun;
                                    sessionManager.setImage(urlImage);
                                    sessionManager.setMembership(status_member);
                                    sessionManager.setExpiredDate(expDate);
                                    statusMember = sessionManager.getMembership();
                                    sessionManager.setAccountUser(dataMember.getName(), dataMember.getMainEmail(), dataMember.getMainAddress(), dataMember.getMainPhone1());
                                    cekMember();
                                    getDataWishlist();
                                }
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        dialog1.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void getDataWishlist() {
        idCustomer = sessionManager.getPID();
        url = Http.server + "wishlist-search?customer_id=" + sessionManager.getPID();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.length() > 1) {
                                Gson gson = new Gson();
                                ModelWish modelListItem = gson.fromJson(response, ModelWish.class);
                                List<com.dbelgamembership.membersip.Model.ModelWish.MsgServer> modelItem = modelListItem.getMsgServer();

                                int stokBarang;
                                barangStok = 0;

                                if (modelItem.size() > 0) {
                                    for (MsgServer itemData : modelItem) {
                                        stokBarang = itemData.getQtyStok();

                                        if (stokBarang == 0) {
                                            barangIndent++;
                                        } else if (stokBarang > 0) {
                                            barangStok++;
                                        }

                                    }
                                }
//                                else {
//                                    Snack("Wishlist Kosong");
//                                }

                                if (barangStok > 0) {
                                    notifikasiStokWishlist();
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: WISHLIST " + e);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getApplicationContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(MainActivity.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getDataWishlist();
                                }
                            });
                    builder1.setNegativeButton(
                            "Tidak",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
                    final androidx.appcompat.app.AlertDialog alert11 = builder1.create();
                    alert11.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                            alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                        }
                    });
                    alert11.show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + sessionManager.getKeyToken());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        arrReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(arrReq);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void logout() {

        new KAlertDialog(MainActivity.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Logout")
                .setContentText("Anda akan keluar dari sesi aplikasi")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MainActivity.this)
                .cancelButtonColor(R.color.grey_font, MainActivity.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                        sessionManager.destroySession();
                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
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

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(mainBinding.imageSlider, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        snackbar.show();
    }

    private void cekMember() {
        Log.e("TAG", "status Member: " + statusMember);
        Drawable image;
        String nama = sessionManager.getName();
        namaUser.setText(sessionManager.getName());
        emailUser.setText(sessionManager.getEmail());
        nomorMember.setText(sessionManager.getPID());
        expiredDate.setText(sessionManager.getExpiredDate());
        cekNotificationExpired();
        String namaPendek;
        if (nama.length() > 15) {
            namaPendek = nama.substring(0, 15);
        } else {
            namaPendek = nama;
        }
        namaMember.setText(namaPendek.toUpperCase());

        if (!urlImage.equals("")) {
            Glide.with(MainActivity.this).asBitmap().load(urlImage).centerCrop().into(profilUser);
        } else {
            image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
            profilUser.setImageDrawable(image);
        }

        if (statusMember.equals("REGULER")) {
            image = getResources().getDrawable(R.drawable.card_reguler);
            bintangPremium.setVisibility(View.VISIBLE);
            bintangGold.setVisibility(View.GONE);
//            statusMembership.setText("REGULER");
            layoutCardMember.setBackground(image);
            layoutExpired.setVisibility(View.GONE);
        } else if (statusMember.equals("DEBET")) {
            image = getResources().getDrawable(R.drawable.card_debet);
            bintangPremium.setVisibility(View.GONE);
            bintangGold.setVisibility(View.VISIBLE);
//            statusMembership.setText("DEBET");
            layoutCardMember.setBackground(image);
            layoutExpired.setVisibility(View.VISIBLE);
        }

        //SetupSlider
        setupSlider();

    }

    private void setupSlider() {
        List<SlideModel> models = new ArrayList<>();

        if (todayBirthday.equals(birthday)) {
            models.add(new SlideModel("https://www.tokodapur.com/wp-content/uploads/2017/08/Banner-TD-Ultah-page.jpg", ScaleTypes.FIT)); // Banner ulang tahun
            notificationBirthDay();
        }

        if (listGambarSlider.length > 0) {
            for (int i = 0; i < listGambarSlider.length; i++) {
                models.add(new SlideModel(listGambarSlider[i], ScaleTypes.FIT)); // Banner promo 3
            }
        }


        imageSlider.setImageList(models, ScaleTypes.FIT);
    }

    private void notificationBirthDay() {
        NotificationManager mNotificationManager;
        String pesanExpired = "Selamat Ulang Tahun !!! \nDapatkan promo menarik pada hari ulang tahun anda";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(MainActivity.this, "notify_001");
        Intent ii = new Intent(MainActivity.this, AkunSaya.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_baseline_cake_24);
        mBuilder.setContentTitle("HBD 4U !");
        mBuilder.setContentText(pesanExpired);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(pesanExpired));

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        if (isOnCreate) {
            mNotificationManager.notify(0, mBuilder.build());
        }

        isOnCreate = false;
    }

    private void notifikasiStokWishlist() {
        NotificationManager mNotificationManager;
        String pesanExpired = barangStok + " Barang di Wishlist anda sudah ready stok !\nSegera buat pesanan dengan menghubungi sales terdekat !";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(MainActivity.this, "notify_001");
        Intent ii = new Intent(MainActivity.this, WishlishActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_baseline_shopping_cart_24);
        mBuilder.setContentTitle("Barangmu sudah ada !");
        mBuilder.setContentText(pesanExpired);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(pesanExpired));

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

//        mNotificationManager.notify(0, mBuilder.build());

        if (isOnCreate) {
            mNotificationManager.notify(0, mBuilder.build());
        }

        isOnCreate = false;
    }

    private void cekNotificationExpired() {

        final Calendar checkHari = Calendar.getInstance();
        Date checkHariTime = checkHari.getTime();
        String paymentExpired = formatExp.format(checkHariTime);


        Log.e(TAG, "Tanggal Sekarang : " + paymentExpired);
//        Log.e(TAG, "Tanggal Sekarang : " + todayString);
        Log.e(TAG, "Tanggal Expired : " + sessionManager.getExpiredDate());

        try {
            Date sekarangDate = formatExp.parse(paymentExpired);
//            Date sekarangDate = formatExp.parse(todayString);
            Date expiredDate = formatExp.parse(sessionManager.getExpiredDate());

            long millisecondsDateNow = sekarangDate.getTime();
            long millisecondsDeadline = expiredDate.getTime();
            long count = millisecondsDeadline - millisecondsDateNow;
            Log.e(TAG, "MiliSecond sekarang: " + millisecondsDateNow);
            Log.e(TAG, "MiliSecond expired: " + millisecondsDeadline);
            Log.e(TAG, "Selisih : " + count);

            int days = (int) (count / (1000 * 60 * 60 * 24));

            if (count <= 604800000 && count > 0) {
                String pesanExpired = "Akun anda " + days + " hari menuju expired !\nLakukan pendaftaran akun member anda kembali pada halaman 'Akun Saya'";

                NotificationManager mNotificationManager;

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this, "notify_001");
                Intent ii = new Intent(MainActivity.this, AkunSaya.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
                mBuilder.setContentTitle("Reminder Expired");
                mBuilder.setContentText(pesanExpired);
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(pesanExpired));

                mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                // === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    mBuilder.setChannelId(channelId);
                }

                mNotificationManager.notify(0, mBuilder.build());
                Log.e(TAG, "cekNotificationExpired 7 hari");
            } else if (count <= 0 && count > (-259200000)) {

                int lamaTenggang = (int) (259200000 - (Math.abs(count)));

                int sisaHari = (int) (lamaTenggang / (1000 * 60 * 60 * 24));

                String pesanExpired = "Akun anda telah expired !\nSisa waktu untuk perpanjang dan pelunasan adalah " + sisaHari + " hari !";

                NotificationManager mNotificationManager;

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this, "notify_001");
                Intent ii = new Intent(MainActivity.this, AkunSaya.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
                mBuilder.setContentTitle("Reminder Expired");
                mBuilder.setContentText(pesanExpired);
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(pesanExpired));

                mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                // === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    mBuilder.setChannelId(channelId);
                }

                mNotificationManager.notify(0, mBuilder.build());
                Log.e(TAG, "cekNotificationExpired 3 hari tenggang");
            } else if (count < (-259200000)) {
                String pesanExpired = "Akun anda telah expired dan tidak melakukan perpanjangan!\nAnda diubah status menjadi member reguler";

                NotificationManager mNotificationManager;

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this, "notify_001");
                Intent ii = new Intent(MainActivity.this, AkunSaya.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
                mBuilder.setContentTitle("Reminder Expired");
                mBuilder.setContentText(pesanExpired);
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(pesanExpired));

                mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                // === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    mBuilder.setChannelId(channelId);
                }

                mNotificationManager.notify(0, mBuilder.build());
                Log.e(TAG, "cekNotificationExpired EXPIRED");

                ubahReguler();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void ubahReguler() {
        url = Http.server;
        url = url + "update-status/" + sessionManager.getPID();
        updateDataUser();
    }

    private void updateDataUser() {
        JSONObject postData = new JSONObject();
        final Calendar expired = Calendar.getInstance();
        expired.add(Calendar.YEAR, 100);
        Date expiredDate = expired.getTime();
        String expDate = formatExpDate.format(expiredDate);

        final Calendar paydate = Calendar.getInstance();
        paydate.add(Calendar.DATE, 1);
        Date paymentDate = paydate.getTime();
        String paymentExpired = formatter.format(paymentDate);
        Log.e(TAG, "URL : " + url);

        try {
            postData.put("status_member", "REGULER");
            postData.put("expired_date", expDate);
            postData.put("pay_date", paymentExpired);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isOnline()) {

            SimpanPost(postData);
        }

    }

    private void SimpanPost(JSONObject postData) {
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
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
                                    String deadlinePay = dataPengguna.getString("pay_date");
                                    String dateExpired = dataPengguna.getString("expired_date");
                                    Log.e("", "id User: " + id);
                                    Log.e("", "nama User: " + name);
                                    Log.e("", "email User: " + email);
                                    Log.e("", "membership: " + membership);
                                    Log.e("", "statusPayment: " + statusPayment);
                                    Log.e("", "expired membership: " + dateExpired);
                                    sessionManager.setMembership(membership);
                                    if (statusPayment.equals("TRUE")) {
                                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, KonfirmasiMembership.class);
                                        intent.putExtra("TANGGAL_DEADLINE", deadlinePay);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: ERROROROOR" + e.getMessage());
                            Snack(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);
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

    private void findID() {
        namaUser = findViewById(R.id.namaUser);
        emailUser = findViewById(R.id.emailUser);
        namaMember = findViewById(R.id.txtNamaMember);
        nomorMember = findViewById(R.id.txtNomorMember);
        expiredDate = findViewById(R.id.txtExpDate);
//        statusMembership = findViewById(R.id.txtStatusMember);
        btnAkunSaya = findViewById(R.id.akunSaya);
        btnBelanja = findViewById(R.id.belanjaMember);
        btnTransaksiSaya = findViewById(R.id.transaksiSaya);
//        btnInfoDiskon = findViewById(R.id.informasiDiskon);
        layoutCardMember = findViewById(R.id.layoutCardMember);
        bintangPremium = findViewById(R.id.bintangPremium);
        bintangGold = findViewById(R.id.bintangGold);
        profilUser = findViewById(R.id.ppUser);
        imageSlider = findViewById(R.id.image_slider);
        layoutExpired = findViewById(R.id.layoutExpired);

    }


}