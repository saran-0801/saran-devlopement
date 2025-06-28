package com.covalsys.ttss_inv.data.network.models.get


import com.google.gson.annotations.SerializedName

data class GetAssetDocumentLineModel(
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
        @SerializedName("BatchNo")
        val batchNo: String,
        @SerializedName("DocEntry")
        val docEntry: Int,
        @SerializedName("ItemCode")
        val itemCode: String,
        @SerializedName("ItemName")
        val itemName: String,
        @SerializedName("Line")
        val line: Int,
        @SerializedName("Remarks")
        val remarks: String,
        @SerializedName("ScanDate")
        val scanDate: String,
        @SerializedName("SysQty")
        val sysQty: Double
    )
}