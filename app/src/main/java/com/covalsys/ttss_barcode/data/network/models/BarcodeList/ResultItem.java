package com.covalsys.ttss_barcode.data.network.models.BarcodeList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultItem implements Serializable {

    @SerializedName("docNum")
    private String docNum;

    @SerializedName("millCode")
    private String millCode;

    @SerializedName("pcsPerPkt")
    private String pcsPerPkt;

    @SerializedName("docentry")
    private int docentry;

    @SerializedName("netwt")
    private String netwt;

    @SerializedName("description")
    private String description;

    @SerializedName("batchnum")
    private String batchnum;

    @SerializedName("type")
    private String type;

    @SerializedName("spec")
    private String spec;

    @SerializedName("uid")
    private int uid;

    @SerializedName("uom")
    private String uom;

    @SerializedName("updatedate")
    private String updatedate;

    @SerializedName("dcValidation")
    private String dcValidation;

    @SerializedName("linenum")
    private int linenum;

    @SerializedName("secScanUpdate")
    private String secScanUpdate;

    @SerializedName("grosswt")
    private Double grosswt;

    @SerializedName("saP_POSTED")
    private String saPPOSTED;

    @SerializedName("pcs")
    private Double pcs;

    @SerializedName("quantity")
    private Double quantity;

    @SerializedName("groupNum")
    private String groupNum;

    @SerializedName("coat")
    private String coat;

    @SerializedName("size")
    private String size;

    @SerializedName("itemcode")
    private String itemcode;

    @SerializedName("status")
    private String status;

    @SerializedName("partNo")
    private String partNo;

    public ResultItem(String itemcode, String status) {
        this.itemcode = itemcode;
        this.status = status;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getMillCode() {
        return millCode;
    }

    public void setMillCode(String millCode) {
        this.millCode = millCode;
    }

    public String getPcsPerPkt() {
        return pcsPerPkt;
    }

    public void setPcsPerPkt(String pcsPerPkt) {
        this.pcsPerPkt = pcsPerPkt;
    }

    public int getDocentry() {
        return docentry;
    }

    public void setDocentry(int docentry) {
        this.docentry = docentry;
    }

    public String getNetwt() {
        return netwt;
    }

    public void setNetwt(String netwt) {
        this.netwt = netwt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBatchnum() {
        return batchnum;
    }

    public void setBatchnum(String batchnum) {
        this.batchnum = batchnum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getDcValidation() {
        return dcValidation;
    }

    public void setDcValidation(String dcValidation) {
        this.dcValidation = dcValidation;
    }

    public int getLinenum() {
        return linenum;
    }

    public void setLinenum(int linenum) {
        this.linenum = linenum;
    }

    public String getSecScanUpdate() {
        return secScanUpdate;
    }

    public void setSecScanUpdate(String secScanUpdate) {
        this.secScanUpdate = secScanUpdate;
    }

    public Double getGrosswt() {
        return grosswt;
    }

    public void setGrosswt(Double grosswt) {
        this.grosswt = grosswt;
    }

    public String getSaPPOSTED() {
        return saPPOSTED;
    }

    public void setSaPPOSTED(String saPPOSTED) {
        this.saPPOSTED = saPPOSTED;
    }

    public Double getPcs() {
        return pcs;
    }

    public void setPcs(Double pcs) {
        this.pcs = pcs;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getCoat() {
        return coat;
    }

    public void setCoat(String coat) {
        this.coat = coat;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
}