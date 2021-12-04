package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.R;
import com.poly.onlineshop.adapter.InfoOrderAdapter;
import com.poly.onlineshop.model.DonHang;

import java.util.ArrayList;
import java.util.List;

public class InfoOrderActivity extends AppCompatActivity {

    TextView tv_ten_nguoi_nhan,tv_sdt_donhang,tv_diachi_donhang,tv_soluong_donhang,tv_thanhtien_donhang,tv_trangthai_donhang;
    RecyclerView rc_view_info_donhang;
    Toolbar toolbar;
    List<DonHang> donHangList;
    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference mRef;
    InfoOrderAdapter adapter;
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
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mRef = database.getReference("DonHang");

        donHangList = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataDonHang();

        adapter = new InfoOrderAdapter(this,donHangList);
        rc_view_info_donhang.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rc_view_info_donhang.setLayoutManager(linearLayoutManager);
//        getDataInfoDonHang();
        findDetailOrder();
    }

    private void getDataInfoDonHang() {
        String key = mRef.push().getKey();
        mRef.child(user.getUid()).child(key).child("Chitiet").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DonHang donHang = dataSnapshot.getValue(DonHang.class);
                    donHangList.add(donHang);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataDonHang() {
        mRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DonHang donHang = dataSnapshot.getValue(DonHang.class);
                    donHangList.add(donHang);
                    tv_ten_nguoi_nhan.setText(donHang.getHoTen());
                    tv_sdt_donhang.setText(donHang.getSoDienThoai());
                    tv_diachi_donhang.setText(donHang.getDiaChi());
                    tv_thanhtien_donhang.setText(String.valueOf(donHang.getTongTien())+"đ");
                    tv_soluong_donhang.setText(String.valueOf(donHang.getSoLuong()));
                    tv_trangthai_donhang.setText(donHang.getTrangThai());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // Lấy thông tin detail order
    private void findDetailOrder(){
        if (donHangList.size() > 0){
            for (int i = 0; i<donHangList.size(); i++){
                DonHang order = donHangList.get(i);
                mRef.child(order.getIdOrder()).child("Chitiet").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataDetail : snapshot.getChildren()){
                            adapter.notifyDataSetChanged();
                            DonHang detailOrder = dataDetail.getValue(DonHang.class);
                            donHangList.add(detailOrder);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }
}