package com.dbelgamembership.membersip.Screen.Katalog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dbelgamembership.membersip.app.Adapter.AdapterListCart;
import com.dbelgamembership.membersip.app.Adapter.AdapterListCheckout;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelResponseCart.DetailItemCart;
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.modelTransaksiStore.ModelTransaksiStore;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ActivityCartBinding;
import com.dbelgamembership.membersip.databinding.PopupCheckoutBinding;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.jarak;

public class CartActivity extends AppCompatActivity implements AdapterListCart.AdapterListGudangCallback, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "CartActivity";
    private ActivityCartBinding binding;
    private SessionManager sessionManager;
    private AdapterListCart adapterListCart;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    int Year, Month, Day, Hour, Minute;

    private List<DetailItemCart> listItem = new ArrayList<>();

    private double jarakKm = jarak;
    private int hitungJarak = 0;
    private int grandTotal = 0;
    private String idGudang = "0";

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sessionManager = new SessionManager(this);


        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (jarakKm < 15) {
            hitungJarak = (int) (Math.round(jarakKm) * 2000);
        } else {
            hitungJarak = (int) (Math.round(jarakKm) * 2500);
        }

        binding.txtJarak.setText("± " + String.valueOf(jarakKm) + " Km");
        binding.txtOngkir.setText("Rp. " + nf.format(hitungJarak));

        searchingCart();

        Log.e(TAG, "onCreate: NOMOR TELFON" + sessionManager.getKeyTelefonMember());
        Log.e(TAG, "onCreate: ALAMAT MEMBER : " + sessionManager.getKeyAlamatMember());
        Log.e(TAG, "onCreate ALAMAT PENGIRIMAN : " + sessionManager.getKeyAlamatPengiriman());

        binding.imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyCart();
            }
        });

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkoutPop();
            }
        });

    }

    private PopupCheckoutBinding checkoutBinding;

    private void checkoutPop() {
        checkoutBinding = PopupCheckoutBinding.inflate(getLayoutInflater());
        final View view = checkoutBinding.getRoot();

        SimpleDateFormat af = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 1);

        Year = cal.get(Calendar.YEAR);
        Month = cal.get(Calendar.MONTH);
        Day = cal.get(Calendar.DAY_OF_MONTH);
        Hour = cal.get(Calendar.HOUR_OF_DAY);
        Minute = cal.get(Calendar.MINUTE);


        checkoutBinding.edTanggalPengiriman.setText(af.format(cal.getTime()));

        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

        dialogBuilder = new AlertDialog.Builder(this);


        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        checkoutBinding.rvItemCheckout.setAdapter(null);

        AdapterListCheckout adapterListCheckout = new AdapterListCheckout(CartActivity.this, listItem);
        checkoutBinding.rvItemCheckout.setAdapter(adapterListCheckout);

        checkoutBinding.txtGrandTotal.setText("Rp. " + nf.format(grandTotal));

        checkoutBinding.produkClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        checkoutBinding.edTanggalPengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Setting Min Date to today date
                Calendar min_date_c = Calendar.getInstance();

                datePickerDialog = DatePickerDialog.newInstance(CartActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setTitle("Pilih Tanggal Pengiriman");

                datePickerDialog.setMinDate(min_date_c);

                // Setting Max Date to next 2 years
                Calendar max_date_c = Calendar.getInstance();
                max_date_c.set(Calendar.YEAR, Year + 2);
                datePickerDialog.setMaxDate(max_date_c);
                SimpleDateFormat tanggalLoop = new SimpleDateFormat("yyyy-MM-dd");

                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(CartActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                        checkoutBinding.edTanggalPengiriman.setText(af.format(cal.getTime()));
                    }
                });

                datePickerDialog.show(getSupportFragmentManager(), "Date Picker");

            }
        });


