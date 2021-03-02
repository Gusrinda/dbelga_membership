package com.dbelgamembership.membersip;

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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.dbelgamembership.membersip.Adapter.AdapterListBarang;
import com.dbelgamembership.membersip.Adapter.AdapterListTransaksi;
import com.dbelgamembership.membersip.Adapter.AdapterListWishlist;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelKatalog;
import com.dbelgamembership.membersip.Model.ModelWish.ModelWish;
import com.dbelgamembership.membersip.Model.ModelWish.Price;
import com.dbelgamembership.membersip.Model.ModelWish.WishlistDetail;
import com.dbelgamembership.membersip.Model.ResponseWishlist.ResponseWishlist;
import com.dbelgamembership.membersip.Model.modelBarang.ModelBarang;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class WishlishActivity extends AppCompatActivity implements AdapterListWishlist.AdapterListWishlistCallback, AdapterListWishlist.AdapterListWishlistCallbackDelete{

    SessionManager sessionManager;
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
    int namaKustomer;

    AdapterListWishlist adapterListSearchBarang;
    ArrayList<WishlistDetail> arrayBarang = new ArrayList<WishlistDetail>();
    List<String> arrayKategori = new ArrayList<String>();
    List<WishlistDetail> listDetail = new ArrayList<>();
    public static String[] stockArr;


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        sessionManager = new SessionManager(this);
        findID();

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvBarang.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        rvBarang.setLayoutManager(layoutManager);

        getDataUser();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
//        Intent intent = new Intent(WishlishActivity.this, KatalogActivity.class);
//        startActivity(intent);
    }

    private void getDataUser() {
        namaKustomer = Integer.parseInt(sessionManager.getPID());
        Log.e(TAG, "getDataUser: " + namaKustomer );
        SearchingWishlist();
    }

    private void SearchingWishlist() {
        url = Http.server + "wishlist-daftar";
        Log.e("url", url);
        getDataWishlist();
    }


    private void getDataWishlist() {
        final ProgressDialog dialog1 = new ProgressDialog(WishlishActivity.this);
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
                                ModelWish modelListItem = gson.fromJson(response, ModelWish.class);
                                List<com.dbelgamembership.membersip.Model.ModelWish.MsgServer> modelItem = modelListItem.getMsgServer();

                                Log.e(TAG, "Nama Set : " + namaKustomer );

                                Log.e(TAG, "SIZE 1 : " + modelItem.size() );

                                for (int i = modelItem.size() - 1; i >= 0; i--) {
                                    if (modelItem.get(i).getWishlistDetail().size() == 0) {
                                        modelItem.remove(i);
                                    }
                                }

                                Log.e(TAG, "SIZE 2 : " + modelItem.size() );

                                for (int i = modelItem.size() - 1; i >= 0; i--) {
                                    if (modelItem.get(i).getIdCustomer() != namaKustomer) {
                                        Log.e(TAG, "Yang dihapus : " + modelItem.get(i).getIdCustomer());
                                        modelItem.remove(i);
                                    } else {
                                        Log.e(TAG, "Ini user sama : " + modelItem.get(i).getIdCustomer() );
                                        listDetail = modelItem.get(i).getWishlistDetail();
                                    }
                                }

                                Log.e(TAG, "list size : "+ listDetail.size()  );

                                if (listDetail.size() > 0) {
                                    arrayBarang.clear();
                                    rvBarang.setAdapter(null);
                                    for (WishlistDetail itemData : listDetail) {
                                        WishlistDetail pm = new WishlistDetail();
                                        pm.setIdProduct((itemData.getIdProduct()));
                                        pm.setName(itemData.getName());
                                        pm.setGambar(itemData.getGambar());
                                        pm.setCodeProduct(String.valueOf(itemData.getCodeProduct()));
                                        pm.setQtyStok(itemData.getQtyStok());
                                        Price hargaBarang = itemData.getPrice();
                                        pm.setPrice(hargaBarang);
                                        arrayBarang.add(pm);
                                    }

                                    adapterListSearchBarang = new AdapterListWishlist(WishlishActivity.this, arrayBarang, WishlishActivity.this);
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
                    Toast.makeText(WishlishActivity.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(WishlishActivity.this);
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

        rvBarang = findViewById(R.id.gridview);
        toolbar = findViewById(R.id.toolbar);

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
    public void AdapterListWishlistClicked(WishlistDetail position) {

    }

    @Override
    public void AdapterListDelete(WishlistDetail position) {
        deleteItemWishlist(position.getIdProduct());
    }

    private void deleteItemWishlist(int idProduk) {
        String code = String.valueOf(idProduk);
        Log.e(TAG, "ID Member : " + sessionManager.getPID());
        Log.e(TAG, "ID Barang : " + code);
        url = Http.server + "wishlist-delete/" + sessionManager.getPID();
        Log.e(TAG, "URL : " + url);
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(WishlishActivity.this);
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Hapus item dari wishlist ?");
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
        final ProgressDialog dialog1 = new ProgressDialog(WishlishActivity.this);
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
                            Toast.makeText(WishlishActivity.this, "Barang berhasil dihapus dari wishlist !", Toast.LENGTH_SHORT).show();
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }
}