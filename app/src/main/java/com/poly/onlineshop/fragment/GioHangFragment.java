package com.poly.onlineshop.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.Activity.ThayDoiActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.adapter.GioHangAdapter;
import com.poly.onlineshop.model.GioHang;
import com.poly.onlineshop.model.SanPham;
import com.poly.onlineshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class GioHangFragment extends Fragment {

    RecyclerView recyclerView;
    TextView tv_thaydiachi,tv_tongtien,tv_diachi;
    Button btn_thanhtoan;
    List<GioHang> gioHangList;
    GioHangAdapter adapter ;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    String idUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        recyclerView = view.findViewById(R.id.rc_view_giohang);
        tv_thaydiachi = view.findViewById(R.id.tv_thaydiachi);
        tv_tongtien = view.findViewById(R.id.tv_tongtien);
        btn_thanhtoan = view.findViewById(R.id.btn_thanhtoan);
        tv_diachi = view.findViewById(R.id.tv_diachi);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        idUser = user.getUid();

        gioHangList=new ArrayList<>();

        tv_thaydiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThayDoiActivity.class);
                startActivity(intent);
            }
        });

        //lay dia chi
        getDiaChi();
        // hien thi gio hang
        adapter = new GioHangAdapter(getContext(),gioHangList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getDataDienthoai();

        return view;
    }

    private void getDataDienthoai() {
        //doc du lieu tu realtime database
        DatabaseReference reference1;
        reference1 = database.getReference("GioHang");
        reference1.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    GioHang sanPham= snapshot.getValue(GioHang.class);
                    gioHangList.add(sanPham);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDiaChi() {
        reference = database.getReference("User");
        reference.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user1 = snapshot.getValue(User.class);
                    if (user1 !=null){
                        String diachi = user1.getDiachi();
                        tv_diachi.setText(diachi);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}