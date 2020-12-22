package com.dbelgamembership.membersip;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;

import com.dbelgamembership.membersip.HelperPrintUniversal.AsyncBluetoothEscPosPrint;
import com.dbelgamembership.membersip.HelperPrintUniversal.AsyncEscPosPrinter;
import com.dbelgamembership.membersip.Adapter.AdapterDetailbarang;
import com.dbelgamembership.membersip.DialogFragment.RiwayatTransaksiQrFragment;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.modelListTransaksi.Datum;
import com.dbelgamembership.membersip.Model.modelListTransaksi.Detail;
import com.dbelgamembership.membersip.Model.modelListTransaksi.ModelListTransaksi;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class
PrintActivity extends AppCompatActivity implements Runnable, AdapterDetailbarang.AdapterDetailbarangCallback {
    public final static int QRcodeWidth = 500;
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final String IMAGE_DIRECTORY = "/QRcodeBELGA/Sales";
    public static ArrayList<HashMap<String, String>> arrayDetailOrder = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> arrayDetail = new ArrayList<HashMap<String, String>>();
    public static String idTransaksi, soCode, sales, costumer, alamatKostumer, alamatKirim, nomorKostumer, tanggalKirim, ongkosKirimText;
    public static float grandTotal;
    public static int ongkosKirim, totalDiskonan, totalBelanja;
    Button mDisc;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mBluetoothDevice;
    Bitmap bitmap;
    ImageView iv, backIc, imgBarcode;
    TextView so_code;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    SimpleAdapter simpleAdapter;
    ListView lisssss;
    String dateNow;
    String path;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_listView1)
    RecyclerView lvListView1;
    @BindView(R.id.tvGrandtotal)
    TextView tvGrandtotal;
    @BindView(R.id.tvKembalian)
    TextView tvKembalian;
    @BindView(R.id.grand_total)
    TextView grand_total;
    @BindView(R.id.linearDiskon)
    LinearLayout linearDiskon;
    @BindView(R.id.txt_Diskon)
    TextView txtDiskon;
    @BindView(R.id.linearOngkir)
    LinearLayout linearOngkir;
    @BindView(R.id.txt_OngkosKirim)
    TextView txtOngkosKirim;
    @BindView(R.id.linearBelanja)
    LinearLayout linearBelanja;
    @BindView(R.id.txt_Belanja)
    TextView txtBelanja;
    @BindView(R.id.viewGarisTotal)
    View viewGarisTotal;



    int totalQty = 0;
    @BindView(R.id.btnQR)
    RelativeLayout btnQR;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.noSo)
    TextView noSo;
    @BindView(R.id.bb)
    LinearLayout bb;
    @BindView(R.id.aa)
    RelativeLayout aa;
    @BindView(R.id.scanbutton)
    RelativeLayout scanbutton;
    @BindView(R.id.cetakbutton)
    RelativeLayout cetakbutton;
    @BindView(R.id.aaa)
    LinearLayout aaa;
    @BindView(R.id.idetcash)
    EditText idetcash;
    @BindView(R.id.btn_full)
    CheckBox btnFull;
    @BindView(R.id.btnClear_fullcash)
    LinearLayout btnClearFullcash;
    @BindView(R.id.bankTransfer)
    Spinner bankTransfer;
    @BindView(R.id.idetnomtf)
    EditText idetnomtf;
    @BindView(R.id.btn_fulltransfer)
    CheckBox btnFulltransfer;
    @BindView(R.id.btnClear_fulltransfer)
    LinearLayout btnClearFulltransfer;
    @BindView(R.id.bankDebit)
    Spinner bankDebit;
    @BindView(R.id.idetDebitCash)
    EditText idetDebitCash;
    @BindView(R.id.btn_fulldebit)
    CheckBox btnFulldebit;
    @BindView(R.id.btnClear_fulldebit)
    LinearLayout btnClearFulldebit;
    @BindView(R.id.idetDebitId)
    EditText idetDebitId;
    @BindView(R.id.bankCredit)
    Spinner bankCredit;
    @BindView(R.id.chargeCredit)
    TextView chargeCredit;
    @BindView(R.id.dateCreated)
    TextView dateCreated;
    @BindView(R.id.chargeCreditRp)
    TextView chargeCreditRp;
    @BindView(R.id.noSoStatus)
    TextView noSoStatus;
    @BindView(R.id.idetCreditCash)
    EditText idetCreditCash;
    @BindView(R.id.btn_fullcredit)
    CheckBox btnFullcredit;
    @BindView(R.id.btnClear_fullcredit)
    LinearLayout btnClearFullcredit;
    @BindView(R.id.idetCreditId)
    EditText idetCreditId;

    @BindView(R.id.bayarbutton)
    Button bayarbutton;
    @BindView(R.id.container)
    CoordinatorLayout container;


    private RelativeLayout mScan, mPrint;
    private float total = 0;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    private String bluetoothAddress = "";

    private Printer mPrinter = null;
    private Context mContext = this;
    private static String BD_ADDRESS = null;
    private ProgressDialog progressDialog;

    private NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);
    private SessionManager sessionManager;
    private RecyclerView.LayoutManager layoutManager;
    private List<Detail> listBarang = new ArrayList<>();
    private String dataSO;
    private Boolean takeorder = false;


    @Override
    public void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
