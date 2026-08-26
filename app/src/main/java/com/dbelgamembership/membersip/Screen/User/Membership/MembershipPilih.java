package com.dbelgamembership.membersip.Screen.User.Membership;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.dbelgamembership.membersip.Model.ModelToko.ModelGudang;
import com.dbelgamembership.membersip.Model.ModelToko.ModelToko;
import com.dbelgamembership.membersip.Model.ModelToko.MsgServer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintActivity;
import com.dbelgamembership.membersip.Screen.User.BoardingMemberDebet;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.MembershipFoto;
import com.dbelgamembership.membersip.databinding.ActivityMembershipPilihBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class MembershipPilih extends AppCompatActivity {

    SessionManager sessionManager;
    Spinner sp_Membership;
    TextView infoLanjut;
    LinearLayout layoutDetail;
    ScrollView memberRegular, memberGold;
    Button pilihMember;
    String choosenMembership;
    public String url = Http.server, jsonResult, type, user;
    String namaMember, alamatMember, nomorMember, tanggalMember, deadlinePayment, passwordMember, emailMember, expiredMembership;
    private String TAG = "";

    ImageView backArrow;
    SimpleDateFormat formatExp, formatter, formatExpDate;

    private ActivityMembershipPilihBinding binding;
    public static String selectedMembership = "";
    String pilihan = "";

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMembershipPilihBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        formatExp = new SimpleDateFormat("MM/yyyy");
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatExpDate = new SimpleDateFormat("yyyy-MM-dd");
        namaMember = getIntent().getStringExtra("NAMA_MEMBER");
        alamatMember = getIntent().getStringExtra("ALAMAT_MEMBER");
        nomorMember = getIntent().getStringExtra("NOMOR_MEMBER");
        emailMember = getIntent().getStringExtra("EMAIL_MEMBER");
        tanggalMember = getIntent().getStringExtra("TANGGAL_MEMBER");
        passwordMember = getIntent().getStringExtra("PASSWORD_MEMBER");

        findID();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(MembershipPilih.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Keluar ?")
                        .setContentText("Keluar dari halaman ini mengganggap anda logout dari sesi aplikasi, anda yakin ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, MembershipPilih.this)
                        .cancelButtonColor(R.color.grey_font, MembershipPilih.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                                sessionManager.destroySession();
                                Intent intent = new Intent(MembershipPilih.this, SplashActivity.class);
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

        layoutDetail.setVisibility(View.GONE);

        final Calendar tanggal1 = Calendar.getInstance();
        tanggal1.add(Calendar.YEAR, 1);
        Date tanggalTahun = tanggal1.getTime();
        String deadlen = formatExp.format(tanggalTahun);
        Log.e(TAG, "Tanggal Tahun : " + deadlen);

        sp_Membership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String yangDipilih = sp_Membership.getSelectedItem().toString();
                Drawable image;
                binding.namaMembership.setText("MEMBER " + yangDipilih.toUpperCase());
                if (yangDipilih.equals("Silver")) {
                    selectedMembership = "SILVER";
                    layoutDetail.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_member_silver);
                    binding.layoutCardMember.setBackground(image);
                    binding.infoMemberReguler.setVisibility(View.VISIBLE);
                    binding.infoMemberGold.setVisibility(View.GONE);
                    binding.layoutExpired.setVisibility(View.GONE);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("SLV" + sessionManager.getPID());
                    choosenMembership = "SILVER";


                    binding.layoutDetailMembership.getBackground().setTint(view.getResources().getColor(R.color.grey_font));

                    binding.inputNominalPlafon.setVisibility(View.GONE);
                    binding.edInputNominalPlafon.setText("0");

                } else if (yangDipilih.equals("Gold")) {
                    selectedMembership = "GOLD";
                    layoutDetail.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_member_gold);
                    binding.layoutCardMember.setBackground(image);
                    binding.infoMemberReguler.setVisibility(View.GONE);
                    binding.infoMemberGold.setVisibility(View.VISIBLE);
                    binding.layoutExpired.setVisibility(View.VISIBLE);
                    binding.txtExpDate.setText(deadlen);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("GLD" + sessionManager.getPID());
                    choosenMembership = "GOLD";


                    binding.layoutDetailMembership.getBackground().setTint(view.getResources().getColor(R.color.material_yellow_800));

                    binding.inputNominalPlafon.setVisibility(View.VISIBLE);
                    binding.inputNominalPlafon.setErrorEnabled(true);
                    binding.inputNominalPlafon.setError("Batas plafon : Rp. 500.000 - Rp. 2.000.000");
                    binding.edInputNominalPlafon.setText("0");

                } else {
                    selectedMembership = "PLATINUM";
                    layoutDetail.setVisibility(View.VISIBLE);
                    image = getResources().getDrawable(R.drawable.card_member_platinum);
                    binding.layoutCardMember.setBackground(image);
                    binding.infoMemberReguler.setVisibility(View.GONE);
                    binding.infoMemberGold.setVisibility(View.VISIBLE);
                    binding.layoutExpired.setVisibility(View.VISIBLE);
                    binding.txtExpDate.setText(deadlen);
                    binding.txtNamaMember.setText(sessionManager.getName().toUpperCase());
                    binding.txtNomorMember.setText("PLT" + sessionManager.getPID());
                    choosenMembership = "PLATINUM";


                    binding.layoutDetailMembership.getBackground().setTint(view.getResources().getColor(R.color.black));

                    binding.inputNominalPlafon.setVisibility(View.VISIBLE);
                    binding.inputNominalPlafon.setErrorEnabled(true);
                    binding.inputNominalPlafon.setError("Batas plafon : Rp. 2.000.000 - Rp. 5.000.000");
                    binding.edInputNominalPlafon.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pilihMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int nominalPlafon = Integer.parseInt(binding.edInputNominalPlafon.getText().toString());

                int min = 0;
                int max = 0;

                if (choosenMembership.equals("GOLD")) {
                    min = 500000;
                    max = 2000000;
                } else if (choosenMembership.equals("PLATINUM")) {
                    min = 2000000;
                    max = 5000000;
                }

                if (nominalPlafon < min) {
                    Toast.makeText(MembershipPilih.this, "Tidak bisa kurang dari minimal !", Toast.LENGTH_SHORT).show();
                    binding.edInputNominalPlafon.setText(String.valueOf(min));
                } else if (nominalPlafon > max) {
                    Toast.makeText(MembershipPilih.this, "Tidak bisa lebih dari maksimal !", Toast.LENGTH_SHORT).show();
                    binding.edInputNominalPlafon.setText(String.valueOf(max));
                } else {
                    registerUser();
                }


            }
        });

        if (getIntent().getStringExtra("pilihan_membership") != null) {
            pilihan = getIntent().getStringExtra("pilihan_membership");
        }

        if (pilihan.equals("SILVER")) {
            binding.spinnerMembership.setSelection(0);
        } else if (pilihan.equals("GOLD")) {
            binding.spinnerMembership.setSelection(1);
        } else if (pilihan.equals("PLATINUM")) {
            binding.spinnerMembership.setSelection(2);
        } else {
            binding.spinnerMembership.setSelection(0);
        }

        getDaftarToko();

    }


    private List<ModelGudang> modelGudangs = new ArrayList<>();
    private List<ModelGudang> daftarGudangToko = new ArrayList<>();
    private HashMap<String, String> daftarGudang = new HashMap<String, String>();

    private ModelGudang selectedGudang;

    String[] daftarGudangNama ;


    private void getDaftarToko() {
        final ProgressDialog progressDialog = ProgressDialog.show(MembershipPilih.this, "Tunggu Sebentar", "Loading Gudang ...");

        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelToko> call = apiInterface.doGetToko();
        call.enqueue(new Callback<ModelToko>() {
            @Override
            public void onResponse(Call<ModelToko> call, retrofit2.Response<ModelToko> response) {

                binding.spinnerTokoGudang.setAdapter(null);

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

                daftarGudangNama = new String[modelGudangs.size()];

                for (int i = 0; i < modelGudangs.size(); i++) {
                    daftarGudang.put(String.valueOf(modelGudangs.get(i).getIdGudang()), modelGudangs.get(i).getNamaGudang());

                    daftarGudangNama[i] = modelGudangs.get(i).getNamaGudang() + " ( " + modelGudangs.get(i).getAlamatGudang() + " )";

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, daftarGudangNama);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerTokoGudang.setAdapter(adapter);

                progressDialog.dismiss();

                binding.spinnerTokoGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.e(TAG, "onItemSelected: " + i );

                        Log.e(TAG, "NAMA Gudang selected :: " + modelGudangs.get(i).getNamaGudang() );
                        Log.e(TAG, "ID Gudang selected :: " + modelGudangs.get(i).getIdGudang() );

                        selectedGudang = modelGudangs.get(i);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

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

    @Override
    public void onBackPressed() {
        new KAlertDialog(MembershipPilih.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Keluar ?")
                .setContentText("Keluar dari halaman ini mengganggap anda logout dari sesi aplikasi, anda yakin ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MembershipPilih.this)
                .cancelButtonColor(R.color.grey_font, MembershipPilih.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
//                        finish();

                        finish();
                        sessionManager.destroySession();
                        Intent intent = new Intent(MembershipPilih.this, SplashActivity.class);
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

    private void registerUser() {
        if (isOnline() == true) {
            accessWebService();
        } else {
            Toast.makeText(MembershipPilih.this, "Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void accessWebService() {
        pilihMember.setEnabled(false);
        new KAlertDialog(MembershipPilih.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Anda akan memilih membership \n'" + choosenMembership + "' ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MembershipPilih.this)
                .cancelButtonColor(R.color.grey_font, MembershipPilih.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        pilihMember.setEnabled(true);
                        sDialog.dismissWithAnimation();
                        if (isOnline()) {
                            url = Http.server;
                            url = url + "update-status/" + sessionManager.getPID();
                            type = "post";
                            JSONObject postData = new JSONObject();
                            try {
                                final Calendar baru = Calendar.getInstance();
                                baru.add(Calendar.DATE, 1);
                                Date deadlineBayar = baru.getTime();
                                String deadlen = formatter.format(deadlineBayar);

                                final Calendar expired = Calendar.getInstance();
                                if (choosenMembership.equals("SILVER")) {
                                    expired.add(Calendar.YEAR, 100);
                                } else {
                                    expired.add(Calendar.YEAR, 1);
                                }

                                Date expiredDate = expired.getTime();
                                String expDate = formatExpDate.format(expiredDate);

                                deadlinePayment = deadlen;
                                expiredMembership = expDate;

                                String jatuhTempo = "";

                                if (binding.spinnerTanggalJatuhTempo.getSelectedItemPosition() == 0) {
                                    jatuhTempo = "1";
                                } else if (binding.spinnerTanggalJatuhTempo.getSelectedItemPosition() == 1) {
                                    jatuhTempo = "15";
                                } else if (binding.spinnerTanggalJatuhTempo.getSelectedItemPosition() == 2) {
                                    jatuhTempo = "30";
                                }

                                postData.put("main_gudang", selectedGudang.getIdGudang());
                                postData.put("status_member", choosenMembership);
                                postData.put("nominal_plafon", binding.edInputNominalPlafon.getText().toString());
                                postData.put("jatuh_tempo", jatuhTempo);
                                postData.put("expired_date", expiredMembership);
                                postData.put("pay_date", deadlinePayment);

                            } catch (Exception e) {
                                e.getMessage();
                            }
                            if (isOnline()) {
                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClickSubmit: " + postData);
                                SimpanPost(postData);
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
                        pilihMember.setEnabled(true);
                    }
                })
                .show();
    }

    private void SimpanPost(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(MembershipPilih.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        Log.e(TAG, "postData: " + postData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog1.dismiss();
                            pilihMember.setClickable(true);
                            if (response != null) {
                                sessionManager.setMembership(choosenMembership);
                                sessionManager.setKeyDeadlinePayment(deadlinePayment);
                                Intent intent = new Intent(MembershipPilih.this, MembershipFoto.class);
                                startActivity(intent);
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
                    pilihMember.setClickable(true);
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void findID() {
        layoutDetail = findViewById(R.id.layoutDetailMembership);
        sp_Membership = findViewById(R.id.spinnerMembership);
        infoLanjut = findViewById(R.id.infoLanjut);
        memberRegular = findViewById(R.id.infoMemberReguler);
        memberGold = findViewById(R.id.infoMemberGold);
        pilihMember = findViewById(R.id.btnPilihMembership);
        backArrow = findViewById(R.id.backArrow);
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
        Snackbar snackbar = Snackbar.make(pilihMember, string, Snackbar.LENGTH_LONG).setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }
}