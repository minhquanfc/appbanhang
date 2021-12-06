package com.poly.onlineshop.model;

public class ChiTietDH {
    public String tenSanpham;
    public int giaSanpham;
    public int soLuong;
    public int tongTien;
    public String anh;

    public ChiTietDH() {
    }

    public ChiTietDH(String tenSanpham, int giaSanpham, int soLuong, int tongTien, String anh) {
        this.tenSanpham = tenSanpham;
        this.giaSanpham = giaSanpham;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.anh = anh;
    }

    public String getTenSanpham() {
        return tenSanpham;
    }

    public void setTenSanpham(String tenSanpham) {
        this.tenSanpham = tenSanpham;
    }

    public int getGiaSanpham() {
        return giaSanpham;
    }

    public void setGiaSanpham(int giaSanpham) {
        this.giaSanpham = giaSanpham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
