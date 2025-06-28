package com.covalsys.ttss_barcode.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetLocationModel(
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("LocationCode")
        val locationCode: String,
        @SerializedName("LocationName")
        val locationName: String
    ) : Parcelable{
        override fun toString(): String {
            return locationName
        }
    }
}