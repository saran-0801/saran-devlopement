package com.covalsys.ttss_barcode.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OutwardMaster {

    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "PalletCode")
    private String palletCode;

    @ColumnInfo(name = "PalletLocationCode")
    private String palletLocationCode;

    @ColumnInfo(name = "Flag")
    private String flag;

    @ColumnInfo(name = "ChBox")
    private Boolean chBox;

    @ColumnInfo(name = "stDate")
    private String stDate;

    public OutwardMaster(@NonNull String palletCode, String palletLocationCode, String flag, String stDate, Boolean chBox) {
        this.palletCode = palletCode;
        this.palletLocationCode = palletLocationCode;
        this.flag = flag;
        this.stDate = stDate;
        this.chBox = chBox;
    }

    @NonNull
    public String getPalletCode() {
        return palletCode;
    }

    public void setPalletCode(@NonNull String palletCode) {
        this.palletCode = palletCode;
    }

    public String getPalletLocationCode() {
        return palletLocationCode;
    }

    public void setPalletLocationCode(String palletLocationCode) {
        this.palletLocationCode = palletLocationCode ;
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

    public String getStDate() {
        return stDate;
    }

    public void setStDate(String stDate) {
        this.stDate = stDate;
    }

    public Boolean getChBox() {
        return chBox;
    }

    public void setChBox(Boolean chBox) {
        this.chBox = chBox;
    }

    @NonNull
    @Override
    public String toString(){
        return palletCode;
    }

}
