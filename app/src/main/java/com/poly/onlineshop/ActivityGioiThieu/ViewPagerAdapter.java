package com.poly.onlineshop.ActivityGioiThieu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OnBoardingFragment();
            case 1:
                return new OnBoardingFragment2();
            case 2:
                return new OnBoardingFragment3();
            default:
                return new OnBoardingFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
