package com.example.moneymanagement_android.models;

public class image {
    private String name;
    private int Hinh;

    public image(String name, int hinh) {
        this.name = name;
        Hinh = hinh;
    }

    public String getName() {
        return name;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }
}
