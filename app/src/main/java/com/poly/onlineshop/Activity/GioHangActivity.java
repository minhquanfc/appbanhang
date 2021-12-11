package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import com.poly.onlineshop.R;
import com.poly.onlineshop.adapter.GioHangAdapter;
import com.poly.onlineshop.model.GioHang;
import com.poly.onlineshop.model.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GioHangActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tv_thaydiachi, tv_tongtien, tv_diachi, tv_ten, tv_sdt;
    Button btn_thanhtoan;
    List<GioHang> gioHangList;
    GioHangAdapter adapter;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    String idUser;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        recyclerView = findViewById(R.id.rc_view_giohang2);
        tv_thaydiachi = findViewById(R.id.tv_thaydiachi2);
        tv_tongtien = findViewById(R.id.tv_tongtien2);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan2);
        tv_diachi = findViewById(R.id.tv_diachi2);
        tv_ten = findViewById(R.id.tv_ten2);
        tv_sdt = findViewById(R.id.tv_sdt2);
        toolbar = findViewById(R.id.toolbar_giohang);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        idUser = user.getUid();
        gioHangList = new ArrayList<>();
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_thaydiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this, ThayDoiActivity.class);
                startActivity(intent);
            }
        });
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gioHangList.isEmpty()) {
                    btn_thanhtoan.setEnabled(true);
                    Toast.makeText(GioHangActivity.this, "Vui lòng thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(GioHangActivity.this, LoadActivity.class);
                    startActivity(intent);
                    addDataOrder();
                }
            }
        });

        getDiaChi();
        setVisibilityGioHang();
        LocalBroadcastManager.getInstance(GioHangActivity.this).registerReceiver(broadcastReceiver, new IntentFilter("Tongtien"));

    }
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            int tongtien = intent.getIntExtra("tongtien", 0);
            tv_tongtien.setText(tongtien + "");
        }
    };

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
        map.put("idOrder", key);
        myRef.child(idUser).child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                for (GioHang gioHang : gioHangList) {
                    myRef.child(idUser).child(key).child("Chitiet").child(myRef.push().getKey())
                            .setValue(gioHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
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
        adapter = new GioHangAdapter(GioHangActivity.this, gioHangList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GioHangActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getDataGiohang();
    }

    private void getDataGiohang() {
        //doc du lieu tu realtime database
        DatabaseReference reference1;
        reference1 = database.getReference("GioHang");
        reference1.child(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
                gioHang.setId(snapshot.getKey());
                if (gioHang != null) {
                    gioHangList.add(gioHang);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
                if (gioHang == null || gioHangList == null || gioHangList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < gioHangList.size(); i++) {
                    if (gioHang.getTenSanpham() == gioHangList.get(i).getTenSanpham()) {
                        gioHangList.set(i, gioHang);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
                if (gioHang == null || gioHangList == null || gioHangList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < gioHangList.size(); i++) {
                    if (gioHang.getTenSanpham() == gioHangList.get(i).getTenSanpham()) {
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
                        tv_sdt.setText(user1.getSdt());
                        tv_ten.setText(user1.getHoTen());
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
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}