package com.dbelgamembership.membersip.Fragment.bottomSheet;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment.MainFragment;
import com.dbelgamembership.membersip.Screen.NewMainScreen.NewMainActivity;
import com.dbelgamembership.membersip.databinding.FragmentBottomSheetSortBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

//import static com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity.dismissDialog;


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
                Log.e(TAG, "onClick urutkan: " + urutkanData );
//                ((KatalogActivity)getActivity()).sortBarang(urutkanData);

                MainFragment fragment = (MainFragment) getParentFragmentManager().findFragmentById(R.id.frameContainer);
                fragment.sortBarang(urutkanData);
//
//                FragmentManager fm = getParentFragmentManager();
//                MainFragment fragm = (MainFragment)fm.findFragmentById(R.id.mainFragment);
//                fragm.sortBarang(urutkanData);
                dismiss();
            }
        });



    }

    private static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}