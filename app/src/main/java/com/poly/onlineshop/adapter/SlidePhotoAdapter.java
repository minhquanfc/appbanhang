package com.poly.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.SlidePhoto;

import java.util.List;

public class SlidePhotoAdapter extends PagerAdapter {
    Context context;
    List<SlidePhoto> photoList;

    public SlidePhotoAdapter(Context context, List<SlidePhoto> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo,null);
        ImageView imageView = view.findViewById(R.id.imgphoto);
        SlidePhoto photo = photoList.get(position);
//        imageView.setImageResource(photo.getId_anh());
        Glide.with(context).load(photo.getId_anh()).into(imageView);

        //add view
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
