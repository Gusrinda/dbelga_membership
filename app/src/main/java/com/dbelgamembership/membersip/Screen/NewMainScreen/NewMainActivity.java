package com.dbelgamembership.membersip.Screen.NewMainScreen;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelListTagihan.ModelListTagihan;
import com.dbelgamembership.membersip.Model.ModelListTagihan.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.Katalog.WishlishActivity;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.AkunFragment;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.MainFragment;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.WishlistFragment;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.TransaksiFragment;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.Setting.EditAkun;
import com.dbelgamembership.membersip.Screen.Setting.SupportActivity;
import com.dbelgamembership.membersip.Screen.User.AkunSaya;
import com.dbelgamembership.membersip.databinding.ActivityNewMainBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.ImageBadgeView;


public class NewMainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    public static ActivityNewMainBinding binding;

    int posisiAwalSpinner;

    public static BottomNavigationView bottomNavigationView;
    AppBarLayout appBarLayout;
    SessionManager sessionManager;

    String idKategori, namaKategori;

    public static ImageBadgeView iconKeranjang;

    private int menuAwal;
    private int menuAkhir;

    @Override
    protected void onResume() {
        super.onResume();
        setupUser();
    }

    private void setupUser() {

        Log.e(TAG, "setupUser: " + sessionManager.getImage());

        if (sessionManager.isLoggedIn()) {
            if (!sessionManager.getImage().equals("") && !sessionManager.getImage().equals("null")) {

                Log.d(TAG, "setupUser: IMAGE USER :: " + sessionManager.getImage());

                Glide.with(NewMainActivity.this).asBitmap().load(sessionManager.getImage()).error(R.drawable.user_kosong).centerCrop().into(binding.ppUser);
            } else {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
                binding.ppUser.setImageDrawable(image);
            }

            if (!sessionManager.getKeyGudangPilihan().isEmpty()) {

                Log.e(TAG, "setupUser: MASUK SINIIII");
                Log.e(TAG, "setupUser: KEY GUDANG :: " + sessionManager.getKeyGudangPilihan());

                for (int i = 0; i < daftarGudang.length; i++) {

                    Log.e(TAG, "setupUser: MODEL GUDANG ID :: " + modelGudangs.get(i).getIdGudang());

                    if (sessionManager.getKeyGudangPilihan().equals(modelGudangs.get(i).getIdGudang())) {

                        Log.e(TAG, "setupUser: SELECTION " + i);

                        binding.spinnerGudang.setSelection(i);
                    }
                }
            }

        } else {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
            binding.ppUser.setImageDrawable(image);
        }
    }

    String[] daftarGudang = new String[modelGudangs.size()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = findViewById(R.id.bottomNavView);
        sessionManager = new SessionManager(this);

        iconKeranjang = findViewById(R.id.icon_Keranjang);

        for (int i = 0; i < modelGudangs.size(); i++) {
            daftarGudang[i] = modelGudangs.get(i).getNamaGudang();
        }

        Log.e(TAG, "onCreate: " + daftarGudang.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, daftarGudang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGudang.setAdapter(adapter);

//        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new MainFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.mainFragment);
        appBarLayout = findViewById(R.id.appBar);

        binding.linearIsiAppBar.setVisibility(View.VISIBLE);
        binding.linearIsiPengaturanAkun.setVisibility(View.GONE);


        if (getIntent().hasExtra("hasExtra")) {
            String idSelection = getIntent().getStringExtra("idGudang");

            for (int i = 0; i < daftarGudang.length; i++) {
                if (idSelection.equals(modelGudangs.get(i).getIdGudang())) {
                    binding.spinnerGudang.setSelection(i);
                }
            }
        }

        if (sessionManager.isLoggedIn()) {
            if (!sessionManager.getImage().equals("")) {
                Glide.with(NewMainActivity.this).asBitmap().load(sessionManager.getImage()).centerCrop().into(binding.ppUser);
            } else {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
                binding.ppUser.setImageDrawable(image);
            }


            Log.e(TAG, "onCreate: SESSION USER ? " + sessionManager.getPID());
            Log.e(TAG, "onCreate: SESSION USER ? " + sessionManager.getName());

            getNotifikasiTagihanUser();


        }

        binding.btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewMainActivity.this, SupportActivity.class);
                startActivity(intent);
            }
        });

        posisiAwalSpinner = binding.spinnerGudang.getSelectedItemPosition();

        binding.spinnerGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int posisiAkhirSpinner = binding.spinnerGudang.getSelectedItemPosition();

                Log.e(TAG, "1. POSISI AWAL SPINNER :  " + posisiAwalSpinner);

                Log.e(TAG, "1. POSISI AKHIR SPINNER :  " + posisiAkhirSpinner);

                if (sessionManager.isLoggedIn()) {

                    int dataCartSize = (MainFragment.dataChartUser == null ? 0 : MainFragment.dataChartUser.getMsgServer().getDetailItemCart().size());

                    if (posisiAkhirSpinner != posisiAwalSpinner && dataCartSize > 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(NewMainActivity.this);
                        alert.setIcon(R.drawable.dbelga);
                        alert.setTitle("Attention!");
                        alert.setMessage("Anda memilih toko yang berbeda dari cart anda, hapus keranjang toko sebelumnya ?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                clearingCart();
                                posisiAwalSpinner = posisiAkhirSpinner;
                                dialogInterface.dismiss();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                binding.spinnerGudang.setSelection(posisiAwalSpinner);
                            }
                        });
                        alert.show();
                    } else {
                        Thread timerThread = new Thread() {
                            public void run() {
                                try {
                                    sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {
                                    posisiAwalSpinner = posisiAkhirSpinner;
                                    settingUpSpinner();
                                }
                            }
                        };
                        timerThread.start();
                    }
                } else {
                    Thread timerThread = new Thread() {
                        public void run() {
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                posisiAwalSpinner = posisiAkhirSpinner;
                                settingUpSpinner();
                            }
                        }
                    };
                    timerThread.start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        menuAwal = binding.bottomNavView.getSelectedItemId();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.e(TAG, "onNavigationItemSelected: " + item.getItemId());

                if (menuAwal != item.getItemId()) {

                    Fragment fragment = new MainFragment();

                    switch (item.getItemId()) {
                        case R.id.mainFragment:
                            fragment = new MainFragment();
                            binding.appBar.setVisibility(View.VISIBLE);
                            binding.linearIsiAppBar.setVisibility(View.VISIBLE);
                            binding.linearIsiPengaturanAkun.setVisibility(View.GONE);
                            break;
                        case R.id.wishlistFragment:
                            fragment = new WishlistFragment();
                            binding.appBar.setVisibility(View.VISIBLE);
                            binding.linearIsiAppBar.setVisibility(View.VISIBLE);
                            binding.linearIsiPengaturanAkun.setVisibility(View.GONE);
                            break;
                        case R.id.transaksiFragment:
                            fragment = new TransaksiFragment();
                            binding.appBar.setVisibility(View.GONE);
                            binding.linearIsiAppBar.setVisibility(View.GONE);
                            binding.linearIsiPengaturanAkun.setVisibility(View.GONE);
                            break;
                        case R.id.akunFragment:
//                        binding.linearIsiAppBar.setVisibility(View.GONE);
                            binding.appBar.setVisibility(View.VISIBLE);
                            binding.linearIsiAppBar.setVisibility(View.GONE);
                            binding.linearIsiPengaturanAkun.setVisibility(View.VISIBLE);
                            fragment = new AkunFragment();
                            break;
                    }

                    Log.e(TAG, "onNavigationItemSelected: " + fragment);

                    assert fragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();

                    menuAwal = item.getItemId();

                    binding.iconKeranjang.setVisibility(View.GONE);

                }

                return true;
            }
        });

        binding.btnPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.isLoggedIn()) {
                    Intent intent = new Intent(NewMainActivity.this, EditAkun.class);
                    startActivity(intent);
                } else {
                    PeringatanBelumLogin("Pengaturan");
                }
            }
        });

        binding.iconKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.isLoggedIn()) {
                    Intent intent = new Intent(NewMainActivity.this, CartActivity.class);
                    startActivity(intent);
                } else {
                    PeringatanBelumLogin("Keranjang");
                }
            }
        });

        binding.iconWhislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.isLoggedIn()) {
                    Intent intent = new Intent(NewMainActivity.this, WishlishActivity.class);
                    startActivity(intent);
                } else {
                    PeringatanBelumLogin("Wishlist");
                }
            }
        });
    }

    private void getNotifikasiTagihanUser() {
        Calendar c = Calendar.getInstance();

        Log.e(TAG, "getListTagihan JATUH TEMPO KAMU : " + sessionManager.getKeyJatuhTempo());

        SimpleDateFormat tanggalPeriode = new SimpleDateFormat("yyyy-MM-dd");

//        c.add(Calendar.DAY_OF_MONTH, 30);

        String tanggalSekarang = tanggalPeriode.format(c.getTime());


        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelListTagihan> callListTagihan = apiInterface.doGetListTagihanUser(sessionManager.getPID(), tanggalSekarang);

        callListTagihan.enqueue(new Callback<ModelListTagihan>() {
            @Override
            public void onResponse(Call<ModelListTagihan> call, Response<ModelListTagihan> response) {
                if (response.code() == 200) {

                    ModelListTagihan modelDaftarTagihan = response.body();

                    if (modelDaftarTagihan.getSuccess()) {
                        double totalTagihan = modelDaftarTagihan.getMsgServer().getLimitPenggunaan() + modelDaftarTagihan.getMsgServer().getTagihanDenda();

                        Log.e(TAG, "onResponse: DAFTAR TOTAL TAGIHAN : " + totalTagihan);

                        String batasHari = "";

                        if (totalTagihan > 0) {

                            if (modelDaftarTagihan.getMsgServer().getTagihanDenda() > 0) {

                                for (int i = 0; i < modelDaftarTagihan.getMsgServer().getDaftarTagihanDenda().size(); i++) {
                                    batasHari = modelDaftarTagihan.getMsgServer().getDaftarTagihanDenda().get(i).getBatasHari();
                                }

                            } else {
                                for (int i = 0; i < modelDaftarTagihan.getMsgServer().getDaftarTagihanPeriode().size(); i++) {
                                    batasHari = modelDaftarTagihan.getMsgServer().getDaftarTagihanPeriode().get(i).getBatasHari();
                                }
                            }


                            Log.e(TAG, "onResponse: BATAS HARINYA ! " + batasHari);

                            try {

                                Date tglBatasHari = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(batasHari);

                                Calendar tglJatuhTempoCal = Calendar.getInstance();

                                tglJatuhTempoCal.setTime(tglBatasHari);

                                tglJatuhTempoCal.add(Calendar.DAY_OF_MONTH, 1);

                                Date a = tanggalPeriode.parse(tanggalSekarang);
                                Date b = tglJatuhTempoCal.getTime();

                                Log.e(TAG, "onResponse: TGL PERIODE SEKARANG = " + a);
                                Log.e(TAG, "onResponse: TGL JTH TEMPO  = " + b);

                                long timeDiff = b.getTime() - a.getTime();

                                long numberOfDays = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

                                if (numberOfDays <= 7 && totalTagihan > 0) {
                                    createNotifikasiTagihan(numberOfDays, modelDaftarTagihan.getMsgServer());
                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


//                            Log.e(TAG, "getNotifikasiTagihanUser: TANGGAL SEKARANG " + tanggalSekarang);
//
//                            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sessionManager.getKeyJatuhTempo() == "" ? "15" : sessionManager.getKeyJatuhTempo()));
//
//                            String tanggalJatuhTempo = tanggalPeriode.format(c.getTime());
//
//                            Log.e(TAG, "getNotifikasiTagihanUser: TANGGAL JATUH TEMPO " + tanggalJatuhTempo);
//
//                            try {
//                                Date a = tanggalPeriode.parse(tanggalSekarang);
//                                Date b = tanggalPeriode.parse(tanggalJatuhTempo);
//
//                                long timeDiff = b.getTime() - a.getTime();
//
//                                long numberOfDays = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
//
//                                if (numberOfDays <= 7 && totalTagihan > 0) {
//                                    createNotifikasiTagihan(numberOfDays, modelDaftarTagihan.getMsgServer());
//                                }
//
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }




                        }


                    } else {
                        Toast.makeText(NewMainActivity.this, "Error :: TIDAK SUKSES DEBIT", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(NewMainActivity.this, "Error :: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelListTagihan> call, Throwable t) {
                Toast.makeText(NewMainActivity.this, "ERROR :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                finish();
            }
        });


    }

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    private void createNotifikasiTagihan(long numberOfDays, MsgServer msgServer) {

        Log.e(TAG, "createNotifikasiTagihan: MASUK NOTIFIKASI !");

        double totalTagihan = msgServer.getLimitPenggunaan() + msgServer.getTagihanDenda();
        NotificationManager mNotificationManager;
        String pesanExpired = "";

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NewMainActivity.this, "notify_001");


////        numberOfDays = 7;
////        numberOfDays = 4;
////        numberOfDays = 3;
////        numberOfDays = 1;
////        numberOfDays = 0;
//        numberOfDays = -1;
////        numberOfDays = -5;

        if (numberOfDays <= 7 && numberOfDays > 3) {
            mBuilder.setContentTitle("dBelga Plafon");
            pesanExpired = numberOfDays + " hari sebelum jatuh tempo, cek tagihanmu dan segera lunasi untuk tetap dapat menggunakan plafon dBelga.";
        } else if (numberOfDays <= 3 && numberOfDays >= 0) {
            mBuilder.setContentTitle("dBelga Jatuh Tempo");
            if (numberOfDays == 0) {
                pesanExpired = "Jatuh tempo pembayaran sisa hari ini, segera lakukan pelunasan tagihan total Rp. " + nf.format(totalTagihan) + " sebelum jatuh tempo.";
            } else {
                pesanExpired = "Jatuh tempo pembayaran sisa " + numberOfDays + " hari, segera lakukan pelunasan tagihan total Rp. " + nf.format(totalTagihan) + " sebelum jatuh tempo.";
            }
        } else if (numberOfDays < 0) {
            mBuilder.setContentTitle("dBelga Tagihan !");
            pesanExpired = "Tagihan plafon dBelga anda sudah jatuh tempo, total tagihan dan denda anda Rp. " + nf.format(totalTagihan) + " . Segera lakukan pelunasan sebelum terkena denda lebih besar !";
        } else {
            pesanExpired = numberOfDays + " hari sebelum jatuh tempo, cek tagihanmu dan segera lunasi untuk tetap dapat menggunakan plafon dBelga.";
        }


        mBuilder.setSmallIcon(R.drawable.dbelga);

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

        Log.e(TAG, "createNotifikasiTagihan: NOTIFIKASI KELUAR !");


    }

    private void settingUpSpinner() {
        Map<String, String> kategoriID = GudangActivity.daftarGudang;

        for (Map.Entry<String, String> pair : kategoriID.entrySet()) {
            if (pair.getValue() == binding.spinnerGudang.getSelectedItem()) {
                idKategori = pair.getKey();
                namaKategori = pair.getValue();
                sessionManager.setKeySetGudangPencarian(idKategori);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new MainFragment()).commit();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // call the invalidate()
                        bottomNavigationView.setSelectedItemId(R.id.mainFragment);
                    }
                });
            }
        }

        Log.e(TAG, "onItemSelected NAMA : " + namaKategori);
        Log.e(TAG, "onItemSelected ID : " + idKategori);
    }

    private void clearingCart() {
        final ProgressDialog progressDialog = ProgressDialog.show(NewMainActivity.this, "Loading", "Deleting data ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doEmptyCart(sessionManager.getPID());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                progressDialog.dismiss();
                settingUpSpinner();
                MainFragment.dataChartUser = null;
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NewMainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void PeringatanBelumLogin(String from) {
        Log.e(TAG, "PeringatanBelumLogin: FROM :: " + from);
        AlertDialog.Builder alert = new AlertDialog.Builder(NewMainActivity.this);
        alert.setIcon(R.drawable.dbelga);
        alert.setTitle("Fitur Dikunci");
        alert.setMessage("Anda harus mempunyai akun Membership terlebih dahulu untuk mengakses fitur ini !");
        alert.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(NewMainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alert.setNegativeButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(NewMainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alert.setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

}