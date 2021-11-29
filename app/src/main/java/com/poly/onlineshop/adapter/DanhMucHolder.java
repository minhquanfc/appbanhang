package com.poly.onlineshop.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.onlineshop.R;

public class DanhMucHolder extends RecyclerView.ViewHolder {
    TextView tv_ten_danhmuc;
    ImageView img_danhmuc;
    RelativeLayout click;
    public DanhMucHolder(@NonNull View itemView) {
        super(itemView);
        tv_ten_danhmuc=itemView.findViewById(R.id.tv_ten_danhmuc);
        img_danhmuc=itemView.findViewById(R.id.img_danhmuc);
        click=itemView.findViewById(R.id.click_danhmuc);
    }
}
