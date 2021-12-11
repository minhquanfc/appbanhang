package com.poly.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanPhamSearchAdapter extends ArrayAdapter<SanPham> {
    Context context;
    List<SanPham> sanPhamList;

    public SanPhamSearchAdapter(@NonNull Context context, int resource, List<SanPham> sanPhamList) {
        super(context, resource);
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        }

        ImageView imgSearch = convertView.findViewById(R.id.img_search);
        TextView tvSearchName = convertView.findViewById(R.id.tv_search_name);
        TextView tvSearchPrice = convertView.findViewById(R.id.tv_search_price);

        SanPham product = getItem(position);

        Glide.with(imgSearch.getContext()).load(product.getAnh()).into(imgSearch);
        tvSearchName.setText(product.getTen());
        tvSearchPrice.setText(product.getGia()+"đ");
        return convertView;
    }
    @NonNull
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<SanPham> listSuggest = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    listSuggest.addAll(sanPhamList);
                } else {
                    String filter = constraint.toString().toLowerCase().trim();
                    for (SanPham p : sanPhamList) {
                        if (p.getTen().toLowerCase().contains(filter)) {
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
                clear();
                addAll((List<SanPham>) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((SanPham) resultValue).getTen();
            }
        };
    }
}
