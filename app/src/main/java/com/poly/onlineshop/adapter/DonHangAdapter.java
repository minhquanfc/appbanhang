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
import com.poly.onlineshop.Activity.InfoOrderActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.DonHang;
import com.poly.onlineshop.model.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangHolder> {
    Context context;
    List<DonHang> donHangList;

    public DonHangAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public DonHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donhang,parent,false);
        return new DonHangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangHolder holder, @SuppressLint("RecyclerView") int position) {
        DonHang donHang = donHangList.get(position);
        holder.tv_ten_donhang.setText("Mã đơn: "+donHang.getIdOrder());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_gia_donhang.setText("Thành tiền : "+decimalFormat.format(donHang.getTongTien()));
        holder.tv_soluong_donhang.setText(String.valueOf(donHang.getSoLuong())+" sản phẩm");
        holder.tv_trangthai.setText(donHang.getTrangThai());
//        Glide.with(context).load(gioHang.getAnh()).into(holder.img_sp_donhang);
        holder.click_donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoOrderActivity.class);
                intent.putExtra("donhang",donHangList.get(position));
                intent.putExtra("id",donHang.getIdOrder());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }
}
