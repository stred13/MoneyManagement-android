package com.nhom10.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = @Index("id"))
public class catexpense implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    private int image;

    public catexpense(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    @Ignore
    public catexpense() {
    }

}
