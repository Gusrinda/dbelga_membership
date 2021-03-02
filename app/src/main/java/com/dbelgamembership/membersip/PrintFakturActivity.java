package com.dbelgamembership.membersip;

/**
 * Created by hp on 12/23/2016.
 */

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
import android.util.DisplayMetrics;
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
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.dbelgamembership.membersip.Adapter.AdapterDetailbarangFak;
import com.dbelgamembership.membersip.DialogFragment.RiwayatTransaksiQrFragment;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.HelperPrintUniversal.AsyncBluetoothEscPosPrint;
import com.dbelgamembership.membersip.HelperPrintUniversal.AsyncEscPosPrinter;
import com.dbelgamembership.membersip.Model.ModelPayment.ModelPayment;

import com.dbelgamembership.membersip.Model.modelListFaktur.Datum;
import com.dbelgamembership.membersip.Model.modelListFaktur.Item;
import com.dbelgamembership.membersip.Model.modelListFaktur.ModelListFaktur;
import com.dbelgamembership.membersip.Model.modelListFaktur.OrderDetail;
import com.dbelgamembership.membersip.Model.modelListFaktur.PaymentDetail;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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


public class PrintFakturActivity extends AppCompatActivity implements Runnable, AdapterDetailbarangFak.AdapterDetailbarangCallback {

    public final static int QRcodeWidth = 500;
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final String IMAGE_DIRECTORY = "/QRcodeBELGA/Kasir";
    public static ArrayList<HashMap<String, String>> arrayDetailOrder = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> arrayDetail = new ArrayList<HashMap<String, String>>();
    public static String idTransaksi, soCode, sales, costumer, FLAG_DP = "false";
    public static long grandTotal;
    int GTCOKCOKCOKCOCKCOK = 0;
    @BindView(R.id.tvOngkosKirim)
    TextView tvOngkosKirim;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.image_qrCode)
    ImageView imageQrCode;
    @BindView(R.id.viewPembayaranTunai)
    RelativeLayout viewPembayaranTunai;
    @BindView(R.id.aa)
    RelativeLayout aa;
    @BindView(R.id.scanbutton)
    RelativeLayout scanbutton;
    @BindView(R.id.contentQRCOde)
    LinearLayout contentQRCOde;
    @BindView(R.id.cetakbutton)
    RelativeLayout cetakbutton;
    @BindView(R.id.cancelButton)
    RelativeLayout cancelButton;
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
    @BindView(R.id.chargeCreditRp)
    TextView chargeCreditRp;
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
    @BindView(R.id.titleKembalian)
    TextView titleKembalian;
    @BindView(R.id.linearContent)
    RelativeLayout llcontent;
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
    @BindView(R.id.tvCatatan)
    TextView txtCatatan;
    @BindView(R.id.viewGarisTotal)
    View viewGarisTotal;

    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    private Printer mPrinter = null;
    private Context mContext = this;
    private static String BD_ADDRESS = null, ALAMAT_KIRIM = "", NAMA_CUSTOMER;
    int ONGKIR_COK;


    Button mDisc;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mBluetoothDevice;
    Bitmap bitmap;
    ImageView iv, backIc, imgBarcode;
    TextView so_code;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    SimpleAdapter simpleAdapter;
    ListView lisssss;
    String dateNow, path;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_listView1)
    RecyclerView lvListView1;
    @BindView(R.id.tvTotalPembayaran)
    TextView tvTotalPembayaran;
    @BindView(R.id.tvKembalian)
    TextView tvKembalian;
    @BindView(R.id.grand_total)
    TextView grand_total;
    int totalQty = 0;
    @BindView(R.id.btnQR)
    RelativeLayout btnQR;
    @BindView(R.id.tvdate)
    TextView tvdate;
    @BindView(R.id.tvSO)
    TextView tvSO;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.contentScan)
    LinearLayout contentScan;
    @BindView(R.id.contentCetak)
    LinearLayout contentCetak;
    @BindView(R.id.contentCancel)
    LinearLayout contentCancel;
    @BindView(R.id.bb)
    LinearLayout bb;
    @BindView(R.id.tvPembayaranTunai)
    TextView tvPembayaranTunai;
    @BindView(R.id.tvPembauaranLain)
    TextView tvPembayaranLain;
    @BindView(R.id.tvMetodeLain)
    TextView tvmetodeLain;
    @BindView(R.id.viewPembayaranLain)
    RelativeLayout viewPembayaranLain;
    @BindView(R.id.tvChargePembayaran)
    TextView tvChargePembayaran;
    @BindView(R.id.viewChargePembayaran)
    RelativeLayout viewChargePembayaran;

    @BindView(R.id.linearCharge)
    LinearLayout linearCharge;
    private RelativeLayout mScan, mPrint;
    private int total = 0;
    private String tipeMetode = null;
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(PrintFakturActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
        }
    };
    private NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);
    private RecyclerView.LayoutManager layoutManager;
    private List<Item> listBarang = new ArrayList<>();
    private SessionManager sessionManager;
    private String url = "";
    private String printBayar, printKembalian, printTotal;
    long grandTOTAL;
    private float amountCharge;
    private float amountAnotherPayment;
    private float pembayaranTunai;

    @Override
    public void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        setContentView(R.layout.activity_buktifaktur_new);
        ButterKnife.bind(this);
        llcontent.setVisibility(View.GONE);
        linearCharge.setVisibility(View.GONE);
        sessionManager = new SessionManager(this);

