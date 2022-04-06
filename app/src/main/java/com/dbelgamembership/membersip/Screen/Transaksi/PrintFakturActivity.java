package com.dbelgamembership.membersip.Screen.Transaksi;

/**
 * Created by hp on 12/23/2016.
 */

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.dbelgamembership.membersip.Model.ModelPayment.Datum;
import com.dbelgamembership.membersip.Model.ModelPayment.DetailBarangTebu;
import com.dbelgamembership.membersip.Model.ModelPayment.Item;
import com.dbelgamembership.membersip.Model.modelArrayDetailBarangOrder;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterDetailbarangFak;
import com.dbelgamembership.membersip.DialogFragment.RiwayatTransaksiQrFragment;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelPayment.AddItem;
import com.dbelgamembership.membersip.Model.ModelPayment.ModelPayment;

import com.dbelgamembership.membersip.Model.ModelPayment.OrderDetail;
import com.dbelgamembership.membersip.Model.ModelPayment.PaymentDetail;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.databinding.ActivityBuktifakturNewBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
    public static List<modelArrayDetailBarangOrder> arrayDetail = new ArrayList<modelArrayDetailBarangOrder>();
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
    //    @BindView(R.id.linearContent)
//    RelativeLayout llcontent;
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
    String dateNow, path, timeNow;
    private String cardNumber, cardHolder;
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
    private double total = 0;
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
    private List<AddItem> listBarangTambah = new ArrayList<>();
    private SessionManager sessionManager;
    private String url = "";
    private String printBayar, printKembalian, printTotal;
    long grandTOTAL;
    private float amountCharge;
    private float amountAnotherPayment;
    private float pembayaranTunai;


    ActivityBuktifakturNewBinding binding;

    @Override
    public void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        binding = ActivityBuktifakturNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);
        binding.contentBuktiFaktur.linearContent.setVisibility(View.GONE);
        linearCharge.setVisibility(View.GONE);
        sessionManager = new SessionManager(this);
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


    }

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
                            ModelPayment modelListFaktur = gson.fromJson(String.valueOf(response), ModelPayment.class);
                            Datum b = modelListFaktur.getData().getData().get(0);
                            idTransaksi = String.valueOf(b.getId());
                            grandTotal = (long) (Double.parseDouble(b.getTotalPaymentPaid()) - Double.parseDouble(b.getChange()));
                            String cok1 = String.valueOf(b.getTotalPaymentPaid());
                            String cok2 = String.valueOf(b.getChange());

                            String idGudang = String.valueOf(b.getGudang());

                            for (int i = 0; i < modelGudangs.size(); i++) {
                                if (idGudang == modelGudangs.get(i).getIdGudang()) {
                                    binding.contentBuktiFaktur.txtNamaToko.setText(" : Toko " + modelGudangs.get(i).getNamaGudang());
                                }
                            }

                            //BAGIAN AKHIR

