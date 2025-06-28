package com.covalsys.ttss_barcode.data.network.models.post


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BasePostErrorResponse(
    @SerializedName("DocEntry")
    val docEntry: String,
    @SerializedName("DocNum")
    val docNum: String,
    @SerializedName("responseObject")
    val responseObject: ResponseObject,
    @SerializedName("statusCode")
    val statusCode: Int, // 1
    @SerializedName("statusMessage")
    val statusMessage: String // An error occurred while processing this request.
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("error")
        val error: Error
    ) : Parcelable {
        @Parcelize
        data class Error(
            @SerializedName("code")
            val code: Int, // -1116
            @SerializedName("message")
            val message: Message
        ) : Parcelable {
            @Parcelize
            data class Message(
                @SerializedName("lang")
                val lang: String, // en-us
                @SerializedName("value")
                val value: String // (-1241) Customer Exceeds Credit Limit *Contact Admin* !
            ) : Parcelable
        }
    }
}