package com.covalsys.ttss_barcode.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AssetCountScanLine {

    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "DocEntry")
    private Integer docEntry;

    @ColumnInfo(name = "Itemcode")
    private String itemcode;

    @ColumnInfo(name = "ScanDate")
    private String scanDate;

    public AssetCountScanLine(Integer docEntry, String itemcode, String scanDate) {
        this.docEntry = docEntry;
        this.itemcode = itemcode;
        this.scanDate = scanDate;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }
}
