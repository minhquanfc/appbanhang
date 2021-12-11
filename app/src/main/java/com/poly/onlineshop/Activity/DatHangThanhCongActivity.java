package com.poly.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.poly.onlineshop.MainActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.fragment.HomeFragment;

public class DatHangThanhCongActivity extends AppCompatActivity {

    Button btn_xem_donhang,btn_trangchu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang_thanh_cong);
        btn_trangchu=findViewById(R.id.btn_trangchu);
        btn_xem_donhang=findViewById(R.id.btn_xem_donhang);
        btn_trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatHangThanhCongActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_xem_donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatHangThanhCongActivity.this, DonHangActivity.class);
                startActivity(intent);
            }
        });
    }
}