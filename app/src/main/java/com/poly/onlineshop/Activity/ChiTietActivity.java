package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.DongHo;
import com.poly.onlineshop.model.SanPham;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ChiTietActivity extends AppCompatActivity {

    Button btn_add_giohang,btn_muangay;
    TextView tv_gia_chitiet,tv_tensp_chitiet,tv_mota_chitiet;
    Toolbar toolbar;
    ImageView img_sanpham,back_chitiet;

    SanPham sanPham = null;
    DongHo dongHo = null;

    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseUser user;
    String idUser;
    int soLuong = 1;
    int tonggia=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        btn_add_giohang=findViewById(R.id.btn_add_giohang);
        btn_muangay=findViewById(R.id.btn_muangay);
        tv_gia_chitiet=findViewById(R.id.tv_gia_chitiet);
        tv_tensp_chitiet=findViewById(R.id.tv_tensp_chitiet);
        tv_mota_chitiet=findViewById(R.id.tv_mota_chitiet);
        img_sanpham=findViewById(R.id.img_sp_chitiet);
        database=FirebaseDatabase.getInstance();
        mRef=database.getReference("GioHang");
        user = FirebaseAuth.getInstance().getCurrentUser();
        idUser = user.getUid();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_add_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = tv_tensp_chitiet.getText().toString();
                String giasp = tv_gia_chitiet.getText().toString();

                String thoigian, ngay;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                ngay = sdf.format(calendar.getTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM:mm:ss a");
                thoigian = simpleDateFormat.format(calendar.getTime());


                mRef.child(idUser).child("tenSanpham").setValue(tensp);
                mRef.child(idUser).child("giaSanpham").setValue(Integer.valueOf(giasp));
                mRef.child(idUser).child("soLuong").setValue(soLuong);
                mRef.child(idUser).child("tongTien").setValue(tonggia);
                mRef.child(idUser).child("ngayMua").setValue(thoigian);

                Toast.makeText(ChiTietActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        });

        final Object obj = getIntent().getSerializableExtra("chitiet");
        if (obj instanceof SanPham) {
            sanPham = (SanPham) obj;
        }

        if (sanPham != null) {
            Glide.with(ChiTietActivity.this).load(sanPham.getAnh()).into(img_sanpham);
            tv_tensp_chitiet.setText(sanPham.getTen());
            tv_gia_chitiet.setText(String.valueOf(sanPham.getGia()));
            tv_mota_chitiet.setText(sanPham.getMota());
        }

        if (obj instanceof DongHo) {
            dongHo = (DongHo) obj;
        }
        if (dongHo != null) {
            Glide.with(ChiTietActivity.this).load(dongHo.getAnh()).into(img_sanpham);
            tv_tensp_chitiet.setText(dongHo.getTen());
            tv_gia_chitiet.setText(String.valueOf(dongHo.getGia()));
            tv_mota_chitiet.setText(dongHo.getMota());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}