package com.dbelgamembership.membersip.Screen.Transaksi;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Constant;
import com.dbelgamembership.membersip.Helper.HelperPrintUniversal.AsyncBluetoothEscPosPrint;
import com.dbelgamembership.membersip.Helper.HelperPrintUniversal.AsyncEscPosPrinter;
import com.dbelgamembership.membersip.Model.modelListTransaksi.DetailKekurangan;
import com.dbelgamembership.membersip.Model.responseCancel.ResponseCancel;
import com.dbelgamembership.membersip.Screen.Katalog.Model.ModelPostSetPayment;
import com.dbelgamembership.membersip.Screen.Limit.BayarTagihan;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.Screen.PembayaranTransfer.TransferPayment;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterDetailbarang;
import com.dbelgamembership.membersip.DialogFragment.RiwayatTransaksiQrFragment;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.modelListTransaksi.Datum;
import com.dbelgamembership.membersip.Model.modelListTransaksi.Detail;
import com.dbelgamembership.membersip.Model.modelListTransaksi.ModelListTransaksi;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterListPembatalan;
import com.dbelgamembership.membersip.databinding.ActivityBuktibayarNewBinding;
import com.dbelgamembership.membersip.databinding.PopupMetodePembayaranBinding;
import com.dbelgamembership.membersip.databinding.PopupPembatalanTransaksiBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import retrofit2.Call;
import retrofit2.Callback;

