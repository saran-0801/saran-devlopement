package com.covalsys.ttss_barcode.data.network.models.HeaderModel;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("code")
    private int code;

    @SerializedName("description")
    private String description;

    @SerializedName("errorDetails")
    private Object errorDetails;

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

    public Object getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(Object errorDetails) {
        this.errorDetails = errorDetails;
    }
}