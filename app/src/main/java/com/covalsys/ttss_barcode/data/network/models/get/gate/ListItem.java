package com.covalsys.ttss_barcode.data.network.models.get.gate;

public class ListItem {

    public String slNo, docNo, itemCode, description, size, uom, specification, coat, millCode, custPartNo, batchNo, netWt, grossWt, pcs, status;

    public ListItem(String slNo, String docNo, String itemCode, String description, String size, String uom, String specification, String coat, String millCode, String custPartNo, String batchNo, String netWt, String grossWt, String pcs, String status) {
        this.slNo = slNo;
        this.docNo = docNo;
        this.itemCode = itemCode;
        this.description = description;
        this.size = size;
        this.uom = uom;
        this.specification = specification;
        this.coat = coat;
        this.millCode = millCode;
        this.custPartNo = custPartNo;
        this.batchNo = batchNo;
        this.netWt = netWt;
        this.grossWt = grossWt;
        this.pcs = pcs;
        this.status = status;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getCoat() {
        return coat;
    }

    public void setCoat(String coat) {
        this.coat = coat;
    }

    public String getMillCode() {
        return millCode;
    }

    public void setMillCode(String millCode) {
        this.millCode = millCode;
    }

    public String getCustPartNo() {
        return custPartNo;
    }

    public void setCustPartNo(String custPartNo) {
        this.custPartNo = custPartNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getNetWt() {
        return netWt;
    }

    public void setNetWt(String netWt) {
        this.netWt = netWt;
    }

    public String getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(String grossWt) {
        this.grossWt = grossWt;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
