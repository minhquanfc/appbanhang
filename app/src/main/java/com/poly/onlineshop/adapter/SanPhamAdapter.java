package com.poly.onlineshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.onlineshop.Activity.ChiTietActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamHolder> implements Filterable {
    Context context;
    List<SanPham> sanPhamList;

    public SanPhamAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham,parent,false);
        return new SanPhamHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.tv_gia_sanpham.setText(String.valueOf(sanPham.getGia()));
        Glide.with(holder.img_sanpham.getContext()).load(sanPham.getAnh()).into(holder.img_sanpham);
        holder.tv_ten_sanpham.setText(sanPham.getTen());
        holder.click_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietActivity.class);
                intent.putExtra("chitiet",sanPhamList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<SanPham> listSuggest = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    listSuggest.addAll(sanPhamList);
                }
                else {
                    String filter = constraint.toString().toLowerCase().trim();
                    for (SanPham p : sanPhamList){
                        if (p.getTen().toLowerCase().contains(filter)){
                            listSuggest.add(p);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listSuggest;
                filterResults.count = listSuggest.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sanPhamList = (List<SanPham>) results.values;
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ( (SanPham) resultValue).getTen();
            }
        };
    }
}
