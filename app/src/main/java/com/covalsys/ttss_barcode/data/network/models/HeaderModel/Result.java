package com.covalsys.ttss_barcode.data.network.models.HeaderModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {

    @SerializedName("areaOfWkng")
    private String areaOfWkng;

    @SerializedName("docentry")
    private Integer docentry;

    @SerializedName("secCode")
    private String secCode;

    @SerializedName("u_UserName")
    private String uUserName;

    @SerializedName("type")
    private String type;

    @SerializedName("uid")
    private Integer uid;

    @SerializedName("dcValidation")
    private String dcValidation;

    @SerializedName("outPassNo")
    private String outPassNo;

    @SerializedName("totalGROSSWT")
    private Double totalGROSSWT;

    @SerializedName("totDoc")
    private Integer totDoc;

    @SerializedName("docdate")
    private String docdate;

    @SerializedName("saP_POSTED")
    private String saPPOSTED;

    @SerializedName("batchnuma")
    private String batchnuma;

    @SerializedName("doC_STATUS")
    private String doCSTATUS;

    @SerializedName("secName")
    private String secName;

    @SerializedName("batchnumb")
    private String batchnumb;

    @SerializedName("outPassDATE")
    private String outPassDATE;

    @SerializedName("groupNum")
    private Integer groupNum;

    @SerializedName("totalPCS")
    private Double totalPCS;

    @SerializedName("docNo")
    private String docNo;

    @SerializedName("priority")
    private String priority;

    @SerializedName("vehicleNo")
    private String vehicleNo;

    @SerializedName("noDoc")
    private Integer noDoc;

    @SerializedName("cusType")
    private String cusType;

    @SerializedName("totalNETWT")
    private Double totalNETWT;

    @SerializedName("ewayBill")
    private String ewayBill;

    @SerializedName("remarks")
    private String remarks;

    @SerializedName("wrongBatchno")
    private String wrongBatchno;

    public String getWrongBatchno() {
        return wrongBatchno;
    }

    public void setWrongBatchno(String wrongBatchno) {
        this.wrongBatchno = wrongBatchno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAreaOfWkng() {
        return areaOfWkng;
    }

    public void setAreaOfWkng(String areaOfWkng) {
        this.areaOfWkng = areaOfWkng;
    }

    public Integer getDocentry() {
        return docentry;
    }

    public void setDocentry(Integer docentry) {
        this.docentry = docentry;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getUUserName() {
        return uUserName;
    }

    public void setUUserName(String uUserName) {
        this.uUserName = uUserName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getDcValidation() {
        return dcValidation;
    }

    public void setDcValidation(String dcValidation) {
        this.dcValidation = dcValidation;
    }

    public String getOutPassNo() {
        return outPassNo;
    }

    public void setOutPassNo(String outPassNo) {
        this.outPassNo = outPassNo;
    }

    public Double getTotalGROSSWT() {
        return totalGROSSWT;
    }

    public void setTotalGROSSWT(Double totalGROSSWT) {
        this.totalGROSSWT = totalGROSSWT;
    }

    public Integer getTotDoc() {
        return totDoc;
    }

    public void setTotDoc(Integer totDoc) {
        this.totDoc = totDoc;
    }

    public String getDocdate() {
        return docdate;
    }

    public void setDocdate(String docdate) {
        this.docdate = docdate;
    }

    public String getSaPPOSTED() {
        return saPPOSTED;
    }

    public void setSaPPOSTED(String saPPOSTED) {
        this.saPPOSTED = saPPOSTED;
    }

    public String getBatchnuma() {
        return batchnuma;
    }

    public void setBatchnuma(String batchnuma) {
        this.batchnuma = batchnuma;
    }

    public String getDoCSTATUS() {
        return doCSTATUS;
    }

    public void setDoCSTATUS(String doCSTATUS) {
        this.doCSTATUS = doCSTATUS;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getBatchnumb() {
        return batchnumb;
    }

    public void setBatchnumb(String batchnumb) {
        this.batchnumb = batchnumb;
    }

    public String getOutPassDATE() {
        return outPassDATE;
    }

    public void setOutPassDATE(String outPassDATE) {
        this.outPassDATE = outPassDATE;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Double getTotalPCS() {
        return totalPCS;
    }

    public void setTotalPCS(Double totalPCS) {
        this.totalPCS = totalPCS;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Integer getNoDoc() {
        return noDoc;
    }

    public void setNoDoc(Integer noDoc) {
        this.noDoc = noDoc;
    }

    public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    public Double getTotalNETWT() {
        return totalNETWT;
    }

    public void setTotalNETWT(Double totalNETWT) {
        this.totalNETWT = totalNETWT;
    }

    public String getEwayBill() {
        return ewayBill;
    }

    public void setEwayBill(String ewayBill) {
        this.ewayBill = ewayBill;
    }
}