package com.poly.onlineshop.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.onlineshop.R;

public class DanhSachHolder extends RecyclerView.ViewHolder {
    TextView tv_tensp,tv_giasp;
    ImageView img_sp;
    CardView click;
    public DanhSachHolder(@NonNull View itemView) {
        super(itemView);
        tv_tensp=itemView.findViewById(R.id.tv_ten_danhsach);
        tv_giasp=itemView.findViewById(R.id.tv_gia_danhsach);
        img_sp=itemView.findViewById(R.id.img_sp_danhsach);
        click=itemView.findViewById(R.id.click_danhsach);

    }
}
