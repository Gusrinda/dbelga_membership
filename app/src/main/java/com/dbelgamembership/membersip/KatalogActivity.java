package com.dbelgamembership.membersip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.dbelgamembership.membersip.Adapter.AdapterListBarang;
import com.dbelgamembership.membersip.Fragment.bottomSheet.BottomSheetFilterFragment;
import com.dbelgamembership.membersip.Fragment.bottomSheet.BottomSheetFilterFragmentUrutkan;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;

import com.dbelgamembership.membersip.Model.ModelKatalog;

import com.dbelgamembership.membersip.Model.ModelSearchWish.ModelSearchWish;
import com.dbelgamembership.membersip.Model.ResponseWishlist.ResponseWishlist;
import com.dbelgamembership.membersip.Model.modelBarang.Datum;
import com.dbelgamembership.membersip.Model.modelBarang.ModelBarang;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.dbelgamembership.membersip.Fragment.bottomSheet.BottomSheetFilterFragment.filter;


public class KatalogActivity extends AppCompatActivity implements AdapterListBarang.AdapterListBarangCallback {

    SessionManager sessionManager;

    //popupkostumer
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private TextView kategoriProduk, namaProduk, deskripsiProduk, stokProduk, harga1Produk, harga2Produk, textTotalWishlist, textAdaBarang;
    private Button btnTambahQTY, btnKurangQTY;
    private EditText jumlahQTY;
    private ImageView closeButton, gambarProduk;
    private LinearLayout btnTambah;
    private int totalWish;
    private int IdKostumer;
    private RelativeLayout getLayoutTotalWishlist;
    public static int jumlahWishlistAwal = 0;

    Toolbar toolbar;

    public String url = Http.server, jsonResult, type, user, pass;
    private String TAG = "";
    String cariBarang;
    LinearLayout mainLayout, btnSortFilter, btnUrutkanData;
    TextView judulAppBar, totalWishlist;
    EditText textCariBarang;
    ImageView btnCari;
    RecyclerView rvBarang;

    //PAGENATION
    SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager layoutManager;
    private int pastVisisbleItems, visibleItemsCount, totalItemsCount, previous_totals = 0;
    private Boolean isLoading = true;
    private int view_threshold = 9;
    private int page_number = 1;
    private String urlNextPage = "";
    int page = 0;
    int total;
    int allData = 0;
    int current_index = 0;
    //PAGENATION

    int checker = 0;
    Spinner spinnerSort, spinnerFilter, spinnerContent, spinnerHarga;
    String sortData, filterData, filterHarga;
    Boolean filter;
    RelativeLayout layoutWishList, layoutTotalWishlist;

    AdapterListBarang adapterListSearchBarang;
    ArrayList<ModelKatalog> arrayBarang = new ArrayList<ModelKatalog>();
    List<String> arrayKategori = new ArrayList<String>();
    List<com.dbelgamembership.membersip.Model.ModelSearchWish.MsgServer> listDetail = new ArrayList<>();
    public static String[] stockArr;
    private List<com.dbelgamembership.membersip.Model.ModelSearchWish.MsgServer> listBarang = new ArrayList<>();

    public static String filterString = "";
    private static boolean isFilter = false;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e(TAG, "onPostResume: " + jumlahWishlistAwal);

