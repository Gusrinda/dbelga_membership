package com.dbelgamembership.membersip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.dbelgamembership.membersip.Model.modelListTransaksi.Datum;
import com.dbelgamembership.membersip.Model.modelListTransaksi.ModelListTransaksi;
import com.dbelgamembership.membersip.databinding.ActivityAkunSayaBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AkunSaya extends AppCompatActivity {

    TextView namaSaya, statusSaya, textCreditLimit, textSisaLimit, textPiutangBelanja, textTotalPoin, textTotalTransaksi, jumlahVoucherMember, jumlahVoucherKlaim;
    RelativeLayout kartuSaya;
    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass;
    RecyclerView rvVoucher;
    CircleImageView imageUser;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
    LinearLayout plafonDebet, plafonReguler, layoutTotalTransaksi, layoutTotalPoin, layoutDetailTransaksi, layoutVoucherMember, layoutVoucherKlaim;

    String limitPlafon, sisaPlafon, piutangBelanja;
    int poinMember;
    ImageView btnSetting;
    Button btnUpgrade;
    private String TAG = "";
    private String totalPoin, totalTransaksi;

    Toolbar toolbar;

    //Menghitung limit plafon member
    private long limitAwal = 0;
    private long totalPenggunaanLimit = 0;
    private long limitSisa = 0;

    private ActivityAkunSayaBinding akunSayaBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        akunSayaBinding = ActivityAkunSayaBinding.inflate(getLayoutInflater());
        View view = akunSayaBinding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(this);
        findID();


        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, EditAkun.class);
                startActivity(intent);
            }
        });

        namaSaya.setFocusable(true);
        layoutTotalTransaksi.setVisibility(View.GONE);
        layoutDetailTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, ListTransaksi.class);
                startActivity(intent);
            }
        });

        accessDataUser();
        getDataUser();
        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, MembershipChoose.class);
                startActivity(intent);
            }
        });

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, EditAkun.class);
                startActivity(intent);
            }
        });

        layoutVoucherMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, VoucherMember.class);
                startActivity(intent);
            }
        });

        layoutVoucherKlaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, ListVoucher.class);
                startActivity(intent);
            }
        });

        akunSayaBinding.plafonDebet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunSaya.this, LimitPlafon.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void accessDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(AkunSaya.this);
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
                            Gson gson = new Gson();
                            ModelUser modelListTransaction = gson.fromJson(String.valueOf(response), ModelUser.class);
                            com.dbelgamembership.membersip.Model.ModelUser.MsgServer dataUser = modelListTransaction.getMsgServer().get(0);

                            if (dataUser.getCreditLimit() == null || dataUser.getCreditLimit().equals("0")) {
                                limitAwal = 0;
                            } else {
                                limitAwal = Long.parseLong(dataUser.getCreditLimit());
                            }

                            limitPlafon = String.valueOf(limitAwal);
                            sisaPlafon = String.valueOf(dataUser.getSisaCreditLimit());
                            piutangBelanja = String.valueOf(dataUser.getGrandTotalSo());

                            poinMember = dataUser.getPoin();


                            Log.e(TAG, "limit plafon: Rp. " + nf.format(Long.parseLong(limitPlafon)));
                            Log.e(TAG, "sisa plafon: Rp. " + nf.format(Long.parseLong(sisaPlafon)));
                            Log.e(TAG, "piutang belanja: Rp. " + nf.format(Long.parseLong(piutangBelanja)));
                            Log.e(TAG, "Poin Belanja : " + poinMember);

                            textCreditLimit.setText("Rp. " + nf.format(Long.parseLong(limitPlafon)));
                            textSisaLimit.setText("Rp. " + nf.format(Long.parseLong(sisaPlafon)));
                            textPiutangBelanja.setText("Rp. " + nf.format(Long.parseLong(piutangBelanja)));
                            textTotalPoin.setText(poinMember + " Poin");


