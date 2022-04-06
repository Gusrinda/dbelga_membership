package com.dbelgamembership.membersip.Screen.Promo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelKatalog;
import com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart;
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.modelBarang.Datum;
import com.dbelgamembership.membersip.Model.modelBarang.ModelBarang;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterListBarang;
import com.dbelgamembership.membersip.databinding.ActivityKatalogPromoBinding;
import com.dbelgamembership.membersip.databinding.PopupBarangBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KatalogPromo extends AppCompatActivity implements AdapterListBarang.AdapterListBarangCallback {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityKatalogPromoBinding binding;
    private SessionManager sessionManager;
    AdapterListBarang adapterListSearchBarang;
    ArrayList<ModelKatalog> arrayBarangPromo = new ArrayList<ModelKatalog>();

    List<DetailItemCart> itemCartList = new ArrayList<>();

    //PAGENATION
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


    com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum dataPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKatalogPromoBinding.inflate(getLayoutInflater());
        sessionManager = new SessionManager(this);

        setContentView(binding.getRoot());

        binding.gridview.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        binding.gridview.setLayoutManager(layoutManager);

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().hasExtra("hasExtra")) {
            dataPromo = getIntent().getParcelableExtra("dataPromo");
            setupKatalogPromo();
        } else {
            finish();
        }

        binding.iconKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KatalogPromo.this, CartActivity.class);
                startActivity(intent);
            }
        });

        binding.swipeBarangOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (isOnline()) {
                    pastVisisbleItems = 0;
                    visibleItemsCount = 0;
                    totalItemsCount = 0;
                    previous_totals = 0;
                    page_number = 1;
                    page = 0;
                    urlNextPage = "";
                    arrayBarangPromo.clear();
                    String cari = binding.cariBarang.getText().toString();
                    searchKatalogPromo(cari);
                } else {
                    Snack("Tidak ada koneksi internet");
                    binding.swipeBarangOrder.setRefreshing(false);
                }
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()) {
                    pastVisisbleItems = 0;
                    visibleItemsCount = 0;
                    totalItemsCount = 0;
                    previous_totals = 0;
                    page_number = 1;
                    page = 0;
                    urlNextPage = "";
                    arrayBarangPromo.clear();
                    String cari = binding.cariBarang.getText().toString();
                    searchKatalogPromo(cari);
                } else {
                    Snack("Tidak ada koneksi internet");
                    binding.swipeBarangOrder.setRefreshing(false);
                }
            }
        });

        binding.gridview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        Toast.makeText(KatalogPromo.this, "Semua barang sudah tampil !", Toast.LENGTH_SHORT).show();
                    }
                    isLoading = true;
                }
            }
        });

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
        Snackbar snackbar = Snackbar.make(binding.mainLayout, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }

    private void setupKatalogPromo() {
        binding.toolbar.setTitle(dataPromo.getKeterangan());

        Glide.with(this)
                .asBitmap()
                .load(dataPromo.getImage())
                .error(R.drawable.promo_banner_belga)
                .into(binding.gambarBanner);


        searchKatalogPromo("");
        SearchingCart();

    }

    private void SearchingCart() {
        final ProgressDialog progressDialog = ProgressDialog.show(KatalogPromo.this, "Loading", "Please Wait...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doDetailCart(sessionManager.getPID());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                progressDialog.dismiss();
                try {

                    JSONObject obj = new JSONObject(response.body());

                    boolean success = obj.getBoolean("success");
                    String msgServer = obj.get("msgServer").toString();

                    itemCartList.clear();

                    if (success) {
                        Gson gson = new Gson();
                        ModelResponseCart modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);

                        assert modelResponseCart != null;
                        binding.iconKeranjang.setBadgeValue(modelResponseCart.getMsgServer().getDetailItemCart().size());

                        itemCartList.addAll(modelResponseCart.getMsgServer().getDetailItemCart());
                    } else {
//                        Toast.makeText(KatalogActivity.this, msgServer, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + msgServer);
                        binding.iconKeranjang.setBadgeValue(0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KatalogPromo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void searchKatalogPromo(String cari) {
        final ProgressDialog dialog1 = new ProgressDialog(KatalogPromo.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doGetKatalogPromo(sessionManager.getKeySetGudangPencarian(), cari, dataPromo.getPromoCode());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                dialog1.dismiss();
                binding.swipeBarangOrder.setRefreshing(false);
                if (response != null) {
                    Gson gson = new Gson();
                    String responseX = String.valueOf(response.body());
                    JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                    boolean success = root.get("success").getAsBoolean();
                    Log.e("", "Test : " + success);
                    if (!success) {
                        Toast.makeText(KatalogPromo.this, "Error" + root.get("msgServer"), Toast.LENGTH_SHORT).show();
                    } else {
                        ModelBarang modelListItem = gson.fromJson(String.valueOf(response.body()), ModelBarang.class);
                        List<Datum> modelItem = modelListItem.getMsgServer().getData();

                        if (modelListItem.getMsgServer().getCurrentPage() <= modelListItem.getMsgServer().getLastPage()) {
                            if (modelListItem.getMsgServer().getNextPageUrl() != null) {
                                try {
                                    urlNextPage = modelListItem.getMsgServer().getNextPageUrl() +
                                            "&gudang=" + sessionManager.getKeySetGudangPencarian() +
                                            "&name=" + URLEncoder.encode(binding.cariBarang.getText().toString().trim(), "UTF-8") +
                                            "&kode_promo=" + dataPromo.getPromoCode();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                urlNextPage = String.valueOf(modelListItem.getMsgServer().getNextPageUrl());
                            }
                            page = modelListItem.getMsgServer().getCurrentPage();
                            Log.e(TAG, "onResponse: " + urlNextPage);
                        }

                        if (modelItem.size() > 0) {
                            arrayBarangPromo.clear();
                            binding.gridview.setAdapter(null);
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

                                pm.setBarcode(itemData.getBarcode());
                                pm.setImages(itemData.getImages());
                                pm.setHarga_barang(itemData.getPrice());
                                pm.setHarga_2((itemData.getPriceDua() == null) ? "0" : itemData.getPriceDua());
                                pm.setHarga_3((itemData.getPriceTiga() == null) ? "0" : itemData.getPriceTiga());

                                String batasan1 = String.valueOf(itemData.getQtyHarga1());
                                String batasan2 = String.valueOf(itemData.getQtyHarga2());
                                String batasan3 = String.valueOf(itemData.getQtyHarga3());
                                pm.setBatasan1(batasan1);
                                pm.setBatasan2(batasan2);
                                pm.setBatasan3(batasan3);
                                if (itemData.getProdukPromo()) {
                                    pm.setIsPromo(itemData.getProdukPromo() ? 1 : 0);
                                    pm.setAkhirPromo(itemData.getAkhirPromo());
                                    pm.setHarga_promo(itemData.getPricePromo());
                                    pm.setStokPromo(itemData.getStokPromo());
                                } else {
                                    pm.setIsPromo(0);
                                }

                                if (itemData.getProdukPromoMember()) {
                                    pm.setPromoMember(1);
                                    pm.setPromoMemberKode(itemData.getKodePromoMember());
                                    pm.setPromoMemberAkhir(itemData.getAkhirPromoMember());
                                    //PROMO DUMMY
                                    pm.setPromoMemberPersenSilver(Double.parseDouble(itemData.getPersenPromoMemberSilver()));
                                    pm.setPromoMemberPersenGold(Double.parseDouble(itemData.getPersenPromoMemberGold()));
                                    pm.setPromoMemberPersenPlatinum(Double.parseDouble(itemData.getPersenPromoMemberPlatinum()));
                                } else {
                                    pm.setPromoMember(0);
                                }

                                arrayBarangPromo.add(pm);
                            }

                            adapterListSearchBarang = new AdapterListBarang(KatalogPromo.this, arrayBarangPromo, KatalogPromo.this);
                            binding.gridview.setAdapter(null);
                            binding.gridview.setAdapter(adapterListSearchBarang);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                dialog1.dismiss();
                binding.swipeBarangOrder.setRefreshing(false);
                Toast.makeText(KatalogPromo.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + "\n" + Arrays.toString(t.getStackTrace()));
            }
        });

    }

    private void pagenation() {
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, urlNextPage, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<ModelKatalog> arrayBarangAdd = new ArrayList<>();
                    Gson gson = new Gson();
                    ModelBarang modelListItem = gson.fromJson(response, ModelBarang.class);
                    List<Datum> modelItem = modelListItem.getMsgServer().getData();
                    if (modelListItem.getMsgServer().getCurrentPage() <= modelListItem.getMsgServer().getLastPage()) {
                        if (modelListItem.getMsgServer().getNextPageUrl() != null) {
                            try {
                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() +
                                        "&gudang=" + sessionManager.getKeySetGudangPencarian() +
                                        "&name=" + URLEncoder.encode(binding.cariBarang.getText().toString().trim(), "UTF-8") +
                                        "&kode_promo=" + dataPromo.getPromoCode();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
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
                            pm.setBarcode(itemData.getBarcode());
                            pm.setImages(itemData.getImages());
                            pm.setHarga_barang(itemData.getPrice());
                            pm.setHarga_2((itemData.getPriceDua() == null) ? "0" : itemData.getPriceDua());
                            pm.setHarga_3((itemData.getPriceTiga() == null) ? "0" : itemData.getPriceTiga());


                            String batasan1 = String.valueOf(itemData.getQtyHarga1());
                            String batasan2 = String.valueOf(itemData.getQtyHarga2());
                            String batasan3 = String.valueOf(itemData.getQtyHarga3());

                            pm.setBatasan1(batasan1);
                            pm.setBatasan2(batasan2);
                            pm.setBatasan3(batasan3);
                            if (itemData.getProdukPromo()) {
                                pm.setIsPromo(itemData.getProdukPromo() ? 1 : 0);
                                pm.setAkhirPromo(itemData.getAkhirPromo());
                                pm.setHarga_promo(itemData.getPricePromo());
                                pm.setStokPromo(itemData.getStokPromo());
                            } else {
                                pm.setIsPromo(0);
                            }

                            if (itemData.getProdukPromoMember()) {
                                pm.setPromoMember(1);
                                pm.setPromoMemberKode(itemData.getKodePromoMember());
                                pm.setPromoMemberAkhir(itemData.getAkhirPromoMember());
                                //PROMO DUMMY
                                pm.setPromoMemberPersenSilver(Double.parseDouble(itemData.getPersenPromoMemberSilver()));
                                pm.setPromoMemberPersenGold(Double.parseDouble(itemData.getPersenPromoMemberGold()));
                                pm.setPromoMemberPersenPlatinum(Double.parseDouble(itemData.getPersenPromoMemberPlatinum()));
                            } else {
                                pm.setPromoMember(0);
                            }

                            arrayBarangPromo.add(pm);
                        }
                        adapterListSearchBarang.addItems(arrayBarangAdd);
                    }
                } catch (Exception e) {
                    Snack("Barang Sudah Tampil Semua");
                    Log.e(TAG, "onResponse: Exception pagenation " + e.getMessage());
                }
            }
        }, new com.android.volley.Response.ErrorListener() {

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
                    Toast.makeText(KatalogPromo.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(getApplicationContext());
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
            protected com.android.volley.Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        arrReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(arrReq);
    }

    //popUpBarang
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    PopupBarangBinding popupBarangBinding;
    boolean cekTanggal = false;

    @Override
    public void AdapterListBarangClicked(ModelKatalog position) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        Log.e(TAG, "AdapterListBarangClicked: " + position.getKode_barang());
        dialogBuilder = new AlertDialog.Builder(KatalogPromo.this);

        popupBarangBinding = PopupBarangBinding.inflate(getLayoutInflater());

        final View kostumerPopUp = popupBarangBinding.getRoot();
        dialogBuilder.setView(kostumerPopUp);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        String StatusMber = "";
        StatusMber = sessionManager.getMembership();

        popupBarangBinding.layoutButton.setVisibility(View.GONE);

        popupBarangBinding.produkKategori.setText(position.getKategori_barang());
        popupBarangBinding.produkName.setText(position.getNama_barang());
        if (position.getDeskripsi() == null) {
            popupBarangBinding.produkDeskripsi.setText("Produk tidak ada deskripsi");
        } else {
            popupBarangBinding.produkDeskripsi.setText(position.getDeskripsi());
        }

        double cekStok = Double.parseDouble(position.getStok());

        if (cekStok > 0 && cekStok < 10) {
            popupBarangBinding.produkStok.setText(" < 10 Stok");
        } else if (cekStok >= 10 && cekStok < 25) {
            popupBarangBinding.produkStok.setText(" < 25 Stok");
        } else if (cekStok >= 25 && cekStok < 50) {
            popupBarangBinding.produkStok.setText(" < 50 Stok");
        } else if (cekStok >= 50) {
            popupBarangBinding.produkStok.setText(" > 50 Stok");
        } else {
            popupBarangBinding.produkStok.setText("KOSONG");
        }

        if (popupBarangBinding.produkStok.getText().toString().equals("KOSONG")) {
            popupBarangBinding.produkPrice1.setVisibility(View.GONE);
            popupBarangBinding.produkPrice2.setText("? (Harga Belum Diketahui)");
        } else {

            if (position.getIsPromo() == 1) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final Calendar baru = Calendar.getInstance();

                try {
                    Date tanggalNow = baru.getTime();
                    Date tanggalAkhir = formatter.parse(position.getAkhirPromo());

                    long mlNow = tanggalNow.getTime();
                    long mlAkhir = tanggalAkhir.getTime();

                    if (mlNow <= mlAkhir) {
                        cekTanggal = true;
                    } else {
                        cekTanggal = false;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            Log.e(TAG, "CEK IS PROMO: " + position.getIsPromo());
            Log.e(TAG, "CEK STOK PROMO: " + position.getStokPromo());
            Log.e(TAG, "CEK TANGGAL: " + cekTanggal);


            Log.e(TAG, "MASUK 6");

            if (position.getIsPromo() == 1 && position.getStokPromo() > 0 && cekTanggal) {
                Log.e(TAG, "MASUK PROMO");

                double hargaNormal = Double.parseDouble(position.getHarga_barang());
                double hargaPromo = Double.parseDouble(position.getHarga_promo());

                double disc = hargaNormal - hargaPromo;

                popupBarangBinding.keteranganDiskon.setVisibility(View.VISIBLE);
                popupBarangBinding.keteranganDiskon.setText(" ( Disc. -" + nf.format(disc) + " )");
                popupBarangBinding.produkPrice2.setVisibility(View.VISIBLE);
                popupBarangBinding.produkPrice2.setText("Rp. " + nf.format(hargaPromo));
                popupBarangBinding.produkPrice1.setText("Rp. " + nf.format(hargaNormal));
                popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.strike_through));

                popupBarangBinding.layoutStokPromo.setVisibility(View.VISIBLE);
                popupBarangBinding.stokPromo.setText("[ " + nf.format(position.getStokPromo()) + " Promo ]");

            } else {
                popupBarangBinding.layoutStokPromo.setVisibility(View.GONE);
                int hargaBarang = (int) Double.parseDouble(position.getHarga_barang());
                int hargaBarang2 = (int) Double.parseDouble(position.getHarga_2());
                int hargaBarang3 = (int) Double.parseDouble(position.getHarga_3());

                String testHarga = "Rp. " + nf.format(hargaBarang);
                String testHarga2 = "Rp. " + nf.format(hargaBarang2);
                String testHarga3 = "Rp. " + nf.format(hargaBarang3);
                popupBarangBinding.produkPrice1.setText(testHarga);
            }

        }

        Drawable image;

        if (!position.getImages().equals(Http.serverNotApi + "upload/barang/")) {
            Glide.with(this)
                    .asBitmap()
                    .load(position.getImages())
                    .into(popupBarangBinding.produkImage);
        } else {
            image = this.getResources().getDrawable(R.drawable.not_found);
            popupBarangBinding.produkImage.setImageDrawable(image);
        }

        popupBarangBinding.produkClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        popupBarangBinding.orderBtnPlusQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupBarangBinding.orderQtyOrder.getText().toString().equals("")) {
                    popupBarangBinding.orderQtyOrder.setText("0");
                }
                popupBarangBinding.orderBtnMinQty.setClickable(true);
                popupBarangBinding.layoutButtonKeranjang.setClickable(true);
                String qtyawal = popupBarangBinding.orderQtyOrder.getText().toString();
                double qty = Double.parseDouble(qtyawal);
                double plusQty = qty + 1;
                String hasil = String.valueOf(plusQty);
                popupBarangBinding.orderQtyOrder.setText(hasil);
            }
        });

        popupBarangBinding.orderBtnMinQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupBarangBinding.orderQtyOrder.getText().toString().equals("")) {
                    popupBarangBinding.orderQtyOrder.setText("0");
                }
                double stokInput = Double.parseDouble(popupBarangBinding.orderQtyOrder.getText().toString());
                if (stokInput == 0 || stokInput < 0) {
                    popupBarangBinding.orderBtnMinQty.setClickable(false);
                    popupBarangBinding.orderBtnPlusQty.setClickable(true);
                } else {
                    popupBarangBinding.orderBtnPlusQty.setClickable(true);
                    popupBarangBinding.orderBtnMinQty.setClickable(true);
                    String qtyawal = popupBarangBinding.orderQtyOrder.getText().toString();
                    double qty = Double.parseDouble(qtyawal);
                    double plusQty = qty - 1;
                    if (plusQty < 0) {
                        plusQty = 0;
                    }
                    String hasil = String.valueOf(plusQty);
                    popupBarangBinding.orderQtyOrder.setText(hasil);
                }
            }

        });

        double stokMax = Double.parseDouble(position.getStok());

        popupBarangBinding.orderQtyOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 500; // milliseconds

            @Override
            public void afterTextChanged(Editable editable) {

                double check = 0;

                if (editable.length() > 0) {
                    check = Double.parseDouble(editable.toString());
                } else {
                    check = 0;
                }

                if (check > stokMax) {
                    check = stokMax;
                    popupBarangBinding.orderQtyOrder.setText(String.valueOf(stokMax));
                    popupBarangBinding.orderBtnPlusQty.setEnabled(false);
                    popupBarangBinding.orderBtnPlusQty.setBackgroundColor(getResources().getColor(R.color.greyBelha));
                } else {
                    popupBarangBinding.orderBtnPlusQty.setEnabled(true);
                    popupBarangBinding.orderBtnPlusQty.setBackgroundColor(getResources().getColor(R.color.prangeBelha));
                }

                double jumlahBarangDibeli = check;

                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Log.e(TAG, "run: " + jumlahBarangDibeli);

                                        String hargaFix = "0";
                                        double diskon = 0;

                                        if (position.getIsPromo() == 1 && position.getStokPromo() > 0 && cekTanggal) {
                                            Log.e(TAG, "MASUK PROMO");

                                            double hargaNormal = Double.parseDouble(position.getHarga_barang());
                                            double hargaPromo = Double.parseDouble(position.getHarga_promo());

                                            diskon = hargaNormal - hargaPromo;
                                            hargaFix = String.valueOf(hargaPromo);

                                            popupBarangBinding.keteranganDiskon.setVisibility(View.VISIBLE);
                                            popupBarangBinding.keteranganDiskon.setText(" ( Disc. -" + nf.format(diskon) + " )");
                                            popupBarangBinding.produkPrice2.setVisibility(View.VISIBLE);
                                            popupBarangBinding.produkPrice2.setText("Rp. " + nf.format(hargaPromo));
                                            popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(KatalogPromo.this, R.drawable.strike_through));

                                        } else {

                                            int batasan1 = (int) Double.parseDouble(position.getBatasan1());
                                            int batasan2 = (int) Double.parseDouble(position.getBatasan2());
                                            int batasan3 = (int) Double.parseDouble(position.getBatasan3());

                                            if (batasan1 == batasan2) {
                                                hargaFix = position.getHarga_barang();
                                                Log.e(TAG, "run: HARGA 1");
                                            } else {
                                                if (jumlahBarangDibeli < batasan2) {
                                                    hargaFix = position.getHarga_barang();
                                                    Log.e(TAG, "run: HARGA 1");
                                                } else if (jumlahBarangDibeli >= batasan2 && jumlahBarangDibeli < batasan3) {
                                                    hargaFix = position.getHarga_2();
                                                    Log.e(TAG, "run: HARGA 2");
                                                } else if (jumlahBarangDibeli >= batasan3) {
                                                    hargaFix = position.getHarga_3();
                                                    Log.e(TAG, "run: HARGA 3");
                                                }
                                            }

                                            diskon = (int) (Double.parseDouble(position.getHarga_barang()) - Double.parseDouble(hargaFix));

                                        }

                                        if (diskon > 0) {
                                            popupBarangBinding.keteranganDiskon.setVisibility(View.VISIBLE);
                                            popupBarangBinding.keteranganDiskon.setText(" ( Disc. -" + nf.format(diskon) + " )");
                                            popupBarangBinding.produkPrice2.setVisibility(View.VISIBLE);
                                            popupBarangBinding.produkPrice2.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
                                            popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(KatalogPromo.this, R.drawable.strike_through));
                                        } else {
                                            popupBarangBinding.keteranganDiskon.setVisibility(View.GONE);
                                            popupBarangBinding.produkPrice2.setVisibility(View.GONE);
                                            popupBarangBinding.produkPrice2.setText("0");
                                            popupBarangBinding.produkPrice1.setBackgroundDrawable(null);
                                        }
                                    }
                                });

                            }
                        },
                        DELAY
                );

            }
        });

        Log.e(TAG, "AdapterListBarangClicked: " + itemCartList.size());

        for (int i = 0; i < itemCartList.size(); i++) {

            Log.e(TAG, "AdapterListBarangClicked: " + itemCartList.get(i).getNamaProduk());
            Log.e(TAG, "AdapterListBarangClicked: " + itemCartList.get(i).getBarcode());

            if (itemCartList.get(i).getCode().equals(position.getKode_barang())) {
                popupBarangBinding.peringatanText.setVisibility(View.VISIBLE);
                popupBarangBinding.orderQtyOrder.setText(String.valueOf(itemCartList.get(i).getQty()));
            }
        }

        Log.e(TAG, "AdapterListBarangClicked: " + position.getStok());

        if (Double.parseDouble(position.getStok()) == 0) {
            popupBarangBinding.layoutButtonKeranjang.setEnabled(false);
            Drawable d = this.getResources().getDrawable(R.drawable.button_round_dead);
            popupBarangBinding.layoutButtonKeranjang.setBackground(d);
            popupBarangBinding.layoutPesanStok.setVisibility(View.GONE);
        }

        popupBarangBinding.layoutButtonKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.isLoggedIn()) {
                    double jumlah = 0;

                    if (popupBarangBinding.orderQtyOrder.getText().toString().equals("")) {
                        jumlah = 0;
                    } else {
                        jumlah = Double.parseDouble(popupBarangBinding.orderQtyOrder.getText().toString());
                    }

                    Log.e(TAG, "onClick: " + String.valueOf(jumlah));

                    if (jumlah == 0) {
                        Toast.makeText(KatalogPromo.this, "Tentukan terlebih dahulu stok yang anda inginkan !", Toast.LENGTH_SHORT).show();
                    } else {
                        double jumlahAkhir = jumlah;
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(KatalogPromo.this);
                        builder1.setTitle("Konfirmasi");
                        builder1.setMessage("Menambah item ke keranjang ?");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton(
                                "Ya",
                                new DialogInterface.OnClickListener() {
                                    @SuppressLint("NewApi")
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        final ProgressDialog progressDialog = ProgressDialog.show(KatalogPromo.this, "Loading", "Please Wait...");
                                        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                                        Call<String> call = apiInterface.doAddCart(sessionManager.getPID(), sessionManager.getKeySetGudangPencarian(), position.getId(), position.getBarcode(), jumlahAkhir);
                                        call.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                                progressDialog.dismiss();

                                                try {
                                                    JSONObject obj = new JSONObject(response.body());

                                                    boolean success = obj.getBoolean("success");
                                                    String msgServer = obj.get("msgServer").toString();

                                                    if (success) {
                                                        itemCartList.clear();
                                                        Gson gson = new Gson();
                                                        ModelResponseCart modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);
                                                        assert modelResponseCart != null;
                                                        NewMainActivity.iconKeranjang.setBadgeValue(modelResponseCart.getMsgServer().getDetailItemCart().size());
                                                        binding.iconKeranjang.setBadgeValue(modelResponseCart.getMsgServer().getDetailItemCart().size());
                                                        alertDialog.dismiss();

                                                        itemCartList.addAll(modelResponseCart.getMsgServer().getDetailItemCart());

                                                    } else {
                                                        Toast.makeText(KatalogPromo.this, msgServer, Toast.LENGTH_SHORT).show();
                                                        Log.e(TAG, "onResponse: " + msgServer);
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                progressDialog.dismiss();
                                                Toast.makeText(KatalogPromo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                Log.e(TAG, "onFailure: " + t.getMessage());
                                                alertDialog.dismiss();
                                            }
                                        });

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
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(KatalogPromo.this);
                    alert.setIcon(R.drawable.dbelga);
                    alert.setTitle("Attention!");
                    alert.setMessage("Anda harus mempunyai akun Membership terlebih dahulu untuk menambahkan ke keranjang ?");
                    alert.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(KatalogPromo.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert.setNegativeButton("REGISTER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(KatalogPromo.this, RegisterActivity.class);
                            startActivity(intent);
                            finish();
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
    }

}