        String jumlahNih = "";
        if (jumlahWishlistAwal == 0) {
            jumlahNih = "";
        } else {
            jumlahNih = String.valueOf(jumlahWishlistAwal);
        }
        totalWishlist.setText(jumlahNih);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog);
        sessionManager = new SessionManager(this);
        findID();

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        filter = false;
        filterData = "";
        sortData = "";
        if (textCariBarang.getText().toString() == null | textCariBarang.getText().toString().equals("")) {
            cariBarang = "";
        }

        rvBarang.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        rvBarang.setLayoutManager(layoutManager);

        getDataUser();
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

                if (!isFilter) {
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            isFilter = false;
                                            pastVisisbleItems = 0;
                                            visibleItemsCount = 0;
                                            totalItemsCount = 0;
                                            previous_totals = 0;
                                            page_number = 1;
                                            page = 0;
                                            urlNextPage = "";
                                            arrayBarang.clear();
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

            }


        });

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFilter = false;
                pastVisisbleItems = 0;
                visibleItemsCount = 0;
                totalItemsCount = 0;
                previous_totals = 0;
                page_number = 1;
                page = 0;
                urlNextPage = "";
                arrayBarang.clear();
                cariBarang = textCariBarang.getText().toString();
                if (isOnline()) {
                    SearchingBarang(cariBarang);
                } else {
                    Snack("Tidak ada koneksi internet");
                }
            }
        });

        btnSortFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFilterFragment bottomSheetFragment = new BottomSheetFilterFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        btnUrutkanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFilterFragmentUrutkan bottomSheetFragment = new BottomSheetFilterFragmentUrutkan();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        layoutWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KatalogActivity.this, WishlishActivity.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String cari = textCariBarang.getText().toString();
                if (isOnline()) {
//                    ceksearch = true;
                    isFilter = false;
                    pastVisisbleItems = 0;
                    visibleItemsCount = 0;
                    totalItemsCount = 0;
                    previous_totals = 0;
                    page_number = 1;
                    page = 0;
                    urlNextPage = "";
                    arrayBarang.clear();
                    SearchingBarang(cari);
                } else {
                    Snack("Tidak ada koneksi internet");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        rvBarang.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemsCount = layoutManager.getChildCount();
                totalItemsCount = layoutManager.getItemCount();
                pastVisisbleItems = layoutManager.findFirstVisibleItemPosition();

                if (isLoading) {
                    if (totalItemsCount > previous_totals) {
                        isLoading = false;
                        previous_totals = totalItemsCount;
                    }
                }
                if (!isLoading && (totalItemsCount - visibleItemsCount)
                        <= (pastVisisbleItems + view_threshold)) {
                    // End has been reached

                    Log.i("Yaeye!", "end called");

                    page_number++;
                    Log.e(TAG, "onScrolled: page terakhir " + page);
                    Log.e(TAG, "onScrolled: urlNext " + urlNextPage);
                    Log.e(TAG, "onScrolled: page dituju " + page_number);
                    if (page_number >= page && !urlNextPage.equals("null")) {
                        pagenation();
                    } else {
                        Snack("Semua Barang Sudah Tampil");
                    }
                    isLoading = true;
                }
            }
        });

    }

    private void getDataUser() {
        IdKostumer = Integer.parseInt(sessionManager.getPID());
        Log.e(TAG, "getDataUser: " + IdKostumer);
        SearchingWishlist();
    }

    private void SearchingWishlist() {
        String pid = sessionManager.getPID();
        url = Http.server + "wishlist-search?customer=" + pid;
        Log.e("url", url);
        getDataWishlist();
    }

    private void getDataWishlist() {
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

                                JsonObject root = new JsonParser().parse(response).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                if (success) {
                                    Gson gson = new Gson();
                                    ModelSearchWish modelListItem = gson.fromJson(response, ModelSearchWish.class);

                                    listDetail = modelListItem.getMsgServer();

                                    String jumlahWish = "";
                                    if (listDetail.size() > 0) {
                                        jumlahWish = String.valueOf(listDetail.size());
                                        layoutTotalWishlist.setVisibility(View.VISIBLE);

//                                    Toast.makeText(KatalogActivity.this, "Wishlist user : " + namaKustomer + "\n" + modelItem.size() + " Wishlist item", Toast.LENGTH_SHORT).show();
                                    } else {
                                        layoutTotalWishlist.setVisibility(View.GONE);
                                    }
                                    jumlahWishlistAwal = Integer.parseInt(jumlahWish);
                                    Log.e(TAG, "on GET WISH : JumlahWishlist : " + jumlahWishlistAwal);
                                    totalWishlist.setText(jumlahWish);
                                } else {
                                    totalWishlist.setText("0");
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
                                    getDataWishlist();
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
                } else if (error.networkResponse == null) {
                    dialog1.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(KatalogActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Server not responding!\nTry again ?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    startActivity(getIntent());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(KatalogActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void findID() {
        mainLayout = findViewById(R.id.mainLayout);
        judulAppBar = findViewById(R.id.judulAppbar);
        textCariBarang = findViewById(R.id.cariBarang);
        btnCari = findViewById(R.id.imageBtn);
        rvBarang = findViewById(R.id.gridview);
        swipeRefreshLayout = findViewById(R.id.swipeBarangOrder);
        btnSortFilter = findViewById(R.id.layoutFilter);
        btnUrutkanData = findViewById(R.id.layoutSort);

        judulAppBar.setText("Katalog Produk");

        totalWishlist = findViewById(R.id.text_totalWishlist);
        layoutWishList = findViewById(R.id.layout_wishlist);
        toolbar = findViewById(R.id.toolbar);
        layoutTotalWishlist = findViewById(R.id.layout_totalWish);
    }

    private void SearchingBarang(String cari) {
        page = 1;
        current_index = 0;
        url = Http.server;
        swipeRefreshLayout.setRefreshing(false);


        if (isFilter) {

            try {
                url = url + "search-katalog?name=" + URLEncoder.encode(cari.trim(), "UTF-8") + filterString;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {

            try {
                url = url + "search-katalog?name=" + URLEncoder.encode(cari.trim(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        getDataKatalogAwal();

        Log.e("url", url);
    }

    private void pagenation() {
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, urlNextPage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<ModelKatalog> arrayBarangAdd = new ArrayList<>();
                    Gson gson = new Gson();
                    ModelBarang modelListItem = gson.fromJson(response, ModelBarang.class);
                    List<Datum> modelItem = modelListItem.getMsgServer().getData();
                    if (modelListItem.getMsgServer().getCurrentPage() <= modelListItem.getMsgServer().getLastPage()) {
//                        urlNextPage = String.valueOf(modelListItem.getMsgServer().getNextPageUrl());
                        if (modelListItem.getMsgServer().getNextPageUrl() != null) {
                            if (isFilter) {
                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&name=" + URLEncoder.encode(textCariBarang.getText().toString().trim(), "UTF-8")
                                        + filterString;
                            } else {
                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&name=" + URLEncoder.encode(textCariBarang.getText().toString().trim(), "UTF-8");

                            }

                        } else {
                            urlNextPage = String.valueOf(modelListItem.getMsgServer().getNextPageUrl());
                        }
                        page = modelListItem.getMsgServer().getCurrentPage();
                        Log.e(TAG, "onResponse: " + urlNextPage);
                    }
                    if (modelItem.size() > 0) {
                        arrayBarangAdd.clear();
                        for (Datum itemData : modelItem) {
                            ModelKatalog pm = new ModelKatalog();
                            pm.setId(String.valueOf(itemData.getId()));
                            pm.setNama_barang(itemData.getName());

                            String deskripsi = "";
                            if (itemData.getDeskripsi() == null || itemData.getDeskripsi().isEmpty()) {
                                deskripsi = "Deskripsi Kosong";
                            } else {
                                deskripsi = itemData.getDeskripsi();
                            }
                            pm.setDeskripsi(deskripsi);
                            pm.setMerk_barang(String.valueOf(itemData.getMerekProduk()));
                            pm.setKategori_barang(itemData.getNamaKategori());
                            pm.setKode_barang(itemData.getCode());
                            pm.setStok(String.valueOf(itemData.getStok()));

                            String satuan = "";
                            if (itemData.getSatuanKemasan() != null) {
                                satuan = itemData.getSatuanKemasan();
                            } else {
                                satuan = "unit";
                            }
                            pm.setSatuan_kemasan(satuan);
                            pm.setBarcode(itemData.getCode());
                            pm.setImages(itemData.getImages());
                            pm.setHarga_barang(itemData.getPrice());
                            pm.setHarga_2(itemData.getPriceDua());
                            pm.setHarga_3(itemData.getPriceTiga());
                            arrayBarang.add(pm);
                        }
                        adapterListSearchBarang.addItems(arrayBarangAdd);
                    }
                } catch (Exception e) {
                    Snack("Barang Sudah Tampil Semua");
                    Log.e(TAG, "onResponse: Exception pagenation " + e.getMessage());
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
                                    pagenation();
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

    private void getDataKatalogAwal() {
        Log.e(TAG, "getDataKatalogAwal: ISFILTER : " + isFilter);
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
                        Log.e(TAG, "onResponse: " + response);
                        dialog1.dismiss();
                        try {
                            if (response != null) {
                                Gson gson = new Gson();
                                swipeRefreshLayout.setRefreshing(false);
                                arrayKategori.clear();
//                                arrayKategori.add("FILTER KATEGORI");
                                JsonObject root = new JsonParser().parse(response).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                if (!success) {
                                    Snack("Barang tidak ada !");
                                } else {

                                    ModelBarang modelListItem = gson.fromJson(response, ModelBarang.class);
                                    List<Datum> modelItem = modelListItem.getMsgServer().getData();
                                    if (modelListItem.getMsgServer().getCurrentPage() <= modelListItem.getMsgServer().getLastPage()) {
//                                        urlNextPage = String.valueOf(modelListItem.getMsgServer().getNextPageUrl() == null ? "" : modelListItem.getMsgServer().getNextPageUrl());
                                        if (modelListItem.getMsgServer().getNextPageUrl() != null) {
                                            if (isFilter) {
                                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&name=" + URLEncoder.encode(textCariBarang.getText().toString().trim(), "UTF-8")
                                                        + filterString;
                                            } else {
                                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&name=" + URLEncoder.encode(textCariBarang.getText().toString().trim(), "UTF-8");
                                            }
//                                            urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&name=" + URLEncoder.encode(textCariBarang.getText().toString().trim(), "UTF-8") + filterString;
                                        } else {
                                            urlNextPage = String.valueOf(modelListItem.getMsgServer().getNextPageUrl());
                                        }
                                        page = modelListItem.getMsgServer().getCurrentPage();
                                        Log.e(TAG, "onResponse: " + urlNextPage);
                                    }

                                    if (modelItem.size() > 0) {
                                        arrayBarang.clear();
                                        rvBarang.setAdapter(null);
                                        for (Datum itemData : modelItem) {
                                            ModelKatalog pm = new ModelKatalog();
                                            pm.setId(String.valueOf(itemData.getId()));
                                            pm.setNama_barang(itemData.getName());

                                            String deskripsi = "";
                                            if (itemData.getDeskripsi() == null || itemData.getDeskripsi().isEmpty()) {
                                                deskripsi = "Deskripsi Kosong";
                                            } else {
                                                deskripsi = itemData.getDeskripsi();
                                            }

                                            pm.setDeskripsi(deskripsi);
                                            pm.setMerk_barang(String.valueOf(itemData.getMerekProduk()));
                                            pm.setKategori_barang(itemData.getNamaKategori());
                                            pm.setKode_barang(itemData.getCode());
                                            pm.setStok(String.valueOf(itemData.getStok()));

                                            String satuan = "";
                                            if (itemData.getSatuanKemasan() != null) {
                                                satuan = itemData.getSatuanKemasan();
                                            } else {
                                                satuan = "unit";
                                            }
                                            pm.setSatuan_kemasan(satuan);

                                            pm.setBarcode(itemData.getCode());
                                            pm.setImages(itemData.getImages());
                                            pm.setHarga_barang(itemData.getPrice());
                                            pm.setHarga_2(itemData.getPriceDua());
                                            pm.setHarga_3(itemData.getPriceTiga());
//                                            arrayKategori.add(itemData.getNamaKategori());
                                            arrayBarang.add(pm);
                                        }
//                                        stockArr = new ArrayList<String>(new LinkedHashSet<String>(arrayKategori)).toArray(new String[0]);
                                        adapterListSearchBarang = new AdapterListBarang(KatalogActivity.this, arrayBarang, KatalogActivity.this);
                                        rvBarang.setAdapter(null);
                                        rvBarang.setAdapter(adapterListSearchBarang);

                                    }

                                }
                            } else {
                                Snack("Item Kosong");
                            }
//                            Snack("Barang sudah tampil semua !");
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: Error haha" + e);
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
                } else if (error.networkResponse == null) {
                    dialog1.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(KatalogActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Server not responding!\nTry again ?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    startActivity(getIntent());
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

    public void sortBarang(String urutkanData) {

        if (urutkanData.equals("priceDown")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = Integer.parseInt(modelKatalog.getHarga_barang());
                    int b = Integer.parseInt(t1.getHarga_barang());
                    return a - b;
                }
            });
            Snack("Barang diurutkan harga terendah !");
        } else if (urutkanData.equals("priceUp")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = (int) Double.parseDouble(modelKatalog.getHarga_barang());
                    int b = (int) Double.parseDouble(t1.getHarga_barang());
                    return b - a;
                }
            });
            Snack("Barang diurutkan harga tertinggi !");
        } else if (urutkanData.equals("stokUp")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = (int) Double.parseDouble(modelKatalog.getStok());
                    int b = (int) Double.parseDouble(t1.getStok());
                    return b - a;
                }
            });
            Snack("Barang diurutkan stok tertinggi !");
        } else if (urutkanData.equals("stokDown")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = Integer.parseInt(modelKatalog.getStok());
                    int b = Integer.parseInt(t1.getStok());
                    return a - b;
                }
            });
            Snack("Barang diurutkan stok terendah !");
        }

        adapterListSearchBarang = new AdapterListBarang(KatalogActivity.this, arrayBarang, KatalogActivity.this);
        rvBarang.setAdapter(null);
        rvBarang.setAdapter(adapterListSearchBarang);
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
        textAdaBarang = (TextView) kostumerPopUp.findViewById(R.id.peringatanText);
        namaProduk = (TextView) kostumerPopUp.findViewById(R.id.produk_name);
        deskripsiProduk = (TextView) kostumerPopUp.findViewById(R.id.produk_deskripsi);
        harga1Produk = (TextView) kostumerPopUp.findViewById(R.id.produk_price1);
        harga2Produk = (TextView) kostumerPopUp.findViewById(R.id.produk_price2);
        stokProduk = (TextView) kostumerPopUp.findViewById(R.id.produk_stok);
        gambarProduk = (ImageView) kostumerPopUp.findViewById(R.id.produk_image);
        closeButton = (ImageView) kostumerPopUp.findViewById(R.id.produk_close);
        btnTambah = (LinearLayout) kostumerPopUp.findViewById(R.id.layout_button);
        btnTambahQTY = (Button) kostumerPopUp.findViewById(R.id.order_btnPlusQty);
        btnKurangQTY = (Button) kostumerPopUp.findViewById(R.id.order_btnMinQty);
        jumlahQTY = (EditText) kostumerPopUp.findViewById(R.id.order_qtyOrder);
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
            harga1Produk.setBackgroundResource(R.drawable.strike_through);
        } else {
            harga2Produk.setVisibility(View.GONE);
        }

        int cekStok = Integer.parseInt(position.getStok());

        if (cekStok > 0 && cekStok < 10) {
            stokProduk.setText(" < 10 Stok");
        } else if (cekStok >= 10 && cekStok < 25) {
            stokProduk.setText(" < 25 Stok");
        } else if (cekStok >= 25 && cekStok < 50) {
            stokProduk.setText(" < 50 Stok");
        } else if (cekStok >= 50) {
            stokProduk.setText(" > 50 Stok");
        } else {
            stokProduk.setText("KOSONG");
        }

        if (stokProduk.getText().toString().equals("KOSONG")) {
            harga1Produk.setVisibility(View.GONE);
            harga2Produk.setText("? (Harga Belum Diketahui)");
        } else {
            long hargaBarang = 0;
            long hargaBarang2 = 0;
            if (StatusMber.equals("REGULER")) {
                Log.e(TAG, "harga 1");
                hargaBarang = (long) Double.parseDouble(position.getHarga_barang());
            } else if (StatusMber.equals("DEBET")) {
                Log.e(TAG, "harga 2");
                hargaBarang = (long) Double.parseDouble(position.getHarga_barang());
                hargaBarang2 = (long) Double.parseDouble(position.getHarga_2());
            }

            harga1Produk.setText("Rp. " + nf.format(hargaBarang));
            harga2Produk.setText("Rp. " + nf.format(hargaBarang2));

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

        btnTambahQTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumlahQTY.getText().toString().equals("")) {
                    jumlahQTY.setText("0");
                }
                btnKurangQTY.setClickable(true);
                btnTambah.setClickable(true);
                String qtyawal = jumlahQTY.getText().toString();
                int qty = Integer.parseInt(qtyawal);
                int plusQty = qty + 1;
                String hasil = String.valueOf(plusQty);
                jumlahQTY.setText(hasil);
            }
        });

        btnKurangQTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumlahQTY.getText().toString().equals("")) {
                    jumlahQTY.setText("0");
                }
                int stokInput = Integer.parseInt(jumlahQTY.getText().toString());
                if (stokInput == 0 || stokInput < 0) {
                    btnKurangQTY.setClickable(false);
                    btnTambahQTY.setClickable(true);
                } else {
                    btnTambahQTY.setClickable(true);
                    btnKurangQTY.setClickable(true);
                    String qtyawal = jumlahQTY.getText().toString();
                    int qty = Integer.parseInt(qtyawal);
                    int plusQty = qty - 1;
                    String hasil = String.valueOf(plusQty);
                    jumlahQTY.setText(hasil);
                }
            }

        });

        for (int i = 0; i < jumlahWishlistAwal; i++) {
            String haha = "";
            String jumlahQTQTQTY = "0";
            if (listDetail.size() == 0) {
                haha = "";
            } else {
                haha = String.valueOf(listDetail.get(i).getIdProduk());
                jumlahQTQTQTY = String.valueOf(listDetail.get(i).getQty());
            }

            String hehe = "";
            if (listBarang.size() == 0) {
                hehe = "";
            } else {
                hehe = String.valueOf(listBarang.get(i).getIdProduk());
            }

            Log.e(TAG, "AdapterListBarangClicked HAHA: " + haha);
            Log.e(TAG, "AdapterListBarangClicked HEHE: " + hehe);
            Log.e(TAG, "AdapterListBarangClicked POSITION: " + position.getId());

            if (position.getId().equals(haha) || position.getId().equals(hehe)) {
                textAdaBarang.setVisibility(View.VISIBLE);
                jumlahQTY.setText(jumlahQTQTQTY);
            }

        }

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlahStokWish = 0;
                if (jumlahQTY.getText().toString().equals("")) {
                    jumlahStokWish = 0;
                } else {
                    jumlahStokWish = Integer.parseInt(jumlahQTY.getText().toString());
                }

                if (jumlahStokWish == 0) {
                    Toast.makeText(KatalogActivity.this, "Tentukan terlebih dahulu stok yang anda inginkan !", Toast.LENGTH_SHORT).show();
                } else {
                    tambahItemWishlist(position.getId(), jumlahStokWish);
                }
            }

            private void tambahItemWishlist(String kode_barang, int stokBarang) {
                Log.e(TAG, "Size awal : " + listBarang.size());
                Log.e(TAG, "tambahItemWishlist: Stok ingin " + stokBarang);
                String code = kode_barang;
                Log.e(TAG, "ID Member : " + sessionManager.getPID());
                Log.e(TAG, "ID Barang : " + code);
                url = Http.server + "wishlist-add/" + sessionManager.getPID();
                Log.e(TAG, "URL : " + url);
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(KatalogActivity.this);
                builder1.setTitle("Konfirmasi");
                builder1.setMessage("Menambah item ke wishlist ?");
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
                                        postData.put("produk", code);
                                        postData.put("qty", stokBarang);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (isOnline()) {
                                        Log.e(TAG, "URL : " + url);
                                        Log.e(TAG, "onClickSubmit: " + postData);
                                        SimpanPost(postData);
                                        alertDialog.dismiss();
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

        });

    }

    public void dismissDialog(String filterData) {
        isFilter = true;
        filterString = filterData;
        pastVisisbleItems = 0;
        visibleItemsCount = 0;
        totalItemsCount = 0;
        previous_totals = 0;
        page_number = 1;
        page = 0;
        urlNextPage = "";
        arrayBarang.clear();
        Log.e("TAG", "dismissDialog: " + filterString);
        textCariBarang.setText("");
        SearchingBarang("");
    }

    private void SimpanPost(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(KatalogActivity.this);
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
                            ResponseWishlist responseWishlist = gson.fromJson(String.valueOf(response), ResponseWishlist.class);
                            listBarang = responseWishlist.getMsgServer();

                            boolean responseBool = responseWishlist.isSuccess();

                            if (responseWishlist.isSuccess()) {
                                Log.e(TAG, "onResponse: " + responseBool);
                                Snack("Berhasil menambahkan barang di Wishlist");
                            } else {
                                Log.e(TAG, "onResponse: " + responseBool);
                                String string = "error : " + responseWishlist.getDescription();
                                Snackbar snackbar = Snackbar.make(mainLayout, string, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null);
                                View snackBarView = snackbar.getView();
                                snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
                                snackbar.show();
                            }

                            listDetail = listBarang;
                            jumlahWishlistAwal = listBarang.size();
                            Log.e(TAG, "on Tambah : JumlahWishlist : " + jumlahWishlistAwal);
                            totalWishlist.setText(String.valueOf(listBarang.size()));

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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }


}