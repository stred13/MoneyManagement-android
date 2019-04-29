package com.example.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class budget implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String currency;

    private String note;

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

    public budget(String name, String currency, String note) {
        this.name = name;
        this.currency = currency;
        this.note = note;
    }

    @Ignore
    public budget() {
    }
}
