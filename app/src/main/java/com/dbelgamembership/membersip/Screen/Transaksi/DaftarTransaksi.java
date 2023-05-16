package com.dbelgamembership.membersip.Screen.Transaksi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterListTransaksi;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Model.modelListTransaksi.Datum;
import com.dbelgamembership.membersip.Model.modelListTransaksi.ModelListTransaksi;
import com.dbelgamembership.membersip.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaftarTransaksi extends AppCompatActivity implements AdapterListTransaksi.AdapterListTransactionCallback {

    SessionManager sessionManager;

    public String url = Http.server, jsonResult, type, user, pass;
    private String TAG = "";
    private AdapterListTransaksi adapterListTransaksi;
    private LinearLayoutManager layoutManager;

    Toolbar toolbar;

    //TestData
    String idUser;
    EditText txt_CariTransaksi;
    RecyclerView rvTransaksi;
    private List<Datum> itemlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_transaksi);
        sessionManager = new SessionManager(this);
        findID();

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        rvTransaksi.setLayoutManager(layoutManager);
        rvTransaksi.setHasFixedSize(false);

        getDataUser();
    }

    private void getDataUser() {
        idUser = sessionManager.getPID();
        Log.e(TAG, "ID USER SEARCH : " + idUser );
        url = url + "transaction/list/";
        getDataTransaksi();
    }

    private void getDataTransaksi() {
        Log.e(TAG, "URL : " + url );
        final ProgressDialog dialog1 = new ProgressDialog(DaftarTransaksi.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, "onResponse: "+response);
                        rvTransaksi.setVisibility(View.VISIBLE);
                        try {
                            itemlist.clear();
                            if (response != null) {
                                Gson gson = new Gson();
                                ModelListTransaksi modelListTransaction = gson.fromJson(String.valueOf(response), ModelListTransaksi.class);
                                itemlist = modelListTransaction.getData().getData();
                                if (itemlist.size() > 0) {
                                    for (int i = itemlist.size() - 1; i >= 0; i--) {
                                        Log.e(TAG, i + " Nomor ID User : " + itemlist.get(i).getIdentitasCustomer());
                                        if (!itemlist.get(i).getIdentitasCustomer().equals(idUser)) {
                                            itemlist.remove(i);
                                        }
                                    }

                                    Collections.sort(itemlist, new Comparator<Datum>() {
                                        @Override
                                        public int compare(Datum datum, Datum t1) {
                                            return t1.getCreatedAt().compareToIgnoreCase(datum.getCreatedAt());
                                        }

                                    });

                                    Log.e(TAG, "Hasil " + itemlist.toString() );

                                    adapterListTransaksi = new AdapterListTransaksi(DaftarTransaksi.this, -1, itemlist, DaftarTransaksi.this);
                                    rvTransaksi.setAdapter(adapterListTransaksi);
                                } else {
                                    Snack("Data Tidak Ditemukan 1");
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Snack("Data Tidak Ditemukan 2");
                            rvTransaksi.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage(), error);
//                swipe_search.setRefreshing(false);
                rvTransaksi.setVisibility(View.GONE);
//                dialog.dismiss();
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(DaftarTransaksi.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DaftarTransaksi.this);
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
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(jsonObjectRequest);
        dialog1.dismiss();
    }

    private void findID() {
        txt_CariTransaksi = findViewById(R.id.edt_cariTransaksi);
        rvTransaksi = findViewById(R.id.rv_Transaksi);
        txt_CariTransaksi.setFocusable(false);
        toolbar = findViewById(R.id.toolbar);
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(rvTransaksi, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorDark));
        snackbar.show();
    }

    @Override
    public void onRowAdapterListTransactionClicked(Datum position) {
        Intent intent = new Intent(DaftarTransaksi.this, PrintActivity.class);
        String DataOOS = position.getCode();
        Log.e(TAG, "onRowAdapterListTransactionClicked: "+DataOOS );
        intent.putExtra("DATAPRINT", DataOOS);
        startActivity(intent);
    }
}