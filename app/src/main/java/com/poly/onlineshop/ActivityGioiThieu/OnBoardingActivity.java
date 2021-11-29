package com.poly.onlineshop.ActivityGioiThieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.poly.onlineshop.Activity.LoginActivity;
import com.poly.onlineshop.MainActivity;
import com.poly.onlineshop.R;

import me.relex.circleindicator.CircleIndicator;

public class OnBoardingActivity extends AppCompatActivity {
    TextView tv_skip;
    Button btn_next;
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        tv_skip = findViewById(R.id.tv_skip);
        btn_next = findViewById(R.id.btn_next);
        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circleindicator);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        circleIndicator.setViewPager(viewPager);
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() <2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }else {
                    Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        });
    }
}