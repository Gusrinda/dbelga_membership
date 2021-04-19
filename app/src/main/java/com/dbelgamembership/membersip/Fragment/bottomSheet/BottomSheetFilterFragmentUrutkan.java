package com.dbelgamembership.membersip.Fragment.bottomSheet;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dbelgamembership.membersip.Fragment.bottomSheet.Adapter.AdapterListKategori;
import com.dbelgamembership.membersip.KatalogActivity;
import com.dbelgamembership.membersip.databinding.FragmentBottomSheetFilterBinding;
import com.dbelgamembership.membersip.databinding.FragmentBottomSheetSortBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.dbelgamembership.membersip.Fragment.bottomSheet.Adapter.AdapterListKategori.selected_position;
import static com.dbelgamembership.membersip.SplashActivity.listArrayKategori;

//import static com.dbelgamembership.membersip.KatalogActivity.dismissDialog;


public class BottomSheetFilterFragmentUrutkan extends BottomSheetDialogFragment {


    private static final String TAG = "FramentBottom";

    private FragmentBottomSheetSortBinding binding;
    String urutkanData = "";
    int selectedPosition = 0;
    public BottomSheetFilterFragmentUrutkan() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBottomSheetSortBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Stok Terbanyak
//        Stok Sedikit
//        Harga Tertinggi
//        Harga Terendah



        binding.spinnerUrut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPosition = binding.spinnerUrut.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 0 :
                        urutkanData = "stokUp";
                        break;
                    case 1 :
                        urutkanData = "stokDown";
                        break;
                    case 2 :
                        urutkanData = "priceUp";
                        break;
                    case 3 :
                        urutkanData = "priceDown";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        binding.btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + selectedPosition );
                Log.e(TAG, "onClick: " + urutkanData );
                ((KatalogActivity)getActivity()).sortBarang(urutkanData);
                dismiss();
            }
        });

        

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}