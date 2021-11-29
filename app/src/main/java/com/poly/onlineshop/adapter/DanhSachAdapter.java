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
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.SanPham;

import java.util.List;

public class DanhSachAdapter extends RecyclerView.Adapter<DanhSachHolder> {
    Context context;
    List<SanPham> sanPhamList;

    public DanhSachAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public DanhSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_sanpham,parent,false);
        return new DanhSachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.tv_giasp.setText(String.valueOf(sanPham.getGia()));
        Glide.with(holder.img_sp.getContext()).load(sanPham.getAnh()).into(holder.img_sp);
        holder.tv_tensp.setText(sanPham.getTen());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietActivity.class);
                intent.putExtra("chitiet",sanPhamList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }
}
