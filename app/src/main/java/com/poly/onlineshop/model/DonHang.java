package com.poly.onlineshop.model;

import java.io.Serializable;

public class DonHang implements Serializable {
    private String idOrder;
    private String diaChi;
    private String hoTen;
    private String soDienThoai;
    private String ngayMua;
    private String trangThai;
    private int soLuong;
    private int tongTien;

    public DonHang() {
    }

    public DonHang(String orderNo, String diaChi, String hoTen, String soDienThoai, String ngayMua, String trangThai, int soLuong, int tongTien) {
        idOrder = orderNo;
        this.diaChi = diaChi;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.ngayMua = ngayMua;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String orderNo) {
        idOrder = orderNo;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
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
}
