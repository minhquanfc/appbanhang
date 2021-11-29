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
import com.poly.onlineshop.Activity.DanhSachActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.Danhmuc;

import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucHolder> {

    Context context;
    List<Danhmuc> danhmucList;

    public DanhMucAdapter(Context context, List<Danhmuc> danhmucList) {
        this.context = context;
        this.danhmucList = danhmucList;
    }

    @NonNull
    @Override
    public DanhMucHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc,parent,false);
        return new DanhMucHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucHolder holder, @SuppressLint("RecyclerView") int position) {
        Danhmuc danhmuc = danhmucList.get(position);
        holder.tv_ten_danhmuc.setText(danhmuc.getTen());
        Glide.with(holder.img_danhmuc).load(danhmuc.getImg()).into(holder.img_danhmuc);
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhSachActivity.class);
                intent.putExtra("type",danhmucList.get(position).getLoai());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhmucList.size();
    }
}
