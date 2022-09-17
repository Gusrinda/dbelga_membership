package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.ApiBanks;
import com.dbelgamembership.membersip.Helper.Constant;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.Api_Banks.BniDetailPayment.BniDetailPayment;
import com.dbelgamembership.membersip.Model.Api_Banks.BriDetailPayment.BriDetailPayment;
import com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.DetailTransaksi;
import com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.ModelDaftarTagihanDebet;
import com.dbelgamembership.membersip.Model.ModelSearchPembayaranMembership.ModelSearchPembayaranMembership;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelUser.MsgServer;
import com.dbelgamembership.membersip.Model.ResponseBayarTagihan.ResponseBayarTagihan;
import com.dbelgamembership.membersip.Model.ResponseLogMembership.ResponseLogMembership;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.Adapter.SpinnerBankAdapter;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Katalog.Model.PostBNI;
import com.dbelgamembership.membersip.Screen.Katalog.Model.PostBRI;
import com.dbelgamembership.membersip.Screen.Limit.BayarTagihan;
import com.dbelgamembership.membersip.Screen.Limit.DaftarTagihan;
import com.dbelgamembership.membersip.Screen.Limit.RiwayatTagihan;
import com.dbelgamembership.membersip.Screen.PembayaranTransfer.TransferMembership;
import com.dbelgamembership.membersip.Screen.PembayaranTransfer.TransferTagihan;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintActivity;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipPilih;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.model.PostCreateMembership;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.model.ResponseCreatePembayaranMembership.ResponseCreatePembayaranMembership;
import com.dbelgamembership.membersip.Screen.Voucher.VoucherActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterDaftarSemuaTagihan;
import com.dbelgamembership.membersip.databinding.ActivityPembayaranMembershipBinding;
import com.dbelgamembership.membersip.databinding.PopupCheckoutBinding;
import com.dbelgamembership.membersip.databinding.PopupPembayaranMembershipBinding;
import com.dbelgamembership.membersip.databinding.PopupRedeemCodeVoucherBinding;
import com.dbelgamembership.membersip.databinding.PopupRedeemReferralBinding;
import com.developer.kalert.KAlertDialog;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.snap.Gopay;
import com.midtrans.sdk.corekit.models.snap.Shopeepay;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranMembership extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityPembayaranMembershipBinding binding;
    private SessionManager sessionManager;
    private Boolean isPay = false;

    SimpleDateFormat formatExp;
    private String selectedBank = "";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPembayaranMembershipBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        formatExp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        getLogUpdateMembership();

