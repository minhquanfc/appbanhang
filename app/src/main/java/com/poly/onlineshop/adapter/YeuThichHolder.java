package com.poly.onlineshop.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.onlineshop.R;

public class YeuThichHolder extends RecyclerView.ViewHolder {
    CardView click_yeuthich;
    ImageView img_sp_yeuthich,btn_tru_sp_yeuthich,btn_cong_sp_yeuthich;
    TextView tv_ten_sp_yeuthich,tv_gia_sp_yeuthich,tv_soluong_yeuthich;
    Button btn_add_sp_yeuthich;
    public YeuThichHolder(@NonNull View itemView) {
        super(itemView);
        click_yeuthich=itemView.findViewById(R.id.click_yeuthich);
        img_sp_yeuthich=itemView.findViewById(R.id.img_sp_yeuthich);
        btn_tru_sp_yeuthich=itemView.findViewById(R.id.btn_tru_sp_yeuthich);
        btn_cong_sp_yeuthich=itemView.findViewById(R.id.btn_cong_sp_yeuthich);
        tv_ten_sp_yeuthich=itemView.findViewById(R.id.tv_ten_sp_yeuthich);
        tv_gia_sp_yeuthich=itemView.findViewById(R.id.tv_gia_sp_yeuthich);
        tv_soluong_yeuthich=itemView.findViewById(R.id.tv_soluong_yeuthich);
        btn_add_sp_yeuthich=itemView.findViewById(R.id.btn_add_sp_yeuthich);

    }
}
