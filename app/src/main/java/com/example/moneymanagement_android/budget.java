package com.example.moneymanagement_android;

public class budget {
    private String bName;
    private String bNote;
    private int urlImage;

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbNote() {
        return bNote;
    }

    public void setbNote(String bNote) {
        this.bNote = bNote;
    }

    public int getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(int urlImage) {
        this.urlImage = urlImage;
    }

    public budget(String bName, String bNote, int urlImage) {
        this.bName = bName;
        this.bNote = bNote;
        this.urlImage = urlImage;
    }

    public budget() {

    }
}
