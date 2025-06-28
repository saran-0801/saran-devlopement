package com.covalsys.ttss_barcode.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pallet {

    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "PalletCode")
    private String palletCode;

    @ColumnInfo(name = "Flag")
    private String flag;

    public Pallet(@NonNull String palletCode, String flag) {
        this.palletCode = palletCode;
        this.flag = flag;
    }

    @NonNull
    public String getPalletCode() {
        return palletCode;
    }

    public void setPalletCode(@NonNull String palletCode) {
        this.palletCode = palletCode;
    }

    @NonNull
    public Integer getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @NonNull
    @Override
    public String toString(){
        return palletCode;
    }

}
