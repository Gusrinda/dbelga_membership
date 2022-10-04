package com.dbelgamembership.membersip.Screen.NewMainScreen;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.Katalog.WishlishActivity;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.AkunFragment;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.MainFragment;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.WishlistFragment;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.TransaksiFragment;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.Setting.EditAkun;
import com.dbelgamembership.membersip.Screen.Setting.SupportActivity;
import com.dbelgamembership.membersip.databinding.ActivityNewMainBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
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
            if (!sessionManager.getImage().equals("")) {

                Log.d(TAG, "setupUser: IMAGE USER :: " + sessionManager.getImage());

                Glide.with(NewMainActivity.this).asBitmap().load(sessionManager.getImage()).centerCrop().into(binding.ppUser);
            } else {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
                binding.ppUser.setImageDrawable(image);
            }

            if (!sessionManager.getKeyGudangPilihan().isEmpty()) {

                Log.e(TAG, "setupUser: MASUK SINIIII" );
                Log.e(TAG, "setupUser: KEY GUDANG :: " + sessionManager.getKeyGudangPilihan() );

                for (int i = 0; i < daftarGudang.length; i++) {

                    Log.e(TAG, "setupUser: MODEL GUDANG ID :: " + modelGudangs.get(i).getIdGudang() );

                    if (sessionManager.getKeyGudangPilihan().equals(modelGudangs.get(i).getIdGudang())) {

                        Log.e(TAG, "setupUser: SELECTION " + i );

                        binding.spinnerGudang.setSelection(i);
                    }
                }
            }

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