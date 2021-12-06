package com.poly.onlineshop;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.adapter.ViewPagerAdapter2;
import com.poly.onlineshop.model.GioHang;
import com.poly.onlineshop.model.SanPham;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<GioHang> gioHangList;
    BottomNavigationView navigationView;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpagerpro);
        navigationView = findViewById(R.id.bottomnav);
        gioHangList = new ArrayList<>();
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.nav_giohang:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.nav_favorite:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.nav_account:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return true;
                    }
                });
        ViewPagerAdapter2 adapter = new ViewPagerAdapter2(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.nav_giohang).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.nav_favorite).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10){
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            }
        }
    }
}