//        setContentView(R.layout.review_buktipembayaran);
        setContentView(R.layout.activity_buktibayar_new);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgBarcode = findViewById(R.id.image_qrCode);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (takeorder) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        });

//        Paper.init(this);

//        BD_ADDRESS = "BT:" + Paper.book().read(Address.bluetoothAddress);


        arrayDetailOrder.clear();
        arrayDetail.clear();
        grand_total.setText("Rp. 0");

        ongkosKirim = 0;
        totalDiskonan = 0;
        totalBelanja = 0;

        progressDialog = new ProgressDialog(this);

        Date c = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateNow = df.format(c.getTime());
        layoutManager = new LinearLayoutManager(this);
        lvListView1.setLayoutManager(layoutManager);
        lvListView1.setHasFixedSize(false);
        lvListView1.setVisibility(View.GONE);
        getSupportActionBar().setTitle("Detail Take Order");
        if (getIntent().hasExtra("DATAPRINT")) {
            takeorder = getIntent().getBooleanExtra("TAKEORDER", false);
            dataSO = getIntent().getStringExtra("DATAPRINT");
            Log.e(TAG, "onCreate: " + dataSO);
            noSo.setText(dataSO.toUpperCase());
            final ProgressDialog dialog1 = new ProgressDialog(PrintActivity.this);
            dialog1.setCancelable(false);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setTitle("Mengambil Data");
            dialog1.setMessage("Harap Menunggu...");
            dialog1.show();

            new Thread() {
                @Override
                public void run() {
                    accessWebService();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            }.start();
            dialog1.dismiss();


        } else {
            finish();
        }

        mScan = findViewById(R.id.scanbutton);
//        mScan.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View mView) {
//                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//                if (mBluetoothAdapter == null) {
//                    Toast.makeText(PrintActivity.this, "Message1", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (!mBluetoothAdapter.isEnabled()) {
//                        Intent enableBtIntent = new Intent(
//                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                        startActivityForResult(enableBtIntent,
//                                REQUEST_ENABLE_BT);
//                    } else {
////                            ListPairedDevices();
//                        Intent connectIntent = new Intent(PrintActivity.this, DeviceListActivity.class);
//                        startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
//
//                    }
//                }
//
//            }
//        });

        mPrint = findViewById(R.id.cetakbutton);
        mPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {

                printBluetooth();

            }
        });


    }

    //batas awal api access
    private void accessWebService() {
        String url = Http.server + "transaction/list?code=" + dataSO;
        Log.e(TAG, "URL" + url);
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        Log.e(TAG, "accessWebService: Katalog Start with Token " + sessionManager.getKeyToken());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e(TAG, "Response : " + response);
                            Gson gson = new Gson();
                            ModelListTransaksi modelListTransaction = gson.fromJson(String.valueOf(response), ModelListTransaksi.class);
                            Datum b = modelListTransaction.getData().get(0);
                            idTransaksi = String.valueOf(b.getId());
                            grandTotal = Float.parseFloat(String.valueOf(b.getGrandtotal()));
                            ongkosKirim = 0;

                            if (b.getOngkosKirim() != 0) {
                                ongkosKirim = b.getOngkosKirim();
                            } else {
                                ongkosKirim = 0;
                            }

                            linearOngkir.setVisibility(View.GONE);
                            linearDiskon.setVisibility(View.GONE);
                            viewGarisTotal.setVisibility(View.GONE);

                            soCode = b.getCode();
                            dateNow = b.getCreatedAt();
                            dateCreated.setText(dateNow + "");
                            Log.e(TAG, "onCreate: " + soCode);

                            sales = String.valueOf(b.getCreateuser());
                            costumer = b.getCustomer();
                            alamatKostumer = b.getAlamatCustomer();

                            if (b.getAlamatPengiriman() == null) {
                                alamatKirim = "";
                            } else {
                                alamatKirim = b.getAlamatPengiriman().toString();
                            }

                            nomorKostumer = b.getNomorCustomer();
                            tanggalKirim = b.getTanggalKirim();

                            ongkosKirimText = String.valueOf(ongkosKirim);


                            if (ongkosKirimText.equals("0")) {
                                linearOngkir.setVisibility(View.GONE);
                            } else {
                                viewGarisTotal.setVisibility(View.VISIBLE);
                                linearOngkir.setVisibility(View.VISIBLE);
                                txtOngkosKirim.setText("Rp. " + nf.format(Integer.parseInt(ongkosKirimText)));
                                Log.e(TAG, "Ongkos Kirim : " + ongkosKirimText);
                            }

                            Log.e(TAG, "Nomor telepon : " + b.getNomorCustomer());
                            Log.e(TAG, "Alamat Kirim : " + String.valueOf(b.getAlamatPengiriman()));
                            Log.e(TAG, "Tanggal Kirim : " + b.getTanggalKirim());

                            tvKembalian.setVisibility(View.GONE);
                            noSoStatus.setText(b.getStatus().toUpperCase());
                            tvKembalian.setText("Rp." + nf.format(Integer.parseInt(String.valueOf(b.getGrandtotal()))));

                            btnQR.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FragmentManager fm = getSupportFragmentManager();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("noFaktur", b.getCode());
                                    RiwayatTransaksiQrFragment editNameDialogFragment = new RiwayatTransaksiQrFragment();
                                    editNameDialogFragment.setArguments(bundle);
                                    editNameDialogFragment.show(fm, RiwayatTransaksiQrFragment.class.getSimpleName());
                                }
                            });


                            tvGrandtotal.setText("Rp. " + nf.format(grandTotal));
                            total = grandTotal;
                            dateNow = b.getDate();
                            grand_total.setText("Rp. " + nf.format(total));
                            listBarang = b.getDetail();

                            int diskonBarang = 0;
                            int belanjaBarang = 0;

                            for (Detail barang : listBarang) {
                                HashMap<String, String> hashMap = new HashMap<>(); //create a hashmap to store the data in key value pair
                                hashMap.put("namaBrg", barang.getName());
                                Log.e(TAG, "Masuk1: " + barang.getName() );
                                Log.e(TAG, "Masuk11: " + barang.getQtyStore() );
                                Log.e(TAG, "Masuk12: " + barang.getQtyOutlet() );
                                Log.e(TAG, "Masuk13: " + barang.getIndentValue() );

                                int Qty = Integer.parseInt(barang.getQtyOutlet()) + Integer.parseInt(barang.getQtyStore()) + barang.getIndentValue();
                                hashMap.put("qtyUnit", Qty + " Unit");
                                Log.e(TAG, "Masuk2: " + barang.getName() );
                                hashMap.put("qty", String.valueOf(Qty));
                                Log.e(TAG, "Masuk3: " + Qty );
                                hashMap.put("harga", barang.getRealPrice() + "");
                                Log.e(TAG, "Masuk4: " + barang.getRealPrice() );
                                hashMap.put("Code", barang.getCodeProduct() + "");
                                Log.e(TAG, "Masuk5: " + barang.getCodeProduct() );

                                belanjaBarang = Integer.parseInt(barang.getTotal());
                                Log.e(TAG, "Belanja Barang : " + belanjaBarang );
                                if (barang.getTotalDiskon() != "0") {
                                    hashMap.put("total", barang.getTotalSetelahDiskon() + "");
                                    diskonBarang = Integer.parseInt(barang.getTotalDiskon());
                                } else {
                                    diskonBarang = 0;
                                    hashMap.put("total", barang.getTotal() + "");
                                }
                                Log.e(TAG, "Cek diskon barang : " + barang.getTotalDiskon() );
                                hashMap.put("diskon", String.valueOf(diskonBarang));
                                totalBelanja += belanjaBarang;
                                Log.e(TAG, "Total Belanja : " + totalBelanja);
                                totalDiskonan += diskonBarang;
                                arrayDetail.add(hashMap);
                                Log.e(TAG, "Tambah " +
                                        " detail: " + arrayDetail);
                            }

                            if (totalDiskonan > 0) {
                                viewGarisTotal.setVisibility(View.VISIBLE);
                                linearDiskon.setVisibility(View.VISIBLE);
                                txtDiskon.setText("Rp. " + nf.format(totalDiskonan));
                            }

                            if (totalBelanja != b.getGrandtotal()) {
                                viewGarisTotal.setVisibility(View.VISIBLE);
                                linearBelanja.setVisibility(View.VISIBLE);
                                txtBelanja.setText("Rp. " + nf.format(totalBelanja));
                            }

                            Log.e(TAG, "Total Diskon : " + totalDiskonan);
                            Log.e(TAG, "onCreate:COK " + arrayDetail.size());
                            Log.e(TAG, "array Detail: " + arrayDetail);
                            Log.e("arrayDetail: ", String.valueOf(arrayDetailOrder));
                            if (!arrayDetail.isEmpty()) {
                                lvListView1.setVisibility(View.VISIBLE);
                                AdapterDetailbarang adapterDetailbarang = new AdapterDetailbarang(PrintActivity.this, -1, listBarang, PrintActivity.this);
                                lvListView1.setAdapter(adapterDetailbarang);
                            }
                            Log.e("idTransaksi: ", idTransaksi);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage(), error);

