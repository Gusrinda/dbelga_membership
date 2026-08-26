package com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment;

import static com.dbelgamembership.membersip.Screen.SplashActivity.listGambarSlider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.dbelgamembership.membersip.Fragment.bottomSheet.BottomSheetFilterFragment;
import com.dbelgamembership.membersip.Fragment.bottomSheet.BottomSheetFilterFragmentUrutkan;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelBannerPromo.ModelBannerPromo;
import com.dbelgamembership.membersip.Model.ModelBarangTerlaris.ModelBarangTerlaris;
import com.dbelgamembership.membersip.Model.ModelKatalog;
import com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart;
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.ModelSearchWish.ModelSearchWish;
import com.dbelgamembership.membersip.Model.modelBarang.Datum;
import com.dbelgamembership.membersip.Model.modelBarang.ModelBarang;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.Screen.Log.model.LogModel;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.adapter.AdapterListMenu;
import com.dbelgamembership.membersip.Screen.Promo.KatalogPromo;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterListBarang;
import com.dbelgamembership.membersip.app.Adapter.AdapterListPromo;
import com.dbelgamembership.membersip.app.Adapter.AdapterListTerlaris;
import com.dbelgamembership.membersip.databinding.FragmentMainBinding;
import com.dbelgamembership.membersip.databinding.PopupBarangBinding;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class MainFragment extends Fragment implements AdapterListBarang.AdapterListBarangCallback, AdapterListTerlaris.AdapterListTerlarisCallback, AdapterListMenu.AdapterListMenuCallback, AdapterListPromo.AdapterListPromoCallback {

    private final String TAG = this.getClass().getSimpleName();
    private static boolean isAlreadyLoad = false;
    private FragmentMainBinding binding;


    SessionManager sessionManager;

    //popUpBarang
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    private int totalWish;
    private int IdKostumer;
    public static int jumlahWishlistAwal = 0;
    private String idGudang, namaGudang;


    public String url = Http.server, jsonResult, type, user, pass;
    String cariBarang;
    LinearLayout mainLayout, btnSortFilter, btnUrutkanData;
    ImageView btnCari;

    //PAGENATION
    private GridLayoutManager layoutManager;
    private GridLayoutManager layoutManagerTerlaris;
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

    AdapterListBarang adapterListSearchBarang;
    AdapterListTerlaris adapterListBarangTerlaris;
    ArrayList<ModelKatalog> arrayBarang = new ArrayList<ModelKatalog>();
    ArrayList<ModelKatalog> arrayBarangTerlaris = new ArrayList<ModelKatalog>();
    List<String> arrayKategori = new ArrayList<String>();
    public static ModelResponseCart dataChartUser;
    List<com.dbelgamembership.membersip.Model.ModelSearchWish.MsgServer> listDetail = new ArrayList<>();
    List<DetailItemCart> itemCartList = new ArrayList<>();
    public static String[] stockArr;
    private List<com.dbelgamembership.membersip.Model.ModelSearchWish.MsgServer> listBarang = new ArrayList<>();

    public static String filterString = "";
    private static boolean isFilter = false;

    private List<com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum> daftarPromo = new ArrayList<>();

    ArrayList<HashMap<String, Object>> daftarMenu = new ArrayList<HashMap<String, Object>>();

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataWishdanCart();
    }

    private void getDataWishdanCart() {
        if (sessionManager.isLoggedIn()) {
            getDataUser();
        }
    }

    private void getDataUser() {
        IdKostumer = Integer.parseInt(sessionManager.getPID());
        Log.e(TAG, "getDataUser: " + IdKostumer);
        SearchingWishlist();
        SearchingCart();
//        searchingCart();
    }

    private void SearchingCart() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doDetailCart(sessionManager.getPID());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                progressDialog.dismiss();
                try {

                    JSONObject obj = new JSONObject(response.body());

                    boolean success = obj.getBoolean("success");
                    String msgServer = obj.get("msgServer").toString();

                    itemCartList.clear();

                    if (success) {
                        Gson gson = new Gson();
                        ModelResponseCart modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);
                        dataChartUser = modelResponseCart;
                        assert modelResponseCart != null;
                        NewMainActivity.iconKeranjang.setBadgeValue(modelResponseCart.getMsgServer().getDetailItemCart().size());

                        itemCartList.addAll(modelResponseCart.getMsgServer().getDetailItemCart());
                    } else {
//                        Toast.makeText(KatalogActivity.this, msgServer, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + msgServer);
                        NewMainActivity.iconKeranjang.setBadgeValue(0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void SearchingWishlist() {
        String pid = sessionManager.getPID();
        url = Http.server + "wishlist-search?customer_id=" + pid;
        Log.e("url", url);
        getDataWishlist();
    }

    private void getDataWishlist() {
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        dialog1.dismiss();
                        try {
                            if (response.length() > 1) {

                                JsonObject root = new JsonParser().parse(response).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                if (success) {
                                    Gson gson = new Gson();
                                    ModelSearchWish modelListItem = gson.fromJson(response, ModelSearchWish.class);
                                    listDetail = modelListItem.getMsgServer();
                                    int jumlahWish = listDetail.size();
                                    jumlahWishlistAwal = jumlahWish;
                                    jumlahWishlistAwal = jumlahWish;
                                    Log.e(TAG, "on GET WISH : JumlahWishlist : " + jumlahWishlistAwal);
                                    NewMainActivity.binding.iconWhislist.setBadgeValue(jumlahWish);
                                    NewMainActivity.binding.bottomNavView.getOrCreateBadge(R.id.wishlistFragment).setNumber(jumlahWish);
                                } else {
                                    NewMainActivity.binding.iconWhislist.setBadgeValue(0);
                                    NewMainActivity.binding.bottomNavView.getOrCreateBadge(R.id.wishlistFragment).setNumber(0);
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
                    Intent intent = new Intent(requireContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(requireContext(), "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
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
                } else if (error.networkResponse == null) {
//                    dialog1.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Server not responding!\nTry again ?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getActivity().finish();
                                    startActivity(getActivity().getIntent());
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

        arrReq.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(arrReq);

    }

    AdapterListMenu adapterListMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        sessionManager = new SessionManager(getContext());
        getListSlider();

        filter = false;
        filterData = "";
        sortData = "";
        if (binding.cariBarang.getText().toString() == null | binding.cariBarang.getText().toString().equals("")) {
            cariBarang = "";
        }

        if (sessionManager.getKeySetGudangPencarian() != null) {
            idGudang = sessionManager.getKeySetGudangPencarian();
            Log.e(TAG, "onCreate ID GUDANG : " + idGudang);
            SearchingBarang(cariBarang);
            SearchKatalogTerlaris();
            getDaftarPromo();
        } else {
            Log.e(TAG, "onCreate: Doesnt have extra");
            getActivity().finish();
        }

        binding.gridview.setHasFixedSize(false);
        binding.gridViewTerlaris.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        layoutManagerTerlaris = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        binding.gridview.setLayoutManager(layoutManager);
        binding.gridViewTerlaris.setLayoutManager(layoutManagerTerlaris);

        binding.cariBarang.addTextChangedListener(new TextWatcher() {
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
                                    ((Activity) requireContext()).runOnUiThread(new Runnable() {
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
                                            cariBarang = binding.cariBarang.getText().toString();
                                            SearchingBarang(cariBarang);

                                        }
                                    });

                                }
                            },
                            DELAY
                    );


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
                        Toast.makeText(requireActivity(), "Semua barang sudah tampil !", Toast.LENGTH_SHORT).show();
                    }
                    isLoading = true;
                }
            }
        });

        binding.layoutFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFilterFragment bottomSheetFragment = new BottomSheetFilterFragment();
                bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        binding.layoutSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFilterFragmentUrutkan bottomSheetFragment = new BottomSheetFilterFragmentUrutkan();
                bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        binding.swipeBarangOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String cari = binding.cariBarang.getText().toString();

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

            }
        });

        binding.swipeBarangOrderTerlaris.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SearchKatalogTerlaris();
            }
        });

        binding.swipeBarangPromo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDaftarPromo();
            }
        });

        HashMap<String, Object> menu1 = new HashMap<String, Object>() {{
            put("id", 1);
            put("nama", "Katalog");
            put("img", R.drawable.icon_katalog);
            put("isSelected", true);
        }};

        HashMap<String, Object> menu2 = new HashMap<String, Object>() {{
            put("id", 2);
            put("nama", "Terlaris");
            put("img", R.drawable.icon_terlaris);
            put("isSelected", false);
        }};

        HashMap<String, Object> menu3 = new HashMap<String, Object>() {{
            put("id", 3);
            put("nama", "Promo");
            put("img", R.drawable.icon_promo);
            put("isSelected", false);
        }};

        daftarMenu.add(menu1);
        daftarMenu.add(menu2);
        daftarMenu.add(menu3);

        adapterListMenu = new AdapterListMenu(requireContext(), daftarMenu, MainFragment.this);
        binding.rvMenuPilihan.setAdapter(adapterListMenu);

        binding.rvMenuPilihan.setLayoutManager(new LinearLayoutManager(requireContext()) {

            @Override
            public void setOrientation(int orientation) {
                super.setOrientation(RecyclerView.HORIZONTAL);
            }

            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = (int) (getWidth() * 0.7);
                return true;
            }
        });

        return binding.getRoot();
    }

    private void SearchKatalogTerlaris() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doSearchBarangTerlaris(idGudang);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                binding.swipeBarangOrderTerlaris.setRefreshing(false);
                try {

                    JSONObject obj = new JSONObject(String.valueOf(response.body()));

                    boolean success = obj.getBoolean("success");
                    String msgServer = obj.get("msgServer").toString();

                    arrayBarangTerlaris.clear();

                    if (success) {
                        Gson gson = new Gson();
                        ModelBarangTerlaris modelBarangTerlaris = gson.fromJson(String.valueOf(response.body()), ModelBarangTerlaris.class);
                        List<com.dbelgamembership.membersip.Model.ModelBarangTerlaris.Datum> listItemTerlair = modelBarangTerlaris.getMsgServer().getData();

                        if (listItemTerlair.size() > 0) {

                            binding.gridViewTerlaris.setAdapter(null);
                            for (com.dbelgamembership.membersip.Model.ModelBarangTerlaris.Datum itemData : listItemTerlair) {
                                ModelKatalog pm = new ModelKatalog();
                                pm.setId(String.valueOf(itemData.getId()));
                                pm.setNama_barang(itemData.getName());

                                String deskripsi = "Deskripsi Kosong";

                                pm.setDeskripsi(deskripsi);
                                pm.setMerk_barang(itemData.getNamaKategori());
                                pm.setKategori_barang(itemData.getNamaSubKategori());
                                pm.setKode_barang(itemData.getCode());

                                //INI MASALAH STOK DIPERBARUI
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
                                pm.setHarga_barang(itemData.getPrice().getHarga());
                                pm.setHarga_2((itemData.getPrice().getHargaDua() == null) ? "0" : itemData.getPrice().getHargaDua());
                                pm.setHarga_3((itemData.getPrice().getHargaTiga() == null) ? "0" : itemData.getPrice().getHargaTiga());

                                String batasan1 = String.valueOf(itemData.getPrice().getQtyHarga1());
                                String batasan2 = String.valueOf(itemData.getPrice().getQtyHarga2());
                                String batasan3 = String.valueOf(itemData.getPrice().getQtyHarga3());
                                pm.setBatasan1(batasan1);
                                pm.setBatasan2(batasan2);
                                pm.setBatasan3(batasan3);

                                pm.setIsPromo(0);
                                pm.setPromoMember(0);

                                pm.setJumlahTerjual(Double.parseDouble(itemData.getTerjual()));

                                arrayBarangTerlaris.add(pm);
                            }

                            adapterListBarangTerlaris = new AdapterListTerlaris(getActivity(), arrayBarangTerlaris, MainFragment.this);
                            binding.gridViewTerlaris.setAdapter(null);
                            binding.gridViewTerlaris.setAdapter(adapterListBarangTerlaris);

                            binding.gridViewTerlaris.setVisibility(View.VISIBLE);
                            binding.rvTerlarisKosong.setVisibility(View.GONE);


                        } else {
                            Snack("Data barang terlaris tidak ada !");
                            binding.gridViewTerlaris.setVisibility(View.GONE);
                            binding.rvTerlarisKosong.setVisibility(View.VISIBLE);
                        }

                    } else {

                        Toast.makeText(requireContext(), msgServer, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + msgServer);
                        binding.gridViewTerlaris.setVisibility(View.GONE);
                        binding.rvTerlarisKosong.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    binding.gridViewTerlaris.setVisibility(View.GONE);
                    binding.rvTerlarisKosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                binding.swipeBarangOrderTerlaris.setRefreshing(false);
                binding.gridViewTerlaris.setVisibility(View.GONE);
                binding.rvTerlarisKosong.setVisibility(View.VISIBLE);
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void SearchingBarang(String cari) {
        page = 1;
        current_index = 0;
        url = Http.server;
        binding.swipeBarangOrder.setRefreshing(false);

        if (isFilter) {

            try {
                url = url + "search-katalog?gudang=" + idGudang + "&name=" + URLEncoder.encode(cari.trim(), "UTF-8") + filterString;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {

            try {
                url = url + "search-katalog?gudang=" + idGudang + "&name=" + URLEncoder.encode(cari.trim(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        getDataKatalogAwal();

        closeKeyboard();

        Log.e("url", url);

    }

    private void getDaftarPromo() {


        Log.e(TAG, "MASUK KE DAFTAR PROMO");

        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelBannerPromo> call = apiInterface.doGetBannerPromo();
        call.enqueue(new Callback<ModelBannerPromo>() {
            @Override
            public void onResponse(Call<ModelBannerPromo> call, retrofit2.Response<ModelBannerPromo> response) {
                binding.swipeBarangPromo.setRefreshing(false);

                if (response.code() == 200) {

                    ModelBannerPromo bannerPromo = response.body();
                    assert bannerPromo != null;
                    daftarPromo = Objects.requireNonNull(bannerPromo).getData();

                    Log.e(TAG, "SIZE DAFTAR PROMO :: " + daftarPromo.size());


                    if (daftarPromo.size() > 0) {
                        List<com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum> dataPromoTokoIni = new ArrayList<>();

                        for (int i = 0; i < daftarPromo.size(); i++) {
                            if (String.valueOf(daftarPromo.get(i).getGudang()).equals(sessionManager.getKeySetGudangPencarian())) {
                                dataPromoTokoIni.add(daftarPromo.get(i));
                            }
                        }

                        Log.e(TAG, "DATA PROMO TOKO INI :: " + dataPromoTokoIni.size());

                        List<com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum> dataPromoTokoFix = new ArrayList<>();

                        SimpleDateFormat af = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar cal = Calendar.getInstance(); // creates calendar

                        Date sekarang = cal.getTime();

                        for (int i = 0; i < dataPromoTokoIni.size(); i++) {
                            try {
                                Date tanggalBatas = af.parse(dataPromoTokoIni.get(i).getDateEnd());

                                if (sekarang.getTime() < tanggalBatas.getTime()) {
                                    dataPromoTokoFix.add(dataPromoTokoIni.get(i));
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        if (dataPromoTokoFix.size() > 0) {

                            for (int i = 0; i < dataPromoTokoFix.size(); i++) {
                                Log.e(TAG, "PROMO FIX :: " + i + " :: " + dataPromoTokoFix.get(i).getPromoCode());
                            }

                            AdapterListPromo adapterListPromo = new AdapterListPromo(requireContext(), dataPromoTokoFix, MainFragment.this);
                            binding.rvPromo.setAdapter(adapterListPromo);

                            binding.rvPromo.setVisibility(View.VISIBLE);
                            binding.rvPromoKosong.setVisibility(View.GONE);

                        } else {

                            binding.rvPromo.setVisibility(View.GONE);
                            binding.rvPromoKosong.setVisibility(View.VISIBLE);

                        }

                    } else {
                        binding.rvPromo.setVisibility(View.GONE);
                        binding.rvPromoKosong.setVisibility(View.VISIBLE);
//                        Toast.makeText(requireContext(), "PROMO KOSONG !!!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(requireContext(), "ERROR :: " + response.code(), Toast.LENGTH_SHORT).show();
                    binding.rvPromo.setVisibility(View.GONE);
                    binding.rvPromoKosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ModelBannerPromo> call, Throwable t) {
                binding.swipeBarangPromo.setRefreshing(false);
                binding.rvPromo.setVisibility(View.GONE);
                binding.rvPromoKosong.setVisibility(View.VISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                Toast.makeText(requireContext(), "ERROR :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void AdapterListPromoClicked(com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum position) {

        if (sessionManager.isLoggedIn()) {

            com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum dataPromo = position;
            Log.e(TAG, "AdapterListPromoClicked: " + dataPromo.getKeterangan());
            Log.e(TAG, "AdapterListPromoClicked: " + dataPromo.getDateEnd());
            Log.e(TAG, "AdapterListPromoClicked: " + dataPromo.getGudang());

            if (dataPromo.getGudang() == Integer.parseInt(sessionManager.getKeySetGudangPencarian())) {
                Intent intent = new Intent(requireContext(), KatalogPromo.class);
                intent.putExtra("hasExtra", true);
                intent.putExtra("dataPromo", (Parcelable) dataPromo);
                startActivity(intent);
            } else {
                Toast.makeText(requireContext(), "Promo tidak berlaku di toko ini !", Toast.LENGTH_SHORT).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
            alert.setIcon(R.drawable.dbelga);
            alert.setTitle("Attention!");
            alert.setMessage("Anda harus mempunyai akun Membership terlebih dahulu untuk melihat detail promo dbelga !");
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

    public void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = getActivity().getCurrentFocus();
        /*
         * If no view is focused, an NPE will be thrown
         *
         * Maxim Dmitriev
         */
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void pagenation() {
        RequestQueue mQueue = Volley.newRequestQueue(getContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, urlNextPage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<ModelKatalog> arrayBarangAdd = new ArrayList<>();
                    Gson gson = new Gson();
                    ModelBarang modelListItem = gson.fromJson(response, ModelBarang.class);
                    List<Datum> modelItem = modelListItem.getMsgServer().getData();
                    if (modelListItem.getMsgServer().getCurrentPage() <= modelListItem.getMsgServer().getLastPage()) {

                        if (modelListItem.getMsgServer().getNextPageUrl() != null) {
                            if (isFilter) {
                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&gudang=" + idGudang + "&name=" + URLEncoder.encode(binding.cariBarang.getText().toString().trim(), "UTF-8")
                                        + filterString;
                            } else {
                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&gudang=" + idGudang + "&name=" + URLEncoder.encode(binding.cariBarang.getText().toString().trim(), "UTF-8");

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
                    Intent intent = new Intent(getContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(requireActivity(), "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
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

        arrReq.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(arrReq);
    }

    private void getDataKatalogAwal() {
        Log.e(TAG, "getDataKatalogAwal: ISFILTER : " + isFilter);
        Log.e(TAG, "url : " + url);
        final ProgressDialog dialog1 = new ProgressDialog(requireContext());
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
        StringRequest arrReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " + response);
                        dialog1.dismiss();
                        try {
                            if (response != null) {
                                Gson gson = new Gson();
                                binding.swipeBarangOrder.setRefreshing(false);
                                arrayKategori.clear();
//                                arrayKategori.add("FILTER KATEGORI");
                                JsonObject root = new JsonParser().parse(response).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                if (!success) {
                                    Snack("Barang tidak ada !");
                                    binding.rvBarangKosong.setVisibility(View.VISIBLE);
                                    binding.gridview.setVisibility(View.GONE);
                                } else {

                                    ModelBarang modelListItem = gson.fromJson(response, ModelBarang.class);
                                    List<Datum> modelItem = modelListItem.getMsgServer().getData();
                                    if (modelListItem.getMsgServer().getCurrentPage() <= modelListItem.getMsgServer().getLastPage()) {
                                        if (modelListItem.getMsgServer().getNextPageUrl() != null) {
                                            if (isFilter) {
                                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&gudang=" + idGudang + "&name=" + URLEncoder.encode(binding.cariBarang.getText().toString().trim(), "UTF-8")
                                                        + filterString;
                                            } else {
                                                urlNextPage = modelListItem.getMsgServer().getNextPageUrl() + "&gudang=" + idGudang + "&name=" + URLEncoder.encode(binding.cariBarang.getText().toString().trim(), "UTF-8");
                                            }
                                        } else {
                                            urlNextPage = String.valueOf(modelListItem.getMsgServer().getNextPageUrl());
                                        }
                                        page = modelListItem.getMsgServer().getCurrentPage();
                                        Log.e(TAG, "onResponse: " + urlNextPage);
                                    }

                                    if (modelItem.size() > 0) {
                                        arrayBarang.clear();
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

//                                            arrayKategori.add(itemData.getNamaKategori());
                                            arrayBarang.add(pm);
                                        }
//                                        stockArr = new ArrayList<String>(new LinkedHashSet<String>(arrayKategori)).toArray(new String[0]);
                                        adapterListSearchBarang = new AdapterListBarang(requireContext(), arrayBarang, MainFragment.this);
                                        binding.gridview.setAdapter(null);
                                        binding.gridview.setAdapter(adapterListSearchBarang);

                                        binding.rvBarangKosong.setVisibility(View.GONE);
                                        binding.gridview.setVisibility(View.VISIBLE);
                                    }

                                }
                            } else {
                                Snack("Item Kosong");
                                binding.rvBarangKosong.setVisibility(View.VISIBLE);
                                binding.gridview.setVisibility(View.GONE);
                            }
//                            Snack("Barang sudah tampil semua !");
                        } catch (Exception e) {

                            binding.rvBarangKosong.setVisibility(View.VISIBLE);
                            binding.gridview.setVisibility(View.GONE);
                            Log.e(TAG, "onResponse: Error haha " + e + "\n" + Arrays.toString(e.getStackTrace()));

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                binding.rvBarangKosong.setVisibility(View.VISIBLE);
                binding.gridview.setVisibility(View.GONE);

                //mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    dialog1.dismiss();
                    sessionManager.destroySession();
                    Intent intent = new Intent(requireContext(), KatalogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (error instanceof NetworkError) {
                    dialog1.dismiss();
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(requireContext(), "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
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
                } else if (error.networkResponse == null) {
                    dialog1.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Server not responding!\nTry again ?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getActivity().finish();
                                    startActivity(getActivity().getIntent());
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

        arrReq.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(arrReq);

    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(binding.layoutMain, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.darkBiruBelga));
        snackbar.show();
    }

    public void sortBarang(String urutkanData) {
        Log.e(TAG, "sortBarang: Masuk SORT");
        if (urutkanData.equals("priceDown")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = 0;
                    int b = 0;

                    if (Double.parseDouble(modelKatalog.getStok()) == 0) {
                        a = 999999999;
                    } else {
                        a = (int) Double.parseDouble(modelKatalog.getHarga_barang());
                    }

                    if (Double.parseDouble(t1.getStok()) == 0) {
                        b = 999999999;
                    } else {
                        b = (int) Double.parseDouble(t1.getHarga_barang());

                    }

                    return a - b;
                }
            });
            Snack("Barang diurutkan harga terendah !");
        } else if (urutkanData.equals("priceUp")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    int a = 0;
                    int b = 0;

                    if (Double.parseDouble(modelKatalog.getStok()) > 0) {
                        a = (int) Double.parseDouble(modelKatalog.getHarga_barang());
                    }

                    if (Double.parseDouble(t1.getStok()) > 0) {
                        b = (int) Double.parseDouble(t1.getHarga_barang());
                    }

                    return b - a;
                }
            });
            Snack("Barang diurutkan harga tertinggi !");
        } else if (urutkanData.equals("stokUp")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    double a = Double.parseDouble(modelKatalog.getStok());
                    double b = Double.parseDouble(t1.getStok());
                    return (int) (b - a);
                }
            });
            Snack("Barang diurutkan stok tertinggi !");
        } else if (urutkanData.equals("stokDown")) {
            Collections.sort(arrayBarang, new Comparator<ModelKatalog>() {
                @Override
                public int compare(ModelKatalog modelKatalog, ModelKatalog t1) {
                    double a = Double.parseDouble(modelKatalog.getStok());
                    double b = Double.parseDouble(t1.getStok());
                    return (int) (a - b);
                }
            });
            Snack("Barang diurutkan stok terendah !");
        }

        adapterListSearchBarang = new AdapterListBarang(requireContext(), arrayBarang, MainFragment.this);
        binding.gridview.setAdapter(null);
        binding.gridview.setAdapter(adapterListSearchBarang);
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
        binding.cariBarang.setText("");
        SearchingBarang("");
    }

    private void getListSlider() {
        List<SlideModel> models = new ArrayList<>();

        if (listGambarSlider.length > 0) {
            for (int i = 0; i < listGambarSlider.length; i++) {
                models.add(new SlideModel(listGambarSlider[i], ScaleTypes.FIT)); // Banner promo 3
            }
        }

        binding.imageSlider.setImageList(models, ScaleTypes.FIT);
    }

    PopupBarangBinding popupBarangBinding;
    boolean cekTanggal = false;

    @Override
    public void AdapterListBarangClicked(ModelKatalog position) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        Log.e(TAG, "AdapterListBarangClicked: " + position.getKode_barang());
        dialogBuilder = new AlertDialog.Builder(requireContext());

        popupBarangBinding = PopupBarangBinding.inflate(getLayoutInflater());

        final View kostumerPopUp = popupBarangBinding.getRoot();
        dialogBuilder.setView(kostumerPopUp);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        String StatusMber = "";
        StatusMber = sessionManager.getMembership();

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
            popupBarangBinding.produkStok.setText("HABIS");
        }

        if (popupBarangBinding.produkStok.getText().toString().equals("HABIS")) {
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
                popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.strike_through));

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


            if (sessionManager.getMembership() != null) {
                Log.e(TAG, "AdapterListBarangClicked STATUS MEMBERSHIP: " + sessionManager.getMembership());

                if (position.getPromoMember() == 1) {
                    popupBarangBinding.layoutDiskonMembership.setVisibility(View.VISIBLE);
                    double persen = 0;
                    if (sessionManager.getMembership().equals("SILVER")) {
                        persen = position.getPromoMemberPersenSilver();
                        popupBarangBinding.keteranganDiskonMembership.setText(nf.format(persen) + "% ( SILVER )");
                    } else if (sessionManager.getMembership().equals("GOLD")) {
                        persen = position.getPromoMemberPersenGold();
                        popupBarangBinding.keteranganDiskonMembership.setText(nf.format(persen) + "% ( GOLD )");
                    } else if (sessionManager.getMembership().equals("PLATINUM")) {
                        persen = position.getPromoMemberPersenPlatinum();
                        popupBarangBinding.keteranganDiskonMembership.setText(nf.format(persen) + "% ( PLATINUM )");
                    }

                }

            }

        }

        Drawable image;

        if (!position.getImages().equals(Http.serverNotApi + "upload/barang/")) {
            Glide.with(this)
                    .asBitmap()
                    .load(position.getImages())
                    .error(R.drawable.not_found)
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


        //INI DISABLE
//        popupBarangBinding.orderQtyOrder.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            private Timer timer = new Timer();
//            private final long DELAY = 500; // milliseconds
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                double check = 0;
//
//                if (editable.length() > 0) {
//                    check = Double.parseDouble(editable.toString());
//                } else {
//                    check = 0;
//                }
//
//
//                if (check > stokMax) {
//                    check = stokMax;
//                    popupBarangBinding.orderQtyOrder.setText(String.valueOf(stokMax));
//                    popupBarangBinding.orderBtnPlusQty.setEnabled(false);
//                    popupBarangBinding.orderBtnPlusQty.setBackgroundColor(getResources().getColor(R.color.greyBelha));
//                } else {
//                    popupBarangBinding.orderBtnPlusQty.setEnabled(true);
//                    popupBarangBinding.orderBtnPlusQty.setBackgroundColor(getResources().getColor(R.color.prangeBelha));
//                }
//
//                double jumlahBarangDibeli = check;
//
//                timer.cancel();
//                timer = new Timer();
//                timer.schedule(
//                        new TimerTask() {
//                            @Override
//                            public void run() {
//                                ((Activity) requireContext()).runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        Log.e(TAG, "run: " + jumlahBarangDibeli);
//
//                                        String hargaFix = "0";
//                                        double diskon = 0;
//
//                                        if (position.getIsPromo() == 1 && position.getStokPromo() > 0 && cekTanggal) {
//                                            Log.e(TAG, "MASUK PROMO");
//
//                                            double hargaNormal = Double.parseDouble(position.getHarga_barang());
//                                            double hargaPromo = Double.parseDouble(position.getHarga_promo());
//
//                                            diskon = hargaNormal - hargaPromo;
//                                            hargaFix = String.valueOf(hargaPromo);
//
//                                            popupBarangBinding.keteranganDiskon.setVisibility(View.VISIBLE);
//                                            popupBarangBinding.keteranganDiskon.setText(" ( Disc. -" + nf.format(diskon) + " )");
//                                            popupBarangBinding.produkPrice2.setVisibility(View.VISIBLE);
//                                            popupBarangBinding.produkPrice2.setText("Rp. " + nf.format(hargaPromo));
//                                            popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.strike_through));
//
//                                        } else {
//
//                                            int batasan1 = (int) Double.parseDouble(position.getBatasan1());
//                                            int batasan2 = (int) Double.parseDouble(position.getBatasan2());
//                                            int batasan3 = (int) Double.parseDouble(position.getBatasan3());
//
//                                            if (batasan1 == batasan2) {
//                                                hargaFix = position.getHarga_barang();
//                                                Log.e(TAG, "run: HARGA 1");
//                                            } else {
//                                                if (jumlahBarangDibeli < batasan2) {
//                                                    hargaFix = position.getHarga_barang();
//                                                    Log.e(TAG, "run: HARGA 1");
//                                                } else if (jumlahBarangDibeli >= batasan2 && jumlahBarangDibeli < batasan3) {
//                                                    hargaFix = position.getHarga_2();
//                                                    Log.e(TAG, "run: HARGA 2");
//                                                } else if (jumlahBarangDibeli >= batasan3) {
//                                                    hargaFix = position.getHarga_3();
//                                                    Log.e(TAG, "run: HARGA 3");
//                                                }
//                                            }
//
//                                            diskon = (int) (Double.parseDouble(position.getHarga_barang()) - Double.parseDouble(hargaFix));
//
//                                        }
//
//
//                                        if (diskon > 0) {
//                                            popupBarangBinding.keteranganDiskon.setVisibility(View.VISIBLE);
//                                            popupBarangBinding.keteranganDiskon.setText(" ( Disc. -" + nf.format(diskon) + " )");
//                                            popupBarangBinding.produkPrice2.setVisibility(View.VISIBLE);
//                                            popupBarangBinding.produkPrice2.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
//                                            popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.strike_through));
//                                        } else {
//                                            popupBarangBinding.keteranganDiskon.setVisibility(View.GONE);
//                                            popupBarangBinding.produkPrice2.setVisibility(View.GONE);
//                                            popupBarangBinding.produkPrice2.setText("0");
//                                            popupBarangBinding.produkPrice1.setBackgroundDrawable(null);
//                                        }
//                                    }
//                                });
//
//                            }
//                        },
//                        DELAY
//                );
//
//
//            }
//        });

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
            Drawable d = getContext().getResources().getDrawable(R.drawable.button_round_dead);
            popupBarangBinding.layoutButtonKeranjang.setBackground(d);
            popupBarangBinding.layoutPesanStok.setVisibility(View.GONE);
        }

        popupBarangBinding.layoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double jumlah = 0;
                if (popupBarangBinding.orderQtyOrder.getText().toString().equals("")) {
                    jumlah = 1;
                } else {
                    jumlah = Double.parseDouble(popupBarangBinding.orderQtyOrder.getText().toString());
                }

                tambahItemWishlist(position.getId(), jumlah, position.getNama_barang());
            }

            private void tambahItemWishlist(String kode_barang, double stokBarang, String namaBarang) {
                Log.e(TAG, "Size awal : " + listBarang.size());
                Log.e(TAG, "tambahItemWishlist: Stok ingin " + stokBarang);
                String code = kode_barang;
                Log.e(TAG, "ID Member : " + sessionManager.getPID());
                Log.e(TAG, "ID Barang : " + code);
                url = Http.server + "wishlist-add/" + sessionManager.getPID();
                Log.e(TAG, "URL : " + url);
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getContext());
                builder1.setTitle("Konfirmasi");
                builder1.setMessage("Menambah item ke wishlist ?");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                JSONObject postData = new JSONObject();
                                try {
                                    postData.put("produk", code);
                                    postData.put("qty", stokBarang);
                                    postData.put("id_gudang", idGudang);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClickSubmit: " + postData);
                                SimpanPost(postData, namaBarang);
                                alertDialog.dismiss();
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
                        Toast.makeText(requireContext(), "Tentukan terlebih dahulu stok yang anda inginkan !", Toast.LENGTH_SHORT).show();
                    } else {
                        double jumlahAkhir = jumlah;
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(requireContext());
                        builder1.setTitle("Konfirmasi");
                        builder1.setMessage("Menambah item ke keranjang ?");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton(
                                "Ya",
                                new DialogInterface.OnClickListener() {
                                    @SuppressLint("NewApi")
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        final ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Loading", "Please Wait...");
                                        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                                        Call<String> call = apiInterface.doAddCart(sessionManager.getPID(), idGudang, position.getId(), position.getBarcode(), jumlahAkhir);
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
                                                        alertDialog.dismiss();

                                                        sessionManager.addLogHistory(new LogModel(
                                                                "CART", Calendar.getInstance().getTime(), "Menambahkan barang " + position.getNama_barang() + " sejumlah " + jumlahAkhir + " ke keranjang anda"
                                                        ));

                                                        itemCartList.addAll(modelResponseCart.getMsgServer().getDetailItemCart());

                                                    } else {
                                                        Toast.makeText(requireContext(), msgServer, Toast.LENGTH_SHORT).show();
                                                        Log.e(TAG, "onResponse: " + msgServer);
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                progressDialog.dismiss();
                                                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                    alert.setIcon(R.drawable.dbelga);
                    alert.setTitle("Attention!");
                    alert.setMessage("Anda harus mempunyai akun Membership terlebih dahulu untuk menambahkan ke keranjang ?");
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

//        popupBarangBinding.layoutButtonKeranjang.setEnabled(false);
        popupBarangBinding.layoutButtonKeranjang.setEnabled(true);
    }

    private void SimpanPost(JSONObject postData, String namaBarang) {
        final ProgressDialog dialog1 = new ProgressDialog(requireContext());
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
        Log.e(TAG, "postData: " + postData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog1.dismiss();
                            Log.e(TAG, "Response : " + response);
                            Gson gson = new Gson();
                            ModelSearchWish responseWishlist = gson.fromJson(String.valueOf(response), ModelSearchWish.class);
                            listBarang = responseWishlist.getMsgServer();

                            boolean responseBool = responseWishlist.getSuccess();

                            if (responseWishlist.getSuccess()) {
                                Log.e(TAG, "onResponse: " + responseBool);
                                Snack("Berhasil menambahkan barang di Wishlist");
                                sessionManager.addLogHistory(new LogModel(
                                        "WISHLIST", Calendar.getInstance().getTime(), "Anda menambahkan barang " + namaBarang+ " ke wishlist."
                                ));
                            } else {
                                Log.e(TAG, "onResponse: " + responseBool);
                                String string = "error : " + responseWishlist.getDescription();
                                Snackbar snackbar = Snackbar.make(binding.layoutMain, string, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null);
                                View snackBarView = snackbar.getView();
                                snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
                                snackbar.show();
                            }

                            listDetail = listBarang;
                            jumlahWishlistAwal = listBarang.size();
                            Log.e(TAG, "on Tambah : JumlahWishlist : " + jumlahWishlistAwal);
                            NewMainActivity.binding.iconWhislist.setBadgeValue(listBarang.size());
                            NewMainActivity.binding.bottomNavView.getOrCreateBadge(R.id.wishlistFragment).setNumber(listBarang.size());

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
                    Intent intent = new Intent(requireContext(), KatalogActivity.class);
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

    @Override
    public void AdapterListTerlaris(ModelKatalog position) {

        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        Log.e(TAG, "AdapterListBarangClicked: " + position.getKode_barang());
        dialogBuilder = new AlertDialog.Builder(requireContext());

        popupBarangBinding = PopupBarangBinding.inflate(getLayoutInflater());

        final View kostumerPopUp = popupBarangBinding.getRoot();
        dialogBuilder.setView(kostumerPopUp);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        String StatusMber = "";
        StatusMber = sessionManager.getMembership();

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
            popupBarangBinding.produkStok.setText("HABIS");
        }

        if (popupBarangBinding.produkStok.getText().toString().equals("HABIS")) {
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
                popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.strike_through));

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


            if (sessionManager.getMembership() != null) {
                Log.e(TAG, "AdapterListBarangClicked STATUS MEMBERSHIP: " + sessionManager.getMembership());

                if (position.getPromoMember() == 1) {
                    popupBarangBinding.layoutDiskonMembership.setVisibility(View.VISIBLE);
                    double persen = 0;
                    if (sessionManager.getMembership().equals("SILVER")) {
                        persen = position.getPromoMemberPersenSilver();
                        popupBarangBinding.keteranganDiskonMembership.setText(nf.format(persen) + "% ( SILVER )");
                    } else if (sessionManager.getMembership().equals("GOLD")) {
                        persen = position.getPromoMemberPersenGold();
                        popupBarangBinding.keteranganDiskonMembership.setText(nf.format(persen) + "% ( GOLD )");
                    } else if (sessionManager.getMembership().equals("PLATINUM")) {
                        persen = position.getPromoMemberPersenPlatinum();
                        popupBarangBinding.keteranganDiskonMembership.setText(nf.format(persen) + "% ( PLATINUM )");
                    }

                }

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
//                popupBarangBinding.layoutButtonKeranjang.setClickable(false);
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
                                ((Activity) requireContext()).runOnUiThread(new Runnable() {
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
                                            popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.strike_through));

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
                                            popupBarangBinding.produkPrice1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.strike_through));
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
            Drawable d = getContext().getResources().getDrawable(R.drawable.button_round_dead);
            popupBarangBinding.layoutButtonKeranjang.setBackground(d);
            popupBarangBinding.layoutPesanStok.setVisibility(View.GONE);
        }

        popupBarangBinding.layoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double jumlah = 0;
                if (popupBarangBinding.orderQtyOrder.getText().toString().equals("")) {
                    jumlah = 1;
                } else {
                    jumlah = Double.parseDouble(popupBarangBinding.orderQtyOrder.getText().toString());
                }

                tambahItemWishlist(position.getId(), jumlah, position.getNama_barang());
            }

            private void tambahItemWishlist(String kode_barang, double stokBarang, String namaBarang) {
                Log.e(TAG, "Size awal : " + listBarang.size());
                Log.e(TAG, "tambahItemWishlist: Stok ingin " + stokBarang);
                String code = kode_barang;
                Log.e(TAG, "ID Member : " + sessionManager.getPID());
                Log.e(TAG, "ID Barang : " + code);
                url = Http.server + "wishlist-add/" + sessionManager.getPID();
                Log.e(TAG, "URL : " + url);
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getContext());
                builder1.setTitle("Konfirmasi");
                builder1.setMessage("Menambah item ke wishlist ?");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                JSONObject postData = new JSONObject();
                                try {
                                    postData.put("produk", code);
                                    postData.put("qty", stokBarang);
                                    postData.put("id_gudang", idGudang);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClickSubmit: " + postData);
                                SimpanPost(postData, namaBarang);
                                alertDialog.dismiss();
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
                        Toast.makeText(requireContext(), "Tentukan terlebih dahulu stok yang anda inginkan !", Toast.LENGTH_SHORT).show();
                    } else {
                        double jumlahAkhir = jumlah;
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(requireContext());
                        builder1.setTitle("Konfirmasi");
                        builder1.setMessage("Menambah item ke keranjang ?");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton(
                                "Ya",
                                new DialogInterface.OnClickListener() {
                                    @SuppressLint("NewApi")
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        final ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Loading", "Please Wait...");
                                        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                                        Call<String> call = apiInterface.doAddCart(sessionManager.getPID(), idGudang, position.getId(), position.getBarcode(), jumlahAkhir);
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
                                                        alertDialog.dismiss();

                                                        sessionManager.addLogHistory(new LogModel(
                                                                "CART", Calendar.getInstance().getTime(), "Menambahkan barang " + position.getNama_barang() + " sejumlah " + jumlahAkhir + " ke keranjang anda"
                                                        ));

                                                        itemCartList.addAll(modelResponseCart.getMsgServer().getDetailItemCart());

                                                    } else {
                                                        Toast.makeText(requireContext(), msgServer, Toast.LENGTH_SHORT).show();
                                                        Log.e(TAG, "onResponse: " + msgServer);
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                progressDialog.dismiss();
                                                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                    alert.setIcon(R.drawable.dbelga);
                    alert.setTitle("Attention!");
                    alert.setMessage("Anda harus mempunyai akun Membership terlebih dahulu untuk menambahkan ke keranjang ?");
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

        popupBarangBinding.layoutButtonKeranjang.setEnabled(false);

    }

    @SuppressLint("NewApi")
    @Override
    public void AdapterListMenu(int id) {

        for (int i = 0; i < daftarMenu.size(); i++) {

            int idMenu = (int) daftarMenu.get(i).get("id");

            if (idMenu == id) {
                daftarMenu.get(i).replace("isSelected", true);
            } else {
                daftarMenu.get(i).replace("isSelected", false);
            }
        }

        adapterListMenu.notifyDataSetChanged();

        switch (id) {
            case 1:
                binding.linearKatalog.setVisibility(View.VISIBLE);
                binding.linearTerlaris.setVisibility(View.GONE);
                binding.linearPromo.setVisibility(View.GONE);
                break;
            case 2:
                binding.linearKatalog.setVisibility(View.GONE);
                binding.linearTerlaris.setVisibility(View.VISIBLE);
                binding.linearPromo.setVisibility(View.GONE);
                break;
            case 3:
                binding.linearKatalog.setVisibility(View.GONE);
                binding.linearTerlaris.setVisibility(View.GONE);
                binding.linearPromo.setVisibility(View.VISIBLE);
                break;
        }


//        adapterListMenu = new AdapterListMenu(requireContext(), daftarMenu, MainFragment.this);
//        binding.rvMenuPilihan.setAdapter(adapterListMenu);
    }
}