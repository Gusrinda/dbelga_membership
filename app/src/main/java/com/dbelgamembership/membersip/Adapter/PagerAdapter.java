package com.dbelgamembership.membersip.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dbelgamembership.membersip.Fragment.FakturFragment;
import com.dbelgamembership.membersip.Fragment.SoFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numberTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int numberTabs) {
        super(fm);

        this.numberTabs = numberTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SoFragment();
            case 1:
                return new FakturFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberTabs;
    }
}
