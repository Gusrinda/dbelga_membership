package com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.ModelSearchVoucher;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelVoucherCustomer.ModelVoucherCustomer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.Screen.Limit.BayarTagihan;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.Limit.LimitPlafon;
import com.dbelgamembership.membersip.Screen.User.AkunSaya;
import com.dbelgamembership.membersip.Screen.User.ListVoucher;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipChoose;
import com.dbelgamembership.membersip.Screen.User.VoucherMember;
import com.dbelgamembership.membersip.Screen.Voucher.DaftarVoucherMember;
import com.dbelgamembership.membersip.Screen.Voucher.VoucherActivity;
import com.dbelgamembership.membersip.databinding.FragmentAkunBinding;
import com.dbelgamembership.membersip.databinding.PopupBarcodeMemberBinding;
import com.developer.kalert.KAlertDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AkunFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    public String url = Http.server, jsonResult, type, user, pass;

    SessionManager sessionManager;

    private FragmentAkunBinding binding;

    String todayString, todayBirthday, birthday;

    String limitPlafon, sisaPlafon, piutangBelanja;
    int poinMember;

    ModelUser modelDatUser;
    private boolean isHavingDenda = false;

    //Menghitung limit plafon member
    private long limitAwal = 0;
    private long totalPenggunaanLimit = 0;
    private long limitSisa = 0;

    ClipboardManager clipboardManager;

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

    public AkunFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getDateServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAkunBinding.inflate(inflater, container, false);
        sessionManager = new SessionManager(requireContext());

        getDateServer();

        clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        binding.viewDetailTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewMainActivity.bottomNavigationView.setSelectedItemId(R.id.transaksiFragment);
            }
        });

        binding.btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        binding.btnPiutangBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), LimitPlafon.class);
                startActivity(intent);
            }
        });

        binding.btnMembershipPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.isLoggedIn()) {

                    if (isHavingDenda) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                        alert.setIcon(R.drawable.dbelga);
                        alert.setTitle("Attention!");
                        alert.setMessage("Anda harus melunasi piutang anda terlebih dahulu !");
                        alert.setPositiveButton("LUNASI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(requireActivity(), BayarTagihan.class);
                                startActivity(intent);
                            }
                        });
                        alert.setNeutralButton("TUTUP", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alert.show();
                    } else {
                        Intent intent = new Intent(requireContext(), MembershipChoose.class);
                        startActivity(intent);
                    }


                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                    alert.setIcon(R.drawable.dbelga);
                    alert.setTitle("Attention!");
                    alert.setMessage("Anda harus mempunyai akun Membership terlebih dahulu untuk melihat proses upgrade membership");
                    alert.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(requireActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                    alert.setNegativeButton("REGISTER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(requireActivity(), RegisterActivity.class);
                            startActivity(intent);
                            getActivity().finish();
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
        });

        binding.cardMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupPopUpBarcode();
            }
        });

        binding.lnVoucherMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), DaftarVoucherMember.class);
                startActivity(intent);
            }
        });

        binding.lnVoucherKlaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), VoucherActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();

    }

    PopupBarcodeMemberBinding popupBarcodeMemberBinding;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    private void setupPopUpBarcode() {

        String kodeUser = modelDatUser.getMsgServer().get(0).getCode();

        popupBarcodeMemberBinding = PopupBarcodeMemberBinding.inflate(getLayoutInflater());
        View view = popupBarcodeMemberBinding.getRoot();

        dialogBuilder = new AlertDialog.Builder(requireContext());

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        popupBarcodeMemberBinding.txtBarcode.setText(kodeUser);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(kodeUser, BarcodeFormat.CODE_128, 300, 120);
            popupBarcodeMemberBinding.outputBarcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "ERROR GENERATE BARCODE !", Toast.LENGTH_SHORT).show();
        }

        popupBarcodeMemberBinding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        popupBarcodeMemberBinding.buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData = ClipData.newPlainText("text", kodeUser);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(requireContext(), "Berhasil copy kode member !", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getDateServer() {
        url = Http.server;
        url = url + "get-date";

        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
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
                                    Toast.makeText(requireContext(), response.getJSONArray("msgServer").toString(), Toast.LENGTH_LONG).show();
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

                                    SetupDataAkun();

                                }
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(requireContext(), "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);

    }

    private void SetupDataAkun() {
        //GetDataMember
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(requireContext());
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
        Log.e(TAG, "SetupDataAkun: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog1.dismiss();
                        if (response != null) {
                            Log.e("", "onResponse: " + response);
                            Gson gson = new Gson();
                            ModelUser modelListTransaction = gson.fromJson(String.valueOf(response), ModelUser.class);
                            com.dbelgamembership.membersip.Model.ModelUser.MsgServer dataUser = modelListTransaction.getMsgServer().get(0);

                            try {
                                modelDatUser = modelListTransaction;
                                String status_member = dataUser.getStatusMember();
                                String expiredMember = dataUser.getExpiredDate();

                                String lastUpdate = dataUser.getUpdatedAt();

                                String ulangTahun = dataUser.getDateBirth();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                SimpleDateFormat formatEXP = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat formatHariIni = new SimpleDateFormat("dd-MM-yyyy");
                                Date birth = null;
                                birth = formatEXP.parse(ulangTahun);


                                Date terakhirUpdate = formatter.parse(lastUpdate);


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
                                String expDate = formatHariIni.format(expired);
                                Log.e("", "status member: " + status_member);
                                Log.e("", "expired date: " + expDate);
                                String urlImage = dataUser.getImageCustomer();

                                if (urlImage.equals("http://8.215.31.212/upload/customer-photo/")) {
                                    urlImage = "";
                                } else {
                                    urlImage = dataUser.getImageCustomer();
                                }

                                Log.e(TAG, "url Image: " + urlImage);
                                sessionManager.setImage(urlImage);
                                sessionManager.setMembership(status_member);
                                sessionManager.setExpiredDate(expDate);
                                sessionManager.setAccountUser(dataUser.getName(), dataUser.getMainEmail(), dataUser.getMainAddress(), dataUser.getMainPhone1());

                                if (dataUser.getCreditLimit() == null || dataUser.getCreditLimit().equals("0")) {
                                    limitAwal = 0;
                                } else {
                                    limitAwal = Long.parseLong(dataUser.getCreditLimit());
                                }

                                limitPlafon = String.valueOf(limitAwal);
                                sisaPlafon = String.valueOf((int) dataUser.getSisaCreditLimit());
                                piutangBelanja = String.valueOf(dataUser.getGrandTotalDebet());

                                poinMember = (int) Math.floor(dataUser.getPoin());

                                if (poinMember < 0) {
                                    poinMember = 0;
                                }

                                Log.e(TAG, "limit plafon: Rp. " + nf.format(Long.parseLong(limitPlafon)));
                                Log.e(TAG, "sisa plafon: Rp. " + nf.format(Long.parseLong(sisaPlafon)));
                                Log.e(TAG, "piutang belanja: Rp. " + nf.format(Long.parseLong(String.valueOf((int) Double.parseDouble(piutangBelanja)))));
                                Log.e(TAG, "Poin Belanja : " + poinMember);

                                binding.textCreditLimit.setText("Rp. " + nf.format(Long.parseLong(limitPlafon)));
                                binding.textSisaLimit.setText("Rp. " + nf.format(Math.floor(Double.parseDouble(sisaPlafon))));
                                binding.textPiutangBelanja.setText("Rp. " + nf.format(Math.ceil(Double.parseDouble(piutangBelanja))));
                                binding.txtTotalPoin.setText(poinMember + " Poin");

                                if (dataUser.getFlagDenda().equals("true")) {
                                    isHavingDenda = true;
                                } else {
                                    isHavingDenda = false;
                                }

                                long longHari = todayDate.getTime() - terakhirUpdate.getTime();
                                int jumlahHari = (int) TimeUnit.DAYS.convert(longHari, TimeUnit.MILLISECONDS);

//                                if (jumlahHari < 30 || Math.ceil(Double.parseDouble(piutangBelanja)) > 0 || isHavingDenda) {
//                                    binding.btnMembershipPlan.setVisibility(View.GONE);
//                                } else {
//                                    binding.btnMembershipPlan.setVisibility(View.VISIBLE);
//                                }

                                cekMember();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(requireContext(), "Tidak ada response", Toast.LENGTH_LONG).show();
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void cekMember() {

        String nama = sessionManager.getName();
        binding.txtNomorMember.setText(sessionManager.getPID());
        binding.txtExpDate.setText(sessionManager.getExpiredDate());
        String namaPendek;
        if (nama.length() > 15) {
            namaPendek = nama.substring(0, 15);
        } else {
            namaPendek = nama;
        }
        binding.txtNamaMember.setText(namaPendek.toUpperCase());

        Drawable imageKartu;

        if (sessionManager.getMembership().equals("SILVER")) {
            imageKartu = getResources().getDrawable(R.drawable.card_member_silver);
            binding.layoutCardMember.setBackground(imageKartu);
            binding.layoutExpired.setVisibility(View.GONE);
            binding.plafonDebet.setVisibility(View.GONE);
            binding.plafonReguler.setVisibility(View.VISIBLE);
        } else if (sessionManager.getMembership().equals("GOLD")) {
            imageKartu = getResources().getDrawable(R.drawable.card_member_gold);
            binding.layoutCardMember.setBackground(imageKartu);
            binding.layoutExpired.setVisibility(View.VISIBLE);
            binding.plafonDebet.setVisibility(View.VISIBLE);
            binding.plafonReguler.setVisibility(View.GONE);
        } else if (sessionManager.getMembership().equals("PLATINUM")) {
            imageKartu = getResources().getDrawable(R.drawable.card_member_platinum);
            binding.layoutCardMember.setBackground(imageKartu);
            binding.layoutExpired.setVisibility(View.VISIBLE);
            binding.plafonDebet.setVisibility(View.VISIBLE);
            binding.plafonReguler.setVisibility(View.GONE);
        }

        getUserVoucher();
        getdataVoucher();

    }

    private void getUserVoucher() {
        url = Http.server;
        url = url + "customer-voucher?customer=" + sessionManager.getPID();
        Log.e(TAG, "URL : " + url);
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            int jumlahVoucher = 0;
                            if (response.length() > 1) {
                                Gson gson = new Gson();
                                ModelVoucherCustomer modelListItem = gson.fromJson(response, ModelVoucherCustomer.class);
                                com.dbelgamembership.membersip.Model.ModelVoucherCustomer.MsgServer modelVoucher = modelListItem.getMsgServer().get(0);

                                for (int i = 0; i < modelVoucher.getDaftarVoucher().size(); i++) {

                                    boolean isVoucherExpired = false;

                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    final Calendar baru = Calendar.getInstance();

                                    Date tanggalNow = baru.getTime();
                                    Date tanggalAkhir = formatter.parse(modelVoucher.getDaftarVoucher().get(i).getExpiredDate());

                                    long mlNow = tanggalNow.getTime();
                                    long mlAkhir = tanggalAkhir.getTime();

                                    if (mlNow <= mlAkhir) {
                                        isVoucherExpired = false;
                                    } else {
                                        isVoucherExpired = true;
                                    }

                                    if (!modelVoucher.getDaftarVoucher().get(i).getFlagPakai() && !isVoucherExpired) {
                                        jumlahVoucher++;
                                    }

                               }

                            }

                            binding.textJumlahVoucherMember.setText(String.valueOf(jumlahVoucher) + " Voucher");

                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: Error " + e);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(requireContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(requireContext(), "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getdataVoucher();
                                }
                            });
                    builder1.setNegativeButton(
                            "Tidak",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    getActivity().finish();
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

    private void getdataVoucher() {
        url = Http.server;
        url = url + "list-voucher";
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.length() > 1) {
                                Gson gson = new Gson();
                                ModelSearchVoucher modelListItem = gson.fromJson(response, ModelSearchVoucher.class);
                                List<MsgServer> modelVoucher = modelListItem.getMsgServer();

                                Log.e(TAG, "SIZE 1 : " + modelVoucher.size());

                                String statusMember = sessionManager.getMembership();

                                for (int i = modelVoucher.size() - 1; i >= 0; i--) {
                                    if (statusMember.equals("SILVER")) {
                                        Log.e(TAG, "Status Member : " + statusMember);
                                        if (modelVoucher.get(i).getTipeMember().equals("DEBET")) {
                                            modelVoucher.remove(i);
                                        }
                                    } else {
                                        Log.e(TAG, "Status Member : " + statusMember);
                                    }
                                }

                                Log.e(TAG, "SIZE 2 : " + modelVoucher.size());

                                binding.textJumlahVoucherKlaim.setText(String.valueOf(modelVoucher.size()) + " Voucher");

                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: Error " + e);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(requireContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//                    Snack(error.getMessage());
                    Toast.makeText(requireContext(), "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getdataVoucher();
                                }
                            });
                    builder1.setNegativeButton(
                            "Tidak",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    getActivity().finish();
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

    private void logout() {

        new KAlertDialog(requireContext(), KAlertDialog.WARNING_TYPE)
                .setTitleText("Logout")
                .setContentText("Anda akan keluar dari sesi aplikasi")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, requireContext())
                .cancelButtonColor(R.color.grey_font, requireContext())
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        getActivity().finish();
                        sessionManager.destroySession();
                        Intent intent = new Intent(requireContext(), SplashActivity.class);
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
}