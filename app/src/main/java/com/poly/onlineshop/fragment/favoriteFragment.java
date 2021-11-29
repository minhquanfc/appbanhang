package com.poly.onlineshop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
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


public class favoriteFragment extends Fragment {

    RecyclerView recyclerView;
    List<SanPham> sanPhamList;
    YeuThichAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.rc_view_yeuthich);
        sanPhamList= new ArrayList<>();
        adapter = new YeuThichAdapter(getContext(),sanPhamList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getDataDienthoai();
        return view;
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

//    private void getDataYeuthich() {
//        FirebaseDatabase database;
//        database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef;
//        myRef=database.getReference("San_pham");
//        myRef.child("Dienthoai").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    SanPham sanPham = snapshot.getValue(SanPham.class);
//                    sanPhamList.add(sanPham);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}