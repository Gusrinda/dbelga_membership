package com.dbelgamembership.membersip.Fragment.bottomSheet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dbelgamembership.membersip.Fragment.bottomSheet.Adapter.AdapterListKategori;
import com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity;
import com.dbelgamembership.membersip.databinding.FragmentBottomSheetFilterBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.dbelgamembership.membersip.Fragment.bottomSheet.Adapter.AdapterListKategori.selected_position;
//import static com.dbelgamembership.membersip.Screen.Katalog.KatalogActivity.dismissDialog;
import static com.dbelgamembership.membersip.Screen.SplashActivity.listArrayKategori;


public class BottomSheetFilterFragment extends BottomSheetDialogFragment {


    private static final String TAG = "FramentBottom";
    private AdapterListKategori adapterListKategori;

    private FragmentBottomSheetFilterBinding binding;

    public static String terendah = "0";
    public static String tertinggi = "0";
    public static String katekategori = "";
    public static String filter = "";

    public BottomSheetFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBottomSheetFilterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onCreate: " + listArrayKategori );
        adapterListKategori = new AdapterListKategori(getContext(), listArrayKategori);
        binding.rvKategori.setAdapter(adapterListKategori);


        binding.btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(binding.hargaRendah.getText().toString())) {
                    terendah = "0";
                } else {
                    terendah = binding.hargaRendah.getText().toString();
                }

                if (TextUtils.isEmpty(binding.hargaTinggi.getText().toString())) {
                    tertinggi = "999999999";
                } else {
                    tertinggi = binding.hargaTinggi.getText().toString();
                }


                int down = Integer.parseInt(terendah);
                int up = Integer.parseInt(tertinggi);
                String kategori = "";

                if (selected_position == 0) {
                    kategori = "";
                } else {
                    try {
                        kategori =   URLEncoder.encode( listArrayKategori.get(selected_position).toString().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                Log.e(TAG, "onClick DOWN: " + down );
                Log.e(TAG, "onClick UP: " + up );
                Log.e(TAG, "onClick: " +  kategori);

                filter = "&hargadown=" + terendah + "&hargaup=" + tertinggi + "&kategori=" + kategori;

                if (!TextUtils.isEmpty(binding.hargaTinggi.getText().toString()) && down > up) {
                    Toast.makeText(getActivity(), "Harga rendah tidak bisa lebih besar dari harga Tinggi !", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getContext(), filter, Toast.LENGTH_LONG).show();

                    ((KatalogActivity)getActivity()).dismissDialog(filter);
//                    dismissDialog(filter);
                    dismiss();
                }

            }
        });

        

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}