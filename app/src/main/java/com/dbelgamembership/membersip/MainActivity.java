package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public String url = Http.server, jsonResult, type, user, pass;
    public static String statusMember = "";
    TextView namaMember, nomorMember, expiredDate, statusMembership, namaUser, emailUser;
    RelativeLayout btnAkunSaya, btnBelanja, btnTransaksiSaya, btnKeluar;
    SessionManager sessionManager;

    CircleImageView profilUser;
    RelativeLayout layoutCardMember;
    LinearLayout bintangPremium, bintangGold;
    private String TAG = "";
    SimpleDateFormat formatExp;
    String urlImage;
    String birthMember;

    String todayString, birthday;

    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        formatExp = new SimpleDateFormat("dd-MM-yyyy");

//        formatExpDate = new SimpleDateFormat("yyyy-MM-dd");
        findID();
        getDataUser();

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
                Intent intent = new Intent(MainActivity.this, KatalogActivity.class);
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

        btnKeluar.setOnClickListener(new View.OnClickListener() {
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
                            Log.e("", "onResponse: " + response);
                            try {
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test : " + success);
                                if (success == false) {
                                    Toast.makeText(MainActivity.this, response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    JSONObject jsonObject = response.getJSONObject("msgServer");
                                    String status_member = jsonObject.getString("status_member");
                                    String updated_at = jsonObject.getString("updated_at");
                                    String ulangTahun = jsonObject.getString("date_birth");
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    SimpleDateFormat formatEXP = new SimpleDateFormat("yyyy-MM-dd");


                                    Date birth = formatEXP.parse(ulangTahun);
                                    Date todayDate = Calendar.getInstance().getTime();
                                    SimpleDateFormat formatToday = new SimpleDateFormat("MM-dd");
                                    todayString = formatToday.format(todayDate);
                                    birthday = formatToday.format(birth);

                                    Date created = formatter.parse(updated_at);
                                    Calendar cal = Calendar.getInstance();

                                    Log.e(TAG, "Today : " + todayString);
                                    Log.e(TAG, "Birthday : " + birthday);
                                    cal.setTime(created);
//                                    Log.e(TAG, "Today : " + cal.getTime());
                                    cal.add(Calendar.YEAR, 1);
                                    Log.e(TAG, "Next year expired : " + cal.getTime());
                                    Date nextYear = cal.getTime();
                                    String expDate = formatExp.format(nextYear);
                                    Log.e("", "status member: " + status_member);
                                    Log.e("", "expired date: " + expDate);
                                    urlImage = jsonObject.getString("image_customer");

                                    if (urlImage.equals("http://54.254.194.122/upload/customer-photo/")) {
                                        urlImage = "";
                                    } else {
                                        urlImage = jsonObject.getString("image_customer");
                                    }

                                    Log.e(TAG, "url Image: " + urlImage);
                                    birthMember = ulangTahun;
                                    sessionManager.setImage(urlImage);
                                    sessionManager.setMembership(status_member);
                                    sessionManager.setExpiredDate(expDate);
                                    statusMember = sessionManager.getMembership();
                                    cekMember();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void logout() {
        finish();
        Snack("Log Out Berhasil");
        sessionManager.destroySession();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
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
        Snackbar snackbar = Snackbar.make(btnKeluar, string, Snackbar.LENGTH_LONG)
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
            Glide.with(this).asBitmap().load(urlImage).centerCrop().into(profilUser);
        }

        if (statusMember.equals("REGULER")) {
            image = getResources().getDrawable(R.drawable.card_reguler);
            bintangPremium.setVisibility(View.VISIBLE);
            bintangGold.setVisibility(View.GONE);
//            statusMembership.setText("REGULER");
            layoutCardMember.setBackground(image);
        } else if (statusMember.equals("DEBET")) {
            image = getResources().getDrawable(R.drawable.card_debet);
            bintangPremium.setVisibility(View.GONE);
            bintangGold.setVisibility(View.VISIBLE);
//            statusMembership.setText("DEBET");
            layoutCardMember.setBackground(image);
        }

        //SetupSlider
        setupSlider();

    }

    private void setupSlider() {
        List<SlideModel> models = new ArrayList<>();

        if (todayString.equals(birthday)) {
            models.add(new SlideModel("https://www.tokodapur.com/wp-content/uploads/2017/08/Banner-TD-Ultah-page.jpg", ScaleTypes.FIT)); // Banner ulang tahun
            notificationBirthDay();
        }
        models.add(new SlideModel("https://image.shutterstock.com/image-vector/brush-sale-banner-promotion-ribbon-260nw-1182942766.jpg", ScaleTypes.FIT)); // Banner promo 1
        models.add(new SlideModel("https://www.jagoanhosting.com/wp-content/uploads/2019/08/Banner-Promo-Extra-19.jpg", ScaleTypes.FIT)); // Banner promo 2
        models.add(new SlideModel("https://www.jagoanhosting.com/wp-content/uploads/2019/07/Banner-promo-epic-77.png", ScaleTypes.FIT)); // Banner promo 3
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

        mNotificationManager.notify(0, mBuilder.build());
    }

    private void cekNotificationExpired() {
        final Calendar today = Calendar.getInstance();
        Date tanggalSekarang = today.getTime();
        String tanggalNow = formatExp.format(tanggalSekarang);

        Log.e(TAG, "Tanggal Sekarang : " + tanggalNow);
        Log.e(TAG, "Tanggal Expired : " + sessionManager.getExpiredDate());


        try {
            Date sekarangDate = formatExp.parse(tanggalNow);
            Date expiredDate = formatExp.parse(sessionManager.getExpiredDate());

            long millisecondsDateNow = sekarangDate.getTime();
            long millisecondsDeadline = expiredDate.getTime();
            long count = millisecondsDeadline - millisecondsDateNow;
            Log.e(TAG, "MiliSecond sekarang: " + millisecondsDateNow);
            Log.e(TAG, "MiliSecond expired: " + millisecondsDeadline);
            Log.e(TAG, "Selisih : " + count);

            int days = (int) (count / (1000 * 60 * 60 * 24));

            if (count <= 604800000) {
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


//                Intent intent = new Intent(MainActivity.this, AkunSaya.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                builder.setContentIntent(pendingIntent);
//
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(0, builder.build());

                Log.e(TAG, "cekNotificationExpired");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


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
        btnKeluar = findViewById(R.id.logoutAkun);
        layoutCardMember = findViewById(R.id.layoutCardMember);
        bintangPremium = findViewById(R.id.bintangPremium);
        bintangGold = findViewById(R.id.bintangGold);
        profilUser = findViewById(R.id.ppUser);
        imageSlider = findViewById(R.id.image_slider);

    }


}