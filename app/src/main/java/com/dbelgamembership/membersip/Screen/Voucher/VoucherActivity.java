package com.dbelgamembership.membersip.Screen.Voucher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelRedeemVoucher.ModelRedeemVoucher;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.ModelSearchVoucher;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelVoucherCustomer.DaftarVoucher;
import com.dbelgamembership.membersip.Model.ModelVoucherCustomer.ModelVoucherCustomer;
import com.dbelgamembership.membersip.Model.ResponseClaim.ResponseClaim;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Voucher.Dummy.AdapterListVoucherAvailable;
import com.dbelgamembership.membersip.Screen.Voucher.Dummy.DummyModelVoucher;
import com.dbelgamembership.membersip.databinding.ActivityVoucherBinding;
import com.dbelgamembership.membersip.databinding.PopupBarcodeMemberBinding;
import com.dbelgamembership.membersip.databinding.PopupRedeemCodeVoucherBinding;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherActivity extends AppCompatActivity implements AdapterListVoucherAvailable.AdapterListVoucherDummyCallback {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityVoucherBinding binding;
    private SessionManager sessionManager;

    int poinMember = 0;

    public static List<DummyModelVoucher> voucherCustomer = new ArrayList<>();
    public static List<DaftarVoucher> voucherMember = new ArrayList<>();
    public static List<DaftarVoucher> voucherMemberSemua = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sessionManager = new SessionManager(this);

        getDataUserMember();
        setupDaftarVoucher();

        binding.iconKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VoucherActivity.this, DaftarVoucherMember.class);
                startActivity(intent);
            }
        });

        binding.btnRedeemCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpRedeemCode();
            }
        });

    }

    private void getDataUserMember() {
        String url = Http.server + "search-customer/" + sessionManager.getPID();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelUser> callUser = apiInterface.doLoopCustomer(url);
        callUser.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, retrofit2.Response<ModelUser> response) {
                ModelUser object = response.body();
                com.dbelgamembership.membersip.Model.ModelUser.MsgServer dataUser = object.getMsgServer().get(0);

                poinMember = (int) Math.floor(dataUser.getPoin());

                if (poinMember < 0) {
                    poinMember = 0;
                }

                binding.txtPoinBelanja.setText(String.valueOf(poinMember));

                getDataVoucherMember();

            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    private PopupRedeemCodeVoucherBinding popupRedeemCodeVoucherBinding;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    private void popUpRedeemCode() {
        popupRedeemCodeVoucherBinding = PopupRedeemCodeVoucherBinding.inflate(getLayoutInflater());
        View view = popupRedeemCodeVoucherBinding.getRoot();

        dialogBuilder = new AlertDialog.Builder(VoucherActivity.this);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();


        popupRedeemCodeVoucherBinding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        popupRedeemCodeVoucherBinding.buttonRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(popupRedeemCodeVoucherBinding.edInputKodeRedeem.getText().toString())) {
                    Toast.makeText(VoucherActivity.this, "Code kosong !", Toast.LENGTH_SHORT).show();
                } else {
                    searchingKodeVoucher(popupRedeemCodeVoucherBinding.edInputKodeRedeem.getText().toString());
                }

            }
        });
    }

    private void searchingKodeVoucher(String kodeVoucher) {
        final ProgressDialog progressDialog = ProgressDialog.show(VoucherActivity.this, "Loading", "Getting Voucher...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doGetVoucherCustomer(kodeVoucher);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    if (response != null) {
                        Gson gson = new Gson();
                        String responseX = String.valueOf(response.body());
                        JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                        boolean success = root.get("success").getAsBoolean();
                        Log.e("", "Test : " + success);
                        if (!success) {
                            Toast.makeText(VoucherActivity.this, root.get("msgServer").toString(), Toast.LENGTH_SHORT).show();
                        } else {

//                            ModelSearchVoucher modelSearchVoucher = gson.fromJson(String.valueOf(response.body()), ModelSearchVoucher.class);
                            ModelRedeemVoucher modelRedeemVoucher = gson.fromJson(String.valueOf(response.body()), ModelRedeemVoucher.class);
                            com.dbelgamembership.membersip.Model.ModelRedeemVoucher.MsgServer voucherDiambil = modelRedeemVoucher.getMsgServer().get(0);

                            int sameVoucher = 0;

                            for (int i = 0; i < voucherMemberSemua.size(); i++) {
                                if (sameVoucher != 1) {
                                    if (voucherMemberSemua.get(i).getCode().equals(voucherDiambil.getCode())) {
                                        sameVoucher = 1;
                                    }
                                }
                            }

                            if (sameVoucher != 1) {
                                alertDialog.dismiss();
                                setupRedeemVoucher(voucherDiambil);
//                                setupClaimVoucher(voucherDiambil, true);
                            } else {
                                Toast.makeText(VoucherActivity.this, "Voucher sudah anda miliki / pernah anda claim !!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(VoucherActivity.this, "Response Null !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(VoucherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Error " + e);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VoucherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    private void setupRedeemVoucher(com.dbelgamembership.membersip.Model.ModelRedeemVoucher.MsgServer voucherDiambil) {
        final ProgressDialog progressDialog = ProgressDialog.show(VoucherActivity.this, "Loading", "Claiming voucher . . .");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doRedeemVoucher("claim-unique-voucher", sessionManager.getPID(), voucherDiambil.getUnikCode(), voucherDiambil.getCode());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                getDataVoucherMember();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VoucherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ERROR :: " + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    List<MsgServer> listVoucher = new ArrayList<>();
    AdapterListVoucherAvailable adapterListSearchVoucher;

    private void setupDaftarVoucher() {

        final ProgressDialog progressDialog = ProgressDialog.show(VoucherActivity.this, "Loading", "Getting voucher list ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);

        Call<JsonElement> call = apiInterface.doGetListVoucher(
                sessionManager.getMembership(),
                "aktif"
        );

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                progressDialog.dismiss();

                try {
                    if (response != null) {
                        Gson gson = new Gson();
                        String responseX = String.valueOf(response.body());
                        JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                        boolean success = root.get("success").getAsBoolean();
                        Log.e("", "Test : " + success);
                        if (!success) {
                            Toast.makeText(VoucherActivity.this, root.get("msgServer").toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            ModelSearchVoucher modelMember = gson.fromJson(String.valueOf(response.body()), ModelSearchVoucher.class);

                            listVoucher.clear();

                            listVoucher = modelMember.getMsgServer();

                            String statusMember = sessionManager.getMembership();

                            for (int i = listVoucher.size() - 1; i >= 0; i--) {
                                if (statusMember.equals("SILVER")) {
                                    if (!listVoucher.get(i).getTipeMember().equals("SILVER")) {
                                        listVoucher.remove(i);
                                    }
                                } else if (statusMember.equals("GOLD")) {
                                    if (!listVoucher.get(i).getTipeMember().equals("SILVER") && !listVoucher.get(i).getTipeMember().equals("GOLD")) {
                                        listVoucher.remove(i);
                                    }
                                }
                            }

                            Log.e(TAG, "SIZE 2 : " + listVoucher.size());

                            for (int i = listVoucher.size() - 1; i >= 0; i--) {
                                if (listVoucher.get(i).getStatus().equals("tidak aktif")) {
                                    listVoucher.remove(i);
                                }
                            }

                            for (int i = listVoucher.size() - 1; i >= 0; i--) {
                                if (listVoucher.get(i).getStok() < 1) {
                                    listVoucher.remove(i);
                                }
                            }

                            getDataVoucherMember();

                        }

                    } else {
                        Toast.makeText(VoucherActivity.this, "Response Null !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(VoucherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Error " + e);
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VoucherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ERROR :: " + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(binding.rvListVoucher, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }

    @Override
    public void onRowDaftarVoucher(MsgServer item, int posisi) {

        boolean isThereAlreadyVoucher = false;

        for (int i = 0; i < voucherMember.size(); i++) {
            if (voucherMember.get(i).getCode().equals(item.getCode())) {
                isThereAlreadyVoucher = true;
            }
        }

        if (isThereAlreadyVoucher) {
            Toast.makeText(VoucherActivity.this, "Anda sudah pernah mengambil voucher yang sama !", Toast.LENGTH_SHORT).show();
        } else {
            if (poinMember >= item.getKlaim()) {
                new KAlertDialog(VoucherActivity.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Redeem Voucher")
                        .setContentText("Anda yakin akan mengurangi poin belanja untuk mengambil voucher ini ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, VoucherActivity.this)
                        .cancelButtonColor(R.color.grey_font, VoucherActivity.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                setupClaimVoucher(item, false);
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
            } else {
                Toast.makeText(VoucherActivity.this, "Poin anda tidak cukup untuk mengambil voucher ini !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void setupClaimVoucher(MsgServer voucher, boolean isRedeem) {

        final ProgressDialog progressDialog = ProgressDialog.show(VoucherActivity.this, "Loading", "Claiming voucher . . .");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doClaimVoucher("claim-voucher/" + sessionManager.getPID(), sessionManager.getPID(), voucher.getCode());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    Log.e(TAG, "Response : " + response);
                    Gson gson = new Gson();
                    ResponseClaim responseWishlist = gson.fromJson(String.valueOf(response.body()), ResponseClaim.class);
                    Toast.makeText(VoucherActivity.this, responseWishlist.getDescription(), Toast.LENGTH_SHORT).show();

                    if (!isRedeem) {
                        poinMember -= voucher.getKlaim();
                    }
                    binding.txtPoinBelanja.setText(String.valueOf(poinMember));

                    getDataVoucherMember();

                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage() + Arrays.toString(e.getStackTrace()));
                    Snack(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VoucherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }
        });

    }

    private void getDataVoucherMember() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelVoucherCustomer> call = apiInterface.doGetVoucherMember(sessionManager.getPID());

        call.enqueue(new Callback<ModelVoucherCustomer>() {
            @Override
            public void onResponse(Call<ModelVoucherCustomer> call, Response<ModelVoucherCustomer> response) {
                try {
                    voucherMember.clear();
                    voucherMemberSemua.clear();
                    Log.e(TAG, "onResponse: " + response);
                    Gson gson = new Gson();
                    ModelVoucherCustomer modelVoucher = response.body();
                    com.dbelgamembership.membersip.Model.ModelVoucherCustomer.MsgServer mVoucher = modelVoucher.getMsgServer().get(0);

                    int jumlahVoucher = 0;

                    voucherMemberSemua = mVoucher.getDaftarVoucher();

                    for (int i = 0; i < mVoucher.getDaftarVoucher().size(); i++) {

                        boolean isVoucherExpired = false;

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final Calendar baru = Calendar.getInstance();

                        Date tanggalNow = baru.getTime();
                        Date tanggalAkhir = formatter.parse(mVoucher.getDaftarVoucher().get(i).getExpiredDate());

                        long mlNow = tanggalNow.getTime();
                        long mlAkhir = tanggalAkhir.getTime();

                        if (mlNow <= mlAkhir) {
                            isVoucherExpired = false;
                        } else {
                            isVoucherExpired = true;
                        }

                        if (!mVoucher.getDaftarVoucher().get(i).getFlagPakai() && !isVoucherExpired) {
                            voucherMember.add(mVoucher.getDaftarVoucher().get(i));
                            jumlahVoucher++;
                        }

                    }
                    binding.iconKeranjang.setBadgeValue(jumlahVoucher);

                    if (listVoucher.size() > 0) {
                        binding.rvListVoucher.setAdapter(null);

                        for (int i = 0; i < voucherMemberSemua.size(); i++) {

                            Log.e(TAG, "onResponse: VOUCHER MEMBER :: " + voucherMemberSemua.get(i).getCode());
                            for (int j = listVoucher.size() - 1; j >= 0; j--) {
                                Log.e(TAG, "onResponse: VOUCHER LIST :: " + listVoucher.get(j).getCode());

                                if (voucherMemberSemua.get(i).getCode().equals(listVoucher.get(j).getCode())) {
                                    Log.e(TAG, "onResponse: REMOVING THIS SHIT !!!");
                                    listVoucher.remove(j);
//                                    listVouhcerAkhir.remove(j);
                                    Log.e(TAG, "onResponse: SIZE VOUCHER BIASA :: " + listVoucher.size());
//                                    Log.e(TAG, "onResponse: SIZE VOUCHER AKHIR :: " + listVouhcerAkhir.size());
                                }
                            }
                        }

                        Log.e(TAG, "onResponse: VOUCHER SIZE :: " + listVoucher.size());

                        adapterListSearchVoucher = new AdapterListVoucherAvailable(VoucherActivity.this, listVoucher, false, VoucherActivity.this);
                        binding.rvListVoucher.setAdapter(adapterListSearchVoucher);
                    } else {
                        Snack("Voucher Kosong");
                    }

                } catch (Exception e) {
                    Log.e(TAG, "onResponse: Error " + e);
                }
            }

            @Override
            public void onFailure(Call<ModelVoucherCustomer> call, Throwable t) {
                Toast.makeText(VoucherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }
        });

    }
}