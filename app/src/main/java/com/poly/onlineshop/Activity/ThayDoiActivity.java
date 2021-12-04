package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.User;

import java.util.HashMap;
import java.util.Map;

public class ThayDoiActivity extends AppCompatActivity {

    EditText ed_mail_thaydoi, ed_sdt_thaydoi, ed_diachi_thaydoi, ed_hoTen_thaydoi;
    Toolbar toolbar;
    Button btn_thaydoi;
    FirebaseUser user;
    DatabaseReference reference;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi);
        toolbar = findViewById(R.id.toolbar_thaydoi);
        ed_mail_thaydoi = findViewById(R.id.ed_mail_thaydoi);
        ed_sdt_thaydoi = findViewById(R.id.ed_sdt_thaydoi);
        ed_diachi_thaydoi = findViewById(R.id.ed_diachi_thaydoi);
        ed_hoTen_thaydoi = findViewById(R.id.ed_hoTen_thaydoi);
        btn_thaydoi = findViewById(R.id.btn_thaydoi);
        setSupportActionBar(toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        idUser = user.getUid();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_mail_thaydoi.setEnabled(false);
        reference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1 = snapshot.getValue(User.class);
                if (user1 != null) {
                    String name = user1.getHoTen();
                    String email = user1.getEmail();
                    String sdt = user1.getSdt();
                    String diachi = user1.getDiachi();
                    ed_hoTen_thaydoi.setText(name);
                    ed_mail_thaydoi.setText(email);
                    ed_sdt_thaydoi.setText(sdt);
                    ed_diachi_thaydoi.setText(diachi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_hoTen_thaydoi.getText().toString().isEmpty()) {
                    Toast.makeText(ThayDoiActivity.this, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
                    ed_hoTen_thaydoi.requestFocus();
                    return;
                } else if (ed_sdt_thaydoi.getText().toString().isEmpty()) {
                    Toast.makeText(ThayDoiActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    ed_sdt_thaydoi.requestFocus();
                    return;
                } else if (ed_diachi_thaydoi.getText().toString().isEmpty()) {
                    Toast.makeText(ThayDoiActivity.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    ed_diachi_thaydoi.requestFocus();
                    return;
                }
                int sdt;
                try {
                    sdt = Integer.valueOf(ed_sdt_thaydoi.getText().toString());
                }catch (Exception e){
                    Toast.makeText(ThayDoiActivity.this, "Vui lòng nhập số điện thoại là số", Toast.LENGTH_SHORT).show();
                    ed_sdt_thaydoi.requestFocus();
                    return;
                }
                if (ed_sdt_thaydoi.getText().toString().length()<10) {
                    Toast.makeText(ThayDoiActivity.this, "Vui lòng nhập số điện thoại trên 10 số", Toast.LENGTH_SHORT).show();
                    ed_sdt_thaydoi.requestFocus();
                    return;
                }
                UpdateProfile();
            }
        });
    }

    private void UpdateProfile() {
        String name = ed_hoTen_thaydoi.getText().toString().trim();
        String sdt1 = ed_sdt_thaydoi.getText().toString().trim();
        String diachi1 = ed_diachi_thaydoi.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("hoTen", name);
        map.put("sdt", sdt1);
        map.put("diachi", diachi1);
        reference.child(idUser).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ThayDoiActivity.this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}