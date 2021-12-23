package com.dbelgamembership.membersip.Screen.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Limit.BayarTagihan;
import com.dbelgamembership.membersip.Screen.Limit.DaftarTagihan;
import com.dbelgamembership.membersip.databinding.ActivityLimitPlafonBinding;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class LimitPlafon extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityLimitPlafonBinding limitPlafonBinding;
    private SessionManager sessionManager;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

    double limitPlafon, sisaPlafon, piutangBelanja;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        limitPlafonBinding = ActivityLimitPlafonBinding.inflate(getLayoutInflater());
        View view = limitPlafonBinding.getRoot();
        setContentView(view);
        sessionManager = new SessionManager(this);

        limitPlafonBinding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        limitPlafonBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDataCustomer();

        limitPlafonBinding.btnTagihanBulanIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LimitPlafon.this, BayarTagihan.class);
                startActivity(intent);
            }
        });

        limitPlafonBinding.btnDaftarTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LimitPlafon.this, DaftarTagihan.class);
                startActivity(intent);
            }
        });

        limitPlafonBinding.btnRiwayatLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LimitPlafon.this, DaftarTagihan.class);
                startActivity(intent);
            }
        });



    }

    private void getDataCustomer() {
        String url = Http.server + "search-customer/" + sessionManager.getPID();
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelUser> callUser = apiInterface.doLoopCustomer(url);
        callUser.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, retrofit2.Response<ModelUser> response) {
                ModelUser object = response.body();
                com.dbelgamembership.membersip.Model.ModelUser.MsgServer dataUser = object.getMsgServer().get(0);


                    limitPlafon = (dataUser.getCreditLimit() == null ? 0 : Double.parseDouble(dataUser.getCreditLimit()));
                    sisaPlafon = dataUser.getSisaCreditLimit();
                    piutangBelanja = Double.parseDouble(dataUser.getGrandTotalSo());

                    Log.e(TAG, "onResponse LP: " + limitPlafon);
                    Log.e(TAG, "onResponse SP: " + sisaPlafon);
                    Log.e(TAG, "onResponse PB: " + piutangBelanja);

                    pengaturanDataLimit();


            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    private void pengaturanDataLimit() {

        limitPlafonBinding.txtLimitPengguna.setText("Rp. " + nf.format(limitPlafon));
        limitPlafonBinding.txtSisaLimit.setText("Rp. " + nf.format(sisaPlafon));

        int persentase = (int) (sisaPlafon * 100 / limitPlafon);

        limitPlafonBinding.txtPersentasePenggunaan.setText(String.valueOf(persentase) + "%");

        limitPlafonBinding.progressHorizontal.setProgress(persentase);

        if (persentase < 30) {
            limitPlafonBinding.progressHorizontal.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

    }


}