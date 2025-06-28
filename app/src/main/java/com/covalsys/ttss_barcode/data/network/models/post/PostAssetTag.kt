package com.covalsys.ttss_inv.data.network.models.post

import com.google.gson.annotations.SerializedName

data class PostAssetTag(
    @SerializedName("CreatedDate")
    val createdDate: String,
    @SerializedName("DocDate")
    val docDate: String,
    @SerializedName("line")
    val line: List<Line>,
    @SerializedName("BinCode")
    val location: String,
    @SerializedName("LoggedUser")
    val loggedUser: String,
    @SerializedName("DocNum")
    val remarks: String,
    @SerializedName("DocStatus")
    val docStatus: String,
    @SerializedName("UpdatedDate")
    val updatedDate: String
) {
    data class Line(
        @SerializedName("ActQty")
        val actQty: Double,
        @SerializedName("DocNum")
        val docEntry: String,
        @SerializedName("ItemCode")
        val itemCode: String,
        @SerializedName("ItemName")
        val itemName: String,
        @SerializedName("Line")
        val line: String,
        @SerializedName("ScanDate")
        val scanDate: String,
        @SerializedName("ScanStatus")
        val scanStatus: String,
        @SerializedName("SysQty")
        val sysQty: Double
    )
}