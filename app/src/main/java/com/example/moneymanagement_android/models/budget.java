package com.example.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class budget {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String currency;

    private String note;

    private int nmoney;

    private int urlimage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNmoney() {
        return nmoney;
    }

    public void setNmoney(int nmoney) {
        this.nmoney = nmoney;
    }

    public int getUrlimage() {
        return urlimage;
    }

    public void setUrlimage(int urlimage) {
        this.urlimage = urlimage;
    }

    @Ignore
    public budget(int id, String name, String currency, String note, int nmoney, int urlimage) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.note = note;
        this.nmoney = nmoney;
        this.urlimage = urlimage;
    }

    public budget(String name, String currency, String note, int nmoney, int urlimage) {
        this.name = name;
        this.currency = currency;
        this.note = note;
        this.nmoney = nmoney;
        this.urlimage = urlimage;
    }

}
