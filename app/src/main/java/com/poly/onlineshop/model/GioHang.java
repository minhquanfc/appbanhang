package com.poly.onlineshop.model;

public class GioHang {
    private String tenSanpham;
    private int giaSanpham;
    private int soLuong;
    private String ngayMua;
    private String anh;

    public GioHang() {
    }

    public GioHang(String tenSanpham, int giaSanpham, int soLuong, String ngayMua, String anh) {
        this.tenSanpham = tenSanpham;
        this.giaSanpham = giaSanpham;
        this.soLuong = soLuong;
        this.ngayMua = ngayMua;
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

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
