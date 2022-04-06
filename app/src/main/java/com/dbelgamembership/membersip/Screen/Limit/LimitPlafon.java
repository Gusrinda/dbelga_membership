package com.dbelgamembership.membersip.Screen.Limit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelDataLimit.DetailLimitUser;
import com.dbelgamembership.membersip.Model.ModelListTagihan.DaftarTagihanDenda;
import com.dbelgamembership.membersip.Model.ModelListTagihan.DaftarTagihanPeriode;
import com.dbelgamembership.membersip.Model.ModelListTagihan.ModelListTagihan;
import com.dbelgamembership.membersip.Model.ModelTagihanUser.ModelTagihanUser;
import com.dbelgamembership.membersip.Model.ModelTagihanUser.MsgServer;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.app.Adapter.AdapterListDenda;
import com.dbelgamembership.membersip.app.Adapter.AdapterListTagihan;
import com.dbelgamembership.membersip.databinding.ActivityLimitPlafonBinding;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        getDataLimit();

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
                Intent intent = new Intent(LimitPlafon.this, RiwayatTagihan.class);
                startActivity(intent);
            }
        });

    }

    private void getDataLimit() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<DetailLimitUser> callUser = apiInterface.doGetDetailLimitUser(sessionManager.getPID());
        callUser.enqueue(new Callback<DetailLimitUser>() {
            @Override
            public void onResponse(Call<DetailLimitUser> call, Response<DetailLimitUser> response) {

                DetailLimitUser detailLimitUser = response.body();

                limitPlafon = detailLimitUser.getMsgServer().getLimitAwal();
                sisaPlafon = Math.floor(detailLimitUser.getMsgServer().getLimitSisa());
                piutangBelanja = Math.ceil(detailLimitUser.getMsgServer().getLimitPenggunaan());

                Log.e(TAG, "onResponse LP: " + limitPlafon);
                Log.e(TAG, "onResponse SP: " + sisaPlafon);
                Log.e(TAG, "onResponse PB: " + piutangBelanja);

                pengaturanDataLimit();
//                getDataTagihanPeriode();
                getListTagihan();
            }

            @Override
            public void onFailure(Call<DetailLimitUser> call, Throwable t) {
                Toast.makeText(LimitPlafon.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + "   ::   " + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    private void getDataTagihanPeriode() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelTagihanUser> callTagihan = apiInterface.doGetTagihanPeriodeIni(sessionManager.getPID());

        callTagihan.enqueue(new Callback<ModelTagihanUser>() {
            @Override
            public void onResponse(Call<ModelTagihanUser> call, Response<ModelTagihanUser> response) {
                if (response.code() == 200) {

                    ModelTagihanUser modelTagihanUser = response.body();

                    if (modelTagihanUser.getSuccess()) {

                        MsgServer dataTagihanUser = modelTagihanUser.getMsgServer();

                        limitPlafonBinding.txtTagihanBulanIni.setText("Rp. " + nf.format(dataTagihanUser.getTagihanTotal()));


                    } else {
                        Toast.makeText(LimitPlafon.this, "Error :: TIDAK SUKSES", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LimitPlafon.this, "Error :: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelTagihanUser> call, Throwable t) {
                Toast.makeText(LimitPlafon.this, "Error :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + " :: " + t.getStackTrace());
            }
        });
    }

    private void getListTagihan() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat tanggalPeriode = new SimpleDateFormat("yyyy-MM-dd");

        String tanggalSekarang = tanggalPeriode.format(c.getTime());

        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelListTagihan> callListTagihan = apiInterface.doGetListTagihanUser(sessionManager.getPID(), tanggalSekarang);

        callListTagihan.enqueue(new Callback<ModelListTagihan>() {
            @Override
            public void onResponse(Call<ModelListTagihan> call, Response<ModelListTagihan> response) {
                if (response.code() == 200) {

                    ModelListTagihan modelDaftarTagihan = response.body();

                    if (modelDaftarTagihan.getSuccess()) {
                        double totalTagihan = modelDaftarTagihan.getMsgServer().getLimitPenggunaan() + modelDaftarTagihan.getMsgServer().getTagihanDenda();
                        limitPlafonBinding.txtTagihanBulanIni.setText("Rp. " + nf.format(totalTagihan));
                    } else {
                        Toast.makeText(LimitPlafon.this, "Error :: TIDAK SUKSES", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LimitPlafon.this, "Error :: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelListTagihan> call, Throwable t) {
                Toast.makeText(LimitPlafon.this, "ERROR :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                finish();
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