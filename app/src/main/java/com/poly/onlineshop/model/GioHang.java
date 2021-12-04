package com.poly.onlineshop.model;

public class GioHang {
    private String tenSanpham;
    private int giaSanpham;
    private int soLuong;
    private String anh;
    private int tongTien;
    public GioHang() {
    }

    public GioHang( String tenSanpham, int giaSanpham, int soLuong, String anh, int tongTien) {
        this.tenSanpham = tenSanpham;
        this.giaSanpham = giaSanpham;
        this.soLuong = soLuong;
        this.anh = anh;
        this.tongTien = tongTien;
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

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
