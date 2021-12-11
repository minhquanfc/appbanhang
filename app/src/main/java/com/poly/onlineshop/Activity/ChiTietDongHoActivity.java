package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.DongHo;
import com.poly.onlineshop.model.GioHang;
import com.poly.onlineshop.model.SanPham;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiTietDongHoActivity extends AppCompatActivity {

    Button btn_add_giohang, btn_muangay;
    TextView tv_gia_chitiet, tv_tensp_chitiet, tv_mota_chitiet, tv_soluong_sp;
    Toolbar toolbar;
    ImageView img_sanpham, btn_cong_sp, btn_tru_sp;

    DongHo dongHo = null;

    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseUser user;
    String idUser;

    FirebaseFirestore db;
    int soLuong = 1;
    int tonggia = 0;

    List<GioHang> gioHangList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dong_ho);
        btn_add_giohang = findViewById(R.id.btn_add_giohang_dongho);
        tv_gia_chitiet = findViewById(R.id.tv_gia_chitiet_dongho);
        tv_tensp_chitiet = findViewById(R.id.tv_tensp_chitiet_dongho);
        tv_mota_chitiet = findViewById(R.id.tv_mota_chitiet_dongho);
        tv_soluong_sp = findViewById(R.id.tv_soluong_sp_dongho);
        btn_cong_sp = findViewById(R.id.btn_cong_sp_dongho);
        btn_tru_sp = findViewById(R.id.btn_tru_sp_dongho);
        img_sanpham = findViewById(R.id.img_sp_chitiet_dongho);
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("GioHang");
        user = FirebaseAuth.getInstance().getCurrentUser();
        idUser = user.getUid();
        db = FirebaseFirestore.getInstance();
        gioHangList = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar_chitiet_dongho);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_add_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataGioHang();
            }
        });

        getData();

        btn_cong_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuong < 10) {
                    soLuong++;
                    tv_soluong_sp.setText(String.valueOf(soLuong));
                    tonggia = dongHo.getGia() * soLuong;
                }
            }
        });
        btn_tru_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuong > 1) {
                    soLuong--;
                    tv_soluong_sp.setText(String.valueOf(soLuong));
                }
            }
        });
    }
    private void getData() {
        final Object obj = getIntent().getSerializableExtra("chitiet");
        if (obj instanceof DongHo) {
            dongHo = (DongHo) obj;
        }
        if (dongHo != null) {
            Glide.with(ChiTietDongHoActivity.this).load(dongHo.getAnh()).into(img_sanpham);
            tv_tensp_chitiet.setText(dongHo.getTen());
            tv_gia_chitiet.setText(String.valueOf(dongHo.getGia()));
            tv_mota_chitiet.setText(dongHo.getMota());
        }
    }
    private void addDataGioHang() {
        String tensp = tv_tensp_chitiet.getText().toString();
        String giasp = tv_gia_chitiet.getText().toString();

        Map<String, Object> cartMap = new HashMap<>();
        String key = mRef.push().getKey();
        cartMap.put("id",key);
        cartMap.put("tenSanpham", tensp);
        cartMap.put("giaSanpham", Integer.valueOf(giasp));
        cartMap.put("soLuong", soLuong);
        tonggia = dongHo.getGia() * soLuong;
        cartMap.put("tongTien", tonggia);
        cartMap.put("anh", dongHo.getAnh());
        mRef.child(idUser).child(key).setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ChiTietDongHoActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChiTietDongHoActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}