//        visiting[0].visiting_img

        checkoutBinding.layoutBtnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();

                try {

                    int totalDiskonSO = 0;
                    ArrayList<HashMap<String, String>> detail_order = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < listItem.size(); i++) {
                        HashMap<String, String> map_order = new HashMap<String, String>();

                        DetailItemCart item = listItem.get(i);

                        int batasan1 = item.getHarga().getQtyHarga1();
                        int batasan2 = item.getHarga().getQtyHarga2();
                        int batasan3 = item.getHarga().getQtyHarga3();

                        String harga1 = item.getHarga().getHarga();
                        String harga2 = item.getHarga().getQtyHarga2() == null ? "0" : item.getHarga().getHargaDua();
                        String harga3 = item.getHarga().getQtyHarga3() == null ? "0" : item.getHarga().getHargaTiga();

                        int qty = item.getQty();

                        int jumlahBarangDibeli = qty;
                        String hargaFix = "0";

                        if (batasan1 == batasan2) {
//                            Log.e(TAG, "TambahkanKeListBarang: " + pm.getHarga_barang());
                            hargaFix = harga1;
                        } else {
                            if (jumlahBarangDibeli < batasan2) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga1());
                                hargaFix = harga1;
                            } else if (jumlahBarangDibeli >= batasan2 && jumlahBarangDibeli < batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga2());
                                hargaFix = harga2;
                            } else if (jumlahBarangDibeli >= batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga3());
                                hargaFix = harga3;
                            }
                        }

                        int diskon = (int) (Double.parseDouble(harga1) - Double.parseDouble(hargaFix));

                        map_order.put("code", item.getCode());
                        map_order.put("price", hargaFix);
                        map_order.put("real_price", harga1);


                        int totalDiskon = (int) (qty * diskon);
                        int totalNormal = (int) (Double.parseDouble(harga1) * qty);

                        map_order.put("qty_store", "0");
                        map_order.put("qty_outlet", String.valueOf(item.getQty()));
                        map_order.put("indent", "false");
                        map_order.put("indent_value", "0");
                        map_order.put("persentase_diskon", "0");
                        map_order.put("potongan_diskon", String.valueOf(diskon));
                        map_order.put("total_diskon", String.valueOf(totalDiskon));
                        map_order.put("total_normal", String.valueOf(totalNormal));
                        map_order.put("total_setelah_diskon", String.valueOf(totalNormal - totalDiskon));
                        detail_order.add(map_order);
                        totalDiskonSO += totalDiskon;
                    }

                    jsonObject.put("createuser", "0");
                    jsonObject.put("id_gudang", idGudang);

                    jsonObject.put("customer", sessionManager.getName());
                    jsonObject.put("alamat_customer", sessionManager.getKeyAlamatMember());
                    jsonObject.put("id_spv", "");
                    jsonObject.put("id_member", sessionManager.getPID());
                    jsonObject.put("alamat_pengiriman", sessionManager.getKeyAlamatPengiriman());
                    jsonObject.put("flagKirim", true);
                    jsonObject.put("online", true);
                    jsonObject.put("identitas_customer", sessionManager.getPID());
                    jsonObject.put("nomor_customer", sessionManager.getKeyTelefonMember());
                    jsonObject.put("ongkos_kirim", String.valueOf(hitungJarak));
                    jsonObject.put("tanggal_kirim", checkoutBinding.edTanggalPengiriman.getText().toString());
                    jsonObject.put("grandtotal", String.valueOf(grandTotal));
                    jsonObject.put("total_diskon_so", String.valueOf(totalDiskonSO));

                    JSONArray array_order = new JSONArray(detail_order);

                    if (detail_order.size() > 0) {
                        jsonObject.put("detail", array_order);
                    } else {
                        jsonObject.put("detail", "");
                    }


                    Log.e(TAG, "POST DATA: " + jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ERROR CREATE DATA : " + e.getLocalizedMessage());
                }

                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(CartActivity.this);
                builder1.setTitle("Konfirmasi");
                builder1.setMessage("Anda yakin checkout dengan pesanan anda ?");
                builder1.setCancelable(false);
                builder1.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        sendingData(jsonObject);


                    }
                });
                builder1.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
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

    private void sendingData(JSONObject postData) {

        String url = Http.server;
        url = url + "transaction/store";

        Log.e(TAG, "sendingData URL :  " + url);
        Log.e(TAG, "sendingData DATA :  " + postData);

        final ProgressDialog progressDialog = ProgressDialog.show(CartActivity.this, "Creating Order", "Please Wait...");

        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();
                    if (response != null) {
                        Gson gson = new Gson();
                        ModelTransaksiStore object = gson.fromJson(String.valueOf(response), ModelTransaksiStore.class);
                        Log.e(TAG, "onResponseSimpan: " + response);
                        Log.e(TAG, "onResponse: " + object.getStatus().getDescription());
                        if (!object.getStatus().getDescription().equals("Order success.")) {
                            Toast.makeText(CartActivity.this, object.getStatus().getDescription(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CartActivity.this, "Order Berhasil !", Toast.LENGTH_SHORT).show();
                            APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                            Call<String> call = apiInterface.doEmptyCart(sessionManager.getPID());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(CartActivity.this, "GAGAL !", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                }
                            });
                        }
                    } else {
                        Toast.makeText(CartActivity.this, "GAGAL !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CartActivity.this, "GAGAL !", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse SENDING : " + e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("onResponse : ", error.getMessage(), error);
                if (error instanceof TimeoutError) {
                    AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage(error.getMessage() + ", coba lagi ?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendingData(postData);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "TIDAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        alertDialog.dismiss();


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);

    }

    private void searchingCart() {
        final ProgressDialog progressDialog = ProgressDialog.show(CartActivity.this, "Loading", "Please Wait...");
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

                    binding.rvItemCart.setAdapter(null);

                    listItem.clear();

                    if (success) {

                        Gson gson = new Gson();
                        ModelResponseCart modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);

                        assert modelResponseCart != null;

                        idGudang = String.valueOf(modelResponseCart.getMsgServer().getIdGudang());
                        adapterListCart = new AdapterListCart(CartActivity.this, modelResponseCart.getMsgServer().getDetailItemCart(), CartActivity.this);
                        binding.rvItemCart.setAdapter(adapterListCart);
                        hitungHitung(modelResponseCart);

                        listItem.addAll(modelResponseCart.getMsgServer().getDetailItemCart());

                    } else {
                        Toast.makeText(CartActivity.this, msgServer, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + msgServer);

                        if (msgServer.equals("Data item pada cart kosong")) {
                            binding.rvItemCart.setAdapter(null);
                            binding.txtTotalBelanja.setText("Rp. 0");
                            binding.txtTotalQty.setText("-");
                            binding.txtGrandTotal.setText("Rp. 0");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void updateCart() {
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


                    binding.rvItemCart.setAdapter(null);

                    listItem.clear();

                    if (success) {


                        Gson gson = new Gson();
                        ModelResponseCart modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);

                        assert modelResponseCart != null;

                        idGudang = String.valueOf(modelResponseCart.getMsgServer().getIdGudang());
                        adapterListCart = new AdapterListCart(CartActivity.this, modelResponseCart.getMsgServer().getDetailItemCart(), CartActivity.this);
                        binding.rvItemCart.setAdapter(adapterListCart);

                        hitungHitung(modelResponseCart);

                        listItem.addAll(modelResponseCart.getMsgServer().getDetailItemCart());

                    } else {
                        Toast.makeText(CartActivity.this, msgServer, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + msgServer);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void hitungHitung(ModelResponseCart modelResponseCart) {

        int totalItem = modelResponseCart.getMsgServer().getTotalItem();
        int totalQty = modelResponseCart.getMsgServer().getTotalQty();
        double totalBeli = 0;

        for (int i = 0; i < modelResponseCart.getMsgServer().getDetailItemCart().size(); i++) {
            DetailItemCart detailItemCart = modelResponseCart.getMsgServer().getDetailItemCart().get(i);
            int batasan1 = detailItemCart.getHarga().getQtyHarga1();
            int batasan2 = detailItemCart.getHarga().getQtyHarga2();
            int batasan3 = detailItemCart.getHarga().getQtyHarga3();

            String harga1 = detailItemCart.getHarga().getHarga();
            String harga2 = detailItemCart.getHarga().getQtyHarga2() == null ? "0" : detailItemCart.getHarga().getHargaDua();
            String harga3 = detailItemCart.getHarga().getQtyHarga3() == null ? "0" : detailItemCart.getHarga().getHargaTiga();


            int jumlahBarangDibeli = detailItemCart.getQty();
            String hargaFix = "0";

            if (batasan1 == batasan2) {
//                            Log.e(TAG, "TambahkanKeListBarang: " + pm.getHarga_barang());
                hargaFix = harga1;
            } else {
                if (jumlahBarangDibeli < batasan2) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga1());
                    hargaFix = harga1;
                } else if (jumlahBarangDibeli >= batasan2 && jumlahBarangDibeli < batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga2());
                    hargaFix = harga2;
                } else if (jumlahBarangDibeli >= batasan3) {
//                            mapArray.setPrice(itemBarangList.get(i).getHarga3());
                    hargaFix = harga3;
                }
            }

//            int diskon = (int) (Double.parseDouble(harga1) - Double.parseDouble(hargaFix));

            totalBeli += Double.parseDouble(hargaFix) * jumlahBarangDibeli;
        }

        binding.txtTotalQty.setText(String.valueOf(totalQty));
        binding.txtTotalBelanja.setText("Rp. " + nf.format(totalBeli));

        grandTotal = (int) (totalBeli + hitungJarak);

        binding.txtGrandTotal.setText("Rp. " + nf.format(grandTotal) + " ( " + totalItem + " ) Item");

    }


    @Override
    public void deleteBarang(DetailItemCart detailItemCart) {
//        Toast.makeText(this, "Delete : " + detailItemCart.getNamaProduk(), Toast.LENGTH_SHORT).show();
        AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create();
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Anda akan menghapus barang ini dari keranjang anda ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        final ProgressDialog progressDialog = ProgressDialog.show(CartActivity.this, "Loading", "Please Wait...");
                        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                        Call<String> call = apiInterface.doDeleteCart(sessionManager.getPID(),
                                idGudang,
                                String.valueOf(detailItemCart.getProduk()),
                                detailItemCart.getBarcode());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                                progressDialog.dismiss();

                                try {
                                    JSONObject obj = new JSONObject(response.body());
                                    boolean success = obj.getBoolean("success");
                                    String msgServer = obj.get("msgServer").toString();

                                    listItem.clear();

                                    if (success) {

                                        updateCart();

                                    } else {
                                        Toast.makeText(CartActivity.this, msgServer, Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "onResponse: " + msgServer);

                                        if (msgServer.equals("Data item pada cart kosong")) {
                                            binding.rvItemCart.setAdapter(null);

                                            if (msgServer.equals("Data item pada cart kosong")) {
                                                binding.rvItemCart.setAdapter(null);
                                                binding.txtTotalBelanja.setText("Rp. 0");
                                                binding.txtTotalQty.setText("-");
                                                binding.txtGrandTotal.setText("Rp. 0");
                                            }
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
//                                progressDialog.dismiss();
                                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    @Override
    public void updateQtyBarang(DetailItemCart detailItemCart, int qtyItem) {
//        final ProgressDialog progressDialog = ProgressDialog.show(CartActivity.this, "Loading", "Updating data ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doAddCart(sessionManager.getPID(),
                idGudang,
                String.valueOf(detailItemCart.getProduk()),
                detailItemCart.getBarcode(),
                qtyItem);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                progressDialog.dismiss();

                try {
                    JSONObject obj = new JSONObject(response.body());
                    boolean success = obj.getBoolean("success");
                    String msgServer = obj.get("msgServer").toString();

                    listItem.clear();

                    if (success) {

                        updateCart();

                    } else {
                        Toast.makeText(CartActivity.this, msgServer, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + msgServer);

                        if (msgServer.equals("Data item pada cart kosong")) {
                            binding.rvItemCart.setAdapter(null);
                            binding.txtTotalBelanja.setText("Rp. 0");
                            binding.txtTotalQty.setText("-");
                            binding.txtGrandTotal.setText("Rp. 0");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void emptyCart() {
        //        Toast.makeText(this, "Delete : " + detailItemCart.getNamaProduk(), Toast.LENGTH_SHORT).show();
        AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create();
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Anda akan menghapus keranjang anda ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = ProgressDialog.show(CartActivity.this, "Loading", "Deleting data ...");
                        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                        Call<String> call = apiInterface.doEmptyCart(sessionManager.getPID());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                progressDialog.dismiss();

                                try {
                                    JSONObject obj = new JSONObject(response.body());
                                    boolean success = obj.getBoolean("success");
                                    String msgServer = obj.get("msgServer").toString();

                                    if (success) {

                                        updateCart();

                                    } else {
                                        Toast.makeText(CartActivity.this, msgServer, Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "onResponse: " + msgServer);

                                        if (msgServer.equals("Data item pada cart kosong")) {
                                            binding.rvItemCart.setAdapter(null);
                                            binding.txtTotalBelanja.setText("Rp. 0");
                                            binding.txtTotalQty.setText("-");
                                            binding.txtGrandTotal.setText("Rp. 0");
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        Log.e(TAG, "BULAN : " + month);
        String formattedMonth = "" + month;
        String formattedDayOfMonth = "" + dayOfMonth;
        if (month < 10) {
            formattedMonth = "0" + month;
        }
        if (dayOfMonth < 10) {
            formattedDayOfMonth = "0" + dayOfMonth;
        }
        checkoutBinding.edTanggalPengiriman.setText(year + "-" + formattedMonth + "-" + formattedDayOfMonth);

        openDialogTime();

    }

    private void openDialogTime() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(CartActivity.this,
                mHour,
                mMinute,
                true);

        timePickerDialog.setTitle("Pilih Waktu");
        timePickerDialog.show(getSupportFragmentManager(), "TimePicker");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Log.e(TAG, "String tanggal : " + checkoutBinding.edTanggalPengiriman.getText().toString());
        String waktuPengiriman = checkoutBinding.edTanggalPengiriman.getText().toString();

//        Toast.makeText(this, String.format("Waktu yang anda pilih : %02d:%02d:%02d", hourOfDay, minute, second), Toast.LENGTH_SHORT).show();
        waktuPengiriman = waktuPengiriman + String.format(" %02d:%02d:%02d", hourOfDay, minute, second);
        Log.e(TAG, "onTimeSet: " + waktuPengiriman);
        checkoutBinding.edTanggalPengiriman.setText(waktuPengiriman);
    }
}