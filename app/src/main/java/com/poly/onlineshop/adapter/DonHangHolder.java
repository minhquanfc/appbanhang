package com.poly.onlineshop.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.onlineshop.R;

public class DonHangHolder extends RecyclerView.ViewHolder {
    TextView tv_ten_donhang,tv_soluong_donhang,tv_gia_donhang,tv_trangthai;
    CardView click_donhang;
    ImageView img_sp_donhang;
    public DonHangHolder(@NonNull View itemView) {
        super(itemView);
        tv_gia_donhang=itemView.findViewById(R.id.tv_gia_donhang);
        tv_ten_donhang=itemView.findViewById(R.id.tv_ten_donhang);
        tv_soluong_donhang=itemView.findViewById(R.id.tv_soluong_donhang);
        tv_trangthai=itemView.findViewById(R.id.tv_trangthai);
        click_donhang=itemView.findViewById(R.id.click_donhang);
        img_sp_donhang=itemView.findViewById(R.id.img_sp_donhang);

    }
}
