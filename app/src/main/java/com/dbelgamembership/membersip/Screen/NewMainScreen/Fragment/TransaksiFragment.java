package com.dbelgamembership.membersip.Screen.NewMainScreen.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Screen.LoginActivity;
import com.dbelgamembership.membersip.Screen.Registrasi.RegisterActivity;
import com.dbelgamembership.membersip.Screen.Transaksi.FragmentAdapter;
import com.dbelgamembership.membersip.databinding.FragmentTransaksiBinding;
import com.google.android.material.tabs.TabLayout;

public class TransaksiFragment extends Fragment {
    public TransaksiFragment() {
    }

    FragmentTransaksiBinding binding;
    SessionManager sessionManager;

    FragmentAdapter fragmentAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransaksiBinding.inflate(inflater, container, false);
        sessionManager = new SessionManager(requireContext());


        if (sessionManager.isLoggedIn()) {

            binding.layoutTransaksi.setVisibility(View.VISIBLE);
            binding.layoutBelumLogin.setVisibility(View.GONE);

            FragmentManager fm = requireActivity().getSupportFragmentManager();
            fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
            binding.viewPager2.setAdapter(fragmentAdapter);

            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Berjalan"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Selesai"));

            binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    binding.viewPager2.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
                }
            });
        } else {
            binding.layoutTransaksi.setVisibility(View.GONE);
            binding.layoutBelumLogin.setVisibility(View.VISIBLE);
        }

        binding.btnLoginTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnRegisterTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }
}