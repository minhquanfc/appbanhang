package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.R;
import com.poly.onlineshop.adapter.YeuThichAdapter;
import com.poly.onlineshop.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class YeuThichActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<SanPham> sanPhamList;
    YeuThichAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_thich);
        recyclerView = findViewById(R.id.rc_view_yeuthich2);
        toolbar = findViewById(R.id.toolbar_yeuthich);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sanPhamList= new ArrayList<>();
        adapter = new YeuThichAdapter(YeuThichActivity.this,sanPhamList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YeuThichActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getDataDienthoai();
        getDataDongho();
    }
    private void getDataDienthoai() {
        //doc du lieu tu realtime database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference("San_pham");
        myRef.child("Dienthoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    SanPham sanPham= dataSnapshot.getValue(SanPham.class);
                    sanPhamList.add(sanPham);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getDataDongho() {
        //doc du lieu tu realtime database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference("San_pham");
        myRef.child("Dongho").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    SanPham sanPham= dataSnapshot.getValue(SanPham.class);
                    sanPhamList.add(sanPham);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}