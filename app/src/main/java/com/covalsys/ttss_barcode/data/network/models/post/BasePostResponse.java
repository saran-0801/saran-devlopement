package com.covalsys.ttss_barcode.data.network.models.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CBS on 28-08-2020.
 */
public class BasePostResponse {
    /*@SerializedName("DocEntry")
    @Expose
    private Integer docEntry;
    @SerializedName("DocNum")
    @Expose
    private Integer docNum;*/
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("responseObject")
    @Expose
    private String responseObject;

    /*public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }*/

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(String responseObject) {
        this.responseObject = responseObject;
    }
}
