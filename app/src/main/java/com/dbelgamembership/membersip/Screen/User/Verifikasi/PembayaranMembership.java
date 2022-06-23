package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Constant;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelUser.MsgServer;
import com.dbelgamembership.membersip.Screen.Limit.RiwayatTagihan;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.databinding.ActivityPembayaranMembershipBinding;
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

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranMembership extends AppCompatActivity implements TransactionFinishedCallback {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityPembayaranMembershipBinding binding;
    private SessionManager sessionManager;
    private Boolean isPay = false;

    SimpleDateFormat formatExp;

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

        initMidTransSDK();
        getUserData();
        setupCountDown();
        setupButton();

    }

    private void initMidTransSDK() {
        String client_key = Constant.MERCHANT_CLIENT_KEY;
        String base_url = Constant.MERCHANT_BASE_CHECKOUT_URL;
        SdkUIFlowBuilder sdkUIFlowBuilder = SdkUIFlowBuilder.init()
                .setClientKey(client_key) // client_key is mandatory
                .setContext(this) // context is mandatory
                .setTransactionFinishedCallback(this) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(base_url)//set merchant url
                .setUIkitCustomSetting(uiKitCustomSetting())
                .enableLog(true) // enable sdk log
//                .setColorTheme(new CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // will replace theme on snap theme on MAP
                .setLanguage("id");
        sdkUIFlowBuilder.buildSDK();
    }

    private UIKitCustomSetting uiKitCustomSetting() {
        UIKitCustomSetting uIKitCustomSetting = new UIKitCustomSetting();
        uIKitCustomSetting.setSkipCustomerDetailsPages(true);
        uIKitCustomSetting.setShowPaymentStatus(true);
        return uIKitCustomSetting;
    }

    private void setupButton() {

        binding.btnBatalPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(PembayaranMembership.this);
                builder1.setTitle("Konfirmasi");
                builder1.setMessage("Anda yakin untuk membatalkan proses membership ?\n(Status membership anda akan berubah menjadi SILVER)");
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

        binding.btnLakukanPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PembayaranMembership.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PembayaranMembership.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                } else {
                    setupPembayaran();
                }
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

    private void setupPembayaran() {

        CustomerDetails mCustomerDetails = new CustomerDetails();
        mCustomerDetails.setPhone(sessionManager.getKeyTelefonMember());
        mCustomerDetails.setFirstName(sessionManager.getName());
        mCustomerDetails.setEmail(sessionManager.getEmail());
        mCustomerDetails.setCustomerIdentifier(sessionManager.getPID());

        TransactionRequest transactionRequestNew = new
                TransactionRequest("PAY_MEMBER_" + sessionManager.getPID() + "_" + System.currentTimeMillis(), 50000);
        transactionRequestNew.setCustomerDetails(mCustomerDetails);

        MidtransSDK.getInstance().setTransactionRequest(transactionRequestNew);
        MidtransSDK.getInstance().startPaymentUiFlow(PembayaranMembership.this);
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(this, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();

                    uploadPembayaran();

                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
            }
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
                        String responseX = String.valueOf(response);
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

    private void setupView() {
        if (isPay) {
            binding.animationView.setAnimationFromUrl("https://assets7.lottiefiles.com/packages/lf20_dyf5lscb.json");
            binding.animationView.playAnimation();
            binding.tvNominalBayar.setVisibility(View.GONE);
            binding.tvDeskripsi.setText("Pembayaran sedang dikonfirmasi, silahkan menunggu. Anda bisa menghubungi admin dengan menekan icon telfon diatas");
            binding.btnLakukanPembayaran.setVisibility(View.GONE);
        } else {
            binding.btnLakukanPembayaran.setVisibility(View.VISIBLE);
            binding.tvNominalBayar.setVisibility(View.VISIBLE);
            binding.animationView.setAnimationFromUrl("https://assets5.lottiefiles.com/packages/lf20_yzoqyyqf.json");
            binding.animationView.playAnimation();
            binding.tvDeskripsi.setText("Anda diharuskan membayar sesuai dengan nominal diatas untuk menjadi member dBelga, lakukan pembayaran dengan menekan tombol bayar dibawah");
        }
    }

    private void getUserData() {
        String url = Http.server + "search-customer/" + sessionManager.getPID();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelUser> callUser = apiInterface.doLoopCustomer(url);
        callUser.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, retrofit2.Response<ModelUser> response) {
                ModelUser object = response.body();

                boolean status_pay = Boolean.parseBoolean(object.getMsgServer().get(0).getStatusPayment());

                if (status_pay) {
                    Intent intent = new Intent(PembayaranMembership.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    MsgServer dataMember = object.getMsgServer().get(0);

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

                    setupView();
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
        final ProgressDialog progressDialog = ProgressDialog.show(PembayaranMembership.this, "Loading", "Canceling Payment ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doUpdateStatusMembership(sessionManager.getPID(), "SILVER", "");

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    if (response != null) {
                        sessionManager.setMembership("SILVER");
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