package com.dbelgamembership.membersip.Screen.Transaksi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dbelgamembership.membersip.Fragment.FakturFragment;
import com.dbelgamembership.membersip.Fragment.SoFragment;

import org.jetbrains.annotations.NotNull;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1 :
                return new FakturFragment();
        }

        return new SoFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