//        getUserData();
        setupCountDown();
        setupButton();

    }


    boolean isFromNotSilverMember = false;
    com.dbelgamembership.membersip.Model.ResponseLogMembership.MsgServer dataLog;

    private void getLogUpdateMembership() {
        String url = Http.server + "log-membership/" + sessionManager.getPID();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ResponseLogMembership> callLog = apiInterface.searchLogCustomer(url);

        callLog.enqueue(new Callback<ResponseLogMembership>() {
            @Override
            public void onResponse(Call<ResponseLogMembership> call, Response<ResponseLogMembership> response) {
                try {
                    ResponseLogMembership object = response.body();
                    assert object != null;

                    dataLog = object.getMsgServer();

                    if (dataLog.getIsDataLama() != null) {
                        if (!dataLog.getMembershipLama().equals("SILVER")) {
                            isFromNotSilverMember = true;
                        }
                    }

                    getUserData();

                } catch (Exception e) {
                    finish();
                    Toast.makeText(PembayaranMembership.this, "ERROR ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse Error message : " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseLogMembership> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(PembayaranMembership.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void setupButton() {

        binding.btnBatalPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(PembayaranMembership.this);
                builder1.setTitle("Konfirmasi");
                if (isFromNotSilverMember) {
                    builder1.setMessage("Anda yakin untuk membatalkan proses membership ?\n(Status membership anda akan berubah menjadi status sebelumnya : " + dataLog.getMembershipLama() + " )");
                } else {
                    builder1.setMessage("Anda yakin untuk membatalkan proses membership ?\n(Status membership anda akan berubah menjadi SILVER)");
                }

                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                cancelingPaymentMember();
                            }
                        });

                builder1.setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
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
        });


        binding.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPay) {
                    String mobileNumber = Constant.NOMOR_WA_DEFAULT;
                    String message = "Halo saya adalah Member Belga dengan ID : " + sessionManager.getPID() + " bernama " + sessionManager.getName();
                    boolean installed = appInstalledOrNot("com.whatsapp");
                    if (installed) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mobileNumber + "&text=" + message));
                        startActivity(intent);
                    } else {
                        Toast.makeText(PembayaranMembership.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.btnLogoutPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(PembayaranMembership.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Keluar ?")
                        .setContentText("Keluar dari halaman ini mengganggap anda logout dari sesi aplikasi, anda yakin ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, PembayaranMembership.this)
                        .cancelButtonColor(R.color.grey_font, PembayaranMembership.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                                sessionManager.destroySession();
                                Intent intent = new Intent(PembayaranMembership.this, SplashActivity.class);
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

        binding.btnKodeReferaal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupReferal();
            }
        });

    }

    private PopupRedeemReferralBinding popupRedeemReferralBinding;
    private androidx.appcompat.app.AlertDialog.Builder dialogBuilder;
    private androidx.appcompat.app.AlertDialog alertDialog;
    private boolean isReferralValid = false;

    private void popupReferal() {
        popupRedeemReferralBinding = PopupRedeemReferralBinding.inflate(getLayoutInflater());
        View view = popupRedeemReferralBinding.getRoot();

        dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(PembayaranMembership.this);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();


        popupRedeemReferralBinding.btnCheckKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(popupRedeemReferralBinding.edInputKodeRedeem.getText().toString())) {
                    checkingValidasiKode();
                } else {
                    Toast.makeText(PembayaranMembership.this, "Pastikan kode diisi !", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void checkingValidasiKode() {
        isReferralValid = !isReferralValid;

        if (isReferralValid) {
            popupRedeemReferralBinding.layoutUseKode.setVisibility(View.VISIBLE);
        } else {
            popupRedeemReferralBinding.layoutUseKode.setVisibility(View.GONE);
        }

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

    private PopupPembayaranMembershipBinding popupPembayaranMembershipBinding;

//    private androidx.appcompat.app.AlertDialog.Builder dialogBuilder;
//    private androidx.appcompat.app.AlertDialog alertDialog;

    private void popUpPembayaran() {

        Log.e(TAG, "popUpPembayaran: SHOW POP PEMBAYARAN");

        popupPembayaranMembershipBinding = PopupPembayaranMembershipBinding.inflate(getLayoutInflater());
        final View view = popupPembayaranMembershipBinding.getRoot();


        dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        selectedBank = "";

        String[] namaBanks = {"BRI VA", "BCA VA", "BNI VA", "MANDIRI VA"};
        int iconBanks[] = {R.drawable.bri_icon, R.drawable.bca_icon, R.drawable.bni_icon, R.drawable.mandiri_icon};

        SpinnerBankAdapter customAdapter = new SpinnerBankAdapter(getApplicationContext(), iconBanks, namaBanks);
        popupPembayaranMembershipBinding.spinnerBankVa.setAdapter(customAdapter);

        popupPembayaranMembershipBinding.spinnerBankVa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBank = namaBanks[popupPembayaranMembershipBinding.spinnerBankVa.getSelectedItemPosition()];
                Log.e(TAG, "onItemSelected BANK SELECTED :: " + selectedBank);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        popupPembayaranMembershipBinding.produkClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        popupPembayaranMembershipBinding.layoutBtnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(PembayaranMembership.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Pembayaran Member")
                        .setContentText("Anda yakin memakai bank ini untuk pembayaran anda ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, PembayaranMembership.this)
                        .cancelButtonColor(R.color.grey_font, PembayaranMembership.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                alertDialog.dismiss();

                                createPembayaranMembership();


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


    }

    private void createPembayaranMembership() {

        PostCreateMembership postCreateMembership = new PostCreateMembership();

        double idCustomer = dataMember.getId();

        postCreateMembership.setId_customer(String.valueOf((int) idCustomer));
        postCreateMembership.setStatus_membership(dataMember.getStatusMember());
        postCreateMembership.setAmount("50000");
        postCreateMembership.setTipe_payment("TRANSFER");
        postCreateMembership.setBank_payment(selectedBank.substring(0, selectedBank.length() - 3));

        final ProgressDialog progressDialog = ProgressDialog.show(PembayaranMembership.this, "Loading", "Pembuatan VA ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ResponseCreatePembayaranMembership> call = apiInterface.doCreatePaymentMembership(postCreateMembership);

        call.enqueue(new Callback<ResponseCreatePembayaranMembership>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ResponseCreatePembayaranMembership> call, Response<ResponseCreatePembayaranMembership> response) {
                try {
                    progressDialog.dismiss();
                    alertDialog.dismiss();

                    if (selectedBank.equals("BRI VA")) {

                        //BRI
                        pembuatanBRIVA(response.body().getMsgServer().get(0));
                    } else if (selectedBank.equals("BNI VA")) {

                        //BRI
                        setupPembuatanVABNI(response.body().getMsgServer().get(0));
                    } else if (selectedBank.equals("BCA VA")) {

                    } else if (selectedBank.equals("MANDIRI VA")) {

                    }

                    Toast.makeText(PembayaranMembership.this, "ERROR !", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    progressDialog.dismiss();
                    alertDialog.dismiss();
                    finish();
                    Toast.makeText(PembayaranMembership.this, "ERROR !", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse Error message : " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseCreatePembayaranMembership> call, Throwable t) {
                progressDialog.dismiss();
                alertDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }

        });

    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pembuatanBRIVA(com.dbelgamembership.membersip.Screen.User.Verifikasi.model.ResponseCreatePembayaranMembership.MsgServer response) {


        kodePembayaran = "0000000000";

        if (object.getMsgServer().get(0).getStatusMember().equals("GOLD")) {
            kodePembayaran = "11";
        } else if (object.getMsgServer().get(0).getStatusMember().equals("PLATINUM")) {
            kodePembayaran = "12";
        } else {
            kodePembayaran = "10";
        }

        String idUser = nf.format(object.getMsgServer().get(0).getId());

        kodePembayaran = kodePembayaran + idUser + response.getKodePembayaran().substring(response.getKodePembayaran().length() - 4);

        String customerCode = kodePembayaran;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, 1);

        String dateExpired = formatter.format(cal.getTime());

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        String formattedDate = now.toString();

        Log.e(TAG, "onCreate: FORMATTED DATE :: " + formattedDate);

        try {

            StringBuilder stringPayload = new StringBuilder();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("amount", "50000");
            jsonObject.put("brivaNo", "77777");
            jsonObject.put("custCode", customerCode);
            jsonObject.put("expiredDate", dateExpired);
            jsonObject.put("institutionCode", "J104408");
            jsonObject.put("keterangan", "Pembayaran Biaya Membership :: " + kodePembayaran);
            jsonObject.put("nama", sessionManager.getName());

            Log.e(TAG, "setupPembuatanVATransfer :: " + jsonObject.toString());

            stringPayload.append("path=/v1/briva")
                    .append("&verb=POST")
                    .append("&token=Bearer ")
                    .append(ApiBanks.BRI_TOKEN == null ? sessionManager.getKeyTokenBriApi() : ApiBanks.BRI_TOKEN.getAccessToken()).append("&timestamp=").append(formattedDate).append("&body=")
                    .append(jsonObject.toString());

            PostBRI postBRI = new PostBRI(
                    "J104408",
                    "77777",
                    customerCode,
                    sessionManager.getName(),
                    "50000",
                    "Pembayaran Biaya Membership :: " + kodePembayaran,
                    dateExpired
            );

            Log.e(TAG, "setupPembuatanVATransfer STRING PAYLOAD :: " + stringPayload);

            String sign = hash_hmac(stringPayload.toString(), ApiBanks.BRI_CONSUMER_SECRET);
            String bearerToken = "Bearer " + (ApiBanks.BRI_TOKEN == null ? sessionManager.getKeyTokenBriApi() : ApiBanks.BRI_TOKEN.getAccessToken());

            final ProgressDialog progressDialog = ProgressDialog.show(PembayaranMembership.this, "Loading", "Pembuatan VA ...");
            APIInterface apiInterface = APIClient.getClient(ApiBanks.urlBRI).create(APIInterface.class);
            Call<JsonElement> call = apiInterface.bri_createEndPointVA(
                    bearerToken,
                    formattedDate,
                    sign,
                    "application/json",
                    postBRI
            );

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                    try {
                        progressDialog.dismiss();
                        if (response != null) {
                            Toast.makeText(PembayaranMembership.this, "Pembuatan VA berhasil, Lakukan Pelunasan Transfer !", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PembayaranMembership.this, TransferMembership.class);
                            intent.putExtra("hasExtra", true);
                            intent.putExtra("banks", "BRI");
                            intent.putExtra("kode_pembayaran", kodePembayaran);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                        Toast.makeText(PembayaranMembership.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, "onResponse: " + t.getMessage());
                    Toast.makeText(PembayaranMembership.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupPembuatanVABNI(com.dbelgamembership.membersip.Screen.User.Verifikasi.model.ResponseCreatePembayaranMembership.MsgServer response) {

        com.dbelgamembership.membersip.Screen.User.Verifikasi.model.ResponseCreatePembayaranMembership.MsgServer dataPembayaran = response;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        Date expiredLocalTime = calendar.getTime();

        String offset = "+07:00";
        LocalDateTime ldt = LocalDateTime.ofInstant(expiredLocalTime.toInstant(), ZoneId.systemDefault());
        ZoneOffset zoneOffset = ZoneOffset.of(offset);
        OffsetDateTime odt = OffsetDateTime.of(ldt, zoneOffset);

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String formattedDate = odt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

//      double grandTotal = Double.parseDouble(responseBayarTagihan.getMsgServer().get(0).getTotalTagihan());

        try {

            String lastFoutStamp = timeStamp.substring(timeStamp.length() - 4);
            int idCustomer = Integer.parseInt(sessionManager.getPID());

            String with4digits = String.format("%04d", idCustomer);

            String templateVANumber = "98816055" + with4digits.substring(0, 4) + lastFoutStamp;

            PostBNI postBNI = new PostBNI(
                    "MEMBERSHIP",
                    response.getKodePembayaran(),
                    "createbilling",
                    "16055",
                    response.getKodePembayaran() + "-" + timeStamp,
                    "50000",
                    "c",
                    sessionManager.getName(),
                    sessionManager.getEmail(),
                    sessionManager.getKeyTelefonMember(),
                    templateVANumber,
                    formattedDate,
                    "TRANSAKSI PELUNASAN BIAYA MEMBERSHIP : " + response.getKodePembayaran()
            );

            final ProgressDialog progressDialog = ProgressDialog.show(PembayaranMembership.this, "Loading", "Pembuatan VA ...");
            APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
            Call<JsonElement> call = apiInterface.bni_createEndPointVA(
                    postBNI
            );

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                    try {
                        progressDialog.dismiss();
                        if (response != null) {
                            Toast.makeText(PembayaranMembership.this, "Pembuatan VA berhasil, Lakukan Pelunasan Transfer !", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PembayaranMembership.this, TransferMembership.class);
                            intent.putExtra("hasExtra", true);
                            intent.putExtra("banks", "BNI");
                            intent.putExtra("kode_pembayaran", dataPembayaran.getKodePembayaran());
                            intent.putExtra("kode_trx", dataPembayaran.getKodePembayaran() + "-" + timeStamp);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                        Toast.makeText(PembayaranMembership.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, "onResponse: " + t.getMessage());
                    Toast.makeText(PembayaranMembership.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void uploadPembayaran() {
        final ProgressDialog progressDialog = ProgressDialog.show(PembayaranMembership.this, "Loading", "Uploading Payment ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doUploadPayment(sessionManager.getPID(), "", "");

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    if (response != null) {
                        Log.e(TAG, "onResponseSimpan: " + response);
                        String responseX = String.valueOf(response.body());
                        JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                        boolean success = root.get("success").getAsBoolean();
                        Log.e("", "Test : " + success);
                        if (success == false) {
                            Toast.makeText(PembayaranMembership.this, "Error Upload", Toast.LENGTH_SHORT).show();
                        } else {
                            getUserData();
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse ERROR : " + e.getMessage() + " :: " + Arrays.toString(e.getStackTrace()));
                    Toast.makeText(PembayaranMembership.this, "Error Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PembayaranMembership.this, "Error Update", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void setupView() {

        if (isPay) {
            binding.animationView.setAnimationFromUrl("https://assets7.lottiefiles.com/packages/lf20_dyf5lscb.json");
            binding.animationView.playAnimation();
            binding.tvNominalBayar.setVisibility(View.GONE);
            binding.tvDeskripsi.setText("Akun sedang dikonfirmasi, silahkan menunggu. Anda bisa menghubungi admin dengan menekan icon telfon diatas");
            binding.btnLakukanPembayaran.setVisibility(View.GONE);
        } else {
            binding.btnLakukanPembayaran.setVisibility(View.VISIBLE);
            binding.tvNominalBayar.setVisibility(View.VISIBLE);
            binding.animationView.setAnimationFromUrl("https://assets5.lottiefiles.com/packages/lf20_yzoqyyqf.json");
            binding.animationView.playAnimation();
            binding.tvDeskripsi.setText("Anda diharuskan membayar sesuai dengan nominal diatas untuk menjadi member dBelga, lakukan pembayaran dengan menekan tombol bayar dibawah");
        }

    }

    String jenisBanks = "";

    @SuppressLint("NewApi")
    private void setupViewPembayaran(String jenisBanks, String kodePembayaran) {

        if (jenisBanks.equals("BRI")) {
            searchPaymentBRI(kodePembayaran);

        } else if (jenisBanks.equals("BNI")) {

            binding.btnLakukanPembayaran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PembayaranMembership.this, TransferMembership.class);
                    intent.putExtra("hasExtra", true);
                    intent.putExtra("banks", "BNI");
                    intent.putExtra("kode_tagihan", modelSearchPembayaran.getMsgServer().getKodePembayaran());
                    intent.putExtra("kode_pembayaran", modelSearchPembayaran.getMsgServer().getKodePembayaran());
                    intent.putExtra("kode_trx", kodePembayaran);
                    startActivity(intent);
                }
            });

        } else {
            Log.e(TAG, "setupViewPembayaran: PEMBAYARAN SELAIN BRI !!!");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void searchPaymentBRI(String kodePembayaran) {

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        String formattedDate = now.toString();

        Log.e(TAG, "onCreate: FORMATTED DATE :: " + formattedDate);

        StringBuilder stringPayload = new StringBuilder();

//        /v1/briva/J104408/77777/9118
        String path = "/v1/briva/" + ApiBanks.BRI_INSTITUSI_CODE + "/" + ApiBanks.BRI_BRIVA_NO + "/" + kodePembayaran;

        stringPayload.append("path=").append(path)
                .append("&verb=GET")
                .append("&token=Bearer ")
                .append(ApiBanks.BRI_TOKEN == null ? sessionManager.getKeyTokenBriApi() : ApiBanks.BRI_TOKEN.getAccessToken()).append("&timestamp=").append(formattedDate).append("&body=");

        String sign = null;

        try {
            sign = hash_hmac(stringPayload.toString(), ApiBanks.BRI_CONSUMER_SECRET);

            String bearerToken = "Bearer " + (ApiBanks.BRI_TOKEN == null ? sessionManager.getKeyTokenBriApi() : ApiBanks.BRI_TOKEN.getAccessToken());

            APIInterface apiInterface = APIClient.getClient(ApiBanks.urlBRI).create(APIInterface.class);
            Call<BriDetailPayment> call = apiInterface.getPaymentBRI(
                    bearerToken,
                    formattedDate,
                    sign,
                    kodePembayaran
            );

            call.enqueue(new Callback<BriDetailPayment>() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onResponse(Call<BriDetailPayment> call, Response<BriDetailPayment> response) {
                    try {
                        if (response != null) {

                            if (response.code() == 400) {
                                binding.btnLakukanPembayaran.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        popUpPembayaran();
                                    }
                                });

                            } else {
                                BriDetailPayment detailPayment = response.body();

                                Gson gson = new Gson();
                                String json = gson.toJson(detailPayment);

                                if (detailPayment.getData().getStatusBayar().equals("Y") && dataMember.getImagePay() == null) {
                                    uploadPembayaran();
                                }

                                binding.btnLakukanPembayaran.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(PembayaranMembership.this, TransferMembership.class);
                                        intent.putExtra("hasExtra", true);
                                        intent.putExtra("banks", "BRI");
                                        intent.putExtra("kode_pembayaran", kodePembayaran);
                                        intent.putExtra("kode_trx", kodePembayaran);
                                        startActivity(intent);
                                    }
                                });

                            }

                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BriDetailPayment> call, Throwable t) {
//                    progressDialog.dismiss();
                    Log.e(TAG, "onResponse: " + t.getMessage());
//                    Toast.makeText(PembayaranMembership.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String hash_hmac(String str, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        String hash = Base64.encodeToString(sha256_HMAC.doFinal(str.getBytes()), Base64.NO_WRAP);

        return hash;
    }

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    String kodePembayaran = "0000000";
    MsgServer dataMember;
    ModelSearchPembayaranMembership modelSearchPembayaran;

    ModelUser object;

    private void getUserData() {
        String url = Http.server + "search-customer/" + sessionManager.getPID();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelUser> callUser = apiInterface.doLoopCustomer(url);
        callUser.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, retrofit2.Response<ModelUser> response) {
                object = response.body();

                assert object != null;
                dataMember = object.getMsgServer().get(0);

                boolean status_pay = Boolean.parseBoolean(object.getMsgServer().get(0).getStatusPayment());

                if (isFromNotSilverMember) {

                    if (status_pay) {
                        Intent intent = new Intent(PembayaranMembership.this, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        isPay = true;
                        setupView();
                    }
                } else {
                    if (status_pay) {
                        Intent intent = new Intent(PembayaranMembership.this, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                        Call<JsonElement> callPembayaran = apiInterface.doSearchPembayaranMembership(sessionManager.getPID());

                        callPembayaran.enqueue(new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                                if (response.code() == 200) {


                                    try {
                                        JSONObject obj = new JSONObject(String.valueOf(response.body()));
                                        boolean success = obj.getBoolean("success");
                                        String msgServer = obj.get("msgServer").toString();

                                        if (success) {

                                            Gson gson = new Gson();
                                            modelSearchPembayaran = gson.fromJson(response.body(), ModelSearchPembayaranMembership.class);

                                            assert modelSearchPembayaran != null;

                                            if (modelSearchPembayaran.getMsgServer().getTipePayment().equals("TRANSFER")) {

                                                if (modelSearchPembayaran.getMsgServer().getBankPayment().equals("BRI")) {
                                                    Log.e(TAG, "onResponse: MASUK PEMBAYARAN :: BRI");

                                                    kodePembayaran = "0000000";

                                                    if (object.getMsgServer().get(0).getStatusMember().equals("GOLD")) {
                                                        kodePembayaran = "11";
                                                    } else if (object.getMsgServer().get(0).getStatusMember().equals("PLATINUM")) {
                                                        kodePembayaran = "12";
                                                    } else {
                                                        kodePembayaran = "10";
                                                    }

                                                    String idUser = nf.format(object.getMsgServer().get(0).getId());

                                                    kodePembayaran = kodePembayaran + idUser + modelSearchPembayaran.getMsgServer().getKodePembayaran().substring(modelSearchPembayaran.getMsgServer().getKodePembayaran().length() - 4);
                                                    jenisBanks = "BRI";
                                                    dataMember = object.getMsgServer().get(0);

                                                    Log.e(TAG, "onResponse IMAGEPAY : " + dataMember.getImagePay());
                                                    Log.e(TAG, "onResponse ISPAY : " + isPay);

                                                    if (dataMember.getImagePay() != null) {
                                                        if (!dataMember.getImagePay().equals("null")) {
                                                            isPay = true;
                                                        } else {
                                                            isPay = false;
                                                        }
                                                    } else {
                                                        isPay = false;
                                                    }

                                                } else if (modelSearchPembayaran.getMsgServer().getBankPayment().equals("BNI")) {
                                                    Log.e(TAG, "onResponse: MASUK PEMBAYARAN :: BNI");

                                                    kodePembayaran = modelSearchPembayaran.getMsgServer().getDetailPaymentBni().getTrxId();
                                                    jenisBanks = "BNI";

                                                    if (modelSearchPembayaran.getMsgServer().getDetailPaymentBni().getStatus().equals("paid")) {
                                                        isPay = true;
                                                    } else {
                                                        isPay = false;
                                                    }

                                                    Log.e(TAG, "onResponse ISPAY : " + isPay);

                                                }

                                                setupViewPembayaran(jenisBanks, kodePembayaran);

                                            }

                                        } else {
//                                        Toast.makeText(PembayaranMembership.this, msgServer, Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "onResponse: " + msgServer);

                                            Log.e(TAG, "onResponse: MASUK SINIIIIININII");

                                            isPay = false;

                                            binding.btnLakukanPembayaran.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    popUpPembayaran();
                                                }
                                            });


                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {

                                    Log.e(TAG, "onResponse: MASUK SINIIIIININII");

                                    isPay = false;

                                    binding.btnLakukanPembayaran.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popUpPembayaran();
                                        }
                                    });

                                }

                                setupView();

                            }

                            @Override
                            public void onFailure(Call<JsonElement> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                Toast.makeText(PembayaranMembership.this, "Belum melakukan pembayaran !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }


            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {

            }

        });
    }

    private void setupCountDown() {

        try {
            final Calendar cal = Calendar.getInstance();
            Date tanggalNow = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            String stringDeadline = sessionManager.getKeyDeadlinePayment();
            Date tanggalDeadline = formatExp.parse(stringDeadline);

            long millisecondsDateNow = tanggalNow.getTime();
            long millisecondsDeadline = tanggalDeadline.getTime();
            long count = millisecondsDeadline - millisecondsDateNow;

            if (count >= 0) {
                binding.countdown.start(count);
                binding.countdown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        cancelingPaymentMember();
                    }
                });

            } else {
                Timer timer = new Timer();
                final long DELAY = 2000; // milliseconds
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(PembayaranMembership.this).create();
                alertDialog.setTitle("PERHATIAN");
                alertDialog.setMessage("Akun anda melebihi batas waktu untuk proses verifikasi membership DBELGA, akun anda akan dialihkan menjadi akun SILVER.\nAnda dapat mengubah kembali menjadi member debet dalam pengaturan akun aplikasi");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                cancelingPaymentMember();
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void cancelingPaymentMember() {

        final Calendar expired = Calendar.getInstance();
        expired.add(Calendar.YEAR, 100);

        SimpleDateFormat formatExpDate = new SimpleDateFormat("yyyy-MM-dd");
        Date expiredDate = expired.getTime();

        final ProgressDialog progressDialog = ProgressDialog.show(PembayaranMembership.this, "Loading", "Canceling Payment ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call;

        if (isFromNotSilverMember) {
            call = apiInterface.doUpdateStatusMembership(sessionManager.getPID(), dataLog.getMembershipLama().toUpperCase(Locale.ROOT), "", dataLog.getJatuhTempoLama(), dataLog.getExpiredDate(), dataLog.getNominalPlafonLama(), "true");
        } else {
            call = apiInterface.doUpdateStatusMembership(sessionManager.getPID(), "SILVER", "", "1", formatExpDate.format(expiredDate), "0", "true");
        }

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    if (response != null) {

                        if (isFromNotSilverMember) {
                            sessionManager.setMembership(dataLog.getMembershipLama());
                        } else {
                            sessionManager.setMembership("SILVER");
                        }

                        sessionManager.setKeyDeadlinePayment("");
                        Intent intent = new Intent(PembayaranMembership.this, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    Toast.makeText(PembayaranMembership.this, "Error Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PembayaranMembership.this, "Error Update", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    int loop = 0;
    Handler handler = new Handler();

    Runnable myRunnable = new Runnable() {
        public void run() {
            loop++;
            getUserData();
            Log.e(TAG, "run: " + loop);
            handler.postDelayed(this, 10000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: DESTROY");
        handler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onDestroy: PAUSE");
        handler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: RESUME");
        handler.postDelayed(myRunnable, 2000);
    }

}