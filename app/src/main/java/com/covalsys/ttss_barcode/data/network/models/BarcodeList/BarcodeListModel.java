package com.covalsys.ttss_barcode.data.network.models.BarcodeList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarcodeListModel {

    @SerializedName("result")
    private List<ResultItem> result;

    @SerializedName("pagination")
    private Object pagination;

    @SerializedName("message")
    private Message message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("processedTime")
    private String processedTime;

    public List<ResultItem> getResult() {
        return result;
    }

    public void setResult(List<ResultItem> result) {
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