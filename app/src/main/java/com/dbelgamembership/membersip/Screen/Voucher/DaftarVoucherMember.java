package com.dbelgamembership.membersip.Screen.Voucher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelVoucherCustomer.DaftarVoucher;
import com.dbelgamembership.membersip.Model.ModelVoucherCustomer.ModelVoucherCustomer;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Voucher.Dummy.AdapterListVoucherMember;
import com.dbelgamembership.membersip.databinding.ActivityDaftarVoucherMemberBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarVoucherMember extends AppCompatActivity implements AdapterListVoucherMember.AdapterListVoucherDummyCallback {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityDaftarVoucherMemberBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarVoucherMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setupVoucherMember();

    }

    List<DaftarVoucher> daftarVoucherMember = new ArrayList<>();
    AdapterListVoucherMember adapterListVoucherOmset;

    private void setupVoucherMember() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelVoucherCustomer> call = apiInterface.doGetVoucherMember(sessionManager.getPID());

        call.enqueue(new Callback<ModelVoucherCustomer>() {
            @Override
            public void onResponse(Call<ModelVoucherCustomer> call, Response<ModelVoucherCustomer> response) {
                try {
                    Log.e(TAG, "onResponse: " + response);
                    ModelVoucherCustomer modelVoucher = response.body();

                    daftarVoucherMember.clear();

//                    for (int i = 0; i < modelVoucher.getMsgServer().get(0).getDaftarVoucher().size(); i++) {

                    for (int i = 0; i < modelVoucher.getMsgServer().get(0).getDaftarVoucher().size(); i++) {

                        boolean isVoucherExpired = false;

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final Calendar baru = Calendar.getInstance();

                        Date tanggalNow = baru.getTime();
                        Date tanggalAkhir = formatter.parse(modelVoucher.getMsgServer().get(0).getDaftarVoucher().get(i).getExpiredDate());

                        long mlNow = tanggalNow.getTime();
                        long mlAkhir = tanggalAkhir.getTime();

                        if (mlNow <= mlAkhir) {
                            isVoucherExpired = false;
                        } else {
                            isVoucherExpired = true;
                        }

                        if (!modelVoucher.getMsgServer().get(0).getDaftarVoucher().get(i).getFlagPakai() && !isVoucherExpired) {
                            daftarVoucherMember.add(modelVoucher.getMsgServer().get(0).getDaftarVoucher().get(i));
                        }

                    }

                    binding.rvListVoucher.setAdapter(null);

                    adapterListVoucherOmset = new AdapterListVoucherMember(DaftarVoucherMember.this, daftarVoucherMember, true, DaftarVoucherMember.this);
                    binding.rvListVoucher.setAdapter(adapterListVoucherOmset);

                } catch (Exception e) {
                    Log.e(TAG, "onResponse: Error " + e);
                }
            }

            @Override
            public void onFailure(Call<ModelVoucherCustomer> call, Throwable t) {
                Toast.makeText(DaftarVoucherMember.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    @Override
    public void onRowDaftarVoucher(DaftarVoucher item, int posisi) {

    }
}