//                            getSisaPlafonMember(sessionManager.getPID());

                        } else {
                            Toast.makeText(AkunSaya.this, "Tidak ada response", Toast.LENGTH_LONG).show();
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

//    private void getSisaPlafonMember(String idMember) {
//        url = Http.server;
//        url = url + "transaction/list?customer=" + idMember;
//        Log.e(TAG, "URL Ambil Plafon : " + url);
//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        if (response != null) {
//                            Log.e("", "onResponse: " + response);
//                            try {
//                                if (response != null) {
//                                    Gson gson = new Gson();
//                                    ModelListTransaksi modelListTransaction = gson.fromJson(String.valueOf(response), ModelListTransaksi.class);
//                                    List<Datum> itemlist = modelListTransaction.getData().getData();
//                                    if (itemlist.size() > 0) {
//                                        Log.e(TAG, "Nilai plafon awal :" + limitAwal);
//
//                                        Log.e(TAG, "Item transaksi awal :" + itemlist.size());
//                                        for (int i = itemlist.size() - 1; i >= 0; i--) {
//                                            if (!itemlist.get(i).getStatus().equalsIgnoreCase("approved")) {
//                                                itemlist.remove(i);
//                                            }
//                                        }
//                                        Log.e(TAG, "Item transaksi setelah filter :" + itemlist.size());
//
//                                        Log.e(TAG, "Nilai penggunaan plafon awal :  " + totalPenggunaanLimit);
//                                        for (int i = itemlist.size() - 1; i >= 0; i--) {
//                                            totalPenggunaanLimit += itemlist.get(i).getGrandtotal();
//                                        }
//                                        Log.e(TAG, "Nilai penggunaan plafon akhir :  " + totalPenggunaanLimit);
//
//                                        limitSisa = limitAwal - totalPenggunaanLimit;
//
//                                        Log.e(TAG, "Sisa penggunaan plafon :  " + limitSisa);
//                                    } else {
//                                        limitSisa = limitAwal;
//                                    }
//
//                                    textCreditLimit.setText("Rp. " + nf.format(limitAwal));
//                                    textSisaLimit.setText("Rp. " + nf.format(limitSisa));
//
//                                }
//                            } catch (Exception e) {
//                                Log.e(TAG, "onResponse: " + e.getMessage());
//                            }
//                        } else {
////                            Toast.makeText(HomeActivity.this, "Tidak ada response", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
////                        Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        mQueue.add(jsonObjectRequest);
//    }


    private void getDataUser() {
        namaSaya.setText(sessionManager.getName());
        statusSaya.setText(sessionManager.getMembership());
        Log.e(TAG, "getDataUser: " + sessionManager.getImage());


        Drawable image;
        if (sessionManager.getImage() != "" && sessionManager.getImage() != null) {
            Glide.with(this).asBitmap().load(sessionManager.getImage()).centerCrop().into(imageUser);
        } else {
            image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
            imageUser.setImageDrawable(image);
        }


        String cekStatus = sessionManager.getMembership();

        if (cekStatus.equals("DEBET")) {
            image = getResources().getDrawable(R.drawable.member_gold);
            kartuSaya.setBackground(image);
            plafonDebet.setVisibility(View.VISIBLE);
            plafonReguler.setVisibility(View.GONE);
        } else {
            image = getResources().getDrawable(R.drawable.member_premium);
            kartuSaya.setBackground(image);
            plafonDebet.setVisibility(View.GONE);
            plafonReguler.setVisibility(View.VISIBLE);
        }

        getUserVoucher();
        getdataVoucher();

    }

    private void getUserVoucher() {
        url = Http.server;
        url = url + "customer-voucher?customer=" + sessionManager.getPID();
        Log.e(TAG, "URL : " + url );
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
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

                                jumlahVoucher = modelVoucher.getDaftarVoucher().size();

                            }

                            jumlahVoucherMember.setText(String.valueOf(jumlahVoucher) + " Voucher");

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
                    Intent intent = new Intent(getApplicationContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(AkunSaya.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(AkunSaya.this);
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

    private void getdataVoucher() {
        url = Http.server;
        url = url + "list-voucher";
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.length() > 1) {
                                Gson gson = new Gson();
                                ModelSearchVoucher modelListItem = gson.fromJson(response, ModelSearchVoucher.class);
                                List<MsgServer> modelVoucher = modelListItem.getMsgServer();

                                Log.e(TAG, "SIZE 1 : " + modelVoucher.size() );

                                String statusMember = sessionManager.getMembership();

                                for (int i = modelVoucher.size() - 1; i >= 0; i--) {
                                    if (statusMember.equals("REGULER")) {
                                        Log.e(TAG, "Status Member : " + statusMember );
                                        if (modelVoucher.get(i).getTipeMember().equals("DEBET")) {
                                            modelVoucher.remove(i);
                                        }
                                    } else {
                                        Log.e(TAG, "Status Member : " + statusMember );
                                    }
                                }

                                Log.e(TAG, "SIZE 2 : " + modelVoucher.size() );

                                jumlahVoucherKlaim.setText(String.valueOf(modelVoucher.size()) + " Voucher");
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
                    Intent intent = new Intent(getApplicationContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(AkunSaya.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(AkunSaya.this);
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

    private void findID() {
        plafonDebet = findViewById(R.id.plafonDebet);
        plafonReguler = findViewById(R.id.plafonReguler);
        textPiutangBelanja = findViewById(R.id.text_piutangBelanja);
        textSisaLimit = findViewById(R.id.text_sisaLimit);
        textCreditLimit = findViewById(R.id.text_creditLimit);
        imageUser = findViewById(R.id.profile_image);
        btnUpgrade = findViewById(R.id.btb_upgrade);
        namaSaya = findViewById(R.id.namaMember);
        statusSaya = findViewById(R.id.statusMembership);
        kartuSaya = findViewById(R.id.drawMember);
//        rvVoucher = findViewById(R.id.rv_Voucher);
        layoutDetailTransaksi = findViewById(R.id.view_DetailTransaksi);
        layoutTotalPoin = findViewById(R.id.view_TotalPoinBelanja);
        layoutTotalTransaksi = findViewById(R.id.view_TotalTransaksi);
        textTotalPoin = findViewById(R.id.txt_TotalPoin);
        textTotalTransaksi = findViewById(R.id.txt_TotalTransaksi);
        jumlahVoucherMember = findViewById(R.id.text_jumlahVoucherMember);
        jumlahVoucherKlaim = findViewById(R.id.text_jumlahVoucherKlaim);
        layoutVoucherMember = findViewById(R.id.ln_voucherMember);
        layoutVoucherKlaim = findViewById(R.id.ln_voucherKlaim);

        btnSetting = findViewById(R.id.btnSettingAccount);

        toolbar = findViewById(R.id.toolbar);
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(layoutVoucherMember, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }
}