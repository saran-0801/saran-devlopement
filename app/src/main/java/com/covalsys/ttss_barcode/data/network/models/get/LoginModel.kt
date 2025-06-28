package com.covalsys.ttss_barcode.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginModel(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: Message,
    @SerializedName("pagination")
    val pagination: String?,
    @SerializedName("processedTime")
    val processedTime: String?,
    @SerializedName("result")
    val result: Result
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
        @SerializedName("token")
        val token: String,
        @SerializedName("tokenRole")
        val tokenRole: String,
        @SerializedName("user")
        val user: User
    ) : Parcelable {
        @Parcelize
        data class User(
            @SerializedName("department")
            val department: String,
            @SerializedName("docEntry")
            val docEntry: Int,
            @SerializedName("locked")
            val locked: String,
            @SerializedName("password")
            val password: String,
            @SerializedName("userCode")
            val userCode: String,
            @SerializedName("userName")
            val userName: String,
            @SerializedName("userType")
            val userType: String
        ) : Parcelable
    }
}