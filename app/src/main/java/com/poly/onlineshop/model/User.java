package com.poly.onlineshop.model;

public class User {
    public String hoTen;
    public String email;
    public String pass;
    public String sdt;
    public String diachi;
    public String anh;

    public User() {
    }

    public User(String hoTen, String email, String pass, String sdt, String diachi, String anh) {
        this.hoTen = hoTen;
        this.email = email;
        this.pass = pass;
        this.sdt = sdt;
        this.diachi = diachi;
        this.anh = anh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
