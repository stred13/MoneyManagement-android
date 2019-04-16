package com.example.moneymanagement_android.models;

public class budget {
    private int id;
    private String bName;
    private String bCurrency;
    private String bNote;
    private String bnMoney;
    private int burlImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbCurrency() {
        return bCurrency;
    }

    public void setbCurrency(String bCurrency) {
        this.bCurrency = bCurrency;
    }

    public String getbNote() {
        return bNote;
    }

    public void setbNote(String bNote) {
        this.bNote = bNote;
    }

    public String getBnMoney() {
        return bnMoney;
    }

    public void setBnMoney(String bnMoney) {
        this.bnMoney = bnMoney;
    }

    public int getBurlImage() {
        return burlImage;
    }

    public void setBurlImage(int burlImage) {
        this.burlImage = burlImage;
    }

    public budget(String bName, String bCurrency, String bNote, String bnMoney, int burlImage) {
        this.bName = bName;
        this.bCurrency = bCurrency;
        this.bNote = bNote;
        this.bnMoney = bnMoney;
        this.burlImage = burlImage;
    }

    public budget(int id, String bName, String bCurrency, String bNote, String bnMoney, int burlImage) {
        this.id = id;
        this.bName = bName;
        this.bCurrency = bCurrency;
        this.bNote = bNote;
        this.bnMoney = bnMoney;
        this.burlImage = burlImage;
    }

    public budget() {

    }
}
