package com.poly.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.ChiTietDH;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietDHAdapter extends RecyclerView.Adapter<ChiTietDHHolder> {
    Context context;
    List<ChiTietDH> chiTietDHList;

    public ChiTietDHAdapter(Context context, List<ChiTietDH> chiTietDHList) {
        this.context = context;
        this.chiTietDHList = chiTietDHList;
    }

    @NonNull
    @Override
    public ChiTietDHHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chitiet_donhang,parent,false);
        return new ChiTietDHHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietDHHolder holder, int position) {
        ChiTietDH dh = chiTietDHList.get(position);
        holder.tv_tensp_chitiet_donhang.setText(dh.getTenSanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_giasp_chitiet_donhang.setText("Giá: "+decimalFormat.format(dh.getGiaSanpham())+"đ");
        holder.tv_soluongsp_chitiet_donhang.setText(String.valueOf("Số lượng: "+dh.getSoLuong()));
        holder.tv_tongtiensp_chitiet_donhang.setText(String.valueOf("Tổng tiền: "+decimalFormat.format(dh.getTongTien())+"đ"));
        Glide.with(context).load(dh.getAnh()).into(holder.anh_sp_chitiet_donhang);
    }

    @Override
    public int getItemCount() {
        return chiTietDHList.size();
    }
}
