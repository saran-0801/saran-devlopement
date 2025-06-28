package com.covalsys.ttss_inv.data.network.models.get


import com.google.gson.annotations.SerializedName

data class GetAssetDocumentModel(
    @SerializedName("docStatus")
    val docStatus: String,
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) {
    data class ResponseObject(
        @SerializedName("ActQty")
        val actQty: Double,
        @SerializedName("DocNum")
        val docNum: Int,
        @SerializedName("ItemCode")
        val itemCode: String,
        @SerializedName("ItemName")
        val itemName: String,
        @SerializedName("Line")
        val line: Int,
        @SerializedName("ScanDate")
        val scanDate: String,
        @SerializedName("ScanStatus")
        val status: String,
        @SerializedName("SysQty")
        val sysQty: Double
    )
}