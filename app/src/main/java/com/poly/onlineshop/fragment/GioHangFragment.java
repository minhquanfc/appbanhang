package com.poly.onlineshop.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.Activity.ThayDoiActivity;
import com.poly.onlineshop.MainActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.adapter.GioHangAdapter;
import com.poly.onlineshop.model.GioHang;
import com.poly.onlineshop.model.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GioHangFragment extends Fragment {

    RecyclerView recyclerView;
    TextView tv_thaydiachi, tv_tongtien, tv_diachi,tv_ten,tv_sdt;
    Button btn_thanhtoan;
    List<GioHang> gioHangList;
    GioHangAdapter adapter;
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
        tv_ten = view.findViewById(R.id.tv_ten);
        tv_sdt = view.findViewById(R.id.tv_sdt);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        idUser = user.getUid();
        gioHangList = new ArrayList<>();

        tv_thaydiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThayDoiActivity.class);
                startActivity(intent);
            }
        });
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gioHangList.isEmpty()){
                    btn_thanhtoan.setEnabled(true);
                    Toast.makeText(getContext(), "Vui lòng thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }else {
                    addDataOrder();
                }
            }
        });

        //lay dia chi
        getDiaChi();
        // hien thi gio hang
        setVisibilityGioHang();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("Tongtien"));

        return view;
    }

    private void addDataOrder() {
        DatabaseReference myRef = database.getReference("DonHang");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUser = user.getUid();

        Map<String, Object> map = new HashMap<>();
        Date date = new Date(System.currentTimeMillis());
        map.put("ngayMua", date.toString());
        map.put("hoTen", tv_ten.getText().toString());
        map.put("diaChi", tv_diachi.getText().toString());
        map.put("soDienThoai", tv_sdt.getText().toString());
        int num = 0;
        for (GioHang product : gioHangList) {
            num = num + product.getSoLuong();
        }
        map.put("soLuong", num);
        map.put("tongTien", Integer.valueOf(tv_tongtien.getText().toString()));
        map.put("trangThai", "Đang chờ xác nhận");
        //id tu dong
        String key = myRef.push().getKey();
        map.put("idOrder",key);
        myRef.child(idUser).child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                for (GioHang gioHang : gioHangList){
                    myRef.child(idUser).child(key).child("Chitiet").child(myRef.push().getKey())
                            .setValue(gioHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(),"Đã đăng ký đơn hàng",Toast.LENGTH_SHORT).show();
                            gioHangList.clear();
                            XoaData();
                        }
                    });

                }

            }
        });
    }

    private void XoaData() {
        DatabaseReference reference = database.getReference("GioHang");
        reference.child(idUser).removeValue();
    }

    private void setVisibilityGioHang() {
        adapter = new GioHangAdapter(getContext(), gioHangList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getDataGiohang();
    }

    private void getDataGiohang() {
        //doc du lieu tu realtime database
        DatabaseReference reference1;
        reference1 = database.getReference("GioHang");
        //cach 1
//        reference1.child(idUser).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (gioHangList !=null){
//                    gioHangList.clear();
//                }
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    GioHang gioHang = dataSnapshot.getValue(GioHang.class);
//                    gioHangList.add(gioHang);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //cach 2
        reference1.child(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
//                gioHang.setTenSanpham(snapshot.getKey());
                if (gioHang !=null){
                    gioHangList.add(gioHang);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
                if (gioHang == null || gioHangList == null ||  gioHangList.isEmpty()){
                    return;
                }
                for (int i=0 ;i<gioHangList.size();i++){
                    if (gioHang.getTenSanpham()== gioHangList.get(i).getTenSanpham()){
                        gioHangList.set(i,gioHang);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
                if (gioHang == null || gioHangList == null ||  gioHangList.isEmpty()){
                    return;
                }
                for (int i=0 ;i<gioHangList.size();i++){
                    if (gioHang.getTenSanpham()== gioHangList.get(i).getTenSanpham()){
                        gioHangList.remove(gioHangList.get(i));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user1 = snapshot.getValue(User.class);
                    if (user1 != null) {
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

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int bill = intent.getIntExtra("tong", 0);
            tv_tongtien.setText(bill + "");
        }
    };
}