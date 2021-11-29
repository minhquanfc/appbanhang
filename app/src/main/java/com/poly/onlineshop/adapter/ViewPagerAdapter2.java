package com.poly.onlineshop.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.poly.onlineshop.fragment.AccountFragment;
import com.poly.onlineshop.fragment.GioHangFragment;
import com.poly.onlineshop.fragment.HomeFragment;
import com.poly.onlineshop.fragment.favoriteFragment;

public class ViewPagerAdapter2 extends FragmentStatePagerAdapter {
    public ViewPagerAdapter2(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new GioHangFragment();
            case 2:
                return new favoriteFragment();
            case 3:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
