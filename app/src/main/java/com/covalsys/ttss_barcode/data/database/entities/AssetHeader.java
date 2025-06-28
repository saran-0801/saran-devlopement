package com.covalsys.ttss_barcode.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AssetHeader {
    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "DocNum")
    private Integer docNum;

    @ColumnInfo(name = "BinCode")
    private String binCode;

    @ColumnInfo(name = "DocDate")
    private String docDate;

    @ColumnInfo(name = "Remarks")
    private String remarks;

    @ColumnInfo(name = "Status")
    private String status;

    public AssetHeader(Integer docNum, String binCode, String remarks, String docDate, String status) {

        this.docNum = docNum;
        this.binCode = binCode;
        this.docDate = docDate;
        this.remarks = remarks;
        this.status = status;
    }

    public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
