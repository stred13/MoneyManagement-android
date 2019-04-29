package com.example.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class catincome {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int name;

    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public catincome(int name, String image) {
        this.name = name;
        this.image = image;
    }

    @Ignore
    public catincome() {
    }
}
