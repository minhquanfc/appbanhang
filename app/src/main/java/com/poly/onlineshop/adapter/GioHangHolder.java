package com.poly.onlineshop.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.onlineshop.R;

public class GioHangHolder extends RecyclerView.ViewHolder {
    TextView tv_ten_sp_giohang,tv_gia_sp_giohang,tv_soluong_giohang;
    ImageView img_delete,img_sp_giohang,btn_tru_sp_giohang,btn_cong_sp_giohang;
    public GioHangHolder(@NonNull View itemView) {
        super(itemView);
        tv_gia_sp_giohang=itemView.findViewById(R.id.tv_gia_sp_giohang);
        tv_ten_sp_giohang=itemView.findViewById(R.id.tv_ten_sp_giohang);
        tv_soluong_giohang=itemView.findViewById(R.id.tv_soluong_giohang);
        img_delete=itemView.findViewById(R.id.img_delete);
        img_sp_giohang=itemView.findViewById(R.id.img_sp_giohang);
        btn_tru_sp_giohang=itemView.findViewById(R.id.btn_tru_sp_giohang);
        btn_cong_sp_giohang=itemView.findViewById(R.id.btn_cong_sp_giohang);
    }
}