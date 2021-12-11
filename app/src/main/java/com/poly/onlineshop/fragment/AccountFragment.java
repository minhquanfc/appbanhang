package com.poly.onlineshop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.Activity.CaiDatActivity;
import com.poly.onlineshop.Activity.DonHangActivity;
import com.poly.onlineshop.Activity.GioHangActivity;
import com.poly.onlineshop.Activity.LoginActivity;
import com.poly.onlineshop.Activity.ThayDoiActivity;
import com.poly.onlineshop.Activity.YeuThichActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.User;


public class AccountFragment extends Fragment {

    ImageView img_edit;
    TextView tv_name_user, tv_mail_user, tv_donhangcuatoi, tv_yeuthich, tv_caidat, tv_giohang, tv_danhgia, tv_gioithieu, tv_hotro, tv_dangxuat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        img_edit = view.findViewById(R.id.img_edit);
        tv_name_user = view.findViewById(R.id.tv_name_user);
        tv_mail_user = view.findViewById(R.id.tv_mail_user);
        tv_donhangcuatoi = view.findViewById(R.id.tv_donhangcuatoi);
        tv_yeuthich = view.findViewById(R.id.tv_yeuthich);
        tv_caidat = view.findViewById(R.id.tv_caidat);
        tv_giohang = view.findViewById(R.id.tv_giohang);
        tv_danhgia = view.findViewById(R.id.tv_danhgia);
        tv_gioithieu = view.findViewById(R.id.tv_gioithieu);
        tv_hotro = view.findViewById(R.id.tv_hotro);
        tv_dangxuat = view.findViewById(R.id.tv_dangxuat);

        tv_donhangcuatoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonHangActivity.class);
                startActivity(intent);
            }
        });
        tv_caidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaiDatActivity.class);
                startActivity(intent);
            }
        });
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThayDoiActivity.class);
                startActivity(intent);
            }
        });
        tv_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GioHangActivity.class);
                startActivity(intent);
            }
        });
        //dang xuat
        tv_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        getInforUser();

        tv_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YeuThichActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Bạn có muốn đăng xuất không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void getInforUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        String idUser;
        idUser = user.getUid();
        reference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1 = snapshot.getValue(User.class);
                if (user1 != null) {
                    String name = user1.getHoTen();
                    String email = user1.getEmail();
                    tv_mail_user.setText(email);
                    tv_name_user.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}