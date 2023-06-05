package com.poly.onlineshop.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.poly.onlineshop.MainActivity;
import com.poly.onlineshop.R;
import com.poly.onlineshop.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ThayDoiActivity extends AppCompatActivity {

    EditText ed_mail_thaydoi, ed_sdt_thaydoi, ed_diachi_thaydoi, ed_hoTen_thaydoi;
    Toolbar toolbar;
    Button btn_thaydoi;
    ImageView imageView;
    FirebaseUser user;
    DatabaseReference reference;
    String idUser;
    ProgressDialog progressDialog;

    private StorageReference Sreference = FirebaseStorage.getInstance().getReference();
    private Uri imgUrl;

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
        imageView = findViewById(R.id.anh_avt);
        progressDialog =new ProgressDialog(this);
        setSupportActionBar(toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        idUser = user.getUid();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog.setMessage("Vui lòng chờ");

//        ed_mail_thaydoi.setEnabled(false);

        //hien thi du lieu
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
                    if (user1.getAnh()!=null){
                        Glide.with(ThayDoiActivity.this).load(user1.getAnh()).into(imageView);
                    }                }
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
                if (imgUrl !=null){
                    progressDialog.show();
                    add(imgUrl);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(ThayDoiActivity.this, "Vui lòng thêm ảnh", Toast.LENGTH_SHORT).show();
                }
//                UpdateProfile();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });
    }

    private void add(Uri imgUrl) {
        final  StorageReference fileRef = Sreference.child(System.currentTimeMillis()+"."+ getFileExtension(imgUrl));
        fileRef.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String name = ed_hoTen_thaydoi.getText().toString().trim();
                        String email = ed_mail_thaydoi.getText().toString().trim();
                        String sdt1 = ed_sdt_thaydoi.getText().toString().trim();
                        String diachi1 = ed_diachi_thaydoi.getText().toString().trim();
                        Map<String, Object> map = new HashMap<>();
                        map.put("hoTen", name);
                        map.put("email",email);
                        map.put("sdt", sdt1);
                        map.put("diachi", diachi1);
                        map.put("anh",uri.toString());
                        reference.child(idUser).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                UpdateEmail();
                                Toast.makeText(ThayDoiActivity.this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void UpdateEmail() {
        String email = ed_mail_thaydoi.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(email);
    }


    private void UpdateProfile() {
        progressDialog.show();
        String name = ed_hoTen_thaydoi.getText().toString().trim();
        String email = ed_mail_thaydoi.getText().toString().trim();
        String sdt1 = ed_sdt_thaydoi.getText().toString().trim();
        String diachi1 = ed_diachi_thaydoi.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("hoTen", name);
        map.put("email",email);
        map.put("sdt", sdt1);
        map.put("diachi", diachi1);
        reference.child(idUser).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                UpdateEmail();
                Toast.makeText(ThayDoiActivity.this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void Reauthenticate(){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        AuthCredential credential = EmailAuthProvider
//                .getCredential("user@example.com", "password1234");
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                    }
//                });
//    }


    private String getFileExtension(Uri imgUrl) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(imgUrl));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data !=null) {
            imgUrl = data.getData();
            imageView.setImageURI(imgUrl);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}