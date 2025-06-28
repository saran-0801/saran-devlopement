package com.covalsys.ttss_inv.data.network.models.get


import com.google.gson.annotations.SerializedName

data class GetAssetDocumentHeaderModel(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) {
    data class ResponseObject(
        @SerializedName("DocDate")
        val docDate: String,
        @SerializedName("DocEntry")
        val docEntry: Int,
        @SerializedName("Location")
        val location: String,
        @SerializedName("LoggedUserId")
        val loggedUserId: String,
        @SerializedName("Remarks")
        val remarks: String
    )
}