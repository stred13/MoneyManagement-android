package com.example.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class catincome implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public catincome(String name, String image) {
        this.name = name;
        this.image = image;
    }

    @Ignore
    public catincome() {
    }
}
