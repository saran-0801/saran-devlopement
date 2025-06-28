package com.covalsys.ttss_barcode.data.network.models.get.gate


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AreaOfWorkModel(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: Message,
    @SerializedName("pagination")
    val pagination: String?,
    @SerializedName("processedTime")
    val processedTime: String,
    @SerializedName("result")
    val result: List<Result>
) : Parcelable {
    @Parcelize
    data class Message(
        @SerializedName("code")
        val code: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("errorDetails")
        val errorDetails: String?
    ) : Parcelable

    @Parcelize
    data class Result(
        @SerializedName("code")
        val code: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    ) : Parcelable
}