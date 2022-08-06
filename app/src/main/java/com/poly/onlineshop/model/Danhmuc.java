package com.poly.onlineshop.model;

public class Danhmuc {
    public String ten;
    public String img;


    public Danhmuc() {
    }

    public Danhmuc(String ten, String img, String loai) {
        this.ten = ten;
        this.img = img;

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
}
