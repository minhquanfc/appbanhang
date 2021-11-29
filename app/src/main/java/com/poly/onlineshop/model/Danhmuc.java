package com.poly.onlineshop.model;

public class Danhmuc {
    public String ten;
    public String img;
    public String loai;

    public Danhmuc() {
    }

    public Danhmuc(String ten, String img, String loai) {
        this.ten = ten;
        this.img = img;
        this.loai = loai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
