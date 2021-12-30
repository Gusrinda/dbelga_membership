package com.dbelgamembership.membersip.Screen.Promo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelDataRegister;
import com.dbelgamembership.membersip.Model.ModelKatalog;
import com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.ResponseCekVerifikasi;
import com.dbelgamembership.membersip.Model.modelBarang.Datum;
import com.dbelgamembership.membersip.Model.modelBarang.ModelBarang;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Model.DummyPromo;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.Registrasi.RegistrasiNext;
import com.dbelgamembership.membersip.app.Adapter.AdapterListBarang;
import com.dbelgamembership.membersip.databinding.ActivityKatalogPromoBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KatalogPromo extends AppCompatActivity implements AdapterListBarang.AdapterListBarangCallback {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityKatalogPromoBinding binding;
    private SessionManager sessionManager;
    AdapterListBarang adapterListSearchBarang;
    ArrayList<ModelKatalog> arrayBarangPromo = new ArrayList<ModelKatalog>();

    DummyPromo dataPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKatalogPromoBinding.inflate(getLayoutInflater());
        sessionManager = new SessionManager(this);

        setContentView(binding.getRoot());

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().hasExtra("hasExtra")) {
            dataPromo = getIntent().getParcelableExtra("dataPromo");
            setupKatalogPromo();
        } else {
            finish();
        }

    }

    private void setupKatalogPromo() {
        binding.toolbar.setTitle(dataPromo.getNamaPromo());
        binding.gambarBanner.setImageResource(dataPromo.getAlamatGambar());

        searchKatalogPromo();

    }

    private void searchKatalogPromo() {
        final ProgressDialog dialog1 = new ProgressDialog(KatalogPromo.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doGetKatalogPromo(sessionManager.getKeySetGudangPencarian(), "");
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                dialog1.dismiss();
                if (response != null) {
                    Gson gson = new Gson();
                    String responseX = String.valueOf(response.body());
                    JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                    boolean success = root.get("success").getAsBoolean();
                    Log.e("", "Test : " + success);
                    if (!success) {
                        Toast.makeText(KatalogPromo.this, "Error" + root.get("msgServer"), Toast.LENGTH_SHORT).show();
                    } else {
                        ModelBarang modelListItem = gson.fromJson(String.valueOf(response.body()), ModelBarang.class);
                        List<Datum> modelItem = modelListItem.getMsgServer().getData();

                        if (modelItem.size() > 0) {
                            arrayBarangPromo.clear();
                            binding.gridview.setAdapter(null);
                            for (Datum itemData : modelItem) {
                                ModelKatalog pm = new ModelKatalog();
                                pm.setId(String.valueOf(itemData.getId()));
                                pm.setNama_barang(itemData.getName());

                                String deskripsi = "";
                                if (itemData.getDeskripsi() == null || itemData.getDeskripsi().isEmpty()) {
                                    deskripsi = "Deskripsi Kosong";
                                } else {
                                    deskripsi = itemData.getDeskripsi();
                                }

                                pm.setDeskripsi(deskripsi);
                                pm.setMerk_barang(String.valueOf(itemData.getMerekProduk()));
                                pm.setKategori_barang(itemData.getNamaKategori());
                                pm.setKode_barang(itemData.getCode());
                                pm.setStok(String.valueOf(itemData.getStok()));

                                String satuan = "";
                                if (itemData.getSatuanKemasan() != null) {
                                    satuan = itemData.getSatuanKemasan();
                                } else {
                                    satuan = "unit";
                                }
                                pm.setSatuan_kemasan(satuan);

                                pm.setBarcode(itemData.getBarcode());
                                pm.setImages(itemData.getImages());
                                pm.setHarga_barang(itemData.getPrice());
                                pm.setHarga_2((itemData.getPriceDua() == null) ? "0" : itemData.getPriceDua());
                                pm.setHarga_3((itemData.getPriceTiga() == null) ? "0" : itemData.getPriceTiga());

                                String batasan1 = String.valueOf(itemData.getQtyHarga1());
                                String batasan2 = String.valueOf(itemData.getQtyHarga2());
                                String batasan3 = String.valueOf(itemData.getQtyHarga3());
                                pm.setBatasan1(batasan1);
                                pm.setBatasan2(batasan2);
                                pm.setBatasan3(batasan3);

//                                            arrayKategori.add(itemData.getNamaKategori());
                                arrayBarangPromo.add(pm);
                            }
//                                        stockArr = new ArrayList<String>(new LinkedHashSet<String>(arrayKategori)).toArray(new String[0]);
                            adapterListSearchBarang = new AdapterListBarang(KatalogPromo.this, arrayBarangPromo, KatalogPromo.this);
                            binding.gridview.setAdapter(null);
                            binding.gridview.setAdapter(adapterListSearchBarang);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                dialog1.dismiss();
                Toast.makeText(KatalogPromo.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + "\n" + Arrays.toString(t.getStackTrace()));
            }
        });

    }


    @Override
    public void AdapterListBarangClicked(ModelKatalog position) {

    }

}