package com.covalsys.ttss_barcode.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class StockCountLine {

    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "DocEntry")
    private Integer docEntry;

    @ColumnInfo(name = "DocNum")
    private Integer docNum;

    @ColumnInfo(name = "BatchNo")
    private String batchNo;

    @ColumnInfo(name = "SysQty")
    private Float sysQty;

    @ColumnInfo(name = "ActQty")
    private Float actQty;

    @ColumnInfo(name = "ItemCode")
    private String itemCode;

    @ColumnInfo(name = "ItemName")
    private String itemName;

    @ColumnInfo(name = "ScanDate")
    private String scanDate;

    @ColumnInfo(name = "Remarks")
    private String remarks;

    @ColumnInfo(name = "Status")
    private String status;

    public StockCountLine(Integer docEntry, Integer docNum, String batchNo, Float sysQty, Float actQty, String itemCode, String itemName, String remarks, String scanDate, String status) {
        this.docEntry = docEntry;
        this.docNum = docNum;
        this.batchNo = batchNo;
        this.sysQty = sysQty;
        this.actQty = actQty;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.scanDate = scanDate;
        this.remarks = remarks;
        this.status = status;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public Float getSysQty() {
        return sysQty;
    }

    public void setSysQty(Float quantity) {
        this.sysQty = quantity;
    }

    public Float getActQty() {
        return actQty;
    }

    public void setActQty(Float quantity) {
        this.actQty = quantity;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
