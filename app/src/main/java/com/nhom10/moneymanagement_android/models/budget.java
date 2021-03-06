package com.nhom10.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.nhom10.moneymanagement_android.converters.dateConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(indices = @Index("id"))
public class budget implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String currency;

    @TypeConverters(dateConverters.class)
    private Date bdate;

    private String note;

    private long bmoney;

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

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

    public long getBmoney() {
        return bmoney;
    }

    public void setBmoney(long bmoney) {
        this.bmoney = bmoney;
    }

   /* public budget(String name, String currency, String note) {
        this.name = name;
        //this.currency = currency;
        this.note = note;
    }*/

    public budget(String name,  String note) {
        this.name = name;
        //this.currency = currency;
        //this.bdate = bdate;
        this.note = note;
    }

    @Ignore
    public budget() {
    }
}
