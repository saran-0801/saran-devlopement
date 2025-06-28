package com.covalsys.ttss_barcode.data.network.models.BarcodeList;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("code")
    private int code;

    @SerializedName("description")
    private String description;

    @SerializedName("errorDetails")
    private String errorDetails;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}