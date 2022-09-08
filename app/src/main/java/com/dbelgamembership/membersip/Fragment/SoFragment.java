package com.dbelgamembership.membersip.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.dbelgamembership.membersip.Screen.Transaksi.PrintActivity;
import com.dbelgamembership.membersip.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoFragment extends Fragment implements AdapterListTransaksi.AdapterListTransactionCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SessionManager sessionManager;

    public String url = Http.server, jsonResult, type, user, pass;
    private String TAG = "";
    private AdapterListTransaksi adapterListTransaksi;
    private LinearLayoutManager layoutManager;

    //TestData
    String idUser;
    EditText txt_CariTransaksi;
    RecyclerView rvTransaksi;

    List<Datum> itemlist = new ArrayList<>();

    private boolean isOnCreate = true;


    //PAGENATION
    SwipeRefreshLayout swipeRefreshLayout;
    private int pastVisisbleItems, visibleItemsCount, totalItemsCount, previous_totals = 0;
    private Boolean isLoading = true;
    private int view_threshold = 1;
    private int page_number = 1;
    private String urlNextPage = "";
    int page = 0;
    int total;
    int allData = 0;
    int current_index = 0;
    //PAGENATION

    public SoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this.getContext());
        layoutManager = new LinearLayoutManager(this.getContext());
        getDataUser();

        Log.e(TAG, "onCreate: ULANG ULANG" );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isOnCreate) {
            Log.e(TAG, "onResume SO FRAGMENT: " + isOnCreate );
            getDataUser();
        }
    }

    private void pagenation() {

        urlNextPage = urlNextPage +  "&customer=" + sessionManager.getPID();
        Log.e(TAG, "URL : " + urlNextPage);

        final ProgressDialog dialog1 = new ProgressDialog(getActivity());
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlNextPage, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog1.dismiss();
                        try {
                            Gson gson = new Gson();
                            ModelListTransaksi modelListTransaction = gson.fromJson(String.valueOf(response), ModelListTransaksi.class);
                            Log.e(TAG, "masuk Page 1");

                            if (modelListTransaction.getData().getCurrentPage() <= modelListTransaction.getData().getLastPage()) {
                                if (modelListTransaction.getData().getNextPageUrl() != null) {
                                    urlNextPage = String.valueOf(modelListTransaction.getData().getNextPageUrl()) + "&customer=" + sessionManager.getPID();
                                    page = modelListTransaction.getData().getCurrentPage();
                                    Log.e(TAG, "onResponse: " + urlNextPage);
                                }else {
                                    urlNextPage = String.valueOf(modelListTransaction.getData().getNextPageUrl());
                                }
                            }

                            int beforeSize = itemlist.size();
                            int afterSize = 0;

                            if (modelListTransaction.getData().getData().size() > 0) {

                                for (int i = 0; i < modelListTransaction.getData().getData().size(); i++) {
                                    if (!modelListTransaction.getData().getData().get(i).getStatus().equals("closed")) {
                                        Log.e(TAG, "onResponse: ADD PAGENATION : " + modelListTransaction.getData().getData().get(i).getCode() );

                                        itemlist.add(modelListTransaction.getData().getData().get(i));
                                    }
                                }

                                afterSize = itemlist.size();

                                if (afterSize == beforeSize) {
                                    isLoading = false;
                                }

                                Log.e(TAG, "onResponse: PAGENATION IS LOADING :: " + isLoading );
                                Log.e(TAG, "onResponse: TOTAL ITEM COUNT :: " + totalItemsCount );
                                Log.e(TAG, "onResponse: VISIBLE ITEM COUNT :: " + visibleItemsCount );
                                Log.e(TAG, "onResponse: PAST VISIBLE ITEMS :: " + pastVisisbleItems );
                                Log.e(TAG, "onResponse: VIEW THRESHOLD :: " + view_threshold );

                                adapterListTransaksi = new AdapterListTransaksi(getContext(), -1, itemlist, SoFragment.this::onRowAdapterListTransactionClicked);
                                rvTransaksi.setAdapter(adapterListTransaksi);

                            } else {
                                Snack("Data Terakhir !");
                            }

                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Snack("Data SO Error !");
                            rvTransaksi.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog1.dismiss();
                Log.e("onErrorResponse", error.getMessage(), error);
//                swipe_search.setRefreshing(false);
                rvTransaksi.setVisibility(View.GONE);
//                dialog.dismiss();
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getActivity(), NewMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(getContext(), "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
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
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(jsonObjectRequest);
//        dialog1.dismiss();

    }

    private void getDataUser() {

        pastVisisbleItems = 0;
        visibleItemsCount = 0;
        totalItemsCount = 0;
        previous_totals = 0;
        page_number = 1;
        page = 0;
        urlNextPage = "";
        itemlist.clear();

        idUser = sessionManager.getPID();
        Log.e(TAG, "ID USER SEARCH : " + idUser);
         url = Http.server;
        url = url + "transaction/list?customer=" + sessionManager.getPID();
        getDataTransaksi();

    }

    private void getDataTransaksi() {
        Log.e(TAG, "URL : " + url);
        final ProgressDialog dialog1 = new ProgressDialog(getActivity());
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog1.dismiss();
//                        Log.e(TAG, "onResponse: "+response);
                        rvTransaksi.setVisibility(View.VISIBLE);
                        try {
                            Log.e(TAG, "masuk 1");
                            Log.e(TAG, "masuk 2");
                            if (response != null) {
                                itemlist.clear();
                                Log.e(TAG, "masuk 3");
                                Gson gson = new Gson();
                                ModelListTransaksi modelListTransaction = gson.fromJson(String.valueOf(response), ModelListTransaksi.class);
                                Log.e(TAG, "masuk 4");

                                if (modelListTransaction.getData().getCurrentPage() <= modelListTransaction.getData().getLastPage()) {
                                    urlNextPage = (String.valueOf(modelListTransaction.getData().getNextPageUrl()));
                                    page = modelListTransaction.getData().getCurrentPage();
                                    Log.e(TAG, "onResponse Masuk sini : " + urlNextPage);
                                }

                                if (modelListTransaction.getData().getData().size() > 0) {

                                    for (int i = 0; i < modelListTransaction.getData().getData().size(); i++) {
                                        if (!modelListTransaction.getData().getData().get(i).getStatus().equals("closed")) {
                                            Log.e(TAG, "onResponse: ADD : " + modelListTransaction.getData().getData().get(i).getCode() );
                                            itemlist.add(modelListTransaction.getData().getData().get(i));
                                        }
                                    }

                                    adapterListTransaksi = new AdapterListTransaksi(getContext(), -1, itemlist, SoFragment.this::onRowAdapterListTransactionClicked);
                                    rvTransaksi.setAdapter(adapterListTransaksi);

                                } else {
                                    Snack("Data SO Kosong");
                                }

                                isOnCreate = false;
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Snack("Data SO Error !");
                            rvTransaksi.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog1.dismiss();
                Log.e("onErrorResponse", error.getMessage(), error);
//                swipe_search.setRefreshing(false);
                rvTransaksi.setVisibility(View.GONE);
//                dialog.dismiss();
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getActivity(), NewMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Snack(error.getMessage());
                    Toast.makeText(getContext(), "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
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
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(jsonObjectRequest);
//        dialog1.dismiss();

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
        Intent intent = new Intent(getContext(), PrintActivity.class);
        String DataOOS = position.getCode();
        Log.e(TAG, "onRowAdapterListTransactionClicked: " + DataOOS);
        intent.putExtra("DATAPRINT", DataOOS);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_so, container, false);

        Log.e(TAG, "onCreateView: ULANG ULANG" );

        rvTransaksi = view.findViewById(R.id.rv_Transaksi);
        rvTransaksi.setLayoutManager(layoutManager);
        rvTransaksi.setHasFixedSize(false);

        rvTransaksi.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        Snack("Semua Transaksi Sudah Tampil");
                    }
                    isLoading = true;
                }
            }
        });

        return view;
    }

}