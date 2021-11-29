package com.poly.onlineshop.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.onlineshop.R;

public class SanPhamHolder extends RecyclerView.ViewHolder {
    TextView tv_ten_sanpham,tv_gia_sanpham;
    ImageView img_sanpham;
    RelativeLayout click_sanpham;
    public SanPhamHolder(@NonNull View itemView) {
        super(itemView);
        tv_gia_sanpham=itemView.findViewById(R.id.tv_gia_sanpham);
        tv_ten_sanpham=itemView.findViewById(R.id.tv_ten_sanpham);
        img_sanpham=itemView.findViewById(R.id.img_sanpham);
        click_sanpham=itemView.findViewById(R.id.click_sanpham);
    }
}
