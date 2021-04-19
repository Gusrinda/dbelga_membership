package com.dbelgamembership.membersip.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.dbelgamembership.membersip.Adapter.AdapterListTransaksi;
import com.dbelgamembership.membersip.Adapter.AdapterListTransaksiPayment;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.MainActivity;
import com.dbelgamembership.membersip.Model.ModelPayment.Datum;
import com.dbelgamembership.membersip.Model.ModelPayment.ModelPayment;
import com.dbelgamembership.membersip.Model.modelListFaktur.ModelListFaktur;
import com.dbelgamembership.membersip.Model.modelListTransaksi.ModelListTransaksi;
import com.dbelgamembership.membersip.PrintActivity;
import com.dbelgamembership.membersip.PrintFakturActivity;
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

public class FakturFragment extends Fragment implements AdapterListTransaksiPayment.AdapterListTransactionCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SessionManager sessionManager;

    public String url = Http.server, jsonResult, type, user, pass;
    private String TAG = "";
    private AdapterListTransaksiPayment adapterListTransaksi;
    private LinearLayoutManager layoutManager;

    //TestData
    String idUser;
    EditText txt_CariTransaksi;
    RecyclerView rvTransaksi;
    private List<com.dbelgamembership.membersip.Model.modelListFaktur.Datum> itemlist = new ArrayList<>();


    private String mParam1;
    private String mParam2;

    public FakturFragment() {
        // Required empty public constructor
    }

    public static FakturFragment newInstance(String param1, String param2) {
        FakturFragment fragment = new FakturFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this.getContext());
        layoutManager = new LinearLayoutManager(this.getContext());
        getDataUser();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void getDataUser() {
        idUser = sessionManager.getPID();
        Log.e(TAG, "ID USER SEARCH : " + idUser);
        url = url + "payment/list/";
        getDataTransaksi();
    }

    private void getDataTransaksi() {
        Log.e(TAG, "URL : " + url);
        final ProgressDialog dialog1 = new ProgressDialog(FakturFragment.this.getContext());
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, "onResponse: "+response);
                        rvTransaksi.setVisibility(View.VISIBLE);
                        try {
                            Log.e(TAG, "masuk 1");
                            itemlist.clear();
                            Log.e(TAG, "masuk 2");
                            if (response != null) {
                                Log.e(TAG, "masuk 3");
                                Gson gson = new Gson();
                                ModelListFaktur modelListTransaction = gson.fromJson(String.valueOf(response), ModelListFaktur.class);
                                Log.e(TAG, "masuk 4");
                                itemlist = modelListTransaction.getData();
                                if (itemlist.size() > 0) {
                                    Log.e(TAG, "masuk 5");
                                    for (int i = itemlist.size() - 1; i >= 0; i--) {
                                        Log.e(TAG, i + " Nomor ID User : " + itemlist.get(i).getIdentitasCustomer());

                                        String idCustom = "";

                                        if ( itemlist.get(i).getIdentitasCustomer() == null) {
                                            idCustom = "";
                                        } else {
                                            idCustom = itemlist.get(i).getIdentitasCustomer();
                                        }

                                        if (!idCustom.equals(idUser)) {
                                            itemlist.remove(i);
                                        }
                                    }
                                    Log.e(TAG, "masuk 6");
                                    Collections.sort(itemlist, new Comparator<com.dbelgamembership.membersip.Model.modelListFaktur.Datum>() {
                                        @Override
                                        public int compare(com.dbelgamembership.membersip.Model.modelListFaktur.Datum datum, com.dbelgamembership.membersip.Model.modelListFaktur.Datum t1) {
                                            return t1.getDateTransaction().compareToIgnoreCase(datum.getDateTransaction());
                                        }
                                    });

                                    Log.e(TAG, "Hasil " + itemlist.toString());

                                    adapterListTransaksi = new AdapterListTransaksiPayment(getContext(), -1, itemlist, FakturFragment.this::onRowAdapterListTransactionClicked);
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
                    Intent intent = new Intent(getActivity(), MainActivity.class);
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
        dialog1.dismiss();

    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(rvTransaksi, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorDark));
        snackbar.show();
    }

    @Override
    public void onRowAdapterListTransactionClicked(int position) {
        Intent intent = new Intent(getContext(), PrintFakturActivity.class);
        String DataOOS = itemlist.get(position).getPembayaranCode();
        Log.e(TAG, "onRowAdapterListTransactionClicked: " + DataOOS);
        intent.putExtra("DATAPRINT", DataOOS);
        intent.putExtra("FAKTUR", true);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faktur, container, false);
//        txt_CariTransaksi = view.findViewById(R.id.edt_cariTransaksi);
        rvTransaksi = view.findViewById(R.id.rv_Transaksi);
        rvTransaksi.setLayoutManager(layoutManager);
        rvTransaksi.setHasFixedSize(false);
        return view;
    }
}