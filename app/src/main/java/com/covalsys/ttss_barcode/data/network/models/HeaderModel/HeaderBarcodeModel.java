package com.covalsys.ttss_barcode.data.network.models.HeaderModel;

import com.google.gson.annotations.SerializedName;

public class HeaderBarcodeModel {

    @SerializedName("result")
    private Result result;

    @SerializedName("pagination")
    private Object pagination;

    @SerializedName("message")
    private Message message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("processedTime")
    private String processedTime;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Object getPagination() {
        return pagination;
    }

    public void setPagination(Object pagination) {
        this.pagination = pagination;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(String processedTime) {
        this.processedTime = processedTime;
    }
}