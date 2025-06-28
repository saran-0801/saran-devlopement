package com.covalsys.ttss_barcode.data.network.models.get.gate;

public class SpinnerItem {

    public int id;
    public String text;

    public SpinnerItem(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
