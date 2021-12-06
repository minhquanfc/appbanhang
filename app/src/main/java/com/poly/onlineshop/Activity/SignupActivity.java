package com.poly.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    TextView tv_dangnhap;
    Button btnSignin;
    EditText ed_email_signin, ed_pass_signin, ed_sdt_signin, ed_diachi_signin, ed_hoten_signin;
    List<User> userList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ed_email_signin = findViewById(R.id.ed_email_signin);
        ed_hoten_signin = findViewById(R.id.ed_hoTen_signin);
        ed_pass_signin = findViewById(R.id.ed_pass_signin);
        ed_sdt_signin = findViewById(R.id.ed_sdt_signin);
        ed_diachi_signin = findViewById(R.id.ed_diachi_signin);
        tv_dangnhap = findViewById(R.id.tv_dangnhap);
        btnSignin = findViewById(R.id.btnSignin);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        progressDialog = new ProgressDialog(this);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = ed_hoten_signin.getText().toString().trim();
                String email = ed_email_signin.getText().toString().trim();
                String password = ed_pass_signin.getText().toString().trim();
                String sdt = ed_sdt_signin.getText().toString().trim();
                String diachi = ed_diachi_signin.getText().toString().trim();
                if (hoTen.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
                    ed_hoten_signin.requestFocus();
                    return;
                } else if (email.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    ed_email_signin.requestFocus();
                    return;
                } else if (password.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    ed_pass_signin.requestFocus();
                    return;
                } else if (sdt.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    ed_sdt_signin.requestFocus();
                    return;
                } else if (diachi.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    ed_diachi_signin.requestFocus();
                    return;
                }
                int numpass;
                try {
                    numpass = Integer.parseInt(password);
                } catch (Exception e) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập mật khẩu là số", Toast.LENGTH_SHORT).show();
                    ed_pass_signin.requestFocus();
                }
                int numsdt;
                try {
                    numsdt = Integer.parseInt(ed_sdt_signin.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập số điện thoại là số", Toast.LENGTH_SHORT).show();
                    ed_sdt_signin.requestFocus();
                    return;
                }
                if (sdt.length()<10) {
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập số điện thoại trên 10 số", Toast.LENGTH_SHORT).show();
                    ed_sdt_signin.requestFocus();
                    return;
                }
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
//                            User user = new User(hoTen, email, password, sdt, diachi);
                            Map<String,Object> map = new HashMap<>();
                            map.put("hoTen",hoTen);
                            map.put("email",email);
                            map.put("pass",password);
                            map.put("sdt",sdt);
                            map.put("diachi",diachi);
                            database.getReference("User").child(firebaseAuth.getCurrentUser().getUid()).setValue(map);
                            Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tv_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}