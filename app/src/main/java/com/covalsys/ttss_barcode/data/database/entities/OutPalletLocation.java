package com.covalsys.ttss_barcode.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OutPalletLocation {

    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "PalletLocationCode")
    private String palletLocationCode;

    @ColumnInfo(name = "Flag")
    private String flag;

    public OutPalletLocation(@NonNull String palletLocationCode, String flag) {
        this.palletLocationCode = palletLocationCode;
        this.flag = flag;
    }

    @NonNull
    public String getPalletLocationCode() {
        return palletLocationCode;
    }

    public void setPalletLocationCode(@NonNull String palletLocationCode) {
        this.palletLocationCode = palletLocationCode;
    }

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
        return palletLocationCode;
    }

}