//                            GTCOKCOKCOKCOCKCOK = (Integer.parseInt(cok1)-Integer.parseInt(cok2));
                            Log.e(TAG, "cok1 : " + cok1 + "cok2 : " + cok2);
                            soCode = b.getPembayaranCode();
                            Log.e(TAG, "onCreate: " + soCode);
                            sales = String.valueOf(b.getCreateuser());
                            tvdate.setText(b.getDateTransaction() + "");
                            tvSO.setText(b.getPembayaranCode());
                            tvStatus.setText(b.getStatus() + "");

                            tvOngkosKirim.setText("Rp. " + nf.format(Double.parseDouble(b.getOngkosKirim())));
                            ONGKIR_COK = (int) Double.parseDouble(b.getOngkosKirim());
                            printBayar = String.valueOf(b.getTotalPaymentPaid());
                            ALAMAT_KIRIM = b.getAlamatPengiriman() == null ? "" : b.getAlamatPengiriman();
                            NAMA_CUSTOMER = b.getCustomer();
                            printKembalian = String.valueOf(b.getChange());
                            FLAG_DP = String.valueOf(b.getFlagDp());
                            if (b.getFlagDp() == true) {
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
                                tvKembalian.setText("Rp. " + nf.format(Double.parseDouble(b.getChange())));
                            }
                            Log.e(TAG, "onResponse: FLAGDP" + FLAG_DP);


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
                            for (PaymentDetail payment : b.getPaymentDetail()) {
                                Log.e(TAG, "onCreate: PAYMENT" + payment.getTotal());
                                Double dnum = Double.parseDouble(payment.getTotal());
                                grandTotal += dnum.intValue();
                                if (payment.getPaymentType().equals("PAY_TYPE_TUNAI")) {
                                    pembayaranTunai = new Float(payment.getTotal());
                                    tvPembayaranTunai.setText("Rp. " + nf.format(pembayaranTunai));
                                }
                            }
                            tvTotalPembayaran.setText("Rp. " + nf.format(Double.parseDouble(b.getTotalPaymentPaid())));
                            dateNow = b.getDateTransaction();
                            timeNow = b.getUpdatedAt().substring(11);

                            for (PaymentDetail DetailCheckOut : b.getPaymentDetail()) {
                                if (DetailCheckOut.getPaymentType().equals("PAY_TYPE_CREDIT") || DetailCheckOut.getPaymentType().equals("PAY_TYPE_TRANSFER")
                                        || DetailCheckOut.getPaymentType().equals("PAY_TYPE_DEBET")) {
                                    if (DetailCheckOut.getPaymentType().equals("PAY_TYPE_CREDIT")) {
                                        tipeMetode = "KREDIT " + DetailCheckOut.getOptionBank().substring(0, 8);
                                    } else if (DetailCheckOut.getPaymentType().equals("PAY_TYPE_TRANSFER")) {
                                        tipeMetode = "TRANSFER " + (DetailCheckOut.getOptionBank().length() > 7 ? DetailCheckOut.getOptionBank().substring(0, 8) : DetailCheckOut.getOptionBank().toString());
                                    } else if (DetailCheckOut.getPaymentType().equals("PAY_TYPE_DEBET")) {
                                        tipeMetode = "DEBIT " + DetailCheckOut.getOptionBank().substring(0, 8);
                                    }
                                    cardNumber = DetailCheckOut.getOptionsAccount();
                                    cardHolder = DetailCheckOut.getCardHolder();
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
                                List<Item> itemss = barangCheckout.getItems();
                                for (int i = 0; i < itemss.size(); i++) {
                                    Item barang = itemss.get(i);
                                    HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
                                    hashMap.put("namaBrg", barang.getName());
                                    double Qty = Double.parseDouble(barang.getQtyOutlet()) + Double.parseDouble(barang.getQtyStore()) + barang.getIndentValue();
                                    hashMap.put("qtyUnit", Qty + " Unit");
                                    hashMap.put("qty", String.valueOf(Qty));
                                    hashMap.put("harga", barang.getRealPrice() + "");
                                    hashMap.put("Code", barang.getCodeProduct() + "");
                                    hashMap.put("total", Double.parseDouble(barang.getTotal()) - Double.parseDouble(barang.getTotalDiskon()) + "");
                                    hashMap.put("nominal_diskon", barang.getTotalDiskon() + "");
                                    listBarang.add(barang);

                                    double totalBarang = Double.parseDouble(barang.getRealPrice()) * Double.parseDouble(barang.getQtyOutlet());
                                    double totalDiskon = Double.parseDouble(barang.getTotalDiskon()) + Double.parseDouble(barang.getTotalDiskonMembership() == null ? "0" : barang.getTotalDiskonMembership());

                                    total += (totalBarang - totalDiskon);
                                    GTCOKCOKCOKCOCKCOK += (totalBarang - totalDiskon);
                                }
                            }

                            Log.e(TAG, "onResponse: GTCOKCOKCOK SAMPAI SO : " + GTCOKCOKCOKCOCKCOK);

                            for (AddItem barangAdd : b.getAddItem()) {
                                HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
                                hashMap.put("namaBrg", barangAdd.getName());
                                double Qty = Double.parseDouble(barangAdd.getQty());
                                double QtyDiskon = Double.parseDouble(barangAdd.getQtyDiskon() == null ? "0" : barangAdd.getQtyDiskon());
                                double tots = Double.parseDouble(barangAdd.getCustomerPrice());
                                hashMap.put("qtyUnit", Qty + " Unit");
                                hashMap.put("qty", String.valueOf(Qty));
                                hashMap.put("harga", tots + "");
                                hashMap.put("Code", barangAdd.getCodeProduct() + "");
                                hashMap.put("total", Qty * tots + "");
                                hashMap.put("nominal_diskon", barangAdd.getDiskonPotongan() + "");
                                listBarangTambah.add(barangAdd);
                                double totalDiskon = (int) (Double.parseDouble(barangAdd.getDiskonPotongan() == null ? "0" : barangAdd.getDiskonPotongan()) * QtyDiskon);
                                totalDiskon += (Double.parseDouble(barangAdd.getTotalDiskonMembership() == null ? "0" : barangAdd.getTotalDiskonMembership()));
                                total += (Qty * Double.parseDouble(barangAdd.getCustomerPrice()));
                                GTCOKCOKCOKCOCKCOK += (Qty * Double.parseDouble(barangAdd.getCustomerPrice()));
                                GTCOKCOKCOKCOCKCOK -= totalDiskon;
                            }

                            Log.e(TAG, "onResponse: GTCOKCOKCOK BARANG ADD : " + GTCOKCOKCOKCOCKCOK);

                            for (int i = 0; i < listBarang.size(); i++) {
                                for (int j = 0; j < listBarangTambah.size(); j++) {

                                    Log.e(TAG, "onResponse CODE PRODUCT: " + listBarang.get(i).getCodeProduct());
                                    Log.e(TAG, "onResponse : " + listBarangTambah.get(j).getCodeProduct());

                                    double qtyBarangA = Double.parseDouble(listBarang.get(i).getQtyOutlet());
                                    double qtyBarangB = Double.parseDouble(String.valueOf(listBarangTambah.get(j).getQty()));

                                    if (listBarang.get(i).getCodeProduct().equals(listBarangTambah.get(j).getCodeProduct())
                                            && qtyBarangA == qtyBarangB) {

                                        double totalBarang = (Double.parseDouble(String.valueOf(listBarang.get(i).getTotalSetelahDiskon())) - Double.parseDouble(listBarang.get(i).getTotalDiskon()));

                                        total -= totalBarang;
                                        Log.e(TAG, "onResponse: " + listBarang.get(i).getName());
                                        Log.e(TAG, "onResponse: " + listBarang.get(i).getTotal());
                                        GTCOKCOKCOKCOCKCOK -= totalBarang;
                                        listBarang.remove(i);
                                    }
                                }
                            }

                            Log.e(TAG, "onResponse: GTCOKCOKCOK list barang sama remove : " + GTCOKCOKCOKCOCKCOK);

                            for (int i = 0; i < listBarang.size(); i++) {
                                modelArrayDetailBarangOrder barang = new modelArrayDetailBarangOrder();
                                barang.setNamaBrg(listBarang.get(i).getName());
                                barang.setCode(listBarang.get(i).getCodeProduct());
                                barang.setHarga(listBarang.get(i).getRealPrice());
                                barang.setQty(Double.parseDouble(listBarang.get(i).getQtyOutlet()));
                                double Qty = Double.parseDouble(listBarang.get(i).getQtyOutlet());
                                double tots = Double.parseDouble(listBarang.get(i).getRealPrice());
                                double qtyDiskon = Double.parseDouble(listBarang.get(i).getQtyDiskon() == null ? "0" : listBarang.get(i).getQtyDiskon());
                                barang.setTotal(String.valueOf(Qty * tots));

                                double totalDiskon = (int) Double.parseDouble(listBarang.get(i).getTotalDiskon() == null ? "0" : listBarang.get(i).getTotalDiskon());
                                double diskonPerBarang = totalDiskon / qtyDiskon;

                                //Sementara yang dari SO seperti ini !
                                barang.setPotongan_diskon(String.valueOf(diskonPerBarang));
                                barang.setNominal_diskon(listBarang.get(i).getTotalDiskon());
                                barang.setQtyDiskon(qtyDiskon);
                                barang.setKeterangan("Order");

                                barang.setDiskonMembership(listBarang.get(i).getIsDiskonMembership() == null ? false : listBarang.get(i).getIsDiskonMembership());
                                barang.setPersenDiskonMemberhsip(listBarang.get(i).getPresentaseDiskonMembership() == null ? 0 : Double.parseDouble(listBarang.get(i).getPresentaseDiskonMembership()));
                                barang.setTotalDiskonMembership(listBarang.get(i).getTotalDiskonMembership() == null ? 0 : Double.parseDouble(listBarang.get(i).getTotalDiskonMembership()));
                                barang.setBarangTebus(false);

                                arrayDetail.add(barang);

                            }

                            for (int i = 0; i < listBarangTambah.size(); i++) {
                                modelArrayDetailBarangOrder barang = new modelArrayDetailBarangOrder();
                                barang.setNamaBrg(listBarangTambah.get(i).getName());
                                barang.setCode(listBarangTambah.get(i).getCodeProduct());
                                barang.setHarga(listBarangTambah.get(i).getCustomerPrice());
                                barang.setQty(Double.parseDouble(listBarangTambah.get(i).getQty()));
                                double Qty = Double.parseDouble(listBarangTambah.get(i).getQty());
                                double tots = Double.parseDouble(listBarangTambah.get(i).getCustomerPrice());
                                double qtyDiskon = Double.parseDouble(listBarangTambah.get(i).getQtyDiskon() == null ? "0" : listBarangTambah.get(i).getQtyDiskon());
                                double potonganDiskon = Double.parseDouble(listBarangTambah.get(i).getDiskon() == null ? "0" : listBarangTambah.get(i).getDiskon());
                                barang.setQtyDiskon(qtyDiskon);
                                barang.setTipeDiskon(listBarangTambah.get(i).getTypeDiskon());
                                barang.setPotongan_diskon(String.valueOf(potonganDiskon));
                                barang.setTotal(String.valueOf(Qty * tots));
                                barang.setNominal_diskon(String.valueOf(potonganDiskon));
                                barang.setKeterangan("Beli Langsung");

                                barang.setDiskonMembership(listBarangTambah.get(i).getIsDiskonMembership() == null ? false : listBarangTambah.get(i).getIsDiskonMembership());
                                barang.setPersenDiskonMemberhsip(listBarangTambah.get(i).getPresentaseDiskonMembership() == null ? 0 : Double.parseDouble(listBarangTambah.get(i).getPresentaseDiskonMembership()));
                                barang.setTotalDiskonMembership(listBarangTambah.get(i).getTotalDiskonMembership() == null ? 0 : Double.parseDouble(listBarangTambah.get(i).getTotalDiskonMembership()));
                                barang.setBarangTebus(false);

                                arrayDetail.add(barang);

                            }

                            List<modelArrayDetailBarangOrder> mergedList = new ArrayList<>();
                            for (modelArrayDetailBarangOrder p : arrayDetail) {
                                int index = mergedList.indexOf(p);
                                if (index != -1) {
                                    mergedList.set(index, mergedList.get(index).merge(p));
                                } else {
                                    mergedList.add(p);
                                }
                            }

                            arrayDetail.clear();
                            arrayDetail = mergedList;

                            for (int i = 0; i < mergedList.size(); i++) {
                                Log.e(TAG, "Merged Code : " + mergedList.get(i).getCode());
                                Log.e(TAG, "Merged QTY : " + mergedList.get(i).getQty());
                            }

                            if (b.getDetailBarangTebus().size() > 0) {

                                DetailBarangTebu selectedBarangTebus = b.getDetailBarangTebus().get(0);

                                int indexDelete = 0;
                                boolean isThereAreSame = false;

                                Log.d(TAG, "onResponse: SELECTED BARANG TEBUS CODE :: " + selectedBarangTebus.getBarangTebusCode());

                                for (int i = 0; i < arrayDetail.size(); i++) {

                                    Log.d(TAG, "onResponse: ARRAY DETAIL CODE :: " + arrayDetail.get(i).getCode());
                                    Log.d(TAG, "onResponse: ARRAY DETAIL QTY :: " + arrayDetail.get(i).getQty());
                                    Log.d(TAG, "\n");

                                    if (arrayDetail.get(i).getCode().equals(selectedBarangTebus.getBarangTebusCode()) && arrayDetail.get(i).getQty() == 0) {
                                        isThereAreSame = true;
                                        indexDelete = i;
                                    }

                                }

                                if (isThereAreSame) {
                                    arrayDetail.remove(indexDelete);
                                }

                                modelArrayDetailBarangOrder barangTebus = new modelArrayDetailBarangOrder();

                                barangTebus.setBarangTebus(true);
                                barangTebus.setCode(selectedBarangTebus.getBarangTebusCode());
                                barangTebus.setNamaBrg(selectedBarangTebus.getNamaProduk());
                                barangTebus.setHarga(selectedBarangTebus.getHargaNormal());

                                double diskon = Double.parseDouble(selectedBarangTebus.getHargaNormal()) - Double.parseDouble(selectedBarangTebus.getBarangTebusHarga());

                                barangTebus.setPotongan_diskon(String.valueOf((int) diskon));
                                barangTebus.setTotal(selectedBarangTebus.getBarangTebusHarga());

                                arrayDetail.add(barangTebus);

                                GTCOKCOKCOKCOCKCOK += Double.parseDouble(selectedBarangTebus.getBarangTebusHarga());

                            }

                            double besaranPotonganVoucher = 0;
                            if (b.getVoucher() != null) {
                                if (b.getVoucher()) {
//                                    isUsingVoucher = b.getVoucher();
//                                    dataVoucherDipilih = new DaftarVoucher();
//                                    dataVoucherDipilih.setCode(b.getVoucherCode());
//                                    dataVoucherDipilih.setNominal((int) Double.parseDouble(b.getVoucherNominal()));
                                    binding.contentBuktiFaktur.layoutVoucher.setVisibility(View.VISIBLE);
                                    binding.contentBuktiFaktur.txtKodeVoucher.setText("VOC : " + b.getVoucherCode());
                                    binding.contentBuktiFaktur.txtNominalVoucher.setText("- Rp. " + nf.format(Double.parseDouble(b.getVoucherNominal())));
                                    besaranPotonganVoucher = Double.parseDouble(b.getVoucherNominal());
                                }
                            }

                            Log.e("mergedList : ", String.valueOf(mergedList));

                            Log.e(TAG, "onCreate: " + arrayDetail.size());
                            grandTOTAL = (long) (Double.parseDouble(b.getTotalPaymentPaid()) - Double.parseDouble(b.getChange()));
                            GTCOKCOKCOKCOCKCOK += Double.parseDouble(b.getOngkosKirim());
                            GTCOKCOKCOKCOCKCOK -= besaranPotonganVoucher;
                            grand_total.setText("Rp. " + nf.format(GTCOKCOKCOKCOCKCOK));

                            if (!arrayDetail.isEmpty()) {
                                Log.e(TAG, "onResponse: ");
                                lvListView1.setVisibility(View.VISIBLE);
                                AdapterDetailbarangFak adapterDetailbarang = new AdapterDetailbarangFak(PrintFakturActivity.this, -1, arrayDetail, PrintFakturActivity.this);
                                lvListView1.setAdapter(adapterDetailbarang);
                            }

                            binding.contentBuktiFaktur.linearContent.setVisibility(View.VISIBLE);
                            Log.e("idTransaksi: ", idTransaksi);

                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage() + Arrays.toString(e.getStackTrace()));
                            Snack("Data Tidak Ditemukan");
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
                    Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
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
 
                } else if (error.networkResponse == null) {
                    dialog1.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PrintFakturActivity.this);
                    builder1.setTitle("Peringatan");
                    builder1.setMessage("Server not responding!\nTry again ?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    startActivity(getIntent());
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
        Snackbar snackbar = Snackbar.make(binding.contentBuktiFaktur.linearContent, string, Snackbar.LENGTH_LONG)
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

}
