package com.poly.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.DonHang;

import java.util.List;

public class InfoOrderAdapter extends RecyclerView.Adapter<InfoOrderHolder> {
    Context context;
    List<DonHang>donHangList;

    public InfoOrderAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public InfoOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donhang,parent,false);
        return new InfoOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoOrderHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        holder.tv_ten_info_donhang.setText(donHang.getIdOrder());
        holder.tv_gia_info_donhang.setText(String.valueOf(donHang.getTongTien()));
        holder.tv_soluong_info_donhang.setText(String.valueOf(donHang.getSoLuong())+" sản phẩm");
        holder.tv_trangthai_info_donhang.setText(donHang.getTrangThai());
//        Glide.with(context).load(donHang.()).into(holder.img_sp_info_donhang);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
