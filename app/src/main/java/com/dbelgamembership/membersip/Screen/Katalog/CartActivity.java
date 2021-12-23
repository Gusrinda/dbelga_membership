package com.dbelgamembership.membersip.Screen.Katalog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dbelgamembership.membersip.Model.ModelResponseDistance.ModelResponseDistance;
import com.dbelgamembership.membersip.Model.ModelToko.ModelGudang;
import com.dbelgamembership.membersip.Model.ModelToko.ModelToko;
import com.dbelgamembership.membersip.Model.ModelToko.MsgServer;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Screen.Maps.MapsActivity;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintActivity;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.KonfirmasiMembership;
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
import com.dbelgamembership.membersip.app.Adapter.AdapterListGudang;
import com.dbelgamembership.membersip.databinding.ActivityCartBinding;
import com.dbelgamembership.membersip.databinding.PopupCheckoutBinding;
import com.dbelgamembership.membersip.databinding.PopupMetodePembayaranBinding;
import com.dbelgamembership.membersip.databinding.PopupPilihPembayaranBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.alamatPengirimanPengguna;
import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.jarak;
import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

public class CartActivity extends AppCompatActivity implements AdapterListCart.AdapterListGudangCallback, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "CartActivity";
    private ActivityCartBinding binding;
    private SessionManager sessionManager;
    private AdapterListCart adapterListCart;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    ModelResponseCart modelResponseCart;

    int Year, Month, Day, Hour, Minute;

    private List<DetailItemCart> listItem = new ArrayList<>();

    private double jarakKm = jarak;
    private double hitungJarak = 0;
    private int grandTotal = 0;
    private String idGudang = "0";

    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

    private boolean isPaymentSelected = false;
    private boolean isCOD = true;
    private boolean isDebit = false;
    private boolean IS_MEMBER_DEBIT = false;
    Bitmap bitmap;
    double limitPlafon, sisaPlafon, piutangBelanja;
    private String imageString = "";
    private Uri ImageUri;

    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sessionManager = new SessionManager(this);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        idGudang = sessionManager.getKeySetGudangPencarian();

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (jarakKm < 10) {
            hitungJarak = 0;
        } else {
            hitungJarak = (Math.round(jarakKm - 10) * 2500);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        String dx = df.format(hitungJarak);
        hitungJarak = Double.parseDouble(dx);

        binding.txtJarak.setText("± " + String.valueOf(Math.round(jarakKm)) + " Km");

        if (hitungJarak == 0) {
            binding.txtOngkir.setText("FREE ( < 10 Km )");
        } else {
            binding.txtOngkir.setText("Rp. " + nf.format(hitungJarak));
        }

        cekDataUser();
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

        if (isPaymentSelected) {
            binding.btnCheckout.setEnabled(true);
            binding.btnCheckout.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        } else {
            binding.btnCheckout.setEnabled(false);
            binding.btnCheckout.setBackgroundColor(getResources().getColor(R.color.greyBelha));
        }

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkoutPop();
            }
        });

        binding.btnAlamatPengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MapsActivity.class);
                intent.putExtra("hasLocation", true);
                LatLng latLongBaru = new LatLng(Double.parseDouble(sessionManager.getKeyLat()), Double.parseDouble(sessionManager.getKeyLong()));
                intent.putExtra("location", latLongBaru);
                startActivityForResult(intent, 1);
            }
        });

        binding.btnMetodePembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popOutMetodePembayaran();
            }
        });

    }

    private void cekDataUser() {
        String url = Http.server + "search-customer/" + sessionManager.getPID();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelUser> callUser = apiInterface.doLoopCustomer(url);
        callUser.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, retrofit2.Response<ModelUser> response) {
                ModelUser object = response.body();
                com.dbelgamembership.membersip.Model.ModelUser.MsgServer dataUser = object.getMsgServer().get(0);

                if (dataUser.getStatusMember().equals("SILVER")) {
                    IS_MEMBER_DEBIT = false;
                } else {
                    IS_MEMBER_DEBIT = true;

                    limitPlafon = (dataUser.getCreditLimit() == null ? 0 : Double.parseDouble(dataUser.getCreditLimit()));
                    sisaPlafon = dataUser.getSisaCreditLimit();
                    piutangBelanja = Double.parseDouble(dataUser.getGrandTotalSo());

                    Log.e(TAG, "onResponse LP: " + limitPlafon);
                    Log.e(TAG, "onResponse SP: " + sisaPlafon);
                    Log.e(TAG, "onResponse PB: " + piutangBelanja);

                }
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    private PopupPilihPembayaranBinding popupPilihPembayaranBinding;

    private void popOutMetodePembayaran() {
        popupPilihPembayaranBinding = PopupPilihPembayaranBinding.inflate(getLayoutInflater());
        final View view = popupPilihPembayaranBinding.getRoot();

        dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        if (IS_MEMBER_DEBIT) {
            popupPilihPembayaranBinding.radioDebitMember.setVisibility(View.VISIBLE);
        } else {
            popupPilihPembayaranBinding.radioDebitMember.setVisibility(View.GONE);
        }

        if (isPaymentSelected) {
            if (isCOD) {
                popupPilihPembayaranBinding.radioCOD.setChecked(true);
            } else {
                if (isDebit) {
                    popupPilihPembayaranBinding.radioDebitMember.setChecked(true);
                } else {
                    popupPilihPembayaranBinding.radioTransfer.setChecked(true);
                }
            }
        }

        popupPilihPembayaranBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == popupPilihPembayaranBinding.radioCOD.getId()) {
                    isCOD = true;
                    isDebit = false;
                    bitmap = null;
                    binding.txtMetode.setText("COD");
                    popupPilihPembayaranBinding.layoutCOD.setVisibility(View.VISIBLE);
                    popupPilihPembayaranBinding.layoutTransfer.setVisibility(View.GONE);
                    popupPilihPembayaranBinding.layoutDebit.setVisibility(View.GONE);
                } else if (i == popupPilihPembayaranBinding.radioTransfer.getId()) {
                    isCOD = false;
                    isDebit = false;
                    bitmap = null;
                    binding.txtMetode.setText("TRANSFER");
                    popupPilihPembayaranBinding.layoutCOD.setVisibility(View.GONE);
                    popupPilihPembayaranBinding.layoutTransfer.setVisibility(View.VISIBLE);
                    popupPilihPembayaranBinding.layoutDebit.setVisibility(View.GONE);
                } else if (i == popupPilihPembayaranBinding.radioDebitMember.getId()) {
                    isCOD = false;
                    isDebit = true;
                    bitmap = null;
                    binding.txtMetode.setText("DEBIT ( sisa : Rp. " + nf.format(sisaPlafon) + " )");
                    popupPilihPembayaranBinding.layoutCOD.setVisibility(View.GONE);
                    popupPilihPembayaranBinding.layoutTransfer.setVisibility(View.GONE);
                    popupPilihPembayaranBinding.layoutDebit.setVisibility(View.VISIBLE);
                }
                isPaymentSelected = true;
                setupBtnCheckout();
            }
        });

        popupPilihPembayaranBinding.layoutBtnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCOD) {
                    alertDialog.dismiss();
                    Toast.makeText(CartActivity.this, "Anda memilih metode COD !", Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.dismiss();
                    if (isDebit) {
                        Toast.makeText(CartActivity.this, "Anda memilih metode DEBIT !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CartActivity.this, "Anda memilih metode TRANSFER !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        popupPilihPembayaranBinding.produkClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void setupBtnCheckout() {

        if (isPaymentSelected) {
            binding.btnCheckout.setEnabled(true);
            binding.btnCheckout.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        } else {
            binding.btnCheckout.setEnabled(false);
            binding.btnCheckout.setBackgroundColor(getResources().getColor(R.color.greyBelha));
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP);
    }

    private PopupCheckoutBinding checkoutBinding;
    private boolean isUploadBuktiTransfer = false;


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

        if (isCOD) {
            checkoutBinding.txtMetodePembayaran.setText("COD");
            checkoutBinding.btnUploadBuktiTransfer.setVisibility(View.GONE);
            isUploadBuktiTransfer = true;
        } else {
            if (isDebit) {
                checkoutBinding.txtMetodePembayaran.setText("DEBIT");
                checkoutBinding.btnUploadBuktiTransfer.setVisibility(View.GONE);
                isUploadBuktiTransfer = true;
            } else {
                checkoutBinding.txtMetodePembayaran.setText("TRANSFER\nBCA - 0110-01-013660-53-7");
                checkoutBinding.txtMetodePembayaran.setTextSize(14);
                checkoutBinding.btnUploadBuktiTransfer.setVisibility(View.VISIBLE);
                isUploadBuktiTransfer = false;

                checkoutBinding.txtMetodePembayaran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipData clipData = ClipData.newPlainText("text", "011001013660537" );
                        clipboardManager.setPrimaryClip(clipData);

                        Toast.makeText(CartActivity.this, "Berhasil copy nomor rekening !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }





        if (isUploadBuktiTransfer) {
            checkoutBinding.layoutBtnCheckout.setEnabled(true);
            checkoutBinding.layoutBtnCheckout.setBackgroundColor(getResources().getColor(R.color.biruBelga));
        } else {
            checkoutBinding.layoutBtnCheckout.setEnabled(false);
            checkoutBinding.layoutBtnCheckout.setBackgroundColor(getResources().getColor(R.color.greyBelha));
        }

        if (isDebit) {
            if (grandTotal > sisaPlafon) {
                Toast.makeText(CartActivity.this, "Belanjaan melebihi sisa plafon Anda !", Toast.LENGTH_SHORT).show();
                checkoutBinding.layoutBtnCheckout.setEnabled(false);
                checkoutBinding.layoutBtnCheckout.setBackgroundColor(getResources().getColor(R.color.greyBelha));
            } else {
                checkoutBinding.layoutBtnCheckout.setEnabled(true);
                checkoutBinding.layoutBtnCheckout.setBackgroundColor(getResources().getColor(R.color.biruBelga));
            }
        }

        checkoutBinding.btnUploadBuktiTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(CartActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(100);
            }
        });

        AdapterListCheckout adapterListCheckout = new AdapterListCheckout(CartActivity.this, listItem);
        checkoutBinding.rvItemCheckout.setAdapter(adapterListCheckout);

        checkoutBinding.txtGrandTotal.setText("Rp. " + nf.format(grandTotal));

        checkoutBinding.txtGrandTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clipData = ClipData.newPlainText("text", String.valueOf(grandTotal) );
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(CartActivity.this, "Berhasil copy total belanja !", Toast.LENGTH_SHORT).show();
            }
        });

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

                        double qty = Double.parseDouble(item.getQty());

                        double jumlahBarangDibeli = qty;
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
                        map_order.put("id", String.valueOf(item.getId()));
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
                    jsonObject.put("identitas_customer", sessionManager.getKeyUseridentitas());
//                    jsonObject.put("identitas_customer", sessionManager.getPID());
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
                        if (isUploadBuktiTransfer) {
                            sendingData(jsonObject);
                        } else {
                            Toast.makeText(CartActivity.this, "Anda harus upload bukti transfer terlebih dahulu !", Toast.LENGTH_SHORT).show();
                        }

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

                            JSONObject postData = new JSONObject();
                            try {
                                ArrayList<HashMap<String, String>> detail_order = new ArrayList<HashMap<String, String>>();

                                for (int i = 0; i < listItem.size(); i++) {
                                    HashMap<String, String> map_order = new HashMap<String, String>();
                                    map_order.put("produk", String.valueOf(listItem.get(i).getProduk()));
                                    map_order.put("idGudang", idGudang);
                                    detail_order.add(map_order);
                                }

                                JSONArray arrayWishlist = new JSONArray(detail_order);

                                postData.put("wishlist", arrayWishlist);

                                String url = Http.server + "wishlist-delete/" + sessionManager.getPID();
                                RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
                                Log.e(TAG, "postData : " + postData);
                                Log.e(TAG, "url : " + url);
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.e(TAG, "onResponse wishlist-delete : " + response);
                                                try {
                                                    APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                                                    Call<String> call = apiInterface.doEmptyCart(sessionManager.getPID());
                                                    call.enqueue(new Callback<String>() {
                                                        @Override
                                                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                                            prosesPemilihanPayment(object.getData().getSoCode(), (isCOD ? "COD" : (isDebit ? "DEBIT" : "TRANSFER")), imageString);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<String> call, Throwable t) {
                                                            alertDialog.dismiss();
                                                            Toast.makeText(CartActivity.this, "GAGAL !" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            Log.e(TAG, "onFailure: " + t.getMessage());
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    Log.e(TAG, "onResponse: " + e.getMessage());
                                                    Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("onResponse", error.getMessage(), error);
                                        if (error instanceof AuthFailureError) {
                                            sessionManager.destroySession();
                                            Intent intent = new Intent(getApplicationContext(), KatalogActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else if (error instanceof ServerError) {
                                            Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        } else if (error instanceof NetworkError) {
                                            Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        } else if (error instanceof ParseError) {
                                            Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } else {
                        Toast.makeText(CartActivity.this, "GAGAL ! " + response.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    alertDialog.dismiss();
                    Toast.makeText(CartActivity.this, "GAGAL ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);

    }

    private void prosesPemilihanPayment(String kodeSo, String tipePayment, String imageString) {

        final ProgressDialog progressDialog = ProgressDialog.show(CartActivity.this, "Loading", "Setting Up Payment ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doSetPayment(kodeSo, tipePayment, imageString);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {

                    Log.e(TAG, "onResponse: " + response.toString());

                    if (response != null) {
                        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                        Call<String> callUpdate = apiInterface.doUpdateSO(kodeSo, "confirmation");

                        callUpdate.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                progressDialog.dismiss();
                                Log.e(TAG, "onResponse: " + response.toString());
                                Toast.makeText(CartActivity.this, "Order berhasil, tunggu konfirmasi dari admin dan barang segera dikirim !", Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progressDialog.dismiss();
                                alertDialog.dismiss();
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });

                    } else {
                        Toast.makeText(CartActivity.this, "KESALAHAN POSTING PEMBAYARAN !", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(CartActivity.this, "ERROR !", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse Error message : " + e.getLocalizedMessage());
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                alertDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

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
                        modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);

                        assert modelResponseCart != null;

                        idGudang = String.valueOf(modelResponseCart.getMsgServer().getIdGudang());
                        adapterListCart = new AdapterListCart(CartActivity.this, modelResponseCart.getMsgServer().getDetailItemCart(), CartActivity.this);
                        binding.rvItemCart.setAdapter(adapterListCart);

                        if (modelResponseCart.getMsgServer().getDetailItemCart().size() > 0) {
                            hitungHitung(modelResponseCart);
                        }

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
                        modelResponseCart = gson.fromJson(response.body(), ModelResponseCart.class);

                        assert modelResponseCart != null;

                        idGudang = String.valueOf(modelResponseCart.getMsgServer().getIdGudang());
                        adapterListCart = new AdapterListCart(CartActivity.this, modelResponseCart.getMsgServer().getDetailItemCart(), CartActivity.this);
                        binding.rvItemCart.setAdapter(adapterListCart);

                        if (modelResponseCart.getMsgServer().getDetailItemCart().size() > 0) {
                            hitungHitung(modelResponseCart);

                        }


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

        double totalItem = modelResponseCart.getMsgServer().getTotalItem();
        double totalQty = Double.parseDouble(modelResponseCart.getMsgServer().getTotalQty());
        double totalBeli = 0;

        for (int i = 0; i < modelResponseCart.getMsgServer().getDetailItemCart().size(); i++) {
            DetailItemCart detailItemCart = modelResponseCart.getMsgServer().getDetailItemCart().get(i);
            int batasan1 = detailItemCart.getHarga().getQtyHarga1();
            int batasan2 = detailItemCart.getHarga().getQtyHarga2();
            int batasan3 = detailItemCart.getHarga().getQtyHarga3();

            String harga1 = detailItemCart.getHarga().getHarga();
            String harga2 = detailItemCart.getHarga().getQtyHarga2() == null ? "0" : detailItemCart.getHarga().getHargaDua();
            String harga3 = detailItemCart.getHarga().getQtyHarga3() == null ? "0" : detailItemCart.getHarga().getHargaTiga();

            double jumlahBarangDibeli = Double.parseDouble(detailItemCart.getQty());
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
    public void updateQtyBarang(DetailItemCart detailItemCart, double qtyItem) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        binding.btnCheckout.setFocusable(true);
        binding.btnCheckout.hasFocus();
        binding.btnCheckout.requestFocus();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = null;
        try {
            if (requestCode == 1) {
                if (resultCode == -1) {
                    Log.e(TAG, "onActivityResult: " + data);
                    if (data != null) {
                        if (data.hasExtra("hasSetAlamat")) {
                            fusedClient = LocationServices.getFusedLocationProviderClient(CartActivity.this);
                            getLastLocation();
                        }
                    } else {
                        Log.e(TAG, "onActivityResult: data " + data);
                    }
                }
            } else if (requestCode == 100) {
                if (resultCode == Activity.RESULT_OK) {
//            Log.e("TAG", "Path:" + ImagePicker.Companion.getFilePath(data));
                    Uri uri = data.getData();
                    ImageUri = uri;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
                        imageString = imageToString(bitmap);
                        isUploadBuktiTransfer = true;
                        checkoutBinding.layoutBtnCheckout.setEnabled(true);
                        checkoutBinding.layoutBtnCheckout.setBackgroundColor(getResources().getColor(R.color.biruBelga));
                        Log.e(TAG, "onActivityResult: " + bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onActivityResult: Exception " + e.getMessage());
        }
    }

    private FusedLocationProviderClient fusedClient;
    ProgressDialog progressDialog;
    Location locationPublic;
    LatLng latLngPublick;

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        progressDialog = ProgressDialog.show(CartActivity.this, "Loading", "Please Wait...");
        fusedClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            locationPublic = location;
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            latLngPublick = new LatLng(latitude, longitude);

                            Log.e(TAG, "onSuccess LAT : " + latitude);
                            Log.e(TAG, "onSuccess LONG : " + longitude);

                            Geocoder geocoder = new Geocoder(CartActivity.this, Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();

                                Log.e(TAG, "onClick ALAMAT : " + address);
                                Log.e(TAG, "onClick KECAMATAN : " + city);
                                Log.e(TAG, "onClick PROVINSI : " + state);
                                Log.e(TAG, "onClick NEGARA : " + country);
                                Log.e(TAG, "onClick KODEPOS : " + postalCode);
                                Log.e(TAG, "onClick KNOWNNAME : " + knownName);

                                StringBuilder locDestinations = new StringBuilder();

                                for (int i = 0; i < modelGudangs.size(); i++) {
                                    String desti = modelGudangs.get(i).getLatGudang() + "," + modelGudangs.get(i).getLongGudang() + "|";

                                    locDestinations.append(desti);
                                }


                                String locSetAlamat = alamatPengirimanPengguna.getLatLng().latitude + "," + alamatPengirimanPengguna.getLatLng().longitude;
                                gettingDistance(locSetAlamat, locDestinations.toString());

                            } catch (IOException e) {
                                progressDialog.dismiss();
                                e.printStackTrace();
                                Toast.makeText(CartActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create();
                                alertDialog.setTitle("Error GOOGLE MAPS : " + e.getLocalizedMessage());
                                alertDialog.setMessage("Pastikan aplikasi maps sudah terinstall dan dapat dijalankan ! Check Maps ?");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
//                                                getLastLocation();
                                                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latitude + "," + longitude));
                                                mapIntent.setPackage("com.google.android.apps.maps");
                                                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(mapIntent);
                                                } else {
                                                    Snackbar.make(binding.layoutBottom, "Google apps is not installed", Snackbar.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                alertDialog.show();

                            }

                        } else {
                            progressDialog.dismiss();
                            LocationRequest locationRequest = new LocationRequest()
                                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                    .setInterval(10000)
                                    .setFastestInterval(1000)
                                    .setNumUpdates(1);

                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    finish();
                                    startActivity(new Intent(CartActivity.this, GudangActivity.class));
                                }
                            };

                            AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create();
                            alertDialog.setTitle("Hi, " + sessionManager.getName());
                            alertDialog.setMessage("Lokasi belum diambil, ambil sekarang ?");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            fusedClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                            alertDialog.show();


                        }
                    }
                });
    }

    private void gettingDistance(String origin, String destinasi) {

        APIInterface apiInterface = APIClient.getClient(Http.mapsGoogle).create(APIInterface.class);
        Call<ModelResponseDistance> getMaps = apiInterface.doGetDistance(origin, destinasi, "driving", "AIzaSyC0NMGZYXcRkiWqPGU5hJZ2wOi4Vl7DtRY");

        getMaps.enqueue(new Callback<ModelResponseDistance>() {
            @Override
            public void onResponse(Call<ModelResponseDistance> call, retrofit2.Response<ModelResponseDistance> response) {
                progressDialog.dismiss();
                Log.e(TAG, "onResponse: " + response.body());

                ModelResponseDistance modelResponseDistance = response.body();

                for (int i = 0; i < modelResponseDistance.getRows().get(0).getElements().size(); i++) {
                    ModelGudang baru = modelGudangs.get(i);
                    baru.setTextJarak(modelResponseDistance.getRows().get(0).getElements().get(i).getDistance().getText());
                    baru.setValueJarak(modelResponseDistance.getRows().get(0).getElements().get(i).getDistance().getValue());
                    modelGudangs.set(i, baru);
                }

                Log.e(TAG, "onResponse SIZE : " + modelGudangs.size());

                for (int i = 0; i < modelGudangs.size(); i++) {
                    if (modelGudangs.get(i).getIdGudang().equals(idGudang)) {
                        jarakKm = (modelGudangs.get(i).getValueJarak() / 1000);
                    }
                }

                if (jarakKm < 10) {
                    hitungJarak = 0;
                } else {
                    hitungJarak = (Math.round(jarakKm - 10) * 2000);
                }

                DecimalFormat df = new DecimalFormat("#.##");
                String dx = df.format(hitungJarak);
                hitungJarak = Double.parseDouble(dx);

                binding.txtJarak.setText("± " + String.valueOf(Math.round(jarakKm)) + " Km");

                if (hitungJarak == 0) {
                    binding.txtOngkir.setText("FREE ( < 10 Km )");
                } else {
                    binding.txtOngkir.setText("Rp. " + nf.format(hitungJarak));
                }

                if (modelResponseCart != null) {
                    hitungHitung(modelResponseCart);
                }

            }

            @Override
            public void onFailure(Call<ModelResponseDistance> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
                finish();
            }
        });
    }
}