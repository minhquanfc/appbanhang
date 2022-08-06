package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.R;
import com.poly.onlineshop.adapter.DanhMucAdapter;
import com.poly.onlineshop.adapter.DanhSachAdapter;
import com.poly.onlineshop.model.DongHo;
import com.poly.onlineshop.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class DanhSachActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    DanhSachAdapter danhSachAdapter;
    List<SanPham> sanPhamList;
    FirebaseDatabase database;
    DatabaseReference reference;
    String type;
    String thumuc="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);
        recyclerView =findViewById(R.id.rcview_list_sp);
        toolbar =findViewById(R.id.toolbar_sanpham);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        type = getIntent().getStringExtra("type");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sanPhamList = new ArrayList<>();
        danhSachAdapter = new DanhSachAdapter(DanhSachActivity.this,sanPhamList);
        recyclerView.setAdapter(danhSachAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getData();

    }
    //get du lieu dien thoai
    private void getData() {
        //doc du lieu tu realtime database
        reference = database.getReference("San_pham");
        //do data cũ tên không dấu nên phải đổi lại tên loại
        if (type.equalsIgnoreCase("Điện thoại")){
            thumuc="Dienthoai";
        } else if (type.equalsIgnoreCase("Đồng hồ")){
            thumuc="Dongho";
        } else if (type.equalsIgnoreCase("Ipad")){
            thumuc="Ipad";
        } else if (type.equalsIgnoreCase("Máy tính")){
            thumuc="Maytinh";
        } else if (type.equalsIgnoreCase("Phụ kiện")){
            thumuc="Phukien";
        } else {
            thumuc = type;
        }

        reference.child(thumuc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    SanPham sanPham= dataSnapshot.getValue(SanPham.class);
                    if (type !=null && type.equalsIgnoreCase(type)){
                        sanPhamList.add(sanPham);
                        danhSachAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}