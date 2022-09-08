package com.dbelgamembership.membersip.Screen.PembayaranTransfer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.ApiBanks;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.Api_Banks.BniDetailPayment.BniDetailPayment;
import com.dbelgamembership.membersip.Model.Api_Banks.BriDetailPayment.BriDetailPayment;
import com.dbelgamembership.membersip.Model.modelListTransaksi.DetailPaymentBni;
import com.dbelgamembership.membersip.Model.responseCancel.ResponseCancel;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Katalog.Model.PostBNI;
import com.dbelgamembership.membersip.Screen.Setting.SupportActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintActivity;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.PembayaranMembership;
import com.dbelgamembership.membersip.databinding.ActivityTransferPaymentBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

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
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferPayment extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityTransferPaymentBinding binding;
    private SessionManager sessionManager;

    String kodeSO = "";
    String kodeFaktur = "";
    String kodePembayaran = "";
    String jenisBanks = "";
    String kodeGudang = "";

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

    ClipboardManager clipboardManager;

    SimpleDateFormat formatExp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DetailPaymentBni detailPaymentBni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransferPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().hasExtra("hasExtra")) {

            jenisBanks = getIntent().getStringExtra("banks");
            kodePembayaran = getIntent().getStringExtra("kode_payment");
            kodeSO = getIntent().getStringExtra("kode_so");
            kodeFaktur = getIntent().getStringExtra("kode_faktur");
            kodeGudang = getIntent().getStringExtra("kode_gudang");

            if (jenisBanks.equals("BNI")) {
                detailPaymentBni = getIntent().getParcelableExtra("data_payment");

                Log.e(TAG, "ON PAGE PAYMENT :: " + detailPaymentBni.getTrxId()  );
            }


            setupViewPembayaran(jenisBanks, kodePembayaran);

        } else {
            finish();
            Toast.makeText(this, "Tidak ada pembayaran !", Toast.LENGTH_SHORT).show();
        }

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupViewPembayaran(jenisBanks, kodePembayaran);
            }
        });

        binding.btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransferPayment.this, SupportActivity.class);
                startActivity(intent);
            }
        });


    }

    @SuppressLint("NewApi")
    private void setupViewPembayaran(String jenisBanks, String kodePembayaran) {

        binding.swipeRefresh.setRefreshing(false);

        if (jenisBanks.equals("BRI")) {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable myDrawable = getResources().getDrawable(R.drawable.bri_icon);
            binding.imgIconBank.setImageDrawable(myDrawable);
            searchPaymentBRI(kodePembayaran);
        } else if (jenisBanks.equals("BNI")) {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable myDrawable = getResources().getDrawable(R.drawable.bni_icon);
            binding.imgIconBank.setImageDrawable(myDrawable);
            searchPaymentBNI(detailPaymentBni.getTrxId());
        }else {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable myDrawable = getResources().getDrawable(R.drawable.template_card_icon);
            binding.imgIconBank.setImageDrawable(myDrawable);
        }

    }

    private void searchPaymentBNI(String trxId) {

        final ProgressDialog progressDialog = ProgressDialog.show(TransferPayment.this, "Loading", "Getting VA ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<BniDetailPayment> call = apiInterface.getPaymentBNI(
                "16055",
                "inquirybilling",
                trxId
        );

        call.enqueue(new Callback<BniDetailPayment>() {
            @Override
            public void onResponse(Call<BniDetailPayment> call, Response<BniDetailPayment> response) {
                try {
                    progressDialog.dismiss();
                    if (response != null) {
                        Toast.makeText(TransferPayment.this, "berhasil get data payment !", Toast.LENGTH_LONG).show();

                        BniDetailPayment detailPayment = response.body();

                        Gson gson = new Gson();
                        String json = gson.toJson(detailPayment);

                        binding.txtNamaBank.setText("BNI");

                        binding.txtNomorPembayaran.setText(detailPayment.getData().getVirtualAccount());
                        binding.txtAmountPembayaran.setText("Rp. " + nf.format(Double.parseDouble(detailPayment.getData().getTrxAmount())));

                        binding.btnCopyPembayaran.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                assert detailPayment != null;
                                Toast.makeText(TransferPayment.this, "berhasil copy data : " + detailPayment.getData().getVirtualAccount(), Toast.LENGTH_SHORT).show();
                                ClipData clipData = ClipData.newPlainText("text", detailPayment.getData().getVirtualAccount());
                                clipboardManager.setPrimaryClip(clipData);
                            }
                        });


                        try {
                            final Calendar cal = Calendar.getInstance();
                            Date tanggalNow = cal.getTime();
                            String stringDeadline = detailPayment.getData().getDatetimeExpired();
                            Date tanggalDeadline = formatExp.parse(stringDeadline);

                            long millisecondsDateNow = tanggalNow.getTime();
                            long millisecondsDeadline = tanggalDeadline.getTime();
                            long count = millisecondsDeadline - millisecondsDateNow;


                            if (!detailPayment.getData().getVaStatus().equals("2")) {
                                if (count >= 0) {
                                    binding.countdownTimer.start(count);
                                    binding.countdownTimer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                                        @Override
                                        public void onEnd(CountdownView cv) {

                                            if (!detailPayment.getData().getVaStatus().equals("2")) {
                                                Toast.makeText(TransferPayment.this, "Waktu untuk pembayaran berakhir !", Toast.LENGTH_SHORT).show();
                                                searchPaymentBNI(trxId);
                                            }

                                        }
                                    });

                                } else {

                                    Timer timer = new Timer();
                                    final long DELAY = 2000; // milliseconds
                                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(TransferPayment.this).create();
                                    alertDialog.setTitle("PERHATIAN");
                                    alertDialog.setMessage("Waktu untuk pembayaran anda sudah berakhir , apakah anda ingin melanjutkan pembelian transaksi ini atau tidak ?");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                            new DialogInterface.OnClickListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.N)
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //Canceling SO
                                                    createNewVA();
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //Canceling SO
                                                    methodCancelTransaksi();
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }

                                if (count >= 0) {
                                    if (detailPayment.getData().getVaStatus().equals("2")) {
                                        binding.btnUpdateManual.setVisibility(View.VISIBLE);
                                        binding.txtStatusPembayaran.setText("SELESAI DIBAYAR");
                                        binding.txtStatusPembayaran.setTextColor(Color.GREEN);
                                    } else {
                                        binding.btnUpdateManual.setVisibility(View.GONE);
                                        binding.txtStatusPembayaran.setText("BELUM DIBAYAR");
                                        binding.txtStatusPembayaran.setTextColor(Color.RED);
                                    }

                                } else  {
                                    binding.btnUpdateManual.setVisibility(View.GONE);
                                    binding.txtStatusPembayaran.setText("BELUM DIBAYAR (EXPIRED)");
                                    binding.txtStatusPembayaran.setTextColor(Color.RED);
                                }

                            } else {


                                if (detailPayment.getData().getDatetimePayment() == null ) {
                                    binding.btnUpdateManual.setVisibility(View.GONE);
                                    binding.txtStatusPembayaran.setText("BELUM DIBAYAR (EXPIRED)");
                                    binding.txtStatusPembayaran.setTextColor(Color.RED);

                                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(TransferPayment.this).create();
                                    alertDialog.setTitle("PERHATIAN");
                                    alertDialog.setMessage("Waktu untuk pembayaran anda sudah berakhir , apakah anda ingin melanjutkan pembelian transaksi ini atau tidak ?");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                            new DialogInterface.OnClickListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.N)
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //Canceling SO
                                                    createNewVA();
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //Canceling SO
//                                                    methodCancelTransaksi();
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();

                                } else  {
                                    binding.btnUpdateManual.setVisibility(View.VISIBLE);
                                    binding.txtStatusPembayaran.setText("SELESAI DIBAYAR");
                                    binding.txtStatusPembayaran.setTextColor(Color.GREEN);

                                }

                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                        binding.btnUpdateManual.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                updateStatusTransaksi();
                            }
                        });

                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    Toast.makeText(TransferPayment.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BniDetailPayment> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(TransferPayment.this, "Error Transfer", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createNewVA() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        Date expiredLocalTime = calendar.getTime();

        String offset = "+07:00";
        LocalDateTime ldt = LocalDateTime.ofInstant(expiredLocalTime.toInstant(), ZoneId.systemDefault());
        ZoneOffset zoneOffset = ZoneOffset.of(offset);
        OffsetDateTime odt = OffsetDateTime.of(ldt, zoneOffset);

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String formattedDate = odt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        try {

            String lastFoutStamp = timeStamp.substring(timeStamp.length() - 4);
            int idCustomer = Integer.parseInt(sessionManager.getPID());

            String with4digits = String.format("%04d", idCustomer);

            String templateVANumber = "98816055" + with4digits.substring(0,4) + lastFoutStamp;

            PostBNI postBNI = new PostBNI(
                    "BELANJA",
                    kodeSO,
                    "createbilling",
                    "16055",
                    kodeSO + "-" + timeStamp,
                    String.valueOf(Math.round((int) Double.parseDouble(detailPaymentBni.getTrxAmount()))),
                    "c",
                    sessionManager.getName(),
                    sessionManager.getEmail(),
                    sessionManager.getKeyTelefonMember(),
                    templateVANumber,
                    formattedDate,
                    "TRANSAKSI PEMBAYARAN BELANJA KODE : " + kodeSO
            );


            final ProgressDialog progressDialog = ProgressDialog.show(TransferPayment.this, "Loading", "Pembuatan VA ...");
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
                            Toast.makeText(TransferPayment.this, "Order berhasil, Lakukan Pelunasan Transfer !", Toast.LENGTH_LONG).show();
                            finish();
//                            Intent intent = new Intent(TransferPayment.this, PrintActivity.class);
//                            Log.e(TAG, "onRowAdapterListTransactionClicked: " + kodeSO);
//                            intent.putExtra("DATAPRINT", kodeSO);
//                            intent.putExtra("isPayment", true);
////                            intent.putExtra("isPayment", true);
//                            startActivity(intent);
//                            finish();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                        Toast.makeText(TransferPayment.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, "onResponse: " + t.getMessage());
                    Toast.makeText(TransferPayment.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
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
                .append(ApiBanks.BRI_TOKEN.getAccessToken()).append("&timestamp=").append(formattedDate).append("&body=");

        String sign = null;
        try {
            sign = hash_hmac(stringPayload.toString(), ApiBanks.BRI_CONSUMER_SECRET);

            String bearerToken = "Bearer " + ApiBanks.BRI_TOKEN.getAccessToken();

            final ProgressDialog progressDialog = ProgressDialog.show(TransferPayment.this, "Loading", "Pembuatan VA ...");
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
                        progressDialog.dismiss();
                        if (response != null) {
                            Toast.makeText(TransferPayment.this, "berhasil get data payment !", Toast.LENGTH_LONG).show();

                            BriDetailPayment detailPayment = response.body();

                            Gson gson = new Gson();
                            String json = gson.toJson(detailPayment);

                            binding.txtNomorPembayaran.setText(detailPayment.getData().getCustCode());
                            binding.txtAmountPembayaran.setText("Rp. " + nf.format(Double.parseDouble(detailPayment.getData().getAmount())));

                            binding.btnCopyPembayaran.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    assert detailPayment != null;
                                    Toast.makeText(TransferPayment.this, "berhasil copy data : " + detailPayment.getData().getCustCode(), Toast.LENGTH_SHORT).show();
                                    ClipData clipData = ClipData.newPlainText("text", detailPayment.getData().getCustCode());
                                    clipboardManager.setPrimaryClip(clipData);
                                }
                            });

                            if (detailPayment.getData().getStatusBayar().equals("Y")) {
                                binding.btnUpdateManual.setVisibility(View.VISIBLE);
                                binding.txtStatusPembayaran.setText("SELESAI DIBAYAR");
                                binding.txtStatusPembayaran.setTextColor(Color.GREEN);
                            } else {
                                binding.btnUpdateManual.setVisibility(View.GONE);
                                binding.txtStatusPembayaran.setText("BELUM DIBAYAR");
                                binding.txtStatusPembayaran.setTextColor(Color.RED);
                            }

                            binding.btnUpdateManual.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    updateStatusTransaksi();
                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                        Toast.makeText(TransferPayment.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BriDetailPayment> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, "onResponse: " + t.getMessage());
                    Toast.makeText(TransferPayment.this, "Error Transfer", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //TODO :: UPDATE STATUS PEMBAYARAN !!!!
    private void updateStatusTransaksi() {

        final ProgressDialog progressDialog = ProgressDialog.show(TransferPayment.this, "Loading", "Pengubahan status ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> callUpdate = apiInterface.doUpdateSO(kodeSO, "confirmation");

        callUpdate.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                Toast.makeText(TransferPayment.this, "Pembayaran berhasil, tunggu konfirmasi dari admin dan barang segera dikirim !", Toast.LENGTH_LONG).show();

                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private String hash_hmac(String str, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        String hash = Base64.encodeToString(sha256_HMAC.doFinal(str.getBytes()), Base64.NO_WRAP);

        return hash;
    }

    private void methodCancelTransaksi() {
        final ProgressDialog progressDialog = ProgressDialog.show(TransferPayment.this, "Loading", "Canceling Transaction ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ResponseCancel> call = apiInterface.doCancelTransaksi(kodeSO, kodeFaktur);

        call.enqueue(new Callback<ResponseCancel>() {
            @Override
            public void onResponse(Call<ResponseCancel> call, retrofit2.Response<ResponseCancel> response) {
                try {
                    progressDialog.dismiss();
                    if (response != null) {
                        ResponseCancel object = response.body();
                        assert object != null;
                        if (object.getDescription().equals("Update success!")) {
                            Toast.makeText(getApplicationContext(), "Berhasil canceling order", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e(TAG, "onResponse: " + object.getDescription());
                            Toast.makeText(TransferPayment.this, object.getDescription(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage() + Arrays.toString(e.getStackTrace()));
                    Toast.makeText(TransferPayment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseCancel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}