//                dialog.dismiss();
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (error instanceof NetworkError) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//                    Snack(error.getMessage());
//                    Toast.makeText(DaftarOrderActivity.this, "error : lod" + error.getMessage(), Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PrintActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    accessWebService();
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
    //batas akhir api access


    //BatasNewPrint

        /*==============================================================================================
    ======================================BLUETOOTH PART============================================
    ==============================================================================================*/

    public static final int PERMISSION_BLUETOOTH = 1;

    public void printBluetooth() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, PrintActivity.PERMISSION_BLUETOOTH);
        } else {
            // this.printIt(BluetoothPrintersConnections.selectFirstPaired());
            new AsyncBluetoothEscPosPrint(this).execute(this.getAsyncEscPosPrinter(null));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PrintActivity.PERMISSION_BLUETOOTH:
                    this.printBluetooth();
                    break;
            }
        }
    }

    /*==============================================================================================
    ===================================ESC/POS PRINTER PART=========================================
    ==============================================================================================*/


    /**
     * Synchronous printing
     */
    @SuppressLint("SimpleDateFormat")
    public void printIt(DeviceConnection printerConnection) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
            EscPosPrinter printer = new EscPosPrinter(printerConnection, 203, 48f, 32);
            printer
                    .printFormattedText(
//                            "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                            "[L]\n" +
                                    "[C]<u><font size='big'>ORDER N°045</font></u>\n" +
                                    "[C]<font size='small'>" + format.format(new Date()) + "</font>\n" +
                                    "[L]\n" +
                                    "[C]================================\n" +
                                    "[L]\n" +
                                    "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
                                    "[L]  + Size : S\n" +
                                    "[L]\n" +
                                    "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                                    "[L]  + Size : 57/58\n" +
                                    "[L]\n" +
                                    "[C]--------------------------------\n" +
                                    "[R]TOTAL PRICE :[R]34.98e\n" +
                                    "[R]TAX :[R]4.23e\n" +
                                    "[L]\n" +
                                    "[C]================================\n" +
                                    "[L]\n" +
                                    "[L]<font size='tall'>Customer :</font>\n" +
                                    "[L]Raymond DUPONT\n" +
                                    "[L]5 rue des girafes\n" +
                                    "[L]31547 PERPETES\n" +
                                    "[L]Tel : +33801201456\n" +
                                    "[L]\n" +
                                    "[C]<qrcode size='20'>" + soCode + "</qrcode>"
                    );
        } catch (EscPosConnectionException e) {
            e.printStackTrace();
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Broken connection")
                    .setMessage(e.getMessage())
                    .show();
        } catch (EscPosParserException e) {
            e.printStackTrace();
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Invalid formatted text")
                    .setMessage(e.getMessage())
                    .show();
        } catch (EscPosEncodingException e) {
            e.printStackTrace();
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Bad selected encoding")
                    .setMessage(e.getMessage())
                    .show();
        } catch (EscPosBarcodeException e) {
            e.printStackTrace();
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Invalid barcode")
                    .setMessage(e.getMessage())
                    .show();
        }
    }

    /**
     * Asynchronous printing
     */
    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);

        StringBuilder dataBarang = new StringBuilder();
        StringBuilder dataDiskon = new StringBuilder();
        StringBuilder dataOngkir = new StringBuilder();
        long totalDiskon = 0;
        for (int i = 0; i < arrayDetail.size(); i++) {
            int j = 1;
            String kodeBarang = arrayDetail.get(i).get("Code");
            String namaBarang = arrayDetail.get(i).get("namaBrg");
            String qtyUnit = arrayDetail.get(i).get("qty");
            String hargaBarang = arrayDetail.get(i).get("harga");
            String discount = arrayDetail.get(i).get("diskon");

            String jumlahUnit = "X " + qtyUnit;
            String hargaUnit = "@ " + nf.format(Integer.parseInt(hargaBarang));
            int totalHargaUnit = Integer.parseInt(qtyUnit) * Integer.parseInt(hargaBarang);

            dataBarang.append("[L]<font size='small'>" + kodeBarang + " # " + namaBarang + "</font>\n");
            dataBarang.append("[L]<font size='small'>" + jumlahUnit + " " + hargaUnit + "[R]" + nf.format(totalHargaUnit) + "</font>\n");

            if (!discount.equals("0")) {
                dataBarang.append("[L]<font size='small'>Diskon. " + discount + "</font>\n");

            }

            totalDiskon += Long.parseLong(discount);
        }

        if (totalDiskon != 0) {
            dataDiskon.append("[L]PROMO DISKON[R]Rp. " + nf.format(totalDiskon) + "\n");
        } else if (totalDiskon == 0) {
            dataDiskon.append("");
        }

        if (ongkosKirim != 0) {
            dataOngkir.append("[L]BIAYA KIRIM[R]Rp. " + nf.format(ongkosKirim) + "\n");
        } else if (ongkosKirim == 0) {
            dataDiskon.append("");
        }

        return printer.setTextToPrint(
//               PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                "[L]\n" +
                        "[C]<u><font size='big'>SALES ORDER</font></u>\n" +
                        "[C]<font size='small'>Pameran Belanja PGP</font>\n" +
                        "[C]<font size='small'>SURABAYA</font>\n" +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[C]<b>__________ DATA SALES __________</b>\n" +
                        "[L]<font size='small'>Nama Sales     : " + sales + "</font>\n" +
                        "[L]<font size='small'>Nomor Order    : " + soCode + "</font>\n" +
                        "[L]<font size='small'>Tanggal Order  : " + dateNow + "</font>\n" +
                        "[L]\n" +
                        "[C]<b>________ DATA PELANGGAN ________</b>\n" +
                        "[L]<font size='small'>Nama Pelanggan : " + costumer + "</font>\n" +
                        "[L]<font size='small'>Nomor Telepon  : " + nomorKostumer + "</font>\n" +
                        "[L]<font size='small'>Alamat Kirim   : " + alamatKirim + "</font>\n" +
                        "[L]<font size='small'>Tangaal Kirim  : " + tanggalKirim + "</font>\n" +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]Item[R][R]Total\n" +
                        "[C]================================\n" +
                        dataBarang.toString() +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]TOTAL BELANJA[R]Rp. " + nf.format(total) + "\n" +
                        dataDiskon.toString() +
                        dataOngkir.toString() +
                        "[C]================================\n" +
                        "[L]<font size='tall'>TOTAL : " + nf.format(grandTotal) + "</font>\n" +
                        "[C]================================\n" +
//                        "[L]\n" +
//                        "<qrcode size='20'>" + soCode + "</qrcode>\n" +
//                        "[C]" + soCode + "\n" +
                        "[L]\n" +
                        "[C]TERIMAKASIH TELAH BELANJA\n"
        );
    }


    //AkhirNewPrint


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(PrintActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
            Toast.makeText(PrintActivity.this, bluetoothAddress, Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onRowAdapterDetailbarangClicked(int position) {

    }


    @Override
    public void run() {

    }
}