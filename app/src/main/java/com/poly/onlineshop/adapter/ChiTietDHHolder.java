package com.poly.onlineshop.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.onlineshop.R;

public class ChiTietDHHolder extends RecyclerView.ViewHolder {
    TextView tv_tensp_chitiet_donhang,tv_giasp_chitiet_donhang,tv_soluongsp_chitiet_donhang,tv_tongtiensp_chitiet_donhang;
    ImageView anh_sp_chitiet_donhang;
    public ChiTietDHHolder(@NonNull View itemView) {
        super(itemView);
        tv_tensp_chitiet_donhang=itemView.findViewById(R.id.tv_tensp_chitiet_donhang);
        tv_giasp_chitiet_donhang=itemView.findViewById(R.id.tv_giasp_chitiet_donhang);
        tv_soluongsp_chitiet_donhang=itemView.findViewById(R.id.tv_soluongsp_chitiet_donhang);
        tv_tongtiensp_chitiet_donhang=itemView.findViewById(R.id.tv_tongtiensp_chitiet_donhang);
        anh_sp_chitiet_donhang=itemView.findViewById(R.id.anh_sp_chitiet_donhang);
    }
}
