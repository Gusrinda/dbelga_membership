package com.dbelgamembership.membersip.Screen.Limit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.DetailTransaksi;
import com.dbelgamembership.membersip.Model.ModelDaftarTagihanDebet.ModelDaftarTagihanDebet;
import com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan.DaftarPelunasan;
import com.dbelgamembership.membersip.Model.ModelRiwayatPelunasanTagihan.ModelRiwayatPelunasanTagihan;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.PembayaranTransfer.TransferTagihan;
import com.dbelgamembership.membersip.app.Adapter.AdapterDaftarSemuaTagihan;
import com.dbelgamembership.membersip.app.Adapter.AdapterRiwayatPelunasanTagihan;
import com.dbelgamembership.membersip.databinding.ActivityRiwayatTagihanBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatTagihan extends AppCompatActivity implements AdapterRiwayatPelunasanTagihan.AdapterRiwayatPelunasan {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityRiwayatTagihanBinding binding;
    private SessionManager sessionManager;

    private List<DaftarPelunasan> daftarPelunasan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiwayatTagihanBinding.inflate(getLayoutInflater());
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

        setupRiwayatPelunasanTagihan();

    }

    private void setupRiwayatPelunasanTagihan() {
        final ProgressDialog progressDialog = ProgressDialog.show(RiwayatTagihan.this, "Loading", "Setting Up Data ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> call = apiInterface.doRiwayatPelunasanTagihan(sessionManager.getPID());


        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response.body()));
                    boolean success = obj.getBoolean("success");
                    String msgServer = obj.get("msgServer").toString();

                    binding.rvTagihan.setAdapter(null);

                    daftarPelunasan.clear();

                    if (success) {

                        Gson gson = new Gson();
                        ModelRiwayatPelunasanTagihan modelRiwayatPelunasanTagihan = gson.fromJson(response.body(), ModelRiwayatPelunasanTagihan.class);

                        assert modelRiwayatPelunasanTagihan != null;

                        if (modelRiwayatPelunasanTagihan.getMsgServer().getDaftarPelunasan() != null) {
                            if (modelRiwayatPelunasanTagihan.getMsgServer().getDaftarPelunasan().size() > 0) {

                                daftarPelunasan = modelRiwayatPelunasanTagihan.getMsgServer().getDaftarPelunasan();

                                AdapterRiwayatPelunasanTagihan adapterRiwayatPelunasanTagihan = new AdapterRiwayatPelunasanTagihan(RiwayatTagihan.this, daftarPelunasan, RiwayatTagihan.this);
                                binding.rvTagihan.setAdapter(adapterRiwayatPelunasanTagihan);

                            } else {
                                Toast.makeText(RiwayatTagihan.this, "Tidak ada daftar transaksi !", Toast.LENGTH_SHORT).show();
                            }

                        } else  {
                            Toast.makeText(RiwayatTagihan.this, "Tidak ada daftar transaksi !", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(RiwayatTagihan.this, msgServer, Toast.LENGTH_SHORT).show();
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
    public void onPembayaranTransfer(DaftarPelunasan item, String kodePembayaran) {


        Intent intent = new Intent(RiwayatTagihan.this, TransferTagihan.class);

        if (item.getBankPayment().equals("BNI")){
            intent.putExtra("hasExtra", true);
            intent.putExtra("banks", "BNI");
            intent.putExtra("dataTagihan", String.valueOf((int) Double.parseDouble(item.getTotalPelunasan())));
            intent.putExtra("kode_payment", kodePembayaran);
            intent.putExtra("kode_tagihan", item.getCodePelunasan());
            startActivity(intent);

        } else {
            intent.putExtra("hasExtra", true);
            intent.putExtra("banks", "BRI");
            intent.putExtra("kode_payment", kodePembayaran);
            intent.putExtra("kode_tagihan", item.getCodePelunasan());

            startActivity(intent);
        }




    }
}