package com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Model.DummyPromo;
import com.dbelgamembership.membersip.app.Adapter.AdapterListPromo;
import com.dbelgamembership.membersip.databinding.FragmentAkunBinding;
import com.dbelgamembership.membersip.databinding.FragmentPromoBinding;

import java.util.ArrayList;
import java.util.List;


public class PromoFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    public String url = Http.server, jsonResult, type, user, pass;

    SessionManager sessionManager;

    private FragmentPromoBinding binding;


    public PromoFragment() {
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPromoBinding.inflate(inflater, container, false);
        sessionManager = new SessionManager(requireContext());

        List<DummyPromo> daftarPromo = new ArrayList<>();


        daftarPromo.add(
                new DummyPromo(
                        "Promo Pertama",
                        true,
                        "25 Des 2021",
                        "PEMBAYARAN",
                        R.drawable.promo_2
                )
        );

        daftarPromo.add(
                new DummyPromo(
                        "Promo Kedua",
                        false,
                        "31 Des 2021",
                        "BARANG",
                        R.drawable.promo_4
                )
        );

        daftarPromo.add(
                new DummyPromo(
                        "Promo Elektronik",
                        false,
                        "31 Des 2021",
                        "BARANG",
                        R.drawable.promo_5
                )
        );

        daftarPromo.add(
                new DummyPromo(
                        "Promo Keempat",
                        true,
                        "29 Des 2021",
                        "TAMBAH BELI",
                        R.drawable.promo_3
                )
        );

        daftarPromo.add(
                new DummyPromo(
                        "Promo Kelima",
                        true,
                        "1 Jan 2022",
                        "DISKON",
                        R.drawable.promo_4
                )
        );


        AdapterListPromo adapterListPromo = new AdapterListPromo(requireContext(), daftarPromo);
        binding.rvPromo.setAdapter(adapterListPromo);

        return binding.getRoot();
    }
}