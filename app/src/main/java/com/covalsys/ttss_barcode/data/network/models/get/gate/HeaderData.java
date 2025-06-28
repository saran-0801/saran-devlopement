package com.covalsys.ttss_barcode.data.network.models.get.gate;

import com.google.gson.annotations.SerializedName;

public class HeaderData {

    @SerializedName("docNum")
    private String docNum;

    @SerializedName("docentry")
    private Integer docentry;

    public HeaderData(String docNum, Integer docentry) {
        this.docNum = docNum;
        this.docentry = docentry;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public Integer getDocentry() {
        return docentry;
    }

    public void setDocentry(Integer docentry) {
        this.docentry = docentry;
    }
}
