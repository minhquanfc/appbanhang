package com.poly.onlineshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.GioHang;

import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangHolder> {
    Context context;
    List<GioHang> gioHangList;
    int soluong = 1;
    int tonggia = 0;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public GioHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang,parent,false);
        return new GioHangHolder(view);
    }

    @SuppressLint("RecyclerView")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull GioHangHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.tv_ten_sp_giohang.setText(gioHang.getTenSanpham());
        holder.tv_gia_sp_giohang.setText(String.valueOf(gioHang.getGiaSanpham()));
        holder.tv_soluong_giohang.setText(String.valueOf(gioHang.getSoLuong()));
        Glide.with(context).load(gioHang.getAnh()).into(holder.img_sp_giohang);
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHangList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.btn_cong_sp_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soluong < 10) {
                    soluong++;
                    holder.tv_soluong_giohang.setText(String.valueOf(soluong));
                    tonggia = gioHang.getGiaSanpham() * soluong;
                }
            }
        });
        holder.btn_tru_sp_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soluong > 1) {
                    soluong--;
                    holder.tv_soluong_giohang.setText(String.valueOf(soluong));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }
}
