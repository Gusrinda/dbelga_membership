package com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum;
import com.dbelgamembership.membersip.Model.ModelBannerPromo.ModelBannerPromo;
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.ModelSearchWish.ModelSearchWish;
import com.dbelgamembership.membersip.Model.ModelSearchWish.MsgServer;
import com.dbelgamembership.membersip.Model.ModelSearchWish.Price;
import com.dbelgamembership.membersip.Model.ModelWish.ModelWish;
import com.dbelgamembership.membersip.Model.ResponseWishlist.ResponseWishlist;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.Screen.Katalog.WishlishActivity;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.Screen.Promo.KatalogPromo;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterListPromo;
import com.dbelgamembership.membersip.app.Adapter.AdapterListWishlist;
import com.dbelgamembership.membersip.databinding.FragmentWishlistBinding;
import com.dbelgamembership.membersip.databinding.PopupWishlistEditingBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment implements AdapterListWishlist.AdapterListWishlistCallback {

    private final String TAG = this.getClass().getSimpleName();
    public String url = Http.server, jsonResult, type, user, pass;

    SessionManager sessionManager;

    private List<Datum> daftarPromo = new ArrayList<>();
    List<MsgServer> listDetail = new ArrayList<>();
    AdapterListWishlist adapterListSearchBarang;
    ArrayList<MsgServer> arrayBarang = new ArrayList<MsgServer>();
    List<String> arrayKategori = new ArrayList<String>();
    
    private FragmentWishlistBinding binding;

    int namaKustomer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWishlistBinding.inflate(inflater, container, false);
        sessionManager = new SessionManager(requireContext());


        if (sessionManager.isLoggedIn()) {
            namaKustomer = Integer.parseInt(sessionManager.getPID());
            Log.e(TAG, "getDataUser: " + namaKustomer);
            SearchingWishlist(String.valueOf(namaKustomer));
        }  else {
            PeringatanBelumLogin("Wishlist");
        }

        return binding.getRoot();
    }

    private void SearchingWishlist(String idCustomer) {
        final ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Loading", "Please Wait...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doDetailWishlistCustomer(idCustomer);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response.body());

                    boolean success = obj.getBoolean("success");
                    String msgServer = obj.get("msgServer").toString();

                    if (!success) {
                        binding.layoutWishlistKosong.setVisibility(View.VISIBLE);
                        binding.gridview.setVisibility(View.GONE);
                    } else {
                        binding.layoutWishlistKosong.setVisibility(View.GONE);
                        binding.gridview.setVisibility(View.VISIBLE);
                        Gson gson = new Gson();
                        ModelSearchWish modelListItem = gson.fromJson(response.body(), ModelSearchWish.class);
                        listDetail = modelListItem.getMsgServer();
                        if (listDetail.size() > 0) {
                            arrayBarang.clear();
                            binding.gridview.setAdapter(null);
                            for (MsgServer itemData : listDetail) {
                                MsgServer pm = new MsgServer();
                                pm.setIdProduk((itemData.getIdProduk()));
                                pm.setName(itemData.getName());
                                pm.setGambar(itemData.getGambar());
                                pm.setCodeProduct(String.valueOf(itemData.getCodeProduct()));
                                pm.setQty(itemData.getQty());
                                pm.setQtyStok(itemData.getQtyStok());
                                pm.setIdGudang(itemData.getIdGudang());
                                Price hargaBarang = itemData.getPrice();
                                pm.setPrice(hargaBarang);
                                arrayBarang.add(pm);
                            }

                            adapterListSearchBarang = new AdapterListWishlist(requireContext(), arrayBarang, WishlistFragment.this);
                            binding.gridview.setAdapter(null);
                            binding.gridview.setAdapter(adapterListSearchBarang);

                            NewMainActivity.binding.bottomNavView.getOrCreateBadge(R.id.wishlistFragment).setNumber(arrayBarang.size());

                        } else {
                            Toast.makeText(requireContext(), "Error Wishlist kosong 1 " , Toast.LENGTH_SHORT).show();
                            NewMainActivity.binding.bottomNavView.getOrCreateBadge(R.id.wishlistFragment).setNumber(0);
                        }

                    }

                } catch (Exception e) {
                    Log.e(TAG, "onResponse: Error " + e);
                    Toast.makeText(requireContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    NewMainActivity.binding.bottomNavView.getOrCreateBadge(R.id.wishlistFragment).setNumber(0);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(requireContext(), "Error Kosong !", Toast.LENGTH_SHORT).show();
                NewMainActivity.binding.bottomNavView.getOrCreateBadge(R.id.wishlistFragment).setNumber(0);
            }
        });

    }

    private void PeringatanBelumLogin(String from) {
        Log.e(TAG, "PeringatanBelumLogin: FROM :: " + from);
        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
        alert.setIcon(R.drawable.dbelga);
        alert.setTitle("Fitur Dikunci");
        alert.setMessage("Anda harus mempunyai akun Membership terlebih dahulu untuk mengakses fitur ini !");
        alert.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        alert.setNegativeButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(requireContext(), RegisterActivity.class);
                startActivity(intent);
                requireActivity().finish();
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


    //popUpBarang
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private PopupWishlistEditingBinding popupWishlistEditingBinding;

    @Override
    public void AdapterListDelete(MsgServer position) {
        deleteItemWishlist(position, true);
    }

    private void deleteItemWishlist(MsgServer position, boolean fromAdapter) {
        String code = String.valueOf(position.getIdProduk());
        Log.e(TAG, "ID Member : " + sessionManager.getPID());
        Log.e(TAG, "ID Barang : " + code);
        url = Http.server + "wishlist-delete/" + sessionManager.getPID();
        Log.e(TAG, "URL : " + url);
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(requireContext());
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Hapus item dari wishlist ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
               
                            JSONObject postData = new JSONObject();
                            try {
                                ArrayList<HashMap<String, String>> detail_order = new ArrayList<HashMap<String, String>>();
                                HashMap<String, String> map_order = new HashMap<String, String>();

                                map_order.put("produk", String.valueOf(position.getIdProduk()));
                                map_order.put("idGudang", String.valueOf(position.getIdGudang()));
                                detail_order.add(map_order);
                                JSONArray arrayWishlist = new JSONArray(detail_order);

                                postData.put("wishlist", arrayWishlist);

                                Log.e(TAG, "URL : " + url);
                                Log.e(TAG, "onClickSubmit: " + postData);
                                if (!fromAdapter) {
                                    alertDialog.dismiss();
                                }
                                SimpanPost(postData);
                            } catch (JSONException e) {
                                e.printStackTrace();
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
    
    @Override
    public void AdapterListTambahKeranjang(MsgServer position) {
        if (String.valueOf(position.getIdGudang()).equals( sessionManager.getKeySetGudangPencarian())) {
            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(requireContext());
            builder1.setTitle("Konfirmasi");
            builder1.setMessage("Menambah item ke keranjang ?");
            builder1.setCancelable(false);
            builder1.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    double qty = 0;
                    boolean flagQtyLebih = false;

                    if (Double.parseDouble(position.getQty()) > position.getQtyStok()) {
                        qty = position.getQtyStok();
                        flagQtyLebih = true;
                    } else {
                        qty = Double.parseDouble(position.getQty());
                        flagQtyLebih = false;
                    }

                    final ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Loading", "Please Wait...");
                    APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                    Call<String> call = apiInterface.doAddCart(sessionManager.getPID(),
                            String.valueOf(position.getIdGudang()),
                            String.valueOf(position.getIdProduk()),
                            position.getBarcodeProduct(),
                            qty);

                    boolean finalFlagQtyLebih = flagQtyLebih;
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                            progressDialog.dismiss();
                            dialogInterface.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response.body());

                                boolean success = obj.getBoolean("success");
                                String msgServer = obj.get("msgServer").toString();

                                if (success) {
                                    Gson gson = new Gson();
                                    ModelResponseCart modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);

                                    assert modelResponseCart != null;

                                    if (finalFlagQtyLebih) {
                                        Toast.makeText(requireContext(), "Barang anda melebihi stok yang ada, menambahkan ke keranjang sesuai dengan stok maksimal sistem !", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(requireContext(), "Berhasil menambahkan ke keranjang !", Toast.LENGTH_SHORT).show();
                                    }


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
                            dialogInterface.dismiss();
                            Log.e(TAG, "onFailure: " + t.getMessage());
                        }
                    });

                }
            });

            builder1.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
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
        } else {
            Toast.makeText(requireContext(), "Barang wishlist bukan dari Toko yang dipilih !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void AdapterEditListWishlist(MsgServer position) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        Log.e(TAG, "AdapterListBarangClicked: " + position.getCodeProduct());
        dialogBuilder = new AlertDialog.Builder(requireContext());

        popupWishlistEditingBinding = PopupWishlistEditingBinding.inflate(getLayoutInflater());

        final View wishlistPop = popupWishlistEditingBinding.getRoot();
        dialogBuilder.setView(wishlistPop);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        popupWishlistEditingBinding.namaBarang.setText(position.getName());
        Glide.with(requireContext()).load(position.getGambar()).error(R.drawable.not_found).into(popupWishlistEditingBinding.imageBarang);
        double qtyPesan = Double.parseDouble(position.getQty());
        double stokMaksimal = position.getQtyStok();

        popupWishlistEditingBinding.qty.setText(String.valueOf(qtyPesan));

        String hargaFix = "0";

        int batasan1 = (int) position.getPrice().getQtyHarga1();
        int batasan2 = (int) position.getPrice().getQtyHarga2();
        int batasan3 = (int) position.getPrice().getQtyHarga3();

        if (batasan1 == batasan2) {
            hargaFix = position.getPrice().getHarga();
        } else {
            if (qtyPesan < batasan2) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga1());
                hargaFix = position.getPrice().getHarga();
            } else if (qtyPesan >= batasan2 && qtyPesan < batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga2());
                hargaFix = position.getPrice().getHargaDua();
            } else if (qtyPesan >= batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga3());
                hargaFix = position.getPrice().getHargaTiga();
            }
        }

        int diskon = (int) (Double.parseDouble(position.getPrice().getHarga()) - Double.parseDouble(hargaFix));

        if (diskon > 0) {
            popupWishlistEditingBinding.hargaRealBarang.setVisibility(View.VISIBLE);
            popupWishlistEditingBinding.hargaRealBarang.setText("Rp. " + nf.format(Double.parseDouble(position.getPrice().getHarga())));
            popupWishlistEditingBinding.hargaBarang.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
        } else {
            popupWishlistEditingBinding.hargaRealBarang.setVisibility(View.GONE);
            popupWishlistEditingBinding.hargaRealBarang.setText("0");
            popupWishlistEditingBinding.hargaBarang.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
        }

        popupWishlistEditingBinding.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double qty = Double.parseDouble(popupWishlistEditingBinding.qty.getText().toString());
                popupWishlistEditingBinding.qty.setText(String.valueOf(qty + 1));
            }
        });

        popupWishlistEditingBinding.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (popupWishlistEditingBinding.qty.getText().toString().equals("1")) {
                    deleteItemWishlist(position, false);
                } else {
                    double qty = Double.parseDouble(popupWishlistEditingBinding.qty.getText().toString());
                    popupWishlistEditingBinding.qty.setText(String.valueOf(qty - 1));
                }
            }
        });


        popupWishlistEditingBinding.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000; // milliseconds

            @SuppressLint("NewApi")
            @Override
            public void afterTextChanged(Editable editable) {

                double qty = Double.parseDouble(popupWishlistEditingBinding.qty.getText().toString());

                Log.e(TAG, "afterTextChanged: " + qty);

                if (qty >= stokMaksimal) {
                    popupWishlistEditingBinding.increment.setEnabled(false);
                    popupWishlistEditingBinding.increment.setTextColor(requireContext().getColor(R.color.greyBelha));
                } else {
                    popupWishlistEditingBinding.increment.setEnabled(true);
                    popupWishlistEditingBinding.increment.setTextColor(requireContext().getColor(R.color.black));
                }


                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                ((Activity) requireContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String hargaFix = "0";

                                        int batasan1 = (int) position.getPrice().getQtyHarga1();
                                        int batasan2 = (int) position.getPrice().getQtyHarga2();
                                        int batasan3 = (int) position.getPrice().getQtyHarga3();

                                        Log.e(TAG, "run: BATASAN 1 " + batasan1);
                                        Log.e(TAG, "run: BATASAN 2 " + batasan2);
                                        Log.e(TAG, "run: BATASAN 3 " + batasan3);
                                        Log.e(TAG, "run: QTY " + qty);


                                        if (batasan1 == batasan2) {
                                            hargaFix = position.getPrice().getHarga();
                                        } else {
                                            if (qty < batasan2) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga1());
                                                hargaFix = position.getPrice().getHarga();
                                            } else if (qty >= batasan2 && qty < batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga2());
                                                hargaFix = position.getPrice().getHargaDua();
                                            } else if (qty >= batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga3());
                                                hargaFix = position.getPrice().getHargaTiga();
                                            }
                                        }

                                        int diskon = (int) (Double.parseDouble(position.getPrice().getHarga()) - Double.parseDouble(hargaFix));

                                        if (diskon > 0) {
                                            popupWishlistEditingBinding.hargaRealBarang.setVisibility(View.VISIBLE);
                                            popupWishlistEditingBinding.hargaRealBarang.setText("Rp. " + nf.format(Double.parseDouble(position.getPrice().getHarga())));
                                            popupWishlistEditingBinding.hargaBarang.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
                                        } else {
                                            popupWishlistEditingBinding.hargaRealBarang.setVisibility(View.GONE);
                                            popupWishlistEditingBinding.hargaRealBarang.setText("0");
                                            popupWishlistEditingBinding.hargaBarang.setText("Rp. " + nf.format(Double.parseDouble(hargaFix)));
                                        }
                                    }
                                });


                            }
                        },
                        DELAY
                );


            }
        });


        popupWishlistEditingBinding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        popupWishlistEditingBinding.btnHapusWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemWishlist(position, false);
            }
        });

        popupWishlistEditingBinding.btnUpdateWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItemWishilist(String.valueOf(position.getIdProduk()), popupWishlistEditingBinding.qty.getText().toString(), position.getIdGudang());
            }
        });
    }

    private void updateItemWishilist(String code, String stokBarang, int idGudang) {
        Log.e(TAG, "ID Member : " + sessionManager.getPID());
        Log.e(TAG, "ID Barang : " + code);
        url = Http.server + "wishlist-update/" + sessionManager.getPID();
        Log.e(TAG, "URL : " + url);
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(requireContext());
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Update item dari wishlist ?");
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
                        SimpanPostUpdate(postData);
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


    private void SimpanPost(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(requireContext());
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
        Log.e(TAG, "postData: " + postData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog1.dismiss();
                            Log.e(TAG, "Response : " + response);
                            Toast.makeText(requireContext(), "Barang berhasil dihapus dari wishlist !", Toast.LENGTH_SHORT).show();
                            SearchingWishlist(String.valueOf(namaKustomer));
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
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
                    Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(requireContext(), "Tidak ada koneksi internet !", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
            protected com.android.volley.Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void SimpanPostUpdate(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(requireContext());
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());
        Log.e(TAG, "postData: " + postData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog1.dismiss();

                            Log.e(TAG, "Response : " + response);
                            Gson gson = new Gson();
                            ResponseWishlist responseWishlist = gson.fromJson(String.valueOf(response), ResponseWishlist.class);

                            boolean responseBool = responseWishlist.getSuccess();

                            if (responseWishlist.getSuccess()) {
                                Log.e(TAG, "onResponse: " + responseBool);
                                Toast.makeText(requireContext(), "berhasil update wishlist !", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Tidak berhasil create wishlist !", Toast.LENGTH_SHORT).show();
                            }

                            SearchingWishlist(sessionManager.getPID());

                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);
                dialog1.dismiss();
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(requireContext(), WishlishActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (error instanceof ServerError) {
                    Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(requireContext(), "Tidak ada koneksi internet !", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
            protected com.android.volley.Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
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