package com.dbelgamembership.membersip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.dbelgamembership.membersip.Adapter.AdapterListBarang;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;

import com.dbelgamembership.membersip.Model.ModelKatalog;

import com.dbelgamembership.membersip.Model.ModelListWishlist.Datum;
import com.dbelgamembership.membersip.Model.ModelListWishlist.ModelListWishlist;
import com.dbelgamembership.membersip.Model.modelBarang.ModelBarang;
import com.dbelgamembership.membersip.Model.modelBarang.MsgServer;
import com.dbelgamembership.membersip.Model.modelBarang.Price;
import com.dbelgamembership.membersip.Model.modelFilter.ModelFilter;
import com.dbelgamembership.membersip.app.AppController;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class KatalogActivity extends AppCompatActivity implements AdapterListBarang.AdapterListBarangCallback {

    SessionManager sessionManager;

    //popupkostumer
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private TextView kategoriProduk, namaProduk, deskripsiProduk, stokProduk, harga1Produk, harga2Produk;
    private ImageView closeButton, gambarProduk;
    private LinearLayout btnTambah;
    private int totalWish;

    public String url = Http.server, jsonResult, type, user, pass;
    private String TAG = "";
    String cariBarang;
    LinearLayout mainLayout, btnSortFilter, layoutSpinner, layoutContentFilter, btnHapusFilter;
    TextView judulAppBar, totalWishlist;
    EditText textCariBarang;
    ImageView btnCari;
    RecyclerView rvBarang;
    SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager layoutManager;
    int checker = 0;
    Spinner spinnerSort, spinnerFilter, spinnerContent;
    String sortData, filterData;
    Boolean filter;
    RelativeLayout layoutWishList, layoutTotalWishlist;

    AdapterListBarang adapterListSearchBarang;
    ArrayList<ModelKatalog> arrayBarang = new ArrayList<ModelKatalog>();
    List<String> arrayKategori = new ArrayList<String>();
    public static String[] stockArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog);
        sessionManager = new SessionManager(this);
        findID();
        filter = false;
        filterData = "";
        sortData = "";
        if (textCariBarang.getText().toString() == null | textCariBarang.getText().toString().equals("")) {
            cariBarang = "";
        }

        rvBarang.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        rvBarang.setLayoutManager(layoutManager);

        SearchingBarang(cariBarang);

        textCariBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000; // milliseconds

            @Override
            public void afterTextChanged(Editable editable) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cariBarang = textCariBarang.getText().toString();
                                        if (isOnline()) {
//                                            swipeBarang.setRefreshing(true);
                                            SearchingBarang(cariBarang);
                                        } else {
                                            Snack("Tidak ada koneksi internet");
                                        }
                                    }
                                });
                            }
                        },
                        DELAY
                );

            }
        });


        btnSortFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker++;
                if ((checker % 2) == 0) {
                    // number is even
                    layoutSpinner.setVisibility(View.GONE);
                } else {
                    // number is odd
                    layoutSpinner.setVisibility(View.VISIBLE);
                }
            }
        });

        btnHapusFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutContentFilter.setVisibility(View.GONE);
                filter = false;
                spinnerFilter.setSelection(0);
                spinnerSort.setSelection(0);
                textCariBarang.setText("");
                SearchingBarang("");
            }
        });

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int iCurrentSelection = spinnerSort.getSelectedItemPosition();
                switch (iCurrentSelection) {
                    case 1:
                        sortData = "priceDown";
                        sortBarang();
                        break;
                    case 2:
                        sortData = "priceUp";
                        sortBarang();
                        break;
                    case 3:
                        sortData = "stokUp";
                        sortBarang();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int iCurrentSelection = spinnerFilter.getSelectedItemPosition();
                switch (iCurrentSelection) {
                    case 1:
                        layoutContentFilter.setVisibility(View.VISIBLE);
                        textCariBarang.setFocusable(false);
                        textCariBarang.setText("");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stockArr);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerContent.setAdapter(adapter);
                        break;
                    case 2:
//                        Log.e(TAG, "onItemSelected: " + spinnerFilter.getSelectedItemPosition());
//                        Toast.makeText(KatalogActivity.this, spinnerFilter.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        layoutContentFilter.setVisibility(View.VISIBLE);
                        textCariBarang.setFocusable(false);
                        textCariBarang.setText("");
                        ArrayAdapter<CharSequence> adapterX = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.filter_Harga, android.R.layout.simple_spinner_item);
                        spinnerContent.setAdapter(adapterX);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterData = spinnerContent.getSelectedItem().toString();
                filter = true;
                SearchingBarang("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        layoutWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KatalogActivity.this, WishlishActivity.class);
                startActivity(intent);
            }
        });

    }


    private void findID() {
        mainLayout = findViewById(R.id.mainLayout);
        judulAppBar = findViewById(R.id.judulAppbar);
        textCariBarang = findViewById(R.id.cariBarang);
        btnCari = findViewById(R.id.imageBtn);
        rvBarang = findViewById(R.id.gridview);
        swipeRefreshLayout = findViewById(R.id.swipeBarangOrder);
        btnSortFilter = findViewById(R.id.layoutFilter);
        layoutSpinner = findViewById(R.id.spinner);
        judulAppBar.setText("Katalog Produk");
        spinnerSort = findViewById(R.id.sortSpinner);
        spinnerFilter = findViewById(R.id.filterSpinner);
        spinnerContent = findViewById(R.id.filterContent);
        layoutContentFilter = findViewById(R.id.layoutContentFilter);
        btnHapusFilter = findViewById(R.id.btnDeleteFilter);
        totalWishlist = findViewById(R.id.text_totalWishlist);
        layoutWishList = findViewById(R.id.layout_wishlist);
        layoutTotalWishlist = findViewById(R.id.layout_totalWish);

        getDataWishlistUser();
    }

    private void getDataWishlistUser() {
        url = url + "wishlist/daftar?id=" + sessionManager.getPID();
        getWishlist();
    }

    private void getWishlist() {
        totalWish = 0;
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.length() > 1) {
                                Gson gson = new Gson();
                                swipeRefreshLayout.setRefreshing(false);
                                ModelListWishlist modelListItem = gson.fromJson(response, ModelListWishlist.class);
                                List<Datum> modelItem = modelListItem.getData();
                                totalWish = modelItem.size();

                                if (totalWish > 0) {
                                    layoutTotalWishlist.setVisibility(View.VISIBLE);
                                    totalWishlist.setText(totalWish);
                                } else {
                                    layoutTotalWishlist.setVisibility(View.GONE);
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
                    Toast.makeText(KatalogActivity.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(KatalogActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getDataKatalogAwal();
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

    private void SearchingBarang(String cari) {
        Log.e(TAG, "filter : " + filter);
        Log.e(TAG, "SearchingBarang: " + cariBarang);
        Log.e(TAG, "SearchingBarang: " + cariBarang.length());
        url = Http.server;
        if (filter != true) {
            if (cariBarang.length() > 0) {
                try {
                    url = url + "search-katalog?name=" + URLEncoder.encode(cari.trim(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                url = url + "search-katalog?name=";
            }
            getDataKatalogAwal();
        } else {
            if (!filterData.equals("")) {
                url = url + "search-katalog?kategori=" + filterData;
            }
            filterDataKatalog();
        }

        Log.e("url", url);
    }


    private void getDataKatalogAwal() {
        final ProgressDialog dialog1 = new ProgressDialog(KatalogActivity.this);
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
                                swipeRefreshLayout.setRefreshing(false);
                                ModelBarang modelListItem = gson.fromJson(response, ModelBarang.class);
                                List<MsgServer> modelItem = modelListItem.getMsgServer();
                                if (modelItem.size() > 0) {
                                    arrayBarang.clear();
                                    rvBarang.setAdapter(null);
                                    for (MsgServer itemData : modelItem) {
                                        ModelKatalog pm = new ModelKatalog();
                                        pm.setId(String.valueOf(itemData.getId()));
                                        pm.setNama_barang(itemData.getName());
                                        pm.setDeskripsi(itemData.getDeskripsi());
                                        pm.setMerk_barang(String.valueOf(itemData.getMerek()));
                                        pm.setKategori_barang(itemData.getNamaKategori());
                                        pm.setKode_barang(itemData.getCode());
                                        pm.setStok(String.valueOf(itemData.getStokMinimal()));
                                        pm.setBarcode(itemData.getCode());
                                        pm.setImages(itemData.getImages());
                                        Price hargaBarang = itemData.getPrice();
                                        pm.setHarga_barang(String.valueOf(hargaBarang.getHarga()));
                                        arrayKategori.add(itemData.getNamaKategori());
                                        arrayBarang.add(pm);
                                    }

                                    stockArr = new ArrayList<String>(new LinkedHashSet<String>(arrayKategori)).toArray(new String[0]);

                                    adapterListSearchBarang = new AdapterListBarang(KatalogActivity.this, arrayBarang, KatalogActivity.this);
                                    rvBarang.setAdapter(null);
                                    rvBarang.setAdapter(adapterListSearchBarang);

                                } else {
                                    Snack("Item Kosong");
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
                    Toast.makeText(KatalogActivity.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(KatalogActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getDataKatalogAwal();
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

    private void filterDataKatalog() {
        final ProgressDialog dialog1 = new ProgressDialog(KatalogActivity.this);
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
                                swipeRefreshLayout.setRefreshing(false);
                                ModelFilter modelListItem = gson.fromJson(response, ModelFilter.class);
                                List<com.dbelgamembership.membersip.Model.modelFilter.MsgServer> modelItem = modelListItem.getMsgServer();
                                if (modelItem.size() > 0) {
                                    arrayBarang.clear();
                                    rvBarang.setAdapter(null);
                                    for (com.dbelgamembership.membersip.Model.modelFilter.MsgServer itemData : modelItem) {
                                        ModelKatalog pm = new ModelKatalog();
                                        pm.setId(String.valueOf(itemData.getId()));
                                        pm.setNama_barang(itemData.getName());
                                        pm.setMerk_barang(String.valueOf(itemData.getMerek()));
                                        pm.setKategori_barang(itemData.getKategoriProduk());
                                        pm.setKode_barang(itemData.getCode());
                                        pm.setStok(String.valueOf(itemData.getStokMinimal()));
                                        pm.setBarcode(itemData.getCode());
                                        pm.setImages(itemData.getImages());
                                        com.dbelgamembership.membersip.Model.modelFilter.Price hargaBarang = itemData.getPrice();
                                        pm.setHarga_barang(String.valueOf(hargaBarang.getHarga()));
//                                        arrayKategori.add(itemData.getNamaKategori());
                                        arrayBarang.add(pm);
                                    }
                                    adapterListSearchBarang = new AdapterListBarang(KatalogActivity.this, arrayBarang, KatalogActivity.this);
                                    rvBarang.setAdapter(null);
                                    rvBarang.setAdapter(adapterListSearchBarang);

                                } else {
                                    Snack("Item Kosong");
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
                    Toast.makeText(KatalogActivity.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(KatalogActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getDataKatalogAwal();
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

    private void sortBarang() {

        if (sortData.equals("priceDown")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = Integer.parseInt(modelKatalog.getHarga_barang());
                    int b = Integer.parseInt(t1.getHarga_barang());
                    return a - b;
                }
            });
            Snack("Barang diurutkan harga terendah !");
        } else if (sortData.equals("priceUp")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = Integer.parseInt(modelKatalog.getHarga_barang());
                    int b = Integer.parseInt(t1.getHarga_barang());
                    return b - a;
                }
            });
            Snack("Barang diurutkan harga tertinggi !");
        } else if (sortData.equals("stokUp")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = Integer.parseInt(modelKatalog.getStok());
                    int b = Integer.parseInt(t1.getStok());
                    return b - a;
                }
            });
            Snack("Barang diurutkan stok tertinggi !");
        }

        adapterListSearchBarang = new AdapterListBarang(KatalogActivity.this, arrayBarang, KatalogActivity.this);
        rvBarang.setAdapter(null);
        rvBarang.setAdapter(adapterListSearchBarang);
//        dialog1.dismiss();

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
        Snackbar snackbar = Snackbar.make(mainLayout, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }


    @Override
    public void AdapterListBarangClicked(ModelKatalog position) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        Log.e(TAG, "AdapterListBarangClicked: " + position.getKode_barang());
        dialogBuilder = new AlertDialog.Builder(this);
        final View kostumerPopUp = getLayoutInflater().inflate(R.layout.popup_barang, null);
        dialogBuilder.setView(kostumerPopUp);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        String StatusMber = "";

        StatusMber = sessionManager.getMembership();
        Log.e(TAG, "STATUS MBER: " + StatusMber);


        kategoriProduk = (TextView) kostumerPopUp.findViewById(R.id.produk_kategori);
        namaProduk = (TextView) kostumerPopUp.findViewById(R.id.produk_name);
        deskripsiProduk = (TextView) kostumerPopUp.findViewById(R.id.produk_deskripsi);
        harga1Produk = (TextView) kostumerPopUp.findViewById(R.id.produk_price1);
        harga2Produk = (TextView) kostumerPopUp.findViewById(R.id.produk_price2);
        stokProduk = (TextView) kostumerPopUp.findViewById(R.id.produk_stok);
        gambarProduk = (ImageView) kostumerPopUp.findViewById(R.id.produk_image);
        closeButton = (ImageView) kostumerPopUp.findViewById(R.id.produk_close);
        btnTambah = (LinearLayout) kostumerPopUp.findViewById(R.id.layout_button);

        kategoriProduk.setText(position.getKategori_barang());
        namaProduk.setText(position.getNama_barang());

        Log.e(TAG, "AdapterListBarangClicked: " + position.getDeskripsi());

        if (position.getDeskripsi() == null) {
            deskripsiProduk.setText("Produk tidak ada deskripsi");
        } else {
            deskripsiProduk.setText(position.getDeskripsi());
        }

        if (StatusMber.equals("DEBET")) {
            harga1Produk.setVisibility(View.VISIBLE);
            harga2Produk.setVisibility(View.VISIBLE);
        } else {
            harga1Produk.setVisibility(View.GONE);
        }

        harga1Produk.setText("Rp. " + nf.format(Long.parseLong(position.getHarga_barang())));
        harga2Produk.setText("Rp. " + nf.format(Long.parseLong(position.getHarga_barang())));

        int cekStok = Integer.parseInt(position.getStok());

        if (cekStok > 0 && cekStok < 10) {
            stokProduk.setText(" < 10 Stok");
        } else if (cekStok >= 10 && cekStok < 25) {
            stokProduk.setText(" < 25 Stok");
        } else if (cekStok >= 25 && cekStok < 50) {
            stokProduk.setText(" < 50 Stok");
        } else if (cekStok > 50) {
            stokProduk.setText(" > 50 Stok");
        } else {
            stokProduk.setText("KOSONG");
        }

        Drawable image;
        if (!position.getImages().equals("http://54.254.194.122/upload/barang/")) {
            Glide.with(this)
                    .asBitmap()
                    .load(position.getImages())
                    .into(gambarProduk);
        } else {
            image = this.getResources().getDrawable(R.drawable.not_found);
            gambarProduk.setImageDrawable(image);
        }

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}