//        Paper.init(this);

//        BD_ADDRESS = "BT:" + Paper.book().read(Address.bluetoothAddress);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgBarcode = findViewById(R.id.image_qrCode);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arrayDetailOrder.clear();
        arrayDetail.clear();
        Date c = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateNow = df.format(c.getTime());
        layoutManager = new LinearLayoutManager(this);
        lvListView1.setLayoutManager(layoutManager);
        lvListView1.setHasFixedSize(false);
        lvListView1.setVisibility(View.GONE);
        if (getIntent().hasExtra("DATAPRINT")) {
            if (getIntent().hasExtra("FAKTUR")) {
                getSupportActionBar().setTitle("Detail Pembayaran");
                contentCancel.setVisibility(View.GONE);
                contentScan.setVisibility(View.GONE);
                linearCharge.setVisibility(View.VISIBLE);
                contentCetak.setVisibility(View.VISIBLE);
                setupFaktur(getIntent().getStringExtra("DATAPRINT"));
            }
        } else {
            finish();
        }


//        mScan = findViewById(R.id.scanbutton);
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
//                        ListPairedDevices();
//                        Intent connectIntent = new Intent(PrintActivity.this,
//                                DeviceListActivity.class);
//                        startActivityForResult(connectIntent,
//                                REQUEST_CONNECT_DEVICE);
//                    }
//                }
//            }
//        });

        mPrint = findViewById(R.id.cetakbutton);
        mPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                printBluetooth();
            }
        });

    }


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
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);

        StringBuilder alamatKirim = new StringBuilder();
        if (ALAMAT_KIRIM.equals("null") || ALAMAT_KIRIM.isEmpty() || ALAMAT_KIRIM == null) {
            ALAMAT_KIRIM = "";
        }
        if (ALAMAT_KIRIM.length() > 46) {
            alamatKirim.append("[L]<font size='small'>Alamat Kirim   : " + ALAMAT_KIRIM.substring(0, 46) + "</font>");
        } else {
            alamatKirim.append("[L]<font size='small'>Alamat Kirim   : " + ALAMAT_KIRIM + "</font>");
        }
        StringBuilder dataBarang = new StringBuilder();
        for (int i = 0; i < arrayDetail.size(); i++) {
            int j = 1;
            String kodeBarang = arrayDetail.get(i).get("Code");
            String namaBarang = arrayDetail.get(i).get("namaBrg");
            String qtyUnit = arrayDetail.get(i).get("qty");
            String hargaBarang = arrayDetail.get(i).get("harga");
            String discount = arrayDetail.get(i).get("nominal_diskon");
            String jumlahUnit = "X " + qtyUnit;
            String hargaUnit = "@ " + nf.format(Integer.parseInt(hargaBarang));
            int totalHargaUnit = Integer.parseInt(qtyUnit) * Integer.parseInt(hargaBarang);

            dataBarang.append("[L]<font size='small'>" + namaBarang + "</font>\n");
            dataBarang.append("[L]<font size='small'>" + jumlahUnit + " " + hargaUnit + "[R]" + nf.format(totalHargaUnit) + "</font>\n");

            if (!discount.isEmpty()) {
                dataBarang.append("[L]<font size='small'>Diskon. " + nf.format(Integer.parseInt(discount)) + "</font>\n");
            }
        }

        StringBuilder dataBayarLain = new StringBuilder();
        if (tipeMetode != null) {
            dataBayarLain.append("[L]" + tipeMetode + "[R]Rp. " + nf.format(amountAnotherPayment) + "\n");
            dataBayarLain.append("[L]Charge[R]Rp. " + nf.format(amountCharge) + "\n");
        }

        StringBuilder dataTotalBelanja = new StringBuilder();
        if (FLAG_DP.equals("true")) {
            String kembalianString = "0";
            int kembalianDP = 0;
            if (!printKembalian.isEmpty()) {
                kembalianString = printKembalian.substring(1, printKembalian.length());
            }
            if (!kembalianString.isEmpty()) {
                kembalianDP = Integer.parseInt(kembalianString);
            }
            dataTotalBelanja.append("[L]CASH (DP)[R]Rp. " + nf.format(pembayaranTunai) + "\n");
            if (tipeMetode != null) {
                dataTotalBelanja.append("[L]" + "DC/CC" + "(DP)" + "[R]Rp. " + nf.format(amountAnotherPayment) + "\n");
                dataTotalBelanja.append("[L]Charge[R]Rp. " + nf.format(amountCharge) + "\n");
            }
            dataTotalBelanja.append("[L]KURANG BAYAR[R]Rp. " + nf.format(kembalianDP) + "\n");

        } else {
            dataTotalBelanja.append("[L]TUNAI[R]Rp. " + nf.format(pembayaranTunai) + "\n" + dataBayarLain.toString() + "[L]KEMBALIAN[R]Rp. " + nf.format(Integer.parseInt(printKembalian)) + "\n" + "[L]GRAND TOTAL[R]Rp. " + nf.format((pembayaranTunai + amountAnotherPayment + amountCharge)) + "\n");
//                        amountAnotherPayment
//                amountCharge

        }

        return printer.setTextToPrint(
//               PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                "[L]\n" +
//                        "[C]<u><font size='big'>FAKTUR BELANJA</font></u>\n" +
                        "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.dbelga, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
//                        "[C]<font size='small'>Pameran Belanja PGP</font>\n" +
//                        "[C]<font size='small'>SURABAYA</font>\n" +
//                        "[C]" + soCode + "\n" +
//                        "[L]\n" +
//                        "[C]================================\n" +
//                        "[L]\n" +
                        "[C]<b>_______________________________</b>\n" +
                        "[L]<font size='small'>Nama Kasir     : " + sales + "</font>\n" +
                        "[L]<font size='small'>Nomor Order    : " + soCode + "</font>\n" +
                        "[L]<font size='small'>Tgl Pembayaran : " + dateNow.substring(0, 10) + "</font>\n" +
                        "[L]<font size='small'>Nama Cust.     : " + NAMA_CUSTOMER + "</font>\n" +
                        alamatKirim.toString() + "\n" +
//                        "[L]\n" +
//                        "[C]<b>_______________________________</b>\n" +
//                        "[L]<font size='small'>Nama Kustomer  : " + "Kustomer" + "</font>\n" +
//                        "[L]<font size='small'>Nomor Telepon  : " + soCode + "</font>\n" +
//                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]Item[R][R]Total\n" +
                        "[C]================================\n" +
                        dataBarang.toString() +
                        "[L]\n" +
                        "[C]================================\n" +
//                        "[L]PROMO DISKON[R]Rp. " + "-" + "\n" +
                        "[L]BIAYA KIRIM[R]Rp. " + nf.format(ONGKIR_COK) + "\n" + //KURANGONGKIR
                        "[L]TOTAL BELANJA[R]Rp. " + nf.format(GTCOKCOKCOKCOCKCOK) + "\n" +
                        "[C]________________________________\n" +
//                        "[C]<font size='medium'>PEMBAYARAN</font>\n" +
                        "[C]________________________________\n" +
                        dataTotalBelanja.toString() +
                        "[C]================================\n" +
                        "[L]\n" +
//                        "<qrcode size='20'>" + soCode + "</qrcode>\n" +

                        "[C]TERIMAKASIH TELAH BERBELANJA\n"

        );
    }


    //AkhirNewPrint


    private void setupFaktur(String dataprint) {
        Log.e(TAG, "setupFaktur: " + dataprint);
        bb.setVisibility(View.VISIBLE);
        String url = Http.server + "payment/list?code=" + dataprint;
        Log.e(TAG, "url: " + url);
        final ProgressDialog dialog1 = new ProgressDialog(PrintFakturActivity.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        Log.e(TAG, "accessWebService: Katalog Start with Token " + sessionManager.getKeyToken());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gson = new Gson();
                            ModelListFaktur modelListFaktur = gson.fromJson(String.valueOf(response), ModelListFaktur.class);
                            Datum b = modelListFaktur.getData().get(0);
                            idTransaksi = String.valueOf(b.getId());
                            grandTotal = (b.getTotalPaymentPaid() - b.getChange());
                            String cok1 = String.valueOf(b.getTotalPaymentPaid());
                            String cok2 = String.valueOf(b.getChange());

                            Log.e(TAG, "cok1 : " + cok1 + "cok2 : " + cok2);
                            soCode = b.getPembayaranCode();
                            Log.e(TAG, "onCreate: " + soCode);
                            sales = String.valueOf(b.getCreateuser());
                            tvdate.setText(b.getDateTransaction() + "");
                            tvSO.setText(b.getPembayaranCode());

                            String status = "";

                            Log.e(TAG, "Data : " + b.getStatusPengiriman());
                            if (b.getStatusPengiriman() == null) {
                                status = "Belum Dikirim";

                            } else {
                                status = b.getStatusPengiriman();
                            }

                            tvStatus.setText(status);

                            String catatanPesanan = "";
                            if (b.getCatatanPengiriman() == null) {
                                catatanPesanan = "Tidak ada catatan khusus";
                            } else {
                                catatanPesanan = b.getCatatanPengiriman();
                            }

                            txtCatatan.setText(catatanPesanan);

                            tvOngkosKirim.setText("Rp. " + nf.format(b.getOngkosKirim()));
                            ONGKIR_COK = b.getOngkosKirim();
                            printBayar = String.valueOf(b.getTotalPaymentPaid());
                            ALAMAT_KIRIM = b.getAlamatPengiriman();
                            NAMA_CUSTOMER = b.getCustomer();
                            printKembalian = String.valueOf(b.getChange());
                            FLAG_DP = String.valueOf(b.isFlagDp());
                            if (b.isFlagDp() == true) {
//                                tvKembalian.setVisibility(View.GONE);
                                String kembalianString = "0";
                                int kembalianDP = 0;
                                if (!printKembalian.isEmpty()) {
                                    kembalianString = printKembalian.substring(1, printKembalian.length());
                                }
                                if (!kembalianString.isEmpty()) {
                                    kembalianDP = Integer.parseInt(kembalianString);
                                }
                                tvKembalian.setText("Rp. " + nf.format(kembalianDP));
                                titleKembalian.setText("Kurang Bayar");
                            } else {
                                tvKembalian.setText("Rp. " + nf.format(b.getChange()));
                            }
                            Log.e(TAG, "onResponse: FLAGDP" + FLAG_DP);

//                            if (!b.getOrderDetail().get(0).getAlamatPengiriman().equals(null)){
//                                ALAMAT_KIRIM = b.getOrderDetail().get(0).getAlamatPengiriman();
//                            }

                            btnQR.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FragmentManager fm = getSupportFragmentManager();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("noFaktur", b.getPembayaranCode());
                                    RiwayatTransaksiQrFragment editNameDialogFragment = new RiwayatTransaksiQrFragment();
                                    editNameDialogFragment.setArguments(bundle);
                                    editNameDialogFragment.show(fm, RiwayatTransaksiQrFragment.class.getSimpleName());
                                }
                            });

                            grandTotal = 0;
                            for (com.dbelgamembership.membersip.Model.modelListFaktur.PaymentDetail payment : b.getPaymentDetail()) {
                                Log.e(TAG, "onCreate: PAYMENT" + payment.getTotal());
                                Double dnum = Double.parseDouble(payment.getTotal());
                                grandTotal += dnum.intValue();
                                if (payment.getPaymentType().equals("PAY_TYPE_TUNAI")) {
                                    pembayaranTunai = new Float(payment.getTotal());
                                    tvPembayaranTunai.setText("Rp. " + nf.format(pembayaranTunai));
                                }
                            }
                            tvTotalPembayaran.setText("Rp. " + nf.format(b.getTotalPaymentPaid()));
                            dateNow = b.getDateTransaction();
                            for (PaymentDetail DetailCheckOut : b.getPaymentDetail()) {
                                if (DetailCheckOut.getPaymentType().equals("PAY_TYPE_CREDIT") || DetailCheckOut.getPaymentType().equals("PAY_TYPE_TRANSFER")
                                        || DetailCheckOut.getPaymentType().equals("PAY_TYPE_DEBET")) {
                                    if (DetailCheckOut.getPaymentType().equals("PAY_TYPE_CREDIT")) {
                                        tipeMetode = "KREDIT " + DetailCheckOut.getOptionBank();
                                    } else if (DetailCheckOut.getPaymentType().equals("PAY_TYPE_TRANSFER")) {
                                        tipeMetode = "TRANSFER " + DetailCheckOut.getOptionBank();
                                    } else if (DetailCheckOut.getPaymentType().equals("PAY_TYPE_DEBET")) {
                                        tipeMetode = "DEBIT " + DetailCheckOut.getOptionBank();
                                    }
                                    amountAnotherPayment = Float.parseFloat(DetailCheckOut.getTotal());
                                    amountCharge = Float.parseFloat(DetailCheckOut.getChargeAmount());
                                    viewPembayaranLain.setVisibility(View.VISIBLE);
                                    viewChargePembayaran.setVisibility(View.VISIBLE);
                                    tvmetodeLain.setText(tipeMetode);
                                    tvPembayaranLain.setText("Rp. " + nf.format(amountAnotherPayment));
                                    tvChargePembayaran.setText("Rp. " + nf.format(amountCharge));
                                }
                            }
                            for (OrderDetail barangCheckout : b.getOrderDetail()) {
                                List<com.dbelgamembership.membersip.Model.modelListFaktur.Item> itemss = barangCheckout.getItems();
                                for (int i = 0; i < itemss.size(); i++) {
                                    com.dbelgamembership.membersip.Model.modelListFaktur.Item barang = itemss.get(i);
                                    HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
                                    hashMap.put("namaBrg", barang.getName());
                                    int Qty = Integer.parseInt(barang.getQtyOutlet()) + Integer.parseInt(barang.getQtyStore()) + barang.getIndentValue();
                                    hashMap.put("qtyUnit", Qty + " Unit");
                                    hashMap.put("qty", String.valueOf(Qty));
                                    hashMap.put("harga", barang.getRealPrice() + "");
                                    hashMap.put("Code", barang.getCodeProduct() + "");
                                    hashMap.put("total", Integer.parseInt(barang.getTotal()) - Integer.parseInt(barang.getTotalDiskon()) + "");
                                    hashMap.put("nominal_diskon", barang.getTotalDiskon() + "");
                                    arrayDetail.add(hashMap);
                                    listBarang.add(barang);
                                    total += (Integer.parseInt(barang.getTotal()) - Integer.parseInt(barang.getTotalDiskon()));
                                    GTCOKCOKCOKCOCKCOK += (Integer.parseInt(barang.getTotal()) - Integer.parseInt(barang.getTotalDiskon()));
                                }
                            }
                            Log.e(TAG, "onCreate: " + arrayDetail.size());
                            grandTOTAL = b.getTotalPaymentPaid() - b.getChange();
                            float grandCOK = total + b.getOngkosKirim() + amountCharge;
                            grand_total.setText("Rp. " + nf.format(grandCOK));
                            Log.e("arrayDetailOrder: ", String.valueOf(arrayDetailOrder));
                            if (!arrayDetail.isEmpty()) {
                                lvListView1.setVisibility(View.VISIBLE);
                                AdapterDetailbarangFak adapterDetailbarang = new AdapterDetailbarangFak(PrintFakturActivity.this, -1, listBarang, PrintFakturActivity.this);
                                lvListView1.setAdapter(adapterDetailbarang);
                            }
                            llcontent.setVisibility(View.VISIBLE);
                            Log.e("idTransaksi: ", idTransaksi);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Snack("Data Tidak Ditemukan");
//                            list_trans.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);

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
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PrintFakturActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Terjadi Kesalahan\nIngin memuat ulang?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    setupFaktur(dataprint);
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
        dialog1.dismiss();


    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(llcontent, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

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


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
//            //resume tasks needing this permission
//        }
//    }

}
