package com.example.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import com.example.moneymanagement_android.converters.dateConverters;

import java.util.Date;

@Entity(tableName = "income", foreignKeys = {
        @ForeignKey(
                entity = budget.class,
                parentColumns = "id",
                childColumns = "idbudget",
                onUpdate = ForeignKey.CASCADE),
        @ForeignKey(
                entity = catincome.class,
                parentColumns = "id",
                childColumns = "idcatin",
                onUpdate = ForeignKey.CASCADE)
})
public class income {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String note;

    private long nmoney;

    @TypeConverters(dateConverters.class)
    private Date dcreated;

    private int idcatin;

    private int idbudget;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getNmoney() {
        return nmoney;
    }

    public void setNmoney(long nmoney) {
        this.nmoney = nmoney;
    }

    public Date getDcreated() {
        return dcreated;
    }

    public void setDcreated(Date dcreated) {
        this.dcreated = dcreated;
    }

    public int getIdcatin() {
        return idcatin;
    }

    public void setIdcatin(int idcatin) {
        this.idcatin = idcatin;
    }

    public int getIdbudget() {
        return idbudget;
    }

    public void setIdbudget(int idbudget) {
        this.idbudget = idbudget;
    }

    public income(String name, String note, long nmoney, Date dcreated, int idcatin, int idbudget) {
        this.name = name;
        this.note = note;
        this.nmoney = nmoney;
        this.dcreated = dcreated;
        this.idcatin = idcatin;
        this.idbudget = idbudget;
    }

    @Ignore
    public income(){

    }
}
