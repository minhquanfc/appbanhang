package com.poly.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.poly.onlineshop.Activity.ChiTietActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.SanPham;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YeuThichAdapter extends RecyclerView.Adapter<YeuThichHolder> {
    Context context;
    List<SanPham> sanPhamList;
    int soluong = 1;
    int tongGia = 0;

    public YeuThichAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public YeuThichHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yeuthich, parent, false);
        return new YeuThichHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YeuThichHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.tv_ten_sp_yeuthich.setText(sanPham.getTen());
        holder.tv_gia_sp_yeuthich.setText(String.valueOf(sanPham.getGia()));
        Glide.with(context).load(sanPham.getAnh()).into(holder.img_sp_yeuthich);
        holder.btn_cong_sp_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soluong < 10) {
                    soluong++;
                    holder.tv_soluong_yeuthich.setText(String.valueOf(soluong));
                    tongGia = sanPham.getGia() * soluong;
                }
            }
        });
        holder.btn_tru_sp_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soluong > 1) {
                    soluong--;
                    holder.tv_soluong_yeuthich.setText(String.valueOf(soluong));
                    tongGia = sanPham.getGia() * soluong;
                }
            }
        });
        holder.btn_add_sp_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mRef = database.getReference("GioHang");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Map<String, Object> cartMap = new HashMap<>();
                cartMap.put("tenSanpham",holder.tv_ten_sp_yeuthich.getText().toString());
                cartMap.put("giaSanpham", Integer.valueOf(holder.tv_gia_sp_yeuthich.getText().toString()));
                cartMap.put("soLuong", soluong);
                tongGia = sanPham.getGia() * soluong;
                cartMap.put("tongTien", tongGia);
                cartMap.put("anh", sanPham.getAnh());
                String key = mRef.push().getKey();

                mRef.child(user.getUid()).child(key).setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }
}
