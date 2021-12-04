package com.poly.onlineshop.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.Activity.ChiTietActivity;
import com.poly.onlineshop.Activity.DanhSachActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.adapter.DanhMucAdapter;
import com.poly.onlineshop.adapter.DongHoAdapter;
import com.poly.onlineshop.adapter.SanPhamAdapter;
import com.poly.onlineshop.adapter.SanPhamSearchAdapter;
import com.poly.onlineshop.adapter.SlidePhotoAdapter;
import com.poly.onlineshop.model.Danhmuc;
import com.poly.onlineshop.model.DongHo;
import com.poly.onlineshop.model.SanPham;
import com.poly.onlineshop.model.SlidePhoto;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView_danhmuc, recyclerView_dienthoai, recyclerView_dongho;
    TextView tv_xemthem_dienthoai, tv_xemthem_dongho;
    ViewPager viewPager_silde;
    private CircleIndicator circleIndicator;
    AutoCompleteTextView timkiem;

    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Danhmuc> danhmucList;
    DanhMucAdapter danhMucAdapter;
    //san pham
    List<SanPham> sanPhamList;
    SanPhamAdapter sanPhamAdapter;
    List<DongHo> dongHoList;
    DongHoAdapter dongHoAdapter;

    //slide anh
    List<SlidePhoto> photoList;
    private Handler handler = new Handler();
    private Runnable runnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView_danhmuc = view.findViewById(R.id.rcview_danhmuc);
        recyclerView_dienthoai = view.findViewById(R.id.rc_view_dienthoai);
        recyclerView_dongho = view.findViewById(R.id.rc_view_dongho);
        tv_xemthem_dienthoai = view.findViewById(R.id.tv_xemthem_dt);
        tv_xemthem_dongho = view.findViewById(R.id.tv_xemthem_dh);
        circleIndicator =view.findViewById(R.id.circle_indicator);
        viewPager_silde = view.findViewById(R.id.viewpager_slide);
        timkiem = view.findViewById(R.id.ed_search);
        database = FirebaseDatabase.getInstance();

        //hien thi anh slide
        photoList = new ArrayList<>();
        getDataSlide();

        //hien thi du lieu len danh muc
        danhmucList = new ArrayList<>();
        danhMucAdapter = new DanhMucAdapter(getContext(),danhmucList);
        recyclerView_danhmuc.setAdapter(danhMucAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView_danhmuc.setLayoutManager(linearLayoutManager);
        getDataDanhMuc();

        //hien thi du lieu sanpham
        sanPhamList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getContext(),sanPhamList);
        recyclerView_dienthoai.setAdapter(sanPhamAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView_dienthoai.setLayoutManager(linearLayoutManager1);
        getDataDienthoai();

        // hien thi dong ho
        dongHoList = new ArrayList<>();
        dongHoAdapter = new DongHoAdapter(getContext(),dongHoList);
        recyclerView_dongho.setAdapter(dongHoAdapter);
        LinearLayoutManager DonghoLinear = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView_dongho.setLayoutManager(DonghoLinear);
        getDataDongHo();


        //xem them dien thoai
        tv_xemthem_dienthoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhSachActivity.class);
                intent.putExtra("type","dienthoai");
                startActivity(intent);
            }
        });
        //xem them dong ho
        tv_xemthem_dongho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhSachActivity.class);
                intent.putExtra("type","dongho");
                startActivity(intent);
            }
        });
        setSanphamSearchAdapter();

        return view;
    }

    private void viewSlidePhoto(List<SlidePhoto> slidePhotoList) {
//        photoList = getListPhoto();
        SlidePhotoAdapter photoAdapter = new SlidePhotoAdapter(getContext(),slidePhotoList);
        viewPager_silde.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager_silde);
        //auto slide
        autoslidePhoto();
    }

    private void getDataDanhMuc() {
        //doc du lieu tu realtime database
        myRef = database.getReference("Danh_muc");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Danhmuc danhmuc= dataSnapshot.getValue(Danhmuc.class);
                    danhmucList.add(danhmuc);
                }
                danhMucAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void autoslidePhoto() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager_silde.getCurrentItem() == photoList.size() - 1) {
                    viewPager_silde.setCurrentItem(0);
                } else {
                    viewPager_silde.setCurrentItem(viewPager_silde.getCurrentItem() + 1);
                }
            }
        };
        handler.postDelayed(runnable, 2000);
        viewPager_silde.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //get dien thoai
    private void getDataDienthoai() {
        //doc du lieu tu realtime database
        myRef = database.getReference("San_pham");
        myRef.child("Dienthoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    SanPham sanPham= dataSnapshot.getValue(SanPham.class);
                    sanPham.setId(dataSnapshot.getKey());
                    sanPhamList.add(sanPham);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setSanphamSearchAdapter() {
        SanPhamSearchAdapter adapter = new SanPhamSearchAdapter(getActivity(), android.R.layout.activity_list_item,sanPhamList);
        timkiem.setAdapter(adapter);
        //intent qua chi tiet
        timkiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),ChiTietActivity.class);
                intent.putExtra("chitiet",sanPhamList.get(position));
                startActivity(intent);
            }
        });
    }

    //get du lieu dong ho
    private void getDataDongHo() {
        //doc du lieu tu realtime database
        myRef = database.getReference("San_pham");
        myRef.child("Dongho").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DongHo sanPham= dataSnapshot.getValue(DongHo.class);
                    sanPham.setId(dataSnapshot.getKey());
                    dongHoList.add(sanPham);
                }
                dongHoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getDataSlide() {
        //doc du lieu tu realtime database
        myRef = database.getReference("Banner");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    photoList.add(new SlidePhoto(dataSnapshot.child("img").getValue().toString()));
                    //add vao list anh
                    viewSlidePhoto(photoList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}