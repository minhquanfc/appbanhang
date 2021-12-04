package com.poly.onlineshop.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.onlineshop.R;

public class InfoOrderHolder extends RecyclerView.ViewHolder {
    TextView tv_ten_info_donhang,tv_soluong_info_donhang,tv_gia_info_donhang,tv_trangthai_info_donhang;
    ImageView img_sp_info_donhang;
    public InfoOrderHolder(@NonNull View itemView) {
        super(itemView);
        tv_gia_info_donhang=itemView.findViewById(R.id.tv_gia_info_donhang);
        tv_ten_info_donhang=itemView.findViewById(R.id.tv_ten_info_donhang);
        tv_soluong_info_donhang=itemView.findViewById(R.id.tv_soluong_info_donhang);
        tv_trangthai_info_donhang=itemView.findViewById(R.id.tv_trangthai_info_donhang);
        img_sp_info_donhang=itemView.findViewById(R.id.img_sp_info_donhang);
    }
}
