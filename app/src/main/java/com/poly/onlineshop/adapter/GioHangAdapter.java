package com.poly.onlineshop.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.poly.onlineshop.MainActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.GioHang;

import org.w3c.dom.Document;

import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangHolder> {
    Context context;
    List<GioHang> gioHangList;
    int tongtien = 0;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public GioHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new GioHangHolder(view);
    }

    @SuppressLint("RecyclerView")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull GioHangHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.tv_ten_sp_giohang.setText(gioHang.getTenSanpham());
        holder.tv_gia_sp_giohang.setText(String.valueOf(gioHang.getGiaSanpham()));
        holder.tv_soluong_giohang.setText(String.valueOf(gioHang.getSoLuong()));
        Glide.with(context).load(gioHang.getAnh()).into(holder.img_sp_giohang);

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = database.getReference("GioHang");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference.child(user.getUid()).child(gioHang.getId()).removeValue();
                        gioHangList.remove(position);
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                notifyDataSetChanged();
            }
        });

        tongtien = 0;
        for(int i=0;i<gioHangList.size();i++)
        {
            tongtien += gioHangList.get(i).getTongTien();
        }
        Intent intent = new Intent("Tongtien");
        intent.putExtra("tongtien", tongtien);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }
}
