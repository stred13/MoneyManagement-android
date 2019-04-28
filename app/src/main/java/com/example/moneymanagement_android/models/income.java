package com.example.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "income")
public class income {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String note;
    private int nmoney;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Ignore
    public income(int id, String note, int nmoney) {
        this.id = id;
        this.note = note;
        this.nmoney = nmoney;
    }

    @Ignore
    public income(String note, int nmoney) {
        this.note = note;
        this.nmoney = nmoney;
    }

    public income(){}
}
