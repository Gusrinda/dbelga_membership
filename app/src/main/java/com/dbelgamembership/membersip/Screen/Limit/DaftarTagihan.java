package com.dbelgamembership.membersip.Screen.Limit;

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
import com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.DetailTransaksi;
import com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.ModelDaftarTagihanDebet;
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.modelListTransaksi.Datum;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.CartActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.PrintFakturActivity;
import com.dbelgamembership.membersip.app.Adapter.AdapterDaftarSemuaTagihan;
import com.dbelgamembership.membersip.app.Adapter.AdapterListCart;
import com.dbelgamembership.membersip.databinding.ActivityDaftarTagihanBinding;
import com.developer.kalert.KAlertDialog;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarTagihan extends AppCompatActivity implements AdapterDaftarSemuaTagihan.AdapterListSemuaTagihan {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityDaftarTagihanBinding binding;
    private SessionManager sessionManager;
    private List<DetailTransaksi> daftarTagihan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarTagihanBinding.inflate(getLayoutInflater());
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

        setupDaftarTagihanUser();

    }

    private void setupDaftarTagihanUser() {
        final ProgressDialog progressDialog = ProgressDialog.show(DaftarTagihan.this, "Loading", "Setting Up Data ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doDaftarTagihanUser(sessionManager.getPID());

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response.body()));
                    boolean success = obj.getBoolean("success");
                    String msgServer = obj.get("msgServer").toString();

                    binding.rvTagihan.setAdapter(null);

                    daftarTagihan.clear();

                    if (success) {

                        Gson gson = new Gson();
                        ModelDaftarTagihanDebet modelDaftarTagihanDebet = gson.fromJson(response.body(), ModelDaftarTagihanDebet.class);

                        assert modelDaftarTagihanDebet != null;

                        if (modelDaftarTagihanDebet.getMsgServer().getDetailTransaksi().size() > 0) {

                            daftarTagihan = modelDaftarTagihanDebet.getMsgServer().getDetailTransaksi();

                            Collections.sort(daftarTagihan, new Comparator<DetailTransaksi>() {
                                @Override
                                public int compare(DetailTransaksi datum, DetailTransaksi t1) {
                                    return t1.getCreatedAt().compareToIgnoreCase(datum.getCreatedAt());
                                }

                            });

                            AdapterDaftarSemuaTagihan adapterDaftarSemuaTagihan = new AdapterDaftarSemuaTagihan(DaftarTagihan.this, daftarTagihan, DaftarTagihan.this);
                            binding.rvTagihan.setAdapter(adapterDaftarSemuaTagihan);

                        } else {
                            Toast.makeText(DaftarTagihan.this, "Tidak ada daftar transaksi !", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(DaftarTagihan.this, msgServer, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + msgServer);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
                finish();
            }
        });

    }

    @Override
    public void onRowDetailTransaksi(DetailTransaksi item) {
        new KAlertDialog(DaftarTagihan.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Lihat Transaksi")
                .setContentText("Anda ingin melihat detail transaksi " + item.getPembayaranCode() + " ?\n\n")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.material_deep_orange_600, DaftarTagihan.this)
                .cancelButtonColor(R.color.merahBelga, DaftarTagihan.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(DaftarTagihan.this, PrintFakturActivity.class);
                        String DataOOS = item.getPembayaranCode();
                        Log.e(TAG, "onRowAdapterListTransactionClicked: " + DataOOS);
                        intent.putExtra("DATAPRINT", DataOOS);
                        intent.putExtra("FAKTUR", true);
                        startActivity(intent);
                    }
                })
                .setCancelText("Tidak")
                .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        kAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}