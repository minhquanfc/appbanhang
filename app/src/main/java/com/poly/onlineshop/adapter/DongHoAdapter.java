package com.poly.onlineshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.onlineshop.Activity.ChiTietActivity;
import com.poly.onlineshop.Activity.ChiTietDongHoActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.DongHo;
import com.poly.onlineshop.model.SanPham;

import java.util.List;

public class DongHoAdapter extends RecyclerView.Adapter<SanPhamHolder> {
    Context context;
    List<DongHo> dongHoList;

    public DongHoAdapter(Context context, List<DongHo> sanPhamList) {
        this.context = context;
        this.dongHoList = sanPhamList;
    }

    @NonNull
    @Override
    public SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham,parent,false);
        return new SanPhamHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHolder holder, @SuppressLint("RecyclerView") int position) {
        DongHo dongHo = dongHoList.get(position);
        holder.tv_gia_sanpham.setText(String.valueOf(dongHo.getGia()));
        Glide.with(holder.img_sanpham.getContext()).load(dongHo.getAnh()).into(holder.img_sanpham);
        holder.tv_ten_sanpham.setText(dongHo.getTen());
        holder.click_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietDongHoActivity.class);
                intent.putExtra("chitiet",dongHoList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dongHoList.size();
    }
}