public class
PrintActivity extends AppCompatActivity implements AdapterDetailbarang.AdapterDetailbarangCallback, AdapterListPembatalan.AdapterListGudangCallback {
    public final static int QRcodeWidth = 500;
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final String IMAGE_DIRECTORY = "/QRcodeBELGA/Sales";
    public static ArrayList<HashMap<String, String>> arrayDetailOrder = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> arrayDetail = new ArrayList<HashMap<String, String>>();
    public static String idTransaksi, soCode, sales, costumer, alamatKostumer, alamatKirim, nomorKostumer, tanggalKirim, ongkosKirimText;
    public static float grandTotal;
    public static double ongkosKirim, totalDiskonan, totalBelanja;
    Button mDisc;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mBluetoothDevice;
    Bitmap bitmap;
    private Uri ImageUri;
    ImageView iv, backIc, imgBarcode;
    TextView so_code;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    SimpleAdapter simpleAdapter;
    ListView lisssss;
    String dateNow;
    String path;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private boolean isCOD = true;
    private String imageString = "";

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
    private Boolean isDoingPayment = false;

    private ActivityBuktibayarNewBinding binding;
    private boolean fromNotifikasi = false;

    private boolean isOnCreate = true;

    @Override
    protected void onResume() {
        super.onResume();

        if (!isOnCreate) {
            accessWebService();
        }

    }

    @Override
    public void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        binding = ActivityBuktibayarNewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
//        setContentView(R.layout.review_buktipembayaran);
        setContentView(view);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgBarcode = findViewById(R.id.image_qrCode);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (takeorder) {
                    Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (fromNotifikasi) {
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                } else {
                    finish();
                }
            }
        });

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
            isOnCreate = false;
            takeorder = getIntent().getBooleanExtra("TAKEORDER", false);
            dataSO = getIntent().getStringExtra("DATAPRINT");
            isDoingPayment = getIntent().getBooleanExtra("isPayment", false);
            fromNotifikasi = getIntent().getBooleanExtra("isFromNotifikasi", false);
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


        binding.contentBuktiBayar.layoutBtnPembatalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popOutPembatalanBelanja();
            }
        });

    }

    private void setupOpenWebView() {

        String urlWebView = "https://www.google.com/";
        Intent intent = new Intent(PrintActivity.this, WebViewPembayaranActivity.class);
        intent.putExtra("url", urlWebView);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        if (resultCode == Activity.RESULT_OK) {
//            Log.e("TAG", "Path:" + ImagePicker.Companion.getFilePath(data));
            Uri uri = data.getData();
            ImageUri = uri;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
                Log.e(TAG, "onActivityResult: " + bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            popupMetodePembayaranBinding.imgBuktiTransfer.setImageURI(ImageUri);
            popupMetodePembayaranBinding.imgBuktiTransfer.setVisibility(View.VISIBLE);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private PopupPembatalanTransaksiBinding popupPembatalanTransaksiBinding;

    private void popOutPembatalanBelanja() {
        popupPembatalanTransaksiBinding = PopupPembatalanTransaksiBinding.inflate(getLayoutInflater());
        View view = popupPembatalanTransaksiBinding.getRoot();

        dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();


        List<DetailKekurangan> daftarItemKekurangan = new ArrayList<>();
        popupPembatalanTransaksiBinding.rvItemPembatalan.setAdapter(null);

        daftarItemKekurangan = b.getDetailKekurangan();
        AdapterListPembatalan adapterListPembatalan = new AdapterListPembatalan(PrintActivity.this, daftarItemKekurangan, PrintActivity.this);
        popupPembatalanTransaksiBinding.rvItemPembatalan.setAdapter(adapterListPembatalan);

        popupPembatalanTransaksiBinding.produkClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        popupPembatalanTransaksiBinding.layoutBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(PrintActivity.this);
                builder1.setTitle("Konfirmasi");
                builder1.setMessage("Lanjut batalkan transaksi " + b.getCode() + " ?");
                builder1.setCancelable(false);
                builder1.setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder1.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        url = Http.server + "transaction/cancel?code=" + b.get(finalI).getCode();
//                        Log.e(TAG, "onClick: YES" + url);
//                        SimpanPost(null);
                        dialog.dismiss();
                        methodCancelTransaksi();
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
        });


    }

    private void methodCancelTransaksi() {
        final ProgressDialog progressDialog = ProgressDialog.show(PrintActivity.this, "Loading", "Canceling Transaction ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ResponseCancel> call = apiInterface.doCancelTransaksi(b.getCode(), b.getPembayaranCode());

        call.enqueue(new Callback<ResponseCancel>() {
            @Override
            public void onResponse(Call<ResponseCancel> call, retrofit2.Response<ResponseCancel> response) {
                try {
                    progressDialog.dismiss();
                    if (response != null) {
                        ResponseCancel object = response.body();
                        if (object.getDescription().equals("Update success!")) {
                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e(TAG, "onResponse: " + object.getDescription());
                            Toast.makeText(PrintActivity.this, object.getDescription(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage() + Arrays.toString(e.getStackTrace()));
                    Toast.makeText(PrintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseCancel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private PopupMetodePembayaranBinding popupMetodePembayaranBinding;

    private void popOutMetodePembayaran() {

        popupMetodePembayaranBinding = PopupMetodePembayaranBinding.inflate(getLayoutInflater());
        View view = popupMetodePembayaranBinding.getRoot();

        dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        popupMetodePembayaranBinding.txtGrandTotal.setText("Rp. " + nf.format(grandTotal));

        popupMetodePembayaranBinding.radioGroup.check(popupMetodePembayaranBinding.radioCOD.getId());

        popupMetodePembayaranBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == popupMetodePembayaranBinding.radioCOD.getId()) {
                    popupMetodePembayaranBinding.layoutCOD.setVisibility(View.VISIBLE);
                    popupMetodePembayaranBinding.layoutTransfer.setVisibility(View.GONE);
                    isCOD = true;
                    bitmap = null;
                    popupMetodePembayaranBinding.imgBuktiTransfer.setVisibility(View.GONE);
                } else {
                    popupMetodePembayaranBinding.layoutCOD.setVisibility(View.GONE);
                    popupMetodePembayaranBinding.layoutTransfer.setVisibility(View.VISIBLE);
                    isCOD = false;
                    bitmap = null;
                }
            }
        });

        popupMetodePembayaranBinding.btnUploadBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(PrintActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        popupMetodePembayaranBinding.layoutBtnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCOD) {
//                    Toast.makeText(PrintActivity.this, "Pembayaran COD", Toast.LENGTH_SHORT).show();

                    AlertDialog alertDialog = new AlertDialog.Builder(PrintActivity.this).create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setTitle("Peringatan");
                    alertDialog.setMessage("Yakin memilih metode pembayaran COD ?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    prosesPemilihanPayment("COD", imageString);
                                }
                            });
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "TIDAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();

                } else {

                    if (bitmap != null) {
//                        Toast.makeText(PrintActivity.this, "Pembayaran TRANSFER", Toast.LENGTH_SHORT).show();

                        imageString = imageToString(bitmap);

                        AlertDialog alertDialog = new AlertDialog.Builder(PrintActivity.this).create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.setTitle("Peringatan");
                        alertDialog.setMessage("Yakin memilih metode pembayaran TRANSFER ?");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        prosesPemilihanPayment("TRANSFER", imageString);
                                    }
                                });
                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "TIDAK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();

                    } else {
                        Toast.makeText(PrintActivity.this, "Upload bukti transfer terlebih dahulu !", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        popupMetodePembayaranBinding.produkClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void prosesPemilihanPayment(String tipePayment, String imageString) {

        final ProgressDialog progressDialog = ProgressDialog.show(PrintActivity.this, "Loading", "Setting Up Payment ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doSetPayment(new ModelPostSetPayment(dataSO, tipePayment, imageString, null, null));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {

                    Log.e(TAG, "onResponse: " + response.toString());

                    if (response != null) {
                        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
                        Call<String> callUpdate = apiInterface.doUpdateSO(dataSO, "confirmation");

                        callUpdate.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                progressDialog.dismiss();
                                Log.e(TAG, "onResponse: " + response.toString());
                                Toast.makeText(PrintActivity.this, "Pilih metode pembayaran selesai , tunggu konfirmasi admin dan barang akan segera dikirim kurir dBelga", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });

                    } else {
                        Toast.makeText(PrintActivity.this, "KESALAHAN POSTING PEMBAYARAN !", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(PrintActivity.this, "ERROR !", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse Error message : " + e.getLocalizedMessage());
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP);
    }

    ModelListTransaksi modelListTransaction;
    Datum b;

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
                            modelListTransaction = gson.fromJson(String.valueOf(response), ModelListTransaksi.class);
                            b = modelListTransaction.getData().getData().get(0);
                            idTransaksi = String.valueOf(b.getId());
                            grandTotal = Float.parseFloat(String.valueOf(b.getGrandtotal()));
                            ongkosKirim = 0;

                            String idGudang = String.valueOf(b.getGudang());

                            for (int i = 0; i < modelGudangs.size(); i++) {
                                if (idGudang == modelGudangs.get(i).getIdGudang()) {
                                    binding.contentBuktiBayar.txtNamaToko.setText(" : Toko " + modelGudangs.get(i).getNamaGudang());
                                }
                            }


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

                            if (b.getStatus().equals("cancel approval")) {
                                binding.contentBuktiBayar.layoutBtnPembatalan.setVisibility(View.VISIBLE);
                            } else {
                                binding.contentBuktiBayar.layoutBtnPembatalan.setVisibility(View.GONE);
                            }

                            nomorKostumer = b.getNomorCustomer();
                            tanggalKirim = b.getTanggalKirim();

                            ongkosKirimText = String.valueOf(ongkosKirim);

                            if (ongkosKirimText.equals("0")) {
                                linearOngkir.setVisibility(View.GONE);
                            } else {
                                viewGarisTotal.setVisibility(View.VISIBLE);
                                linearOngkir.setVisibility(View.VISIBLE);
                                txtOngkosKirim.setText("Rp. " + nf.format(Double.parseDouble(ongkosKirimText)));
                                Log.e(TAG, "Ongkos Kirim : " + ongkosKirimText);
                            }

                            Log.e(TAG, "Nomor telepon : " + b.getNomorCustomer());
                            Log.e(TAG, "Alamat Kirim : " + String.valueOf(b.getAlamatPengiriman()));
                            Log.e(TAG, "Tanggal Kirim : " + b.getTanggalKirim());

                            tvKembalian.setVisibility(View.GONE);
                            noSoStatus.setText(b.getStatus().toUpperCase());
                            tvKembalian.setText("Rp." + nf.format(Double.parseDouble(String.valueOf(b.getGrandtotal()))));

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

                            double diskonBarang = 0;
                            double belanjaBarang = 0;
                            for (Detail barang : listBarang) {
                                HashMap<String, String> hashMap = new HashMap<>(); //create a hashmap to store the data in key value pair
                                hashMap.put("namaBrg", barang.getName());
                                Log.e(TAG, "Masuk1: " + barang.getName());
                                Log.e(TAG, "Masuk11: " + barang.getQtyStore());
                                Log.e(TAG, "Masuk12: " + barang.getQtyOutlet());
                                Log.e(TAG, "Masuk13: " + barang.getIndentValue());

                                double Qty = Double.parseDouble(barang.getQtyOutlet()) + Double.parseDouble(barang.getQtyStore()) + barang.getIndentValue();
                                hashMap.put("qtyUnit", Qty + " Unit");
                                Log.e(TAG, "Masuk2: " + barang.getName());
                                hashMap.put("qty", String.valueOf(Qty));
                                Log.e(TAG, "Masuk3: " + Qty);
                                hashMap.put("harga", barang.getRealPrice() + "");
                                Log.e(TAG, "Masuk4: " + barang.getRealPrice());
                                hashMap.put("Code", barang.getCodeProduct() + "");
                                Log.e(TAG, "Masuk5: " + barang.getCodeProduct());

                                belanjaBarang = Double.parseDouble(barang.getRealPrice()) * Double.parseDouble(barang.getQtyOutlet());
                                Log.e(TAG, "Belanja Barang : " + belanjaBarang);

                                double totalDiskon = Double.parseDouble(barang.getTotalDiskon() == null ? "0" : barang.getTotalDiskon());

                                if (totalDiskon > 0) {
                                    hashMap.put("total", barang.getTotalSetelahDiskon() + "");
                                    diskonBarang = totalDiskon;
                                } else {
                                    diskonBarang = 0;
                                    hashMap.put("total", barang.getTotalSetelahDiskon() + "");
                                }

                                double totalDiskonMembership = Double.parseDouble(barang.getTotalDiskonMembership() == null ? "0" : barang.getTotalDiskonMembership());
                                Log.e(TAG, "Cek diskon barang : " + barang.getTotalDiskon());
                                Log.e(TAG, "Cek diskon membership : " + totalDiskonMembership);
                                hashMap.put("diskon", String.valueOf(diskonBarang));
                                totalBelanja += belanjaBarang;
                                Log.e(TAG, "Total Belanja : " + totalBelanja);
                                totalDiskonan += (diskonBarang + totalDiskonMembership);
                                arrayDetail.add(hashMap);
                                Log.e(TAG, "Tambah " +
                                        " detail: " + arrayDetail);
                            }

                            if (totalDiskonan > 0) {
                                viewGarisTotal.setVisibility(View.VISIBLE);
                                linearDiskon.setVisibility(View.VISIBLE);
                                txtDiskon.setText(" - Rp. " + nf.format(totalDiskonan));
                            }

                            if (totalBelanja != b.getGrandtotal()) {
                                viewGarisTotal.setVisibility(View.VISIBLE);
                                linearBelanja.setVisibility(View.VISIBLE);
                                txtBelanja.setText("Rp. " + nf.format(totalBelanja));
                            }


                            if (b.getVoucher() != null) {
                                if (b.getVoucher()) {
                                    binding.contentBuktiBayar.linearVoucher.setVisibility(View.VISIBLE);
                                    binding.contentBuktiBayar.txtKodeVoucher.setText("VOC : " + b.getVoucherCode());
                                    binding.contentBuktiBayar.txtNominalVoucher.setText("- Rp. " + nf.format(Double.parseDouble(b.getVoucherNominal())));
                                }
                            }

                            if (b.getVoucherSuplier() != null) {
                                if (b.getVoucherSuplier()) {
                                    binding.contentBuktiBayar.linearVoucherSuplier.setVisibility(View.VISIBLE);
                                    binding.contentBuktiBayar.txtKodeVoucherSuplier.setText("VOC SP : " + b.getVoucherCodeSuplier());
                                    binding.contentBuktiBayar.txtNominalVoucherSuplier.setText("- Rp. " + nf.format(Double.parseDouble(b.getVoucherNominalSuplier())));
                                }
                            }

                            binding.contentBuktiBayar.grandTotal.setText("Rp. " + nf.format(b.getGrandtotal()));

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


                            if (b.getStatus().equals("payment")) {
                                binding.contentBuktiBayar.layoutPilihMetode.setVisibility(View.VISIBLE);

                                binding.contentBuktiBayar.layoutPilihMetode.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String lastTwo = dataSO.substring(dataSO.length() - 6);
                                        String customerCode = String.valueOf(b.getGudang()) + sessionManager.getPID() + lastTwo;

                                        Intent intent = new Intent(PrintActivity.this, TransferPayment.class);
                                        intent.putExtra("hasExtra", true);
                                        intent.putExtra("banks", b.getBankPayment() == null ? "" : b.getBankPayment());
                                        intent.putExtra("kode_payment", customerCode);
                                        intent.putExtra("kode_so", dataSO);
                                        intent.putExtra("kode_faktur", b.getPembayaranCode());
                                        intent.putExtra("data_payment", (Parcelable) (b.getDetailPaymentBni() == null ? null : b.getDetailPaymentBni()));

                                        startActivity(intent);
                                    }
                                });
                            }

                            if (isDoingPayment) {

                                isDoingPayment = false;

                                String lastTwo = dataSO.substring(dataSO.length() - 6);
                                String customerCode = String.valueOf(b.getGudang()) + sessionManager.getPID() + lastTwo;

                                Intent intent = new Intent(PrintActivity.this, TransferPayment.class);
                                intent.putExtra("hasExtra", true);
                                intent.putExtra("banks", b.getBankPayment() == null ? "" : b.getBankPayment());
                                intent.putExtra("kode_payment", customerCode);
                                intent.putExtra("kode_faktur", b.getPembayaranCode());
                                intent.putExtra("kode_so", dataSO);
                                intent.putExtra("data_payment", (Parcelable) (b.getDetailPaymentBni() == null ? null : b.getDetailPaymentBni()));

                                startActivity(intent);
                            }


                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage() + Arrays.toString(e.getStackTrace()));
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

    //AkhirNewPrint

    @Override
    public void onBackPressed() {
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
        setResult(RESULT_CANCELED);
        if (fromNotifikasi) {
            startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        }
        finish();
    }

    @Override
    public void onRowAdapterDetailbarangClicked(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}