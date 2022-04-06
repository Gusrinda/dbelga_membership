package com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelBannerPromo.Datum;
import com.dbelgamembership.membersip.Model.ModelBannerPromo.ModelBannerPromo;
import com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.ResponseCekVerifikasi;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Model.DummyPromo;
import com.dbelgamembership.membersip.Screen.Promo.KatalogPromo;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.User.Membership.MembershipChoose;
import com.dbelgamembership.membersip.app.Adapter.AdapterListPromo;
import com.dbelgamembership.membersip.databinding.FragmentAkunBinding;
import com.dbelgamembership.membersip.databinding.FragmentPromoBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoFragment extends Fragment implements AdapterListPromo.AdapterListPromoCallback {

    private final String TAG = this.getClass().getSimpleName();
    public String url = Http.server, jsonResult, type, user, pass;

    SessionManager sessionManager;

    private List<Datum> daftarPromo = new ArrayList<>();

    private FragmentPromoBinding binding;

    public PromoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPromoBinding.inflate(inflater, container, false);
        sessionManager = new SessionManager(requireContext());

        getDaftarPromo();


        return binding.getRoot();
    }

    private void getDaftarPromo() {
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<ModelBannerPromo> call = apiInterface.doGetBannerPromo();
        call.enqueue(new Callback<ModelBannerPromo>() {
            @Override
            public void onResponse(Call<ModelBannerPromo> call, Response<ModelBannerPromo> response) {
                if (response.code() == 200) {

                    ModelBannerPromo bannerPromo = response.body();
                    daftarPromo = bannerPromo.getData();

                    if (daftarPromo.size() > 0) {
                        List<Datum> dataPromoTokoIni = new ArrayList<>();

                        for (int i = 0; i < daftarPromo.size(); i++) {
                            if (String.valueOf(daftarPromo.get(i).getGudang()).equals(sessionManager.getKeySetGudangPencarian())) {
                                dataPromoTokoIni.add(daftarPromo.get(i));
                            }
                        }

                        List<Datum> dataPromoTokoFix = new ArrayList<>();

//                        2022-01-28 17:00:00

                        SimpleDateFormat af = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar cal = Calendar.getInstance(); // creates calendar

                        Date sekarang = cal.getTime();

                        for (int i = 0; i < dataPromoTokoIni.size(); i++) {
                            try {
                                Date tanggalBatas = af.parse(dataPromoTokoIni.get(i).getDateEnd());

                                if (sekarang.getTime() < tanggalBatas.getTime()) {
                                    dataPromoTokoFix.add(dataPromoTokoIni.get(i));
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        AdapterListPromo adapterListPromo = new AdapterListPromo(requireContext(), dataPromoTokoFix, PromoFragment.this);
                        binding.rvPromo.setAdapter(adapterListPromo);

                    } else {
                        Toast.makeText(requireContext(), "PROMO KOSONG !!!", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(requireContext(), "ERROR :: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelBannerPromo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() + Arrays.toString(t.getStackTrace()));
                Toast.makeText(requireContext(), "ERROR :: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void AdapterListPromoClicked(Datum position) {

        if (sessionManager.isLoggedIn()) {

            Datum dataPromo = position;
            Log.e(TAG, "AdapterListPromoClicked: " + dataPromo.getKeterangan());
            Log.e(TAG, "AdapterListPromoClicked: " + dataPromo.getDateEnd());
            Log.e(TAG, "AdapterListPromoClicked: " + dataPromo.getGudang());

            if (dataPromo.getGudang() == Integer.parseInt(sessionManager.getKeySetGudangPencarian())) {
                Intent intent = new Intent(requireContext(), KatalogPromo.class);
                intent.putExtra("hasExtra", true);
                intent.putExtra("dataPromo", (Parcelable) dataPromo);
                startActivity(intent);
            } else {
                Toast.makeText(requireContext(), "Promo tidak berlaku di toko ini !", Toast.LENGTH_SHORT).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
            alert.setIcon(R.drawable.dbelga);
            alert.setTitle("Attention!");
            alert.setMessage("Anda harus mempunyai akun Membership terlebih dahulu untuk melihat detail promo dbelga !");
            alert.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            alert.setNegativeButton("REGISTER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(requireActivity(), RegisterActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            alert.setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert.show();
        }

    }

}