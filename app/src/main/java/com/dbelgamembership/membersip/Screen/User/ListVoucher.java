package com.dbelgamembership.membersip.Screen.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.app.Adapter.AdapterListVoucher;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.ModelSearchVoucher;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelUser.MsgServer;
import com.dbelgamembership.membersip.Model.ResponseClaim.ResponseClaim;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListVoucher extends AppCompatActivity implements AdapterListVoucher.AdapterListBarangCallback {

    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass, TAG = "";

    //getDataUser
    String getNamaMember, getStatusMember, getImageURL;
    int getPoinMember;

    //Data User
    TextView namaMember, poinMember;
    CircleImageView imageMember;
    RelativeLayout badgeMember;

    Toolbar toolbar;

    //Data Voucher
    private GridLayoutManager layoutManager;
    AdapterListVoucher adapterListSearchVoucher;
    RecyclerView rvVoucher;
    List<com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer> listVoucher = new ArrayList<>();
    ArrayList<com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer> arrayVoucher = new ArrayList<com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voucher);

        sessionManager = new SessionManager(this);
        findID();

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        namaMember.setFocusable(true);
        rvVoucher.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        rvVoucher.setLayoutManager(layoutManager);

        getDataUser();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        getNamaMember = "";
        getStatusMember = "";
        getPoinMember = 0;
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.length() > 1) {
                                Gson gson = new Gson();
                                ModelUser modelMember = gson.fromJson(response, ModelUser.class);
                                MsgServer dataMember = modelMember.getMsgServer().get(0);
                                getNamaMember = dataMember.getName();
                                getStatusMember = dataMember.getStatusMember();
                                getPoinMember = (int) Math.floor(dataMember.getPoin());
                                getImageURL = dataMember.getImageCustomer();
                                Log.e(TAG, "onResponse Member\nNama : " + getNamaMember + "\nStatus : " + getStatusMember  + "\nPoin : " + getPoinMember);
                                placeDataUser();
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
                    Toast.makeText(ListVoucher.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ListVoucher.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getDataUser();
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

        arrReq.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(arrReq);
    }

    private void placeDataUser() {
        namaMember.setText(getNamaMember);
        poinMember.setText(String.valueOf(getPoinMember));

        Drawable imageX;
        if (sessionManager.getImage() != "" && sessionManager.getImage() != null) {
            Glide.with(getApplicationContext()).asBitmap().load(sessionManager.getImage()).centerCrop().into(imageMember);
        } else {
            imageX = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
            imageMember.setImageDrawable(imageX);
        }

        Drawable image;
        String cekStatus = sessionManager.getMembership();

        if (cekStatus.equals("DEBET")) {
            image = getResources().getDrawable(R.drawable.card_member_gold);
            badgeMember.setBackground(image);
        } else {
            image = getResources().getDrawable(R.drawable.member_premium);
            badgeMember.setBackground(image);
        }

        getdataVoucher();
    }

    private void getdataVoucher() {
        url = Http.server;
        url = url + "list-voucher";
        Log.e(TAG, "getdataVoucher: " + url );
        final ProgressDialog dialog1 = new ProgressDialog(ListVoucher.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog1.dismiss();
                        try {
                            if (response.length() > 1) {
                                Gson gson = new Gson();
                                ModelSearchVoucher modelMember = gson.fromJson(response, ModelSearchVoucher.class);
                                listVoucher = modelMember.getMsgServer();

                                Log.e(TAG, "SIZE 1 : " + listVoucher.size() );

                                String statusMember = sessionManager.getMembership();

                                for (int i = listVoucher.size() - 1; i >= 0; i--) {
                                    if (statusMember.equals("REGULER")) {
                                        Log.e(TAG, "Status Member : " + statusMember );
                                        if (listVoucher.get(i).getTipeMember().equals("DEBET")) {
                                            listVoucher.remove(i);
                                        }
                                    } else {
                                        Log.e(TAG, "Status Member : " + statusMember );
                                    }
                                }

                                Log.e(TAG, "SIZE 2 : " + listVoucher.size() );

                                if (listVoucher.size() > 0) {
                                    arrayVoucher.clear();
                                    rvVoucher.setAdapter(null);
                                    for (com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer itemData : listVoucher) {
                                        com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer pocer = new com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer();
                                        pocer.setName(itemData.getName());
                                        pocer.setCode(itemData.getCode());
                                        pocer.setDeskripsi(itemData.getDeskripsi());
                                        pocer.setExpired(itemData.getExpired());
                                        pocer.setKlaim(itemData.getKlaim());
                                        pocer.setNominal(itemData.getNominal());
                                        pocer.setStok(itemData.getStok());
                                        pocer.setTipe(itemData.getTipe());
                                        pocer.setTipeMember(itemData.getTipeMember());
                                        arrayVoucher.add(pocer);
                                    }
                                    adapterListSearchVoucher = new AdapterListVoucher(ListVoucher.this, arrayVoucher, ListVoucher.this);
                                    rvVoucher.setAdapter(null);
                                    rvVoucher.setAdapter(adapterListSearchVoucher);

                                } else {
                                    Snack("Voucher Kosong");
                                }
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
                    Toast.makeText(ListVoucher.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ListVoucher.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getDataUser();
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

        arrReq.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(arrReq);
    }

    private void findID() {
        namaMember = findViewById(R.id.text_namaMembership);
        poinMember = findViewById(R.id.text_poinMembership);
        imageMember = findViewById(R.id.profile_image);
        badgeMember = findViewById(R.id.drawMember);
        rvVoucher = findViewById(R.id.rv_voucher);
        toolbar = findViewById(R.id.toolbar);
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(rvVoucher, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    @Override
    public void AdapterListBarangClicked(com.dbelgamembership.membersip.Model.ModelSearchVoucher.MsgServer position) {
        String idMember = sessionManager.getPID();
        String kodeVoucher = position.getCode();
        int poinMember = getPoinMember;
        int biayaKlaim = position.getKlaim();
        if (poinMember >= biayaKlaim) {
             klaimVoucher(idMember, kodeVoucher);
        } else {
            Snack("Poin belanja kurang untuk klaim voucher !");
        }

    }

    private void klaimVoucher(String idMember, String kodeVoucher) {
        url = Http.server + "claim-voucher/" + idMember;
        Log.e(TAG, "URL : " + url);
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(ListVoucher.this);
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Klaim voucher ini ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (isOnline()) {
                            JSONObject postData = new JSONObject();
                            try {
                                postData.put("id_member", idMember);
                                postData.put("code_voucher", kodeVoucher);
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                });
        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final android.app.AlertDialog alert11 = builder1.create();
        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert11.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert11.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        alert11.show();
    }

    private void SimpanPost(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(ListVoucher.this);
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
                            Log.e(TAG, "Response : " + response);
                            Gson gson = new Gson();
                            ResponseClaim responseWishlist = gson.fromJson(String.valueOf(response), ResponseClaim.class);
                            Toast.makeText(ListVoucher.this, responseWishlist.getDescription(), Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
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
                    Intent intent = new Intent(getApplicationContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
}