package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.poly.onlineshop.R;
import com.poly.onlineshop.adapter.ChiTietDHAdapter;
import com.poly.onlineshop.model.ChiTietDH;
import com.poly.onlineshop.model.DonHang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InfoOrderActivity extends AppCompatActivity {

    TextView tv_ten_nguoi_nhan,tv_sdt_donhang,tv_diachi_donhang,tv_soluong_donhang,tv_thanhtien_donhang,tv_trangthai_donhang,tv_time_donhang;
    RecyclerView rc_view_info_donhang;
    Toolbar toolbar;
    List<ChiTietDH> donHangList;
    ChiTietDHAdapter adapter;
    DonHang donHang;
    String keyid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_order);
        toolbar = findViewById(R.id.toolbar_info_donhang);
        tv_ten_nguoi_nhan = findViewById(R.id.tv_ten_nguoi_nhan);
        tv_sdt_donhang = findViewById(R.id.tv_sdt_donhang);
        tv_diachi_donhang = findViewById(R.id.tv_diachi_donhang);
        tv_soluong_donhang = findViewById(R.id.tv_soluong_donhang);
        tv_thanhtien_donhang = findViewById(R.id.tv_thanhtien_donhang);
        tv_trangthai_donhang = findViewById(R.id.tv_trangthai_donhang);
        rc_view_info_donhang = findViewById(R.id.rc_view_info_donhang);
        tv_time_donhang = findViewById(R.id.tv_time_donhang);

        donHangList = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //intent data donhang sang info don hang
        donHang = (DonHang) getIntent().getSerializableExtra("donhang");
        Intent intent = getIntent();
        keyid= intent.getStringExtra("id");
        if ( donHang != null) {
            tv_ten_nguoi_nhan.setText(donHang.getHoTen());
            tv_sdt_donhang.setText(donHang.getSoDienThoai());
            tv_diachi_donhang.setText(donHang.getDiaChi());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tv_thanhtien_donhang.setText(decimalFormat.format(donHang.getTongTien())+"đ");
            tv_soluong_donhang.setText(String.valueOf(donHang.getSoLuong()));
            tv_trangthai_donhang.setText(donHang.getTrangThai());
            tv_time_donhang.setText(donHang.getNgayMua());
        }

        //hien thi list san pham da mua
        adapter = new ChiTietDHAdapter(this,donHangList);
        rc_view_info_donhang.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rc_view_info_donhang.setLayoutManager(linearLayoutManager);
        getDataInfoDonHang();
    }

    private void getDataInfoDonHang() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mRef = database.getReference("DonHang");
        mRef.child(user.getUid()).child(keyid).child("Chitiet").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChiTietDH donHang = snapshot.getValue(ChiTietDH.class);
                if (donHang !=null){
                    donHangList.add(donHang);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}